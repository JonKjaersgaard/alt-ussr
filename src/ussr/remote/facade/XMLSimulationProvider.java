package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;
import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.builder.simulationLoader.SimulationXMLFileLoader;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;

public class XMLSimulationProvider extends UnicastRemoteObject implements XMLSimulationProviderInter {

	private SimulationXMLFileLoader simulationLoader;
	
	public XMLSimulationProvider(SimulationXMLFileLoader simulationLoader) throws RemoteException {
		this.simulationLoader=simulationLoader;
	}
	
	public String getRobotMorphologyLocation()throws RemoteException{
		return simulationLoader.getRobotMorphologyLocation();
	}
		
	public String getIDsModules()throws RemoteException{
		return simulationLoader.getIdsModules();
		
	}
	
	public SimulationSpecification getSimulationSpecification()throws RemoteException{
		return simulationLoader.getSimulationSpecification();
	}
}
