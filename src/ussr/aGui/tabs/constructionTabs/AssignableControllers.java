package ussr.aGui.tabs.constructionTabs;

import java.util.Vector;

import javax.swing.JPanel;

import ussr.aGui.GuiFrames;
import ussr.builder.controllerAdjustmentTool.ATRONDisconnectAllConnectors;
import ussr.builder.controllerAdjustmentTool.ATRONStopRotating;
import ussr.builder.controllerAdjustmentTool.CKBOT_STANDARDControllerNOT_SUPPORTED_YET;
import ussr.builder.controllerAdjustmentTool.withEditors.ATRONDisconnectSpecificConnector;
import ussr.builder.controllerAdjustmentTool.withEditors.ATRONRotateContinuous;
import ussr.builder.controllerAdjustmentTool.withEditors.ATRONRotateDegrees;
import ussr.builder.controllerAdjustmentTool.withEditors.MTRANRotateContinuously;
import ussr.builder.controllerAdjustmentTool.withEditors.ODINTubesExpandContractContinuously;
import ussr.builder.enumerations.SupportedModularRobots;

/**
 * Contains controllers(with editors in GUI) available to assign interactively in the tab called Assign controller.
 * Moreover, associates each controller name with its: 1)user friendly name displayed in the GUI, 2) modular robot type it is
 * implemented for,3) the class controller is implemented in and  3) Call for editor displayed in GUI.
 * 
 * NOTE: If you want to add new controller, just define it here by defining each of above elements.
 * 
 * @author Konstantinas
 *
 */
public enum AssignableControllers {
    /*ATRON*/
	ROTATE_CONTINUOUS("Rotate continuously",SupportedModularRobots.ATRON, ATRONRotateContinuous.class,AssignableControllersEditors.addATRONRotateContinuousEditor()),
	ROTATE_DEGREES("Rotate amount of degrees",SupportedModularRobots.ATRON,ATRONRotateDegrees.class,AssignableControllersEditors.addATRONRotateDegreesEditor()),
	STOP_ROTATING ("Stop rotating",SupportedModularRobots.ATRON,ATRONStopRotating.class, new JPanel()),//new jPanel() is just dummy (empty/ not used)
	DISCONNECT_ALL_CONNECTORS("Disconnect all connectors", SupportedModularRobots.ATRON,ATRONDisconnectAllConnectors.class,new JPanel()),
	DISCONNECT_SPECIFIC_CONNECTOR("Disconnect spec. connector", SupportedModularRobots.ATRON,ATRONDisconnectSpecificConnector.class,AssignableControllersEditors.addATRONDisconnectSpecificConnectorEditor()),
	
	/*Odin*/
    EXPAND_CONTRACT_CONSTINUOUSLY("Expand/contract continuously",SupportedModularRobots.ODIN, ODINTubesExpandContractContinuously.class, AssignableControllersEditors.addODINActuateContinuouslyEditor()),
	
	/*MTRAN*/	
	MTRAN_ROTATE_CONTINUOUS("Rotate continuous",SupportedModularRobots.MTRAN,MTRANRotateContinuously.class,AssignableControllersEditors.addMTRANRotateContinuouslyEditor()),
	/*CKbot*/
	CKBOT_STANDARD("CKbot is not supported yet",SupportedModularRobots.CKBOT_STANDARD,  CKBOT_STANDARDControllerNOT_SUPPORTED_YET.class, new JPanel());


	/**
	 * The name of controller as displayed in GUI.
	 */
	private String userFriendlyName;
	
	/**
	 *  The type of modular robot, the controller is implemented for.
	 */
	private SupportedModularRobots forSupportedModularRobot;
	
	/**
	 * The name of the class defining controller implementation. 
	 */
	private Class className;
	
	/**
	 * Panel defining visual appearance of editor value for controller. 
	 */
	private JPanel valueEditor;
	
	/**
	 * Contains controllers(with editors in GUI) available to assign interactively in the tab called Assign controller.
	 * Moreover, associates each controller name with its: 1)user friendly name displayed in the GUI, 2) modular robot type it is
	 * implemented for,3) the class controller is implemented in and  3) Call for editor displayed in GUI.
	 * 
	 * NOTE: If you want to add new controller, just define it here by defining each of above elements.
	 * @author Konstantinas
	 * 
	 * @param userFriendlyName, the name of controller as displayed in GUI.
	 * @param forSupportedModularRobot,the type of modular robot, the controller is implemented for.
	 * @param className, the name of the class defining controller implementation.
	 * @param valueEditor,panel defining visual appearance of editor value for controller.
	 */
	AssignableControllers(String userFriendlyName, SupportedModularRobots forSupportedModularRobot,Class className,JPanel valueEditor){
		this.userFriendlyName = userFriendlyName;
		this.forSupportedModularRobot = forSupportedModularRobot;
		this.className = className;
		this.valueEditor = valueEditor;
	}

	
	/**
	 * Returns the name of controller as displayed in GUI.
	 * @return the name of controller as displayed in GUI.
	 */
	public String getUserFriendlyName() {
		return userFriendlyName;
	}
	
	/**
	 * Returns the type of modular robot, the controller is implemented for.
	 * @return the type of modular robot, the controller is implemented for.
	 */
	public SupportedModularRobots getForSupportedModularRobot() {
		return forSupportedModularRobot;
	}
	
	
	/**
	 * Returns the name of the class defining controller implementation. 
	 * @return  the name of the class defining controller implementation. 
	 */
	public Class getClassName() {
		return className;
	}
	
	/**
	 * Returns panel defining visual appearance of editor value for controller. 
	 * @return panel defining visual appearance of editor value for controller. 
	 */
	public JPanel getValueEditor() {		
		return valueEditor;
	}
	
	/**
	 * Filters and returns the names of all controllers for particular modular robot type.
	 * @param forSupportedModularRobot, the type of modular robot. 
	 * @return returns the names of all controllers for particular modular robot type.
	 */
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

	/**
	 * Returns controller system name (in Java convention) from given user friendly name.
	 * @param userFriendlyName, the name of controller as displayed in GUI
	 * @return controller system name (in Java convention) from given user friendly name.
	 */
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
