package com.example.liu.seekjob.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.liu.seekjob.beans.UserBean;
import com.example.liu.seekjob.utils.Error;
import com.google.gson.Gson;

public class UserDataManagerService extends Service {
    private IBinder mIBinder;
    private int mErrorCode;

    public class DataManagerBinder extends Binder {
        public UserDataManagerService getService() {
            return UserDataManagerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onCreate() {
        mIBinder = new DataManagerBinder();
        super.onCreate();
    }

    public int checkUserAccount(String userName, String password) {
        mErrorCode = Error.OK;

        if (!"test".equals(userName))
            return Error.ERROR_ACCOUNT_NOT_EXIST;

        if (!"test".equals(password))
            return Error.ERROR_PASSWORD_INCORRECT;

        return mErrorCode;
    }

    public UserBean getUserInfo(String userId, String password) {
        String userInfo = "{\"user_name\":\"test\",\"password\":\"test\",\"nick_name\":\"guest\","
                + "\"mail_address\":\"test@gmail.com\", \"phone_number\":\"09012345678\", \"user_level\":0}";
        return new Gson().fromJson(userInfo, UserBean.class);
    }

    public int register(UserBean bean) {
        String userInfo = new Gson().toJson(bean);
        return Error.OK;
    }
}
