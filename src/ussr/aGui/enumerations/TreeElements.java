package ussr.aGui.enumerations;

import ussr.builder.enumerations.XMLTagsUsed;

/**
 * @author Konstantinas
 *
 */
public enum TreeElements {
	//First Level Hierarchy	
	Simulation(XMLTagsUsed.SIMULATION),
	
	  //Second Level Hierarchy
	  //Often used
	  Physics_Simulation_Step_Size(XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE),
	  Resolution_Factor(XMLTagsUsed.RESOLUTION_FACTOR),

	  //Second Level Hierarchy
	  Robots(XMLTagsUsed.ROBOTS),
	    //Third Level Hierarchy
	    Robot(XMLTagsUsed.ROBOT),
	        //Fourth Level Hierarchy
	        Type(XMLTagsUsed.TYPE),
	        Morphology_Location(XMLTagsUsed.MORPHOLOGY_LOCATION),
	        Controller_Location(XMLTagsUsed.CONTROLLER_LOCATION),

	  //Second Level Hierarchy
	  World_Description(XMLTagsUsed.WORLD_DESCRIPTION), 
	    //Third Level Hierarchy
	    Plane_Size(XMLTagsUsed.PLANE_SIZE),
	    Plane_Texture(XMLTagsUsed.PLANE_TEXTURE),
	    Camera_Position(XMLTagsUsed.CAMERA_POSITION),
	    The_World_Is_Flat(XMLTagsUsed.THE_WORLD_IS_FLAT),
	    Has_Background_Scenery(XMLTagsUsed.HAS_BACKGROUND_SCENERY),
	    Has_Heavy_Obstacles(XMLTagsUsed.HAS_HEAVY_OBSTACLES),
	    Is_Frame_Grabbing_Active(XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE),

	  //Second Level Hierarchy    
	  Physics_Parameters(XMLTagsUsed.PHYSICS_PARAMETERS),
	    //Third Level Hierarchy
	    Damping(XMLTagsUsed.DAMPING),
	        //Fourth Level Hierarchy
	        Linear_Velocity(XMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY),
	        Angular_Velocity(XMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY),
	    //Third Level Hierarchy
	    Realistic_Collision(XMLTagsUsed.REALISTIC_COLLISION),
	    Gravity(XMLTagsUsed.GRAVITY),
	    Constraint_Force_Mix(XMLTagsUsed.CONSTRAINT_FORCE_MIX),
	    Error_Reduction_Parameter(XMLTagsUsed.ERROR_REDUCTION_PARAMETER),
	    Use_Mouse_Event_Queue(XMLTagsUsed.USE_MOUSE_EVENT_QUEUE),
	    Synchronize_With_Controllers((XMLTagsUsed.SYNC_WITH_CONTROLLERS)),
	    Physics_Simulation_Controller_Step_Factor(XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR);

	private XMLTagsUsed xmlTagsUsed;

	TreeElements(XMLTagsUsed xmlTagsUsed){
		this.xmlTagsUsed=xmlTagsUsed;
	}
}
