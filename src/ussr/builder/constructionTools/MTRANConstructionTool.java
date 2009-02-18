package ussr.builder.constructionTools;

import ussr.builder.construction.CommonOperationsStrategy;
import ussr.builder.construction.SelectOperationsStrategy;
import ussr.physics.jme.JMESimulation;

public class MTRANConstructionTool extends ModularRobotConstructionTool{
	
	/** 
	 * The physical simulation
	 */
	private JMESimulation simulation;
	
	/** 
	 * The name of the tool from GUI, for example M1,M2,M3 or M4.
	 */
	private String toolName;
	
	/**
	 * Constructor
	 * @param simulation,the physical simulation 
	 * @param toolName, the name of the tool in GUI,for example M1,M2,M3 or M4.
	 */
	public  MTRANConstructionTool(JMESimulation simulation,String toolName){
		this.simulation = simulation;
		this.toolName= toolName;
	}
	
	public void setTool(int connectorNr, int selectedModuleID) {
		if(toolName.equalsIgnoreCase("OnConnector")||toolName.equalsIgnoreCase("chosenConnector")||toolName.equalsIgnoreCase("Loop")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.addNewModuleOnConnector("MTRAN",connectorNr, selectedModuleID);
		}		
	}

	@Override
	public void setTool(int selectedModuleID) {
		 if(toolName.equalsIgnoreCase("AllConnectors")){
				SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
				selectOperations.addModulesOnAllConnectors("MTRAN", selectedModuleID);
			}else if(toolName.equalsIgnoreCase("OppositeRotation")){
				SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
				selectOperations.rotateModuleWithOppositeRotation("MTRAN", selectedModuleID);
			}else if(toolName.equalsIgnoreCase("Variation")){
				System.out.println("IN2");
				SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
				selectOperations.variateModule("MTRAN", selectedModuleID);
			}
	}

	@Override
	public void setTool(int selectedModuleID, String rotationName) {
		if(toolName.equalsIgnoreCase("StandardRotation")){
			SelectOperationsStrategy selectOperations = new CommonOperationsStrategy(simulation);
			selectOperations.rotateModuleStandardRotation("MTRAN", selectedModuleID, rotationName);
		 }
		
	}

}
