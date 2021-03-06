package com.elatesoftware.meetings.ui.activity.man;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.all.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.all.SettingsFragment;
import com.elatesoftware.meetings.ui.fragment.man.DatesManFragment;
import com.elatesoftware.meetings.ui.fragment.man.WalletManFragment;
import com.elatesoftware.meetings.ui.fragment.all.ProfileFragment;

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
            onSwitchFragment(new ProfileFragment(), ProfileFragment.class.getName(), false, true, R.id.container);
        }

        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        onSwitchFragment(new ProfileFragment(), ProfileFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_dales:
                        //showMessage("Oops ;) It is not ready yet!");
                        onSwitchFragment(new DatesManFragment(), DatesManFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_wallet:
                        //showMessage("Oops ;) It is not ready yet!");
                        onSwitchFragment(new WalletManFragment(), WalletManFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_settings:
                        onSwitchFragment(new SettingsFragment(), SettingsFragment.class.getName(), false, true, R.id.container);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
