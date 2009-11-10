package ussr.remote.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.samples.GenericModuleConnectorHelper;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.ckbot.CKBotSimulation;
import ussr.samples.mtran.MTRANSimulation;
import ussr.samples.odin.OdinBuilder;
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
			
			/* Identify each component of the module and remove the visual of it*/
			int amountComponents= currentModule.getNumberOfComponents();		
			for (int compon=0; compon<amountComponents;compon++){			
				BuilderHelper.removeModuleComponent(currentModule.getComponent(compon));  
			}
		}
		/*Remove all modules from  internal list of the modules in USSR*/
		jmeSimulation.getModules().removeAll(modules);
	}


	/**
	 * Default position of initial construction module.
	 *
	 */
	private VectorDescription defaultPosition = new VectorDescription(0,-0.441f,0.7f);


	/**
	 * Adds default(first) construction module in simulation environment.
	 * @param SupportedModularRobot, the type of module to add. 
	 */
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
	 * Checks if module is occupying specific position in simulation environment. 
	 * @param prosition, position in simulation environment
	 * @return true, if module exists at this position.
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
	
	/**
	 * Connects all modules existing in simulation environment.
	 */
	public  void connectAllModules()throws RemoteException{
		if (jmeSimulation.worldDescription.getModulePositions().size()>=0){

			int amountModules = jmeSimulation.getModules().size();
			ArrayList<ModulePosition> atronModulePositions = new ArrayList<ModulePosition>();
			ArrayList<ModulePosition> mtranModulePositions = new ArrayList<ModulePosition>(); 
			ArrayList<ModulePosition> odinAllModulePositions = new ArrayList<ModulePosition>();
			ArrayList<ModulePosition> odinBallModulePositions = new ArrayList<ModulePosition>(); 
			ArrayList<ModulePosition> odinOtherModulesPositions = new ArrayList<ModulePosition>();
			ArrayList<ModulePosition> ckbotModulePositions = new ArrayList<ModulePosition>();

			List<Module> atronModules = new ArrayList<Module>();
			List<Module> mtranModules = new ArrayList<Module>();
			List<Module> odinAllModules = new ArrayList<Module>();
			List<Module> ckbotModules = new ArrayList<Module>();


			for (int i=0; i<amountModules; i++){
				Module currentModule = jmeSimulation.getModules().get(i);
				//currentModule.reset();
				String moduleName = currentModule.getProperty(BuilderHelper.getModuleNameKey());
				String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());

				RotationDescription moduleRotation = currentModule.getPhysics().get(0).getRotation(); 
				if (moduleType.contains("ATRON")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					atronModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					atronModules.add(currentModule);             			
				}else if (moduleType.contains("MTRAN")){ 
					VectorDescription modulePosition = currentModule.getPhysics().get(1).getPosition();
					mtranModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));             			
					mtranModules.add(currentModule);             			
				}else if (moduleType.contains("Odin")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					odinAllModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					odinAllModules.add(currentModule);

					if (moduleType.contains("OdinBall")){
						odinBallModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					}else {
						odinOtherModulesPositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					}
				}else if (moduleType.contains("CKBotStandard")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					ckbotModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					ckbotModules.add(currentModule);    
				}
				else {
					// do nothing
				}
			}         	

			ATRONBuilder atronbuilder = new ATRONBuilder();             
			ArrayList<ModuleConnection> atronModuleConnection = atronbuilder.allConnections(atronModulePositions);        	 
			jmeSimulation.setModules(atronModules);
			jmeSimulation.worldDescription.setModulePositions(atronModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(atronModuleConnection);                          
			jmeSimulation.placeModules();

			ArrayList<ModuleConnection> mtranModuleConnection =MTRANSimulation.allConnections(mtranModulePositions); 
			jmeSimulation.setModules(mtranModules);
			jmeSimulation.worldDescription.setModulePositions(mtranModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(mtranModuleConnection); 
			jmeSimulation.placeModules();              

			OdinBuilder odinBuilder = new OdinBuilder();
			odinBuilder.setBallPos(odinBallModulePositions);
			odinBuilder.setModulePos(odinOtherModulesPositions);             
			ArrayList<ModuleConnection> odinModuleConnection = odinBuilder.allConnections();        	 
			jmeSimulation.setModules(odinAllModules);
			jmeSimulation.worldDescription.setModulePositions(odinAllModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(odinModuleConnection);                          
			jmeSimulation.placeModules();			

			ArrayList<ModuleConnection> ckbotModuleConnection = CKBotSimulation.allConnections(ckbotModulePositions);        	 
			jmeSimulation.setModules(ckbotModules);
			jmeSimulation.worldDescription.setModulePositions(ckbotModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(ckbotModuleConnection);                          
			jmeSimulation.placeModules();
		}       
	}
}
