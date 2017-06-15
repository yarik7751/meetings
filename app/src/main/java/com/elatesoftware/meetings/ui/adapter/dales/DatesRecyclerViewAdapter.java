package com.elatesoftware.meetings.ui.adapter.dales;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.ShowDateActivity;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.util.api.pojo.Result;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DatesRecyclerViewAdapter extends RecyclerView.Adapter<DatesRecyclerViewAdapter.DalesViewHolder> {

    public static final String TAG = "DalesRVAdapter_logs";

    private Context context;
    private List<Result> dates;
    private boolean isCreator = false;
    private int defaultIcon;
    private int defaultTint;
    private View.OnClickListener clickListener;

    public DatesRecyclerViewAdapter(Context context, List<Result> dates, boolean isCreator, int defaultIcon, int defaultTint, View.OnClickListener clickListener) {
        this.context = context;
        this.dates = dates;
        this.isCreator = isCreator;
        this.defaultIcon = defaultIcon;
        this.defaultTint = defaultTint;
        this.clickListener = clickListener;
    }

    public DalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dales, parent, false);
        return new DalesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DalesViewHolder holder, final int position) {
        holder.tvAmount.setText("$" + dates.get(position).getDate().getAmount().intValue());
        Long startTime = dates.get(position).getDate().getStartTime();
        Long endTime = dates.get(position).getDate().getEndTime();
        holder.tvTime.setText(
                DateUtils.getDateByStr(new Date(startTime == null ? 0 : startTime * 1000), DateUtils.DATE_FORMAT_OUTPUT) + "\n" +
                DateUtils.getDateByStr(new Date(endTime == null ? 0 : endTime * 1000), DateUtils.DATE_FORMAT_OUTPUT)
        );
        String name;
        Long userId;
        Long photoId;
        if(isCreator) {
            name = dates.get(position).getCreatorName();
            userId = dates.get(position).getCreatorId();
            photoId = dates.get(position).getCreatorPhotoId();

        } else {
            name = dates.get(position).getPartnerName();
            userId = dates.get(position).getPartnerId();
            photoId = dates.get(position).getPartnerPhotoId();
        }
        holder.tvName.setText(name);
        holder.tvPlace.setText(dates.get(position).getDate().getPlace());
        if(userId != null && photoId != null) {
            Picasso.with(context)
                    .load(StringUtils.getPhotoUrl(userId.intValue(), photoId.intValue()))
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.item_image_size), context.getResources().getDimensionPixelSize(R.dimen.item_image_size))
                    .centerCrop()
                    .into(holder.imgPhoto);
        } else {
            holder.imgPhoto.setImageResource(defaultIcon);
            holder.imgPhoto.setColorFilter(ContextCompat.getColor(context, defaultTint));
            holder.pbProgress.setVisibility(View.GONE);
        }
        holder.pbProgress.setIndicatorColor(context.getResources().getColor(defaultTint));

        holder.rlDate.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return dates == null ? 0 : dates.size();
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class DalesViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvPlace, tvTime, tvAmount, tvName;
        public CircleImageView imgPhoto;
        public AVLoadingIndicatorView pbProgress;
        public RelativeLayout rlDate;

        public DalesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvPlace = (TextView) itemView.findViewById(R.id.tv_place);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
            pbProgress = (AVLoadingIndicatorView) itemView.findViewById(R.id.pb_progress);
            rlDate = (RelativeLayout) itemView.findViewById(R.id.rl_date);
        }
    }
}
