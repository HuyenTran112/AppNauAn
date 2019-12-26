package com.example.appnauan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appnauan.ui.dsmonan.DsMonAnFragment;
import com.example.appnauan.ui.quanly.QuanLyFragment;

import java.util.List;

public class LoaiMAadapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<LoaiMonAn>loaiMonAnList;

    public LoaiMAadapter(Context context, int layout, List<LoaiMonAn> loaiMonAnList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonAnList = loaiMonAnList;
    }

    @Override
    public int getCount() {
        return loaiMonAnList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgHinhAnhLoaiMonAn;
        TextView txtTenLoaiMonAn;
    }

    @Override
    //Trả về mỗi dòng trên item
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_loai_mon_an,null);
            holder=new ViewHolder();
            holder.txtTenLoaiMonAn=(TextView)view.findViewById(R.id.textviewTenLoaiMonAn);
            holder.imgHinhAnhLoaiMonAn=(ImageView) view.findViewById(R.id.test);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)view.getTag();
        }
        final LoaiMonAn loaiMonAn=loaiMonAnList.get(i);
        holder.txtTenLoaiMonAn.setText(loaiMonAn.getTenLoaiMonAn());
        holder.txtTenLoaiMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Bạn vừa chọn loại món ăn: "+loaiMonAn.getMaLoaiMonAn(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,LoaiMonAnActivity.class);
                intent.putExtra("dataLoaiMonAn", loaiMonAn);
                context.startActivity(intent);
            }
        });
        holder.imgHinhAnhLoaiMonAn.setImageResource(R.drawable.monbanh);
        return view;
    }
}
