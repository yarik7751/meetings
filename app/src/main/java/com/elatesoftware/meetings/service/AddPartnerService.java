package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.api.pojo.MessageAnswer;

public class AddPartnerService extends IntentService {

    public static final String TAG = "AddPartnerService_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.AddPartnerService";
    public static final String DATE_ID = "DATE_ID";

    public AddPartnerService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MessageAnswer response = Api.addPartner(CustomSharedPreference.getToken(this), intent.getLongExtra(DATE_ID, -1));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Api.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, long dateId) {
        Intent intent = new Intent(context, AddPartnerService.class);
        intent.putExtra(DATE_ID, dateId);
        return intent;
    }
}
