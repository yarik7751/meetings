package com.elatesoftware.meetings.ui.activity.woman;

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
import com.elatesoftware.meetings.service.GetProfileInfoService;
import com.elatesoftware.meetings.ui.activity.BaseShowDateActivity;
import com.elatesoftware.meetings.ui.adapter.page.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetProfileInfoAnswer;
import com.elatesoftware.meetings.util.model.ButtonAnimation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

public class ShowSearchDateActivity extends BaseShowDateActivity {

    public static final String TAG = "PreviewActivity_logs";
    public static final String CREATOR_ID = "CREATOR_ID";

    private View vConfirmed;
    private ButtonAnimation buttonAnimation;

    private GetProfileInfoReceiver getProfileInfoReceiver;

    long creatorId;

    private View.OnClickListener confirmedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public static Intent getIntent(Context context, long creatorId) {
        Intent intent = new Intent(context, ShowSearchDateActivity.class);
        intent.putExtra(CREATOR_ID, creatorId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonAnimation = new ButtonAnimation(this, (CircularProgressButton) vConfirmed);
        creatorId = getIntent().getLongExtra(CREATOR_ID, -1);

        setUI();
        loadInfo();

        vConfirmed = LayoutInflater.from(this).inflate(R.layout.incl_btn_confirmed, rlBottom, false);
        rlBottom.addView(vConfirmed);
        vConfirmed.setOnClickListener(confirmedListener);
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

    private void setUI() {
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

    private void registerBroadcast() {
        getProfileInfoReceiver = new GetProfileInfoReceiver();
        registerReceiver(getProfileInfoReceiver, Utils.getIntentFilter(GetProfileInfoService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(getProfileInfoReceiver);
    }

    private void requestGetProfileInfo(long userId) {
        setProgressDialogMessage(getString(R.string.loading_info) + " ...");
        showProgressDialog();
        startService(GetProfileInfoService.getIntent(this, userId));
    }

    private void loadPhotoInteger(long userId, List<Integer> photo) {
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
            String response = intent.getStringExtra(Const.RESPONSE);
            hideProgressDialog();
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && GetProfileInfoAnswer.getInstance() != null)  {
                Log.d(TAG, "GetProfileInfo 200");
                if(GetProfileInfoAnswer.getInstance().getSuccess()) {
                    Log.d(TAG, "GetProfileInfo TRUE");
                    String name = GetProfileInfoAnswer.getInstance().getResult().getFirstName();
                    long age = 0;
                    age = GetProfileInfoAnswer.getInstance().getResult().getDateOfBirthByCalendar() == null ? 0 : DateUtils.getAge(GetProfileInfoAnswer.getInstance().getResult().getDateOfBirthByCalendar().getTimeInMillis());
                    tvName.setText(name);
                    tvAge.setText(String.valueOf(age));
                    loadPhotoInteger(GetProfileInfoAnswer.getInstance().getResult().getId(), GetProfileInfoAnswer.getInstance().getResult().getPhotosId());
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
