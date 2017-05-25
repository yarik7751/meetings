package com.elatesoftware.meetings.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.api.Api;

public class GetPhotoService extends IntentService {

    public static final String TAG = "GetPhotoS_log";
    public static final String ACTION = "com.elatesoftware.meetings.ui.service.GetPhotoService";
    public static final String PHOTO_ID = "PHOTO_ID";

    public GetPhotoService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String response = Api.getPhoto(CustomSharedPreference.getToken(this), intent.getLongExtra(PHOTO_ID, -1));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, long photoId) {
        Intent intent = new Intent(context, GetPhotoService.class);
        intent.putExtra(PHOTO_ID, photoId);
        return intent;
    }
}
