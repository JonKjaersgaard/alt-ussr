package ussr.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface defining capabilities for a frontend server for simulations
 * @author ups
 *
 */
public interface SimulationServer extends Remote {
    /**
     * The RMI ID used to denote the simulations server on the name server running on the port
     * used to contact the simulation server
     */
    static final String SERVER_RMI_ID = "USSR";
    /**
     * Register a remote simulation with this simulation frontend
     * @param id the internal ID passed to the simulation process when starting it, used to
     * hook up the simulation to the corresponding proxy object
     * @param simulation the remote simulation
     * @throws RemoteException
     */
    public void register(int id, RemoteActiveSimulation simulation) throws RemoteException;
}
