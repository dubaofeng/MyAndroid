package com.dbf.common.myutils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    public static void showText(Context context, String string) {
        if (null == toast) {
            toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        }
        toast.setText(string);
        toast.show();
    }
}
