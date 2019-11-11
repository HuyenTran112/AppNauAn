package com.example.appnauan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LoaiMonAnAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    ArrayList<LoaiMonAn> arrayList;

    public LoaiMonAnAdapter(Context context, int myLayout, ArrayList<LoaiMonAn> arrayList) {
        this.context = context;
        this.myLayout = myLayout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View convertView=inflater.inflate(R.layout.item_loaimonan,null);
        TextView txtTenLoaiMonAn=(TextView) convertView.findViewById(R.id.textViewLoaiMonAn);
        txtTenLoaiMonAn.setText(arrayList.get(i).getTenLoaiMonAn());
        return convertView;
    }
}
