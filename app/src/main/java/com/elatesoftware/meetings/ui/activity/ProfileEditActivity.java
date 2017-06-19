package com.elatesoftware.meetings.ui.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.page.PhotoFragmentPageAdapter;
import com.elatesoftware.meetings.service.AddPhotoService;
import com.elatesoftware.meetings.service.DeletePhotoService;
import com.elatesoftware.meetings.service.GetPhotosService;
import com.elatesoftware.meetings.service.UpdateAccountService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.api.pojo.Photo;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class ProfileEditActivity extends BaseActivity {

    public static final String TAG = "ProfileEdit_logs";
    private static final int RESULT_LOAD_IMG = 302;

    @BindView(R.id.cet_name) CustomEditText cetName;
    @BindView(R.id.cet_height) CustomEditText cetHeight;
    @BindView(R.id.cet_weight) CustomEditText cetWeight;
    @BindView(R.id.cet_about) CustomEditText cetAbout;
    @BindView(R.id.cet_birth_day) CustomEditText cetBirthDay;
    @BindView(R.id.vp_photos) ViewPager vpPhotos;
    @BindView(R.id.rl_photos) RelativeLayout rlPhotos;
    @BindView(R.id.rl_photos_fab) RelativeLayout rlPhotosFab;
    @BindView(R.id.ink_indicator) CircleIndicator inkIndicator;
    @BindView(R.id.pb_progress) AVLoadingIndicatorView pbProgress;

    private Calendar birthDate;
    private HumanAnswer profileMan;
    private boolean isPermissionsAddPhoto = false;

    private UpdateAccountInfoBroadcastReceiver updateAccountInfoBroadcastReceiver;
    private AddPhotoBroadcastReceiver addPhotoBroadcastReceiver;
    private GetPhotosBroadcastReceiver getPhotosBroadcastReceiver;
    private DeletePhotoReceiver deletePhotoReceiver;

    private PhotoFragmentPageAdapter adapter;
    private int currPhotoId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, Const.REQUEST_PERMISSIONS);
        } else {
            isPermissionsAddPhoto = true;
        }

        registerBroadcast();
        setContentView(R.layout.activity_profile_edit_woman);
        setUI();
        setKeyboardListener();
        setSize();
        loadInfo();
        requestGetPhotos();
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
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
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
        if(requestCode == Const.REQUEST_PERMISSIONS && isPermissionsGranted(grantResults)) {
            isPermissionsAddPhoto = true;
        }
    }

    @Override
    public void onBackPressed() {
        requestUpdateInfo();
    }

    @OnClick(R.id.rl_add)
    public void clickAddPhoto() {
        if(isPermissionsAddPhoto) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        } else {
            showMessage(R.string.permission_not_found);
        }
    }

    @OnClick(R.id.rl_delete)
    public void clickDeletePhoto() {
        requestDeletePhoto();
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        requestUpdateInfo();
    }

    public void showDateDialog() {
        AndroidUtils.hideKeyboard(this);
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
                cetBirthDay.getEditText().setText(DateUtils.getDateToString(ProfileEditActivity.this, birthDate));
            }
        }, year, month, day);
        dpd.show();
    }

    private void setUI() {
        cetBirthDay.getEditText().setFocusable(false);
        cetBirthDay.getEditText().setFocusableInTouchMode(false);
        cetBirthDay.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDateDialog();
                    return true;
                }
                return false;
            }
        });
        if(CustomSharedPreference.getIsMan(this) == Const.WOMAN_VALUE) {
            setTheme(R.style.ThemeWoman);
            rlPhotos.setBackgroundResource(R.drawable.button_red);
            cetHeight.setVisibility(View.VISIBLE);
            cetWeight.setVisibility(View.VISIBLE);
        } else {
            rlPhotos.setBackgroundResource(R.drawable.button_blue);
            cetHeight.setVisibility(View.GONE);
            cetWeight.setVisibility(View.GONE);
        }
    }

    private void setKeyboardListener() {
        cetAbout.setKeyboardListener(this);
        cetName.setKeyboardListener(this);
        cetHeight.setKeyboardListener(this);
        cetWeight.setKeyboardListener(this);
    }

    private void registerBroadcast() {
        updateAccountInfoBroadcastReceiver = new UpdateAccountInfoBroadcastReceiver();
        registerReceiver(updateAccountInfoBroadcastReceiver, Utils.getIntentFilter(UpdateAccountService.ACTION));
        addPhotoBroadcastReceiver = new AddPhotoBroadcastReceiver();
        registerReceiver(addPhotoBroadcastReceiver, Utils.getIntentFilter(AddPhotoService.ACTION));
        getPhotosBroadcastReceiver = new GetPhotosBroadcastReceiver();
        registerReceiver(getPhotosBroadcastReceiver, Utils.getIntentFilter(GetPhotosService.ACTION));
        deletePhotoReceiver = new DeletePhotoReceiver();
        registerReceiver(deletePhotoReceiver, Utils.getIntentFilter(DeletePhotoService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(updateAccountInfoBroadcastReceiver);
        unregisterReceiver(addPhotoBroadcastReceiver);
        unregisterReceiver(getPhotosBroadcastReceiver);
        unregisterReceiver(deletePhotoReceiver);
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
        //boolean isFirstPhoto = GetPhotosAnswer.getInstance() == null ? false : GetPhotosAnswer.getInstance().getResult().size() > 0;
        startService(AddPhotoService.getIntent(this));
    }

    private void requestGetPhotos() {
        startService(GetPhotosService.getIntent(this));
    }

    private void requestDeletePhoto() {
        if(adapter != null && adapter.getPhotos() != null && adapter.getPhotos().size() > 0) {
            setProgressDialogMessage(getString(R.string.delete_photo) + " ...");
            showProgressDialog();
            startService(DeletePhotoService.getIntent(this, currPhotoId));
        }
    }

    private void setSize() {
        rlPhotos.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * Const.PHOTOS_HEIGHT_PERCENT);
        rlPhotosFab.getLayoutParams().height = (int) (AndroidUtils.getWindowsSizeParams(this)[1] * Const.PHOTOS_HEIGHT_PERCENT);
    }

    private void updateLocalInfo() {
        String name = cetName.getEditText().getText().toString();
        String aboutMe = cetAbout.getEditText().getText().toString();
        String heightStr = cetHeight.getEditText().getText().toString();
        double height = TextUtils.isEmpty(heightStr) ? 0 : Double.parseDouble(heightStr);
        String weightStr = cetWeight.getEditText().getText().toString();
        double weight = TextUtils.isEmpty(weightStr) ? 0 : Double.parseDouble(weightStr);
        //profileMan = new HumanAnswer(name, birthDate == null ? 0 : birthDate.getTimeInMillis() / 1000, aboutMe, height, weight);
        profileMan = CustomSharedPreference.getProfileInformation(this);
        profileMan.setFirstName(name);
        profileMan.setAboutMe(aboutMe);
        if(height > 0) {
            profileMan.setHeight(height);
        }
        if(weight > 0) {
            profileMan.setWeight(weight);
        }
        if(birthDate != null) {
            profileMan.setDateOfBirth(birthDate.getTimeInMillis() / 1000);
        }
    }

    private void loadInfo() {
        HumanAnswer profileWoman = CustomSharedPreference.getProfileInformation(this);
        if(profileWoman != null) {
            if(profileWoman.getDateOfBirthByCalendar() != null) {
                birthDate = profileWoman.getDateOfBirthByCalendar();
            }
            cetName.getEditText().setText(profileWoman.getFirstName());
            cetAbout.getEditText().setText(profileWoman.getAboutMe());
            Double height = profileWoman.getHeight();
            cetHeight.getEditText().setText(height == null ? "" : String.valueOf(height.intValue()));
            Double weight = profileWoman.getWeight();
            cetWeight.getEditText().setText(weight == null ? "" : String.valueOf(weight.intValue()));
            if(profileWoman.getDateOfBirthByCalendar() != null) {
                cetBirthDay.getEditText().setText(DateUtils.getDateToString(ProfileEditActivity.this, profileWoman.getDateOfBirthByCalendar()));
            }
        } else {

        }
    }

    private void loadPhoto(List<Photo> photos) {
        List<Integer> photoInteger = new ArrayList<>();
        for(Photo photo : photos) {
            photoInteger.add(photo.getId());
        }
        adapter = new PhotoFragmentPageAdapter(getSupportFragmentManager(), photoInteger, CustomSharedPreference.getProfileInformation(this).getId());
        vpPhotos.setAdapter(adapter);
        inkIndicator.setViewPager(vpPhotos);
        vpPhotos.setOffscreenPageLimit(adapter.getCount());
        vpPhotos.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                setCurrentPhotoId(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        setCurrentPhotoId(0);
    }

    private void setCurrentPhotoId(int position) {
        if(adapter.getPhotos().size() > 0) {
            currPhotoId = adapter.getPhotos().get(position);
        } else {
            currPhotoId = -1;
        }
        Log.d(TAG, "currPhotoId: " + currPhotoId);
    }

    public class UpdateAccountInfoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            ProfileEditActivity.super.onBackPressed();
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                Log.d(TAG, "UpdateAccount 200");
                if(MessageAnswer.getInstance().getSuccess()) {
                    updateLocalInfo();
                    CustomSharedPreference.setProfileInformation(ProfileEditActivity.this, profileMan);
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
                    showMessage("Photo loaded successfully");
                    requestGetPhotos();
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

    public class DeletePhotoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            hideProgressDialog();
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                if(MessageAnswer.getInstance().getSuccess()) {
                    requestGetPhotos();
                    Log.d(TAG, "DeletePhoto TRUE");
                }
            }
        }
    }
}
