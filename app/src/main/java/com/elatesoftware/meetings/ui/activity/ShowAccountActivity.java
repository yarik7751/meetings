package com.elatesoftware.meetings.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.activity.man.ProfileEditManActivity;
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.model.ProfileMan;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ShowAccountActivity extends BaseActivity {

    public static final String TITLE = "TITLE";
    
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

    private List<View> photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_account);

        setSize();
        setTitle();
        loadPhoto();
        loadInfo();
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
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

    public void loadInfo() {
        ProfileMan profileMan = CustomSharedPreference.getManInformation(this);
        long age = 0;
        if(profileMan != null) {
            tvName.setText(profileMan.getName());
            //tvAbout.setText(profileMan.getAbout());
            age = profileMan.getBirthDate() == null ? 0 : DateUtils.getAge(profileMan.getBirthDate().getTimeInMillis());
            tvAge.setText(String.valueOf(age));
        }
        /*if(TextUtils.isEmpty(tvAbout.getText().toString())) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }*/
        if(TextUtils.isEmpty(tvAge.getText().toString()) || age <= 0) {
            tvAgeTitle.setVisibility(View.GONE);
            tvAge.setVisibility(View.GONE);
        } else {
            tvAgeTitle.setVisibility(View.VISIBLE);
            tvAge.setVisibility(View.VISIBLE);
        }
    }
}
