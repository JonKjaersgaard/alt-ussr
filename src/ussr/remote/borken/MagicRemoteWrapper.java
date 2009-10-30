package ussr.remote.borken;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.remote.SimulationAccessError;


public class MagicRemoteWrapper extends UnicastRemoteObject implements RemoteWrapper {
    private Object target;
    
    public MagicRemoteWrapper(Object target) throws RemoteException {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T wrap(Class<T> clazz, Object target) throws RemoteException {
        MagicRemoteWrapper wrapper = new MagicRemoteWrapper(target);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new MagicHandlerHelper(wrapper));
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws RemoteException {
        try {
            Method targetMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            return targetMethod.invoke(target, args);
        } catch(NoSuchMethodException exn) {
            throw new SimulationAccessError("Unable to access remote method: "+method.getName());
        } catch(Throwable t) {
            throw new RemoteException(t.toString());
        }
    }

}
