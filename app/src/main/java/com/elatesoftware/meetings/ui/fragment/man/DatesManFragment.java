package com.elatesoftware.meetings.ui.fragment.man;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.man.AddDateActivity;
import com.elatesoftware.meetings.ui.adapter.dales.BaseDatesRecyclerViewAdapter;
import com.elatesoftware.meetings.ui.adapter.dales.PendingDatesAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.service.GetDatesListService;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetDatesManAnswer;
import com.elatesoftware.meetings.util.api.pojo.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DatesManFragment extends BaseFragment {

    public static final int UPDATE = 105;
    public static final String IS_UPDATE = "IS_UPDATE";

    @BindView(R.id.rv_scheduled_dales) RecyclerView rvScheduledDales;
    @BindView(R.id.rv_pending_dales) RecyclerView rvPendingDales;

    public GetDatesListReceiver getDatesListReceiver;

    private static DatesManFragment dalesManFragment;
    public static DatesManFragment getInstance() {
        if(dalesManFragment == null) {
            dalesManFragment = new DatesManFragment();
        }
        return dalesManFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDatesListReceiver = new GetDatesListReceiver();
        getActivity().registerReceiver(getDatesListReceiver, Utils.getIntentFilter(GetDatesListService.ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dales_man, null);
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

        requestGetDatesList();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(getDatesListReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == UPDATE && data != null) {
            requestGetDatesList();
        }
    }

    @OnClick(R.id.img_add_date)
    public void clickImgAddDate() {
        startActivity(new Intent(getContext(), AddDateActivity.class));
    }

    private void requestGetDatesList() {
        getActivity().startService(GetDatesListService.getIntent(getContext()));
    }

    private List<Result> getPendingDates() {
        List<Result> dates = new ArrayList<>();
        for(int i = 0; i < GetDatesManAnswer.getInstance().getResult().size(); i++) {
            if(GetDatesManAnswer.getInstance().getResult().get(i).getDate().getStatus() == Const.PENDING) {
                dates.add(GetDatesManAnswer.getInstance().getResult().get(i));
            }
        }
        return dates;
    }

    private List<Result> getScheduledDates() {
        List<Result> dates = new ArrayList<>();
        for(int i = 0; i < GetDatesManAnswer.getInstance().getResult().size(); i++) {
            if(GetDatesManAnswer.getInstance().getResult().get(i).getDate().getStatus() == Const.SCHEDULED) {
                dates.add(GetDatesManAnswer.getInstance().getResult().get(i));
            }
        }
        return dates;
    }

    public class GetDatesListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && GetDatesManAnswer.getInstance() != null) {
                if(GetDatesManAnswer.getInstance().getSuccess()) {
                    rvScheduledDales.setAdapter(new BaseDatesRecyclerViewAdapter(getContext(), getScheduledDates(), false, R.drawable.ic_girl, R.color.button_blue_light));
                    rvPendingDales.setAdapter(new PendingDatesAdapter(getContext(), getPendingDates()));
                } else {
                    showMessage(R.string.something_wrong);
                }
            } else {
                showMessage(R.string.request_error);
            }
        }
    }
 }
