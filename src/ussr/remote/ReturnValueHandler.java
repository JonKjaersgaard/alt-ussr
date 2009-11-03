package ussr.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReturnValueHandler extends Remote {
    public void provideReturnValue(String name, Object value ) throws RemoteException;
}
