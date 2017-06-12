package com.elatesoftware.meetings.ui.activity.man;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.MenuItem;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.PinCodeActivity;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.SettingsFragment;
import com.elatesoftware.meetings.ui.fragment.man.DatesManFragment;
import com.elatesoftware.meetings.ui.fragment.man.ProfileManFragment;
import com.elatesoftware.meetings.ui.fragment.man.WalletManFragment;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;

import butterknife.BindView;

public class WorkManActivity extends BaseActivity {

    public static final int PIN = 134;

    @BindView(R.id.bnv_menu)
    BottomNavigationView bnvMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_man);

        if(savedInstanceState == null) {
            onSwitchFragment(ProfileManFragment.getInstance(), ProfileManFragment.class.getName(), false, true, R.id.container);
        }

        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        onSwitchFragment(ProfileManFragment.getInstance(), ProfileManFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_dales:
                        //showMessage("Oops ;) It is not ready yet!");
                        onSwitchFragment(DatesManFragment.getInstance(), DatesManFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_wallet:
                        //showMessage("Oops ;) It is not ready yet!");
                        onSwitchFragment(WalletManFragment.getInstance(), WalletManFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_settings:
                        onSwitchFragment(SettingsFragment.getInstance(), SettingsFragment.class.getName(), false, true, R.id.container);
                        break;
                }
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //todo 6
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, Const.REQUEST_PERMISSIONS);
        }
    }

    //todo 6
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Const.REQUEST_PERMISSIONS && Utils.isPermissionsGranted(grantResults)) {

        }
    }
}
