package com.elatesoftware.meetings.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.service.GetProfileInfoService;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.activity.man.AddDateActivity;
import com.elatesoftware.meetings.ui.adapter.page.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.service.CreateDateService;
import com.elatesoftware.meetings.service.GetPhotosService;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetProfileInfoAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.Meeting;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.api.pojo.Photo;
import com.elatesoftware.meetings.util.model.ButtonAnimation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ShowDateActivity extends BaseActivity implements OnMapReadyCallback {

    public static final String TAG = "ShowDateActivity_logs";
    public static final String TITLE = "TITLE";
    public static final String CREATOR_ID = "CREATOR_ID";
    public static final String IS_SHOW_MAN_INFO = "IS_SHOW_MAN_INFO";
    public static final String TYPE = "TYPE";
    public static final int PREVIEW = 1;
    public static final int SHOW_MAN = 2;
    public static final int SHOW_WOMAN = 3;
    public static final int SEARCH = 4;

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.tv_age_woman) TextView tvAgeWoman;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_age_title) TextView tvAgeTitle;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.tv_height_woman) TextView tvHeightWoman;
    @BindView(R.id.tv_weight_woman) TextView tvWeightWoman;
    @BindView(R.id.tv_hair_color) TextView tvHairColor;
    @BindView(R.id.cv_age_woman) CardView cvAgeWoman;
    @BindView(R.id.cv_height_woman) CardView cvHeightWoman;
    @BindView(R.id.cv_weight_woman) CardView cvWeightWoman;
    @BindView(R.id.cv_hair_color) CardView cvHairColor;
    @BindView(R.id.tv_start_time) TextView tvStartTime;
    @BindView(R.id.tv_end_time) TextView tvEndTime;
    @BindView(R.id.tv_present) TextView tvPresent;
    @BindView(R.id.tv_place_title) TextView tvPlaceTitle;
    @BindView(R.id.ll_main_info) LinearLayout llManInfo;
    @BindView(R.id.map) FrameLayout flMap;
    @BindView(R.id.btn_publish) CircularProgressButton btnPublish;
    @BindView(R.id.btn_confirmed) CircularProgressButton btnConfirm;
    @BindView(R.id.pb_progress) AVLoadingIndicatorView pbProgress;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Meeting meeting;
    private int type;
    private int creatorId = -1;

    private CreateDateBroadcastReceiver createDateBroadcastReceiver;
    private GetPhotosBroadcastReceiver getPhotosBroadcastReceiver;
    private GetProfileInfoReceiver getProfileInfoReceiver;

    private ButtonAnimation buttonAnimation;

    public static Intent getIntent(Context context, String title,  int type) {
        Intent intent = new Intent(context, ShowDateActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(TYPE, type);
        return intent;
    }

    public static Intent getIntent(Context context, String title,  int type, int creatorId) {
        Intent intent = new Intent(context, ShowDateActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(TYPE, type);
        intent.putExtra(CREATOR_ID, creatorId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date);
        type = getIntent().getIntExtra(TYPE, -1);
        creatorId = getIntent().getIntExtra(CREATOR_ID, -1);

        meeting = Meeting.getInstance();
        buttonAnimation = new ButtonAnimation(this, btnPublish);

        setUI();
        initMap();
        registerBroadcast();
        setSize();
        setTitle();
        loadInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_publish)
    public void clickBtnPublish() {
        requestCreateDate();
    }

    private void setUI() {
        int visibility;
        int textColor;
        int gradient;
        if(CustomSharedPreference.getIsMan(this) == Const.WOMAN_VALUE) {
            visibility = View.GONE;
            textColor = R.color.button_red_dark;
            gradient = R.drawable.button_red;
        } else {
            visibility = View.VISIBLE;
            textColor = R.color.seek_bar;
            gradient = R.drawable.button_blue;
        }
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

        switch (type) {
            case PREVIEW:
                btnPublish.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.GONE);
                break;

            case SHOW_MAN:

                break;

            case SHOW_WOMAN:

                break;

            case SEARCH:
                btnPublish.setVisibility(View.GONE);
                btnConfirm.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void requestGetProfileInfo(long userId) {
        setProgressDialogMessage(getString(R.string.loading_info) + " ...");
        showProgressDialog();
        startService(GetProfileInfoService.getIntent(this, userId));
    }

    private void requestCreateDate() {
        buttonAnimation.start();
        startService(new Intent(this, CreateDateService.class));
    }

    private void requestGetPhotos() {
        startService(GetPhotosService.getIntent(this));
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * Const.PHOTOS_HEIGHT_PERCENT);
    }

    private void registerBroadcast() {
        getPhotosBroadcastReceiver = new GetPhotosBroadcastReceiver();
        registerReceiver(getPhotosBroadcastReceiver, Utils.getIntentFilter(GetPhotosService.ACTION));
        createDateBroadcastReceiver = new CreateDateBroadcastReceiver();
        registerReceiver(createDateBroadcastReceiver, Utils.getIntentFilter(CreateDateService.ACTION));
        getProfileInfoReceiver = new GetProfileInfoReceiver();
        registerReceiver(getProfileInfoReceiver, Utils.getIntentFilter(GetProfileInfoService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(getPhotosBroadcastReceiver);
        unregisterReceiver(createDateBroadcastReceiver);
        unregisterReceiver(getProfileInfoReceiver);
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

    private void loadPhotoInteger(long userId, List<Integer> photo) {
        PhotoFragmentPageAdapter adapter = new PhotoFragmentPageAdapter(getSupportFragmentManager(), photo, userId);
        vpPhotos.setAdapter(adapter);
        inkIndicator.setViewPager(vpPhotos);
        vpPhotos.setOffscreenPageLimit(adapter.getCount());
    }

    private void setTitle() {
        String title = getIntent().getStringExtra(TITLE);
        tvTitle.setText(title);
    }

    private void initMap() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.commit();
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    public void loadInfo() {

        switch (type) {
            case PREVIEW:
                requestGetPhotos();
                setLocalProfileData();
                break;

            case SHOW_MAN:
                setLocalProfileData();
                break;

            case SHOW_WOMAN:

                break;

            case SEARCH:
                requestGetProfileInfo(creatorId);
                break;
        }

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng = new LatLng(meeting.getLatitude(), meeting.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16.0f)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.addMarker(new MarkerOptions()
                .position(latLng));
    }

    private void closeActivitySuccess() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(AddDateActivity.IS_CLOSE, true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
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

    public class CreateDateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            //todo удалить хендлер
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String response = intent.getStringExtra(Const.RESPONSE);
                    buttonAnimation.stop();
                    if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                        Log.d(TAG, "CreateDate 200");
                        if(MessageAnswer.getInstance().getSuccess()) {
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
            }, 300);
        }
    }

    public class GetPhotosBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            pbProgress.setVisibility(View.GONE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && GetPhotosAnswer.getInstance() != null) {
                if(GetPhotosAnswer.getInstance().getSuccess()) {
                    loadPhoto(GetPhotosAnswer.getInstance().getResult());
                }
            }
        }
    }
}
