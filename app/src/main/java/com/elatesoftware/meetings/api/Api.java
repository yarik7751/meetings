package com.elatesoftware.meetings.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.api.pojo.GetDatesManAnswer;
import com.elatesoftware.meetings.api.pojo.GetInfoAccAnswer;
import com.elatesoftware.meetings.api.pojo.GetPendingWomenAnswer;
import com.elatesoftware.meetings.api.pojo.GetPhotoAnswer;
import com.elatesoftware.meetings.api.pojo.GetPhotosAnswer;
import com.elatesoftware.meetings.api.pojo.GetProfileInfoAnswer;
import com.elatesoftware.meetings.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.api.pojo.LoginAnswer;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.elatesoftware.meetings.api.pojo.MessageAnswer;
import com.elatesoftware.meetings.api.pojo.SearchDatesAnswer;
import com.elatesoftware.meetings.model.params.SearchDatesFilter;
import com.elatesoftware.meetings.model.params.SelectPartnerParams;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    public static final String RESPONSE = "response";
    public static final int MAN_VALUE = 1;
    public static final int WOMAN_VALUE = 2;
    public static final int CODE_SUCCESS = 200;
    
    //date status
    public static final int PENDING = 1;
    public static final int SCHEDULED = 2;

    private static IApi iApi = null;

    public static final String BASE_URL = "http://dateportal.elatesof.w07.hoster.by/";
    //public static final String BASE_URL = "http://192.168.100.7:3000/";

    public static IApi getApi() {
        if(iApi == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
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

    public static LoginAnswer register(String userName, String password, int gender, String token) {
        JSONObject object = new JSONObject();
        try {
            object.put("Username", userName);
            object.put("Password", password);
            object.put("Gender", gender);
            object.put("Token", token);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object.toString());

            Log.d(TAG, "object.toString: " + object.toString());

            Call<ResponseBody> call = getApi().register(body);
            Response<ResponseBody> response = null;
            String result = null;
            String rawJson = null;
            try {
                response = call.execute();
                if(response != null && response.body() != null) {
                    rawJson = response.body().string();
                    rawJson = rawJson.replace("\\", "");
                    //rawJson = rawJson.substring(1, rawJson.length() - 1);
                    Log.d(TAG, "register: " + rawJson);
                } else {
                    Log.d(TAG, "register: null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null && rawJson != null){
                if(response.code() == CODE_SUCCESS) {
                    Gson gson = new Gson();
                    LoginAnswer messageAnswer = gson.fromJson(rawJson, LoginAnswer.class);
                    return messageAnswer;
                }
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LoginAnswer login(String userName, String password, String token) {
        JSONObject object = new JSONObject();
        try {
            object.put("Username", userName);
            object.put("Password", password);
            object.put("Token", token);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object.toString());

            Log.d(TAG, "object.toString: " + object.toString());

            Call<ResponseBody> call = getApi().login(body);
            Response<ResponseBody> response = null;
            String result = null;
            String rawJson = null;
            try {
                response = call.execute();
                if(response != null && response.body() != null) {
                    rawJson = response.body().string();
                    rawJson = rawJson.replace("\\", "");
                    Log.d(TAG, "login: " + rawJson);
                } else {
                    Log.d(TAG, "login: null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null && rawJson != null){
                if(response.code() == CODE_SUCCESS) {
                    Gson gson = new Gson();
                    LoginAnswer messageAnswer = gson.fromJson(rawJson, LoginAnswer.class);
                    return messageAnswer;
                }
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MessageAnswer updateAccountInfo(String sessionKey, HumanAnswer profile) {
        Gson gson = new Gson();
        String profileStr = gson.toJson(profile, HumanAnswer.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), profileStr);

        Log.d(TAG, "object.toString: " + profileStr);

        Call<ResponseBody> call = getApi().updateAccountInfo(sessionKey, body);
        Response<ResponseBody> response = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "updateAccountInfo: " + rawJson);
            } else {
                Log.d(TAG, "updateAccountInfo: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                MessageAnswer messageAnswer = gson.fromJson(rawJson, MessageAnswer.class);
                return messageAnswer;
            }
        }
        return null;
    }

    public static GetInfoAccAnswer getAccountInfo(String sessionKey) {
        Call<ResponseBody> call = getApi().getAccountInfo(sessionKey);
        Response<ResponseBody> response = null;
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
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                GetInfoAccAnswer answer = gson.fromJson(rawJson, GetInfoAccAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static MessageAnswer createDate(String sessionKey, Meeting meeting) {
        Gson gson = new Gson();
        String meetingStr = gson.toJson(meeting, Meeting.class);
        Log.d(TAG, "meetingStr: " + meetingStr);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), meetingStr);
        Call<ResponseBody> call = getApi().createDate(sessionKey, body);
        Response<ResponseBody> response = null;
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
            if(response.code() == CODE_SUCCESS) {
                MessageAnswer messageAnswer = gson.fromJson(rawJson, MessageAnswer.class);
                return messageAnswer;
            }
        }
        return null;
    }

    public static GetPhotosAnswer getPhotos(String sessionKey) {
        Call<ResponseBody> call = getApi().getPhotos(sessionKey);
        Response<ResponseBody> response = null;
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
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                GetPhotosAnswer answer = gson.fromJson(rawJson, GetPhotosAnswer.class);
                return answer;
            }
        }
        return null;
    }

    //todo The method have not use
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
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                GetPhotoAnswer answer = gson.fromJson(rawJson, GetPhotoAnswer.class);
                GetPhotoAnswer.setInstance(answer);
            }
            result = String.valueOf(response.code());
        }
        return result;
    }
    //

    public static MessageAnswer deletePhoto(String sessionKey, long photoId) {
        String bodyStr = "{\"Id\":" + photoId + "}";
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), bodyStr);
        Call<ResponseBody> call = getApi().deletePhoto(sessionKey, body);
        Response<ResponseBody> response = null;
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
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static GetDatesManAnswer getDatesList(String sessionKey) {
        Log.d(TAG, "getDatesList");
        Call<ResponseBody> call = getApi().getDatesList(sessionKey);
        Response<ResponseBody> response = null;
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
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                GetDatesManAnswer answer = gson.fromJson(rawJson, GetDatesManAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static MessageAnswer exit(String sessionKey) {
        Log.d(TAG, "exit");
        Call<ResponseBody> call = getApi().exit(sessionKey);
        Response<ResponseBody> response = null;
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
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static SearchDatesAnswer searchDates(String sessionKey, SearchDatesFilter searchDatesFilterParams) {
        Log.d(TAG, "searchDatesStr");
        Log.d(TAG, "request: " + new Gson().toJson(searchDatesFilterParams, SearchDatesFilter.class));
        //todo удалить лишнее гсон
        Gson gson = new Gson();
        Call<ResponseBody> call = getApi().searchDates(sessionKey, searchDatesFilterParams.getAmountStart(), searchDatesFilterParams.getStartTime(), searchDatesFilterParams.getPage());
        Response<ResponseBody> response = null;
        String rawJson = null;
        //todo сделать аккуратнее ifЫ
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "responseBody: " + rawJson);
            } else {
                Log.d(TAG, "responseBody: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo переместить response
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                return gson.fromJson(rawJson, SearchDatesAnswer.class);
            }
        }
        return null;
    }

    public static GetProfileInfoAnswer getProfileInfo(String sessionKey, long id) {
        Log.d(TAG, "getProfileInfo");
        Call<ResponseBody> call = getApi().getProfileInfo(sessionKey, id);
        Response<ResponseBody> response = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "response: " + rawJson);
            } else {
                Log.d(TAG, "response: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                GetProfileInfoAnswer answer = gson.fromJson(rawJson, GetProfileInfoAnswer .class);
                return answer;
            }
        }
        return null;
    }

    public static MessageAnswer addPartner(String sessionKey, long dateId) {
        Log.d(TAG, "addPartner");
        Call<ResponseBody> call = getApi().addPartner(sessionKey, dateId);
        Response<ResponseBody> response = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "response: " + rawJson);
            } else {
                Log.d(TAG, "response: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static GetPendingWomenAnswer getDatePartners(String sessionKey, long dateId) {
        Log.d(TAG, "getDatePartners");
        Call<ResponseBody> call = getApi().getDatePartners(sessionKey, dateId);
        Response<ResponseBody> response = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "response: " + rawJson);
            } else {
                Log.d(TAG, "response: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                GetPendingWomenAnswer answer = gson.fromJson(rawJson, GetPendingWomenAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static MessageAnswer selectPartner(String sessionKey, SelectPartnerParams selectPartnerParams) {
        Log.d(TAG, "searchDatesStr");
        Gson gson = new Gson();
        String paramsStr = gson.toJson(selectPartnerParams, SelectPartnerParams.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsStr );
        Log.d(TAG, "request: " + paramsStr );
        Call<ResponseBody> call = getApi().selectPartner(sessionKey, body);
        Response<ResponseBody> response = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "response: " + rawJson);
            } else {
                Log.d(TAG, "response: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                return answer;
            }
        }
        return null;
    }

    public static MessageAnswer uploadFile(String sessionKey, String path) {
        Log.d(TAG, "uploadFile");
        File file = new File(path);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        Call<ResponseBody> call = getApi().uploadAttachment(sessionKey, filePart);
        Response<ResponseBody> response = null;
        String rawJson = null;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                rawJson = rawJson.replace("\\", "");
                Log.d(TAG, "response: " + rawJson);
            } else {
                Log.d(TAG, "response: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null && rawJson != null){
            if(response.code() == CODE_SUCCESS) {
                Gson gson = new Gson();
                MessageAnswer answer = gson.fromJson(rawJson, MessageAnswer.class);
                return answer;
            }
        }
        return null;
    }
}
