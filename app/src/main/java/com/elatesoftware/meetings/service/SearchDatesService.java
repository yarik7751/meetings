package com.elatesoftware.meetings.service;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.api.Api;
import com.elatesoftware.meetings.util.model.params.SearchDatesFilter;

public class SearchDatesService extends IntentService {

    public static final String TAG = "SearchDatesS_log";
    public static final String ACTION = "com.elatesoftware.meetings.service.SearchDatesService";
    public static final String FILTERS = "FILTERS";

    public SearchDatesService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SearchDatesFilter searchDatesFilter = intent.getParcelableExtra(FILTERS);
        String response = Api.searchDates(CustomSharedPreference.getToken(this), searchDatesFilter);
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, SearchDatesFilter searchDatesFilter) {
        Intent intent = new Intent(context, SearchDatesService.class);
        intent.putExtra(FILTERS, searchDatesFilter);
        return intent;
    }
}
