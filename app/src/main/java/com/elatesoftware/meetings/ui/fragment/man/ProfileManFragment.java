package com.elatesoftware.meetings.ui.fragment.man;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.view.InkPageIndicator;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.model.ProfileMan;
import com.elatesoftware.meetings.util.model.ProfileWoman;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ProfileManFragment extends BaseFragment {

    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_height) TextView tvHeight;
    @BindView(R.id.tv_weight) TextView tvWeight;
    @BindView(R.id.tv_about) TextView tvAbout;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.img_edit) ImageView imgEdit;
    @BindView(R.id.rl_edit) RelativeLayout rlEdit;

    private List<View> photos;

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
    }

    @OnClick(R.id.rl_edit)
    public void clickImgEdit() {
        startActivity(new Intent(getContext(), ProfileEditManActivity.class));
    }

    @OnClick(R.id.ll_add_date)
    public void clickLlAddDate() {
        startActivity(new Intent(getContext(), AddDateActivity.class));
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
        ProfileMan profileMan = CustomSharedPreference.getManInformation(getContext());
        if(profileMan != null) {
            tvName.setText(profileMan.getName());
            tvAbout.setText(profileMan.getAbout());
            tvAge.setText(String.valueOf(DateUtils.getAge(profileMan.getBirthDate().getTimeInMillis())));
        }
    }
}
