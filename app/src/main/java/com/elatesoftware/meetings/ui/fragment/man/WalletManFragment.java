package com.elatesoftware.meetings.ui.fragment.man;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.adapter.recycler_view.wallet.LastOperationAdapter;
import com.elatesoftware.meetings.ui.fragment.all.BaseFragment;

import butterknife.BindView;

public class WalletManFragment extends BaseFragment {

    @BindView(R.id.rv_last_operation) RecyclerView rvLastOperation;

    private static WalletManFragment walletManFragment;
    public static WalletManFragment getInstance() {
        if(walletManFragment == null) {
            walletManFragment = new WalletManFragment();
        }
        return walletManFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet_man, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLastOperations();
        setBalance();
    }

    private void setBalance() {
    }

    private void setLastOperations() {
        rvLastOperation.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvLastOperation.setAdapter(new LastOperationAdapter(getContext()));
    }
}
