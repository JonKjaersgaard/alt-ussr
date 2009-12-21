package ussr.remote.facade;


import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.jme.math.Vector3f;

import ussr.builder.constructionTools.ATRONOperationsTemplate;
import ussr.builder.constructionTools.CKBotOperationsTemplate;
import ussr.builder.constructionTools.CommonOperationsTemplate;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.MTRANOperationsTemplate;
import ussr.builder.constructionTools.OdinOperationsTemplate;
import ussr.builder.controllerAdjustmentTool.AssignControllerTool;
import ussr.builder.enumerations.ConstructionTools;
import ussr.builder.enumerations.LabeledEntities;
import ussr.builder.enumerations.LabelingTools;
import ussr.builder.enumerations.SupportedModularRobots;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.genericTools.ColorModuleConnectors;
import ussr.builder.genericTools.RemoveModule;
import ussr.builder.helpers.BuilderHelper;
import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.ckbot.CKBotSimulation;
import ussr.samples.mtran.MTRANSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.Odin;


/**
 * Wrapper for a builder control allowing it to be used as a remote object.
 * (Used on the simulation side.)
 * @author Konstantinas
 *
 */
public class BuilderControlWrapper extends UnicastRemoteObject implements BuilderControlInter{
	
	/**
	 * JME level simulation.
	 */
	private JMESimulation jmeSimulation;	

	/**
	 * Wrapper for a builder control allowing it to be used as a remote object.
     * (Used on the simulation side.)
	 * @param jmeSimulation
	 * @throws RemoteException
	 */
	public BuilderControlWrapper(JMESimulation jmeSimulation) throws RemoteException{
		this.jmeSimulation = jmeSimulation;
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
		//jmeSimulation.getModules().removeAll(modules);
		jmeSimulation.getModules().clear();
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
		
		
		if (moduleExists(defaultPosition)){//do nothing
		}else{ 
		switch (supportedModularRobot){
		case ATRON:		
				comATRON.addDefaultConstructionModule("default", defaultPosition);			
			break;
		case MTRAN:		
				comMTRAN.addDefaultConstructionModule(SupportedModularRobots.MTRAN.toString(),defaultPosition );
			break;
		case ODIN:		
				Odin.setDefaultConnectorSize(0.006f);// make connectors bigger in order to select them successfully with "on Connector tool"
				comOdin.addDefaultConstructionModule(SupportedModularRobots.ODIN.toString(), defaultPosition);
			break;
		case CKBOTSTANDARD:
				comCKBot.addDefaultConstructionModule(SupportedModularRobots.CKBOTSTANDARD.toString(), defaultPosition);
			break;
		default: throw new Error ("Modular robot with the name "+ supportedModularRobot.toString()+ " is not supported yet");
		}
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

	/**
	 * Returns module from the end of the list of modules in simulation environment.
	 * @param amountFromLastMode, amount of modules from the last module in the list.
	 * @return Module, requested module from the end of the list.
	 */
	private Module getModuleCountingFromEnd(int amountFromLastMode ) throws RemoteException {
		int amountModules = jmeSimulation.getModules().size();
		if (amountModules >= amountFromLastMode){
			Module requestedModule= jmeSimulation.getModules().get(amountModules-amountFromLastMode);
			return requestedModule;
		}else{
			throw new Error("Not enough of modules in smulation environment to get "+amountFromLastMode+ "from last module" );
		}
	}

	/**
	 * Returns the type of the module from the end of the list of modules in simulation environment.
	 * @param amountFromLastMode, amount of modules from the last module in the list.
	 * @return String, the type of requested module from the end of the list. 
	 */
	public String getModuleCountingFromEndType(int amountFromLastMode ) throws RemoteException{
		return getModuleCountingFromEnd(amountFromLastMode).getProperty(BuilderHelper.getModuleTypeKey());
	};
		
	/**
	 * Returns the type of the module according to its number sequence in the list of modules.
	 * @param moduleNr, number of the module in the list of modules.
	 * @return type, the type of the module.
	 */
	public String getModuleType(int moduleNr) throws RemoteException{
	 return jmeSimulation.getModules().get(moduleNr).getProperty(BuilderHelper.getModuleTypeKey()); 
	};
	
	/**
	 * Sets picker for moving modular robots (left side of the mouse selection) during running state of simulation.
	 */
	public void setDefaultPicker() throws RemoteException  {
		jmeSimulation.setPicker(new PhysicsPicker());
	}

	/**
	 * Sets picker for removing(deleting) module, selected in simulation environment(paused state, also works in dynamic state however is it not recommended ).
	 */
	public void setRemoveModulePicker() throws RemoteException {
		jmeSimulation.setPicker(new RemoveModule());		
	}
	
	/**
	 * Sets picker for moving module(left side of the mouse selection), selected in simulation environment(in paused state).
	 */
	public void setMoveModulePicker()throws RemoteException{
		jmeSimulation.setPicker(new PhysicsPicker(true,true));
	}

	/**
	 * Sets picker for coloring module connectors with color coding, selected in simulation environment.
	 */
	public void setColorModuleConnectorsPicker()throws RemoteException{
		jmeSimulation.setPicker(new ColorModuleConnectors());
	}
	
	/**
	 * Sets a number of pickers called by specific name. For example: ConstructionTools.AVAILABLE_ROTATIONS,
		ConstructionTools.MODULE_OPPOSITE_ROTATION, ConstructionTools.NEW_MODULE_ON_SELECTED_CONNECTOR and so on.
	 * @param toolName, the name of the picker.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(toolName));		
	}
	
	/**
	 * Sets a number of pickers called by specific name with String parameter. For now it is: ConstructionTools.STANDARD_ROTATIONS
	 * @param toolName, the name of the picker.
	 * @param parameter, String parameter.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName, String parameter)throws RemoteException{
		jmeSimulation.setPicker(new ConstructionToolSpecification(toolName,parameter));
	}
	
	/**
	 * Sets a number of pickers called by specific name with Integer parameter. For now it is: ConstructionTools.ON_CHOSEN_CONNECTOR_NR
	 * @param toolName, the name of the picker.
	 * @param parameter, Integer parameter.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName, int parameter)throws RemoteException{
		jmeSimulation.setPicker(new ConstructionToolSpecification(toolName,parameter));
	}
	
	/**
	 * Sets a picker for adjusting controller of module(s). 
	 * @param controllerLocationDirectory, the directory where the controller can be located.
	 */
	public void setAdjustControllerPicker(String controllerLocationDirectory)throws RemoteException{
		jmeSimulation.setPicker(new AssignControllerTool(controllerLocationDirectory));
	}
	
	public void setLabelingToolReadLabels(LabeledEntities entityName,LabelingTools toolName)throws RemoteException{
		jmeSimulation.setPicker(new LabelingToolSpecification(entityName,toolName));
	}
	
	public void setLabelingToolAssignLabels(LabeledEntities entityName,LabelingTools toolName, String labels)throws RemoteException{
		jmeSimulation.setPicker(new LabelingToolSpecification(entityName,labels,toolName));
	}
		
	public Module createModule(ModulePosition position, boolean assign)throws RemoteException{
		return jmeSimulation.createModule(position, assign);
	}
	
	/**
	 * Returns the list of IDs of all modules in simulation environment.
	 * @return the list of IDs of all modules in simulation environment.
	 */
	public List<Integer> getIDsModules() throws RemoteException{
		
		List<Integer> idsModules = new ArrayList<Integer>();
		
		List<Module> modules =jmeSimulation.getModules();
		for (Module currentModule: modules){
			idsModules.add(currentModule.getID());
		}		
		return idsModules; 
	}
	
	public void loadInXML(UssrXmlFileTypes ussrXmlFileType,String fileDirectoryName) throws RemoteException {
		SaveLoadXMLFileTemplateInter openXML = new InSimulationXMLSerializer(jmeSimulation);
		openXML.loadXMLfile(ussrXmlFileType, fileDirectoryName);		
	}
	
	/**
	 * 
	 */
	private static final Color colors[] = {Color.BLACK,Color.RED,Color.CYAN,Color.GRAY,Color.GREEN,Color.MAGENTA,
        Color.ORANGE,Color.PINK,Color.BLUE,Color.WHITE,Color.YELLOW,Color.LIGHT_GRAY};
	
	/**
	 * Colors connectors of all modules in simulation with color coding.
	 */
	public void colorConnectorsModules()throws RemoteException{
		for(Module module: jmeSimulation.getModules()){
			colorModuleConnectors(module,colors);			
		}		 
	}
	
	/**
	 * Colors connectors of module with color coding.
	 * @param module, the module.
	 * @param colors, the array of colors.
	 */
	private void colorModuleConnectors(Module module, Color[] colors){
		int nrConnectors = module.getConnectors().size();
		for (int connector=0; connector<nrConnectors;connector++){			
			module.getConnectors().get(connector).setColor(colors[connector]);			
		} 
	}
	
	/**
	 * Colors connectors of all modules in simulation with original colors of connectors.
	 */
	public void restoreOriginalColorsConnectors()throws RemoteException{
		for(Module module: jmeSimulation.getModules()){
			
			String moduleType = module.getProperty(BuilderHelper.getModuleTypeKey());
			
			String consistentMRName = SupportedModularRobots.getConsistentMRName(moduleType);
			
			switch(SupportedModularRobots.valueOf(consistentMRName)){
			case ATRON:
				colorModuleConnectors(module,SupportedModularRobots.ATRON_CONNECTORS_COLORS);
				break;
			case ODIN:
				colorModuleConnectors(module,SupportedModularRobots.ODIN_CONNECTORS_COLORS);
				break;
			case MTRAN:
				colorModuleConnectors(module,SupportedModularRobots.MTRAN_CONNECTORS_COLORS);
				break;
			case CKBOTSTANDARD:
				colorModuleConnectors(module,SupportedModularRobots.CKBOTSTANDARD_CONNECTORS_COLORS);
				break;			
			}			
		}	
		
	}
	
	/**
	 * Checks if new module was added after last time checked. 
	 * @param lastCheckAmountModules
	 * @return
	 */
	public boolean isNewModuleAdded(int lastCheckAmountModules)throws RemoteException{
		if (getIDsModules().size()>lastCheckAmountModules){
			return true;
		}
		return false;
		
	}
	
}
