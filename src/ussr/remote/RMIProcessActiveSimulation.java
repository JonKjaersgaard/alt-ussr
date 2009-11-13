package ussr.remote;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ussr.builder.SimulationDescriptionConverter;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.remote.facade.ActiveSimulation;
import ussr.remote.facade.ParameterHolder;
import ussr.remote.facade.RemoteActiveSimulation;
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
    
    /**
     * The internal ID used to hook the process up with the corresponding RMI object
     */
    private int id;
    
    /**
     * The remote RMI object corresponding to the simulation process
     */
    private RemoteActiveSimulation remoteSimulation;
    
    /**
     * Copy of parameters from subprocess, if any
     */
    private ParameterHolder parameters;
    
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

    public void start(Class<?> mainClass, ParameterHolder parameter, ReturnValueHandler handler) throws RemoteException {
        verifyRemote();
        remoteSimulation.start(mainClass, parameter, new ReturnValueHandlerWrapper(handler));
    }

    public void start(Class<?> mainClass) throws RemoteException {
        verifyRemote();
        remoteSimulation.start(mainClass);
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

    public void discardStandardErr() {
        eatStream(this.getStandardErr());
    }

    public void discardStandardOut() {
        eatStream(this.getStandardOut());
    }

    /**
     * Discard contents of a stream
     * @param unusedName unused name parameter, used for convenience when switching to dumpStream
     * @param stream 
     */
    private static void eatStream(final InputStream stream) {
        new Thread() {
            public void run() {
                try {
                    while(stream.read()!=-1);
                    } catch (IOException e) {
                        throw new Error("Unable to dump stream: "+e); 
                    }
                }
        }.start();
    }

    public void setParameters(ParameterHolder parameters) {
        this.parameters = parameters;
    }
    
    public ParameterHolder getParameters() {
        return parameters;
    }

    /**
     * Starts a remote simulation from XML file.
     * @param simulationXMLFile, the location of xml file.
     * @throws RemoteException
     */
	public void start(String simulationXMLFile) throws RemoteException {
		 verifyRemote();
	     remoteSimulation.start(simulationXMLFile);
		
	}
}
