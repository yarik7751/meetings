package com.elatesoftware.meetings.ui.fragment.woman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DatesWomanFragment extends BaseFragment {

    @BindView(R.id.img_avatar) CircleImageView imgAvatar;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.rv_scheduled_dales) RecyclerView rvScheduledDales;
    @BindView(R.id.rv_pending_dales) RecyclerView rvPendingDales;

    private static DatesWomanFragment dalesWomanFragment;
    public static DatesWomanFragment getInstance() {
        if(dalesWomanFragment == null) {
            dalesWomanFragment = new DatesWomanFragment();
        }
        return dalesWomanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dales_woman, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvScheduledDales.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvPendingDales.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        /*rvScheduledDales.setAdapter(new DalesRecyclerViewAdapter(getContext()));
        rvPendingDales.setAdapter(new DalesRecyclerViewAdapter(getContext()));*/
    }
}
