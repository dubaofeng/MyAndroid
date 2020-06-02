package com.dbf.common.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.dbf.common.ipc.annotation.ServiceId;
import com.dbf.common.ipc.model.Parameters;
import com.dbf.common.ipc.model.Request;
import com.dbf.common.ipc.model.Response;
import com.dbf.common.myutils.MyLog;
import com.google.gson.Gson;

import java.util.concurrent.ConcurrentHashMap;

public class Channel {

    private Channel() {
    }

    //饿汉式
//    public static Channel getInstance() {
//        return SingleInstance.instance;
//    }
//
//    private static final class SingleInstance {
//        private static final Channel instance = new Channel();
//    }
    private static volatile Channel instance;

    public static Channel getInstance() {
        if (null == instance) {
            synchronized (Channel.class) {
                if (null == instance) {
                    instance = new Channel();
                }
            }
        }
        return instance;
    }

    //已经绑定过的
    private ConcurrentHashMap<Class<? extends IPCService>, Boolean> mBinds = new ConcurrentHashMap<>();
    //正在绑定的
    private ConcurrentHashMap<Class<? extends IPCService>, Boolean> mBinding = new ConcurrentHashMap<>();
    //已经绑定过的服务对应的ServiceConnect
    private final ConcurrentHashMap<Class<? extends IPCService>, IPCServiceConnection> mServiceConnection = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends IPCService>, IIPCService> mBinders = new ConcurrentHashMap<>();
    private Gson gson = new Gson();

    public void bind(Context context, String packageName, Class<? extends IPCService> service) {
        IPCServiceConnection connection;
        //是否已经绑定
        Boolean isBind = mBinds.get(service);
        if (null != isBind && isBind) {
            return;
        }
        //是否正在绑定
        Boolean isBinding = mBinding.get(service);
        if (null != isBinding && isBinding) {
            return;
        }
        //绑定
        mBinding.put(service, true);
        connection = new IPCServiceConnection(service);
        mServiceConnection.put(service, connection);
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());

        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void unbind(Context context, Class<? extends IPCService> service) {
        Boolean bind = mBinds.get(service);
        if (null != bind && bind) {
            IPCServiceConnection connection = mServiceConnection.get(service);
            if (null != connection) {
                context.unbindService(connection);
            }
            mBinds.put(service, false);
        }
    }

    public Response send(int type, Class<? extends IPCService> service, Class<?> classType,
                         String methodName, Object[] parameters) {
        IIPCService iipcService = mBinders.get(service);
        if (null == iipcService) {
            return new Response(null, false);
        }
        ServiceId annotation = classType.getAnnotation(ServiceId.class);
        String serviceId = annotation.value();
        Parameters[] parametersMake = makeParameters(parameters);
        Request request = new Request(type, serviceId, methodName, parametersMake);

        try {
            MyLog.INSTANCE.d(IPC.TAG, "Channel发送request.toString()" + request.toString());
            return iipcService.send(request);
        } catch (RemoteException e) {
            e.printStackTrace();
            return new Response(null, false);
        }
    }

    private Parameters[] makeParameters(Object[] parameters) {
        Parameters[] p;
        if (null != parameters) {
            p = new Parameters[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object object = parameters[i];
                String type = object.getClass().getName();
                String value = gson.toJson(object);
                MyLog.INSTANCE.d(IPC.TAG, "object.getClass().getName()=" + type);
                MyLog.INSTANCE.d(IPC.TAG, "gson.toJson(object)=" + value);
                p[i] = new Parameters(type, value);
            }
        } else {
            p = new Parameters[0];
        }
        return p;
    }

    private class IPCServiceConnection implements ServiceConnection {
        private Class<? extends IPCService> mService;

        public IPCServiceConnection(Class<? extends IPCService> service) {
            this.mService = service;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IIPCService iipcService = IIPCService.Stub.asInterface(service);
            mBinders.put(mService, iipcService);
            mBinds.put(mService, true);
            mBinding.remove(mService);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinders.remove(mService);
            mBinds.remove(mService);
        }
    }
}
