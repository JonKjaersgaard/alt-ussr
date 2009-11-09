package ussr.remote.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.ConstructionTools;

import ussr.physics.jme.JMESimulation;


public class JMEBuilderControllerWrapper extends UnicastRemoteObject implements BuilderControlInter{
	private JMESimulation jmeSimulation;	
	
//STOPPED HERE
	//final static ConstructionToolSpecification OPPOSITE = new ConstructionToolSpecification(jmeSimulation,ConstructRobotTabController.getChosenMRname(),ConstructionTools.OPPOSITE_ROTATION);


	public JMEBuilderControllerWrapper(JMESimulation jmeSimulation) throws RemoteException{
		this.jmeSimulation = jmeSimulation;
	}

	@Override
	public void setPicker(BuilderSupportingPickers builderSupportingPicker)throws RemoteException {
		jmeSimulation.setPicker(builderSupportingPicker.getPicker());
		//jmeSimulation.setPicker(new ConstructionToolSpecification(ConstructRobotTabController.getChosenMRname(),ConstructionTools.STANDARD_ROTATIONS,ConstructRobotTabController.getChosenStandardRotation()));
	
		//jmeSimulation.setPicker(new ConstructionToolSpecification(ConstructRobotTabController.getChosenMRname(),ConstructionTools.ON_SELECTED_CONNECTOR));
		
	}
	
	//STOPPED HERE
/*	public void setConstructionPicker(ConstructionToolSpecification constructionToolSpecification)throws RemoteException {
		jmeSimulation.setPicker(constructionToolSpecification);		
	}*/
	
	
	
}
