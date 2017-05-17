package com.elatesoftware.meetings.util.api;

import android.util.Log;

import com.elatesoftware.meetings.ui.application.MeetingsApplication;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String TAG = "Api_logs";

    private static IApi iApi = null;

    private static final String BASE_URL = "http://dateportal.elatesof.w07.hoster.by/";

    public static IApi getApi() {
        if(iApi == null) {
            CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MeetingsApplication.getAppContext()));
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            iApi = retrofit.create(IApi.class);
            return iApi;
        } else {
            return iApi;
        }
    }

    public static String register(String userName, String password, int gender) {
        JSONObject object = new JSONObject();
        try {
            object.put("Username", userName);
            object.put("Password", password);
            object.put("Gender", gender);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object.toString());

            Log.d(TAG, "object.toString: " + object.toString());

            Call<ResponseBody> call = getApi().register(body);
            Response<ResponseBody> response = null;
            String result = null;
            String rawJson = null;
            try {
                response = call.execute();
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                rawJson = rawJson.substring(1, rawJson.length() - 1);
                Log.d(TAG, "register: " + rawJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null && rawJson != null){
                if(response.code() == Const.CODE_SUCCESS) {
                    Gson gson = new Gson();
                    MessageAnswer messageAnswer = gson.fromJson(rawJson, MessageAnswer.class);
                    MessageAnswer.setInstance(messageAnswer);
                }
                result = String.valueOf(response.code());
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String login(String userName, String password) {
        JSONObject object = new JSONObject();
        try {
            object.put("Username", userName);
            object.put("Password", password);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object.toString());

            Log.d(TAG, "object.toString: " + object.toString());

            Call<ResponseBody> call = getApi().login(body);
            Response<ResponseBody> response = null;
            String result = null;
            String rawJson = null;
            try {
                response = call.execute();
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                //rawJson = rawJson.substring(1, rawJson.length() - 1);
                Log.d(TAG, "login: " + rawJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null && rawJson != null){
                if(response.code() == Const.CODE_SUCCESS) {
                    Gson gson = new Gson();
                    LoginAnswer messageAnswer = gson.fromJson(rawJson, LoginAnswer.class);
                    LoginAnswer.setInstance(messageAnswer);
                }
                result = String.valueOf(response.code());
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
