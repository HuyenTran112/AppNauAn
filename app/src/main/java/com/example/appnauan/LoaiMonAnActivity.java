package com.example.appnauan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoaiMonAnActivity extends AppCompatActivity {

    ArrayList<MonAn> arrayListMonAn;
    Context context;
    MonAnAdapter adapter;
    LoaiMonAn loaiMonAn;
    GridView gvLoaiMonAn;
    Button btnThoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_mon_an);
        Intent intent=getIntent();
        arrayListMonAn=new ArrayList<>();
        //lấy dữ liệu được gửi qua từ intent
        loaiMonAn= (LoaiMonAn) intent.getSerializableExtra("dataLoaiMonAn");
        Toast.makeText(this,loaiMonAn.getTenLoaiMonAn(),Toast.LENGTH_SHORT).show();
       adapter=new MonAnAdapter(LoaiMonAnActivity.this,R.layout.item_monan,arrayListMonAn);
       GetMonAn(MainActivity.instance.urlGetMonAn);
        gvLoaiMonAn=(GridView)findViewById(R.id.gridviewHinhAnhMonAnLT);
        gvLoaiMonAn.setAdapter(adapter);
        btnThoat=(Button) findViewById(R.id.buttonThoatLoaiMA);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetMonAn(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayListMonAn.clear();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        if(object.getInt("maloaimonan")==loaiMonAn.getMaLoaiMonAn()) {
                            arrayListMonAn.add(new MonAn(
                                    object.getInt("mamonan"),
                                    object.getString("tenmonan"),
                                    object.getString("nguyenlieu"),
                                    object.getString("congthuc"),
                                    object.getString("hinhanh"),
                                    object.getInt("maloaimonan"),
                                    object.getInt("manguoidung")

                            ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoaiMonAnActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
