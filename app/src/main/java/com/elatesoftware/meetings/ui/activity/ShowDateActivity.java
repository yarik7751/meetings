package com.elatesoftware.meetings.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.activity.man.ProfileEditManActivity;
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.ui.fragment.man.ProfileManFragment;
import com.elatesoftware.meetings.ui.service.CreateDateService;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.util.api.pojo.Meeting;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.stmt.query.In;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ShowDateActivity extends BaseActivity implements OnMapReadyCallback {

    public static final String TAG = "ShowDateActivity_logs";
    public static final String TITLE = "TITLE";
    public static final String IS_SHOW_MAN_INFO = "IS_SHOW_MAN_INFO";

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
    @BindView(R.id.tv_start_time) TextView tvStartTime;
    @BindView(R.id.tv_end_time) TextView tvEndTime;
    @BindView(R.id.tv_present) TextView tvPresent;
    @BindView(R.id.tv_place_title) TextView tvPlaceTitle;
    @BindView(R.id.ll_main_info) LinearLayout llManInfo;
    @BindView(R.id.map) FrameLayout flMap;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private List<View> photos;
    private Meeting meeting;

    private CreateDateBroadcastReceiver createDateBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date);

        meeting = Meeting.getInstance();

        initMap();
        setSize();
        setTitle();
        loadPhoto();
        loadInfo();

        if(!getIntent().getBooleanExtra(IS_SHOW_MAN_INFO, true)) {
            llManInfo.setVisibility(View.GONE);
        }

        createDateBroadcastReceiver = new CreateDateBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(CreateDateService.ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(createDateBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(createDateBroadcastReceiver);
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_publish)
    public void clickBtnPublish() {
        requestCreateDate();
    }

    private void requestCreateDate() {
        startService(new Intent(this, CreateDateService.class));
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * 0.3);
    }

    //Todo test
    private void loadPhoto() {
        photos = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            View viewPhoto = LayoutInflater.from(this).inflate(R.layout.item_photo, null);
            //((ImageView) viewPhoto.findViewById(R.id.img_photo)).setImageResource(R.drawable.example_photo);
            Picasso.with(this).load(R.drawable.example_photo).centerCrop()
                    .resize(AndroidUtils.getWindowsSizeParams(this)[0], (int) (AndroidUtils.getWindowsSizeParams(this)[1] * 0.3))
                    .into((ImageView) viewPhoto.findViewById(R.id.img_photo), new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
            photos.add(viewPhoto);
        }
        vpPhotos.setAdapter(new PageAdapter(photos));
        inkIndicator.setViewPager(vpPhotos);
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

        tvAge.setText(meeting.getPrefAgeStart() + "—" + meeting.getPrefAgeEnd());
        tvHeightWoman.setText(meeting.getPrefHeightStart() + "—" + meeting.getPrefHeightEnd());
        tvWeightWoman.setText(meeting.getPrefWeightStart() + "—" + meeting.getPrefWeightEnd());
        tvStartTime.setText(DateUtils.getDateByStr(new Date(meeting.getStartTime()), DateUtils.DATE_FORMAT_OUTPUT));
        tvEndTime.setText(DateUtils.getDateByStr(new Date(meeting.getEndTime()), DateUtils.DATE_FORMAT_OUTPUT));
        tvPresent.setText(String.valueOf(meeting.getAmount()));
        tvPlaceTitle.setText(meeting.getPlace());
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

    public class CreateDateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
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
    }
}
