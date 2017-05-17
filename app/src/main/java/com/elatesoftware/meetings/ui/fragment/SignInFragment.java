package com.elatesoftware.meetings.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.elatesoftware.meetings.ui.service.LoginService;
import com.elatesoftware.meetings.ui.service.RegisterService;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.util.model.ButtonAnimation;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAnimation = new ButtonAnimation(getContext(), btnSignIn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    @OnClick(R.id.tv_sign_up)
    public void clickTvSignUp() {
        getActivity().onBackPressed();
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
        Intent intent = new Intent(getContext(), LoginService.class);
        intent.putExtra(LoginService.USER_NAME, username);
        intent.putExtra(LoginService.PASSWORD, pass);
        getActivity().startService(intent);
        buttonAnimation.start();
    }

    private void registerReceivers() {
        loginBroadcastReceiver = new LoginBroadcastReceiver();
        IntentFilter intentFilterLoginBroadcastReceiver = new IntentFilter(LoginService.ACTION);
        intentFilterLoginBroadcastReceiver.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(loginBroadcastReceiver, intentFilterLoginBroadcastReceiver);
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(loginBroadcastReceiver);
    }
    
    public class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            buttonAnimation.stop();
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && LoginAnswer.getInstance() != null) {
                Log.d(TAG, "registration 200");
                if(LoginAnswer.getInstance().getSuccess()) {
                    CustomSharedPreference.setId(getContext(), LoginAnswer.getInstance().getResult().getId().longValue());
                    if(CustomSharedPreference.isMan(getContext())) {
                        startActivity(new Intent(getContext(), WorkActivityMan.class));
                    } else {
                        startActivity(new Intent(getContext(), WorkActivityWoman.class));
                    }
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
