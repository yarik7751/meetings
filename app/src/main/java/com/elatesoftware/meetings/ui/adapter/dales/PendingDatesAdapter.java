package com.elatesoftware.meetings.ui.adapter.dales;

import android.content.Context;
import android.view.View;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.man.ShowPendingWomenActivity;
import com.elatesoftware.meetings.ui.activity.woman.ShowSearchDateActivity;
import com.elatesoftware.meetings.util.api.pojo.Meeting;
import com.elatesoftware.meetings.util.api.pojo.Result;

import java.util.List;

public class PendingDatesAdapter extends BaseDatesRecyclerViewAdapter {

    public PendingDatesAdapter(Context context, List<Result> dates) {
        super(context, dates, false, R.drawable.ic_girl, R.color.button_blue_light);
    }

    @Override
    public void onBindViewHolder(DalesViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.rlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(ShowPendingWomenActivity.getIntent(context, dates.get(position).getDate().getId()));
            }
        });
    }
}
