package com.elatesoftware.meetings.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.elatesoftware.meetings.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_DYNAMIC = "yyyy-MM-dd";
    public static final String DATE_FORMAT_OUTPUT = "dd.MM.yy HH:mm";

    public static String getDateToString(Context context, Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + " " + getMonthTitle(context, calendar.get(Calendar.MONTH)) + ", " + calendar.get(Calendar.YEAR);
    }

    public static String getMonthTitle(Context context, int numMonth) {
        String[] months = context.getResources().getStringArray(R.array.months);
        return months[numMonth];
    }

    public static String getDateByStr(Date date, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static long getAge(long birthDate) {
        return (System.currentTimeMillis() - birthDate) / 1000 / 31536000;
    }

    public static long stringToUnix(String date) {
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(DATE_FORMAT));
        Date resultDate = null;
        try {
            resultDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (resultDate != null) {
            return resultDate.getTime();
        } else {
            return -1;
        }
    }

    public static void showSlideDateTimeDialog(FragmentManager fragmentManager, SlideDateTimeListener listener) {
        new SlideDateTimePicker.Builder(fragmentManager)
                .setListener(listener)
                .setInitialDate(new Date())
                .setIs24HourTime(true)
                .build()
                .show();
    }
}
