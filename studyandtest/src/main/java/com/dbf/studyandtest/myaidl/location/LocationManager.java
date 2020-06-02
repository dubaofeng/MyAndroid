package com.dbf.studyandtest.myaidl.location;

import com.dbf.common.ipc.annotation.ServiceId;

@ServiceId("LocationManager")
public class LocationManager {
    private LocationManager() {
    }

    private static final LocationManager ourInstance = new LocationManager();

    public static LocationManager getDefault() {
        return ourInstance;
    }

    LocationBean locationBean;

    public LocationBean getLocationBean() {
        return locationBean;
    }

    public LocationBean getLocationBean(String address) {
        locationBean.setAddress(address);
        return locationBean;
    }

    ;

    public LocationBean setLocationBean(LocationBean locationBean) {

        this.locationBean = locationBean;
        return locationBean;
    }

}
