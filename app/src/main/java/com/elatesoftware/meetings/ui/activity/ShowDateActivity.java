package com.elatesoftware.meetings.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.pojo.GetProfileInfoAnswer;
import com.elatesoftware.meetings.service.GetProfileInfoService;
import com.elatesoftware.meetings.ui.adapter.view_pager.page_photo.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.ui.view.animation.ButtonAnimation;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

public class ShowDateActivity extends BaseShowDateActivity {

    public static final String TAG = "PreviewActivity_logs";
    public static final String CREATOR_ID = "CREATOR_ID";

    protected View vCancel;
    protected View vFabChat;
    protected ButtonAnimation buttonAnimation;

    protected GetProfileInfoReceiver getProfileInfoReceiver;

    protected long creatorId;

    public static Intent getIntent(Context context, long creatorId) {
        Intent intent = new Intent(context, ShowDateActivity.class);
        intent.putExtra(CREATOR_ID, creatorId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatorId = getIntent().getLongExtra(CREATOR_ID, -1);

        setUI();
        loadInfo();

        vCancel = LayoutInflater.from(this).inflate(R.layout.incl_btn_cancel, rlBottom, false);
        rlBottom.addView(vCancel);
        buttonAnimation = new ButtonAnimation(this, (CircularProgressButton) vCancel);

        vFabChat = LayoutInflater.from(this).inflate(R.layout.incl_fab_chat, rlMain, false);
        rlMain.addView(vFabChat);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcast();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        LatLng latLng = new LatLng(meeting.getLatitude(), meeting.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16.0f)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.addMarker(new MarkerOptions()
                .position(latLng));
    }

    protected void setUI() {
        int visibility = View.GONE;
        int textColor = R.color.button_red_dark;
        int gradient = R.drawable.button_red;
        cvAgeWoman.setVisibility(visibility);
        cvHeightWoman.setVisibility(visibility);
        cvWeightWoman.setVisibility(visibility);
        cvHairColor.setVisibility(visibility);
        tvName.setTextColor(getResources().getColor(R.color.white));
        tvAge.setTextColor(getResources().getColor(R.color.white));
        tvStartTime.setTextColor(getResources().getColor(textColor));
        tvEndTime.setTextColor(getResources().getColor(textColor));
        tvPresent.setTextColor(getResources().getColor(textColor));
        rlPhotos.setBackgroundResource(gradient);
    }

    protected void registerBroadcast() {
        getProfileInfoReceiver = new GetProfileInfoReceiver();
        registerReceiver(getProfileInfoReceiver, Utils.getIntentFilter(GetProfileInfoService.ACTION));
    }

    protected void unregisterBroadcast() {
        unregisterReceiver(getProfileInfoReceiver);
    }

    protected void requestGetProfileInfo(long userId) {
        setProgressDialogMessage(getString(R.string.loading_info) + " ...");
        showProgressDialog();
        startService(GetProfileInfoService.getIntent(this, userId));
    }

    protected void loadPhotoInteger(long userId, List<Integer> photo) {
        PhotoFragmentPageAdapter adapter = new PhotoFragmentPageAdapter(getSupportFragmentManager(), photo, userId);
        vpPhotos.setAdapter(adapter);
        inkIndicator.setViewPager(vpPhotos);
        vpPhotos.setOffscreenPageLimit(adapter.getCount());
    }

    public void loadInfo() {
        requestGetProfileInfo(creatorId);

        tvStartTime.setText(DateUtils.getDateByStr(new Date(meeting.getStartTime()), DateUtils.DATE_FORMAT_OUTPUT));
        tvEndTime.setText(DateUtils.getDateByStr(new Date(meeting.getEndTime()), DateUtils.DATE_FORMAT_OUTPUT));
        tvPresent.setText("$" + String.valueOf(meeting.getAmount()));
        tvPlaceTitle.setText(meeting.getPlace());
    }

    public class GetProfileInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            GetProfileInfoAnswer response = intent.getParcelableExtra(Const.RESPONSE);
            hideProgressDialog();
            if(response != null)  {
                Log.d(TAG, "GetProfileInfo 200");
                if(response.getSuccess()) {
                    Log.d(TAG, "GetProfileInfo TRUE");
                    String name = response.getResult().getFirstName();
                    long age = 0;
                    age = response.getResult().getDateOfBirthByCalendar() == null ? 0 : DateUtils.getAge(response.getResult().getDateOfBirthByCalendar().getTimeInMillis());
                    tvName.setText(name);
                    tvAge.setText(String.valueOf(age));
                    loadPhotoInteger(response.getResult().getId(), response.getResult().getPhotosId());
                    if(TextUtils.isEmpty(tvAge.getText().toString()) || age <= 0) {
                        tvAgeTitle.setVisibility(View.GONE);
                        tvAge.setVisibility(View.GONE);
                    } else {
                        tvAgeTitle.setVisibility(View.VISIBLE);
                        tvAge.setVisibility(View.VISIBLE);
                    }
                } else {
                    showMessage(R.string.something_wrong);
                    Log.d(TAG, "GetProfileInfo FALSE");
                }
            } else {
                showMessage(R.string.request_error);
                Log.d(TAG, "GetProfileInfo error");
            }
        }
    }
}
