package com.elatesoftware.meetings.ui.activity.man;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.DatePicker;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.man.ProfileManFragment;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.model.ProfileMan;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileEditManActivity extends BaseActivity {

    @BindView(R.id.cet_name) CustomEditText cetName;
    @BindView(R.id.cet_height) CustomEditText cetHeight;
    @BindView(R.id.cet_weight) CustomEditText cetWeight;
    //@BindView(R.id.cet_age) CustomEditText cetAge;
    @BindView(R.id.btn_birth_date) CircularProgressButton btnBirthDate;
    @BindView(R.id.cet_about) CustomEditText cetAbout;

    private Calendar birthDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_man);
        loadInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_done)
    public void clickTvDone() {
        String name = cetName.getEditText().getText().toString();
        String aboutMe = cetAbout.getEditText().getText().toString();
        ProfileMan profileMan = new ProfileMan(name, birthDate, aboutMe);
        CustomSharedPreference.setManInformation(this, profileMan);
        ProfileManFragment.getInstance().loadInfo();
        onBackPressed();
    }

    @OnClick(R.id.btn_birth_date)
    public void clickBtnBirthDate() {
        ProfileMan profileMan = CustomSharedPreference.getManInformation(this);
        int year = profileMan == null ? 1990 : profileMan.getBirthDate().get(Calendar.YEAR);
        int month = profileMan == null ? 0 : profileMan.getBirthDate().get(Calendar.MONTH);
        int day = profileMan == null ? 1 : profileMan.getBirthDate().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthDate.set(Calendar.YEAR, year);
                birthDate.set(Calendar.MONTH, month);
                birthDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                btnBirthDate.setText(DateUtils.getDateToString(ProfileEditManActivity.this, birthDate));
            }
        }, year, month, day);
        dpd.show();
    }

    private void loadInfo() {
        ProfileMan profileMan = CustomSharedPreference.getManInformation(this);
        if(profileMan != null) {
            birthDate = profileMan.getBirthDate();
            cetName.getEditText().setText(profileMan.getName());
            cetAbout.getEditText().setText(profileMan.getAbout());
            btnBirthDate.setText(DateUtils.getDateToString(ProfileEditManActivity.this, profileMan.getBirthDate()));
        } else {
            birthDate = new GregorianCalendar();
        }
    }
}
