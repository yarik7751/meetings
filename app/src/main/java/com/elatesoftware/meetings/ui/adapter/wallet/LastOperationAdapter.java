package com.elatesoftware.meetings.ui.adapter.wallet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.elatesoftware.meetings.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LastOperationAdapter extends RecyclerView.Adapter<LastOperationAdapter.LastOperationViewHolder> {

    public static final String TAG = "DalesRVAdapter_logs";

    Context context;

    public LastOperationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LastOperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_last_operation, parent, false);
        return new LastOperationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LastOperationViewHolder holder, final int position) {
        /*holder.sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DaleDetailsActivity.class);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class LastOperationViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvPlace, tvName, tvAmount;
        public CircleImageView imgPhoto;

        public LastOperationViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvPlace = (TextView) itemView.findViewById(R.id.tv_place);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
        }
    }
}
