package com.dbf.javastudy.proxy;

import com.dbf.javastudy.proxy.dynamic_proxy.DynamicProxyPerson;
import com.dbf.javastudy.proxy.proxyinterface.ProxyFruitsInterface;

public class Main {
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
