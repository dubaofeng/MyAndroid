package com.dbf.studyandtest.testpermissionx

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dbf.studyandtest.R
import com.permissionx.guolindev.PermissionX

class TestPermissiionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_permissiion)
        PermissionX.init(this).permissions(Manifest.permission.CALL_PHONE)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "获得了电话权限", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "您拒绝了拨打电话权限", Toast.LENGTH_SHORT).show()
                }
            }
    }
}