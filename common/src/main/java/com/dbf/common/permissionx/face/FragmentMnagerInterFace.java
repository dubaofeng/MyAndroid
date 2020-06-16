package com.dbf.common.permissionx.face;

public
/**
 *Created by dbf on 2020/6/16 
 *describe:
 */
interface FragmentMnagerInterFace {
    void setMyOnRequestPermissionsResult(MyOnRequestPermissionsResult myOnRequestPermissionsResult);


    void reQuest(String... permission);
}