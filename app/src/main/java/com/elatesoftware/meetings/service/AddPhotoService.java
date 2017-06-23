package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.ImageHelper;
import com.elatesoftware.meetings.api.Api;

public class AddPhotoService extends IntentService {

    public static final String TAG = "AddPhotoS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.AddPhotoService";
    public static final String PATH = "PATH";

    public static Bitmap bitmap = null;

    public AddPhotoService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        MessageAnswer response = Api.uploadFile(CustomSharedPreference.getToken(this), intent.getStringExtra(PATH));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, String path) {
        Intent intent = new Intent(context, AddPhotoService.class);
        intent.putExtra(PATH, path);
        return intent;
    }
}
