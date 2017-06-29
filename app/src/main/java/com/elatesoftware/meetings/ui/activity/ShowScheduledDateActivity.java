package com.elatesoftware.meetings.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;

public class ShowScheduledDateActivity extends ShowDateActivity {

    public static Intent getIntent(Context context, long creatorId) {
        Intent intent = new Intent(context, ShowScheduledDateActivity.class);
        intent.putExtra(CREATOR_ID, creatorId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
