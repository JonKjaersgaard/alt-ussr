package ussr.aGui.enumerations;

import ussr.builder.enumerations.XMLTagsUsed;

/**
 * @author Konstantinas
 *
 */
public enum SimulationTabTreeElements {
	//First Level Hierarchy	
	Simulation(XMLTagsUsed.SIMULATION),
	
	  //Second Level Hierarchy
	  //Often used
	  Physics_simulation_step_size(XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE),
	  Resolution_Factor(XMLTagsUsed.RESOLUTION_FACTOR),

	  //Second Level Hierarchy
	  Robots(XMLTagsUsed.ROBOTS),
	    //Third Level Hierarchy
	    Robot(XMLTagsUsed.ROBOT),
	        //Fourth Level Hierarchy
	        Type(XMLTagsUsed.TYPE),
	        Morphology(XMLTagsUsed.MORPHOLOGY_LOCATION),
	        Controller(XMLTagsUsed.CONTROLLER_LOCATION),

	  //Second Level Hierarchy
	  World_description(XMLTagsUsed.WORLD_DESCRIPTION), 
	    //Third Level Hierarchy
	    Plane_size(XMLTagsUsed.PLANE_SIZE),
	    Plane_texture(XMLTagsUsed.PLANE_TEXTURE),
	    Camera_position(XMLTagsUsed.CAMERA_POSITION),
	    The_world_is_flat(XMLTagsUsed.THE_WORLD_IS_FLAT),
	    Has_background_scenery(XMLTagsUsed.HAS_BACKGROUND_SCENERY),
	    Has_heavy_obstacles(XMLTagsUsed.HAS_HEAVY_OBSTACLES),
	    Is_frame_grabbing_active(XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE),

	  //Second Level Hierarchy    
	  Physics_parameters(XMLTagsUsed.PHYSICS_PARAMETERS),
	    //Third Level Hierarchy
	    Damping(XMLTagsUsed.DAMPING),
	        //Fourth Level Hierarchy
	        Linear_velocity(XMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY),
	        Angular_velocity(XMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY),
	    //Third Level Hierarchy
	    Realistic_collision(XMLTagsUsed.REALISTIC_COLLISION),
	    Gravity(XMLTagsUsed.GRAVITY),
	    Constraint_rorce_mix(XMLTagsUsed.CONSTRAINT_FORCE_MIX),
	    Error_reduction_parameter(XMLTagsUsed.ERROR_REDUCTION_PARAMETER),
	    Use_mouse_event_queue(XMLTagsUsed.USE_MOUSE_EVENT_QUEUE),
	    Synchronize_with_controllers((XMLTagsUsed.SYNC_WITH_CONTROLLERS)),
	    Physics_simulation_controller_step_factor(XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR);

	private XMLTagsUsed xmlTagsUsed;

	SimulationTabTreeElements(XMLTagsUsed xmlTagsUsed){
		this.xmlTagsUsed=xmlTagsUsed;
	}
}
