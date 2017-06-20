package com.elatesoftware.meetings.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.recycler_view.chat.ChatRecyclerViewAdapter;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    public static final String TAG = "ChatActivity_logs";
    private final int INTERVAL = 9000;

    @BindView(R.id.et_message) EditText etMessage;
    @BindView(R.id.rv_chat) RecyclerView rvChat;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private int idMeeting = 0;
    private ChatRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvChat.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(idMeeting + "").child("messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");
                Iterator<DataSnapshot> children = dataSnapshot.getChildren().iterator();
                adapter = new ChatRecyclerViewAdapter(ChatActivity.this, children);
                rvChat.setAdapter(adapter);
                rvChat.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.btn_send)
    public void clickBtnSend() {
        reference.push().updateChildren(getMessage());
        etMessage.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Map<String, Object> getMessage() {
        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("text", etMessage.getText().toString());
        mapInfo.put("date", System.currentTimeMillis() / 1000);
        mapInfo.put("senderId", CustomSharedPreference.getId(this));

        return mapInfo;
    }
}
