package com.elatesoftware.meetings.ui.adapter.dales;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.DaleDetailsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO test data
public class DalesRecyclerViewAdapter extends RecyclerView.Adapter<DalesRecyclerViewAdapter.DalesViewHolder> {

    public static final String TAG = "DalesRVAdapter_logs";

    Context context;

    public DalesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dales, parent, false);
        return new DalesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DalesViewHolder holder, final int position) {
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

    public class DalesViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public LinearLayout llMain;
        public FrameLayout flDelete;
        public TextView tvNameAndAge, tvPlace, tvTime, tvAmount;
        public CircleImageView imgPhoto;
        public SwipeLayout sl;

        public DalesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            flDelete = (FrameLayout) itemView.findViewById(R.id.fl_delete);
            tvNameAndAge = (TextView) itemView.findViewById(R.id.tv_name_and_age);
            tvPlace = (TextView) itemView.findViewById(R.id.tv_place);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
            sl = (SwipeLayout) itemView.findViewById(R.id.sl);
        }
    }
}
