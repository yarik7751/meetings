package com.elatesoftware.meetings.ui.activity.all.show_date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.elatesoftware.meetings.ui.activity.all.ChatActivity;

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
                startActivity(ChatActivity.getIntent(ShowScheduledDateActivity.this));
            }
        });

        vCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
