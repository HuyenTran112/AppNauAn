package com.example.appnauan.ui.dangky;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.R;
import com.example.appnauan.ui.dangnhap.DangNhapFragment;

public class DangKyFragment extends Fragment {

    private DangKyViewModel dangkyViewModel;
    Button btnSignUp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dangkyViewModel =
                ViewModelProviders.of(this).get(DangKyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dangky, container, false);
        btnSignUp=(Button) root.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhapFragment nextFrag= new DangNhapFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
                return root;

    }
}