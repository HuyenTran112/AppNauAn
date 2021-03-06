package com.example.appnauan.ui.dangnhap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.appnauan.NguoiDung;
import com.example.appnauan.R;
import com.example.appnauan.ui.dangky.DangKyFragment;
import com.example.appnauan.ui.dangky.DangKyViewModel;
import com.example.appnauan.ui.dsmonan.DsMonAnFragment;
import com.example.appnauan.ui.quanly.QuanLyFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DangNhapFragment extends Fragment {

    private DangNhapViewModel dangnhapViewModel;
    private DangKyViewModel dangKyViewModel;
    private DangKyFragment dangKyFragment;
    Button btnSignUp, btnLogin, btnLogout;
    public View view;
    EditText edtEmail, edtMatKhau;
    TextView tvTitle, tvUser, tvPass;
    ImageView imgUser;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ArrayList<NguoiDung> arrayListNguoiDung=new ArrayList<>();
    NguoiDung userCurrent;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        dangnhapViewModel =
                ViewModelProviders.of(this).get(DangNhapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dangnhap, container, false);
        view = root;
        AnhXa();

       GetNguoiDung(MainActivity.instance.urlGetUser);
        //bắt sự kiện cho button Signup
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangKyFragment nextFrag = new DangKyFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
        //bắt sự kiện cho button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edtEmail.getText().toString().trim();
                String MatKhau=edtMatKhau.getText().toString().trim();
                Login(MainActivity.instance.URL_LOGIN);
                //Toast.makeText(getActivity(),Email+" "+MatKhau,Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.instance.nguoidung = null;
                                String imageUri = "android.resource://com.example.appnauan/drawable/icon_cook";
                                MainActivity.instance.setInformationUser("",imageUri);
                                DangNhapFragment nextFrag = new DangNhapFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
                                MainActivity.instance.SetEnableMenuItem(false);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DsMonAnFragment nextFrag = new DsMonAnFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
                            }
                        })
                        .show();

            }
        });

        if(KiemTraDangNhap()==false)
        {
            tvTitle.setVisibility(View.VISIBLE);
            tvUser.setVisibility(View.VISIBLE);
            tvPass.setVisibility(View.VISIBLE);
            edtEmail.setVisibility(View.VISIBLE);
            edtMatKhau.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            imgUser.setVisibility(View.GONE);
        }
        else{
            tvTitle.setVisibility(View.GONE);
            tvUser.setVisibility(View.GONE);
            tvPass.setVisibility(View.GONE);
            edtEmail.setVisibility(View.GONE);
            edtMatKhau.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            imgUser.setVisibility(View.VISIBLE);
            Uri imgUri=Uri.parse(MainActivity.instance.nguoidung.getHinhAnh());
            Picasso.with(getActivity()).load(imgUri).into(imgUser);
        }

        return root;

    }

    private void AnhXa() {
        btnSignUp = (Button) view.findViewById(R.id.btn_sign_up);
        btnLogin = (Button) view.findViewById(R.id.buttonSingIn);
        btnLogout = (Button) view.findViewById(R.id.buttonLogout);
        edtEmail = (EditText) view.findViewById(R.id.et_email);
        edtMatKhau = (EditText) view.findViewById(R.id.et_password);
        tvTitle = (TextView) view.findViewById(R.id.tvSignup);
        tvUser = (TextView) view.findViewById(R.id.tv_username);
        tvPass = (TextView) view.findViewById(R.id.tv_password);
        imgUser = (ImageView) view.findViewById(R.id.imgViewHinhUser);
    }

    //Kiem tra login
    private void Login(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //nếu kết quã json trả về là success thì đăng nhập thành công
                if (response.trim().equals("success")) {
                    Toast.makeText(getActivity(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    getUser();
                    //Toast.makeText(getActivity(),userCurrent.getTenHienThi().toString(),Toast.LENGTH_SHORT).show();
                    edtEmail.setText("");
                    edtMatKhau.setText("");
                   ;
                    //khi đăng nhập thành công thì xuất thông báo sau đó chuyển sang fragment danh sách món ăn
                    DsMonAnFragment nextFrag = new DsMonAnFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    //khi người dùng đăng nhập thành công lúc này các menu như
                    //danh sách món ăn của tôi, món ăn yêu thích được set về true
                    MainActivity.instance.SetEnableMenuItem(true);
                }
                //ngược lại xuất thông báo sai thông tin đăng nhập
                else {
                    Toast.makeText(getActivity(), "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Xảy ra lỗi\nVui lòng thử lại", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi!\n" + error.toString());
            }
        }) {
            @Override
            //đây là hàm dùng để truyền giá trị xuống database để kiểm tra thông tin đăng nhập
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email", edtEmail.getText().toString().trim());
                params.put("matkhau", edtMatKhau.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetNguoiDung(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayListNguoiDung.clear();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        arrayListNguoiDung.add(new NguoiDung(
                                object.getInt("manguoidung"),
                                object.getString("email"),
                                object.getString("tenhienthi"),
                                object.getString("matkhau"),
                                object.getInt("maloainguoidung"),
                                object.getString("hinhanh")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //adapter.notifyDataSetChanged();
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
    //Lay thong tin user dang nhap thanh cong
    public void getUser()
    {
        for(NguoiDung a: arrayListNguoiDung)
        {
            if(a.getEmail().equals(edtEmail.getText().toString().trim()) && a.getMatKhau().equals(edtMatKhau.getText().toString()))
            {
                userCurrent=a;
                MainActivity.instance.nguoidung=a;
            }
        }
        //Toast.makeText(getActivity(),userCurrent.getTenHienThi().toString(),Toast.LENGTH_SHORT).show();
        MainActivity.instance.setInformationUser(MainActivity.instance.nguoidung.getTenHienThi(),userCurrent.getHinhAnh());
    }

    public boolean KiemTraDangNhap(){
        if(MainActivity.instance.nguoidung!=null){
            return true;
        }
        return false;
    }
}