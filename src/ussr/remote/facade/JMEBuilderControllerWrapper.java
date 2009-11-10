package ussr.remote.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.constructionTools.ATRONOperationsTemplate;
import ussr.builder.constructionTools.CKBotOperationsTemplate;
import ussr.builder.constructionTools.CommonOperationsTemplate;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.MTRANOperationsTemplate;
import ussr.builder.constructionTools.OdinOperationsTemplate;
import ussr.builder.enums.ConstructionTools;
import ussr.builder.enums.SupportedModularRobots;
import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription.CameraPosition;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.samples.odin.modules.Odin;


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

		List<Module> modules =  jmeSimulation.getModules();
		/*Loop through the modules in simulation*/
		for (int moduleNr =0; moduleNr<jmeSimulation.getModules().size();moduleNr++){

			Module currentModule = jmeSimulation.getModules().get(moduleNr);			
			BuilderHelper.deleteModule(currentModule);

		}
		/*Remove all modules from  internal list of the modules in USSR*/
		jmeSimulation.getModules().removeAll(modules);
	}


	/**
	 * Default position of initial construction module.
	 *
	 */
	private VectorDescription defaultPosition = new VectorDescription(0,-0.441f,0.7f);


	public void addInitialConstructionModule (SupportedModularRobots supportedModularRobot) throws RemoteException{
		CommonOperationsTemplate comATRON = new ATRONOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comMTRAN = new MTRANOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comOdin = new OdinOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comCKBot = new CKBotOperationsTemplate(jmeSimulation);	

		switch (supportedModularRobot){
		case ATRON:
			if (moduleExists(defaultPosition)){//do nothing
			}else{ 
				comATRON.addDefaultConstructionModule("default", defaultPosition);
				//NOT WORKING//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		case MTRAN:
			if (moduleExists(defaultPosition)){//do nothing
			}else {
				comMTRAN.addDefaultConstructionModule(SupportedModularRobots.MTRAN.toString(),defaultPosition );
				//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		case ODIN:
			if (moduleExists(defaultPosition)){//do nothing
			}else{ 
				Odin.setDefaultConnectorSize(0.006f);// make connectors bigger in order to select them successfully with "on Connector tool"
				comOdin.addDefaultConstructionModule(SupportedModularRobots.ODIN.toString(), defaultPosition);
				//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		case CKBOTSTANDARD:
			if (moduleExists(defaultPosition)){//do nothing
			}else {
				comCKBot.addDefaultConstructionModule(SupportedModularRobots.CKBOTSTANDARD.toString(), defaultPosition);
				//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		default: throw new Error ("Modular robot with the name "+ supportedModularRobot.toString()+ " is not supported yet");
		}
	}

	/**
	 * Checks if module already exists at current position.
	 * TODO SHOULD CHANGE WHEN MODULE WILL BE ADDED TO VIEW POINT OR ROTATE CAMERA POSITION. 
	 * @param currentPosition, the position of the module to check.
	 * @param jmeSimulation, the physical simulation.    
	 *@return true, if module exists at current position.
	 */	
	public boolean moduleExists(VectorDescription currentPosition) throws RemoteException{
		int amountModules = jmeSimulation.getModules().size();
		for (int module =0;module<amountModules;module++){
			Module currentModule =jmeSimulation.getModules().get(module); 
			String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());
			VectorDescription modulePosition;
			if (moduleType.equalsIgnoreCase("MTRAN")){
				modulePosition = currentModule.getPhysics().get(1).getPosition(); 
			}else{
				modulePosition = currentModule.getPhysics().get(0).getPosition();
			}
			if (modulePosition.equals(currentPosition)){
				return true;
			}
		}
		return false;    	
	}	
}
