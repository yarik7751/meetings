package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.LocationUtils;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.api.pojo.HumanAnswer;

public class UpdateAccountService extends IntentService {

    public static final String TAG = "UpdateAS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.UpdateAccountService";
    public static final String PROFILE_INFO = "PROFILE_INFO";

    public UpdateAccountService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String city = null;
        try {
            city = LocationUtils.getCity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HumanAnswer profile = intent.getParcelableExtra(PROFILE_INFO);
        profile.setCity(city);
        MessageAnswer response = Api.updateAccountInfo(CustomSharedPreference.getToken(this), profile);
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Api.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, HumanAnswer humanAnswer) {
        Intent intent = new Intent(context, UpdateAccountService.class);
        intent.putExtra(PROFILE_INFO, humanAnswer);
        return intent;
    }
}
