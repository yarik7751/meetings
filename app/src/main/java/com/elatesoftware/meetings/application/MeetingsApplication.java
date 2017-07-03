package com.elatesoftware.meetings.application;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by user on 17.05.2017.
 */

public class MeetingsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeueCyr-Medium.otf")
                .build()
        );

    }
}
