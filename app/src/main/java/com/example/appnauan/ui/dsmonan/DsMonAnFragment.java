package com.example.appnauan.ui.dsmonan;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.appnauan.Database;
import com.example.appnauan.MainActivity;
import com.example.appnauan.MonAn;
import com.example.appnauan.MonAnAdapter;
import com.example.appnauan.R;
import com.example.appnauan.ui.dangky.DangKyFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DsMonAnFragment extends Fragment
        implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener
{

    private DsMonAnViewModel homeViewModel;
    private MonAnAdapter adapter;
    GridView gvMonAn;
    ArrayList<MonAn> arrayListMonAn=new ArrayList<>();

    //String urlGetMonAn="http://172.17.28.47:8080/AppNauAn/Database/dbAppNauAn/getMonAn.php";
    String urlGetMonAn="http://10.80.255.123:8080/dbAppNauAn/getMonAn.php";

    SliderLayout sliderLayout ;
    HashMap<String, Integer> HashMapForLocalRes ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(DsMonAnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dsmonan, container, false);

        gvMonAn=(GridView) root.findViewById(R.id.gridviewHinhAnh);
        adapter=new MonAnAdapter(getActivity(),R.layout.item_monan,arrayListMonAn);
        gvMonAn.setAdapter(adapter);
        //gọi hàm lấy danh sách món ăn
        GetMonAn(urlGetMonAn);
        sliderLayout = (SliderLayout)root.findViewById(R.id.slider);
        AddImageUrlFormLocalRes();

        for (String name : HashMapForLocalRes.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getActivity());

            textSliderView
                    .description(name)
                    .image(HashMapForLocalRes.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(DsMonAnFragment.this);

        //getData
        return root;
    }

//    public void AddImagesUrlOnline(){
//
//        HashMapForURL = new HashMap<String, String>();
//
//        HashMapForURL.put("CupCake", "http://androidblog.esy.es/images/cupcake-1.png");
//    }

    public void AddImageUrlFormLocalRes(){
        HashMapForLocalRes = new HashMap<String, Integer>();

        HashMapForLocalRes.put("Mứt dừa", R.drawable.slide1);
        HashMapForLocalRes.put("Gỏi cuốn", R.drawable.slide2);
        HashMapForLocalRes.put("Sườn xào chua ngọt", R.drawable.slide3);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //lấy danh sách món ăn
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
                        arrayListMonAn.add(new MonAn(
                                object.getInt("mamonan"),
                                object.getString("tenmonan"),
                                object.getString("nguyenlieu"),
                                object.getString("congthuc"),
                                object.getString("hinhanh"),
                                object.getInt("maloaimonan"),
                                object.getInt("manguoidung")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
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
}