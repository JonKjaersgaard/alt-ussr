package ussr.remote;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

public interface RemoteWrapper {

    Object invoke(Object proxy, Method method, Object[] args) throws RemoteException;

}
