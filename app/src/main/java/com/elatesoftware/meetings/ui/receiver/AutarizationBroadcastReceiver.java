package com.elatesoftware.meetings.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.PinCodeActivity;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.util.api.pojo.RegisterAnswer;


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
        String response = intent.getStringExtra(Const.RESPONSE);
        boolean checkAnswer = sign == SIGN_IN ? LoginAnswer.getInstance() != null : RegisterAnswer.getInstance() != null;
        if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && checkAnswer) {
            Log.d(TAG, "registration 200");
            boolean success = sign == SIGN_IN ? LoginAnswer.getInstance().getSuccess() : RegisterAnswer.getInstance().getSuccess();
            if(success) {
                if(sign == SIGN_IN) {
                    CustomSharedPreference.setProfileInformation(context, LoginAnswer.getInstance().getResult().getAccount());
                }
                //todo 11 ЖАХ!!!!! Выпраўляй!!!!
                String token = sign == SIGN_IN ? LoginAnswer.getInstance().getResult().getSessionKey() : RegisterAnswer.getInstance().getResult();
                CustomSharedPreference.setToken(context, token);
                context.startActivity(new Intent(context, PinCodeActivity.class));
                /*if(CustomSharedPreference.getIsMan(context)) {
                    context.startActivity(new Intent(context, WorkManActivity.class));
                } else {
                    context.startActivity(new Intent(context, WorkWomanActivity.class));
                }*/
                Log.d(TAG, "registration TRUE");
            } else {
                Toast.makeText(context, R.string.wrong_data, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "registration FALSE");
            }
        } else {
            Toast.makeText(context, R.string.request_error, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "registration error");
        }
    }
}
