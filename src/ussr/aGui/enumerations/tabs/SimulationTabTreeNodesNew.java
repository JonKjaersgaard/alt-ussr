package ussr.aGui.enumerations.tabs;

import javax.swing.tree.DefaultMutableTreeNode;

import ussr.builder.enumerations.XMLTagsUsed;

/**
 * Contains the names of nodes displayed in Simulation tab tree view.
 * Moreover, associates each name with the tag used during saving/or loading of simulation(also robot).
 * @author Konstantinas 
 */
public enum SimulationTabTreeNodesNew {
	//First Level Hierarchy	
	Simulation(PlaceInHierarchy.FIRST,XMLTagsUsed.SIMULATION),
	
	//Second Level Hierarchy	
	  //Often used
	  Physics_simulation_step_size(PlaceInHierarchy.SECOND,XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE),
	  Resolution_Factor(PlaceInHierarchy.SECOND,XMLTagsUsed.RESOLUTION_FACTOR),

	   //Second Level Hierarchy
	  Robots(PlaceInHierarchy.SECOND,XMLTagsUsed.ROBOTS),

	    //Third Level Hierarchy
	    //Robot_Nr(XMLTagsUsed.ROBOT_NR),


	  //Second Level Hierarchy
	  World_description(PlaceInHierarchy.SECOND,XMLTagsUsed.WORLD_DESCRIPTION), 
	    //Third Level Hierarchy
	    Plane_size(PlaceInHierarchy.THIRD,XMLTagsUsed.PLANE_SIZE),
	    Plane_texture(PlaceInHierarchy.THIRD,XMLTagsUsed.PLANE_TEXTURE),
	    Camera_position(PlaceInHierarchy.THIRD,XMLTagsUsed.CAMERA_POSITION),
	    The_world_is_flat(PlaceInHierarchy.THIRD,XMLTagsUsed.THE_WORLD_IS_FLAT),
	    Has_background_scenery(PlaceInHierarchy.THIRD,XMLTagsUsed.HAS_BACKGROUND_SCENERY),
	    Has_heavy_obstacles(PlaceInHierarchy.THIRD,XMLTagsUsed.HAS_HEAVY_OBSTACLES),
	    Is_frame_grabbing_active(PlaceInHierarchy.THIRD,XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE),

	  //Second Level Hierarchy    
	  Physics_parameters(PlaceInHierarchy.SECOND,XMLTagsUsed.PHYSICS_PARAMETERS),
	    //Third Level Hierarchy
	    Damping(PlaceInHierarchy.THIRD,XMLTagsUsed.DAMPING),
	    Realistic_collision(PlaceInHierarchy.THIRD,XMLTagsUsed.REALISTIC_COLLISION),
	    Gravity(PlaceInHierarchy.THIRD,XMLTagsUsed.GRAVITY),
	    Constraint_force_mixing(PlaceInHierarchy.THIRD,XMLTagsUsed.CONSTRAINT_FORCE_MIX),
	    Error_reduction_parameter(PlaceInHierarchy.THIRD,XMLTagsUsed.ERROR_REDUCTION_PARAMETER),
	    Use_module_event_queue(PlaceInHierarchy.THIRD,XMLTagsUsed.USE_MODULE_EVENT_QUEUE),
	    Synchronize_with_controllers(PlaceInHierarchy.THIRD,XMLTagsUsed.SYNC_WITH_CONTROLLERS),
	    Physics_simulation_controller_step_factor(PlaceInHierarchy.THIRD,XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR);

	/**
	 * The tag used in xml file saving/loading
	 */
	private XMLTagsUsed xmlTagsUsed;
	
    private PlaceInHierarchy placeInHierarchy;

    private DefaultMutableTreeNode defaultMutableTreeNode;

	/**
	 * Contains the names of nodes displayed in Simulation tab tree view.
     * Moreover, associates each name with the tag used during saving/or loading of simulation(also robot).
	 * @param xmlTagsUsed,The tag used in xml file saving/loading
	 */
	SimulationTabTreeNodesNew(PlaceInHierarchy placeInHierarchy,XMLTagsUsed xmlTagsUsed){
		this.placeInHierarchy = placeInHierarchy;
		this.xmlTagsUsed=xmlTagsUsed;
	}
	
	public PlaceInHierarchy getPlaceInHierarchy() {
		return placeInHierarchy;
	}
	
	public DefaultMutableTreeNode getDefaultMutableTreeNode() {
		return defaultMutableTreeNode;
	}



	public void setDefaultMutableTreeNode(
			DefaultMutableTreeNode defaultMutableTreeNode) {
		this.defaultMutableTreeNode = defaultMutableTreeNode;
	}
	
	
	
	public enum PlaceInHierarchy{
		FIRST,SECOND,THIRD
	}
 
}
