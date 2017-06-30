package com.elatesoftware.meetings.ui.fragment.all;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.all.MainActivity;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.receiver.AutarizationBroadcastReceiver;
import com.elatesoftware.meetings.service.LoginService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.DialogUtils;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.ui.view.animation.ButtonAnimation;
import com.elatesoftware.meetings.model.LoginInfo;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInFragment extends BaseFragment {

    public static final String TAG = "SignInFragment_logs";

    @BindView(R.id.cet_email) CustomEditText cetEmail;
    @BindView(R.id.cet_pass) CustomEditText cetPass;
    @BindView(R.id.btn_sign_in) CircularProgressButton btnSignIn;
    @BindView(R.id.tv_sign_up) TextView tvSignUp;
    @BindView(R.id.tv_forgot_pass) TextView tvForgotPass;
    
    private LoginBroadcastReceiver loginBroadcastReceiver;
    private ButtonAnimation buttonAnimation;

    private static SignInFragment signInFragment;
    public static SignInFragment getInstance() {
        if(signInFragment == null) {
            signInFragment = new SignInFragment();
        }
        return signInFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceivers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, null);
        return v;
    }

    private void setKeyboardListener() {
        cetEmail.setKeyboardListener(getActivity());
        cetPass.setKeyboardListener(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoginInfo();
        setKeyboardListener();
        buttonAnimation = new ButtonAnimation(getContext(), btnSignIn);
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

    @OnClick(R.id.tv_sign_up)
    public void clickTvSignUp() {
        //getActivity().onBackPressed();
        ((MainActivity) getActivity()).setSignUpFragment();
    }

    @OnClick(R.id.tv_forgot_pass)
    public void clickTvForgotPass() {

    }

    @OnClick(R.id.btn_sign_in)
    public void clickBtnSignIn() {
        requestLogin();
    }

    public void requestLogin() {
        String username = cetEmail.getEditText().getText().toString();
        String pass = cetPass.getEditText().getText().toString();
        if(checkAutInfo(getContext(), cetEmail, cetPass)) {
            Intent intent = new Intent(getContext(), LoginService.class);
            intent.putExtra(LoginService.USER_NAME, username);
            intent.putExtra(LoginService.PASSWORD, pass);
            getActivity().startService(intent);
            buttonAnimation.start();
        }
    }

    public static boolean checkAutInfo(Context context, CustomEditText cetEmail, CustomEditText cetPass) {
        String userName = cetEmail.getEditText().getText().toString();
        String password = cetPass.getEditText().getText().toString();
        if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
            DialogUtils.showErrorDialog(context, context.getString(R.string.empty_data));
            return  false;
        }
        if(!StringUtils.isEmailValid(userName)) {
            DialogUtils.showErrorDialog(context, context.getString(R.string.invalid_email));
            return  false;
        }
        return true;
    }

    private void setLoginInfo() {
        cetEmail.getEditText().setText(LoginInfo.getInstance().getLogin());
        cetPass.getEditText().setText(LoginInfo.getInstance().getPass());
        Log.d(TAG, "setLoginInfo: " + LoginInfo.getInstance());
    }

    private void registerReceivers() {
        loginBroadcastReceiver = new LoginBroadcastReceiver(AutarizationBroadcastReceiver.SIGN_IN);
        IntentFilter intentFilterLoginBroadcastReceiver = new IntentFilter(LoginService.ACTION);
        intentFilterLoginBroadcastReceiver.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(loginBroadcastReceiver, intentFilterLoginBroadcastReceiver);
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(loginBroadcastReceiver);
    }
    
    public class LoginBroadcastReceiver extends AutarizationBroadcastReceiver {

        public LoginBroadcastReceiver(int sign) {
            super(sign);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            buttonAnimation.stop();
        }
    }
}
