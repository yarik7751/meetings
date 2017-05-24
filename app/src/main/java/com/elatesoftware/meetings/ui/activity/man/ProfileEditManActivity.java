package com.elatesoftware.meetings.ui.activity.man;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.fragment.man.ProfileManFragment;
import com.elatesoftware.meetings.ui.service.AddPhotoService;
import com.elatesoftware.meetings.ui.service.UpdateAccountService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.ImageHelper;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Single;
import rx.Subscriber;

public class ProfileEditManActivity extends BaseActivity {

    public static final String TAG = "ProfileEditMan_logs";
    private static final int RESULT_LOAD_IMG = 302;

    @BindView(R.id.cet_name) CustomEditText cetName;
    /*@BindView(R.id.cet_height) CustomEditText cetHeight;
    @BindView(R.id.cet_weight) CustomEditText cetWeight;*/
    //@BindView(R.id.cet_age) CustomEditText cetAge;
    @BindView(R.id.btn_birth_date) CircularProgressButton btnBirthDate;
    @BindView(R.id.cet_about) CustomEditText cetAbout;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;

    private Calendar birthDate;
    private HumanAnswer profileMan;
    private boolean isPermissionsAddPhoto = false;

    private UpdateAccountInfoBroadcastReceiver updateAccountInfoBroadcastReceiver;
    private AddPhotoBroadcastReceiver addPhotoBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, Const.REQUEST_PERMISSIONS);
        }
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
        unregisterBroadcast();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmapPhoto = BitmapFactory.decodeFile(imgDecodableString);
            AddPhotoService.bitmap = bitmapPhoto;
            requestAddPhoto(bitmapPhoto);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Const.REQUEST_PERMISSIONS && Utils.isPermissionsGranted(grantResults)) {
            isPermissionsAddPhoto = true;
        }
    }

    @OnClick(R.id.fab_add_photo)
    public void clickAddPhoto() {
        if(isPermissionsAddPhoto) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        } else {
            showMessage(R.string.permission_not_found);
        }
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
        registerReceiver(updateAccountInfoBroadcastReceiver, Utils.getIntentFilter(UpdateAccountService.ACTION));
        addPhotoBroadcastReceiver = new AddPhotoBroadcastReceiver();
        registerReceiver(addPhotoBroadcastReceiver, Utils.getIntentFilter(AddPhotoService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(updateAccountInfoBroadcastReceiver);
        unregisterReceiver(addPhotoBroadcastReceiver);
    }

    private void requestUpdateInfo() {
        updateLocalInfo();
        HumanAnswer.setInstance(profileMan);
        startService(new Intent(this, UpdateAccountService.class));
    }

    private void requestAddPhoto(Bitmap bitmap) {
        setProgressDialogMessage(getString(R.string.loading_photo) + " ...");
        showProgressDialog();
        Log.d(TAG, "requestAddPhoto");
        startService(new Intent(this, AddPhotoService.class));
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * 0.3);
    }

    private void updateLocalInfo() {
        String name = cetName.getEditText().getText().toString();
        String aboutMe = cetAbout.getEditText().getText().toString();
        profileMan = new HumanAnswer(name, birthDate == null ? 0 : birthDate.getTimeInMillis() / 1000, aboutMe);
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
                Log.d(TAG, "UpdateAccount 200");
                if(MessageAnswer.getInstance().getSuccess()) {
                    updateLocalInfo();
                    CustomSharedPreference.setProfileInformation(ProfileEditManActivity.this, profileMan);
                    ProfileManFragment.getInstance().loadInfo();
                    onBackPressed();
                    Log.d(TAG, "UpdateAccount TRUE");
                } else {
                    showMessage(R.string.wrong_data);
                    Log.d(TAG, "UpdateAccount FALSE");
                }
            } else {
                showMessage(R.string.request_error);
                Log.d(TAG, "UpdateAccount error");
            }
        }
    }

    public class AddPhotoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            hideProgressDialog();
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                Log.d(TAG, "AddPhoto 200");
                if(MessageAnswer.getInstance().getSuccess()) {
                    Log.d(TAG, "AddPhoto TRUE");
                } else {
                    showMessage(R.string.wrong_data);
                    Log.d(TAG, "AddPhoto FALSE");
                }
            } else {
                showMessage(R.string.request_error);
                Log.d(TAG, "AddPhoto error");
            }
        }
    }
}
