package com.elatesoftware.meetings.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.google.gson.Gson;

public class CustomSharedPreference {

    public static final String TAG = "CS_Preference_logs";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Const.SHARED_PREFERENCES_TITLE, Context.MODE_PRIVATE);
    }

    public static void setIsMan(Context context, boolean isMan) {
        getSharedPreferences(context).edit().putInt(Const.SP_IS_MAN, isMan ? Const.MAN_VALUE : Const.WOMAN_VALUE).apply();
    }

    public static int getIsMan(Context context) {
        return getSharedPreferences(context).getInt(Const.SP_IS_MAN, -1);
    }

    public static void setIsFirst(Context context, boolean isFirst) {
        getSharedPreferences(context).edit().putBoolean(Const.SP_IS_FIRST, isFirst).apply();
    }

    public static boolean isFirst(Context context) {
        return getSharedPreferences(context).getBoolean(Const.SP_IS_FIRST, true);
    }

    public static void setId(Context context, long id) {
        getSharedPreferences(context).edit().putLong(Const.SP_ID, id).apply();
    }

    public static long getId(Context context) {
        return getSharedPreferences(context).getLong(Const.SP_ID, -1);
    }

    public static void setPin(Context context, String pin) {
        getSharedPreferences(context).edit().putString(Const.SP_PIN, pin).apply();
    }

    public static String getPin(Context context) {
        return getSharedPreferences(context).getString(Const.SP_PIN, null);
    }

    public static void setToken(Context context, String token) {
        getSharedPreferences(context).edit().putString(Const.SP_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(Const.SP_TOKEN, null);
    }

    public static void setProfileInformation(Context context, HumanAnswer infoMan) {
        Gson gson = new Gson();
        String info = gson.toJson(infoMan);
        Log.d(TAG, "info (set): " + info);
        getSharedPreferences(context).edit().putString(Const.SP_PROFILE_INFORMATION, info).apply();
    }

    public static HumanAnswer getProfileInformation(Context context) {
        Gson gson = new Gson();
        String info = getSharedPreferences(context).getString(Const.SP_PROFILE_INFORMATION, null);
        Log.d(TAG, "info (get): " + info);
        if(TextUtils.isEmpty(info)) {
            return null;
        } else {
            HumanAnswer infoMan = gson.fromJson(info, HumanAnswer.class);
            return infoMan;
        }
    }
}
