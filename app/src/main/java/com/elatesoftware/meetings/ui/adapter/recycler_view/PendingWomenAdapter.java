package com.elatesoftware.meetings.ui.adapter.recycler_view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.ShowProfileActivity;
import com.elatesoftware.meetings.ui.activity.man.ShowPendingWomenActivity;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.api.pojo.GetPendingWomenAnswer;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PendingWomenAdapter extends RecyclerView.Adapter<PendingWomenAdapter.PendingWomenViewHolder> {

    private Context context;
    private GetPendingWomenAnswer pendingWomen;
    private int dateId;

    public PendingWomenAdapter(Context context, GetPendingWomenAnswer pendingWomen, int dateId) {
        this.context = context;
        this.pendingWomen = pendingWomen;
        this.dateId = dateId;
    }

    @Override
    public PendingWomenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_woman, parent, false);
        return new PendingWomenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendingWomenViewHolder holder, final int position) {
        holder.tvName.setText(pendingWomen.getResult().get(position).getAccount().getFirstName());
        Integer userId = pendingWomen.getResult().get(position).getAccountId();
        Integer photoId = pendingWomen.getResult().get(position).getPhotoId();
        if(userId != null && photoId != null){
            Picasso.with(context)
                    .load(StringUtils.getPhotoUrl(userId.intValue(), photoId.intValue()))
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.pending_girl_image_size), context.getResources().getDimensionPixelSize(R.dimen.pending_girl_image_size))
                    .centerCrop()
                    .into(holder.imgPhoto);
        } else {
            holder.imgPhoto.setImageResource(R.drawable.ic_girl);
            holder.imgPhoto.setColorFilter(context.getResources().getColor(R.color.button_blue_light));
        }
        holder.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).startActivityForResult(ShowProfileActivity.getIntent(context, pendingWomen.getResult().get(position).getAccount(), dateId), ShowPendingWomenActivity.CLOSE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendingWomen.getResult() == null ? 0 : pendingWomen.getResult().size();
    }

    public class PendingWomenViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvName;
        public CircleImageView imgPhoto;
        public LinearLayout llProfile;

        public PendingWomenViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
            llProfile = (LinearLayout) itemView.findViewById(R.id.ll_profile);
        }
    }
}
