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

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

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

    //todo 20 move to base activity
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

    //todo 5
    public static boolean isToken(Context context) {
        String token = CustomSharedPreference.getToken(context);
        return !TextUtils.isEmpty(token) && !token.equals(Const.NULL_TOKEN);
    }

    //todo 21 remove this method
    public static void setMarkerInMap(GoogleMap map, LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17.0f)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.addMarker(new MarkerOptions()
                .position(latLng));
    }

    public static IntentFilter getIntentFilter(String action) {
        IntentFilter intentFilter = new IntentFilter(action);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        return intentFilter;
    }

    //todo 17 move to fragments
    public static boolean checkRegInfo(Context context, CustomEditText cetEmail, CustomEditText cetPass, CustomEditText cetRepPass) {
        String userName = cetEmail.getEditText().getText().toString();
        String password = cetPass.getEditText().getText().toString();
        String repPassword = cetRepPass != null ? cetRepPass.getEditText().getText().toString() : null;
        if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
            showErrorDialog(context, context.getString(R.string.empty_data));
            return  false;
        }
        if(!Utils.isEmailValid(userName)) {
            showErrorDialog(context, context.getString(R.string.invalid_email));
            return  false;
        }
        if(password.length() < 6) {
            showErrorDialog(context, context.getString(R.string.short_password) + "(6)");
            return  false;
        }
        if(cetRepPass != null && !repPassword.equals(password)) {
            showErrorDialog(context, context.getString(R.string.passwords_is_not_equals));
            return  false;
        }
        return true;
    }

    //todo 18 move to fragments
    public static boolean checkAutInfo(Context context, CustomEditText cetEmail, CustomEditText cetPass) {
        String userName = cetEmail.getEditText().getText().toString();
        String password = cetPass.getEditText().getText().toString();
        if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
            showErrorDialog(context, context.getString(R.string.empty_data));
            return  false;
        }
        if(!Utils.isEmailValid(userName)) {
            showErrorDialog(context, context.getString(R.string.invalid_email));
            return  false;
        }
        return true;
    }

    private static void showErrorDialog(Context context, String msgError) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.error_add_date)
                .setMessage(msgError)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    public static void showEditDialog(Context context, String title, String text, int inputType,  DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        final EditText input = new EditText(context);
        input.setGravity(Gravity.CENTER);
        input.setBackgroundResource(R.color.white );
        input.setText(text);
        input.setHint(title);
        input.setInputType(inputType);
        input.setSelection(input.getText().length());
        input.setTextColor(context.getResources().getColor(R.color.seek_bar));
        builder.setView(input);

        builder.setPositiveButton(R.string.ok, listener);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
