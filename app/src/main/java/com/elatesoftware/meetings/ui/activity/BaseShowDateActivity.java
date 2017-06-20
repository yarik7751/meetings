package com.elatesoftware.meetings.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class BaseShowDateActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.tv_title) protected TextView tvTitle;
    @BindView(R.id.vp_photos) protected ViewPager vpPhotos;
    @BindView(R.id.rl_photos) protected RelativeLayout rlPhotos;
    @BindView(R.id.ink_indicator) protected CircleIndicator inkIndicator;
    @BindView(R.id.tv_age_woman) protected TextView tvAgeWoman;
    @BindView(R.id.tv_name) protected TextView tvName;
    @BindView(R.id.tv_age_title) protected TextView tvAgeTitle;
    @BindView(R.id.tv_age) protected TextView tvAge;
    @BindView(R.id.tv_height_woman) protected TextView tvHeightWoman;
    @BindView(R.id.tv_weight_woman) protected TextView tvWeightWoman;
    @BindView(R.id.tv_hair_color) protected TextView tvHairColor;
    @BindView(R.id.cv_age_woman) protected CardView cvAgeWoman;
    @BindView(R.id.cv_height_woman) protected CardView cvHeightWoman;
    @BindView(R.id.cv_weight_woman) protected CardView cvWeightWoman;
    @BindView(R.id.cv_hair_color) protected CardView cvHairColor;
    @BindView(R.id.tv_start_time) protected TextView tvStartTime;
    @BindView(R.id.tv_end_time) protected TextView tvEndTime;
    @BindView(R.id.tv_present) protected TextView tvPresent;
    @BindView(R.id.tv_place_title) protected TextView tvPlaceTitle;
    @BindView(R.id.ll_main_info) protected LinearLayout llManInfo;
    @BindView(R.id.map) protected FrameLayout flMap;
    @BindView(R.id.pb_progress) protected AVLoadingIndicatorView pbProgress;
    @BindView(R.id.rl_bottom) protected RelativeLayout rlBottom;
    @BindView(R.id.ll_place) protected LinearLayout llPlace;

    protected SupportMapFragment mapFragment;
    protected GoogleMap map;

    protected Meeting meeting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date);
        meeting = Meeting.getInstance();

        initMap();
        setSize();

        llPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    protected void initMap() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.commit();
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @OnClick(R.id.rl_back)
    public void back() {
        onBackPressed();
    }

    protected void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * Const.PHOTOS_HEIGHT_PERCENT);
    }
}
