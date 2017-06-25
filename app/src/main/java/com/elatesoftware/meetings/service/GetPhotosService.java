package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;

public class GetPhotosService extends IntentService {

    public static final String TAG = "GetPhotosS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.GetPhotosService";

    public GetPhotosService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GetPhotosAnswer response = Api.getPhotos(CustomSharedPreference.getToken(this));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, GetPhotosService.class);
        return intent;
    }
}
