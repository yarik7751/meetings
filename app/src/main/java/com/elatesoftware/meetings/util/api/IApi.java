package com.elatesoftware.meetings.util.api;

import com.elatesoftware.meetings.util.api.pojo.MessageAnswer;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {

    @POST("api/account/register")
    Call<ResponseBody> register(@Body RequestBody params);

    @POST("api/account/login")
    Call<ResponseBody> login(@Body RequestBody params);

    @POST("api/account/updateAccount")
    Call<ResponseBody> updateAccountInfo(@Query("sessionKey") String sessionKey, @Body RequestBody params);

    @GET("api/account/details")
    Call<ResponseBody> getAccountInfo(@Query("sessionKey") String sessionKey);

    @POST("api/dates/CreateDate")
    Call<ResponseBody> createDate(@Query("sessionKey") String sessionKey, @Body RequestBody params);

    @POST("api/account/addFiles")
    Call<ResponseBody> addPhoto(@Query("sessionKey") String sessionKey, @Body RequestBody params);

    @GET("api/account/photos")
    Call<ResponseBody> getPhotos(@Query("sessionKey") String sessionKey);

    @GET("api/account/photoContent")
    Call<ResponseBody> getPhoto(@Query("sessionKey") String sessionKey, @Query("photoId") int photoId);


}
