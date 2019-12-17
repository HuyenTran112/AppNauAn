package com.example.appnauan.ui.timkiem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.appnauan.MainActivity;
import com.example.appnauan.MonAn;
import com.example.appnauan.MonAnAdapter;
import com.example.appnauan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimKiemFragment extends Fragment {
    View view;
    Toolbar toolbarTimKiem;
    GridView gvTimKiem;
    TextView tvKhongTimThay;
    ImageButton ImbtnSearch;
    EditText etkeySearch;

    private TimKiemViewModel timKiemViewModel;
    MonAnAdapter adapter;
    ArrayList<MonAn> arrayListSearchMonAn = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timKiemViewModel = ViewModelProviders.of(this).get(TimKiemViewModel.class);

        view = inflater.inflate(R.layout.fragment_timkiem, container, false);
//        view = inflater.inflate(R.layout.fragment_monancuatoi, container, false);
//        toolbarTimKiem = view.findViewById(R.id.toolbarSearchMonAn);
        gvTimKiem = view.findViewById(R.id.gridviewSearchMonAn);
        tvKhongTimThay = view.findViewById(R.id.tvKhongTimThay);
//        getActivity().setActionBar(toolbarTimKiem);
//        toolbarTimKiem.setTitle("");
        ImbtnSearch = view.findViewById(R.id.ImbtnSearch);
        etkeySearch = view.findViewById(R.id.etkeySearch);
        etkeySearch.requestFocus();
//        setHasOptionsMenu(true);

        ImbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etkeySearch.getText().toString();
                TimKiem(query);
                adapter=new MonAnAdapter(getActivity(),R.layout.item_monan,arrayListSearchMonAn);
                gvTimKiem.setAdapter(adapter);
            }
        });
        return view;
    }



    public void TimKiem(final String key){
        arrayListSearchMonAn.clear();
        if (key.equals("")) {
            Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa", Toast.LENGTH_SHORT).show();
        }
        else {

            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.instance.URL_SEARCH, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    try {
                        JSONArray jsonArray = new JSONArray(response);


                        if (jsonArray.length() == 0)
                            tvKhongTimThay.setVisibility(View.VISIBLE);
                        else {
                            tvKhongTimThay.setVisibility(View.GONE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    MonAn monAn = new MonAn();
                                    monAn.setMaMonAn(object.getInt("mamonan"));
                                    monAn.setTenMonAn(object.getString("tenmonan"));
                                    monAn.setDSNguyenLieu(object.getString("nguyenlieu"));
                                    monAn.setCongThuc(object.getString("congthuc"));
                                    monAn.setHinhAnh(object.getString("hinhanh"));
                                    monAn.setMaNguoiDung(object.getInt("manguoidung"));
                                    monAn.setMaLoaiMonAn(object.getInt("manguoidung"));
                                    arrayListSearchMonAn.add(monAn);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("key", key);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
}
