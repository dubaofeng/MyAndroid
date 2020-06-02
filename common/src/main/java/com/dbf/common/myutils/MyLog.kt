package com.dbf.common.myutils;

import android.util.Log
import com.dbf.common.BuildConfig

object MyLog {
    private val TAG = "commom_module"
    private val isRelease = !BuildConfig.isRelease
    fun v(tag: String = TAG, log: String) {
        if (isRelease) Log.v(tag, log)
    }

    fun d(tag: String = TAG, log: String) {
        if (isRelease) Log.d(tag, log)
    }

    fun i(tag: String = TAG, log: String) {
        if (isRelease) Log.i(tag, log)
    }

    fun w(tag: String = TAG, log: String) {
        if (isRelease) Log.w(tag, log)
    }

    fun e(tag: String = TAG, log: String) {
        if (isRelease) Log.e(tag, log)
    }

}
