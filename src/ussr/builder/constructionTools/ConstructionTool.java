package ussr.builder.constructionTools;

public interface ConstructionTool {
	public void setTool(int connectorNr, int selectedModuleID);
	public void setTool(int selectedModuleID);
	
	public void setTool(int selectedModuleID, String rotationName);
	
}
