package com.elatesoftware.meetings.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.activity.man.WorkManActivity;
import com.elatesoftware.meetings.ui.activity.woman.WorkWomanActivity;
import com.elatesoftware.meetings.ui.fragment.GenderFragment;
import com.elatesoftware.meetings.ui.fragment.SignInFragment;
import com.elatesoftware.meetings.ui.fragment.SignUpFragment;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;

public class MainActivity extends BaseActivity {

    private final int TIME_INTERVAL = 10 * 1000;
    private final int MIN_DISTANCE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Utils.isToken(this)) {
            FragmentManager fm = getSupportFragmentManager();
            for(int i = 0; i < fm.getBackStackEntryCount(); i++) {
                fm.popBackStack();
            }
            if(CustomSharedPreference.isMan(this)) {
                startActivity(new Intent(this, WorkManActivity.class));
            } else {
                startActivity(new Intent(this, WorkWomanActivity.class));
            }
            finish();
        }

        if(savedInstanceState == null) {
            setGenderFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.isToken(this)) {
            finish();
        }
    }

    public void setSignUpFragment() {
        getSupportFragmentManager().popBackStack();
        onSwitchFragment(new SignUpFragment(), SignUpFragment.class.getName(), true, true, R.id.container);
    }

    public void setSignInFragment() {
        getSupportFragmentManager().popBackStack();
        onSwitchFragment(new SignInFragment(), SignInFragment.class.getName(), true, true, R.id.container);
    }

    public void setGenderFragment() {
        onSwitchFragment(new GenderFragment(), GenderFragment.class.getName(), false, true, R.id.container);
    }
}
