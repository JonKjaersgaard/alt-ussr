package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.builder.simulationLoader.SimulationSpecification;


public interface XMLSimulationProviderInter extends Remote {
	
	public SimulationSpecification getSimulationSpecification()throws RemoteException;
	
	
}
