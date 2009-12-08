package ussr.aGui.enumerations;

/**
 * Contains build in hints for tab called "Construct Robot". Hints are displayed in Display for hints(panel).
 * @author Konstantinas
 * NOTE: In oder to change the hint text just modify it here.
 */
public enum HintsConstructRobotTab {

	
	DEFAULT("This tab is dedicated to interactive construction of modular robot morphology (shape). Follow hints displayed here to explore functionality in more detail. When done constructing robot go to the next tab."),
	
	START_NEW_ROBOT("Choose modular robot type from four available (ATRON, Odin  and so on)."),
	
	MODULAR_ROBOT_CHOSEN("Locate newly added module in the simulation environment. For navigation hold right side of the mouse to look around and W,A,S,D to move. Next there are two options: 1)" +
			             "Apply rotation to newly added module or 2) Zoom in closer to module and select connectors (black-white geometric shapes)"),
	
	OPPOSITE_ROTATION("Zoom in closer and select module in order to rotate it with opposite rotatioin. If the first selection was unsuccessful, try to select module several times."),
	
	AVAILABLE_ROTATIONS("Select module several times to rotate it with available rotations. Each selection will rotate module with new rotation."),
	
	STANDARD_ROTATIONS("Select module to rotate it with chosen rotation named as: "),
	
	// ADD ME ENTITY_CHOSEN(),
	
	VARIATE_MODULE_PROPERTIES("Select module to change its properties, like rotation or module type. In case of Odin will change OdinMuscle module with other types of modules.In case of M-Tran and CKbot will " +
			"rotate the module with additional rotations."),	
	
	MOVE("Select module/robot and move it with movement of the mouse. Hold left side of the mouse pressed during this process."),
	
	DELETE("Select module/robot to delete (remove) from simulation environment. Not recommended to use, if only one module exists in simulation environment."),
	
	COLOR_CONNECTORS("Select module to color its connectors with color coding (color - connector number ). Here: Black - 0, Red - 1,"+
			" Cyan - 2, Grey - 3, Green - 4, Magenta - 5, Orange - 6, Pink - 7, Blue - 8, White - 9, Yellow - 10, Light Grey - 11."),
			
	ON_SELECTED_CONNECTOR ("Zoom in closer to module and select connectors (black-white geometric shapes). On each selected connector will connected new module."),
	
	ON_CHOSEN_CONNECTOR_NR("It is recommended to use this functionality in combination with button Color connectors, because then it is easier to match the color(number) of connector" +
			              "to color chosen here. Select module to add new module on chosen number of connector, which is number: "),
	
	ON_ALL_CONNECTORS("Select module to add new modules on all connectors. Next use button Delete to remove excessive modules."+
			           "It is not recommended to have modules overlaping with the plane."),
			           
    JUMP_FROM_CON_TO_CON("Select module to move newly added module from initial connector to desired with each selection. Newly added module can be moved on any module in the morphology of modular robot. "),
    
    TAB_NOT_AVAILABLE("This tab is only available before simulation is started!");
	;
	
	/**
	 * The text of hint.
	 */
	private String hintText;
	
	/**
	 * Contains build in hints for tab called "Construct Robot". Hints are displayed in Display for hints(panel).
	 * @param hintText, the hint text.
	 */
	HintsConstructRobotTab(String hintText){
		this.hintText=hintText;
	}
	
	
	/**
	 * Returns the text of hint.
	 * @return the text of hint.
	 */
	public String getHintText() {
		return hintText;
	}
	
	
}
