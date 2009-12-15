package ussr.aGui.enumerations.tabs;

/**
 * Contains text elements presented to the user in Simulation tab. Such as text on GUI elements and tool tips text.
 * NOTE NR1: underscore is later replaced with space. 
 * NOTE NR2: is not complete, because not all of them are used in the code.
 * NOTE NR3: if you want to change specific text, just refactor it and keep in mind that underscore is used instead of space. 
 * @author Konstantinas
 */
public enum SimulationTabComponentsText {

	/*METHOD: setText()*/
	Edit_value,
	
	
	/*METHOD: setToolTipText()*/
	
	//for moving modular robot
	Move_up, Move_down,
	Move_left, Move_right,
	Move_closer, Move_away,
}
