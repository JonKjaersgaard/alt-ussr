package ussr.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.remote.facade.RemotePhysicsSimulation;

/**
 * Remote interface used internally for RMI operations.  See ActiveSimulation for a complete interface.
 * 
 * (Interface for proxy object used on frontend side to access simulation process) 
 * @author ups
 *
 */
public interface RemoteActiveSimulation extends Remote {
    /**
     * Obtain a remote reference to the running simulation, if available, null otherwise
     * @return remote reference or null if not available
     * @throws RemoteException
     */
    public RemotePhysicsSimulation getSimulation() throws RemoteException;
    /**
     * Start a remote simulation by directly setting the internal values. Blocks until simulation has completed running.
     * Termination normally results in a RemoteException being thrown 
     * @param parameters the PhysicsParameters to use when starting the simulation 
     * @param options the PhysicsFactory Options to use when starting the simulation
     * @param world the WorldDescription to use when starting the simulaton
     * @throws RemoteException
     */
    public void start(PhysicsParameters parameters, PhysicsFactory.Options options, WorldDescription world) throws RemoteException;
    /**
     * Start a remote simulation by defining the robot XML file to load and the set of controllers to assign to the
     * robot based on the required controller types.  Remote semantics similar to starting by directly setting the
     * internal values @see ussr.remote.RemoteActiveSimulation.start
     * @param simulationXML the name of the XML that contains a robot to load
     * @param controllers set of classes to assign as appropriate in the robot
     * @throws RemoteException
     */
    public void start(String simulationXML, Set<Class<? extends Controller>> controllers) throws RemoteException;
}
