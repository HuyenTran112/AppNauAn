package com.example.appnauan.ui.monanyeuthich;

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

public class MonAnYeuThichFragment extends Fragment {

    private MonAnYeuThichViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(MonAnYeuThichViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monanyeuthich, container, false);
        return root;
    }
}