package ussr.builder.constructionTools;

import ussr.builder.construction.CommonOperationsStrategy;
import ussr.builder.construction.SelectOperationsStrategy;
import ussr.physics.jme.JMESimulation;

public class OdinConstructionTool extends ModularRobotConstructionTool {
   
	/** 
	 * The physical simulation
	 */
	private JMESimulation simulation;
	
	/** 
	 * The name of the tool from GUI, for example O1,O2,O3 or O4.
	 */
	private String toolName;
	
	/**
	 * Constructor
	 * @param simulation,the physical simulation 
	 * @param toolName, the name of the tool in GUI,for example O1,O2,O3 or O4.
	 */
	public  OdinConstructionTool(JMESimulation simulation,String toolName){
		this.simulation = simulation;
		this.toolName= toolName;
	}
	
	public void setTool(int connectorNr, int selectedModuleID) {
		if(toolName.equalsIgnoreCase("O1")||toolName.equalsIgnoreCase("chosenConnector")||toolName.equalsIgnoreCase("Loop")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.addNewModuleOnConnector("Odin",connectorNr, selectedModuleID);
		}
		
	}

	@Override
	public void setTool(int selectedModuleID) {
		if(toolName.equalsIgnoreCase("AllConnectors")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			//CONNECTOR NUMBER IS NOT USED		
			selectOperations.addModulesOnAllConnectors("Odin", selectedModuleID);			
		}else if(toolName.equalsIgnoreCase("Variation")){
			//NOT PROGRAMMING INTO INTERFACE
			CommonOperationsStrategy commonOperations = new CommonOperationsStrategy(simulation);		
			commonOperations.variateModule("Odin", selectedModuleID);	
		}else if(toolName.equalsIgnoreCase("OppositeRotation")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.rotateModuleWithOppositeRotation("Odin", selectedModuleID);
		}
		
	}

	@Override
	public void setTool(int selectedModuleID, String standardRotationName) {
		// TODO Auto-generated method stub
		
	}

}
