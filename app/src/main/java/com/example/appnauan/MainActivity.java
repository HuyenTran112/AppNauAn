package com.example.appnauan;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnauan.ui.dangnhap.DangNhapFragment;
import com.example.appnauan.ui.monancuatoi.MonAnCuaToiFragment;
import com.example.appnauan.ui.timkiem.TimKiemFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private AppBarConfiguration mAppBarConfiguration;
    String User;
    String noidung="";
    public NguoiDung nguoidung=null;
    public String urlGetMonAn="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonAn.php";
    public String URL_SEARCH = "http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/search_monan.php";
    public String URL_LOGIN = "http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/login.php";
    public String urlGetUser = "http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getUser.php";
    public String urlAddMonAnYeuThich="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/AddMonAnYeuThich.php";
    public String urlDeleteMonAnYeuThich="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/DeleteMonAnYeuThich.php";
    public String urlGetMonAnYeuThich="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/GetMonAnYeuThich.php";
    public String URL_INSERT_USER = "http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/insertUser.php";
    public String urlGetDsMonAnYeuThich="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/LayDSMonAnYeuThich.php";
    public String URL_UPDATE = "http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/update_monan.php";
    public String URL_INSERT = "http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/insert_monan.php";
    public String urlGetLoaiMonAn="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getLoaiMonAn.php";
    public String urlGetMonXao="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonXao.php";
    public String urlGetMonCanh="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonCanh.php";
    public String urlGetMonKho="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonKho.php";
    public String urlGetMonLau="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonLau.php";
    public String urlGetMonNuong="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonNuong.php";
    public String urlGetMonBanh="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonBanh.php";
    public String urlGetMonKhac="http://172.17.25.153:8080/AppNauAn/Database/dbappnauan/getMonBanh.php";
    //thay bằng địa chỉ ip máy bà để chạy nha
//public String urlGetMonAn="http://10.80.255.123:8080/dbAppNauAn/getMonAn.php";
//    public String URL_SEARCH = "http://10.80.255.123:8080/dbappnauan/search_monan.php";
//    public String URL_LOGIN = "http://10.80.255.123:8080/dbappnauan/login.php";
//    public String urlGetUser = "http://10.80.255.123:8080/dbAppNauAn/getUser.php";
//    public String urlAddMonAnYeuThich="http://10.80.255.123:8080/dbappnauan/AddMonAnYeuThich.php";
//    public String urlDeleteMonAnYeuThich="http://10.80.255.123:8080/dbappnauan/DeleteMonAnYeuThich.php";
//    public String urlGetMonAnYeuThich="http://10.80.255.123:8080/dbappnauan/GetMonAnYeuThich.php";
//    public String URL_INSERT_USER = "http://10.80.255.123:8080/dbappnauan/insertUser.php";
//    public String urlGetDsMonAnYeuThich="http://10.80.255.123:8080/dbAppNauAn/LayDSMonAnYeuThich.php";
//    public String URL_UPDATE = "http://10.80.255.123:8080/dbappnauan/update_monan.php";
//    public String URL_INSERT = "http://10.80.255.123:8080/dbappnauan/insert_monan.php";
//    public String urlGetLoaiMonAn="http://10.80.255.123:8080/dbappnauan/getLoaiMonAn.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_dsmonan, R.id.nav_monanyeuthich, R.id.nav_monancuatoi,
                    R.id.nav_dangnhap, R.id.nav_quanly, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
        if(KiemTraDangNhap()==false)
        {
                SetEnableMenuItem(false);
        }
        else{
            SetEnableMenuItem(true);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Lay ten user
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewAdmin);
       // navUsername.setText("Your Text Here");
        User=navUsername.getText().toString();
        //Toast.makeText(MainActivity.this,User, Toast.LENGTH_SHORT).show();

        //QuanLyFragment quanLyFragment= (QuanLyFragment) new QuanLyFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_search) {
            TimKiemFragment timKiemFragment = new TimKiemFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_host_fragment, timKiemFragment, "TimKiemMonAn").commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setInformationUser(String tenhienthi, String hinhanh)
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewAdmin);
        ImageView navImageUser=(ImageView) headerView.findViewById(R.id.imageViewUser);
        ImageView imgDangXuat = (ImageView) findViewById(R.id.imgViewHinhUser);
        navUsername.setText(tenhienthi);
        Uri imgUri=Uri.parse(hinhanh);
        Picasso.with(this).load(imgUri).into(navImageUser);
        Picasso.with(this).load(imgUri).into(imgDangXuat);
    }
    public boolean KiemTraDangNhap(){
        if(MainActivity.instance.nguoidung!=null){
            return true;
        }
        return false;
    }
    public void SetEnableMenuItem(boolean enable)
    {
        NavigationView navigationView= findViewById(R.id.nav_view);
        Menu menuNav=navigationView.getMenu();
        MenuItem nav_itemquanly = menuNav.findItem(R.id.nav_quanly);
        nav_itemquanly.setEnabled(enable);
        MenuItem nav_itemmonancuatoi = menuNav.findItem(R.id.nav_monancuatoi);
        nav_itemmonancuatoi.setEnabled(enable);
        MenuItem nav_itemmonanyeuthich = menuNav.findItem(R.id.nav_monanyeuthich);
        nav_itemmonanyeuthich.setEnabled(enable);

    }

}
