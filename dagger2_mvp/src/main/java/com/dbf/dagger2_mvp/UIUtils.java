package com.dbf.dagger2_mvp;

import android.content.Context;
import android.widget.Toast;

public class UIUtils {
    Context context;
    private static Toast toast;

    public static Toast getToast(Context context, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        }
        return toast;
    }


    public static void toastSHORT(Context context, String msg) {
        getToast(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLONG(Context context, String msg) {
        getToast(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void toastSHORT(Context context, int msg) {
        getToast(context, context.getString(msg), Toast.LENGTH_SHORT).show();
    }

    public static void toastLONG(Context context, int msg) {
        getToast(context, context.getString(msg), Toast.LENGTH_LONG).show();
    }

}
