package com.elatesoftware.meetings.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.elatesoftware.meetings.util.model.ProfileMan;
import com.elatesoftware.meetings.util.model.ProfileWoman;
import com.google.gson.Gson;

public class CustomSharedPreference {

    public static final String TAG = "CS_Preference_logs";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Const.SHARED_PREFERENCES_TITLE, Context.MODE_PRIVATE);
    }

    public static void setIsMan(Context context, boolean isMan) {
        getSharedPreferences(context).edit().putBoolean(Const.SP_IS_MAN, isMan).commit();
    }

    public static boolean isMan(Context context) {
        return getSharedPreferences(context).getBoolean(Const.SP_IS_MAN, false);
    }

    public static void setId(Context context, int id) {
        getSharedPreferences(context).edit().putInt(Const.SP_ID, id).commit();
    }

    public static int getId(Context context) {
        return getSharedPreferences(context).getInt(Const.SP_ID, -1);
    }

    public static void setWomanInformation(Context context, ProfileWoman infoWoman) {
        Gson gson = new Gson();
        String info = gson.toJson(infoWoman);
        Log.d(TAG, "info (set): " + info);
        getSharedPreferences(context).edit().putString(Const.SP_WOMAN_INFORMATION, info).commit();
    }

    public static ProfileWoman getWomanInformation(Context context) {
        Gson gson = new Gson();
        String info = getSharedPreferences(context).getString(Const.SP_WOMAN_INFORMATION, null);
        Log.d(TAG, "info (get): " + info);
        ProfileWoman infoWoman = gson.fromJson(info, ProfileWoman.class);
        return infoWoman;
    }

    public static void setManInformation(Context context, ProfileMan infoMan) {
        Gson gson = new Gson();
        String info = gson.toJson(infoMan);
        Log.d(TAG, "info (set): " + info);
        getSharedPreferences(context).edit().putString(Const.SP_MAN_INFORMATION, info).commit();
    }

    public static ProfileMan getManInformation(Context context) {
        Gson gson = new Gson();
        String info = getSharedPreferences(context).getString(Const.SP_MAN_INFORMATION, null);
        Log.d(TAG, "info (get): " + info);
        ProfileMan infoMan = gson.fromJson(info, ProfileMan.class);
        return infoMan;
    }
}
