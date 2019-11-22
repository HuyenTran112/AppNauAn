package com.example.appnauan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
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
            holder.imgHinh=(ImageView)view.findViewById(R.id.imageviewHinhAnhMonAn);
            holder.tvName=(TextView) view.findViewById(R.id.tvName);
            holder.imageViewPromotion=(ImageView) view.findViewById(R.id.imageviewPromotion);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) view.getTag();
        }
        final MonAn monan=ListMonAn.get(i);
//        byte[] decodedString = Base64.decode(monan.getHinhAnh(), Base64.DEFAULT);
//        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //Toast.makeText(context,monan.getHinhAnh(),Toast.LENGTH_SHORT).show();
        Uri imgUri=Uri.parse(monan.getHinhAnh());
        Picasso.with(context).load(imgUri).into(holder.imgHinh);
        holder.tvName.setText(monan.getTenMonAn());
        holder.tvName.setSelected(true);
        //bắt sự kiện xóa và sửa
        holder.imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context,sinhVien.getHoTen(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,ChiTietMonAn.class);
                intent.putExtra("dataMonAn", monan);
                context.startActivity(intent);

            }
        });
        return view;
    }
}
