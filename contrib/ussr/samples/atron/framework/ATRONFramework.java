package ussr.samples.atron.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import ussr.model.Sensor;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.framework.annotations.Behavior;
import ussr.samples.atron.framework.annotations.Handler;
import ussr.samples.atron.framework.annotations.RemoteRole;
import ussr.samples.atron.framework.annotations.Require;
import ussr.samples.atron.framework.annotations.Startup;
import ussr.samples.atron.framework.comm.CommunicationManager;
import ussr.samples.atron.framework.comm.MessageListener;
import ussr.samples.atron.framework.comm.RPCSystem;
import ussr.samples.atron.framework.util.Action;
import ussr.samples.atron.framework.util.EventHandler;

public abstract class ATRONFramework extends ATRONController {

    protected class Role implements Comparable<Role> {
        private String name;
        protected Role self = this;
        private int id;
        private List<EventHandler>[] proximityHandlers = new List[8];
                
        public Role() {
            setupEventHandlers();
        }
        
        public int getNumberOfConnections() {
            int result = 0;
            for(int i=0; i<8; i++)
                if(ATRONFramework.this.isConnected(i)) result++;
            return result;
        }

        public synchronized String getName() {
            if(name==null)
                name = module.getProperty("name");
            return name;
        }

        public void rotateContinuous(float rotationalSpeed) {
            ATRONFramework.this.rotateContinuous(rotationalSpeed);
        }

        public void setup() {
            ATRONFramework.this.setup();
        }

        public List<Sensor> getSensors() {
            return ATRONFramework.this.module.getSensors();
        }

        public boolean checkProximity(int i) {
            return ATRONFramework.this.isObjectNearby(i);
        }
        
        public <R extends RemoteRole> Connection<R> connectedTo(Class<R> qualifier) {
            return new Connection<R>(rpc,qualifier);
        }
     
        public void schedule(float delay, Action action) {
            ATRONFramework.this.schedule(delay,action);
        }

        public void setID(int _id) {
            this.id = _id;
        }

        @Override
        public int compareTo(Role arg) {
            if(this.getClass()==arg.getClass()) return 0;
            if(this.getClass().isAssignableFrom(arg.getClass())) return 1;
            if(arg.getClass().isAssignableFrom(this.getClass())) return -1;
            return this.getClass().getName().compareTo(arg.getClass().getName());
        }

        public void checkEventHandlers() {
            for(int i=0; i<8; i++)
                if(proximityHandlers[i]!=null && ATRONFramework.this.isObjectNearby(i)) {
                    for(EventHandler handler: proximityHandlers[i]) handler.invoke();
                }
        }

        private void setupEventHandlers() {
            for(Method handler: getMethodByAnnotation(this.getClass(),Handler.class)) {
                Handler annotation = handler.getAnnotation(Handler.class);
                int[] proximity = annotation.proximity();
                for(int i=0; i<proximity.length; i++) {
                    int index = proximity[i];
                    if(proximityHandlers[index]==null)
                        proximityHandlers[index] = new ArrayList<EventHandler>();
                    proximityHandlers[index].add(new EventHandler(this,handler));
                }
            }
        }

    }

    private Set<Role> roles = new TreeSet<Role>();
    private PriorityQueue<Action.TimedAction> actions = new PriorityQueue<Action.TimedAction>();
    private List<MessageListener.Raw> messageListeners = new ArrayList<MessageListener.Raw>();
    private Role activeRole;
    private RPCSystem rpc = new RPCSystem(this);

    public ATRONFramework() {
        Role[] _roles = this.getRoles();
        for(int i=0; i<_roles.length; i++) {
            Role role = _roles[i];
            role.setID(i);
            roles.add(role);
            registerCommandMethods(role);
        }
    }

    private void registerCommandMethods(Role role) {
        Class<?> roleClass = role.getClass();
        while(RemoteRole.class.isAssignableFrom(roleClass)) {
            for(Class<?> interf: roleClass.getInterfaces()) {
                if(RemoteRole.class.isAssignableFrom(interf)) {
                    Class<RemoteRole> remoteInterface = (Class<RemoteRole>)interf;
                    List<Method> commands = Arrays.asList(remoteInterface.getDeclaredMethods());
                    for(Method command: commands) 
                        rpc.registerProcedure(remoteInterface,command);
                }
            }
            roleClass = roleClass.getSuperclass();
        }
    }
    
    private void schedule(float delay, Action action) {
        actions.add(new Action.TimedAction(module.getSimulation().getTime()+delay,action));
    }
    
    protected abstract Role[] getRoles();
    
    @Override
    final public void activate() {
        assignRole();
        runMethodsByAnnotation(activeRole,Startup.class);
        while(true) {
            activeRole.checkEventHandlers();
            checkActionQueue();
            runMethodsByAnnotation(activeRole,Behavior.class);
        }
    }

    private void runMethodsByAnnotation(Role role, Class<? extends Annotation> annotation) {
        for(Method behavior: getMethodByAnnotation(role.getClass(),annotation)) {
            try {
                behavior.invoke(activeRole);
            } catch (IllegalArgumentException e) {
                throw new Error("Internal error: "+e);
            } catch (IllegalAccessException e) {
                throw new Error("Internal error: "+e);
            } catch (InvocationTargetException e) {
                throw new Error("Internal error: "+e);
            }
        }
    }

    private void checkActionQueue() {
        float time = this.module.getSimulation().getTime();
        while(true) {
            Action.TimedAction head = actions.peek();
            if(head==null || head.getTime()>time) break;
            actions.remove();
            head.getAction().action();
        }
    }

    private void assignRole() {
        roleLoop: for(Role candidate: roles) {
            List<Method> requires = getMethodByAnnotation(candidate.getClass(),Require.class);
            for(Method method: requires) {
                try {
                    Object result = method.invoke(candidate);
                    if(result instanceof Boolean) {
                        boolean satisfies = (Boolean)result;
                        if(!satisfies) continue roleLoop;
                    }
                } catch (IllegalArgumentException e) {
                    throw new Error("Internal error: illegal argument exception: "+e);
                } catch (IllegalAccessException e) {
                    throw new Error("Internal error: illegal access exception: "+e);
                } catch (InvocationTargetException e) {
                    throw new Error("Internal error: invocation target exception: "+e);
                }
            }
            activeRole = candidate;
            rpc.setReceiver(activeRole);
            System.out.println("Role "+candidate.getClass().getName()+" assigned to "+this.getName());
            return;
        }
        throw new Error("Failed to assign role to module "+this.getName());
    }

    private List<Method> getMethodByAnnotation(Class<?> candidate, Class<? extends Annotation> qualifier) {
        List<Method> result = new ArrayList<Method>();
        Method[] methods = candidate.getMethods();
        for(int i=0; i<methods.length; i++) {
            if(methods[i].getAnnotation(qualifier)!=null) result.add(methods[i]);
        }
        return result;
    }

    public void addMessageListener(MessageListener.Raw listener) {
        messageListeners.add(listener);
    }
    
    @Override
    public void handleMessage(byte[] message, int length, int connector) {
        int[] msgAsInt = new int[length];
        for(int i=0; i<length; i++)
            msgAsInt[i] = CommunicationManager.b2i(message[i]);
        for(MessageListener.Raw listener: messageListeners) listener.messageReceived(connector, msgAsInt);
    }

}
