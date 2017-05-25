package com.elatesoftware.meetings.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
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

    public static void setIsFirst(Context context, boolean isFirst) {
        getSharedPreferences(context).edit().putBoolean(Const.SP_IS_FIRST, isFirst).commit();
    }

    public static boolean isFirst(Context context) {
        return getSharedPreferences(context).getBoolean(Const.SP_IS_FIRST, true);
    }

    public static void setId(Context context, long id) {
        getSharedPreferences(context).edit().putLong(Const.SP_ID, id).commit();
    }

    public static long getId(Context context) {
        return getSharedPreferences(context).getLong(Const.SP_ID, -1);
    }

    public static void setPin(Context context, String pin) {
        getSharedPreferences(context).edit().putString(Const.SP_PIN, pin).commit();
    }

    public static String getPin(Context context) {
        return getSharedPreferences(context).getString(Const.SP_PIN, null);
    }

    public static void setToken(Context context, String token) {
        getSharedPreferences(context).edit().putString(Const.SP_TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(Const.SP_TOKEN, Const.NULL_TOKEN);
    }

    public static void setProfileInformation(Context context, HumanAnswer infoMan) {
        Gson gson = new Gson();
        String info = gson.toJson(infoMan);
        Log.d(TAG, "info (set): " + info);
        getSharedPreferences(context).edit().putString(Const.SP_PROFILE_INFORMATION, info).commit();
    }

    public static HumanAnswer getProfileInformation(Context context) {
        Gson gson = new Gson();
        String info = getSharedPreferences(context).getString(Const.SP_PROFILE_INFORMATION, null);
        Log.d(TAG, "info (get): " + info);
        if(info == null) {
            return null;
        } else {
            HumanAnswer infoMan = gson.fromJson(info, HumanAnswer.class);
            return infoMan;
        }
    }
}
