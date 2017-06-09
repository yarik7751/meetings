package com.elatesoftware.meetings.ui.fragment.woman;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.AddDateActivity;
import com.elatesoftware.meetings.ui.activity.man.ProfileEditManActivity;
import com.elatesoftware.meetings.ui.activity.woman.ProfileEditWomanActivity;
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.ui.adapter.page.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.service.GetAccountInfoService;
import com.elatesoftware.meetings.ui.service.GetPhotosService;
import com.elatesoftware.meetings.ui.view.InkPageIndicator;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetInfoAccAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.Photo;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ProfileWomanFragment extends BaseFragment {

    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_height) TextView tvHeight;
    @BindView(R.id.tv_weight) TextView tvWeight;
    @BindView(R.id.tv_about) TextView tvAbout;
    @BindView(R.id.tv_age_title) TextView tvAgeTitle;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.img_edit) ImageView imgEdit;
    @BindView(R.id.rl_edit) RelativeLayout rlEdit;
    @BindView(R.id.line) View line;
    @BindView(R.id.pb_progress)
    AVLoadingIndicatorView pbProgress;

    private List<View> photos;

    private GetAccountInfoBroadcastReceiver getAccountInfoBroadcastReceiver;
    private GetPhotosBroadcastReceiver getPhotosBroadcastReceiver;

    private static ProfileWomanFragment profileWomanFragment;
    public static ProfileWomanFragment getInstance() {
        if(profileWomanFragment == null) {
            profileWomanFragment = new ProfileWomanFragment();
        }
        return profileWomanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        rlPhotos.setBackgroundResource(R.drawable.button_red);

        setSize();
        loadInfo();
        requestGetPhotos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    @OnClick(R.id.rl_edit)
    public void clickImgEdit() {
        startActivity(new Intent(getContext(), ProfileEditWomanActivity.class));
    }

    @OnClick(R.id.ll_add_date)
    public void clickLlAddDate() {
        startActivity(new Intent(getContext(), AddDateActivity.class));
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
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3);
    }

    private void loadPhoto(List<Photo> photo) {
        PhotoFragmentPageAdapter adapter = new PhotoFragmentPageAdapter(getFragmentManager(), photo);
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
            tvHeight.setText(String.valueOf(profileWoman.getHeight().intValue()));
            tvWeight.setText(String.valueOf(profileWoman.getWeight().intValue()));
            tvAge.setText(String.valueOf(age));
            
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
