package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.GetInfoAccAnswer;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;

public class GetAccountInfoService extends IntentService {

    public static final String TAG = "GetAInfoS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.GetAccountInfoService";

    public GetAccountInfoService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GetInfoAccAnswer response = Api.getAccountInfo(CustomSharedPreference.getToken(this));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Api.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
