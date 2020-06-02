package com.dbf.common.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.dbf.common.ipc.model.Parameters;
import com.dbf.common.ipc.model.Request;
import com.dbf.common.ipc.model.Response;
import com.dbf.common.myutils.MyLog;
import com.google.gson.Gson;

/**
 * 与服务进程同一个进程
 */
import java.lang.reflect.Method;

public abstract class IPCService extends Service {
    private Gson gson = new Gson();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IIPCService.Stub() {
        @Override
        public Response send(Request request) throws RemoteException {
            MyLog.INSTANCE.d(IPC.TAG, "IPCService接收request.toString()" + request.toString());
            String serviceId = request.getServiceId();
            Class<?> clazz = Register.getInstance().getService(serviceId);
            Parameters[] parameters = request.getParameters();
            Object[] objects = restoreParameters(parameters);
            String methodName = request.getMethodName();
            Method method = Register.getInstance().getMethod(clazz, methodName, parameters);
            Response response;
            switch (request.getType()) {
                case Request.GET_INSTANCE:
                    try {
                        Object object = method.invoke(null, objects);
                        Register.getInstance().putObject(serviceId, object);
                        response = new Response(null, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response = new Response(null, false);
                    }
                    break;
                case Request.GET_METHOD:
                    try {
                        Object object = Register.getInstance().getObject(serviceId);
                        Object returnObject = method.invoke(object, objects);
                        response = new Response(gson.toJson(returnObject), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response = new Response(null, false);
                    }
                    break;
                default:
                    response = new Response(null, false);
                    break;
            }
            return response;
        }
    };

    protected Object[] restoreParameters(Parameters[] parameters) {
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameters parameter = parameters[i];
            try {
                objects[i] = gson.fromJson(parameter.getValue(), Class.forName(parameter.getType()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public static class IPCService0 extends IPCService {
    }

    public static class IPCService1 extends IPCService {
    }
}
