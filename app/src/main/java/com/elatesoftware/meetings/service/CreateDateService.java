package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.api.pojo.Meeting;

public class CreateDateService extends IntentService {

    public static final String TAG = "CreateDateS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.CreateDateService";

    public CreateDateService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String response = Api.createDate(CustomSharedPreference.getToken(this), Meeting.getInstance());
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
