package com.example.appnauan.ui.quanly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.LoaiMonAn;
import com.example.appnauan.LoaiMonAnAdapter;
import com.example.appnauan.MainActivity;
import com.example.appnauan.R;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.example.appnauan.R.drawable.noimage;

public class QuanLyFragment extends Fragment {

    private QuanLyViewModel shareViewModel;
    EditText edtTenMonAn, edtDSNguyenLieu, edtCongThuc;
    TextView tvTieude;
    ImageView imgHinhAnh;
     Button btnThem, btnHuy, btnCapNhat;
    public  static View view;
    private static final int IMAGE_REQUEST_CODE = 3;
    final int REQUEST_CODE_FOLDER=456;
   // private static final String URL_INSERT = "http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/insert_monan.php";
    //private static final String URL_UPDATE = "http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/update_monan.php";
    //private static final String URL_UPDATE1 = "http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/upd_monan.php";
    private static final String URL_UPDATE = "http://10.80.255.123:8080/dbappnauan/update_monan.php";
    private static final String URL_UPDATE1 = "http://10.80.255.123:8080/dbappnauan/upd_monan.php";
   // String urlGetLoaiMonAn="http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/getLoaiMonAn.php";
   private static final String URL_INSERT = "http://10.80.255.123:8080/dbappnauan/insert_monan.php";
    String urlGetLoaiMonAn="http://10.80.255.123:8080/dbappnauan/getLoaiMonAn.php";
    private Uri filePath;
    private int counter=0;
    private Bitmap bitmap;
    Spinner spinnerLoaiMonAn;
    ArrayList<LoaiMonAn> arrayListLoaiMonAn=new ArrayList<>();
    LoaiMonAnAdapter loaiMonAnAdapter;
    String maloaimonan, urlImage = "";

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
        //Kiểm tra tiêu đề nếu là cập nhật thì set lại chế độ cho button CapNhat Them
        if ((tvTieude.getText()).equals("Cập nhật món ăn")) {
            btnCapNhat.setVisibility(View.VISIBLE);
            btnThem.setVisibility(View.GONE);
        }
        else{
            btnCapNhat.setVisibility(View.GONE);
            btnThem.setVisibility(View.VISIBLE);
        }

        loaiMonAnAdapter=new LoaiMonAnAdapter(getActivity(),R.layout.item_loaimonan,arrayListLoaiMonAn);
        spinnerLoaiMonAn.setAdapter(loaiMonAnAdapter);
        //Lấy thông tin loại món ăn
        GetLoaiMonAn(urlGetLoaiMonAn, 0);
        requestStoragePermission();
        //chọn hình ảnh
        imgHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
            }
        });
        //bắt sự kiện cho button thêm
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

                Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Drawable myDrawable = getResources().getDrawable(noimage);
                Bitmap bitmapfirst      = ((BitmapDrawable) myDrawable).getBitmap();
                //kiểm tra thông tin dữ liệu đã đầy đủ hay chưa, nếu chưa thì yêu cầu nhập lại thông tin
                if(TenMonAn.isEmpty() || DsNguyenLieu.isEmpty()||CongThuc.isEmpty()||bitmap.sameAs(emptyBitmap)|bitmap.sameAs(bitmapfirst))
                {
                    Toast.makeText(getActivity(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                //ngược lại thì tiến hành thêm món ăn và set lại text cho các textview
                else{
                    ThemMonAn();
                    edtCongThuc.setText("");
                    edtDSNguyenLieu.setText("");
                    edtTenMonAn.setText("");
                    imgHinhAnh.setImageResource(noimage);
                    Toast.makeText(getActivity(),"Thêm món ăn thành công",Toast.LENGTH_SHORT).show();

                }
      }
        });
        //bắt sự kiện cho button Hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTenMonAn.setText("");
                edtDSNguyenLieu.setText("");
                edtCongThuc.setText("");
                imgHinhAnh.setImageResource(noimage);
            }
        });
        //bắt sự kiện chọ button cập nhật
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TenMonAn=edtTenMonAn.getText().toString().trim();
                String DsNguyenLieu=edtDSNguyenLieu.getText().toString().trim();
                String CongThuc=edtCongThuc.getText().toString().trim();

                BitmapDrawable bitmapDrawable= (BitmapDrawable) imgHinhAnh.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);

                Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                if(TenMonAn.isEmpty() || DsNguyenLieu.isEmpty()||CongThuc.isEmpty()||bitmap.sameAs(emptyBitmap))
                {
                    Toast.makeText(getActivity(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else{
                    CapNhatMonAn();
                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null){
            tvTieude.setText(bundle.getString("tieude"));
            edtTenMonAn.setText(bundle.getString("tenmonan"));
            edtDSNguyenLieu.setText(bundle.getString("nguyenlieu"));
            edtCongThuc.setText(bundle.getString("congthuc"));
            GetLoaiMonAn(urlGetLoaiMonAn, bundle.getInt("maloaimonan"));
            Picasso.with(getActivity()).load(bundle.getString("hinhanh")).into(imgHinhAnh);

            if ((tvTieude.getText()).equals("Cập nhật món ăn")) {
                btnCapNhat.setVisibility(View.VISIBLE);
                btnThem.setVisibility(View.GONE);
            }
            else{
                btnCapNhat.setVisibility(View.GONE);
                btnThem.setVisibility(View.VISIBLE);
            }
        }

        spinnerLoaiMonAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloaimonan=arrayListLoaiMonAn.get(i).getMaLoaiMonAn()+"";
                //Toast.makeText(getActivity(),arrayListLoaiMonAn.get(i).getMaLoaiMonAn()+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return root;
    }

    private void AnhXa() {
        edtTenMonAn=(EditText)getView().findViewById(R.id.editTextTenMonAn);
        edtDSNguyenLieu=(EditText)view.findViewById(R.id.editTextDsNguyenLieu);
        edtCongThuc=(EditText) view.findViewById(R.id.editTextCongThuc);
        imgHinhAnh=(ImageView) view.findViewById(R.id.imageViewHinhMonAn);
        btnThem=(Button) view.findViewById(R.id.buttonAddMonAn);
        btnHuy=(Button) view.findViewById(R.id.buttonHuyAddMonAn);
        btnCapNhat = (Button) view.findViewById(R.id.buttonUpdateMonAn);
        spinnerLoaiMonAn=(Spinner) view.findViewById(R.id.spinnerLoaiMonAn);
        tvTieude = (TextView) view.findViewById(R.id.textView);
    }

    //set lại imgHinh sao khi chọn hình ảnh
    @Override
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


    public void ThemMonAn() {
        //String caption = etCaption.getText().toString().trim();
        String TenMonAn=edtTenMonAn.getText().toString().trim();
        String DsNguyenLieu=edtDSNguyenLieu.getText().toString().trim();
        String CongThuc=edtCongThuc.getText().toString().trim();
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            String manguoidung=String.valueOf(MainActivity.instance.nguoidung.getMaNguoiDung());
            new MultipartUploadRequest(getActivity(), uploadId, URL_INSERT)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("tenmonan", TenMonAn)
                    .addParameter("nguyenlieu",DsNguyenLieu)
                    .addParameter("congthuc",CongThuc)
                    .addParameter("maloaimonan",maloaimonan)
                    .addParameter("manguoidung",manguoidung)
                    .setUtf8Charset()
                    //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
        } catch (Exception exc) {
            //Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void CapNhatMonAn() {
        final Integer MaMonAn;
        Bundle bundle1 = getArguments();
        if (bundle1 != null) {
            MaMonAn = bundle1.getInt("mamonan");

        } else {
            MaMonAn = 0;
        }

        final String TenMonAn = edtTenMonAn.getText().toString().trim();
        final String DsNguyenLieu = edtDSNguyenLieu.getText().toString().trim();
        final String CongThuc = edtCongThuc.getText().toString().trim();

        //getting the actual path of the image
        String path;
        try {
            path = getPath(filePath);
        } catch (Exception e) {
            path = "http";
        }

        try {
            String uploadId = UUID.randomUUID().toString();
            if (path.equals("http")) {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Toast.makeText(getActivity(), "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
                            edtCongThuc.setText("");
                            edtDSNguyenLieu.setText("");
                            edtTenMonAn.setText("");
                            imgHinhAnh.setImageResource(noimage);
                        }
                        else
                            Toast.makeText(getActivity(),"Lỗi cập nhật!",Toast.LENGTH_SHORT).show();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(),"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("mamonan", MaMonAn.toString());
                        params.put("tenmonan", TenMonAn);
                        params.put("nguyenlieu", DsNguyenLieu);
                        params.put("congthuc", CongThuc);
                        params.put("maloaimonan", maloaimonan);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
            else {
                //Creating a multi part request
                new MultipartUploadRequest(getActivity(), uploadId, URL_UPDATE)
                        .addParameter("strpath", "upload")
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("mamonan", MaMonAn.toString())
                        .addParameter("tenmonan", TenMonAn)
                        .addParameter("nguyenlieu", DsNguyenLieu)
                        .addParameter("congthuc", CongThuc)
                        .addParameter("maloaimonan", maloaimonan)
                        .setUtf8Charset()
                        //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload
                edtCongThuc.setText("");
                edtDSNguyenLieu.setText("");
                edtTenMonAn.setText("");
                imgHinhAnh.setImageResource(noimage);
                Toast.makeText(getActivity(),"Cập nhật món ăn thành công",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(),"Lỗi cập nhật!",Toast.LENGTH_SHORT).show();
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
    //Lấy dữ liệu loại món ăn
    public void GetLoaiMonAn(String url, final int pos)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayListLoaiMonAn.clear();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        arrayListLoaiMonAn.add(new LoaiMonAn(
                                object.getInt("maloaimonan"),
                                object.getString("tenloaimonan")

                        ));
                        spinnerLoaiMonAn.setSelection(pos-1);
                        } catch (JSONException e) {
                       // e.printStackTrace();
                    }
                    loaiMonAnAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

}