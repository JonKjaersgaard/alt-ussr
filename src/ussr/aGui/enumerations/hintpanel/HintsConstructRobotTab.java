package ussr.aGui.enumerations.hintpanel;

/**
 * Contains build in hints for tab called "Construct Robot". Hints are displayed in Display for hints(panel).
 * @author Konstantinas
 * NOTE Nr.1: In oder to change the hint text just modify it here.
 */
public enum HintsConstructRobotTab implements HintsTabsInter{

	DEFAULT("This tab is dedicated to interactive construction of modular robot morphology (shape). Follow hints displayed here to explore functionality in more detail. When done constructing robot go to the next tab."),
	
	START_NEW_ROBOT("Choose modular robot type from four available (ATRON, Odin  and so on)."),
	
	MODULAR_ROBOT_CHOSEN("Locate newly added module in the simulation environment. For navigation hold right side of the mouse to look around and W,A,S,D to move. Next there are two options: 1)" +
			             "Apply rotation to newly added module or 2) Zoom in closer to module and select connectors (black-white geometric shapes)"),
	
	OPPOSITE_ROTATION("Zoom in closer and select module in order to rotate it with opposite rotatioin. If the first selection was unsuccessful, try to select module several times."),
	
	AVAILABLE_ROTATIONS("Select module several times to rotate it with available rotations. Each selection will rotate module with new rotation in a loop of available rotations."),
	
	STANDARD_ROTATIONS("Select module to rotate it with chosen rotation named as: "),
	
	VARIATE_MODULE_PROPERTIES("Select module to change its properties, like rotation or module type. In case of ATRON an Odin,with each selection of module will substitude it with new module type .In case of M-Tran and CKbot will " +
			"rotate the module with additional rotations."),	
	
	MOVE("Select module and move it with movement of the mouse in new position. Hold left side of the mouse pressed during this process."),
	
	DELETE("Select module to delete (remove) from simulation environment. Not recommended to use, if only one module exists in simulation environment."),
	
	//COLOR_CONNECTORS("Select module to color its connectors with color coding (color - connector number ). Here: Black - 0, Red - 1,"+
	//		" Cyan - 2, Grey - 3, Green - 4, Magenta - 5, Orange - 6, Pink - 7, Blue - 8, White - 9, Yellow - 10, Light Grey - 11."),
			
	ON_SELECTED_CONNECTOR ("Zoom in closer to module and select connectors (black-white geometric shapes). On each selected connector will be positioned new module. It is possible to change new module type by choosing desired one in the combo box beneath."),
	
	ON_CHOSEN_CONNECTOR_NR("Select module to add new module on connector number: "),
	
	ON_ALL_CONNECTORS("Select module to add new modules on all connectors. Next use button Delete to remove excessive modules. It can be found in the last toolbar."+
			           "It is not recommended to have modules overlaping with the ground plane.It is possible to change new module type by choosing desired one in the combo box beneath."),
			           
    JUMP_FROM_CON_TO_CON("Select module(two times) to move newly added module from initial connector to desired with each selection. When desired position is reached, select grey module to restore original colors and keep it. Newly added module can be moved on any module in the morphology of modular robot.It is possible to change new module type by choosing desired one in the combo box beneath. "),
    
   
    TAB_NOT_AVAILABLE_DUE_TO_RUNNING_SIMULATION("This tab us not available after simulation was started!"),
    
	TAB_NOT_AVAILABLE_DUE_TO_AMOUNT_ROBOTS("This tab is not available, because robot construction is limited to a single robot at a time. The main reason for this is that all modules in simulation enviroment are saved as a single robot xml file."),
	
	NEW_MODULE_TYPE("Currently newly added module type is: ");
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
