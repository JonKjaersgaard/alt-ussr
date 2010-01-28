package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.builder.simulationLoader.SimulationSpecification;

/**
 * Provides with remote access to an object describing remote simulation specification loaded from XML file.  
 * (Used on the simulation side.)
 * @author Konstantinas
 *
 */
public interface XMLSimulationProviderInter extends Remote {
	
	/**
	 * Returns the object describing remote simulation.
	 * @return the object describing remote simulation.
	 */
	public SimulationSpecification getSimulationSpecification()throws RemoteException;
}
