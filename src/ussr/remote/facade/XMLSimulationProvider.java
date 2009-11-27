package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.builder.SimulationXMLFileLoader;

public class XMLSimulationProvider extends UnicastRemoteObject implements XMLSimulationProviderInter {

	private SimulationXMLFileLoader simulationLoader;
	
	public XMLSimulationProvider(SimulationXMLFileLoader simulationLoader) throws RemoteException {
		super();
		this.simulationLoader=simulationLoader;
	}
	
	public String getRobotMorphologyLocation()throws RemoteException{
		return simulationLoader.getRobotMorphologyLocation();
	}

}
