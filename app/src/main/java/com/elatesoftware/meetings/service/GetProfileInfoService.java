package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.GetProfileInfoAnswer;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;

public class GetProfileInfoService extends IntentService {

    public static final String TAG = "GetProfileInfoS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.GetProfileInfoService";
    public static final String ID = "ID";

    public GetProfileInfoService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GetProfileInfoAnswer response = Api.getProfileInfo(CustomSharedPreference.getToken(this), intent.getLongExtra(ID, -1));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, long photoId) {
        Intent intent = new Intent(context, GetProfileInfoService.class);
        intent.putExtra(ID, photoId);
        return intent;
    }
}
