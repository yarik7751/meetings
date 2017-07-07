package com.elatesoftware.meetings.ui.adapter.recycler_view.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.pojo.ChatMessage;
import com.elatesoftware.meetings.util.DateUtils;

import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessageBaseViewHolder> {

    public static final int INTERVAL = 5;

    private static final int VIEW_TYPE_MESSAGE_MY = 1;
    private static final int VIEW_TYPE_MESSAGE_HIS_LAST = 2;
    private static final int VIEW_TYPE_MESSAGE_MY_LAST = 3;
    private static final int VIEW_TYPE_MESSAGE_HIS = 4;

    private Context context;
    private List<ChatMessage> messages;
    private long hisId;

    public ChatAdapter(Context context, List<ChatMessage> messages, long hisId) {
        this.context = context;
        this.messages = messages;
        this.hisId = hisId;
    }

    @Override
    public ChatMessageBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_MESSAGE_MY_LAST:
                return new ChatMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_my_first, parent, false));
            case VIEW_TYPE_MESSAGE_HIS_LAST:
                return new ChatMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_his_first, parent, false));
            case VIEW_TYPE_MESSAGE_MY:
                return new ChatMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_my, parent, false));
            case VIEW_TYPE_MESSAGE_HIS:
                return new ChatMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_his, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        ChatMessage nextMessage = position == messages.size() - 1 ? null : messages.get(position + 1);
        if(nextMessage == null) {
            if(hisId == message.getSenderId()) {
                return VIEW_TYPE_MESSAGE_HIS;
            } else {
                return VIEW_TYPE_MESSAGE_MY;
            }
        } else {
            if(nextMessage.getSenderId() == message.getSenderId()) {
                if(hisId == message.getSenderId()) {
                    return VIEW_TYPE_MESSAGE_HIS_LAST;
                } else {
                    return VIEW_TYPE_MESSAGE_MY_LAST;
                }
            } else {
                if(hisId == message.getSenderId()) {
                    return VIEW_TYPE_MESSAGE_HIS;
                } else {
                    return VIEW_TYPE_MESSAGE_MY;
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(ChatMessageBaseViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.setIsRecyclable(false);
        ((ChatMessageViewHolder) holder).tvChatMessage.setText(message.getText());
        ((ChatMessageViewHolder) holder).tvChatDate.setText(DateUtils.getDateByStr(new Date(message.getDateTime() * 1000), DateUtils.DATE_FORMAT_OUTPUT));
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_MY_LAST:
                break;

            case VIEW_TYPE_MESSAGE_HIS_LAST:

                break;

            case VIEW_TYPE_MESSAGE_MY:

                break;

            case VIEW_TYPE_MESSAGE_HIS:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    static class ChatMessageBaseViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        public ChatMessageBaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    static class ChatMessageViewHolder extends ChatMessageBaseViewHolder {

        public TextView tvChatMessage;
        public TextView tvChatDate;
        public RelativeLayout rlChatMessage;

        public ChatMessageViewHolder(View itemView) {
            super(itemView);
            tvChatMessage = (TextView) itemView.findViewById(R.id.tv_chat_message);
            rlChatMessage = (RelativeLayout) itemView.findViewById(R.id.rl_chat_message);
            tvChatDate = (TextView) itemView.findViewById(R.id.tv_chat_date);
        }
    }
}
