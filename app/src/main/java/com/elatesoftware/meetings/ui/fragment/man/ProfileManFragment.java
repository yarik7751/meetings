package com.elatesoftware.meetings.ui.fragment.man;

import android.app.DatePickerDialog;
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

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.view.InkPageIndicator;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.model.ProfileMan;
import com.elatesoftware.meetings.util.model.ProfileWoman;

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

    private List<View> photos;
    private Calendar birthDate;

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
        birthDate = new GregorianCalendar();
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
        loadInfo();
    }

    /*@OnClick(R.id.btn_birth_date)
    public void clickBtnBirthDate() {
        DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthDate.set(Calendar.YEAR, year);
                birthDate.set(Calendar.MONTH, month);
                birthDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                btnBirthDate.setText(DateUtils.getDateToString(getContext(), birthDate));
            }
        }, 1990, 0, 1);
        dpd.show();
    }

    @OnClick(R.id.btn_done)
    public void clickBtnDone() {
        String name = etName.getText().toString();
        String aboutMe = etAboutMe.getText().toString();
        ProfileMan profileMan = new ProfileMan(name, birthDate, aboutMe);
        CustomSharedPreference.setManInformation(getContext(), profileMan);
    }*/

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3);
    }

    //Todo test
    private void loadPhoto() {
        photos = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            View viewPhoto = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, null);
            ((ImageView) viewPhoto.findViewById(R.id.img_photo)).setImageResource(R.drawable.example_photo);
            /*Picasso.with(getContext()).load(R.drawable.ic_meeting_icon).centerInside()
                    .resize(AndroidUtils.getWindowsSizeParams(getContext())[0], (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3))
                    .into((ImageView) viewPhoto.findViewById(R.id.img_photo));*/
            photos.add(viewPhoto);
        }
        vpPhotos.setAdapter(new PageAdapter(photos));
        inkIndicator.setViewPager(vpPhotos);
    }

    private void loadInfo() {
        ProfileMan profileMan = CustomSharedPreference.getManInformation(getContext());
        if(profileMan != null) {
            //Todo test
            loadPhoto();

        }
    }
}
