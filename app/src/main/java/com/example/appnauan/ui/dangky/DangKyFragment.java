package com.example.appnauan.ui.dangky;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.MainActivity;
import com.example.appnauan.R;
import com.example.appnauan.ui.dangnhap.DangNhapFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DangKyFragment extends Fragment {

    private DangKyViewModel dangkyViewModel;
    Button btnSignUp;
    EditText edtEmail;
    EditText edtPassWord;
    EditText edtTenHienThi;
    ImageView imgHinhAnh;
    public View view;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dangkyViewModel =
                ViewModelProviders.of(this).get(DangKyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dangky, container, false);
        view=root;
        AnhXa();
        imgHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
//                ActivityCompat.requestPermissions(
//                        getActivity(),
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_CODE_FOLDER
//                );

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenhienthi=edtTenHienThi.getText().toString();
                String email=edtEmail.getText().toString();
                String matkhau=edtPassWord.getText().toString();
                BitmapDrawable bitmapDrawable= (BitmapDrawable) imgHinhAnh.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
                String loaiuser="User";
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                //chuyển về mảng byte[]
                byte[] hinhAnh=byteArray.toByteArray();
                MainActivity.database.Insert_User(
                        tenhienthi,
                        email,
                        matkhau,
                        loaiuser,
                        hinhAnh
                );
                Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                edtEmail.setText("");
                edtPassWord.setText("");
                //.setText("");
                imgHinhAnh.setImageResource(R.drawable.noimage);
                DangNhapFragment nextFrag= new DangNhapFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
                return root;

    }
    public void AnhXa()
    {
        btnSignUp=(Button) view.findViewById(R.id.btn_sign_up);
        edtEmail=(EditText)view.findViewById(R.id.et_email);
        edtPassWord=(EditText)view.findViewById(R.id.et_password);
        edtTenHienThi=(EditText)view.findViewById(R.id.et_tenhienthi);
        imgHinhAnh=(ImageView) view.findViewById(R.id.imageViewHinhUser);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(REQUEST_CODE_CAMERA==requestCode  && data!=null)
        {
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            imgHinhAnh.setImageBitmap(bitmap);
        }
        if(REQUEST_CODE_FOLDER==requestCode  &&data!=null)
        {
            Uri uri=data.getData();
            try {
                InputStream inputStream=getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imgHinhAnh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}