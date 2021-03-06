package com.elatesoftware.meetings.ui.activity.woman;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.all.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.all.SettingsFragment;
import com.elatesoftware.meetings.ui.fragment.woman.DatesWomanFragment;
import com.elatesoftware.meetings.ui.fragment.all.ProfileFragment;
import com.elatesoftware.meetings.ui.fragment.woman.WalletWomanFragment;
import com.elatesoftware.meetings.util.AndroidUtils;

import butterknife.BindView;

public class WorkWomanActivity extends BaseActivity {

    @BindView(R.id.bnv_menu) BottomNavigationView bnvMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_woman);

        if(savedInstanceState == null) {
            onSwitchFragment(ProfileFragment.getInstance(), ProfileFragment.class.getName(), false, true, R.id.container);
        }

        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AndroidUtils.hideKeyboard(WorkWomanActivity.this);
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        onSwitchFragment(new ProfileFragment(), ProfileFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_dales:
                        onSwitchFragment(DatesWomanFragment.getInstance(), DatesWomanFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_wallet:
                        onSwitchFragment(WalletWomanFragment.getInstance(), WalletWomanFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_settings:
                        onSwitchFragment(SettingsFragment.getInstance(), SettingsFragment.class.getName(), false, true, R.id.container);
                        break;
                }
                return true;
            }
        });
    }
}
