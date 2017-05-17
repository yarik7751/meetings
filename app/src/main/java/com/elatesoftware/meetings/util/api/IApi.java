package com.elatesoftware.meetings.util.api;

import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IApi {

    @POST("api/account/register")
    Call<ResponseBody> register(@Body RequestBody params);

    @POST("api/account/login")
    Call<ResponseBody> login(@Body RequestBody params);
}
