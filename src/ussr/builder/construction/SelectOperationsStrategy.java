package ussr.builder.construction;

public interface SelectOperationsStrategy {
	public void addNewModuleOnConnector(String modularRobotName, int connectorNr, int selectedModuleID);
	
	public void addModulesOnAllConnectors(String modularRobotName,int selectedModuleID);
	
	public void moveModuleOnNextConnector(String modularRobotName,int connectorNr,int selectedModuleID);
	
	public void rotateModuleWithOppositeRotation(String modularRobotName,int selectedModuleID);
	
	public void rotateModuleStandardRotation(String modularRobotName,int selectedModuleID, String rotationName);
}
