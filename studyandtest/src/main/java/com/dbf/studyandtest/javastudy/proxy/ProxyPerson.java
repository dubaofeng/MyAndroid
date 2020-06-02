package com.dbf.studyandtest.javastudy.proxy;


import com.dbf.studyandtest.javastudy.proxy.proxyinterface.ProxyFruitsInterface;
import com.dbf.studyandtest.javastudy.proxy.proxyinterface.ProxyFunitureInterface;

public class ProxyPerson implements ProxyFruitsInterface, ProxyFunitureInterface {
    private FruitsFactory fruitsFactory;
    private FunitureFactory funitureFactory;

    public void setFruitsFactory(FruitsFactory fruitsFactory) {
        this.fruitsFactory = fruitsFactory;
    }

    public void setFunitureFactory(FunitureFactory funitureFactory) {
        this.funitureFactory = funitureFactory;
    }

    private void proxyBefor() {
        System.out.println("proxyBefor");
    }


    private void proxyAfter() {
        System.out.println("proxyAfter");
    }

    @Override
    public void proxyFruits(int number) {

        proxyBefor();
        System.out.println("ProxyPerson.proxyFruits");

        if (fruitsFactory != null) {
            fruitsFactory.proxyFruits(number);
        }
        proxyAfter();
    }

    @Override
    public void proxyFuniture(int number) {
        proxyBefor();
        System.out.println("ProxyPerson.proxyFuniture");

        if (funitureFactory != null) {
            funitureFactory.proxyFuniture(number);
        }
        proxyAfter();
    }
}
