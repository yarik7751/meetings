package com.elatesoftware.meetings.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.MainActivity;
import com.elatesoftware.meetings.ui.activity.man.WorkActivityMan;
import com.elatesoftware.meetings.ui.activity.woman.WorkActivityWoman;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInFragment extends BaseFragment {

    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_pass) EditText etPass;
    @BindView(R.id.btn_sign_in) Button btnSignIn;
    @BindView(R.id.tv_sign_up) TextView tvSignUp;
    @BindView(R.id.tv_forgot_pass) TextView tvForgotPass;

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
    }

    @OnClick(R.id.tv_sign_up)
    public void clickTvSignUp() {
        ((MainActivity) getActivity()).setSignUpFragment();
    }

    @OnClick(R.id.btn_sign_in)
    public void clickBtnSignIn() {
        if(StringUtils.isValidValues(getContext(), etEmail, etPass)) {
            //todo test
            CustomSharedPreference.setId(getContext(), 1000);
        }
    }
}
