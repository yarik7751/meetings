package com.elatesoftware.meetings.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.EditText;

import com.elatesoftware.meetings.R;

public class DialogUtils {

    public static void showEditDialog(Context context, String title, String text, int inputType, DialogInterface.OnClickListener listener) {
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
