package com.elatesoftware.meetings.ui.activity.all;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.ui.activity.man.WorkManActivity;
import com.elatesoftware.meetings.ui.activity.woman.WorkWomanActivity;
import com.elatesoftware.meetings.ui.fragment.all.GenderFragment;
import com.elatesoftware.meetings.ui.fragment.all.SignInFragment;
import com.elatesoftware.meetings.ui.fragment.all.SignUpFragment;
import com.elatesoftware.meetings.util.CustomSharedPreference;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_main) FrameLayout flMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(CustomSharedPreference.isToken(this)) {
            FragmentManager fm = getSupportFragmentManager();
            for(int i = 0; i < fm.getBackStackEntryCount(); i++) {
                fm.popBackStack();
            }
            if(CustomSharedPreference.getIsMan(this) == Api.MAN_VALUE) {
                startActivity(new Intent(this, WorkManActivity.class));
            } else if(CustomSharedPreference.getIsMan(this) == Api.WOMAN_VALUE) {
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
        if(CustomSharedPreference.isToken(this)) {
            finish();
        }
    }

    @Override
    protected void setTheme() {
        super.setTheme();
        setTheme(R.style.ThemeDefault);
    }

    public void setSignUpFragment() {
        changeBackground();
        getSupportFragmentManager().popBackStack();
        onSwitchFragment(new SignUpFragment(), SignUpFragment.class.getName(), true, true, R.id.container);
    }

    public void setSignInFragment() {
        changeBackground();
        getSupportFragmentManager().popBackStack();
        onSwitchFragment(new SignInFragment(), SignInFragment.class.getName(), true, true, R.id.container);
    }

    public void setGenderFragment() {
        changeBackground();
        onSwitchFragment(new GenderFragment(), GenderFragment.class.getName(), false, true, R.id.container);
    }

    private void changeBackground() {
        int isMan = CustomSharedPreference.getIsMan(this);
        if(isMan == Api.WOMAN_VALUE) {
            flMain.setBackgroundResource(R.drawable.bg_woman);
        } else {
            flMain.setBackgroundResource(R.drawable.bg);
        }
    }
}
