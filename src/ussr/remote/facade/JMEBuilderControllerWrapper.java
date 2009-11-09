package ussr.remote.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.enums.ConstructionTools;
import ussr.builder.helpers.BuilderHelper;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;


public class JMEBuilderControllerWrapper extends UnicastRemoteObject implements BuilderControlInter{
	private JMESimulation jmeSimulation;	
	
//STOPPED HERE
	//final static ConstructionToolSpecification OPPOSITE = new ConstructionToolSpecification(jmeSimulation,ConstructRobotTabController.getChosenMRname(),ConstructionTools.OPPOSITE_ROTATION);


	public JMEBuilderControllerWrapper(JMESimulation jmeSimulation) throws RemoteException{
		this.jmeSimulation = jmeSimulation;
	}

	@Override
	public void setPicker(BuilderSupportingUnicastPickers builderSupportingPicker)throws RemoteException {
		jmeSimulation.setPicker(builderSupportingPicker.getPicker());
		//jmeSimulation.setPicker(new ConstructionToolSpecification(ConstructRobotTabController.getChosenMRname(),ConstructionTools.STANDARD_ROTATIONS,ConstructRobotTabController.getChosenStandardRotation()));
		//jmeSimulation.setPicker(new ConstructionToolSpecification(ConstructRobotTabController.getChosenMRname(),ConstructionTools.ON_SELECTED_CONNECTOR));
	}
	
	/**
	 * Removes all modules (robot(s)) from simulation environment.
	 */
	public void removeAllModules() throws RemoteException{
		/*Loop through the modules in simulation*/
		for (int moduleNr =0; moduleNr<jmeSimulation.getModules().size();moduleNr++){
			Module currentModule = jmeSimulation.getModules().get(moduleNr);
			
            /*Remove each module component*/
			for (int compon=0; compon<currentModule.getNumberOfComponents();compon++){			
				BuilderHelper.removeModuleComponent(currentModule.getComponent(compon));
			}	
		}
	}
	
	
	public JMESimulation getRemoteJMESimulation()throws RemoteException{
	return 	jmeSimulation;
	}
    
	
	
	
	
	
	
	
	
	//STOPPED HERE
/*	public void setConstructionPicker(ConstructionToolSpecification constructionToolSpecification)throws RemoteException {
		jmeSimulation.setPicker(constructionToolSpecification);		
	}*/
	
	
	
}