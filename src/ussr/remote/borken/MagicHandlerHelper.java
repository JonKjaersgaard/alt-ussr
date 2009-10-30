package ussr.remote.borken;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ussr.remote.RemoteWrapper;

public class MagicHandlerHelper implements Serializable, InvocationHandler {

    private RemoteWrapper wrapper;

    public MagicHandlerHelper(RemoteWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return wrapper.invoke(proxy, method, args);
    }

}
