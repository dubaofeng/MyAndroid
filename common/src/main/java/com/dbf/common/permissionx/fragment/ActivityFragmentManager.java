package com.dbf.common.permissionx.fragment;

import androidx.annotation.NonNull;

import com.dbf.common.permissionx.face.FragmentMnagerInterFace;
import com.dbf.common.permissionx.face.MyOnRequestPermissionsResult;

public
/**
 *Created by dbf on 2020/6/10 
 *describe:
 */
class ActivityFragmentManager extends android.app.Fragment implements FragmentMnagerInterFace {
    private MyOnRequestPermissionsResult myOnRequestPermissionsResult;

    @Override
    public void setMyOnRequestPermissionsResult(MyOnRequestPermissionsResult myOnRequestPermissionsResult) {
        this.myOnRequestPermissionsResult = myOnRequestPermissionsResult;
    }

    @Override
    public void reQuest(String... permissions) {
        requestPermissions(permissions, 110);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        myOnRequestPermissionsResult.myOnRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}