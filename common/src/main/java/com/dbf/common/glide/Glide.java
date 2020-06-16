package com.dbf.common.glide;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.dbf.common.glide.cache.ActiveCache;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
class Glide {
    private RequestManagerRetriver requestManagerRetriver;

    public Glide(RequestManagerRetriver requestManagerRetriver) {
        this.requestManagerRetriver = requestManagerRetriver;
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return getRetriver(fragmentActivity).get(fragmentActivity);
    }

    public static RequestManager with(Activity activity) {
        return getRetriver(activity).get(activity);
    }

    public static RequestManager with(Context context) {
        return getRetriver(context).get(context);
    }

    private static RequestManagerRetriver getRetriver(Context context) {
        return Glide.get(context).getRetriver();
    }

    private static Glide get(Context context) {
        return new GlideBuilder(context).build();
    }

    public RequestManagerRetriver getRetriver() {
        return requestManagerRetriver;
    }
}
