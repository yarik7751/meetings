package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.api.Api;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginService extends IntentService {

    public static final String TAG = "LoginService_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.LoginService";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD ";

    public LoginService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String userName = intent.getStringExtra(USER_NAME);
        String password = intent.getStringExtra(PASSWORD);
        String response = Api.login(userName, password, FirebaseInstanceId.getInstance().getToken());
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
