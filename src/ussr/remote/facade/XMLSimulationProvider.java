package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import ussr.builder.SimulationXMLFileLoader;
import ussr.description.geometry.VectorDescription;

public class XMLSimulationProvider extends UnicastRemoteObject implements XMLSimulationProviderInter {

	private SimulationXMLFileLoader simulationLoader;
	
	public XMLSimulationProvider(SimulationXMLFileLoader simulationLoader) throws RemoteException {
		super();
		this.simulationLoader=simulationLoader;
	}
	
	public String getRobotMorphologyLocation()throws RemoteException{
		return simulationLoader.getRobotMorphologyLocation();
	}

	@Override
	public Map<String, VectorDescription> getRobotModules()throws RemoteException {
		return simulationLoader.getRobotModules();
	}

}
