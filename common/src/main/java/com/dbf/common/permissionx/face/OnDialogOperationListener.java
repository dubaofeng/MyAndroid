package com.dbf.common.permissionx.face;

public
/**
 *Created by dbf on 2020/6/16 
 *describe:
 */
interface OnDialogOperationListener {
    void disablePermissionOperation(boolean isClickPositiveButton);

    void deniedPermissionOperation(boolean isClickPositiveButton);
}