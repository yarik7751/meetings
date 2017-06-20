package com.elatesoftware.meetings.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.view_pager.page_photo.PageAdapter;
import com.elatesoftware.meetings.ui.view.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DaleDetailsActivity extends BaseActivity {

    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.ink_indicator) InkPageIndicator inkIndicator;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.tv_place) TextView tvPlace;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_amount) TextView tvAmount;
    @BindView(R.id.btn_chat) Button btnChat;

    List<View> photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dale_details);

        loadPhoto();
    }

    @OnClick(R.id.btn_chat)
    public void clickBtnChat() {
        startActivity(new Intent(this, ChatActivity.class));
    }

    //Todo test
    private void loadPhoto() {
        photos = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            View viewPhoto = LayoutInflater.from(this).inflate(R.layout.item_photo, null);
            ((ImageView) viewPhoto.findViewById(R.id.img_photo)).setImageResource(R.drawable.example_photo);
            /*Picasso.with(getContext()).load(R.drawable.ic_meeting_icon).centerInside()
                    .resize(AndroidUtils.getWindowsSizeParams(getContext())[0], (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3))
                    .into((ImageView) viewPhoto.findViewById(R.id.img_photo));*/
            photos.add(viewPhoto);
        }
        vpPhotos.setAdapter(new PageAdapter(photos));
        inkIndicator.setViewPager(vpPhotos);
    }
}
