package com.suryaheho.projectb.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.suryaheho.projectb.ui.user.Model.ModelListUser;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is User fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}