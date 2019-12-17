package com.example.appnauan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.ui.dsmonan.DsMonAnFragment;
import com.example.appnauan.ui.monancuatoi.MonAnCuaToiFragment;
import com.example.appnauan.ui.monanyeuthich.MonAnYeuThichFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MonAnAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MonAn>ListMonAn;
    ArrayList<MonAnYeuThich> arrayListMonAnYeuThich=new ArrayList<>();
    ViewHolder holder_;
    String urlAddMonAnYeuThich="http://10.80.255.123:8080/dbappnauan/AddMonAnYeuThich.php";
    String urlDeleteMonAnYeuThich="http://10.80.255.123:8080/dbappnauan/DeleteMonAnYeuThich.php";
    String urlGetMonAnYeuThich="http://10.80.255.123:8080/dbappnauan/GetMonAnYeuThich.php";
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
        ImageView imageViewStar;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       final ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.imgHinh=(ImageView)view.findViewById(R.id.imageviewHinhAnhMonAn);
            holder.tvName=(TextView) view.findViewById(R.id.tvName);
            holder.imageViewStar=(ImageView) view.findViewById(R.id.imageviewStar);
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

        if(MainActivity.instance.KiemTraDangNhap())
        {
            holder.imageViewStar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue= Volley.newRequestQueue(context);
            //arrayListMonAnYeuThich.add(new MonAnYeuThich(7,1));
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlGetMonAnYeuThich, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    arrayListMonAnYeuThich.clear();
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject object=response.getJSONObject(i);
                            arrayListMonAnYeuThich.add(new MonAnYeuThich(
                                    object.getInt("mamonan"),
                                    object.getInt("manguoidung")

                            ));
                           for(MonAnYeuThich monAnYeuThich:arrayListMonAnYeuThich)
                           {
                               if(monan.getMaMonAn()==monAnYeuThich.getMaMonAn() &&MainActivity.instance.nguoidung.getMaNguoiDung()==monAnYeuThich.getMaNguoiDung())
                               {
                                   holder.imageViewStar.setImageResource(R.drawable.icon_star_check);
                               }
                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonArrayRequest);

//            for(MonAnYeuThich monAnYeuThich: KiemTraMonYeuThich())
//            {
//
//                if(monAnYeuThich.getMaMonAn()==monan.getMaMonAn())
//                {
//                    holder.imageViewStar.setImageResource(R.drawable.icon_star_check);
//                }
//            }

        }
        else
        {
            holder.imageViewStar.setVisibility(View.GONE);
        }
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
        //bắt sự kiện thêm món ăn yêu thích
        holder.imageViewStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView star = (ImageView) view.findViewById(R.id.imageviewStar);
                final Bitmap bmap_star = ((BitmapDrawable)star.getDrawable()).getBitmap();
                Drawable myDrawable = view.getResources().getDrawable(R.drawable.icon_star);
                final Bitmap mystar = ((BitmapDrawable) myDrawable).getBitmap();
                if(bmap_star.sameAs(mystar))
                {
                    AddMonAnYeuThich(monan.getMaMonAn());
                    holder.imageViewStar.setImageResource(R.drawable.icon_star_check);

                }
                else
                {
                    DeleteMonAnYeuThich(monan.getMaMonAn());
                    holder.imageViewStar.setImageResource(R.drawable.icon_star);
//                    MonAnYeuThichFragment test = (MonAnYeuThichFragment) MainActivity.instance.getSupportFragmentManager().findFragmentById(R.id.nav_monanyeuthich);
//                    if (test != null && test.isVisible()) {
//                        Toast.makeText(context,"Test 1",Toast.LENGTH_SHORT).show();
//                        holder.imgHinh.setVisibility(View.GONE);
//                        holder.tvName.setVisibility(View.GONE);
//                        holder.imageViewStar.setVisibility(View.GONE);
//                    }
//                    else {
//                        //Whatever
//                        Toast.makeText(context,"Test 2",Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        return view;
    }
    private void XacNhanAddMonAnYeuThich(String ten, final int id)
    {
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn thêm món "+ten+" vào danh sách món ăn yêu thích không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddMonAnYeuThich(id);

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }
    public void AddMonAnYeuThich(final int id)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlAddMonAnYeuThich, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(context,"Thêm thành công món yêu thích",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Lỗi thêm món ăn yêu thích",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Xảy ra lỗi. Vui lòng thử lại",Toast.LENGTH_SHORT).show();
                //check_add=false;
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("mamonan",String.valueOf(id));
                params.put("manguoidung",String.valueOf(MainActivity.instance.nguoidung.getMaNguoiDung()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void XacNhanDeleteMonAnYeuThich(String ten, final int id)
    {
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa món "+ten+" khỏi danh sách món ăn yêu thích không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteMonAnYeuThich(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }
    public void DeleteMonAnYeuThich(final int id)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlDeleteMonAnYeuThich, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(context,"Xóa thành công món yêu thích",Toast.LENGTH_SHORT).show();
                    //GetData(urlgetData);
                }
                else
                    Toast.makeText(context,"Lỗi xóa món ăn yêu thích",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Xảy ra lỗi. Vui lòng thử lại",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("mamonan",String.valueOf(id));
                params.put("manguoidung",String.valueOf(MainActivity.instance.nguoidung.getMaNguoiDung()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public ArrayList<MonAnYeuThich> KiemTraMonYeuThich()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        //arrayListMonAnYeuThich.add(new MonAnYeuThich(7,1));
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlGetMonAnYeuThich, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //arrayListMonAnYeuThich.clear();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        arrayListMonAnYeuThich.add(new MonAnYeuThich(
                                object.getInt("mamonan"),
                                object.getInt("manguoidung")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
        return arrayListMonAnYeuThich;

    }

}
