package com.dbf.javastudy.proxy;

import com.dbf.javastudy.proxy.proxyinterface.ProxyFunitureInterface;

public class FunitureFactory implements ProxyFunitureInterface {
    @Override
    public void proxyFuniture(int number) {
        System.out.println("FunitureFactory buy " + number + " Funiture");
    }
}
