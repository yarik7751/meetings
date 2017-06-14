package com.elatesoftware.meetings.ui.activity.woman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.service.SearchDatesService;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.dales.DatesRecyclerViewAdapter;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.SearchDatesAnswer;
import com.elatesoftware.meetings.util.model.params.SearchDatesFilter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindView;
import butterknife.OnClick;

public class SearchManActivity extends BaseActivity implements OnMapReadyCallback {

    public static final String TAG = "SearchManActivity_log";

    @BindView(R.id.ts_switch) ToggleSwitch tsSwitch;
    @BindView(R.id.ll_tool) LinearLayout llTool;
    @BindView(R.id.fl_content) FrameLayout flContent;
    private RecyclerView rvDales;

    private SearchDateReceiver searchDateReceiver;

    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_man);

        loadMap();
        tsSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if(position == 0) {
                    loadMap();
                } else if(position == 1) {
                    loadList();
                }
            }
        });
        requestSearchDates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchDateReceiver = new SearchDateReceiver();
        registerReceiver(searchDateReceiver, Utils.getIntentFilter(SearchDatesService.ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(searchDateReceiver);
    }

    private void requestSearchDates() {
        SearchDatesFilter searchDatesFilter = new SearchDatesFilter(0D, 0L, 1);
        startService(SearchDatesService.getIntent(this, searchDatesFilter));
    }

    private void loadMap() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.fl_content, mapFragment).commit();
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void loadList() {
        map = null;
        flContent.removeAllViews();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        params.rightMargin = AndroidUtils.dp(16);
        params.leftMargin = AndroidUtils.dp(16);

        rvDales = new RecyclerView(this);
        rvDales.setLayoutManager(new LinearLayoutManager(this));
        rvDales.setAdapter(new DatesRecyclerViewAdapter(this, SearchDatesAnswer.getInstance().getResult(), true, R.drawable.ic_person_white_24dp, R.color.button_red_dark));
        rvDales.setPadding(0, llTool.getHeight() + AndroidUtils.dp(16), 0, 0);
        flContent.addView(rvDales, params);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }

    public class SearchDateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && SearchDatesAnswer.getInstance() != null) {
                Log.d(TAG, "registration 200");
                boolean success = SearchDatesAnswer.getInstance().getSuccess();
                if(success) {

                } else {
                    Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "registration FALSE " + response);
                }
            } else {
                Toast.makeText(context, R.string.request_error, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "registration error " + response);
            }
        }
    }
}
