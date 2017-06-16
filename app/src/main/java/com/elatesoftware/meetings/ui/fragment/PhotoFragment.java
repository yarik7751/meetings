package com.elatesoftware.meetings.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Const;
import com.elatesoftware.meetings.util.CustomSharedPreference;
import com.elatesoftware.meetings.util.ImageHelper;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.util.api.Api;
import com.elatesoftware.meetings.util.api.pojo.GetPhotoAnswer;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

public class PhotoFragment extends BaseFragment {

    public static final String TAG = "PhotoFragment_logs";
    public static final String USER_ID = "USER_ID";
    public static final String PHOTO_ID = "PHOTO_ID";

    @BindView(R.id.img_photo) ImageView imgPhoto;
    @BindView(R.id.pb_progress) AVLoadingIndicatorView pbProgress;

    private long photoId = -1;
    private long userId = -1;

    public static PhotoFragment getInstance(long userId, long photoId) {
        PhotoFragment photoFragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(PHOTO_ID, photoId);
        bundle.putLong(USER_ID, userId);
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoId = getArguments().getLong(PHOTO_ID);
        userId = getArguments().getLong(USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestGetPhoto();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    public long getPhotoId() {
        return photoId;
    }

    private void requestGetPhoto() {
        //long userId = CustomSharedPreference.getProfileInformation(getContext()).getId();
        String photoUrl = StringUtils.getPhotoUrl((int) userId, (int) photoId);
        int photoWidth = AndroidUtils.getWindowsSizeParams(getContext())[0];
        int photoHeight = (int) (AndroidUtils.getWindowsSizeParams(getContext())[1] * Const.PHOTOS_HEIGHT_PERCENT);
        Picasso.with(getContext()).load(photoUrl).resize(photoWidth, photoHeight).centerCrop().into(imgPhoto, new Callback() {
            @Override
            public void onSuccess() {
                if(pbProgress != null) {
                    pbProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {

            }
        });
        /*pbProgress.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("sessionKey", CustomSharedPreference.getToken(getContext()));
        params.add("photoId", String.valueOf(photoId));
        client.get(Api.BASE_URL + "api/account/photoContent", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(isAdded() && imgPhoto != null) {
                    Gson gson = new Gson();
                    String responseBodyStr = new String(responseBody);
                    Log.d(TAG, "responseBodyStr: " + responseBodyStr);
                    Log.d(TAG, "sessionKey: " + CustomSharedPreference.getToken(getContext()));
                    GetPhotoAnswer answer = gson.fromJson(responseBodyStr, GetPhotoAnswer.class);
                    if (answer.getSuccess()) {
                        Bitmap bitmap = ImageHelper.base64ToBitmap(answer.getResult().getContent());
                        imgPhoto.setImageBitmap(bitmap);
                        imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                } else {
                    //requestGetPhoto();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(pbProgress != null) {
                    pbProgress.setVisibility(View.GONE);
                }
            }
        });*/
    }
}
