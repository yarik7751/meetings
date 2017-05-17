package com.elatesoftware.meetings.ui.fragment.man;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class WalletManFragment extends BaseFragment {

    @BindView(R.id.tv_balance_available) TextView tvBalanceAvailable;
    @BindView(R.id.tv_balance_blocked) TextView tvBalanceBlocked;
    @BindView(R.id.img_photo) CircleImageView imgPhoto;

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

        Picasso.with(getContext()).load(R.drawable.example_photo).centerCrop()
                .resize(AndroidUtils.dp(48), AndroidUtils.dp(48))
                .into(imgPhoto, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
        setBalance();
    }

    private void setBalance() {
    }
}
