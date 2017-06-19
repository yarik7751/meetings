package com.elatesoftware.meetings.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static final String TAG = "Utils_logs";

    //todo This method will be deleted late
    public static void setSelect(Context context, TextView tv, boolean isSelect) {
        if (isSelect) {
            tv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            tv.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        }
        tv.setTag(isSelect);
    }

    //todo This method will be deleted late
    public static List<DataSnapshot> iteratorToList(Iterator<DataSnapshot> iterator) {
        List<DataSnapshot> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            messages.add(iterator.next());
        }
        return messages;
    }

    public static IntentFilter getIntentFilter(String action) {
        IntentFilter intentFilter = new IntentFilter(action);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        return intentFilter;
    }
}
