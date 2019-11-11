package com.example.appnauan;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHeaderMain extends Fragment {
    TextView txtUser;
    public FragmentHeaderMain(){}

    public View onCreateView(LayoutInflater inf, ViewGroup pa, Bundle b) {
        View contentView = inf.inflate(R.layout.nav_header_main, pa, false);
        Bundle args = new Bundle();
        txtUser=(TextView)contentView.findViewById(R.id.textViewUser);
        args.putString("tenhienthi",txtUser.getText().toString());
        return contentView;
    }

}
