package ussr.builder.constructionTools;

import ussr.builder.construction.CommonOperationsStrategy;
import ussr.builder.construction.SelectOperationsStrategy;
import ussr.physics.jme.JMESimulation;

public class ATRONConstructionTool extends ModularRobotConstructionTool{
	
	/** 
	 * The physical simulation
	 */
	private JMESimulation simulation;
	
	/** 
	 * The name of the tool from GUI, for example A1,A2,A3 or A4.
	 */
	private String toolName;
	
	/**
	 * Constructor
	 * @param simulation,the physical simulation 
	 * @param toolName, the name of the tool in GUI,for example A1,A2,A3 or A4.
	 */
	public  ATRONConstructionTool(JMESimulation simulation,String toolName){
		this.simulation = simulation;
		this.toolName= toolName;
	}
	
	public void setTool(int connectorNr, int selectedModuleID){
		if(toolName.equalsIgnoreCase("OnConnector")||toolName.equalsIgnoreCase("chosenConnector")||toolName.equalsIgnoreCase("Loop")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.addNewModuleOnConnector("ATRON",connectorNr, selectedModuleID);
		}
	}
	
	public void setTool(int selectedModuleID){
		 if(toolName.equalsIgnoreCase("AllConnectors")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.addModulesOnAllConnectors("ATRON", selectedModuleID);
		}else if(toolName.equalsIgnoreCase("OppositeRotation")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.rotateModuleWithOppositeRotation("ATRON", selectedModuleID);
		}else if(toolName.equalsIgnoreCase("Variation")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.variateModule("ATRON", selectedModuleID);
		}
	}
	
	public void setTool(int selectedModuleID, String rotationName){
		 if(toolName.equalsIgnoreCase("StandardRotation")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.rotateModuleStandardRotation("ATRON", selectedModuleID, rotationName);
		 }
	}	
}
