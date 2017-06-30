package com.elatesoftware.meetings.ui.adapter.recycler_view.dates;

import android.content.Context;
import android.view.View;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.elatesoftware.meetings.api.pojo.Result;
import com.elatesoftware.meetings.ui.activity.woman.ShowPendingDateActivity;

import java.util.List;

public class WomanPendingDatesAdapter extends BaseDatesRecyclerViewAdapter {

    public WomanPendingDatesAdapter(Context context, List<Result> dates) {
        super(context, dates, true, R.drawable.ic_person_white_24dp, R.color.button_red_dark);
    }

    @Override
    public void onBindViewHolder(DalesViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.rlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting.setInstance(dates.get(position).getDate());
                context.startActivity(ShowPendingDateActivity.getIntent(context, dates.get(position).getCreatorId().longValue()));
            }
        });
    }
}
