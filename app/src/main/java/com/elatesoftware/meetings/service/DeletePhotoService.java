package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;

public class DeletePhotoService extends IntentService {

    public static final String TAG = "DeletePhotoService_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.DeletePhotoService";
    public static final String PHOTO_ID = "PHOTO_ID";

    public DeletePhotoService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MessageAnswer response = Api.deletePhoto(CustomSharedPreference.getToken(this), intent.getLongExtra(PHOTO_ID, -1));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, long photoId) {
        Intent intent = new Intent(context, DeletePhotoService.class);
        intent.putExtra(PHOTO_ID, photoId);
        return intent;
    }
}
