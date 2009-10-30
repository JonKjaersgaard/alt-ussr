package ussr.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SimulationServer extends Remote {
    static final String SERVER_RMI_ID = "USSR";
    static final int SERVER_PORT = 54323;

    public void register(int id, RemoteActiveSimulation simulation) throws RemoteException;
}
