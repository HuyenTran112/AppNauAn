package com.example.appnauan.ui.monanyeuthich;

import android.database.Cursor;
import android.os.Bundle;
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

import com.example.appnauan.MainActivity;
import com.example.appnauan.MonAn;
import com.example.appnauan.MonAnAdapter;
import com.example.appnauan.R;

import java.util.ArrayList;

public class MonAnYeuThichFragment extends Fragment {

    private MonAnYeuThichViewModel galleryViewModel;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter adapter;
    GridView gvMonAnYeuThich;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(MonAnYeuThichViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monanyeuthich, container, false);
        gvMonAnYeuThich=(GridView) root.findViewById(R.id.gridviewMonAnYeuThich);
        setGridView();
        return root;
    }
    public void setGridView()
    {
        Cursor cursor= MainActivity.database.GetData("SELECT * FROM MonAn");
        listMonAn=new ArrayList<>();
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
        //adapter.notifyDataSetChanged();
        adapter=new MonAnAdapter(getActivity(),R.layout.item_monan,listMonAn);
        gvMonAnYeuThich.setAdapter(adapter);

    }

}