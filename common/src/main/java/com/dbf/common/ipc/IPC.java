package com.dbf.common.ipc;

import android.content.Context;

import com.dbf.common.ipc.model.Request;
import com.dbf.common.ipc.model.Response;
import com.dbf.common.myutils.MyLog;
import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IPC {
    public static final String TAG = "myIPC";

    //服务端注册
    public static void register(Class<?> service) {
        Register.getInstance().register(service);
    }

    //连接本app其他进程的服务
    public static void connect(Context context, Class<? extends IPCService> service) {
        connect(context, null, service);
    }

    //连接其他app进程服务
    public static void connect(Context context, String packageName, Class<? extends IPCService> service) {
        Channel.getInstance().bind(context.getApplicationContext(), packageName, service);
    }

    public static void disConnect(Context context, Class<? extends IPCService> service) {
        Channel.getInstance().unbind(context, service);
    }

    public static <T> T getInstance(Class<? extends IPCService> service, Class<T> instanceClass, Object... parameters) {
        return getInstanceWithName(service, instanceClass, "getInstance", parameters);
    }

    public static <T> T getInstanceWithName(Class<? extends IPCService> service,
                                            Class<T> instanceClass, String methodName, Object... parameters) {
        if (!instanceClass.isInterface()) {
            throw new IllegalArgumentException("必须以接口进行通信");
        }
        Response response = Channel.getInstance().send(Request.GET_INSTANCE, service, instanceClass, methodName, parameters);
        if (response.isSuccess()) {
            return getProxy(instanceClass, service);
        }
        return null;
    }

    private static <T> T getProxy(Class<T> instanceClass, Class<? extends IPCService> service) {
        ClassLoader classLoader = instanceClass.getClassLoader();
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{instanceClass}, new IPCInvocationHandler(instanceClass, service));
    }

    static class IPCInvocationHandler implements InvocationHandler {
        private final Class<?> instanceClass;
        private Class<? extends IPCService> service;
        static Gson gson = new Gson();

        public IPCInvocationHandler(Class<?> instanceClass, Class<? extends IPCService> service) {
            this.instanceClass = instanceClass;
            this.service = service;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MyLog.INSTANCE.d(IPC.TAG, "invoke");
            Response response = Channel.getInstance().send(Request.GET_METHOD, service, instanceClass, method.getName(), args);
            if (response.isSuccess()) {
                Class<?> returnType = method.getReturnType();
                if (returnType != Void.class && returnType != void.class) {
                    String source = response.getSource();
                    return gson.fromJson(source, returnType);
                }
            }
            return null;
        }
    }
}
