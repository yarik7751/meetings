package com.elatesoftware.meetings.ui.activity.man;

import android.app.Activity;
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
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.service.CreateDateService;
import com.elatesoftware.meetings.service.GetPhotosService;
import com.elatesoftware.meetings.ui.activity.BaseShowDateActivity;
import com.elatesoftware.meetings.ui.adapter.view_pager.page_photo.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.api.pojo.Photo;
import com.elatesoftware.meetings.ui.view.animation.ButtonAnimation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PreviewActivity extends BaseShowDateActivity {

    public static final String TAG = "PreviewActivity_logs";

    private View vPublish;
    private ButtonAnimation buttonAnimation;

    private CreateDateBroadcastReceiver createDateBroadcastReceiver;
    private GetPhotosBroadcastReceiver getPhotosBroadcastReceiver;

    private View.OnClickListener publishListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requestCreateDate();
        }
    };

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, PreviewActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonAnimation = new ButtonAnimation(this, (CircularProgressButton) vPublish);

        setUI();
        loadInfo();

        vPublish = LayoutInflater.from(this).inflate(R.layout.incl_btn_publish, rlBottom, false);
        rlBottom.addView(vPublish);
        vPublish.setOnClickListener(publishListener);
        buttonAnimation = new ButtonAnimation(this, (CircularProgressButton) vPublish);
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

    private void requestCreateDate() {
        buttonAnimation.start();
        startService(new Intent(this, CreateDateService.class));
    }

    private void requestGetPhotos() {
        startService(GetPhotosService.getIntent(this));
    }

    public void loadInfo() {
        tvTitle.setText(R.string.preview);
        requestGetPhotos();
        setLocalProfileData();

        String[] colors = getResources().getStringArray(R.array.hair_colors);
        Integer hairColor = meeting.getHairColor();
        if(hairColor != null) {
            tvHairColor.setText(colors[hairColor]);
        }
        tvAgeWoman.setText(meeting.getPrefAgeStart() + "—" + meeting.getPrefAgeEnd());
        tvHeightWoman.setText(meeting.getPrefHeightStart() + "—" + meeting.getPrefHeightEnd());
        tvWeightWoman.setText(meeting.getPrefWeightStart() + "—" + meeting.getPrefWeightEnd());
        tvStartTime.setText(DateUtils.getDateByStr(new Date(meeting.getStartTime()), DateUtils.DATE_FORMAT_OUTPUT));
        tvEndTime.setText(DateUtils.getDateByStr(new Date(meeting.getEndTime()), DateUtils.DATE_FORMAT_OUTPUT));
        //TODO вынести в ресурсы
        tvPresent.setText("$" + String.valueOf(meeting.getAmount()));
        tvPlaceTitle.setText(meeting.getPlace());
    }

    private void setLocalProfileData() {
        HumanAnswer profileMan = CustomSharedPreference.getProfileInformation(this);
        long age = 0;
        if(profileMan != null) {
            tvName.setText(profileMan.getFirstName());
            age = profileMan.getDateOfBirthByCalendar() == null ? 0 : DateUtils.getAge(profileMan.getDateOfBirthByCalendar().getTimeInMillis());
            tvAge.setText(String.valueOf(age));
        }
        if(TextUtils.isEmpty(tvAge.getText().toString()) || age <= 0) {
            tvAgeTitle.setVisibility(View.GONE);
            tvAge.setVisibility(View.GONE);
        } else {
            tvAgeTitle.setVisibility(View.VISIBLE);
            tvAge.setVisibility(View.VISIBLE);
        }
    }

    private void registerBroadcast() {
        getPhotosBroadcastReceiver = new GetPhotosBroadcastReceiver();
        registerReceiver(getPhotosBroadcastReceiver, Utils.getIntentFilter(GetPhotosService.ACTION));
        createDateBroadcastReceiver = new CreateDateBroadcastReceiver();
        registerReceiver(createDateBroadcastReceiver, Utils.getIntentFilter(CreateDateService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(getPhotosBroadcastReceiver);
        unregisterReceiver(createDateBroadcastReceiver);
    }

    private void setUI() {
        int textColor = R.color.seek_bar;
        int gradient = R.drawable.button_blue;
        tvName.setTextColor(getResources().getColor(textColor));
        tvAge.setTextColor(getResources().getColor(textColor));
        tvStartTime.setTextColor(getResources().getColor(textColor));
        tvEndTime.setTextColor(getResources().getColor(textColor));
        tvPresent.setTextColor(getResources().getColor(textColor));
        rlPhotos.setBackgroundResource(gradient);
    }

    private void loadPhoto(List<Photo> photos) {
        List<Integer> photoInteger = new ArrayList<>();
        for(Photo photo : photos) {
            photoInteger.add(photo.getId());
        }
        PhotoFragmentPageAdapter adapter = new PhotoFragmentPageAdapter(getSupportFragmentManager(), photoInteger, CustomSharedPreference.getProfileInformation(this).getId());
        vpPhotos.setAdapter(adapter);
        inkIndicator.setViewPager(vpPhotos);
        vpPhotos.setOffscreenPageLimit(adapter.getCount());
    }

    private void closeActivitySuccess() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(AddDateActivity.IS_CLOSE, true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public class CreateDateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            MessageAnswer response = intent.getParcelableExtra(Api.RESPONSE);
            buttonAnimation.stop();
            if(response != null) {
                Log.d(TAG, "CreateDate 200");
                if(response.getSuccess()) {
                    Log.d(TAG, "CreateDate TRUE");
                    showMessage(R.string.date_created_successfully);
                    closeActivitySuccess();
                } else {
                    showMessage(R.string.something_wrong);
                    Log.d(TAG, "CreateDate FALSE");
                }
            } else {
                showMessage(R.string.request_error);
                Log.d(TAG, "CreateDate error");
            }
        }
    }

    public class GetPhotosBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            GetPhotosAnswer response = intent.getParcelableExtra(Api.RESPONSE);
            pbProgress.setVisibility(View.GONE);
            if(response != null) {
                if(response.getSuccess()) {
                    loadPhoto(response.getResult());
                }
            }
        }
    }
}

