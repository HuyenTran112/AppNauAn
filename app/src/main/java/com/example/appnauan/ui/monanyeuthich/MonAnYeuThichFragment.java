package com.example.appnauan.ui.monanyeuthich;

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

public class MonAnYeuThichFragment extends Fragment {

    private MonAnYeuThichViewModel galleryViewModel;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter adapter;
    GridView gvMonAnYeuThich;
    ArrayList<MonAn> arrayListMonAn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(MonAnYeuThichViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monanyeuthich, container, false);
        arrayListMonAn=new ArrayList<>();
        gvMonAnYeuThich=(GridView) root.findViewById(R.id.gridviewMonAnYeuThich);
        adapter=new MonAnAdapter(getActivity(),R.layout.item_monan,arrayListMonAn);
        GetMonAn(MainActivity.instance.urlGetDsMonAnYeuThich);
        gvMonAnYeuThich.setAdapter(adapter);
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
                        if( MainActivity.instance.nguoidung.getMaNguoiDung()==object.getInt("manguoidung"))
                        {
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
                    gvMonAnYeuThich.setAdapter(adapter);
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
    public void setAdapter()
    {
        adapter.notifyDataSetChanged();
        gvMonAnYeuThich.setAdapter(adapter);

    }

}