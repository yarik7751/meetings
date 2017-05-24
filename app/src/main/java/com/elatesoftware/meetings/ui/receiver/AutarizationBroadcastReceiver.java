package com.elatesoftware.meetings.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.man.WorkActivityMan;
import com.elatesoftware.meetings.ui.activity.woman.WorkActivityWoman;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.api.pojo.LoginAnswer;


public class AutarizationBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "AutBR_logs";

    private OnAutarizationResultListener onAutarizationResultListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        String response = intent.getStringExtra(Const.RESPONSE);
        if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && LoginAnswer.getInstance() != null) {
            Log.d(TAG, "registration 200");
            if(LoginAnswer.getInstance().getSuccess()) {
                CustomSharedPreference.setProfileInformation(context, LoginAnswer.getInstance().getResult().getAccount());
                CustomSharedPreference.setToken(context, LoginAnswer.getInstance().getResult().getSessionKey());
                if(CustomSharedPreference.isMan(context)) {
                    context.startActivity(new Intent(context, WorkActivityMan.class));
                } else {
                    context.startActivity(new Intent(context, WorkActivityWoman.class));
                }
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

    public OnAutarizationResultListener getOnAutarizationResultListener() {
        return onAutarizationResultListener;
    }

    public void setOnAutarizationResultListener(OnAutarizationResultListener onAutarizationResultListener) {
        this.onAutarizationResultListener = onAutarizationResultListener;
    }

    public static interface OnAutarizationResultListener {
        void onSuccess();
        void onError();
    }
}
