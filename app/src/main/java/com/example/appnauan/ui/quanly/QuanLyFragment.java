package com.example.appnauan.ui.quanly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.MainActivity;
import com.example.appnauan.R;
import com.example.appnauan.ui.dsmonan.DsMonAnFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class QuanLyFragment extends Fragment {

    private QuanLyViewModel shareViewModel;
    EditText edtTenMonAn, edtDSNguyenLieu, edtCongThuc;
    ImageView imgHinhAnh;
    ImageButton ibtnCamera, ibtnFolder;
    Button btnThem, btnHuy;
    public View view;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(QuanLyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quanly, container, false);
        view=root;
        getActivity().getFragmentManager().popBackStack();
        AnhXa();
        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);

//               ActivityCompat.requestPermissions(
//                        getActivity(),
//                        new String[]{Manifest.permission.CAMERA},
//                        REQUEST_CODE_CAMERA
//                );
            }

        });

        ibtnFolder.setOnClickListener(new View.OnClickListener() {
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

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TenMonAn=edtTenMonAn.getText().toString().trim();
                String DsNguyenLieu=edtDSNguyenLieu.getText().toString().trim();
                String CongThuc=edtCongThuc.getText().toString().trim();
                BitmapDrawable bitmapDrawable= (BitmapDrawable) imgHinhAnh.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                //chuyển về mảng byte[]
                byte[] hinhAnh=byteArray.toByteArray();
                MainActivity.database.Insert_MonAn(
                        TenMonAn,
                        DsNguyenLieu,
                        CongThuc,
                        hinhAnh
                );
                Toast.makeText(getActivity(),"Đã thêm",Toast.LENGTH_SHORT).show();
                edtTenMonAn.setText("");
                edtDSNguyenLieu.setText("");
                edtCongThuc.setText("");
                imgHinhAnh.setImageResource(R.drawable.noimage);
//                DsMonAnFragment dsMonAnFragment=new DsMonAnFragment();
//                dsMonAnFragment.setGridView();
                //Toast.makeText(getActivity(),TenMonAn+" "+DsNguyenLieu+" "+CongThuc+" "+hinhAnh,Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTenMonAn.setText("");
                edtDSNguyenLieu.setText("");
                edtCongThuc.setText("");
                imgHinhAnh.setImageResource(R.drawable.noimage);
            }
        });
        return root;
    }

    private void AnhXa() {
        edtTenMonAn=(EditText)view.findViewById(R.id.editTextTenMonAn);
        edtDSNguyenLieu=(EditText)view.findViewById(R.id.editTextDsNguyenLieu);
        edtCongThuc=(EditText) view.findViewById(R.id.editTextCongThuc);
        ibtnCamera=(ImageButton) view.findViewById(R.id.imageButtonCamera);
        ibtnFolder=(ImageButton) view.findViewById(R.id.imageButtonOpenFolder);
        imgHinhAnh=(ImageView) view.findViewById(R.id.imageViewHinh);
        btnThem=(Button) view.findViewById(R.id.buttonAdd);
        btnHuy=(Button) view.findViewById(R.id.buttonHuy);
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