package com.dbf.common.glide;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.dbf.common.glide.fragment.ActivityFragmentManager;
import com.dbf.common.glide.fragment.FragmentActivirtyFragmentManager;
import com.dbf.common.myutils.MyLog;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:生命周期管理
 */
class RequestManager {
    private final String TAG = RequestManager.class.getCanonicalName();
    private final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_Name";
    private final String ACTIVITY_NAME = "Activity_Name";
    private Context context;
    private RequestTargetEngine engine;
    private final int NET_HANDLER_MSG = 995465;//
    private FragmentActivity fragmentActivity;
    private Activity activity;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (null != fragmentActivity) {
                Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
                Log.d(TAG, "Handler: fragment1" + fragment); // 有值 ： 不在排队中，所以有值
            } else if (null != activity) {
                android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag(ACTIVITY_NAME);
                Log.d(TAG, "Handler: fragment2" + fragment); // 有值 ： 不在排队中，所以有值
            }

            return false;
        }
    });

    {
        //构造代码块，不用在所有的构造方法都写一遍
        if (null == engine) {
            MyLog.INSTANCE.d(TAG, "构造代码块");
        }
    }

    public RequestManager(FragmentActivity fragmentActivity) {
        MyLog.INSTANCE.d(TAG, "RequestManager构造方法fragmentActivity");
        this.fragmentActivity = fragmentActivity;
        this.context = fragmentActivity;
        engine = new RequestTargetEngine(context);
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (null == fragment) {
            //如果fragment==null就要创建fragment
            fragment = new FragmentActivirtyFragmentManager(engine);
            //添加到Fragment管理器
            fragmentManager.beginTransaction().
                    add(fragment, FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss();

        }
        // TODO
        // 测试下面的话，这种测试，不能完全准确
        // 证明是不是排队状态
        Fragment fragment1 = fragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment2" + fragment1); // null ： 还在排队中，还没有消费
        // 添加进去 提交之后 可能还处于 排队状态，想让马上干活 （Android 消息机制管理），快速干活，发了一次Handler
        mHandler.sendEmptyMessage(NET_HANDLER_MSG);
    }

    public RequestManager(Activity activity) {
        MyLog.INSTANCE.d(TAG, "RequestManager构造方法Activity");
        this.activity = activity;
        this.context = activity;
        engine = new RequestTargetEngine(context);
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.Fragment fragment = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        if (fragment == null) {
            fragment = new ActivityFragmentManager(engine);
            fragmentManager.beginTransaction().add(fragment, ACTIVITY_NAME).commitAllowingStateLoss();

        }
        android.app.Fragment fragment1 = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment1=" + fragment1);
        mHandler.sendEmptyMessage(NET_HANDLER_MSG);
    }

    public RequestManager(Context context) {
        MyLog.INSTANCE.d(TAG, "RequestManager构造方法context");
        this.context = context;
        engine = new RequestTargetEngine(context);
    }

    public RequestTargetEngine load(String path) {
        mHandler.removeMessages(NET_HANDLER_MSG);
        engine.loadValueInitAction(path, context);
        return engine;
    }
}
