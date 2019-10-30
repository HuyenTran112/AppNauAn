package com.example.appnauan.ui.dsmonan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DsMonAnViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DsMonAnViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}