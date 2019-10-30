package com.example.appnauan.ui.monancuatoi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonAnCuaToiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MonAnCuaToiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}