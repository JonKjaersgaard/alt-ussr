package ussr.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReturnValueHandlerWrapper extends UnicastRemoteObject implements ReturnValueHandler {

    private ReturnValueHandler handler;

    protected ReturnValueHandlerWrapper(ReturnValueHandler handler) throws RemoteException {
        super();
        this.handler = handler;
    }

    public void provideReturnValue(String experiment, String name, Object value) throws RemoteException {
        this.handler.provideReturnValue(experiment, name, value);
    }

    @Override
    public void provideEventNotification(String experiment, String name, float time) throws RemoteException {
        this.handler.provideEventNotification(experiment, name, time);
    }

}
