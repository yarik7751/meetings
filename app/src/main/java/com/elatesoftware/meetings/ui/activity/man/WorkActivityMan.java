package com.elatesoftware.meetings.ui.activity.man;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.man.DalesManFragment;
import com.elatesoftware.meetings.ui.fragment.man.ProfileManFragment;
import com.elatesoftware.meetings.ui.fragment.man.WalletManFragment;
import com.elatesoftware.meetings.util.Utils;

import butterknife.BindView;

public class WorkActivityMan extends BaseActivity {

    private int REQUEST_PERMISSIONS = 1;

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
                        onSwitchFragment(DalesManFragment.getInstance(), DalesManFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_wallet:
                        onSwitchFragment(WalletManFragment.getInstance(), WalletManFragment.class.getName(), false, true, R.id.container);
                        break;
                }
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS && Utils.isPermissionsGranted(grantResults)) {

        }
    }
}
