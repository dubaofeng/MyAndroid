package com.dbf.studyandtest.javastudy.proxy;


import com.dbf.studyandtest.javastudy.proxy.dynamic_proxy.DynamicProxyPerson;
import com.dbf.studyandtest.javastudy.proxy.proxyinterface.ProxyFruitsInterface;

public class ProxyMain {
    public static void main(String[] args) {
//        FruitsFactory fruitsFactory = new FruitsFactory();
//        FunitureFactory funitureFactory = new FunitureFactory();
//        ProxyPerson proxyPerson = new ProxyPerson();
//        proxyPerson.setFruitsFactory(fruitsFactory);
//        proxyPerson.proxyFruits(5);
//        proxyPerson.setFunitureFactory(funitureFactory);
//        proxyPerson.proxyFuniture(15);

        ProxyFruitsInterface fruitsFactory = new PersonOne();
        DynamicProxyPerson dynamicProxyPerson = new DynamicProxyPerson();
        dynamicProxyPerson.setFactory(fruitsFactory);

        ProxyFruitsInterface fruitsFactory1 = (ProxyFruitsInterface) dynamicProxyPerson.getProxyInstance();
        fruitsFactory1.proxyFruits(18);
    }
}
