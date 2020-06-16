
package com.dbf.common.glide.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;

public
/**
 *Created by dbf on 2020/6/7
 *describe:
 */
class ActivityFragmentManager extends Fragment {
    private LifecycleCallback lifecycleCallback;

    public ActivityFragmentManager() {
    }

    @SuppressLint("ValidFragment")
    public ActivityFragmentManager(LifecycleCallback lifecycleCallback) {
        this.lifecycleCallback = lifecycleCallback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifecycleCallback != null) {
            lifecycleCallback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != lifecycleCallback) {
            lifecycleCallback.glideStopAcyion();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != lifecycleCallback) {
            lifecycleCallback.glideRecycleAction();
        }
    }
}


