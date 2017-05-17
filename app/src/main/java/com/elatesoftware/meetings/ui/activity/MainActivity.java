package com.elatesoftware.meetings.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.activity.man.WorkActivityMan;
import com.elatesoftware.meetings.ui.activity.woman.WorkActivityWoman;
import com.elatesoftware.meetings.ui.fragment.GenderFragment;
import com.elatesoftware.meetings.ui.fragment.SignInFragment;
import com.elatesoftware.meetings.ui.fragment.SignUpFragment;
import com.elatesoftware.meetings.util.CustomSharedPreference;

public class MainActivity extends BaseActivity {

    private final int TIME_INTERVAL = 10 * 1000;
    private final int MIN_DISTANCE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(CustomSharedPreference.getId(this) > 0) {
            if(CustomSharedPreference.isMan(this)) {
                startActivity(new Intent(this, WorkActivityMan.class));
            } else {
                startActivity(new Intent(this, WorkActivityWoman.class));
            }
        }

        if(savedInstanceState == null) {
            setGenderFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CustomSharedPreference.getId(this) > 0) {
            finish();
        }
    }

    public void setSignUpFragment() {
        onSwitchFragment(SignUpFragment.getInstance(), SignUpFragment.class.getName(), true, true, R.id.container);
    }

    public void setSignInFragment() {
        onSwitchFragment(SignInFragment.getInstance(), SignInFragment.class.getName(), true, true, R.id.container);
    }

    public void setGenderFragment() {
        onSwitchFragment(GenderFragment.getInstance(), GenderFragment.class.getName(), false, true, R.id.container);
    }
}
