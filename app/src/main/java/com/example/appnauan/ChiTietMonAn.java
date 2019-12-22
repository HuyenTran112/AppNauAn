package com.example.appnauan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.ui.dangnhap.DangNhapFragment;
import com.example.appnauan.ui.dsmonan.DsMonAnFragment;
import com.example.appnauan.ui.monancuatoi.MonAnCuaToiFragment;
import com.example.appnauan.ui.monancuatoi.MonAnCuaToiViewModel;
import com.example.appnauan.ui.quanly.QuanLyFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietMonAn extends AppCompatActivity {
    TextView txtTenMonAn, txtCongThuc, txtDsNguyenLieu, txtLoaiMonAn;
    ImageView imgHinh;
    ArrayList<LoaiMonAn> arrayListLoaiMonAn;

    //String urlGetLoaiMonAn="http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/getLoaiMonAn.php";
    /*String urlGetLoaiMonAn="http://10.80.255.123:8080/dbappnauan/getLoaiMonAn.php";
    String urlDelete="http://10.80.255.123:8080/dbappnauan/deleteMonAn.php";
    String urlgetData="http://10.80.255.123:8080/dbappnauan/getMonAn.php";*/
    String urlGetLoaiMonAn="http://192.168.1.105/dbappnauan/getLoaiMonAn.php";
    String urlDelete="http://192.168.1.105/dbappnauan/deleteMonAn.php";
    String urlgetData="http://192.168.1.105/dbappnauan/getMonAn.php";

    String TenLoaiMonAn;
    MonAn monAn;
    Button btnThoat, btnCapNhat,btnXoa;
    Context context;
    ArrayList<MonAn> arrayMonAn;
    MonAnAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        Intent intent=getIntent();
        arrayMonAn=new ArrayList<>();
        //lấy dữ liệu được gửi qua từ intent
        monAn= (MonAn) intent.getSerializableExtra("dataMonAn");
        adapter=new MonAnAdapter(ChiTietMonAn.this,R.layout.item_monan,arrayMonAn);
        AnhXa();
        //kiểm tra nếu làm món ăn của người dùng hiện tại thì set lại button cập nhật và button thêm
        if(CheckMonAnCuaToi() == true)
        {
            btnCapNhat.setVisibility(View.VISIBLE);
            btnXoa.setVisibility(View.VISIBLE);
        }
        else
        {
            btnCapNhat.setVisibility(View.GONE);
            btnXoa.setVisibility(View.GONE);
        }

        arrayListLoaiMonAn=new ArrayList<>();
        GetLoaiMonAn(urlGetLoaiMonAn);
        //set lại giá trị cho các textview theo món ăn được chọn
        txtTenMonAn.setText(txtTenMonAn.getText()+monAn.getTenMonAn());
        txtCongThuc.setText(txtCongThuc.getText()+monAn.getCongThuc());
        txtDsNguyenLieu.setText(txtDsNguyenLieu.getText()+monAn.getDSNguyenLieu());
        Picasso.with(this).load(monAn.getHinhAnh()).into(imgHinh);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuanLyFragment quanLyFragment = new QuanLyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tieude", "Cập nhật món ăn");
                bundle.putInt("mamonan", monAn.getMaMonAn());
                bundle.putString("tenmonan", monAn.getTenMonAn());
                bundle.putString("nguyenlieu", monAn.getDSNguyenLieu());
                bundle.putString("congthuc", monAn.getCongThuc());
                bundle.putString("hinhanh", monAn.getHinhAnh());
                bundle.putInt("maloaimonan", monAn.getMaLoaiMonAn());
                quanLyFragment.setArguments(bundle);


                fragmentTransaction.add(R.id.activityCTMonAn, quanLyFragment, "CapNhatMonAn");

                fragmentTransaction.commit();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(monAn.getTenMonAn(),monAn.getMaMonAn());
            }
        });
    }

    private void AnhXa() {
        txtTenMonAn=(TextView) findViewById(R.id.textViewTenMonAnCT);
        txtCongThuc=(TextView)findViewById(R.id.textViewCongThucCT);
        txtDsNguyenLieu=(TextView)findViewById(R.id.textViewDsNguyenLieuCT);
        imgHinh=(ImageView) findViewById(R.id.imageViewHinhMonAnCT);
        txtLoaiMonAn=(TextView) findViewById(R.id.textViewLoaiMonAnCT);
        btnThoat=(Button) findViewById(R.id.buttonThoatCT);
        btnCapNhat = (Button) findViewById(R.id.buttonCapNhat);
        btnXoa=(Button) findViewById(R.id.buttonDeleteCT);
    }
    //Lấy dữ liệu loại món ăn
    public void GetLoaiMonAn(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(ChiTietMonAn.this);
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
                        for(LoaiMonAn loaiMonAn: arrayListLoaiMonAn)
                        {
                            if(loaiMonAn.getMaLoaiMonAn()==monAn.getMaLoaiMonAn())
                            {
                                TenLoaiMonAn=loaiMonAn.getTenLoaiMonAn();
                            }

                        }
                        txtLoaiMonAn.setText("Tên loại món ăn: "+TenLoaiMonAn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChiTietMonAn.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    public boolean CheckMonAnCuaToi(){
        if(MainActivity.instance.nguoidung!=null){
            if(monAn.getMaNguoiDung()== MainActivity.instance.nguoidung.getMaNguoiDung())
            {
                return true;
            }
        }
        return false;
    }
    //xuất hiện dialog có muốn xóa món ăn hay không
    private void XacNhanXoa(String ten, final int id)
    {
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(ChiTietMonAn.this);
        dialogXoa.setMessage("Bạn có muốn xóa món "+ten+" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteMonAn(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }
    //hàm xóa món ăn
    public void DeleteMonAn(final int id)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(ChiTietMonAn.this,"Xóa món ăn thành công",Toast.LENGTH_SHORT).show();
                    GetData(urlgetData);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    DsMonAnFragment quanLyFragment = new DsMonAnFragment();
                    fragmentTransaction.add(R.id.activityCTMonAn, quanLyFragment);
                    fragmentTransaction.commit();
                }
                else
                    Toast.makeText(ChiTietMonAn.this,"Lỗi xóa",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChiTietMonAn.this,"Xảy ra lỗi. Vui lòng thử lại",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("mamonan",String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //Hàm load lại dữ liệu sau khi xóa món ăn
    private void GetData(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayMonAn.clear();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        arrayMonAn.add(new MonAn(
                                object.getInt("mamonan"),
                                object.getString("tenmonan"),
                                object.getString("nguyenlieu"),
                                object.getString("congthuc"),
                                object.getString("hinhanh"),
                                object.getInt("maloaimonan"),
                                object.getInt("manguoidung")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChiTietMonAn.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
