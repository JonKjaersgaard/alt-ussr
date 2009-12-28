package ussr.aGui.tabs.constructionTabs;

import java.util.Vector;

import javax.swing.JPanel;

import ussr.builder.controllerAdjustmentTool.ATRONDisconnectAllConnectors;
import ussr.builder.controllerAdjustmentTool.ATRONStopRotating;
import ussr.builder.controllerAdjustmentTool.CKBOT_STANDARDControllerNOT_SUPPORTED_YET;
import ussr.builder.controllerAdjustmentTool.MTRANControllerNOT_SUPPORTED_YET;
import ussr.builder.controllerAdjustmentTool.withEditors.ATRONDisconnectSpecificConnector;
import ussr.builder.controllerAdjustmentTool.withEditors.ATRONRotateContinuous;
import ussr.builder.controllerAdjustmentTool.withEditors.ATRONRotateDegrees;
import ussr.builder.controllerAdjustmentTool.withEditors.ODINTubesExpandContractContinuously;
import ussr.builder.enumerations.SupportedModularRobots;

public enum AssignableControllers {
    /*ATRON*/
	ROTATE_CONTINUOUS("Rotate continuously",SupportedModularRobots.ATRON, ATRONRotateContinuous.class,AssignableControllersEditors.addRotateContinuousEditor()),
	ROTATE_DEGREES("Rotate amount of degrees",SupportedModularRobots.ATRON,ATRONRotateDegrees.class,AssignableControllersEditors.addRotateDegreesEditor()),
	STOP_ROTATING ("Stop rotating",SupportedModularRobots.ATRON,ATRONStopRotating.class, new JPanel()),//new jPanel() is just dummy (empty/ not used)
	DISCONNECT_ALL_CONNECTORS("Disconnect all connectors", SupportedModularRobots.ATRON,ATRONDisconnectAllConnectors.class,new JPanel()),
	DISCONNECT_SPECIFIC_CONNECTOR("Disconnect spec. connector", SupportedModularRobots.ATRON,ATRONDisconnectSpecificConnector.class,AssignableControllersEditors.addDisconnectSpecificConnectorEditor()),
	
	/*Odin*/
    EXPAND_CONTRACT_CONSTINUOUSLY("Expand or contract continuously",SupportedModularRobots.ODIN, ODINTubesExpandContractContinuously.class, AssignableControllersEditors.addActuateContinuouslyEditor()),
	
	/*MTRAN*/	
	MTRAN_NOT_SUPPORTED("Mtran is not supported yet",SupportedModularRobots.MTRAN,MTRANControllerNOT_SUPPORTED_YET.class,new JPanel()),
	/*CKbot*/
	CKBOT_STANDARD("CKbot is not supported yet",SupportedModularRobots.CKBOT_STANDARD,  CKBOT_STANDARDControllerNOT_SUPPORTED_YET.class, new JPanel());

	private SupportedModularRobots forSupportedModularRobot;

	public SupportedModularRobots getForSupportedModularRobot() {
		return forSupportedModularRobot;
	}

	private Class clas;



	private String userFriendlyName;



	private JPanel valueEditor;

	public JPanel getValueEditor() {
		return valueEditor;
	}

	AssignableControllers(String userFriendlyName, SupportedModularRobots forSupportedModularRobot,Class clas,JPanel valueEditor){
		this.userFriendlyName = userFriendlyName;
		this.forSupportedModularRobot = forSupportedModularRobot;
		this.clas = clas;
		this.valueEditor = valueEditor;
	}

	public Class getClas() {
		return clas;
	}


	public String getUserFriendlyName() {
		return userFriendlyName;
	}

	public static Vector<String> getAllUserFrienlyNamesForRobot(SupportedModularRobots forSupportedModularRobot){
		Vector<String> userFriendlyNames = new Vector<String>();
		for (int entry=0; entry<values().length;entry++){
			if (values()[entry].getForSupportedModularRobot().equals(forSupportedModularRobot)){
				userFriendlyNames.add(values()[entry].getUserFriendlyName());
			}
		}
		
		if (userFriendlyNames.isEmpty()){
			throw new Error("There are no controllers available for  modular robot called :" + forSupportedModularRobot.toString());
		}
		return userFriendlyNames;
	}
	
	
	
	public static AssignableControllers getControllerSystemName(String userFriendlyName){
		String controllerSystemName =" ";
		for (int controllerNr=0;controllerNr<values().length;controllerNr++ ){
			String currentUserFriendlyName = values()[controllerNr].getUserFriendlyName();
			if (currentUserFriendlyName.equalsIgnoreCase(userFriendlyName)||userFriendlyName.equals(values()[controllerNr].toString())){
				controllerSystemName = values()[controllerNr].toString(); 
			};
		}
		
		if (controllerSystemName.equals(" ")){
			throw new Error("There is no such system name matching the following user frienly name: " + userFriendlyName );
		}
		return AssignableControllers.valueOf(controllerSystemName);
	}


}
