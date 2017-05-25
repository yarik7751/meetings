package com.elatesoftware.meetings.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.util.CustomSharedPreference;

import butterknife.BindView;
import butterknife.OnClick;

public class PinCodeActivity extends BaseActivity {

    public static final String TAG = "PinCodeActivity_logs";

    @BindView(R.id.ll_indicators) LinearLayout llIndicators;

    private String pin = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        if(TextUtils.isEmpty(CustomSharedPreference.getToken(this))) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.img_backspace)
    public void clickBackspace() {
        if(pin.length() > 0) {
            pin = pin.substring(0, pin.length() - 1);
        }
        setIndicators();
    }

    public void clickNumber(View v) {
        String numberChar = (String) v.getTag();
        if(pin.length() >= 0 && pin.length() < 4) {
            pin += numberChar;
        }
        setIndicators();
    }

    private void setIndicators() {
        for(int i = 0; i < llIndicators.getChildCount(); i++) {
            ImageView img = (ImageView) llIndicators.getChildAt(i);
            if(i <= pin.length() - 1) {
                img.setImageResource(R.drawable.indicator_white);
            } else {
                img.setImageResource(R.drawable.indicator);
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pin.length() == 4) {
                    Log.d(TAG, "pin :" + pin);
                    checkPin();
                }
            }
        }, 100);
    }

    private void checkPin() {
        String savePin = CustomSharedPreference.getPin(this);
        if(TextUtils.isEmpty(savePin)) {
            CustomSharedPreference.setPin(this, pin);
            pin = "";
            setIndicators();
            return;
        } else {
            if(savePin.equals(pin)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                return;
            }
        }
    }
}
