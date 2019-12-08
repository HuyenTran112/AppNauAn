package com.example.appnauan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.ui.monancuatoi.MonAnCuaToiFragment;
import com.example.appnauan.ui.quanly.QuanLyFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChiTietMonAn extends AppCompatActivity {
    TextView txtTenMonAn, txtCongThuc, txtDsNguyenLieu, txtLoaiMonAn;
    ImageView imgHinh;
    ArrayList<LoaiMonAn> arrayListLoaiMonAn;
    String urlGetLoaiMonAn="http://172.17.28.47:8080/AppNauAn/Database/dbappnauan/getLoaiMonAn.php";
//    String urlGetLoaiMonAn="http://10.80.255.137:8080/dbappnauan/getLoaiMonAn.php";
    String TenLoaiMonAn;
    MonAn monAn;
    Button btnThoat, btnCapNhat;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        Intent intent=getIntent();
        monAn= (MonAn) intent.getSerializableExtra("dataMonAn");
        AnhXa();

        if(CheckMonAnCuaToi() == true)
            btnCapNhat.setVisibility(View.VISIBLE);
        else
            btnCapNhat.setVisibility(View.GONE);

        arrayListLoaiMonAn=new ArrayList<>();
        GetLoaiMonAn(urlGetLoaiMonAn);
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

                fragmentTransaction.add(R.id.activityCTMonAn, quanLyFragment);
                fragmentTransaction.commit();
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
}
