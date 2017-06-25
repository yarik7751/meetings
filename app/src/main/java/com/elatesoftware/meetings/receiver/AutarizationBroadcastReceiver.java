package com.elatesoftware.meetings.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.ui.activity.PinCodeActivity;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.api.pojo.LoginAnswer;


public class AutarizationBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "AutBR_logs";

    public static final String SIGN = "SIGN";
    public static final int SIGN_IN = 701;
    public static final int SIGN_UP = 702;

    private int sign = -1;

    public AutarizationBroadcastReceiver(int sign) {
        this.sign = sign;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(sign == SIGN_UP) {
            CustomSharedPreference.setProfileInformation(context, null);
            CustomSharedPreference.setPin(context, null);
        }
        LoginAnswer response = intent.getParcelableExtra(Api.RESPONSE);
        if(response != null) {
            Log.d(TAG, "registration 200");
            boolean success = response.getSuccess();
            if(success) {
                if(CustomSharedPreference.getIsMan(context) != response.getResult().getAccount().getGender()) {
                    Toast.makeText(context, R.string.wrong_gender, Toast.LENGTH_SHORT).show();
                    return;
                }
                CustomSharedPreference.setProfileInformation(context, response.getResult().getAccount());
                String token = response.getResult().getSessionKey();
                CustomSharedPreference.setToken(context, token);
                context.startActivity(new Intent(context, PinCodeActivity.class));
                Log.d(TAG, "registration TRUE");
            } else {
                Toast.makeText(context, R.string.wrong_data, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "registration FALSE " + response);
            }
        } else {
            Toast.makeText(context, R.string.request_error, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "registration error " + response);
        }
    }
}
