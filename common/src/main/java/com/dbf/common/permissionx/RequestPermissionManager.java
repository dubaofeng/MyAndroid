package com.dbf.common.permissionx;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.dbf.common.permissionx.face.FragmentMnagerInterFace;
import com.dbf.common.permissionx.face.MyOnRequestPermissionsResult;
import com.dbf.common.permissionx.face.OnDialogOperationListener;
import com.dbf.common.permissionx.face.OnRequestResult;
import com.dbf.common.permissionx.fragment.ActivityFragmentManager;
import com.dbf.common.permissionx.fragment.FragmentActivityFragmentManager;

import java.util.ArrayList;
import java.util.List;

public
/**
 *Created by dbf on 2020/6/10 
 *describe:
 */
class RequestPermissionManager {
    private static final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_Name";
    private static final String ACTIVITY_NAME = "Activity_Name";
    private static String[] mPermissions;
    private static final String TAG = RequestPermissionManager.class.getSimpleName();
    private static FragmentMnagerInterFace fragmentMnagerInterFace;
    private static Context mactivity;
    private static OnRequestResult requestResult;
    private static RequestPermissionManager manager = new RequestPermissionManager();
    private static OnDialogOperationListener onDialogOperationListener;

    public static RequestPermissionManager init(FragmentActivity fragmentActivity) {
        Log.d(TAG, "init: androidx.fragment.app.FragmentActivity");
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentMnagerInterFace = (FragmentMnagerInterFace) fragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (null == fragmentMnagerInterFace) {
            fragmentManager.beginTransaction().add((Fragment) createFragmentActivityFragmentManager(fragmentActivity), FRAGMENT_ACTIVITY_NAME).commitNow();
        }
        mactivity = fragmentActivity;
        return manager;
    }

    public static RequestPermissionManager init(Activity activity) {
        Log.d(TAG, "init: android.app.Activity");
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentMnagerInterFace = (FragmentMnagerInterFace) fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        if (null == fragmentMnagerInterFace) {
            fragmentManager.beginTransaction().add((android.app.Fragment) createActivityFragmentManager(activity), ACTIVITY_NAME).commitNow();
        }
        mactivity = activity;
        return manager;
    }

    public RequestPermissionManager permission(String... permission) {
        if (null == fragmentMnagerInterFace) {
            throw new IllegalStateException("You didn't call the init method");
        }
        mPermissions = permission;
        return this;
    }

    public RequestPermissionManager request(OnRequestResult onRequestResult) {
        if (null == mPermissions) {
            throw new IllegalStateException("You didn't call the permission method");
        }
        requestResult = onRequestResult;
        List<String> grantedList = null;
        List<String> deniedList = null;
        for (int i = 0; i < mPermissions.length; i++) {
            if (checkSelfPermission(mactivity, mPermissions[i])) {
                if (null == grantedList) {
                    grantedList = new ArrayList<>();
                }
                grantedList.add(mPermissions[i]);
            } else {
                if (null == deniedList) {
                    deniedList = new ArrayList<>();
                }
                deniedList.add(mPermissions[i]);
            }
        }
        if (deniedList == null) {
            onRequestResult.onResult(null == deniedList, grantedList.toArray(new String[grantedList.size()]), null, null);
        } else {
            fragmentMnagerInterFace.reQuest(deniedList.toArray(new String[deniedList.size()]));
        }
        return this;
    }

    public static boolean checkSelfPermission(Context context, String... permission) {
        for (int i = 0; i < permission.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static void requestAgain() {
        fragmentMnagerInterFace.reQuest(mPermissions);
    }

    private static FragmentMnagerInterFace createFragmentActivityFragmentManager(Context context) {
        fragmentMnagerInterFace = new FragmentActivityFragmentManager();
        fragmentMnagerInterFace.setMyOnRequestPermissionsResult(myOnRequestPermissionsResult);
        return fragmentMnagerInterFace;
    }

    private static FragmentMnagerInterFace createActivityFragmentManager(Context context) {
        fragmentMnagerInterFace = new ActivityFragmentManager();
        fragmentMnagerInterFace.setMyOnRequestPermissionsResult(myOnRequestPermissionsResult);
        return fragmentMnagerInterFace;
    }

    private static MyOnRequestPermissionsResult myOnRequestPermissionsResult = new MyOnRequestPermissionsResult() {
        @Override
        public void myOnRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            List<String> grantedList = null;
            List<String> deniedList = null;
            List<String> disableList = null;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    if (null == grantedList) {
                        grantedList = new ArrayList<>();
                    }
                    grantedList.add(permissions[i]);
                } else {
                    if (null == deniedList) {
                        deniedList = new ArrayList<>();
                    }
                    deniedList.add(permissions[i]);
                }
            }
            if (null != deniedList) {
                for (String s : deniedList) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mactivity, s)) {
                    } else {
                        if (null == disableList) {
                            disableList = new ArrayList<>();
                        }
                        disableList.add(s);
                    }
                }
            }

            requestResult.onResult(null == deniedList,
                    grantedList == null ? null : grantedList.toArray(new String[grantedList.size()]),
                    deniedList == null ? null : deniedList.toArray(new String[deniedList.size()]),
                    disableList == null ? null : disableList.toArray(new String[disableList.size()])
            );
        }
    };


    public static void showDisablePermissionDialog(String msg, String posButStr, String ntvStr) {
//弹框去设置权限
        AlertDialog.Builder builder = new AlertDialog.Builder(mactivity)
                .setMessage(msg)
                .setPositiveButton(posButStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// Intent intent = new Intent(Settings.ACTION_SETTINGS);
// startActivity(intent);
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", mactivity.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", mactivity.getPackageName());
                        }
                        mactivity.startActivity(localIntent);
                        callbackClickDisableDialig(true);
                    }
                })
                .setNegativeButton(ntvStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbackClickDisableDialig(false);
                    }
                });
        builder.show();
    }

    public void setOnDialogOperationListener(OnDialogOperationListener onDialogOperationListener) {
        this.onDialogOperationListener = onDialogOperationListener;
    }

    private static void callbackClickDisableDialig(boolean b) {
        if (onDialogOperationListener != null) {
            onDialogOperationListener.disablePermissionOperation(b);
        }
    }

    private static void callbackClickDeniedDialig(boolean b) {
        if (onDialogOperationListener != null) {
            onDialogOperationListener.disablePermissionOperation(b);
        }
    }

    public static void showDeniedPermissionDialog(String msg, String posButStr, String ntvStr) {
//弹框提示需要这些权限
        AlertDialog.Builder builder = new AlertDialog.Builder(mactivity)
                .setMessage(msg)
                .setPositiveButton(posButStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestAgain();
                        callbackClickDeniedDialig(true);
                    }
                })
                .setNegativeButton(ntvStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbackClickDeniedDialig(false);
                    }
                });
        builder.show();
    }

}