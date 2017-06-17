package com.elatesoftware.meetings.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.man.AddDateActivity;
import com.elatesoftware.meetings.ui.activity.ProfileEditActivity;
import com.elatesoftware.meetings.ui.activity.woman.SearchManActivity;
import com.elatesoftware.meetings.ui.adapter.page.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.service.GetAccountInfoService;
import com.elatesoftware.meetings.service.GetPhotosService;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetInfoAccAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.Photo;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ProfileFragment extends BaseFragment {

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
    @BindView(R.id.pb_progress)
    AVLoadingIndicatorView pbProgress;

    private GetAccountInfoBroadcastReceiver getAccountInfoBroadcastReceiver;
    private GetPhotosBroadcastReceiver getPhotosBroadcastReceiver;

    private static ProfileFragment profileFragment;
    public static ProfileFragment getInstance() {
        if(profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceivers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_woman, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUI();
        setSize();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInfo();
        requestGetPhotos();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    @OnClick(R.id.rl_edit)
    public void clickImgEdit() {
        startActivity(new Intent(getContext(), ProfileEditActivity.class));
    }

    @OnClick(R.id.ll_search)
    public void clickLlAddDate() {
        if(CustomSharedPreference.getIsMan(getContext()) == Const.WOMAN_VALUE) {
            startActivity(new Intent(getContext(), SearchManActivity.class));
        } else {
            startActivity(new Intent(getContext(), AddDateActivity.class));
        }
    }

    private void setUI() {
        if(CustomSharedPreference.getIsMan(getContext()) == Const.WOMAN_VALUE) {
            rlPhotos.setBackgroundResource(R.drawable.button_red);
            llInfo.setBackgroundResource(R.drawable.button_red);
            imgDatesFunc.setImageResource(R.drawable.ic_search_white_24dp);
            tvDatesFunc.setText(R.string.search);
            tvName.setTextColor(getResources().getColor(R.color.white));
            tvAge.setTextColor(getResources().getColor(R.color.white));
            tvHeight.setTextColor(getResources().getColor(R.color.white));
            tvWeight.setTextColor(getResources().getColor(R.color.white));

        } else {
            rlPhotos.setBackgroundResource(R.drawable.button_blue);
            llInfo.setBackgroundResource(R.drawable.button_blue);
            imgDatesFunc.setImageResource(R.drawable.ic_add_white_24dp);
            tvDatesFunc.setText(R.string.add_date);
            tvName.setTextColor(getResources().getColor(R.color.button_blue_light));
            tvAge.setTextColor(getResources().getColor(R.color.button_blue_light));
            tvHeightTitle.setVisibility(View.GONE);
            tvHeight.setVisibility(View.GONE);
            imgPoint1.setVisibility(View.GONE);
            tvWeightTile.setVisibility(View.GONE);
            tvWeight.setVisibility(View.GONE);
            imgPoint2.setVisibility(View.GONE);
        }
    }

    private void registerReceivers() {
        getAccountInfoBroadcastReceiver = new GetAccountInfoBroadcastReceiver();
        getActivity().registerReceiver(getAccountInfoBroadcastReceiver, Utils.getIntentFilter(GetAccountInfoService.ACTION));
        getPhotosBroadcastReceiver = new GetPhotosBroadcastReceiver();
        getActivity().registerReceiver(getPhotosBroadcastReceiver, Utils.getIntentFilter(GetPhotosService.ACTION));
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(getAccountInfoBroadcastReceiver);
        getActivity().unregisterReceiver(getPhotosBroadcastReceiver);
    }

    private void requestGetAccInfo() {
        getActivity().startService(new Intent(getContext(), GetAccountInfoService.class));
    }

    private void requestGetPhotos() {
        getActivity().startService(GetPhotosService.getIntent(getContext()));
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * Const.PHOTOS_HEIGHT_PERCENT);
    }

    private void loadPhoto(List<Photo> photos) {
        List<Integer> photoInteger = new ArrayList<>();
        for(Photo photo : photos) {
            photoInteger.add(photo.getId());
        }
        PhotoFragmentPageAdapter adapter = new PhotoFragmentPageAdapter(getChildFragmentManager(), photoInteger, CustomSharedPreference.getProfileInformation(getContext()).getId());
        vpPhotos.setAdapter(adapter);
        inkIndicator.setViewPager(vpPhotos);
        vpPhotos.setOffscreenPageLimit(adapter.getCount());
    }

    public void loadInfo() {
        HumanAnswer profileWoman = CustomSharedPreference.getProfileInformation(getContext());
        long age = 0;
        if(profileWoman != null) {
            tvName.setText(profileWoman.getFirstName());
            tvAbout.setText(profileWoman.getAboutMe());
            age = profileWoman.getDateOfBirthByCalendar() == null ? 0 : DateUtils.getAge(profileWoman.getDateOfBirthByCalendar().getTimeInMillis());
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

            Double height = profileWoman.getHeight();
            if(height == null || height.intValue() <= 0) {
                tvHeightTitle.setVisibility(View.GONE);
                tvHeight.setVisibility(View.GONE);
                imgPoint1.setVisibility(View.GONE);
            } else {
                tvHeight.setText(String.valueOf(height.intValue()));
            }

            Double weight = profileWoman.getWeight();
            if(weight == null || weight.intValue() <= 0) {
                tvWeightTile.setVisibility(View.GONE);
                tvWeight.setVisibility(View.GONE);
                imgPoint2.setVisibility(View.GONE);
            } else {
                tvWeight.setText(String.valueOf(weight.intValue()));
            }

        }
    }

    public class GetAccountInfoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && GetInfoAccAnswer.getInstance() != null) {
                if(GetInfoAccAnswer.getInstance().getSuccess()) {
                    CustomSharedPreference.setProfileInformation(context, GetInfoAccAnswer.getInstance().getHumanAnswer());
                    loadInfo();
                }
                clickImgEdit();
            }
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
