package ussr.builder.construction;

import javax.swing.JOptionPane;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.builder.BuilderUtilities;

/**
 * @author Konstantinas
 *
 */
public class ConstructionToolSpecification extends CustomizedPicker{

	/** 
	 * The physical simulation.
	 */
	private JMESimulation simulation;
	
	/**
	 * The interface to construction of modular robot morphology. This one is on the level of modules of modular robot.  
	 */
	private SelectOperationsStrategy selectOperations;

	/**
	 * The interface to construction of modular robot morphology. This one is on the level of components of modules.  
	 */
	private ConstructionStrategy construction;

	/**
	 * The constant for ATRON modular robot.
	 */
	private static final String atron = "ATRON";
	
	/**
	 * The constant for MTRAN modular robot.
	 */
	private static final String mtran = "MTRAN";
	
	/**
	 * The constant for Odin modular robot.
	 */
	private static final String odin = "Odin";
	
    /**
     * The module selected in simulation environment with the left side of the mouse.
     */
    private Module selectedModule;    
    
	/**
	 * The connector number on the module, selected with the left side of mouse in simulation environment or chosen in GUI comboBox.
	 */	
	private int selectedConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 

	/**
	 * The name of the modular robot. For example: ATRON, M-Tran, Odin and so on
	 */
	private String modularRobotName;

	/**
	 * The name of the tool from GUI. For example these can be "OnConnector", "All","Loop" and so on.
	 */
	private String toolName;

	/**
	 * The name of rotations, which are standard to particular modular robot. For example for ATRON this can be EW, meaning east-west.
	 */
	private String standardRotationName;

	/**
	 * For calling tools handling construction of morphology of modular robot, in particular tools like "OnConnector" and "All". 
	 * @param simulation, the physical simulation. 
	 * @param modularRobotName,the name of the modular robot. For example: ATRON, MTRAN,Odin and so on.
	 * @param toolName,the name of the tool from GUI. For example, in this case, these can be "OnConnector" or "All".
	 */
	public  ConstructionToolSpecification(JMESimulation simulation, String modularRobotName, String toolName){
		this.simulation = simulation;
		this.modularRobotName = modularRobotName;
		this.toolName = toolName;
		assignConstructionStrategy();
	}

	/**
	 * For calling tools handling construction of morphology of modular robot,in particular tools like "ChosenConnector" or "Loop".
	 * @param simulation, the physical simulation.
	 * @param modularRobotName,the name of the modular robot. For example: ATRON, MTRAN,Odin and so on.
	 * @param toolName,the name of the tool from GUI. For example, in this  case, these can be "ChosenConnector" or "Loop".
	 * @param chosenConnectorNr,the connector number on module, chosen in GUI comboBox ("ChosenConnector")or just passed as default ("Loop").
	 */
	public  ConstructionToolSpecification(JMESimulation simulation, String modularRobotName, String toolName,int chosenConnectorNr){
		this.simulation = simulation;
		this.modularRobotName = modularRobotName;
		this.toolName = toolName;
		this.selectedConnectorNr = chosenConnectorNr;
		assignConstructionStrategy();
	}

	/**
	 * For calling tools handling construction of morphology of modular robot, in particular tools like "StandardRotation". 
	 * @param simulation, the physical simulation.
	 * @param modularRobotName, the name of the modular robot. For example: ATRON, MTRAN,Odin and so on.
	 * @param toolName, the name of the tool from GUI. For example, in this case, this is "StandardRotation".
	 * @param standardRotationName,the name of rotation, which is standard to particular modular robot. For example for ATRON this can be EW, meaning east-west.
	 */
	public  ConstructionToolSpecification(JMESimulation simulation, String modularRobotName, String toolName, String standardRotationName){
		this.simulation = simulation;
		this.modularRobotName = modularRobotName;
		this.toolName = toolName;
		this.standardRotationName = standardRotationName;
		assignConstructionStrategy();
	}
	
	/**
	 * Assigns specific object for construction of modular robot morphology and object for common 
	 * methods(operations), depending on which type of modular robot is passed in constructor. 
	 */
	private void assignConstructionStrategy(){
		if 	(this.modularRobotName.equalsIgnoreCase(atron)){
			this.construction = new ATRONConstructionStrategy();
		}else if (this.modularRobotName.equalsIgnoreCase(mtran)){
			this.construction = new MTRANConstructionStrategy();
		}else if (this.modularRobotName.equalsIgnoreCase(odin)){
			this.construction = new OdinConstructionStrategy();
		}else throw new Error("This modular robot is not supported yet or the name of it is misspelled");
		this.selectOperations = new CommonOperationsStrategy(this.simulation);
	}
	
	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here is identified the module selected in simulation environment, moreover checked if pickTarget()method
	 * resulted in success and the call for appropriate tool is made. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {		
		this.selectedModule = component.getModel();		
		callAppropriateTool();
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here the connector number is extracted from the string of TriMesh. Initial format of string is for example: "Connector 1 #1"
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {		
		if(target instanceof TriMesh) {			
			String name = simulation.getGeometryName((TriMesh)target);
			if(name!=null && name.contains("Connector")){ 
				//System.out.println("Connector: "+name);//For debugging				
				String [] temp = null;	         
				temp = name.split("#");// Split by #, into two parts, line describing the connector. For example "Connector 1 #1"
				this.selectedConnectorNr= Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)				
			}
		}		
	}	

	/**
	 * Checks if construction tool type is matching the module type selected in simulation environment. If
	 * yes calls appropriate tool. If no, For example: if the tool is for ATRON modular robot (modularRobotName) 
	 * and the module type selected in simulation environment is MTRAN. Then the method will complain.	 * 
	 */
	private void callAppropriateTool(){
		if (this.modularRobotName.equalsIgnoreCase(atron)&& isAtron()||this.modularRobotName.equalsIgnoreCase(mtran)&& isMtran()||this.modularRobotName.equalsIgnoreCase("odin")&&isOdin()){		
			callTool();	
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an "+modularRobotName+" module. The chosen tool is for "+ modularRobotName+ "modules!","Error", JOptionPane.ERROR_MESSAGE);// Inform the user
		}
	}

	/**
	 * Checks if the module selected in simulation environment is an ATRON module 
	 * @return true, if selected module is an ATRON module
	 */
	private boolean isAtron(){
		String typeofModule = this.selectedModule.getProperty(BuilderUtilities.getModuleTypeKey());		
		if (typeofModule.contains(atron)){
			return true;
		}
		return false;
	}

	/**
	 * Checks if the module selected in simulation environment is an MTRAN module 
	 * @return true, if selected module is an MTRAN module
	 */
	private boolean isMtran(){		
		String typeofModule = this.selectedModule.getProperty(BuilderUtilities.getModuleTypeKey());
		if (typeofModule.contains(mtran)){
			return true;
		}
		return false;
	}

	/**
	 * Checks if the module selected in simulation environment is an Odin module 
	 * @return true, if selected module is an Odin module
	 */
	private boolean isOdin(){
		String typeofModule = this.selectedModule.getProperty(BuilderUtilities.getModuleTypeKey());		
		if (typeofModule.contains(odin)){			
			return true;
		}
		return false;
	}

	/**
	 * Calls the tool for construction of modular robot morphology. 
	 */
	private void callTool(){

		if (this.toolName.equalsIgnoreCase("OnConnector")){
			if (connectorsMatch()){
				this.selectOperations.addNewModuleOnConnector(this);
			}else{
				JOptionPane.showMessageDialog(null, "Method called pickTarget failed to extract the connector number","Error", JOptionPane.ERROR_MESSAGE);
			}			
		}else if (this.toolName.equalsIgnoreCase("chosenConnector")||this.toolName.equalsIgnoreCase("Loop")){
			this.selectOperations.addNewModuleOnConnector(this);
		}else if (this.toolName.equalsIgnoreCase("AllConnectors")){
			this.selectOperations.addModulesOnAllConnectors(this);		
		}else if(this.toolName.equalsIgnoreCase("StandardRotation")){
			this.selectOperations.rotateModuleStandardRotation(this, this.standardRotationName);
		}else if (this.toolName.equalsIgnoreCase("OppositeRotation")){			
			this.selectOperations.rotateModuleWithOppositeRotation(this);
		}else if (this.toolName.equalsIgnoreCase("Variation")){
			this.selectOperations.variateModule(this);
		}		
	}
	
	/**
	 * Checks for match between the number of connector extracted from TriMesh(method pickTarget()) string and existing connectors of the module 
	 * @return true, if pickTarget() method resulted in success with extraction of connector number from trimesh string and there is such number
	 * of connector on selected module. 
	 */
	private boolean connectorsMatch(){		
		int amountConnectors = this.selectedModule.getConnectors().size();
		for (int connector=0;connector<amountConnectors;connector++){
			String connectorNr = this.selectedModule.getConnectors().get(connector).getProperty(BuilderUtilities.getModuleConnectorNrKey());		
			if (connectorNr== null){
				JOptionPane.showMessageDialog(null, "Something is wrong with property called: ussr.connector_number, implemented by Ulrik Pagh Schultz. Or property is not set at all.","Error", JOptionPane.ERROR_MESSAGE);				
			}else if(Integer.parseInt(connectorNr)==this.selectedConnectorNr){			
				return true;				
			}
		}		
		return false;		
	} 
	
//	TODO SHOULD BE IN SOME WAY MOVED TO CommonOperations class How should I do that?
	public void moveToNextConnector(int connectorNr){
		int amountModules = this.simulation.getModules().size();
		Module lastAddedModule = this.simulation.getModules().get(amountModules-1);//Last module
		Module selectedModule = this.selectedModule;//Last module
		if (this.modularRobotName.equalsIgnoreCase(atron)){			
			ConstructionStrategy con =  new ATRONConstructionStrategy();
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule);
		}else if (this.modularRobotName.equalsIgnoreCase(mtran)){			
			ConstructionStrategy con =  new MTRANConstructionStrategy();
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule);
		}else if (this.modularRobotName.equalsIgnoreCase(odin)){
			ConstructionStrategy con =  new OdinConstructionStrategy();
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule);
		}
	}

	/**
	 * Returns the name of modular robot specified in selection tool
	 * @return modularRobotName, the name of modular robot
	 */
	public String getModularRobotName() {
		return modularRobotName;
	}

	/**
	 * Returns the object of assigned construction strategy.  
	 * @return construction, the object of assigned construction strategy. For example for ATRON this will be an instance of ATRONConstructionStrategy.java.
	 */
	public ConstructionStrategy getConstruction() {
		return construction;
	}

	/**
	 * Returns the connector number selected in GUI or on the module in simulation environment
	 * @return selectedConnectorNr,the connector number selected in GUI or on the module in simulation environment.
	 */
	public int getSelectedConnectorNr() {
		return selectedConnectorNr;
	}

	/**
	 * Returns the module selected in simulation environment with the left side of the mouse
	 * @return, the module selected in simulation environment with the left side of the mouse
	 */
	public Module getSelectedModule() {
		return selectedModule;
	}

	/** 
	 * Returns the connector number selected on module in simulation environment or the one chosen in GUI
	 * @param selectedConnectorNr, the connector number selected on module in simulation environment or the one chosen in GUI
	 */
	public void setSelectedConnectorNr(int selectedConnectorNr) {
		this.selectedConnectorNr = selectedConnectorNr;
	}
}
