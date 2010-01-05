package ussr.aGui.enumerations.hintpanel;

/**
 * Contains hints displayed in Display for hints for Tab called Simulation.
 * NOTE Nr.1: In oder to change the hint text just modify it here.
 * @author Konstantinas
 *
 */
public enum HintsSimulationTab {

	DEFAULT("This tab allows to manipulate simulation values and entities in it. Follow hints displayed here" +
			"to get familliar with the purpose of each element."),
	SIMULATION("TODO "),
	
	PHYSICS_SIMULATION_STEP_SIZE("TODO"),
	
	RESOLUTION_FACTOR("TODO"),
	
	ROBOTS("TODO"),
	
	WORLD_DESCRIPTION("TODO"),
	
	PLANE_SIZE("The size of one edge of the underlying plane."),
	
	PLANE_TEXTURE("TODO"),
	
	CAMERA_POSITION("TODO"),
	
	THE_WORLD_IS_FLAT("Whether is used a plane or a generated texture."),
	
	HAS_BACKGROUND_SCENERY("Whenever the background/overhead have clouds etc"),
	
	HAS_HEAVY_OBSTACLES("Whenever light or heavy obstacles are set in simulation environment."),
	
	IS_FRAME_GRABBING_ACTIVE("TODO"),
	
	PHYSICS_PARAMETERS("TODO"),
	
	DAMPING("TODO"),
	
	REALISTIC_COLLISION("TODO"),
	
	GRAVITY("TODO"),
	
	PLANE_MATERIAL("TODO"),
	
	MAINTAIN_ROTATIONAL_JOINT_POSITIONS("TODO"),
	
	ERROR_REDUCTION_PARAMETER("Error reduction parameter(ERP) controls the menchanism for alligning connected joints together. Joints misalignments can appear due to user specifying imprecise positions/rotations and during simulation, errors can creep so that, joints will drift away from each other." +
			                  "ERP specifies what proportion of joint error will be fixed during next simulation step. Recommended values are:0.1-0.8. Default is 0.8."),
			                  
    CONSTRAINT_FORCE_MIXING("Constraint force mixing (CFM) is used to define constrains in mechanical interaction of objects. If it is set to zero, then objects will be as made of steel and will not penetrate each other in case of collision (connection)." +
    		                 "If CFM is set to positive value, then constraint force will be soft and object will exhibit softness in connectivity. Here typical use is simulation of soft materials. Setting CFM to negative value is not recommended, because this can lead " +
    		                 "to instability."),	
    		                 
    USE_MODULE_EVENT_QUEUE ("TODO"),
    
    SYNCHRONIZE_WITH_CONTROLLERS("TODO"),
    
    PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR("TODO"),
			     
			                ;
	//READ MORE HERE:http://opende.sourceforge.net/wiki/index.php/Manual_%28Concepts%29#Joint_error_and_the_error_reduction_parameter_.28ERP.29
	
	/**
	 * The text of hint.
	 */
	private String hintText;
	
	/**
	 * Contains build in hints for tab called "Simulation". Hints are displayed in Display for hints(panel).
	 * @param hintText, the hint text.
	 */
	HintsSimulationTab(String hintText){
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
