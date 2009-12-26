package ussr.aGui.tabs.constructionTabs;

import javax.swing.JPanel;


import ussr.builder.controllerAdjustmentTool.ATRONRotateContinuousNegative;
import ussr.builder.enumerations.SupportedModularRobots;

public enum AssignableControllers {

	ROTATE_CONTINUOUS("Rotate continuously",SupportedModularRobots.ATRON, ATRONRotateContinuousNegative.class,AssignableControllersEditors.addRotateContinuousEditor());
	
	
	private SupportedModularRobots supportedModularRobot;
	
	public SupportedModularRobots getSupportedModularRobot() {
		return supportedModularRobot;
	}

	private Class clas;
	
	private String userFriendlyName;
	
	private JPanel valueEditor;
	
	AssignableControllers(String userFriendlyName, SupportedModularRobots supportedModularRobot,Class clas,JPanel valueEditor){
		this.userFriendlyName = userFriendlyName;
		this.supportedModularRobot = supportedModularRobot;
		this.clas = clas;
		this.valueEditor = valueEditor;
	}
}
