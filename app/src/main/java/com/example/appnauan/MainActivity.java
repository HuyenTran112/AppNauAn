package com.example.appnauan;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

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
        navUsername.setText(tenhienthi);
        Uri imgUri=Uri.parse(hinhanh);
        Picasso.with(this).load(imgUri).into(navImageUser);
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
