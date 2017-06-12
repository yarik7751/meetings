package com.elatesoftware.meetings.util.api;

import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.api.pojo.GetDatesManAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetInfoAccAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetPhotoAnswer;
import com.elatesoftware.meetings.util.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.util.api.pojo.Meeting;
import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.util.api.pojo.RegisterAnswer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String TAG = "Api_logs";

    private static IApi iApi = null;

    public static final String BASE_URL = "http://dateportal.elatesof.w07.hoster.by/";
    //public static final String BASE_URL = "http://192.168.100.29:3000/";

    public static IApi getApi() {
        if(iApi == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
                //rawJson = rawJson.substring(1, rawJson.length() - 1);
                Log.d(TAG, "register: " + rawJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null && rawJson != null){
                if(response.code() == Const.CODE_SUCCESS) {
                    Gson gson = new Gson();
                    RegisterAnswer messageAnswer = gson.fromJson(rawJson, RegisterAnswer.class);
                    RegisterAnswer.setInstance(messageAnswer);
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

    public static String updateAccountInfo(String sessionKey, HumanAnswer profile) {
        Gson gson = new Gson();
        String profileStr = gson.toJson(profile, HumanAnswer.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), profileStr);

        Log.d(TAG, "object.toString: " + profileStr);

        Call<ResponseBody> call = getApi().updateAccountInfo(sessionKey, body);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            rawJson = response.body().string();
            rawJson = rawJson.replace("\\", "");
            //rawJson = rawJson.substring(1, rawJson.length() - 1);
            Log.d(TAG, "updateAccountInfo: " + rawJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                MessageAnswer messageAnswer = gson.fromJson(rawJson, MessageAnswer.class);
                MessageAnswer.setInstance(messageAnswer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String getAccountInfo(String sessionKey) {
        Call<ResponseBody> call = getApi().getAccountInfo(sessionKey);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            rawJson = response.body().string();
            rawJson = rawJson.replace("\\", "");
            Log.d(TAG, "getAccountInfo: " + rawJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                Gson gson = new Gson();
                GetInfoAccAnswer answer = gson.fromJson(rawJson, GetInfoAccAnswer.class);
                GetInfoAccAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String createDate(String sessionKey, Meeting meeting) {
        Gson gson = new Gson();
        String meetingStr = gson.toJson(meeting, Meeting.class);
        Log.d(TAG, "meetingStr: " + meetingStr);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), meetingStr);
        Call<ResponseBody> call = getApi().createDate(sessionKey, body);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "createDate: " + rawJson);
            } else {
                Log.d(TAG, "createDate: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                MessageAnswer messageAnswer = gson.fromJson(rawJson, MessageAnswer.class);
                MessageAnswer.setInstance(messageAnswer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String addPhoto(String sessionKey, String base64) {
        Log.d(TAG, "addPhoto");
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        try {
            object.put("content", base64);
            array.put(object);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), array.toString());

            Log.d(TAG, "array.toString: " + array.toString());

            Call<ResponseBody> call = getApi().addPhoto(sessionKey, body);
            Response<ResponseBody> response = null;
            String result = null;
            String rawJson = null;
            try {
                response = call.execute();
                if(response != null && response.body() != null) {
                    rawJson = response.body().string();
                    Log.d(TAG, "addPhoto: " + rawJson);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null){
                if(response.code() == Const.CODE_SUCCESS) {
                    Gson gson = new Gson();
                    MessageAnswer messageAnswer = gson.fromJson(rawJson, MessageAnswer.class);
                    MessageAnswer.setInstance(messageAnswer);
                }
                result = String.valueOf(response.code());
            }
            Log.d(TAG, "addPhoto result: " + result);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPhotos(String sessionKey) {
        Call<ResponseBody> call = getApi().getPhotos(sessionKey);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null){
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "getPhotos: " + rawJson);
            } else {
                Log.d(TAG, "getPhotos: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                Gson gson = new Gson();
                GetPhotosAnswer answer = gson.fromJson(rawJson, GetPhotosAnswer.class);
                GetPhotosAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String getPhoto(String sessionKey, long photoId) {
        Call<ResponseBody> call = getApi().getPhoto(sessionKey, photoId);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            rawJson = response.body().string();
            rawJson = rawJson.replace("\\", "");
            Log.d(TAG, "getPhotO: " + rawJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                Gson gson = new Gson();
                GetPhotoAnswer answer = gson.fromJson(rawJson, GetPhotoAnswer.class);
                GetPhotoAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String deletePhoto(String sessionKey, long photoId) {
        String bodyStr = "{\"Id\":" + photoId + "}";
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), bodyStr);
        Call<ResponseBody> call = getApi().deletePhoto(sessionKey, body);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            rawJson = response.body().string();
            rawJson = rawJson.replace("\\", "");
            Log.d(TAG, "deletePhoto: " + rawJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                Gson gson = new Gson();
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                MessageAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String getDatesList(String sessionKey) {
        Log.d(TAG, "getDatesList");
        Call<ResponseBody> call = getApi().getDatesList(sessionKey);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "rawJson: " + rawJson);
            } else {
                Log.d(TAG, "rawJson: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                Gson gson = new Gson();
                GetDatesManAnswer answer = gson.fromJson(rawJson, GetDatesManAnswer.class);
                GetDatesManAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }

    public static String exit(String sessionKey) {
        Log.d(TAG, "exit");
        Call<ResponseBody> call = getApi().exit(sessionKey);
        Response<ResponseBody> response = null;
        String result = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "rawJson: " + rawJson);
            } else {
                Log.d(TAG, "rawJson: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == Const.CODE_SUCCESS) {
                Gson gson = new Gson();
                //todo 29 Плохо. Поговорить с Дашей
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                MessageAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }
}
