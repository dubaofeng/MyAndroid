package com.dbf.studyandtest.testpermissionx

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dbf.common.permissionx.RequestPermissionManager
import com.dbf.common.permissionx.face.OnDialogOperationListener
import com.dbf.common.permissionx.face.OnRequestResult
import com.dbf.studyandtest.R

class TestPermissionActivity : AppCompatActivity(), OnRequestResult, OnDialogOperationListener,
    View.OnClickListener {
    companion object {
        val permissions = arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_permissiion)
        //        PermissionX.init(this).permissions(Manifest.permission.CALL_PHONE)
        //            .request { allGranted, grantedList, deniedList ->
        //                if (allGranted) {
        //                    Toast.makeText(this, "获得了电话权限", Toast.LENGTH_SHORT).show()
        //                } else {
        //                    Toast.makeText(this, "您拒绝了拨打电话权限", Toast.LENGTH_SHORT).show()
        //                }
        //            }
        RequestPermissionManager.init(this).permission(*permissions).request(this);
    }

    override fun onResult(allGranted: Boolean,
                          grantedList: Array<out String>?,
                          deniedList: Array<out String>?,
                          disableList: Array<out String>?) {
        if (allGranted) {
            Toast.makeText(this, "或得权限", Toast.LENGTH_SHORT).show()
        } else {
            if (null != disableList) {
                RequestPermissionManager.showDisablePermissionDialog("这个权限被拒绝并不在询问,程序运行需要此权限,是否前往设置授权？",
                    "去",
                    "退出app");
            } else {
                RequestPermissionManager.showDeniedPermissionDialog("您拒绝了权限，程序运行需要此权限,是否授予权限?",
                    "再次申请",
                    "就是不给");
            }
        }
    }

    override fun deniedPermissionOperation(isClickPositiveButton: Boolean) {
        if (!isClickPositiveButton) {
            finish();
        }
    }

    override fun disablePermissionOperation(isClickPositiveButton: Boolean) {
        if (!isClickPositiveButton) {
            finish();
        }
    }

    override fun onClick(v: View?) {
    }
}