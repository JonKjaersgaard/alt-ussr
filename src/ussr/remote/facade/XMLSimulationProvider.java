package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.builder.simulationLoader.SimulationXMLFileLoader;

/**
 * Wrapper for simulation specification control allowing it to be used as a remote object.
 * (Used on the simulation side.)
 * @author Konstantinas
 *
 */
public class XMLSimulationProvider extends UnicastRemoteObject implements XMLSimulationProviderInter {

	/**
	 * Object for loading simulation from xml file.
	 */
	private SimulationXMLFileLoader simulationLoader;
	
	
	/**
	 * Wrapper for simulation specification control allowing it to be used as a remote object.
     * (Used on the simulation side.)
	 * @param simulationLoader, object responsible for loading simulation from XML file.
	 * @throws RemoteException
	 */
	public XMLSimulationProvider(SimulationXMLFileLoader simulationLoader) throws RemoteException {
		this.simulationLoader=simulationLoader;
	}
	
	/**
	 * Returns the object describing remote simulation.
	 * @return the object describing remote simulation.
	 */
	public SimulationSpecification getSimulationSpecification()throws RemoteException{
		return simulationLoader.getSimulationSpecification();
	}
	

}
