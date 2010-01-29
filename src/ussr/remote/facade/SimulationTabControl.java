package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

/**
 * 
 * Wrapper for a simulation tab("ussr.aGui.tabs.simulation") control allowing it to be used as a remote object.
 * (Used on the simulation side.)
 * @author Konstantinas
 *
 **/
public class SimulationTabControl extends UnicastRemoteObject implements SimulationTabControlInter{

	/**
	 * JME level simulation.
	 */
	private JMESimulation jmeSimulation;

	
	/**
	 * Wrapper for a simulation tab("ussr.aGui.tabs.simulation") control allowing it to be used as a remote object.
     * (Used on the simulation side.)
	 * @param jmeSimulation
	 */
	public SimulationTabControl(JMESimulation jmeSimulation) throws RemoteException {
		this.jmeSimulation = jmeSimulation;
	}

	/**
	 * Moves module to new position.
	 * @param moduleID, the unique global ID of module.
	 * @param newModulePosition, new position for module.
	 */	
	public void setModulePosition(int moduleID,VectorDescription newModulePosition)throws RemoteException{
		
		int amountModules = jmeSimulation.getModules().size();
		for (int index=0; index<amountModules; index++){
			
			if (jmeSimulation.getModules().get(index).getID()==moduleID){
				jmeSimulation.getModules().get(index).setPosition(newModulePosition);
			}
			
		}
	}
	
	/**
	 * Returns module position
	 * @param moduleID, the unique global ID of the module.
	 * @return module position.
	 */
	public VectorDescription getModulePosition(int moduleID)throws RemoteException{
		int amountModules = jmeSimulation.getModules().size();
		for (int index=0; index<amountModules; index++){
			if (jmeSimulation.getModules().get(index).getID()==moduleID){
				return  jmeSimulation.getModules().get(index).getPhysics().get(0).getPosition();
			}
		}
		return null;
	}
	
	private int amountModules =0;
	
	/**
	 * Removes modules from simulation environment.
	 * @param ids, the list of ids of modules to remove.
	 * FIXME SOMETIMES FAILS! REASON IS NOT YET CLEAR.
	 * 	 */
	public void deleteModules(List<Integer> ids)throws RemoteException{

		for(int moduleID=0;moduleID<ids.size();moduleID++){
			 amountModules = jmeSimulation.getModules().size();
			
			//System.out.println("Size List:"+ jmeSimulation.getModules().size());
			
			for (int index=0; index<amountModules; index++){				
				//System.out.println("ID:"+ ids.get(moduleID));
				int currentModuleID = jmeSimulation.getModules().get(index).getID();
				Module currentModule =jmeSimulation.getModules().get(index);
				int moduleToDeleteID = ids.get(moduleID);
				
				if (currentModuleID==moduleToDeleteID ){
					
					BuilderHelper.deleteModule(currentModule,true);
					
					PhysicsSimulation simulation= currentModule.getSimulation();
					List<Module> modules = simulation.getModules();
					modules.remove(currentModule);
					JMESimulation jmeSimulation =(JMESimulation)simulation;
					jmeSimulation.setModules(modules);
					
					//PhysicsSimulation physicsSimulation = currentModule.getSimulation();
					//physicsSimulation.getModules().remove(currentModule);
					
					//amountModules = jmeSimulation.getModules().size();
					break;
					
				}
				
			}
			
		}
		
	}
	
}
