package com.dbf.studyandtest.myaidl.location;

import com.dbf.common.ipc.annotation.ServiceId;

@ServiceId("LocationManager")
public interface ILocationManager {
    LocationBean getLocationBean();

    LocationBean getLocationBean(String address);

    LocationBean setLocationBean(LocationBean locationBean);

}
