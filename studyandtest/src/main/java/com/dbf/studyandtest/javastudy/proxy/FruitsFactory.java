package com.dbf.studyandtest.javastudy.proxy;


import com.dbf.studyandtest.javastudy.proxy.proxyinterface.ProxyFruitsInterface;

public class FruitsFactory implements ProxyFruitsInterface {
    @Override
    public void proxyFruits(int number) {
        System.out.println("FruitsFactory buy " + number + " Fruits");
    }
}
