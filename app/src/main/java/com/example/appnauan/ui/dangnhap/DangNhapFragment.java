package com.example.appnauan.ui.dangnhap;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.R;
import com.example.appnauan.ui.dangky.DangKyFragment;
import com.example.appnauan.ui.dangky.DangKyViewModel;

public class DangNhapFragment extends Fragment {

    private DangNhapViewModel dangnhapViewModel;
    private DangKyViewModel dangKyViewModel;
    private DangKyFragment dangKyFragment;
    Button btnSignUp;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        dangnhapViewModel =
                ViewModelProviders.of(this).get(DangNhapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dangnhap, container, false);
        btnSignUp=(Button) root.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangKyFragment nextFrag= new DangKyFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
                return root;
    }


}