package com.elatesoftware.meetings.ui.adapter.recycler_view.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.model.Message;
import com.google.firebase.database.DataSnapshot;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "ChatRVAdapter_logs";

    private Context context;
    private List<DataSnapshot> messages;

    public ChatRecyclerViewAdapter(Context context, Iterator<DataSnapshot> iterator) {
        this.context = context;
        this.messages = Utils.iteratorToList(iterator);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
        Message message = messages.get(position).getValue(Message.class);
        if(message != null) {
            chatViewHolder.tvMessage.setText(message.text);
            chatViewHolder.tvTime.setText(DateUtils.getDateByStr(new Date(message.date * 1000), DateUtils.DATE_FORMAT_OUTPUT));
            chatViewHolder.tvUser.setText(message.senderId + "");
            Log.d(TAG, "message.text: " + message.text);
            Log.d(TAG, "getId: " + CustomSharedPreference.getId(context) + ", message.senderId: " + message.senderId);
            if(message.senderId == CustomSharedPreference.getId(context)) {
                Log.d(TAG, "white");
                chatViewHolder.llMain.setBackgroundResource(R.color.white);
            } else {
                chatViewHolder.llMain.setBackgroundResource(R.color.background);
            }
            Log.d(TAG, "*************************************");
        }
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvMessage;
        public TextView tvUser;
        public TextView tvTime;
        public LinearLayout llMain;

        public ChatViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            //todo 31 убрать это
            ((RecyclerView.LayoutParams) itemView.getLayoutParams()).width = RecyclerView.LayoutParams.MATCH_PARENT;
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            tvUser = (TextView) itemView.findViewById(R.id.tv_user);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
        }
    }
}
