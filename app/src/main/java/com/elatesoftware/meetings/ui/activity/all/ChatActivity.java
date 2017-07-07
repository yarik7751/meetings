package com.elatesoftware.meetings.ui.activity.all;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.pojo.ChatMessage;
import com.elatesoftware.meetings.ui.adapter.recycler_view.chat.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.rv_chat) RecyclerView rvChat;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvChat.setLayoutManager(new LinearLayoutManager(this));
        ChatAdapter adapter = new ChatAdapter(this, getTestMessage(), 1);
        rvChat.setAdapter(adapter);
    }

    //todo test
    private List<ChatMessage> getTestMessage() {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(0, "qwe1", 123L, 0, 1));
        messages.add(new ChatMessage(0, "qwe2", 123L, 0, 1));
        messages.add(new ChatMessage(0, "qwe3", 123L, 0, 1));
        messages.add(new ChatMessage(0, "qwe4", 123L, 1, 0));
        messages.add(new ChatMessage(0, "qwe6", 123L, 0, 1));
        messages.add(new ChatMessage(0, "qwe7", 123L, 0, 1));
        return messages;
    }
}
