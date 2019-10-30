package com.example.appnauan.ui.monancuatoi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appnauan.R;

public class MonAnCuaToiFragment extends Fragment {

    private MonAnCuaToiViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(MonAnCuaToiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monancuatoi, container, false);

        return root;
    }
}