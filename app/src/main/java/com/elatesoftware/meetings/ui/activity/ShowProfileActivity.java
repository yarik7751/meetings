package com.elatesoftware.meetings.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.service.AddPartnerService;
import com.elatesoftware.meetings.service.GetProfileInfoService;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.activity.woman.ShowSearchDateActivity;
import com.elatesoftware.meetings.ui.adapter.page.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetProfileInfoAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ShowProfileActivity extends BaseActivity {

    private static final String TAG = "ShowProfileActivity_log";
    private static final String PROFILE_INFO = "PROFILE_INFO";

    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_height) TextView tvHeight;
    @BindView(R.id.tv_height_title) TextView tvHeightTitle;
    @BindView(R.id.tv_weight) TextView tvWeight;
    @BindView(R.id.tv_weight_title) TextView tvWeightTile;
    @BindView(R.id.tv_about) TextView tvAbout;
    @BindView(R.id.tv_age_title) TextView tvAgeTitle;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.img_edit) ImageView imgEdit;
    @BindView(R.id.img_point1) ImageView imgPoint1;
    @BindView(R.id.img_point2) ImageView imgPoint2;
    @BindView(R.id.rl_edit) RelativeLayout rlEdit;
    @BindView(R.id.ll_search) LinearLayout llSearch;
    @BindView(R.id.ll_info) LinearLayout llInfo;
    @BindView(R.id.tv_dates_func) TextView tvDatesFunc;
    @BindView(R.id.img_dates_func) ImageView imgDatesFunc;
    @BindView(R.id.line) View line;
    @BindView(R.id.pb_progress) AVLoadingIndicatorView pbProgress;
    @BindView(R.id.rl_back) RelativeLayout rlBack;
    
    private HumanAnswer profile;

    private GetProfileInfoReceiver getProfileInfoReceiver;

    public static Intent getIntent(Context context, HumanAnswer humanAnswer) {
        Intent intent = new Intent(context, ShowProfileActivity.class);
        intent.putExtra(PROFILE_INFO, humanAnswer);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_woman);
        profile = getIntent().getParcelableExtra(PROFILE_INFO);

        llInfo.setVisibility(View.GONE);
        rlEdit.setVisibility(View.GONE);
        setSize();
        requestGetProfileInfo(profile.getId());
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

    @OnClick(R.id.rl_back)
    public void back() {
        onBackPressed();
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * Const.PHOTOS_HEIGHT_PERCENT);
    }

    private void requestGetProfileInfo(long userId) {
        setProgressDialogMessage(getString(R.string.loading_info) + " ...");
        showProgressDialog();
        startService(GetProfileInfoService.getIntent(this, userId));
    }

    public void loadInfo() {
        long age = 0;
        if(profile != null) {
            tvName.setText(profile.getFirstName());
            tvAbout.setText(profile.getAboutMe());
            age = profile.getDateOfBirthByCalendar() == null ? 0 : DateUtils.getAge(profile.getDateOfBirthByCalendar().getTimeInMillis());
            tvAge.setText(String.valueOf(age));
            if(TextUtils.isEmpty(tvAbout.getText().toString())) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(tvAge.getText().toString()) || age <= 0) {
                tvAgeTitle.setVisibility(View.GONE);
                tvAge.setVisibility(View.GONE);
            } else {
                tvAgeTitle.setVisibility(View.VISIBLE);
                tvAge.setVisibility(View.VISIBLE);
            }

            Double height = profile.getHeight();
            if(height == null || height.intValue() <= 0) {
                tvHeightTitle.setVisibility(View.GONE);
                tvHeight.setVisibility(View.GONE);
                imgPoint1.setVisibility(View.GONE);
            } else {
                tvHeight.setText(String.valueOf(height.intValue()));
            }

            Double weight = profile.getWeight();
            if(weight == null || weight.intValue() <= 0) {
                tvWeightTile.setVisibility(View.GONE);
                tvWeight.setVisibility(View.GONE);
                imgPoint2.setVisibility(View.GONE);
            } else {
                tvWeight.setText(String.valueOf(weight.intValue()));
            }

            loadPhotoInteger(profile.getId(), profile.getPhotosId());
        }
    }

    private void loadPhotoInteger(long userId, List<Integer> photo) {
        PhotoFragmentPageAdapter adapter = new PhotoFragmentPageAdapter(getSupportFragmentManager(), photo, userId);
        vpPhotos.setAdapter(adapter);
        inkIndicator.setViewPager(vpPhotos);
        vpPhotos.setOffscreenPageLimit(adapter.getCount());
    }

    private void registerBroadcast() {
        getProfileInfoReceiver = new GetProfileInfoReceiver();
        registerReceiver(getProfileInfoReceiver, Utils.getIntentFilter(GetProfileInfoService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(getProfileInfoReceiver);
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
                    profile = GetProfileInfoAnswer.getInstance().getResult();
                    loadInfo();
                    loadPhotoInteger(profile.getId(), profile.getPhotosId());
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
