package ussr.samples.atron.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import ussr.samples.atron.framework.ATRONFramework.Role;
import ussr.samples.atron.framework.annotations.RemoteRole;
import ussr.samples.atron.framework.comm.RPCSystem;

/**
 * A virtual connection to a remote role, used as InvocationHandler so that it can
 * provide a proxy to all remote modules that have the corresponding role.
 * @author ups
 *
 */
public class Connection<R extends RemoteRole> implements InvocationHandler {
    /**
     * The RPC communication system used to interact with remote modules
     */
    private RPCSystem rpc;
    /**
     * The interface qualifier (remote role) of the connection 
     */
    private Class<R> qualifier;
    
    /**
     * Create a connection communication on a given RPC system, representing the given interface qualifier
     * @param rpc The RPC system used for communication
     * @param qualifier The remote role qualifier
     */
    public Connection(RPCSystem _rpc, Class<R> _qualifier) {
        this.rpc = _rpc; this.qualifier = _qualifier;
    }

    /**
     * Create a proxy representing all remote modules playing a given role at any given point in time,
     * e.g. determined dynamically based on what modules currently are playing the role
     * @return the proxy representing remote modules
     */
    public R getAll() {
        return (R)Proxy.newProxyInstance(qualifier.getClassLoader(), new Class[] { qualifier }, this);
    }

    /**
     * Handler method for the proxy, delegates the call to the RPC mechanism, returns null
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        rpc.callProcedure((Class<RemoteRole>)qualifier, method, arguments);
        return null;
    }

}
