package com.elatesoftware.meetings.ui.activity.man;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.man.ProfileManFragment;
import com.elatesoftware.meetings.ui.service.UpdateAccountService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileEditManActivity extends BaseActivity {

    public static final String TAG = "ProfileEditMan_logs";

    @BindView(R.id.cet_name) CustomEditText cetName;
    /*@BindView(R.id.cet_height) CustomEditText cetHeight;
    @BindView(R.id.cet_weight) CustomEditText cetWeight;*/
    //@BindView(R.id.cet_age) CustomEditText cetAge;
    @BindView(R.id.btn_birth_date) CircularProgressButton btnBirthDate;
    @BindView(R.id.cet_about) CustomEditText cetAbout;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;

    private Calendar birthDate;
    private HumanAnswer profileMan;

    private UpdateAccountInfoBroadcastReceiver updateAccountInfoBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcast();
        setContentView(R.layout.activity_profile_edit_man);
        setSize();
        loadInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateAccountInfoBroadcastReceiver);
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_done)
    public void clickTvDone() {
        requestUpdateInfo();
    }

    @OnClick(R.id.btn_birth_date)
    public void clickBtnBirthDate() {
        int year = 1990;
        int month = 0;
        int day = 1;
        HumanAnswer profileMan = CustomSharedPreference.getProfileInformation(this);
        if(profileMan != null && profileMan.getDateOfBirthByCalendar() != null) {
            year = profileMan.getDateOfBirthByCalendar().get(Calendar.YEAR);
            month = profileMan.getDateOfBirthByCalendar().get(Calendar.MONTH);
            day = profileMan.getDateOfBirthByCalendar().get(Calendar.DAY_OF_MONTH);
        }
        birthDate = new GregorianCalendar();
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

    private void registerBroadcast() {
        updateAccountInfoBroadcastReceiver = new UpdateAccountInfoBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(UpdateAccountService.ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(updateAccountInfoBroadcastReceiver, intentFilter);
    }

    private void requestUpdateInfo() {
        updateLocalInfo();
        HumanAnswer.setInstance(profileMan);
        startService(new Intent(this, UpdateAccountService.class));
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * 0.3);
    }

    private void updateLocalInfo() {
        String name = cetName.getEditText().getText().toString();
        String aboutMe = cetAbout.getEditText().getText().toString();
        profileMan = new HumanAnswer(name, birthDate == null ? 0 : birthDate.getTimeInMillis(), aboutMe);
    }

    private void loadInfo() {
        HumanAnswer profileMan = CustomSharedPreference.getProfileInformation(this);
        if(profileMan != null) {
            if(profileMan.getDateOfBirthByCalendar() != null) {
                birthDate = profileMan.getDateOfBirthByCalendar();
            }
            cetName.getEditText().setText(profileMan.getFirstName());
            cetAbout.getEditText().setText(profileMan.getAboutMe());
            if(profileMan.getDateOfBirthByCalendar() != null) {
                btnBirthDate.setText(DateUtils.getDateToString(ProfileEditManActivity.this, profileMan.getDateOfBirthByCalendar()));
            }
        } else {

        }
    }

    public class UpdateAccountInfoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                Log.d(TAG, "registration 200");
                if(MessageAnswer.getInstance().getSuccess()) {
                    updateLocalInfo();
                    CustomSharedPreference.setProfileInformation(ProfileEditManActivity.this, profileMan);
                    ProfileManFragment.getInstance().loadInfo();
                    onBackPressed();
                    Log.d(TAG, "registration TRUE");
                } else {
                    showMessage(R.string.wrong_data);
                    Log.d(TAG, "registration FALSE");
                }
            } else {
                showMessage(R.string.request_error);
                Log.d(TAG, "registration error");
            }
        }
    }
}
