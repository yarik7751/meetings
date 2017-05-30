package com.elatesoftware.meetings.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.elatesoftware.meetings.ui.activity.base.BaseActivity;

public class PreViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, PinCodeActivity.class));
        finish();
    }
}
