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

        vFabChat.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void setUI() {
        if(CustomSharedPreference.getIsMan(this) == Api.WOMAN_VALUE) {
            super.setUI();
        } else {
            int textColor = R.color.seek_bar;
            int gradient = R.drawable.button_blue;
            tvName.setTextColor(getResources().getColor(textColor));
            tvAge.setTextColor(getResources().getColor(textColor));
            tvStartTime.setTextColor(getResources().getColor(textColor));
            tvEndTime.setTextColor(getResources().getColor(textColor));
            tvPresent.setTextColor(getResources().getColor(textColor));
            rlPhotos.setBackgroundResource(gradient);
            int visibility = View.GONE;
            cvAgeWoman.setVisibility(visibility);
            cvHeightWoman.setVisibility(visibility);
            cvWeightWoman.setVisibility(visibility);
            cvHairColor.setVisibility(visibility);
            //((FloatingActionButton) vFabChat).setColorFilter(getResources().getColor(textColor));
        }
    }
}
