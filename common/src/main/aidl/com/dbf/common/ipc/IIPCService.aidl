// IIPCService.aidl
package com.dbf.common.ipc;
import com.dbf.common.ipc.model.Request;
import com.dbf.common.ipc.model.Response;
// Declare any non-default types here with import statements

interface IIPCService {
  Response send(in Request request);
}
