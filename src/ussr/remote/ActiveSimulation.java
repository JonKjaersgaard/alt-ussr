package ussr.remote;

import java.io.InputStream;
import java.rmi.RemoteException;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.simulations.ATRONCarController1;

public interface ActiveSimulation extends RemoteActiveSimulation {
    public InputStream getStandardOut();
    public InputStream getStandardErr();
    public void start(String simulationXML, Class<? extends Controller> controller) throws RemoteException;
    public boolean isReady();
    public void waitForReady();
}
