package com.elatesoftware.meetings.ui.adapter.dales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.DaleDetailsActivity;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.api.pojo.Metting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO test data
public class DalesRecyclerScheduleViewAdapter extends RecyclerView.Adapter<DalesRecyclerScheduleViewAdapter.DalesViewHolder> {

    public static final String TAG = "DalesRVAdapter_logs";

    private Activity activity;
    private Context context;
    private List<Metting> dates;

    public DalesRecyclerScheduleViewAdapter(Activity activity, Context context, List<Metting> dates) {
        this.activity = activity;
        this.context = context;
        this.dates = new ArrayList<>();
        this.dates.addAll(dates);
        selectDates();
    }

    private void selectDates() {
        for(int i = dates.size() - 1; i >= 0; i--) {
            if(dates.get(i).getStatus() != Const.SCHEDULED) {
                dates.remove(i);
            }
        }
    }

    @Override
    public DalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dales, parent, false);
        return new DalesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DalesViewHolder holder, final int position) {
        /*holder.tvAmount.setText(dates.get(position).getAmount().toString());
        holder.tvTime.setText(
                DateUtils.getDateByStr(new Date(dates.get(position).getStartTime().longValue()), DateUtils.DATE_FORMAT_OUTPUT) + "\n" +
                        DateUtils.getDateByStr(new Date(dates.get(position).getEndTime().longValue()), DateUtils.DATE_FORMAT_OUTPUT)
        );
        holder.tvPlace.setText(dates.get(position).getPlace());*/
    }

    @Override
    public int getItemCount() {
        return /*dates == null ? 0 : dates.size()*/3;
    }

    public class DalesViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvPlace, tvTime, tvAmount, tvName;
        public CircleImageView imgPhoto;

        public DalesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvPlace = (TextView) itemView.findViewById(R.id.tv_place);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
        }
    }
}