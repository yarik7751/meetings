package com.elatesoftware.meetings.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.elatesoftware.meetings.R;

public class StringUtils {

    public static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isValidValues(Context context, EditText etEmail, EditText etPass) {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        if(TextUtils.isEmpty(email)) {
            etEmail.setError(context.getString(R.string.field_is_empty));
            return false;
        }
        if(TextUtils.isEmpty(pass)) {
            etPass.setError(context.getString(R.string.field_is_empty));
            return false;
        }
        if(!StringUtils.isValidEmail(email)) {
            etEmail.setError(context.getString(R.string.email_invalid));
            return false;
        }
        if(pass.length() < 8) {
            etPass.setError(context.getString(R.string.short_password));
            return false;
        }
        return true;
    }
}
