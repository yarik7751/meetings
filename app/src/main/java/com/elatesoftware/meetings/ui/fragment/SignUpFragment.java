package com.elatesoftware.meetings.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpFragment extends BaseFragment {

    @BindView(R.id.cet_email) CustomEditText cetEmail;
    @BindView(R.id.cet_pass) CustomEditText cetPass;
    //@BindView(R.id.cet_rep_pass) CustomEditText etRepPass;
    @BindView(R.id.btn_sign_up) CircularProgressButton btnSignUp;
    @BindView(R.id.tv_forgot_password) TextView tvForgotPassword;

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

    }

    @OnClick(R.id.tv_forgot_password)
    public void clickTvSignIn() {

    }

    @OnClick(R.id.btn_sign_up)
    public void clickBtnSignUp() {
        CustomSharedPreference.setId(getContext(), 1000);
        if(CustomSharedPreference.isMan(getContext())) {
            startActivity(new Intent(getContext(), WorkActivityMan.class));
        } else {
            startActivity(new Intent(getContext(), WorkActivityWoman.class));
        }
    }
}
