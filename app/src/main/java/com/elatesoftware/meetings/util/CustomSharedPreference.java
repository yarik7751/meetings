package com.elatesoftware.meetings.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.api.pojo.HumanAnswer;
import com.google.gson.Gson;

public class CustomSharedPreference {

    public static final String TAG = "CS_Preference_logs";
    public static final String SHARED_PREFERENCES_TITLE = "sp_currency_by_yarik";
    public static final String SP_IS_MAN = "SP_IS_MAN";
    public static final String SP_IS_FIRST = "SP_IS_FIRST";
    public static final String SP_ID = "SP_ID";
    public static final String SP_TOKEN = "SP_TOKEN";
    public static final String SP_PIN = "SP_PIN";
    public static final String SP_WOMAN_INFORMATION = "SP_WOMAN_INFORMATION";
    public static final String SP_MAN_INFORMATION = "SP_MAN_INFORMATION";
    public static final String SP_PROFILE_INFORMATION = "SP_PROFILE_INFORMATION";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_TITLE, Context.MODE_PRIVATE);
    }

    public static void setIsMan(Context context, boolean isMan) {
        getSharedPreferences(context).edit().putInt(SP_IS_MAN, isMan ? Api.MAN_VALUE : Api.WOMAN_VALUE).apply();
    }

    public static int getIsMan(Context context) {
        return getSharedPreferences(context).getInt(SP_IS_MAN, -1);
    }

    public static void setIsFirst(Context context, boolean isFirst) {
        getSharedPreferences(context).edit().putBoolean(SP_IS_FIRST, isFirst).apply();
    }

    public static boolean isFirst(Context context) {
        return getSharedPreferences(context).getBoolean(SP_IS_FIRST, true);
    }

    public static void setId(Context context, long id) {
        getSharedPreferences(context).edit().putLong(SP_ID, id).apply();
    }

    public static long getId(Context context) {
        return getSharedPreferences(context).getLong(SP_ID, -1);
    }

    public static void setPin(Context context, String pin) {
        getSharedPreferences(context).edit().putString(SP_PIN, pin).apply();
    }

    public static String getPin(Context context) {
        return getSharedPreferences(context).getString(SP_PIN, null);
    }

    public static void setToken(Context context, String token) {
        getSharedPreferences(context).edit().putString(SP_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(SP_TOKEN, null);
    }

    public static boolean isToken(Context context) {
        String token = CustomSharedPreference.getToken(context);
        return !TextUtils.isEmpty(token);
    }

    public static void setProfileInformation(Context context, HumanAnswer infoMan) {
        Gson gson = new Gson();
        String info = gson.toJson(infoMan);
        Log.d(TAG, "info (set): " + info);
        getSharedPreferences(context).edit().putString(SP_PROFILE_INFORMATION, info).apply();
    }

    public static HumanAnswer getProfileInformation(Context context) {
        Gson gson = new Gson();
        String info = getSharedPreferences(context).getString(SP_PROFILE_INFORMATION, null);
        Log.d(TAG, "info (get): " + info);
        if(TextUtils.isEmpty(info)) {
            return null;
        } else {
            HumanAnswer infoMan = gson.fromJson(info, HumanAnswer.class);
            return infoMan;
        }
    }
}
