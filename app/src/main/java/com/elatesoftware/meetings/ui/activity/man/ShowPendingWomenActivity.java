package com.elatesoftware.meetings.ui.activity.man;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.service.GetPendingWomenService;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.recycler_view.PendingWomenAdapter;
import com.elatesoftware.meetings.ui.fragment.man.DatesManFragment;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.api.pojo.GetPendingWomenAnswer;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowPendingWomenActivity extends BaseActivity {

    public static final String DATE_ID = "DATE_ID";
    public static final int CLOSE = 105;
    public static final String IS_CLOSE = "IS_CLOSE";

    @BindView(R.id.rv_pending_women) RecyclerView rvPendingWomen;

    private GetPendingWomenReceiver getPendingWomenReceiver;

    private long dateId;

    public static Intent getIntent(Context context, long dateId) {
        Intent intent = new Intent(context, ShowPendingWomenActivity.class);
        intent.putExtra(DATE_ID, dateId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pending_women);
        dateId = getIntent().getLongExtra(DATE_ID, -1);

        rvPendingWomen.setLayoutManager(new LinearLayoutManager(this));
        requestGetPendingWoman();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcast();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            switch(requestCode) {

                case CLOSE:
                    if(data.getBooleanExtra(IS_CLOSE, false)) {
                        updateActivitySuccess();
                    }
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.rl_back)
    public void back() {
        onBackPressed();
    }

    private void updateActivitySuccess() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(DatesManFragment.IS_UPDATE, true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void requestGetPendingWoman() {
        setProgressDialogMessage(getString(R.string.women_loading) + " ...");
        showProgressDialog();
        startService(GetPendingWomenService.getIntent(this, dateId));
    }

    private void registerBroadcast() {
        getPendingWomenReceiver = new GetPendingWomenReceiver();
        registerReceiver(getPendingWomenReceiver, Utils.getIntentFilter(GetPendingWomenService.ACTION));
    }

    private void unregisterBroadcast() {
        unregisterReceiver(getPendingWomenReceiver);
    }

    public class GetPendingWomenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            GetPendingWomenAnswer answer = intent.getParcelableExtra(Const.RESPONSE);
            hideProgressDialog();
            if(answer != null) {
                if(answer.getSuccess()) {
                    PendingWomenAdapter adapter = new PendingWomenAdapter(context, answer, (int) dateId);
                    rvPendingWomen.setAdapter(adapter);
                } else {
                    showMessage(R.string.something_wrong);
                }
            } else {
                showMessage(R.string.request_error);
            }
        }
    }
}
