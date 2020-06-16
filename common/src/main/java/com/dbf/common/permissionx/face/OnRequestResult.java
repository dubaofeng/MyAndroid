package com.dbf.common.permissionx.face;

import java.util.List;

public
/**
 *Created by dbf on 2020/6/10 
 *describe:
 */
interface OnRequestResult {
    void onResult(boolean allGranted, String[] grantedList, String[] deniedList, String[] disableList);
}