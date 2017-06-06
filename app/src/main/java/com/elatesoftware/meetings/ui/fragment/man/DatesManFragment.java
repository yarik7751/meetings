package com.elatesoftware.meetings.ui.fragment.man;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.AddDateActivity;
import com.elatesoftware.meetings.ui.adapter.dales.DalesPendingRecyclerViewAdapter;
import com.elatesoftware.meetings.ui.adapter.dales.DalesRecyclerScheduleViewAdapter;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.ui.service.GetDatesListService;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.util.api.pojo.GetDatesManAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetPhotosAnswer;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

//todo 10 имя класса (OK)
public class DatesManFragment extends BaseFragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(getDatesListReceiver);
    }

    @OnClick(R.id.img_add_date)
    public void clickImgAddDate() {
        startActivity(new Intent(getContext(), AddDateActivity.class));
    }

    private void requestGetDatesList() {
        getActivity().startService(GetDatesListService.getIntent(getContext()));
    }

    public class GetDatesListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(response != null && response.equals(String.valueOf(Const.CODE_SUCCESS)) && GetDatesManAnswer.getInstance() != null) {
                if(GetDatesManAnswer.getInstance().getSuccess()) {
                    rvScheduledDales.setAdapter(new DalesRecyclerScheduleViewAdapter(getActivity(), getContext(), GetDatesManAnswer.getInstance().getResult()));
                    rvPendingDales.setAdapter(new DalesPendingRecyclerViewAdapter(getActivity(), getContext(), GetDatesManAnswer.getInstance().getResult()));
                } else {
                    showMessage(R.string.something_wrong);
                }
            } else {
                showMessage(R.string.request_error);
            }
        }
    }
 }
