package com.example.appnauan.ui.dsmonan;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.Database;
import com.example.appnauan.MonAn;
import com.example.appnauan.MonAnAdapter;
import com.example.appnauan.R;
import com.example.appnauan.ui.dangky.DangKyFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DsMonAnFragment extends Fragment {

    private DsMonAnViewModel homeViewModel;
    private MonAnAdapter adapter;
    private ArrayList<MonAn> listMonAn;
    GridView gvMonAn;
    public Database database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(DsMonAnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dsmonan, container, false);

        gvMonAn=(GridView) root.findViewById(R.id.gridviewHinhAnh);
        listMonAn=new ArrayList<>();

        //MonAn monan=new MonAn("1","Phở","Thịt trứng","N",1);
        //listMonAn.add(monan);
        database=new Database(getActivity(),"QuanLyMonAn.sql",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS MonAn(MaMonAn INTEGER PRIMARY KEY AUTOINCREMENT,TenMonAn VARCHAR(200), DsNguyenLieu VARCHAR(200), CongThuc VARCHAR(200), HinhAnh BLOB)");
        //getData
        Cursor cursor=database.GetData("SELECT * FROM MonAn");
        while(cursor.moveToNext())
        {
            listMonAn.add(new MonAn(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
                    )
            );
        }
//        adapter.notifyDataSetChanged();
        adapter=new MonAnAdapter(getActivity(),R.layout.item_monan,listMonAn);
        gvMonAn.setAdapter(adapter);
        return root;
    }

}