package com.dbf.javastudy.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyPerson implements InvocationHandler {
    private Object factory;

    public void setFactory(Object factory) {
        this.factory = factory;
    }

    //通过Proxy获得动态代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(factory.getClass().getClassLoader()
                , factory.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        befor();
        Object re = method.invoke(factory, objects);
        after();
        return re;
    }

    private void befor() {
        System.out.println("befor()");
    }

    private void after() {
        System.out.println("after()");
    }
}
