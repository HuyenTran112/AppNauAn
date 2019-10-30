package com.example.appnauan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MonAnAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MonAn>ListMonAn;

    public MonAnAdapter(Context context, int layout, List<MonAn> listMonAn) {
        this.context = context;
        this.layout = layout;
        ListMonAn = listMonAn;
    }

    @Override
    public int getCount() {
        return ListMonAn.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imgHinh;
        TextView tvName;
        ImageView imageViewPromotion;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.imgHinh=(ImageView)view.findViewById(R.id.imageviewHinhAnh);
            holder.tvName=(TextView) view.findViewById(R.id.tvName);
            holder.imageViewPromotion=(ImageView) view.findViewById(R.id.imageviewPromotion);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) view.getTag();
        }
        MonAn monan=ListMonAn.get(i);
        //holder.imgHinh.setImageResource(monan.getHinhAnh());
        holder.tvName.setText(monan.getTenMonAn());
       // holder.tvName.setSelected(true);

        return view;
    }
}
