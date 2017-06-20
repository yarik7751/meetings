package com.elatesoftware.meetings.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;

import com.elatesoftware.meetings.R;

public class DialogUtils {

    private static String dialogMessage;
    public static String getDialogMessage() {
        return dialogMessage;
    }

    public static void showEditDialog(Context context, String title, String text, int inputType, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        final EditText input = new EditText(context);
        input.setBackgroundResource(R.color.white );
        float paddingDp = 24f;
        input.setPadding(AndroidUtils.dp(paddingDp), AndroidUtils.dp(paddingDp), AndroidUtils.dp(paddingDp), 0);
        input.setText(text);
        input.setHint(title);
        input.setInputType(inputType);
        input.setSelection(input.getText().length());
        input.setTextColor(context.getResources().getColor(R.color.seek_bar));
        builder.setView(input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogMessage = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        builder.setPositiveButton(R.string.ok, listener);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public static void showErrorDialog(Context context, String msgError) {
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
}
