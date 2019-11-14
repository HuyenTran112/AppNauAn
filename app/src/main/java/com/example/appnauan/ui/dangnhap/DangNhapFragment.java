package com.example.appnauan.ui.dangnhap;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

public class DangNhapFragment extends Fragment {

    private DangNhapViewModel dangnhapViewModel;
    private DangKyViewModel dangKyViewModel;
    private DangKyFragment dangKyFragment;
    Button btnSignUp, btnLogin;
    public View view;
    EditText edtEmail, edtMatKhau;
    private static final String URL_LOGIN = "http://10.80.255.137:8080/dbappnauan/login.php";
    String urlGetUser = "http://10.80.255.137:8080/dbAppNauAn/getUser.php";
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
        GetNguoiDung(urlGetUser);
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
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edtEmail.getText().toString().trim();
                //String MatKhau=edtMatKhau.getText().toString().trim();
                Login(URL_LOGIN);
                //Toast.makeText(getActivity(),Email+" "+MatKhau,Toast.LENGTH_SHORT).show();
            }
        });
        return root;

    }

    private void AnhXa() {
        btnSignUp = (Button) view.findViewById(R.id.btn_sign_up);
        btnLogin = (Button) view.findViewById(R.id.buttonSingIn);
        edtEmail = (EditText) view.findViewById(R.id.et_email);
        edtMatKhau = (EditText) view.findViewById(R.id.et_password);

    }

    //Kiem tra login
    private void Login(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(getActivity(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    getUser();
                    //Toast.makeText(getActivity(),userCurrent.getTenHienThi().toString(),Toast.LENGTH_SHORT).show();
                    edtEmail.setText("");
                    edtMatKhau.setText("");
                } else {
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
            }
        }
        //Toast.makeText(getActivity(),userCurrent.getTenHienThi().toString(),Toast.LENGTH_SHORT).show();
        MainActivity.instance.setInformationUser(userCurrent.getTenHienThi(),userCurrent.getHinhAnh());

    }
}