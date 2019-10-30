package com.example.appnauan.ui.dangnhap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DangNhapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DangNhapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}