package com.dbf.studyandtest.javastudy.proxy;


import com.dbf.studyandtest.javastudy.proxy.proxyinterface.ProxyFruitsInterface;

public class PersonOne implements ProxyFruitsInterface {
    @Override
    public void proxyFruits(int number) {
        System.out.println("PersonOne buy " + number + " Fruits");
    }
}
