package ussr.builder.constructionTools;

import javax.swing.JOptionPane;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import ussr.builder.construction.ATRONConstructionStrategy;
import ussr.builder.construction.ConstructionStrategy;
import ussr.builder.construction.MTRANConstructionStrategy;
import ussr.builder.construction.OdinConstructionStrategy;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

public class ConstructionTools extends CustomizedPicker{

	/** 
	 * The physical simulation
	 */
	public JMESimulation simulation;

	/**
	 * The connector number on module, selected with the mouse in simulation environment
	 */	
	private int selectedConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 

	/**
	 * The connector number on the module, chosen in GUI comboBox.
	 */
	private int chosenConnectorNr = 1000;//just to avoid having default 0 value, which is also number of connector. 

	/**
	 * The global ID of the module selected in simulation environment
	 */
	private int selectedModuleID;

	/**
	 * The name of the modular robot. For example: ATRON, M-Tran,Odin and so on
	 */
	private String modularRobotName;

	/**
	 * The name of the tool from GUI. For example for ATRON, these can be A1,A2,A3,A4 and so on.
	 */
	private String toolName;

	/**
	 * The name of rotations, which are standard to particular modular robot. For example for ATRON this can be EW, meaning east-west.
	 */
	private String standardRotationName;

	/**
	 * Constructor for calling tools handling construction of morphology of modular robot
	 * @param simulation, the physical simulation. 
	 * @param modularRobotName,the name of the modular robot. For example: ATRON, M-Tran,Odin and so on.
	 * @param toolName,the name of the tool from GUI. For example for ATRON, these can be A1,A3,A4.
	 */
	public  ConstructionTools(JMESimulation simulation, String modularRobotName, String toolName){
		this.simulation = simulation;
		this.modularRobotName = modularRobotName;
		this.toolName = toolName;
	}

	/**
	 * Constructor for calling tools handling construction of morphology of modular robot.
	 * @param simulation, the physical simulation.
	 * @param modularRobotName,the name of the modular robot. For example: ATRON, M-Tran,Odin and so on.
	 * @param toolName,the name of the tool from GUI. For example for ATRON, this is A2.
	 * @param chosenConnectorNr,the connector number on module, chosen in GUI comboBox.
	 */
	public  ConstructionTools(JMESimulation simulation, String modularRobotName, String toolName,int chosenConnectorNr){
		this.simulation = simulation;
		this.modularRobotName = modularRobotName;
		this.toolName = toolName;
		this.chosenConnectorNr = chosenConnectorNr;
	}

	/**
	 * Constructor for calling tools handling construction of morphology of modular robot
	 * @param simulation, the physical simulation.
	 * @param modularRobotName, the name of the modular robot. For example: ATRON, M-Tran,Odin and so on.
	 * @param toolName, the name of the tool from GUI. For example for ATRON, this is "StandardRotation".
	 * @param standardRotation,the name of rotation, which is standard to particular modular robot. For example for ATRON this can be EW, meaning east-west.
	 */
	public  ConstructionTools(JMESimulation simulation, String modularRobotName, String toolName, String standardRotation){
		this.simulation = simulation;
		this.modularRobotName = modularRobotName;
		this.toolName = toolName;
		this.standardRotationName = standardRotation;
	}

	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {		
		this.selectedModuleID = component.getModel().getID();		
		callAppropriateTools(this.selectedModuleID);		
	}

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
	 * Calls the tool for construction of specific modular robot
	 * @param selectedModuleID, the global ID of the module selected in simulation environment
	 */
	private void callAppropriateTools(int selectedModuleID){
		if (modularRobotName.equalsIgnoreCase("ATRON")){
			if (isAtron(selectedModuleID)){			 
				callATRONTools();			
			}else{
				JOptionPane.showMessageDialog(null, "This module is not an ATRON module. The chosen tool is for ATRON modules!","Error", JOptionPane.ERROR_MESSAGE);// Handle wrong user input
			}
		}else if (modularRobotName.equalsIgnoreCase("MTRAN")){
			if( isMtran(selectedModuleID)){
				callM_TranTools();
			}else{
				JOptionPane.showMessageDialog(null, "This module is not an M-Tran module. The chosen tool is for M-Tran modules!","Error", JOptionPane.ERROR_MESSAGE);// Handle wrong user input
			}
		}else if (modularRobotName.equalsIgnoreCase("Odin")){
			if( isOdin(selectedModuleID)){
				callOdinTools();
			}else{
				JOptionPane.showMessageDialog(null, "This module is not an Odin module. The chosen tool is for Odin modules!","Error", JOptionPane.ERROR_MESSAGE);// Handle wrong user input
			}
		}		
	}

	/**
	 * Checks if selected module is ATRON module 
	 * @param selectedModuleID, the ID of the module selected in simulation environment
	 * @return true if selected module is an ATRON module
	 */
	private boolean isAtron(int selectedModuleID){
		String typeofModule = simulation.getModules().get(selectedModuleID).getProperty("ussr.module.type");// The property called "ussr.module.type" was implemented by Ulrik.
		//System.out.println("Type of module selected in simulation environment:"+ typeofModule);//For debugging		
		if (typeofModule.contains("ATRON")){
			return true;
		}
		return false;
	}

	/**
	 * Checks if selected module is MTRAN module 
	 * @param selectedModuleID, the ID of the module selected in simulation environment
	 * @return true if selected module is an MTRAN module
	 */
	public boolean isMtran(int selectedModuleID){		
		String typeofModule = simulation.getModules().get(selectedModuleID).getProperty("ussr.module.type");// The property called "ussr.module.type" was implemented by Ulrik.
		//System.out.println("Type of module selected in simulation environment:"+ typeofModule);//For debugging		
		if (typeofModule.contains("MTRAN")){
			return true;
		}
		return false;
	}

	/**
	 * Checks if selected module is Odin module 
	 * @param selectedModuleID, the ID of the module picked in simulation environment
	 * @return true if selected module is an Odin module
	 */
	public boolean isOdin(int selectedModuleID){
		String typeofModule = simulation.getModules().get(selectedModuleID).getProperty("ussr.module.type");// The property called "ussr.module.type" was implemented by Ulrik.
		System.out.println("Type of module selected in simulation environment:"+ typeofModule);//For debugging		
		if (typeofModule.contains("Odin")){			
			return true;
			
		}
		return false;
	}

	/**
	 * Calls the tool for construction of ATRON morphology, depending on which tool name is passed in constructor.
	 */
	private void callATRONTools(){
		if (toolName.equalsIgnoreCase("OnConnector")){
			if (connectorsMatch(selectedModuleID,this.selectedConnectorNr)==true){
				ConstructionTool constructTool =  new ATRONConstructionTool(simulation, this.toolName);
				constructTool.setTool(this.selectedConnectorNr,this.selectedModuleID);
			}else System.out.println("Something is wrong with connector number extracted from TriMesh, method pickTarget()");
		}else if (toolName.equalsIgnoreCase("chosenConnector")||toolName.equalsIgnoreCase("Loop")){
			ConstructionTool constructTool =  new ATRONConstructionTool(simulation, this.toolName);
			constructTool.setTool(this.chosenConnectorNr,this.selectedModuleID);
		}else if (toolName.equalsIgnoreCase("AllConnectors")||toolName.equalsIgnoreCase("OppositeRotation")||toolName.equalsIgnoreCase("Variation")){
			ConstructionTool constructTool =  new ATRONConstructionTool(simulation, this.toolName);
			constructTool.setTool(this.selectedModuleID);
		}else if(toolName.equalsIgnoreCase("StandardRotation")){
			ConstructionTool constructTool =  new ATRONConstructionTool(simulation, this.toolName);
			constructTool.setTool(selectedModuleID, standardRotationName);
		}		
	}

	/**
	 * Calls the tool for construction of M-Tran morphology, depending on which tool name is passed in constructor.
	 */
	private void callM_TranTools(){
		if (toolName.equalsIgnoreCase("OnConnector")){
			if (connectorsMatch(selectedModuleID,this.selectedConnectorNr)==true){
				ConstructionTool constructTool =  new MTRANConstructionTool(simulation, this.toolName);
				constructTool.setTool(this.selectedConnectorNr,this.selectedModuleID);
			}else System.out.println("Something is wrong with connector number extracted from TriMesh, method pickTarget()");
		}else if (toolName.equalsIgnoreCase("chosenConnector")||toolName.equalsIgnoreCase("Loop")){
			ConstructionTool constructTool =  new MTRANConstructionTool(simulation, this.toolName);
			constructTool.setTool(this.chosenConnectorNr,this.selectedModuleID);
		}else if (toolName.equalsIgnoreCase("AllConnectors")||toolName.equalsIgnoreCase("OppositeRotation")||toolName.equalsIgnoreCase("Variation")){			
			ConstructionTool constructTool =  new MTRANConstructionTool(simulation, this.toolName);
			constructTool.setTool(this.selectedModuleID);
		}else if(toolName.equalsIgnoreCase("StandardRotation")){
			ConstructionTool constructTool =  new MTRANConstructionTool(simulation, this.toolName);
			constructTool.setTool(selectedModuleID, standardRotationName);				
		}
	}

	/**
	 * Calls the tool for construction of Odin morphology, depending on which tool name is passed in constructor.
	 */
	private void callOdinTools(){
		if (toolName.equalsIgnoreCase("chosenConnector")||toolName.equalsIgnoreCase("Loop")){			
			ConstructionTool constructTool =  new OdinConstructionTool(simulation, this.toolName);
			constructTool.setTool(this.chosenConnectorNr,this.selectedModuleID);
		}else if (toolName.equalsIgnoreCase("AllConnectors")||toolName.equalsIgnoreCase("OppositeRotation")||toolName.equalsIgnoreCase("Variation")){			
			ConstructionTool constructTool =  new OdinConstructionTool(simulation, this.toolName);
			constructTool.setTool(this.selectedModuleID);			
		}

	}

	/**
	 * Checks for match between the number of connector extracted from TriMesh(method pickTarget()) string and existing connectors of the module 
	 * @param selectedModuleID, the ID of the module selected in simulation environment
	 * @param connectorNumber, the picked connector number on ATRON module
	 * @return true if pickTarget() method resulted in success with extraction of connector number from trimesh string  
	 */
	private boolean connectorsMatch(int selectedModuleID, int connectorNumber){
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		int amountConnectors = selectedModule.getConnectors().size();
		for (int connector=0;connector<amountConnectors;connector++){
			String connectorNr = selectedModule.getConnectors().get(connector).getProperty("ussr.connector_number");// The property called "ussr.connector_number" was implemented by Ulrik.
			//System.out.println("connectorNr:"+connectorNr); //For debugging
			if (Integer.parseInt(connectorNr)==connectorNumber ){
				//System.out.println("connectorNr:"+connectorNr); //For debugging
				return true;				
			}
		}		
		return false;		
	} 

	public void moveToNextConnector(int connectorNr){
//		TODO SHOULD BE IN SOME WAY MOVED TO CommonOperations class How should I do that?
		//Some problem exists with mtran, Null pointer to simulation.
		int amountModules = simulation.getModules().size();
		Module lastAddedModule = simulation.getModules().get(amountModules-1);//Last module
		Module selectedModule = simulation.getModules().get(this.selectedModuleID);//Last module
		if (modularRobotName.equalsIgnoreCase("ATRON")){			
			ConstructionStrategy con =  new ATRONConstructionStrategy();
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule);
		}else if (modularRobotName.equalsIgnoreCase("MTRAN")){			
			ConstructionStrategy con =  new MTRANConstructionStrategy();
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule);
		}else if (modularRobotName.equalsIgnoreCase("Odin")){
			ConstructionStrategy con =  new OdinConstructionStrategy();
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule);
		}

	}


}
