package com.elatesoftware.meetings.ui.fragment;

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
import com.elatesoftware.meetings.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;

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
    }
}
