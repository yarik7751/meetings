package com.elatesoftware.meetings.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.util.api.Api;
import com.elatesoftware.meetings.util.api.pojo.HumanAnswer;
import com.elatesoftware.meetings.util.api.pojo.Meeting;

public class StringUtils {

    public static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String generateTextForDate(HumanAnswer profileInfo, Meeting meeting) {
        String info = "";
        info += (profileInfo != null ? profileInfo.getFirstName() : "") + " " + (profileInfo != null ? DateUtils.getAge(profileInfo.getDateOfBirthByCalendar().getTimeInMillis()) : "") + " years invites the girl to " +
                meeting.getPlace() + ". Gift: " + meeting.getAmount() + ".";
        info += "\nRequirement: Girl from " + meeting.getPrefAgeStart() + " to " + meeting.getPrefAgeEnd() + " years, " +
                "rising from " + meeting.getPrefHeightStart() + " to " + meeting.getPrefHeightEnd() + " cm, " +
                "weight to " + meeting.getPrefWeightStart() + " from " + meeting.getPrefWeightEnd() + " kg.";
        return info;
    }

    public static void setMaskAmount(EditText v, boolean isBol) {
        String str = v.getText().toString();
        if(isBol) {
            v.setCursorVisible(true);
            str = str.replace("$", "");
            v.setText(str);
            v.setSelection(v.getText().toString().length());
        } else {
            v.setCursorVisible(false);
            v.clearFocus();
            if(!str.contains("$")) {
                if(TextUtils.isEmpty(str)) {
                    str = "0";
                }
                int value = Integer.parseInt(str);
                v.setText("$" + value);
            }
        }
    }

    public static String getPhotoUrl(int userId, int photoId) {
        return Api.BASE_URL + "Content/UserPictures/" + userId + "/" + photoId + ".jpeg";
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
