package com.elatesoftware.meetings.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.MainActivity;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.receiver.AutarizationBroadcastReceiver;
import com.elatesoftware.meetings.ui.service.RegisterService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.model.ButtonAnimation;
import com.elatesoftware.meetings.util.model.LoginInfo;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.internal.Util;

public class SignUpFragment extends BaseFragment {

    public static final String TAG = "SignUpFragment_logs";

    //public static final int SIGN_IN = 143;
    //public static final int SIGN_UP = 145;

    CustomEditText cetEmail;
    CustomEditText cetPass;
    //@BindView(R.id.cet_rep_pass) CustomEditText cetRepPass;
    @BindView(R.id.btn_sign_up) CircularProgressButton btnSignUp;
    @BindView(R.id.tv_sign_in) TextView tvSignIn;

    private RegisterBroadcastReceiver registerBroadcastReceiver;
    private ButtonAnimation buttonAnimation;
    //private int screen = SIGN_UP;

    private static SignUpFragment signUpFragment;
    public static SignUpFragment getInstance() {
        if(signUpFragment == null) {
            signUpFragment = new SignUpFragment();
        }
        return signUpFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceivers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        cetEmail = (CustomEditText) view.findViewById(R.id.cet_up_email);
        cetPass = (CustomEditText) view.findViewById(R.id.cet_up_pass);
        setLoginInfo();
        setKeyboardListener();
        buttonAnimation = new ButtonAnimation(getContext(), btnSignUp);
    }

    @Override
    public void onPause() {
        super.onPause();
        String userName = cetEmail.getEditText().getText().toString();
        String password = cetPass.getEditText().getText().toString();
        LoginInfo.getInstance().setLogin(userName);
        LoginInfo.getInstance().setPass(password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    @OnClick(R.id.tv_sign_in)
    public void clickTvSignIn() {
        ((MainActivity) getActivity()).setSignInFragment();
    }

    private void registerReceivers() {
        registerBroadcastReceiver = new RegisterBroadcastReceiver(AutarizationBroadcastReceiver.SIGN_UP);
        IntentFilter intentFilterRegisterBroadcastReceiver = new IntentFilter(RegisterService.ACTION);
        intentFilterRegisterBroadcastReceiver.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(registerBroadcastReceiver, intentFilterRegisterBroadcastReceiver);
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(registerBroadcastReceiver);
    }

    private void setKeyboardListener() {
        cetEmail.setKeyboardListener(getActivity());
        cetPass.setKeyboardListener(getActivity());
        //cetRepPass.setKeyboardListener(getActivity());
    }

    private void requestRegister() {
        String userName = cetEmail.getEditText().getText().toString();
        String password = cetPass.getEditText().getText().toString();
        //String repPassword = cetRepPass.getEditText().getText().toString();
        if(Utils.checkRegInfo(getContext(), cetEmail, cetPass, null)) {
            Intent intent = new Intent(getContext(), RegisterService.class);
            intent.putExtra(RegisterService.USER_NAME, userName);
            intent.putExtra(RegisterService.PASSWORD, password);
            intent.putExtra(RegisterService.GENDER, CustomSharedPreference.isMan(getContext()) ? Const.MAN_VALUE : Const.WOMAN_VALUE);
            getActivity().startService(intent);
            buttonAnimation.start();
        }
    }

    private void setLoginInfo() {
        Log.d(TAG, "setLoginInfo: " + LoginInfo.getInstance());
        cetPass.getEditText().setText(LoginInfo.getInstance().getPass());
        cetEmail.getEditText().setText(LoginInfo.getInstance().getLogin());
    }

    @OnClick(R.id.btn_sign_up)
    public void clickBtnSignUp() {
        requestRegister();
    }

    public class RegisterBroadcastReceiver extends AutarizationBroadcastReceiver {

        public RegisterBroadcastReceiver(int sign) {
            super(sign);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            buttonAnimation.stop();
        }
    }
}
