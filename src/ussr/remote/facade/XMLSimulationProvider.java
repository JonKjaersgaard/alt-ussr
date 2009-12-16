package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.builder.simulationLoader.SimulationXMLFileLoader;

/**
 * @author Konstantinas
 *
 */
public class XMLSimulationProvider extends UnicastRemoteObject implements XMLSimulationProviderInter {

	/**
	 * 
	 */
	private SimulationXMLFileLoader simulationLoader;
	
	public XMLSimulationProvider(SimulationXMLFileLoader simulationLoader) throws RemoteException {
		this.simulationLoader=simulationLoader;
	}
	
	public SimulationSpecification getSimulationSpecification()throws RemoteException{
		return simulationLoader.getSimulationSpecification();
	}
	

}
