package com.elatesoftware.meetings.ui.fragment;

import android.content.BroadcastReceiver;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.MainActivity;
import com.elatesoftware.meetings.ui.activity.man.WorkActivityMan;
import com.elatesoftware.meetings.ui.activity.woman.WorkActivityWoman;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.receiver.AutarizationBroadcastReceiver;
import com.elatesoftware.meetings.ui.service.RegisterService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.util.api.Api;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.model.ButtonAnimation;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpFragment extends BaseFragment {

    public static final String TAG = "SignUpFragment_logs";

    @BindView(R.id.cet_email) CustomEditText cetEmail;
    @BindView(R.id.cet_pass) CustomEditText cetPass;
    @BindView(R.id.cet_rep_pass) CustomEditText etRepPass;
    @BindView(R.id.btn_sign_up) CircularProgressButton btnSignUp;
    @BindView(R.id.tv_sign_in) TextView tvSignIn;

    private RegisterBroadcastReceiver registerBroadcastReceiver;
    private ButtonAnimation buttonAnimation;

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
        super.onViewCreated(view, savedInstanceState);
        buttonAnimation = new ButtonAnimation(getContext(), btnSignUp);
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
        registerBroadcastReceiver = new RegisterBroadcastReceiver();
        IntentFilter intentFilterRegisterBroadcastReceiver = new IntentFilter(RegisterService.ACTION);
        intentFilterRegisterBroadcastReceiver.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(registerBroadcastReceiver, intentFilterRegisterBroadcastReceiver);
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(registerBroadcastReceiver);
    }

    private void requestRegister() {
        String userName = cetEmail.getEditText().getText().toString();
        String password = cetPass.getEditText().getText().toString();
        Intent intent = new Intent(getContext(), RegisterService.class);
        intent.putExtra(RegisterService.USER_NAME, userName);
        intent.putExtra(RegisterService.PASSWORD, password);
        intent.putExtra(RegisterService.GENDER, CustomSharedPreference.isMan(getContext()) ? Const.MAN_VALUE : Const.WOMAN_VALUE);
        getActivity().startService(intent);
        buttonAnimation.start();
    }

    @OnClick(R.id.btn_sign_up)
    public void clickBtnSignUp() {
        requestRegister();
    }

    public class RegisterBroadcastReceiver extends AutarizationBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            buttonAnimation.stop();
        }
    }
}
