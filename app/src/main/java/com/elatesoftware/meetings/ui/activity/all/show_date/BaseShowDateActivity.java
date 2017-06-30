package com.elatesoftware.meetings.ui.activity.all.show_date;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.ui.activity.all.BaseActivity;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.elatesoftware.meetings.util.CustomSharedPreference;
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
    @BindView(R.id.rl_show_date_mail) protected RelativeLayout rlMain;
    @BindView(R.id.ink_indicator) protected CircleIndicator inkIndicator;
    @BindView(R.id.tv_age_woman) protected TextView tvAgeWoman;
    @BindView(R.id.tv_name) protected TextView tvName;
    @BindView(R.id.tv_age_title) protected TextView tvAgeTitle;
    @BindView(R.id.tv_age) protected TextView tvAge;
    @BindView(R.id.tv_height) protected TextView tvHeight;
    @BindView(R.id.tv_height_title) protected TextView tvHeightTitle;
    @BindView(R.id.tv_weight) protected TextView tvWeight;
    @BindView(R.id.tv_weight_title) protected TextView tvWeightTitle;
    @BindView(R.id.img_point1) protected ImageView imgPoint1;
    @BindView(R.id.img_point2) protected ImageView imgPoint2;
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

    protected int visibility;
    protected int visibilityPersonInfo;
    protected int textColor;
    protected int textColorPersonInfo;
    protected int gradient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date);
        meeting = Meeting.getInstance();
        visibility = View.GONE;
        visibilityPersonInfo = View.GONE;
        textColor = R.color.button_red_dark;
        textColorPersonInfo = R.color.white;
        gradient = R.drawable.button_red;

        initMap();
        setSize();
        setUI();

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

    protected void setUI() {
        tvAgeTitle.setText(tvAgeTitle.getText().toString() + ": ");
        tvHeightTitle.setText(tvHeightTitle.getText().toString() + ": ");
        tvWeightTitle.setText(tvWeightTitle.getText().toString() + ": ");

        if(CustomSharedPreference.getIsMan(this) != Api.WOMAN_VALUE) {
            visibilityPersonInfo = View.VISIBLE;
            textColor = R.color.seek_bar;
            gradient = R.drawable.button_blue;
            textColorPersonInfo = R.color.button_blue_light;
        }

        cvAgeWoman.setVisibility(visibility);
        cvHeightWoman.setVisibility(visibility);
        cvWeightWoman.setVisibility(visibility);
        cvHairColor.setVisibility(visibility);

        tvName.setTextColor(getResources().getColor(textColorPersonInfo));
        tvAge.setTextColor(getResources().getColor(textColorPersonInfo));
        tvHeight.setTextColor(getResources().getColor(textColorPersonInfo));
        tvWeight.setTextColor(getResources().getColor(textColorPersonInfo));

        tvStartTime.setTextColor(getResources().getColor(textColor));
        tvEndTime.setTextColor(getResources().getColor(textColor));
        tvPresent.setTextColor(getResources().getColor(textColor));
        rlPhotos.setBackgroundResource(gradient);

        tvHeightTitle.setVisibility(visibilityPersonInfo);
        tvHeight.setVisibility(visibilityPersonInfo);
        imgPoint1.setVisibility(visibilityPersonInfo);

        tvWeightTitle.setVisibility(visibilityPersonInfo);
        tvWeight.setVisibility(visibilityPersonInfo);
        imgPoint2.setVisibility(visibilityPersonInfo);
    }
}
