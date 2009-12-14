package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMESimulation;

public class SimulationTabControl extends UnicastRemoteObject implements SimulationTabControlInter{

	/**
	 * JME level simulation.
	 */
	private JMESimulation jmeSimulation;

	public SimulationTabControl(JMESimulation jmeSimulation) throws RemoteException {
		this.jmeSimulation = jmeSimulation;
	}

	
	public void setModulePosition(int moduleID,VectorDescription newModulePosition)throws RemoteException{
		
		int amountModules = jmeSimulation.getModules().size();
		for (int index=0; index<amountModules; index++){
			
			if (jmeSimulation.getModules().get(index).getID()==moduleID){
				jmeSimulation.getModules().get(index).setPosition(newModulePosition);
			}
			
		}
	}
	
	public VectorDescription getModulePosition(int moduleID)throws RemoteException{
		int amountModules = jmeSimulation.getModules().size();
		for (int index=0; index<amountModules; index++){
			
			if (jmeSimulation.getModules().get(index).getID()==moduleID){
				return  jmeSimulation.getModules().get(index).getPhysics().get(0).getPosition();
			}
			
		}
		return null;
	}
	
}
