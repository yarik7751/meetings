package com.elatesoftware.meetings.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.Api;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;

public class UpdateAccountService extends IntentService {

    public static final String TAG = "UpdateAS_log";
    public static final String ACTION = "com.elatesoftware.meetings.ui.service.UpdateAccountService";

    public UpdateAccountService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String city = "qwerty";
        try {
            city = Utils.getCity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HumanAnswer.getInstance().setCity(city);
        String response = Api.updateAccountInfo(CustomSharedPreference.getToken(this), HumanAnswer.getInstance());
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
