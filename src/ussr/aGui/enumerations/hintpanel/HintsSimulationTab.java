package ussr.aGui.enumerations.hintpanel;

/**
 * Contains hints displayed in Display for hints for Tab called Simulation.
 * NOTE Nr.1: In oder to change the hint text just modify it here.
 * @author Konstantinas
 *
 */
public enum HintsSimulationTab implements HintsTabsInter {

	DEFAULT("This tab allows to manipulate simulation values and entities in it. Follow hints displayed here" +
			"to get familliar with the purpose of each element."),
	SIMULATION("TODO "),
	
	PHYSICS_SIMULATION_STEP_SIZE("The interval at which simulation time is discretized.  A higher value makes the simulation run faster, at the cost of precision.  " +
			"Depending on the specific simulation, higher values can cause spurious effects (e.g., collision not detected for object with high velocity) or failures in the " +
			"underlying physics system."),
	
	RESOLUTION_FACTOR("Factor that determines the number of polygons used to create geometrical primitives (sphere, cones, etc).  Decrease if the simulator uses all the available " +
			"graphics memory (typically a simulation with many modules, see user guide).  Higher values gives less ugly graphics but has a performance overhead."),
	
	ROBOTS("TODO"),
	
	WORLD_DESCRIPTION("TODO"),
	
	PLANE_SIZE("The size of one edge of the underlying plane."),
	
	PLANE_TEXTURE("The texture used to decorate the underlying plane."),
	
	CAMERA_POSITION("Overall preference for initial placement of the viewpoint on the simulation."),
	
	THE_WORLD_IS_FLAT("Whether the underlying plane is flat or has a randomized terrain."),
	
	HAS_BACKGROUND_SCENERY("Whether the background/overhead should have a landscape, clouds etc"),
	
	HAS_HEAVY_OBSTACLES("Cause default obstacles to be very heavy related to a normal module."),
	
	IS_FRAME_GRABBING_ACTIVE("Grab frames from when the simulation starts."),
	
	PHYSICS_PARAMETERS("TODO"),
	
	DAMPING("Reduce all forces by a percentage at every physics step, causes less jumpy movements and gives less sensitivty to inertia."),
	
	REALISTIC_COLLISION("When true, use the visible geometry to detect collision between bodies, otherwise use a more efficient approximation.  Realistic collision are normally " +
			"required for self-reconfiguration and for certain locomotion experiments, but turn them off speeds up the simulation."),
	
	GRAVITY("The gravity."),
	
	PLANE_MATERIAL("The material property of the surface of the underlying plane."),
	
	MAINTAIN_ROTATIONAL_JOINT_POSITIONS("For robots that support this feature, cause actuators to actively maintain a position after they completed any movement."),
	
	ERROR_REDUCTION_PARAMETER("Error reduction parameter(ERP) controls the menchanism for aligning connected joints together. Joints misalignments can appear due to user specifying imprecise positions/rotations and during simulation, errors can creep so that, joints will drift away from each other." +
			                  "ERP specifies what proportion of joint error will be fixed during next simulation step. Recommended values are:0.1-0.8. Default is 0.8."),
			                  
    CONSTRAINT_FORCE_MIXING("Constraint force mixing (CFM) is used to define constrains in mechanical interaction of objects. If it is set to zero, then objects will be as made of steel and will not penetrate each other in case of collision (connection)." +
    		                 "If CFM is set to positive value, then constraint force will be soft and object will exhibit softness in connectivity. Here typical use is simulation of soft materials. Setting CFM to negative value is not recommended, because this can lead " +
    		                 "to instability."),	
    		                 
    USE_MODULE_EVENT_QUEUE ("Controls the concurrency model of the module.  When true, all events are stored in a queue that can be read by the module thread.  When false, " +
    		"an event thread delivers all events (pausing delivery of other events while this event is being processed)."),
    
    SYNCHRONIZE_WITH_CONTROLLERS("Enable strict time synchronization between the simulator and controllers running in native environments supporting this feature.  " +
    		"Currently, TinyOS controllers support this feature."),
    
    PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR("When using strict time synchronization, this is the time step to use in the native controller."),
			     
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
