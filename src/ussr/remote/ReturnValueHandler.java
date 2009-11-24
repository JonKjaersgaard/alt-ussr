package ussr.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReturnValueHandler extends Remote {
    public void provideReturnValue(String experiment, String result, Object value ) throws RemoteException;

    public void provideEventNotification(String experiment, String name, float time) throws RemoteException;
}
