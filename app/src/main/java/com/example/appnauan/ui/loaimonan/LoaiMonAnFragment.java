package com.example.appnauan.ui.loaimonan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.LoaiMAadapter;
import com.example.appnauan.LoaiMonAn;
import com.example.appnauan.LoaiMonAnAdapter;
import com.example.appnauan.MainActivity;
import com.example.appnauan.MonAn;
import com.example.appnauan.MonAnAdapter;
import com.example.appnauan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoaiMonAnFragment extends Fragment {

    private LoaiMonAnViewModel sendViewModel;
    ListView  lvLoaiMonAn;
    LoaiMAadapter adapter;
    ArrayList<LoaiMonAn> arrayListLoaiMonAn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(LoaiMonAnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_loai_mon_an, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        lvLoaiMonAn=root.findViewById(R.id.listViewLoaiMonAn);
        AnhXa();
        adapter=new LoaiMAadapter(getActivity(),R.layout.dong_loai_mon_an,arrayListLoaiMonAn);
        lvLoaiMonAn.setAdapter(adapter);
        return root;
    }
    private void AnhXa() {
        arrayListLoaiMonAn = new ArrayList<>();
        arrayListLoaiMonAn.add(new LoaiMonAn(1, "Món xào", R.drawable.monxao));
        arrayListLoaiMonAn.add(new LoaiMonAn(2,"Món canh",R.drawable.moncanh));
        arrayListLoaiMonAn.add(new LoaiMonAn(3,"Món kho",R.drawable.monkho));
        arrayListLoaiMonAn.add(new LoaiMonAn(4,"Món lẩu",R.drawable.monlau));
        arrayListLoaiMonAn.add(new LoaiMonAn(5,"Món nướng",R.drawable.monnuong));
        arrayListLoaiMonAn.add(new LoaiMonAn(6,"Món bánh",R.drawable.monbanh));
        arrayListLoaiMonAn.add(new LoaiMonAn(7,"Món khác",R.drawable.monkhac));
    }

}