package ussr.aGui.enumerations.tabs;

/**
 * Contains text elements presented to the user in Tabs. Such as text on GUI elements and tool tips text.
 * NOTE NR1: underscore is later replaced with space. 
 * NOTE NR2: if you want to change specific text, just refactor it and keep in mind that underscore is used instead of space. 
 * @author Konstantinas
 */
public enum TabsComponentsText {

	/*CONSTRUCT ROBOT TAB*/
	
	/*METHOD: setText()*/
	START_NEW_ROBOT,
	
	EDIT_VALUE,
	
	ADD_AND_ADJUST_FIRST_MODULE,
	
	OPERATIONS_ON_EXISTING,
	
	ADD_NEW_MODULES,
	CHANGE_MODULE_TYPE,
	
	/*METHOD: setToolTipText()*/
	GENERAL_CONTROL,
	
	CHOOSE_SUPPORTED_MODULAR_ROBOT,
	
	START_CONSTRUCTING_NEW_ROBOT,
	ADJUST_MODULE_PROPERTIES,
	
	ROTATE_OPPOSITE,
	AVAILABLE_ROTATIONS,
	STANDARD_ROTATIONS,
	
	GENERIC_TOOLS,
	OPERATIONS_FOR_CHANGING_MODULE_TYPE, 
	
	MOVE_MODULE,
	DELETE_OR_REMOVE,
	COLOR_MODULE_CONNECTORS,
	VARY_MODULE_TYPE_OR_PROPERTIES,
	
	ADDITION_OF_NEW_MODULES,
	ON_SELECTED_CONNECTOR,
	ON_CHOSEN_CONNECTOR_NUMBER,	
	ON_ALL_CONNECTORS,
	JUMP_FROM_CONNECTOR_TO_CONNECTOR,
	
	DEFAULT_NEW_MODULE_TYPE,

	/*ASSIGN BEHAVIOR(CONTROLLER) TAB*/
	
	/*METHOD: setText()*/
	
	NEW_POSITION,
	LOAD_NEW_ROBOT,
	/*METHOD: setToolTipText()*/
	FILTER_OUT_FOR,
	
	/*Simulation Tab*/
		
	//for moving modular robot
	MOVE_UP, MOVE_DOWN,
	MOVE_LEFT, MOVE_RIGHT,
	MOVE_CLOSER, MOVE_AWAY,
	;
	
	 /**
 	 * Returns the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
 	 * underscore is replaced with space.
 	 * @return the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
 	 * underscore is replaced with space.
 	 */
 	public String getUserFriendlyName(){
 		char[] characters = this.toString().replace("_", " ").toLowerCase().toCharArray();
 		String name = (characters[0]+"").toUpperCase();
         for (int index =1;index<characters.length;index++){
         	name = name+characters[index];
         }		 
 		return name;
 	}
	
	
}
