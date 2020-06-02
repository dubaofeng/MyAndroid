package com.dbf.studyandtest.myaidl.location;

public class LocationBean {
    private String address;
    private float la;
    private float lo;

    @Override
    public String toString() {
        return "LocationBean{" +
                "address='" + address + '\'' +
                ", la=" + la +
                ", lo=" + lo +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLa() {
        return la;
    }

    public void setLa(float la) {
        this.la = la;
    }

    public float getLo() {
        return lo;
    }

    public void setLo(float lo) {
        this.lo = lo;
    }

    public LocationBean(String address, float la, float lo) {
        this.address = address;
        this.la = la;
        this.lo = lo;
    }
}
