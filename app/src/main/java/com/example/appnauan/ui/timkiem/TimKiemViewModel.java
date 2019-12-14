package com.example.appnauan.ui.timkiem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimKiemViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TimKiemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
