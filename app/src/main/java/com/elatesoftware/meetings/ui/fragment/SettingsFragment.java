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

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.MainActivity;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.service.ExitService;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.model.ButtonAnimation;
import com.elatesoftware.meetings.util.model.LoginInfo;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment {

    public static final String TAG = "SettingsFragment_logs";

    @BindView(R.id.btn_sign_out) CircularProgressButton btnSignOut;

    ButtonAnimation buttonAnimation;

    private static SettingsFragment fragment;
    public static SettingsFragment getInstance() {
        if(fragment == null) {
            fragment = new SettingsFragment();
        }
        return fragment;
    }

    public SignOutReceiver signOutReceiver;

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
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    @OnClick(R.id.btn_sign_out)
    public void clickBtnSingOut() {
        requestSugnOut();
        buttonAnimation.start();
    }

    @OnClick(R.id.img_change_email)
    public void clickImgChangeEmail() {
        Utils.showEditDialog(getContext(), getString(R.string.change) + " " + getString(R.string.mail), "", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    @OnClick(R.id.img_change_phone)
    public void clickImgChangePhone() {
        Utils.showEditDialog(getContext(), getString(R.string.change) + " " + getString(R.string.phone), "", InputType.TYPE_CLASS_PHONE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void requestSugnOut() {
        getActivity().startService(ExitService.getIntent(getContext()));
    }

    private void registerReceivers() {
        signOutReceiver = new SignOutReceiver();
        getActivity().registerReceiver(signOutReceiver, Utils.getIntentFilter(ExitService.ACTION));
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(signOutReceiver);
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
