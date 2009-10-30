package ussr.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.remote.facade.RemotePhysicsSimulation;

public interface RemoteActiveSimulation extends Remote {
    public RemotePhysicsSimulation getSimulation() throws RemoteException;
    public void start(PhysicsParameters parameters, PhysicsFactory.Options options, WorldDescription world) throws RemoteException;
    public void start(String simulationXML, Set<Class<? extends Controller>> controllers) throws RemoteException;
}
