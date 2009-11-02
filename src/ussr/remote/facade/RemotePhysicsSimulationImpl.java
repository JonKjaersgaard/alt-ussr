package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsSimulation;

/**
 * Wrapper for a standard PhysicsSimulation allowing it to be used as a remote object.
 * (Wrapper for the simulation, used on the simulation side.)
 * 
 * Note to developers: additional methods for controlling a remote simulation can be added
 * here; RMI beginners should consider whether objects implement Serializable (and hence are
 * copied when passed as an argument to or returned from methods in this class) or should be
 * proxy objects like this one extending UnicastRemoteObject (and hence remain in the frontend
 * process when passed as argument or remain the simulator process when returned)
 * 
 * @author ups
 *
 */
public class RemotePhysicsSimulationImpl extends UnicastRemoteObject implements RemotePhysicsSimulation {
    private PhysicsSimulation simulation;
    
    public RemotePhysicsSimulationImpl(PhysicsSimulation simulation) throws RemoteException {
        this.simulation = simulation;
    }

    public float getTime() throws RemoteException {
       return simulation.getTime();
    }

    public boolean isPaused() throws RemoteException {
        return simulation.isPaused();
    }

    public boolean isStopped() throws RemoteException {
        return simulation.isStopped();
    }

    public void setRobot(Robot bot) throws RemoteException {
        simulation.setRobot(bot);
    }

    public void setRobot(Robot bot, String type) throws RemoteException {
        simulation.setRobot(bot, type);
    }

    public void setWorld(WorldDescription world) throws RemoteException {
        simulation.setWorld(world);
    }

    public void start() throws RemoteException {
        simulation.start();
    }

    public void stop() throws RemoteException {
        simulation.stop();
    }

}
