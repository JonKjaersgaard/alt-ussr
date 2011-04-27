package ussr.samples.atron.framework.comm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import ussr.samples.atron.framework.ATRONFramework;
import ussr.samples.atron.framework.annotations.RemoteRole;

/**
 * 
 * @author ups
 *
 * RPCSystem implements a remote procedure invocation mechanism on top of of
 * the CommunicationManager.  Procedures are identified by an interface and a Method
 *
 */
public class RPCSystem implements MessageListener.AddressBased {
    
    /**
     * Qualifier interface + method is used as an address, this information should either
     * be predefined or shared between modules; to avoid having to predefine the numerical
     * values all these combinations are assigned a unique integer, stored in this table.
     * @author ups
     */
    private static class Global {
        public static Map<Integer,Target> rpcTable_toTarget = new HashMap<Integer,Target>();
        public static Map<Target,Integer> rpcTable_toCode = new HashMap<Target,Integer>();
        private static int rpc_id = 1;
        public synchronized static int registerProcedure(Class<RemoteRole> roleClass, Method command) {
            Target target = new Target(roleClass,command);
            if(rpcTable_toCode.containsKey(target)) return rpcTable_toCode.get(target);
            rpcTable_toTarget.put(rpc_id, target);
            rpcTable_toCode.put(target, rpc_id);
            return rpc_id++;
        }
    }
    
    private CommunicationManager communication;
    private Object receiver;
    
    public RPCSystem(ATRONFramework controller) {
        communication = new CommunicationManager(this,controller);
    }
    
    public void registerProcedure(Class<RemoteRole> qualifier, Method command) {
        int code = Global.registerProcedure(qualifier,command);
        System.out.println("RPC: registered "+qualifier.getName()+"."+command.getName()+" as "+code);
        communication.registerMultiCastAddress(code);
    }

    public void callProcedure(Class<RemoteRole> qualifier, Method method, Object[] arguments) {
        Integer codeMaybe = Global.rpcTable_toCode.get(new Target(qualifier,method));
        if(codeMaybe==null) throw new Error("Unknown qualifier/method combination: "+qualifier.getName()+"."+method.getName());
        int code = codeMaybe;
        int[] int_arguments = new int[arguments.length];
        for(int i=0; i<arguments.length; i++) {
            if(!(arguments[i] instanceof Integer)) throw new Error("RPC only supports integer arguments");
            int_arguments[i] = (Integer)arguments[i];
        }
        communication.sendMultiCast(code, int_arguments);
    }

    @Override
    public void messageReceived(int connector, int code, int[] arguments) {
        Target target = Global.rpcTable_toTarget.get(code);
        if(target==null) throw new Error("RPC received undefined command code");
        if(!target.getTargetClass().isAssignableFrom(receiver.getClass())) return;
        Object[] argumentsAsObject = new Object[arguments.length];
        for(int i=0; i<arguments.length; i++) argumentsAsObject[i] = arguments[i];
        try {
            target.getCommand().invoke(receiver, argumentsAsObject);
        } catch (IllegalArgumentException e) {
            throw new Error("Internal error: "+e);
        } catch (IllegalAccessException e) {
            throw new Error("Internal error: "+e);
        } catch (InvocationTargetException e) {
            throw new Error("Error during invocation: "+e.getTargetException());
        }
    }

    public void setReceiver(Object _receiver) {
        this.receiver = _receiver;
    }

}
