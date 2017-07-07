package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.model.params.AuthorizationParams;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.api.Api;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterService extends IntentService {

    public static final String TAG = "RegisterService_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.RegisterService";
    public static final String AUTHORIZATION_DATA = "AUTHORIZATION_DATA";


    public RegisterService() {
        super(TAG);
        Log.d(TAG, TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AuthorizationParams params = intent.getParcelableExtra(AUTHORIZATION_DATA);
        LoginAnswer response = Api.register(params);
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Api.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, AuthorizationParams params) {
        Intent intent = new Intent(context, RegisterService.class);
        intent.putExtra(AUTHORIZATION_DATA, params);
        return intent;
    }
}
