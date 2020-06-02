package com.dbf.common.ipc;

import com.dbf.common.ipc.annotation.ServiceId;
import com.dbf.common.ipc.model.Parameters;
import com.dbf.common.myutils.MyLog;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Register {
    private static Register instance;

    private Register() {
    }

    public static Register getInstance() {
        if (instance == null) {
            synchronized (Register.class) {
                if (instance == null) {
                    instance = new Register();
                }
            }
        }
        return instance;
    }

    //服务表
    private ConcurrentHashMap<String, Class<?>> mServices = new ConcurrentHashMap<>();
    //方法表
    private ConcurrentHashMap<Class<?>, Map<String, Method>> methods = new ConcurrentHashMap<>();
    //类id:实例对象
    private Map<String, Object> mObjects = new HashMap<>();

    public void register(Class<?> clazz) {
//通过反射获得类的标记
        ServiceId annotation = clazz.getAnnotation(ServiceId.class);
        if (null == annotation) {
            throw new RuntimeException("必须使用ServiceId注解的服务才能注册！");
        }
        String serviceID = annotation.value();
        MyLog.INSTANCE.d(IPC.TAG, "serviceID=" + serviceID);
        mServices.putIfAbsent(serviceID, clazz);//保存至服务表
        methods.put(clazz, new HashMap<String, Method>());//
        Map<String, Method> methodMap = methods.get(clazz);
        for (Method method : clazz.getMethods()) {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName());
            sb.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 0) {
                sb.append(parameterTypes[0].getName());
            }
            for (int i = 1; i < parameterTypes.length; i++) {
                sb.append(",").append(parameterTypes[i].getName());
            }
            sb.append(")");
            String sbStr = sb.toString();
            MyLog.INSTANCE.d(IPC.TAG, "methodStr=" + sbStr);
            MyLog.INSTANCE.i(IPC.TAG, "method=" + method);
            methodMap.put(sbStr, method);
        }


    }

    public Class<?> getService(String serviceId) {
        Class<?> clazz = mServices.get(serviceId);
        return clazz;

    }

    public Method getMethod(Class<?> clazz, String methodName, Parameters[] parameters) {
        Map<String, Method> methodMap = methods.get(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append(methodName).append("(");
        if (parameters.length != 0) {
            sb.append(parameters[0].getType());
        }
        for (int i = 1; i < parameters.length; i++) {
            sb.append(",").append(parameters[i].getType());
        }
        sb.append(")");
        String sbStr = sb.toString();
        Method method = methodMap.get(sbStr);
        MyLog.INSTANCE.d(IPC.TAG, "getMethodStr=" + sbStr);
        MyLog.INSTANCE.i(IPC.TAG, "getmethod=" + method);
        return method;
    }

    public void putObject(String serviceId, Object object) {
        mObjects.put(serviceId, object);
    }

    public Object getObject(String serviceId) {
        return mObjects.get(serviceId);
    }

}
