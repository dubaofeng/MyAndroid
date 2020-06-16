package com.dbf.common.glide;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
class RequestManagerRetriver {
    public RequestManager get(FragmentActivity fragmentActivity) {
        return new RequestManager(fragmentActivity);
    }

    public RequestManager get(Activity activity) {
        return new RequestManager(activity);
    }

    public RequestManager get(Context context) {
        return new RequestManager(context);
    }
}
