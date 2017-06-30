package com.elatesoftware.meetings.ui.fragment.all;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.CircularProgressButton;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.all.MainActivity;
import com.elatesoftware.meetings.util.CustomSharedPreference;

import butterknife.BindView;
import butterknife.OnClick;

public class GenderFragment extends BaseFragment {

    @BindView(R.id.btn_woman) CircularProgressButton btnWoman;
    @BindView(R.id.btn_man) CircularProgressButton btnMan;

    private static GenderFragment genderFragment;
    public static GenderFragment getInstance() {
        if(genderFragment == null) {
            genderFragment = new GenderFragment();
        }
        return genderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_gender, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_woman)
    public void clickBtnWoman() {
        //showMessage("Oops ;) It is not ready yet!");
        CustomSharedPreference.setIsMan(getContext(), false);
        ((MainActivity) getActivity()).setSignUpFragment();
    }

    @OnClick(R.id.btn_man)
    public void clickBtnMan() {
        CustomSharedPreference.setIsMan(getContext(), true);
        ((MainActivity) getActivity()).setSignUpFragment();
    }
}
