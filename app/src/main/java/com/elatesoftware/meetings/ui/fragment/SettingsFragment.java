package com.elatesoftware.meetings.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.service.UpdateAccountService;
import com.elatesoftware.meetings.ui.activity.MainActivity;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.service.ExitService;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DialogUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.ui.view.animation.ButtonAnimation;
import com.elatesoftware.meetings.model.LoginInfo;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment {

    public static final String TAG = "SettingsFragment_logs";

    @BindView(R.id.btn_sign_out) CircularProgressButton btnSignOut;
    @BindView(R.id.sb_push_up) SwitchButton sbPushUp;
    @BindView(R.id.sb_mail_notifications) SwitchButton sbMailNotifications;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.tv_change_password) TextView tvChangePassword;
    @BindView(R.id.tv_change_pin_code) TextView tvChangePinCode;
    @BindView(R.id.img_change_email) ImageView imgChangeEmail;
    @BindView(R.id.img_change_phone) ImageView imgChangePhone;
    @BindView(R.id.fl_change_password) FrameLayout flChangePassword;
    @BindView(R.id.fl_change_phone) FrameLayout flChangePhone;
    @BindView(R.id.fl_change_email) FrameLayout flChangeEmail;
    @BindView(R.id.fl_change_pin_code) FrameLayout flChangePinCode;

    ButtonAnimation buttonAnimation;

    private static SettingsFragment fragment;
    public static SettingsFragment getInstance() {
        if(fragment == null) {
            fragment = new SettingsFragment();
        }
        return fragment;
    }

    private UpdateAccountInfoBroadcastReceiver updateAccountInfoBroadcastReceiver;
    private SignOutReceiver signOutReceiver;

    private HumanAnswer changeProfile;

    @Override
    public void onStart() {
        super.onStart();
        registerReceivers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAnimation = new ButtonAnimation(getContext(), btnSignOut);
        changeProfile = CustomSharedPreference.getProfileInformation(getContext());
        setStyleColor();
        loadInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    @OnClick(R.id.btn_sign_out)
    public void clickBtnSingOut() {
        requestSignOut();
        buttonAnimation.start();
    }

    @OnClick(R.id.fl_change_email)
    public void clickImgChangeEmail() {
        DialogUtils.showEditDialog(
                getContext(),
                getString(R.string.change) + " " + getString(R.string.mail),
                changeProfile.getUsername() == null ? "" : changeProfile.getUsername(),
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeProfile.setUsername(DialogUtils.getDialogMessage());
                requestUpdateInfo();

            }
        });
    }

    @OnClick(R.id.fl_change_phone)
    public void clickImgChangePhone() {
        DialogUtils.showEditDialog(
                getContext(),
                getString(R.string.change) + " " + getString(R.string.phone),
                changeProfile.getPhone() == null ? "" : changeProfile.getPhone(),
                InputType.TYPE_CLASS_PHONE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeProfile.setPhone(DialogUtils.getDialogMessage());
                requestUpdateInfo();
            }
        });
    }

    private void loadInfo() {
        HumanAnswer profile = CustomSharedPreference.getProfileInformation(getContext());
        tvEmail.setText(profile.getUsername());
        tvPhone.setText(profile.getPhone() == null ? "---" : profile.getPhone());
    }

    private void setStyleColor() {
        if(CustomSharedPreference.getIsMan(getContext()) == Const.WOMAN_VALUE) {
            sbPushUp.setBackDrawableRes(R.drawable.switch_bg_woman);
            sbMailNotifications.setBackDrawableRes(R.drawable.switch_bg_woman);
            int color = getColor(R.color.button_red_dark);
            tvPhone.setTextColor(color);
            tvEmail.setTextColor(color);
            tvChangePassword.setTextColor(color);
            tvChangePinCode.setTextColor(color);
            imgChangePhone.setColorFilter(color);
            imgChangeEmail.setColorFilter(color);
            flChangePassword.setBackgroundResource(R.drawable.button_settings_bg_woman_border);
            flChangePinCode.setBackgroundResource(R.drawable.button_settings_bg_woman_border);
        }
    }

    private void requestUpdateInfo() {
        AndroidUtils.hideKeyboard(getActivity());
        setProgressDialogMessage(getString(R.string.data_updating));
        showProgressDialog();
        HumanAnswer.setInstance(changeProfile);
        getActivity().startService(new Intent(getContext(), UpdateAccountService.class));
    }

    private void requestSignOut() {
        getActivity().startService(ExitService.getIntent(getContext()));
    }

    private void registerReceivers() {
        signOutReceiver = new SignOutReceiver();
        getActivity().registerReceiver(signOutReceiver, Utils.getIntentFilter(ExitService.ACTION));
        updateAccountInfoBroadcastReceiver = new UpdateAccountInfoBroadcastReceiver();
        getActivity().registerReceiver(updateAccountInfoBroadcastReceiver, Utils.getIntentFilter(UpdateAccountService.ACTION));
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(updateAccountInfoBroadcastReceiver);
        getActivity().unregisterReceiver(signOutReceiver);
    }

    public class UpdateAccountInfoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            hideProgressDialog();
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                Log.d(TAG, "UpdateAccount 200");
                if(MessageAnswer.getInstance().getSuccess()) {
                    CustomSharedPreference.setProfileInformation(getContext(), HumanAnswer.getInstance());
                    loadInfo();
                    showMessage(R.string.update_success);
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

    public class SignOutReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            buttonAnimation.stop();
            String response = intent.getStringExtra(Const.RESPONSE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && MessageAnswer.getInstance() != null) {
                Log.d(TAG, "SignOut 200");
                if(MessageAnswer.getInstance().getSuccess()) {
                    Log.d(TAG, "SignOut TRUE");
                    CustomSharedPreference.setToken(getContext(), null);
                    Intent mainIntent = new Intent(getContext(), MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                    LoginInfo.getInstance().setPass("");
                    getActivity().finish();
                } else {
                    showMessage(R.string.something_wrong);
                    Log.d(TAG, "SignOut FALSE");
                }
            } else {
                showMessage(R.string.request_error);
                Log.d(TAG, "SignOut error");
            }
        }
    }
}
