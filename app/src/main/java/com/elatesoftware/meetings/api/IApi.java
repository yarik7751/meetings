package com.elatesoftware.meetings.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IApi {

    @POST("api/account/register")
    Call<ResponseBody> register(@Body RequestBody params);

    @POST("api/account/login")
    Call<ResponseBody> login(@Body RequestBody params);

    @POST("api/account/signout")
    Call<ResponseBody> exit(@Query("sessionKey") String sessionKey);

    @POST("api/account/updateAccount")
    Call<ResponseBody> updateAccountInfo(@Query("sessionKey") String sessionKey,
                                         @Body RequestBody params);

    @GET("api/account/details")
    Call<ResponseBody> getAccountInfo(@Query("sessionKey") String sessionKey);

    @POST("api/dates/CreateDate")
    Call<ResponseBody> createDate(@Query("sessionKey") String sessionKey,
                                  @Body RequestBody params);

    @POST("api/account/addFiles")
    Call<ResponseBody> addPhoto(@Query("sessionKey") String sessionKey, @Body RequestBody params);

    @Multipart
    @POST("api/account/addFiles")
    Call<ResponseBody> uploadAttachment(@Query("sessionKey") String sessionKey, @Part MultipartBody.Part filePart);

    @GET("api/account/photos")
    Call<ResponseBody> getPhotos(@Query("sessionKey") String sessionKey);

    @GET("api/account/photoContent")
    Call<ResponseBody> getPhoto(@Query("sessionKey") String sessionKey, @Query("photoId") long photoId);

    @POST("api/account/removeFile")
    Call<ResponseBody> deletePhoto(@Query("sessionKey") String sessionKey, @Body RequestBody params);

    @GET("api/dates/getDatesList")
    Call<ResponseBody> getDatesList(@Query("sessionKey") String sessionKey);

    @GET("api/dates/searchDates")
    Call<ResponseBody> searchDates(
            @Query("sessionKey") String sessionKey,
            @Query("amountStart") Double amountStart,
            @Query("startTime") Long startTime,
            @Query("page") Integer page
    );

    @GET("api/account/getprofileinfo")
    Call<ResponseBody> getProfileInfo(@Query("sessionKey") String sessionKey, @Query("id") long id);

    @GET("api/dates/addpartner")
    Call<ResponseBody> addPartner(@Query("sessionKey") String sessionKey,
                                  @Query("dateid") Long dateId);

    @GET("api/dates/getdatepartners")
    Call<ResponseBody> getDatePartners(
            @Query("sessionKey") String sessionKey,
            @Query("dateid") Long dateId
    );

    @POST("api/dates/selectpartner")
    Call<ResponseBody> selectPartner(
            @Query("sessionKey") String sessionKey,
            @Body RequestBody params
    );
}
