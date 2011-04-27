package ussr.samples.atron.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import ussr.samples.atron.framework.ATRONFramework.Role;
import ussr.samples.atron.framework.annotations.RemoteRole;
import ussr.samples.atron.framework.comm.RPCSystem;

public class Connection<R extends RemoteRole> implements InvocationHandler {
    private RPCSystem rpc;
    private Class<R> qualifier;
    private Role role;
    
    public Connection(RPCSystem _rpc, Class<R> _qualifier, Role _role) {
        this.rpc = _rpc; this.qualifier = _qualifier; this.role = _role;
    }

    public R getAll() {
        return (R)Proxy.newProxyInstance(qualifier.getClassLoader(), new Class[] { qualifier }, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        rpc.callProcedure((Class<RemoteRole>)qualifier, method, arguments);
        return null;
    }

}
