package ussr.remote;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.remote.facade.RemotePhysicsSimulation;

/**
 * Proxy for an active simulation that has been launched using a Process
 * and is controlled using RMI
 *
 * (Proxy object used on frontend side to control simulation process)
 * @author ups
 */
public class RMIProcessActiveSimulation implements ActiveSimulation {

    /**
     * The process used to initially launch the simulator
     */
    private Process process;
    private int id;
    private RemoteActiveSimulation remoteSimulation;
    
    public RMIProcessActiveSimulation(int id, Process process) {
        this.id = id; this.process = process;
    }

    public InputStream getStandardOut() {
        return process.getInputStream();
    }
    
    public InputStream getStandardErr() {
        return process.getErrorStream();
    }
    
    public RemotePhysicsSimulation getSimulation() throws RemoteException {
        verifyRemote();
        return remoteSimulation.getSimulation();
    }
    
    public void start(PhysicsParameters parameters, PhysicsFactory.Options options, WorldDescription world) throws RemoteException {
        verifyRemote();
        remoteSimulation.start(parameters, options, world);
    }

    private void verifyRemote() {
        if(remoteSimulation==null) throw new SimulationAccessError("Simulation subprocess not ready");
    }

    public int getID() {
        return id;
    }

    public synchronized void setRemoteSimulation(RemoteActiveSimulation remoteSimulation) {
        this.remoteSimulation = remoteSimulation;
        this.notifyAll();
    }

    public void start(String simulationXML, Class<? extends Controller> controller) throws RemoteException {
        verifyRemote();
        Set<Class<? extends Controller>> set = new HashSet<Class<? extends Controller>>();
        set.add(controller);
        this.start(simulationXML, set);
    }

    public void start(String simulationXML, Set<Class<? extends Controller>> controllers) throws RemoteException {
        verifyRemote();
        remoteSimulation.start(simulationXML, controllers);
    }

    public boolean isReady() {
        return remoteSimulation!=null;
    }

    public synchronized void waitForReady() {
        try {
            while(!isReady())
                this.wait();
        } catch(InterruptedException exn) {
            throw new Error("Unexpected interruption");
        }
    }
}
