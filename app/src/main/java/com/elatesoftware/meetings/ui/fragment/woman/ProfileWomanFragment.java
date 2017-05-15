package com.elatesoftware.meetings.ui.fragment.woman;

import android.Manifest;
import android.app.DatePickerDialog;
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

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.adapter.page.PageAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.view.InkPageIndicator;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.model.ProfileWoman;
import com.squareup.picasso.Picasso;

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

    public static final String TAG = "ProfileWF_logs";

    private int REQUEST_PERMISSIONS = 1;

    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_city) EditText etCity;
    @BindView(R.id.et_height) EditText etHeight;
    @BindView(R.id.et_hair_color) EditText etHairColor;
    @BindView(R.id.btn_birth_date) Button btnBirthDate;

    private List<View> photos;
    private Calendar birthDate;

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
        birthDate = new GregorianCalendar();
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

        setSize();
        requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS && Utils.isPermissionsGranted(grantResults)) {
            loadInfo();
        }
    }

    @OnClick(R.id.btn_birth_date)
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
        String hairColor = etHairColor.getText().toString();
        String city = etCity.getText().toString();
        String heightStr = etHeight.getText().toString();
        int height = TextUtils.isEmpty(heightStr) ? -1 : Integer.parseInt(heightStr);
        ProfileWoman profileWoman = new ProfileWoman(name, height, hairColor, city, birthDate);
        CustomSharedPreference.setWomanInformation(getContext(), profileWoman);
    }

    private void setSize() {
        vpPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3);
    }

    //Todo test
    private void loadPhoto() {
        photos = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            View viewPhoto = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, null);
            ((ImageView) viewPhoto.findViewById(R.id.img_photo)).setImageResource(R.drawable.example_woman_photo);
            /*Picasso.with(getContext()).load(R.drawable.ic_meeting_icon).centerInside()
                    .resize(AndroidUtils.getWindowsSizeParams(getContext())[0], (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * 0.3))
                    .into((ImageView) viewPhoto.findViewById(R.id.img_photo));*/
            photos.add(viewPhoto);
        }
        vpPhotos.setAdapter(new PageAdapter(photos));
        inkIndicator.setViewPager(vpPhotos);
    }

    private void loadInfo() {
        //Todo test
        loadPhoto();
        ProfileWoman profileWoman = CustomSharedPreference.getWomanInformation(getContext());
        if(profileWoman != null) {
            etName.setText(profileWoman.getName());
            etHairColor.setText(profileWoman.getHairColor());
            etCity.setText(profileWoman.getCity());
            etHeight.setText(profileWoman.getHeight() <= 0 ? "" : profileWoman.getHeight() + "");
            btnBirthDate.setText(DateUtils.getDateToString(getContext(), profileWoman.getBirthDate()));
            birthDate = profileWoman.getBirthDate();
        } else {
            try {
                etCity.setText(Utils.getCity(getContext()));
            } catch (IOException e) {
                Log.d(TAG, "IOException " + e);
                e.printStackTrace();
            }
        }
    }
}
