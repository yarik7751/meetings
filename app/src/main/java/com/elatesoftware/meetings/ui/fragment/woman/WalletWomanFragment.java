package com.elatesoftware.meetings.ui.fragment.woman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;

import butterknife.BindView;

public class WalletWomanFragment extends BaseFragment {

    @BindView(R.id.tv_balance) TextView tvBalance;

    private static WalletWomanFragment walletWomanFragment;
    public static WalletWomanFragment getInstance() {
        if(walletWomanFragment == null) {
            walletWomanFragment = new WalletWomanFragment();
        }
        return walletWomanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet_woman, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setBalance();
    }

    private void setBalance() {
        tvBalance.setText("$785");
    }
}
