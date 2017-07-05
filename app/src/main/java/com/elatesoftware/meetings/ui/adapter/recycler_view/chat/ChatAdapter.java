package com.elatesoftware.meetings.ui.adapter.recycler_view.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessage> {

    static final int VIEW_TYPE_MESSAGE_MY = 0;
    static final int VIEW_TYPE_MESSAGE_HIS = 1;
    static final int VIEW_TYPE_TIME_MY = 2;
    static final int VIEW_TYPE_TIME_HIS = 3;

    private Context context;
    private List<ChatMessage> messages;

    public ChatAdapter(Context context, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public ChatMessage onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_MESSAGE_MY:
                return new MyChatMessage(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_my, parent, false));
            case VIEW_TYPE_MESSAGE_HIS:
                return new HisChatMessage(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_his, parent, false));
            case VIEW_TYPE_TIME_MY:
                return new MyChatDate(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_my, parent, false));
            case VIEW_TYPE_TIME_HIS:
                return new HisChatDate(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_his, parent, false));
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(ChatMessage holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ChatMessage extends RecyclerView.ViewHolder {

        public View itemView;

        public ChatMessage(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    static class MyChatMessage extends ChatMessage {

        public TextView tvChatMessage;
        public RelativeLayout rlChatMessage;

        public MyChatMessage(View itemView) {
            super(itemView);
            tvChatMessage = (TextView) itemView.findViewById(R.id.tv_chat_message);
            rlChatMessage = (RelativeLayout) itemView.findViewById(R.id.rl_chat_message);
        }
    }

    static class HisChatMessage extends ChatMessage {

        public TextView tvChatMessage;
        public RelativeLayout rlChatMessage;

        public HisChatMessage(View itemView) {
            super(itemView);
            tvChatMessage = (TextView) itemView.findViewById(R.id.tv_chat_message);
            rlChatMessage = (RelativeLayout) itemView.findViewById(R.id.rl_chat_message);
        }
    }

    static class MyChatDate extends ChatMessage {

        public TextView tvChatDate;

        public MyChatDate(View itemView) {
            super(itemView);
            tvChatDate = (TextView) itemView.findViewById(R.id.tv_chat_date);
        }
    }

    static class HisChatDate extends ChatMessage {

        public TextView tvChatDate;

        public HisChatDate(View itemView) {
            super(itemView);
            tvChatDate = (TextView) itemView.findViewById(R.id.tv_chat_date);
        }
    }
}
