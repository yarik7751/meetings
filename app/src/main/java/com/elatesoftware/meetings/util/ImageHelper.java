package com.elatesoftware.meetings.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.v4.graphics.BitmapCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageHelper {
    public static final String PROFILE_NAME = "profile.png";
    public static final String PROFILE_DIR = "image_dir";
    public static final String PATH_TO_PROFILE = PROFILE_DIR + PROFILE_NAME;

    public static String convertToBase64(Bitmap bitmap) {
        String base64 = null;
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
            byte[] b = baos.toByteArray();
            base64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return base64;
    }

    public static Single<String> convertToBase64Async(final Bitmap bitmap) {
        return Single.create(new Single.OnSubscribe<String>() {

            @Override
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                try {
                    String base64 = convertToBase64(bitmap);
                    singleSubscriber.onSuccess(base64);
                } catch (Exception e) {
                    singleSubscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public static Bitmap compressBitmap(int maxSize, Bitmap bitmap) {
        int compressQuality = 105;
        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
        int streamLength = maxSize;
        while (streamLength >= maxSize && compressQuality > 5) {
            try {
                bitmapStream.flush();//to avoid out of memory error
                bitmapStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compressQuality -= 5;
            bitmap.compress(Bitmap.CompressFormat.PNG, compressQuality, bitmapStream);
            byte[] bmpPicByteArray = bitmapStream.toByteArray();
            streamLength = bmpPicByteArray.length;
        }
        byte[] bitmapInArray = bitmapStream.toByteArray();
        Bitmap bitmapCreated = BitmapFactory.decodeByteArray(bitmapInArray, 0, bitmapInArray.length);
        return bitmapCreated;
    }

    public static Single<Bitmap> compressBitmapAsync(final int maxSize, final Bitmap bitmap) {
        return Single.create(new Single.OnSubscribe<Bitmap>() {
            @Override
            public void call(SingleSubscriber<? super Bitmap> singleSubscriber) {
                try {
                    Bitmap bitmapCompressed = compressBitmap(maxSize, bitmap);
                    singleSubscriber.onSuccess(bitmapCompressed);
                } catch (Exception e) {
                    singleSubscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Bitmap base64ToBitmap(String encodedImage) {
        if(TextUtils.isEmpty(encodedImage)) return null;
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap,int width, int height) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        return  newBitmap;
    }

    public static Bitmap loadBitmapFromView(View v) {
        if(v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache(true));
        if(bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmap;
    }
}
