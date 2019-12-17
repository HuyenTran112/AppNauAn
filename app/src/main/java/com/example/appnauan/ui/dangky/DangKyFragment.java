package com.example.appnauan.ui.dangky;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.MainActivity;
import com.example.appnauan.R;
import com.example.appnauan.ui.dangnhap.DangNhapFragment;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class DangKyFragment extends Fragment {

    private DangKyViewModel dangkyViewModel;
    Button btnSignUp;
    EditText edtEmail;
    EditText edtPassWord;
    EditText edtTenHienThi;
    ImageView imgHinhAnh;
    public View view;
    private Uri filePath;
    private int counter=0;
    private Bitmap bitmap;
    final int IMAGE_REQUEST_CODE=123;
    final int REQUEST_CODE_FOLDER=456;
    //private static final String URL_INSERT_USER = "http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/insertUser.php";
    private static final String URL_INSERT_USER = "http://10.80.255.123:8080/dbappnauan/insertUser.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dangkyViewModel =
                ViewModelProviders.of(this).get(DangKyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dangky, container, false);
        view=root;
        AnhXa();
        requestStoragePermission();
        //Bắt sự kiện chọn hình ảnh
        imgHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
            }
        });

        //Đăng ký
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy thông tin đăng ký
                String tenhienthi=edtTenHienThi.getText().toString().trim();
                String email=edtEmail.getText().toString().trim();
                String matkhau=edtPassWord.getText().toString().trim();
                BitmapDrawable bitmapDrawable= (BitmapDrawable) imgHinhAnh.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                //Kiểm tra dữ liệu đăng ký nếu không đầy đủ thì yêu cầu nhập đầy đủ thông tin
                if(tenhienthi.isEmpty() || email.isEmpty() || matkhau.isEmpty() || bitmap.sameAs(emptyBitmap))
                {
                    Toast.makeText(getActivity(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                //ngược lại tiến hành đăng ký cho người dùng
                else{
                    ThemUser();
                    edtEmail.setText("");
                    edtPassWord.setText("");
                    edtPassWord.setText("");
                    edtTenHienThi.setText("");
                    imgHinhAnh.setImageResource(R.drawable.avatar);
//                    Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
//                    DangNhapFragment nextFrag= new DangNhapFragment();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
//                            .addToBackStack(null)
//                            .commit();
                }

            }
        });
                return root;

    }
    //Set lại imgHinh khi chọn hình
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(MainActivity.instance.getContentResolver(), filePath);
                imgHinhAnh.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void AnhXa()
    {
        btnSignUp=(Button) view.findViewById(R.id.btn_sign_up);
        edtEmail=(EditText)view.findViewById(R.id.et_email);
        edtPassWord=(EditText)view.findViewById(R.id.et_password);
        edtTenHienThi=(EditText)view.findViewById(R.id.et_tenhienthi);
        imgHinhAnh=(ImageView) view.findViewById(R.id.imageViewHinhUser);
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
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == REQUEST_CODE_FOLDER) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    //Thêm user

    public void ThemUser() {
        //String caption = etCaption.getText().toString().trim();
        String Email=edtEmail.getText().toString();
        String TenHienThi=edtTenHienThi.getText().toString().trim();
        String MatKhau=edtPassWord.getText().toString().trim();
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, URL_INSERT_USER)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("email", Email)
                    .addParameter("tenhienthi",TenHienThi)
                    .addParameter("matkhau",MatKhau)
                    .addParameter("maloainguoidung","2")
                    .setUtf8Charset()
                    //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            Toast.makeText(getActivity(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        } catch (Exception exc) {
//            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
        }
    }

}