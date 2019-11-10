package com.example.appnauan.ui.quanly;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.MainActivity;
import com.example.appnauan.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class QuanLyFragment extends Fragment {

    private QuanLyViewModel shareViewModel;
    EditText edtTenMonAn, edtDSNguyenLieu, edtCongThuc;
    ImageView imgHinhAnh;
     Button btnThem, btnHuy;
    public  static View view;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    private static final String URL_INSERT = "http://10.80.255.137:8080/uploadhinhanh/insert_image.php";
    private Uri filePath;
    private int counter=0;


    public View getView()
    {
        return this.view;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(QuanLyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quanly, container, false);
        view=root;
        getActivity().getFragmentManager().popBackStack();
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
                Toast.makeText(getActivity(),"Path: "+getPath(filePath),Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void AnhXa() {
        edtTenMonAn=(EditText)getView().findViewById(R.id.editTextTenMonAn);
        edtDSNguyenLieu=(EditText)view.findViewById(R.id.editTextDsNguyenLieu);
        edtCongThuc=(EditText) view.findViewById(R.id.editTextCongThuc);
        imgHinhAnh=(ImageView) view.findViewById(R.id.imageViewHinhMonAn);
        btnThem=(Button) view.findViewById(R.id.buttonAdd);
        btnHuy=(Button) view.findViewById(R.id.buttonHuy);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(REQUEST_CODE_CAMERA==requestCode  && data!=null)
        {
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            imgHinhAnh.setImageBitmap(bitmap);
            File file = new File(Environment.getExternalStorageDirectory(), counter+"");

            //Uri of camera image
            filePath = FileProvider.getUriForFile(getActivity(), MainActivity.instance.getApplicationContext().getPackageName() + ".provider", file);

        }
        if(REQUEST_CODE_FOLDER==requestCode  &&data!=null)
        {
            filePath = data.getData();
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


    public void uploadMultipart() {
        //String caption = etCaption.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, URL_INSERT)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("caption", "") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
        } catch (Exception exc) {
            //Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = MainActivity.instance.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = MainActivity.instance.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }




}