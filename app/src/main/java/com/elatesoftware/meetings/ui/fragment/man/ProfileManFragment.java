package com.elatesoftware.meetings.ui.fragment.man;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.AddDateActivity;
import com.elatesoftware.meetings.ui.activity.man.ProfileEditManActivity;
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.service.GetAccountInfoService;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.api.pojo.GetInfoAccAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ProfileManFragment extends BaseFragment {

    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.tv_name) TextView tvName;
    /*@BindView(R.id.tv_height) TextView tvHeight;
    @BindView(R.id.tv_weight) TextView tvWeight;*/
    @BindView(R.id.tv_about) TextView tvAbout;
    @BindView(R.id.tv_age_title) TextView tvAgeTitle;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.img_edit) ImageView imgEdit;
    @BindView(R.id.rl_edit) RelativeLayout rlEdit;
    @BindView(R.id.line) View line;

    private List<View> photos;

    private GetAccountInfoBroadcastReceiver getAccountInfoBroadcastReceiver;

    private static ProfileManFragment profileManFragment;
    public static ProfileManFragment getInstance() {
        if(profileManFragment == null) {
            profileManFragment = new ProfileManFragment();
        }
        return profileManFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceivers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_man, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSize();
        loadPhoto();
        loadInfo();
        /*if(CustomSharedPreference.isFirst(getContext())) {
            CustomSharedPreference.setIsFirst(getContext(), false);
            requestGetAccInfo();
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    @OnClick(R.id.rl_edit)
    public void clickImgEdit() {
        startActivity(new Intent(getContext(), ProfileEditManActivity.class));
    }

    @OnClick(R.id.ll_add_date)
    public void clickLlAddDate() {
        startActivity(new Intent(getContext(), AddDateActivity.class));
    }



    private void registerReceivers() {
        getAccountInfoBroadcastReceiver = new GetAccountInfoBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(GetAccountInfoService.ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(getAccountInfoBroadcastReceiver, intentFilter);
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(getAccountInfoBroadcastReceiver);
    }

    private void requestGetAccInfo() {
        getActivity().startService(new Intent(getContext(), GetAccountInfoService.class));
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3);
    }

    //Todo test
    private void loadPhoto() {
        photos = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            View viewPhoto = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, null);
            //((ImageView) viewPhoto.findViewById(R.id.img_photo)).setImageResource(R.drawable.example_photo);
            Picasso.with(getContext()).load(R.drawable.example_photo).centerCrop()
                    .resize(AndroidUtils.getWindowsSizeParams(getContext())[0], (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3))
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

    public void loadInfo() {
        HumanAnswer profileMan = CustomSharedPreference.getProfileInformation(getContext());
        long age = 0;
        if(profileMan != null) {
            tvName.setText(profileMan.getFirstName());
            tvAbout.setText(profileMan.getAboutMe());
            age = profileMan.getDateOfBirthByCalendar() == null ? 0 : DateUtils.getAge(profileMan.getDateOfBirthByCalendar().getTimeInMillis());
            tvAge.setText(String.valueOf(age));
        }
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
}
