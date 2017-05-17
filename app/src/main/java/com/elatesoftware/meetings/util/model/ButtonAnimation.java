package com.elatesoftware.meetings.util.model;

import android.content.Context;
import android.os.Handler;

import com.dd.CircularProgressButton;

/**
 * Created by user on 17.05.2017.
 */

public class ButtonAnimation {

    private final int INTERVAL = 50;

    private CircularProgressButton button;
    private Context context;

    private Handler handler;

    private int progressValue = 1;

    private Runnable runnableAnim = new Runnable() {
        @Override
        public void run() {
            button.setProgress(progressValue);
            progressValue++;
            if(progressValue > 99) {
                progressValue = 1;
            }
            handler.postDelayed(runnableAnim, INTERVAL);
        }
    };

    public ButtonAnimation(Context context, CircularProgressButton button) {
        this.context = context;
        this.button = button;
        handler = new Handler();
    }

    public void start() {
        handler.postDelayed(runnableAnim, INTERVAL);
    }

    public void stop() {
        handler.removeCallbacks(runnableAnim);
        button.setProgress(0);
    }

    public CircularProgressButton getButton() {
        return button;
    }

    public void setButton(CircularProgressButton button) {
        this.button = button;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
