package com.elatesoftware.meetings.ui.adapter;

import android.content.Context;
import android.view.View;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.elatesoftware.meetings.api.pojo.Result;
import com.elatesoftware.meetings.ui.activity.all.ShowScheduledDateActivity;
import com.elatesoftware.meetings.ui.adapter.recycler_view.dates.BaseDatesRecyclerViewAdapter;

import java.util.List;

public class ScheduledDatesAdapter extends BaseDatesRecyclerViewAdapter {

    public ScheduledDatesAdapter(Context context, List<Result> dates) {
        super(context, dates, false, R.drawable.ic_girl, R.color.button_blue_light);
    }

    @Override
    public void onBindViewHolder(DalesViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.rlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting.setInstance(dates.get(position).getDate());
                context.startActivity(ShowScheduledDateActivity.getIntent(context, dates.get(position).getPartnerId()));
            }
        });
    }
}
