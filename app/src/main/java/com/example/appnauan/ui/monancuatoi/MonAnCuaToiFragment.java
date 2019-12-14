package com.example.appnauan.ui.monancuatoi;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.MainActivity;
import com.example.appnauan.MonAn;
import com.example.appnauan.MonAnAdapter;
import com.example.appnauan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MonAnCuaToiFragment extends Fragment {

    private MonAnCuaToiViewModel slideshowViewModel;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter adapter;
    GridView gvMonAnCuaToi;
    ArrayList<MonAn> arrayListMonAn=new ArrayList<>();
    ArrayList<MonAn> arrayListMonAnCuaToi=new ArrayList<>();
    String urlGetMonAn="http://172.17.20.139:8080/AppNauAn/Database/dbAppNauAn/getMonAn.php";
//    String urlGetMonAn="http://10.80.255.137:8080/dbAppNauAn/getMonAn.php";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(MonAnCuaToiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monancuatoi, container, false);
        gvMonAnCuaToi=(GridView) root.findViewById(R.id.gridviewMonAnCuaToi);
        GetMonAn(urlGetMonAn);
        //getMonAnCuaToi();
        adapter=new MonAnAdapter(getActivity(),R.layout.item_monan,arrayListMonAnCuaToi);
        gvMonAnCuaToi.setAdapter(adapter);
        return root;
    }
    private void GetMonAn(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayListMonAn.clear();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        MonAn monAn = new MonAn();
                        monAn.setMaMonAn(object.getInt("mamonan"));
                        monAn.setTenMonAn(object.getString("tenmonan"));
                        monAn.setDSNguyenLieu(object.getString("nguyenlieu"));
                        monAn.setCongThuc(object.getString("congthuc"));
                        monAn.setHinhAnh(object.getString("hinhanh"));
                        monAn.setMaNguoiDung(object.getInt("manguoidung"));
                        monAn.setMaLoaiMonAn(object.getInt("manguoidung"));
                        arrayListMonAn.add(monAn);
                        if(MainActivity.instance.nguoidung!=null){
                            if(monAn.getMaNguoiDung()==MainActivity.instance.nguoidung.getMaNguoiDung())
                            {
                                arrayListMonAnCuaToi.add(monAn);
                            }
                        }

//                        arrayListMonAn.add(new MonAn(
//                                object.getInt("mamonan"),
//                                object.getString("tenmonan"),
//                                object.getString("nguyenlieu"),
//                                object.getString("congthuc"),
//                                object.getString("hinhanh"),
//                                object.getInt("maloaimonan"),
//                                object.getInt("manguoidung")
//
//                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //Lấy thông tin món ăn của user đã thêm vào
    public void getMonAnCuaToi()
    {
        for(MonAn monan: arrayListMonAn)
        {
            //if(MainActivity.instance.nguoidung.getMaNguoiDung()== monan.getMaNguoiDung())
            {
                arrayListMonAnCuaToi.add(monan);
                Toast.makeText(getActivity(),monan.getTenMonAn().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}