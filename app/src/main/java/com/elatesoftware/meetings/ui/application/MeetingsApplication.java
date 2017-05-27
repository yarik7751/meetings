package com.elatesoftware.meetings.ui.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 17.05.2017.
 */

public class MeetingsApplication extends Application {

    //todo 12 убрать статіческій контекст
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
