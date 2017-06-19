package com.elatesoftware.meetings.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.util.api.pojo.GetPendingWomenAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 19.06.2017.
 */

public class PendingWomenAdapter extends RecyclerView.Adapter<PendingWomenAdapter.PendingWomenViewHolder> {

    private Context context;
    private GetPendingWomenAnswer pendingWomen;

    public PendingWomenAdapter(Context context, GetPendingWomenAnswer pendingWomen) {
        this.context = context;
        this.pendingWomen = pendingWomen;
    }

    @Override
    public PendingWomenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_woman, parent, false);
        return new PendingWomenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendingWomenViewHolder holder, int position) {
        holder.tvName.setText(pendingWomen.getResult().get(position).getAccount().getFirstName());
        Integer userId = pendingWomen.getResult().get(position).getAccountId();
        Integer photoId = pendingWomen.getResult().get(position).getPhotoId();
        if(userId != null && photoId != null){
            Picasso.with(context)
                    .load(StringUtils.getPhotoUrl(userId.intValue(), photoId.intValue()))
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.item_image_size), context.getResources().getDimensionPixelSize(R.dimen.item_image_size))
                    .centerCrop()
                    .into(holder.imgPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return pendingWomen.getResult() == null ? 0 : pendingWomen.getResult().size();
    }

    public class PendingWomenViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvName;
        public CircleImageView imgPhoto;
        public RelativeLayout rlDate;

        public PendingWomenViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
            rlDate = (RelativeLayout) itemView.findViewById(R.id.rl_date);
        }
    }
}
