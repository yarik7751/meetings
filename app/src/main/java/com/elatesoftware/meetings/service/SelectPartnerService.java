package com.elatesoftware.meetings.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.model.params.SelectPartnerParams;

public class SelectPartnerService extends IntentService {

    public static final String TAG = "SelectPartnerS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.SelectPartnerService";
    public static final String PARAMS = "PARAMS";

    public SelectPartnerService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MessageAnswer response = Api.selectPartner(CustomSharedPreference.getToken(this), (SelectPartnerParams) intent.getParcelableExtra(PARAMS));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Api.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, SelectPartnerParams selectPartnerParams) {
        Intent intent = new Intent(context, SelectPartnerService.class);
        intent.putExtra(PARAMS, selectPartnerParams);
        return intent;
    }
}
