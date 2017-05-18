package com.elatesoftware.meetings.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static final String TAG = "Utils_logs";

    public static void setSelect(Context context, TextView tv, boolean isSelect) {
        if (isSelect) {
            tv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            tv.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        }
        tv.setTag(isSelect);
    }

    public static String getCity(Context context) throws IOException {
        if(!AndroidUtils.isNetworkOnline(context)) {
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkSelfPermission - false");
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if(location != null) {
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0)
            {
                return addresses.get(0).getLocality();
            }
            else
            {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean isPermissionsGranted(int[] permissions) {
        for(int permission : permissions) {
            if(permission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static List<DataSnapshot> iteratorToList(Iterator<DataSnapshot> iterator) {
        List<DataSnapshot> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            messages.add(iterator.next());
        }
        return messages;
    }

    public static boolean isToken(Context context) {
        String token = CustomSharedPreference.getToken(context);
        return !TextUtils.isEmpty(token) && !token.equals(Const.NULL_TOKEN);
    }
}
