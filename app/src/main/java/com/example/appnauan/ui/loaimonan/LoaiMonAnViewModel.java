package com.example.appnauan.ui.loaimonan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoaiMonAnViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LoaiMonAnViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}