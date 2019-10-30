package com.example.appnauan.ui.monanyeuthich;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonAnYeuThichViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MonAnYeuThichViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}