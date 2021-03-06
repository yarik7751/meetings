package com.elatesoftware.meetings.ui.fragment.woman;

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
import android.widget.RelativeLayout;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.api.pojo.GetDatesManAnswer;
import com.elatesoftware.meetings.service.GetDatesListService;
import com.elatesoftware.meetings.ui.adapter.recycler_view.dates.WomanPendingDatesAdapter;
import com.elatesoftware.meetings.ui.adapter.recycler_view.dates.WomanScheduledDatesAdapter;
import com.elatesoftware.meetings.ui.fragment.all.BaseFragment;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.api.pojo.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DatesWomanFragment extends BaseFragment {

    /*public static final int UPDATE = 105;
    public static final String IS_UPDATE = "IS_UPDATE";*/

    @BindView(R.id.rv_scheduled_dales) RecyclerView rvScheduledDales;
    @BindView(R.id.rv_pending_dales) RecyclerView rvPendingDales;
    @BindView(R.id.rl_add_date) RelativeLayout rlAddDate;

    public GetDatesListReceiver getDatesListReceiver;

    private static DatesWomanFragment dalesManFragment;
    public static DatesWomanFragment getInstance() {
        if(dalesManFragment == null) {
            dalesManFragment = new DatesWomanFragment();
        }
        return dalesManFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDatesListReceiver = new GetDatesListReceiver();
        getActivity().registerReceiver(getDatesListReceiver, Utils.getIntentFilter(GetDatesListService.ACTION));
    }

    @Override
    public void onResume() {
        super.onResume();
        requestGetDatesList();
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

        rlAddDate.setVisibility(View.INVISIBLE);

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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == UPDATE && data != null) {
            requestGetDatesList();
        }
    }*/

    private void requestGetDatesList() {
        setProgressDialogMessage(getString(R.string.dates_loading) + " ...");
        showProgressDialog();
        getActivity().startService(GetDatesListService.getIntent(getContext()));
    }

    private List<Result> getPendingDates(List<Result> result) {
        List<Result> dates = new ArrayList<>();
        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).getDate().getStatus() == Api.PENDING) {
                dates.add(result.get(i));
            }
        }
        return dates;
    }

    private List<Result> getScheduledDates(List<Result> result) {
        List<Result> dates = new ArrayList<>();
        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).getDate().getStatus() == Api.SCHEDULED) {
                dates.add(result.get(i));
            }
        }
        return dates;
    }

    public class GetDatesListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            GetDatesManAnswer response = intent.getParcelableExtra(Api.RESPONSE);
            hideProgressDialog();
            if(response != null) {
                if(response.getSuccess()) {
                    rvScheduledDales.setAdapter(new WomanScheduledDatesAdapter(getContext(), getScheduledDates(response.getResult())));
                    rvPendingDales.setAdapter(new WomanPendingDatesAdapter(getContext(), getPendingDates(response.getResult())));
                } else {
                    showMessage(R.string.something_wrong);
                }
            } else {
                showMessage(R.string.request_error);
            }
        }
    }
}
