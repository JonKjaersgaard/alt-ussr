package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;
import ussr.builder.simulationLoader.SimulationSpecification;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;

public interface XMLSimulationProviderInter extends Remote {

	
	public String getRobotMorphologyLocation()throws RemoteException;
	

	public String getIDsModules()throws RemoteException;
	
	public SimulationSpecification getSimulationSpecification()throws RemoteException;
}
