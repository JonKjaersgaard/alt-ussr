package ussr.aGui.enumerations.tabs;

import javax.swing.tree.DefaultMutableTreeNode;

import ussr.aGui.enumerations.hintpanel.HintsSimulationTab;
import ussr.aGui.tabs.simulation.SimulationTreeEditors;
import ussr.builder.enumerations.XMLTagsUsed;

/**
 * Contains the names of nodes displayed in Simulation tab tree view.
 * Moreover, associates each name with the tag used during saving/or loading of simulation(also robot).
 * @author Konstantinas 
 */
public enum SimulationTabTreeNodes {
	
	//First Level Hierarchy	
	Simulation(PlaceInTreeView.FIRST,null,HintsSimulationTab.SIMULATION,XMLTagsUsed.SIMULATION),
	
	//Second Level Hierarchy	
	  //Often used
	  Physics_simulation_step_size(PlaceInTreeView.SECOND,SimulationTreeEditors.addPhysicsSimulationStepSizeEditor(),HintsSimulationTab.PHYSICS_SIMULATION_STEP_SIZE,XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE),
	  Resolution_Factor(PlaceInTreeView.SECOND,SimulationTreeEditors.addResolutionFactorEditor(),HintsSimulationTab.RESOLUTION_FACTOR,XMLTagsUsed.RESOLUTION_FACTOR),

	   //Second Level Hierarchy
	  Robots(PlaceInTreeView.SECOND,SimulationTreeEditors.addRobotsEditor(),HintsSimulationTab.ROBOTS,XMLTagsUsed.ROBOTS),

	  //Third Level Hierarchy
	     Robot_Nr(PlaceInTreeView.NOT_USED,null,null,XMLTagsUsed.ROBOT_NR),
      

	  //Second Level Hierarchy
	  World_description(PlaceInTreeView.SECOND,null,HintsSimulationTab.WORLD_DESCRIPTION,XMLTagsUsed.WORLD_DESCRIPTION), 
	    //Third Level Hierarchy
	    Plane_size(PlaceInTreeView.THIRD,SimulationTreeEditors.addPlaneSizeEditor(),HintsSimulationTab.PLANE_SIZE,XMLTagsUsed.PLANE_SIZE),
	    Plane_texture(PlaceInTreeView.THIRD,SimulationTreeEditors.addPlaneTextureEditor(),HintsSimulationTab.PLANE_TEXTURE,XMLTagsUsed.PLANE_TEXTURE),
	    Camera_position(PlaceInTreeView.THIRD,SimulationTreeEditors.addCameraPositionEditor(),HintsSimulationTab.CAMERA_POSITION,XMLTagsUsed.CAMERA_POSITION),
	    The_world_is_flat(PlaceInTreeView.THIRD,SimulationTreeEditors.addTheWorldIsFlatEditor(),HintsSimulationTab.THE_WORLD_IS_FLAT,XMLTagsUsed.THE_WORLD_IS_FLAT),
	    Has_background_scenery(PlaceInTreeView.THIRD,SimulationTreeEditors.addHasBackgroundSceneryEditor(),HintsSimulationTab.HAS_BACKGROUND_SCENERY,XMLTagsUsed.HAS_BACKGROUND_SCENERY),
	    Has_heavy_obstacles(PlaceInTreeView.THIRD,SimulationTreeEditors.addHasHeavyObstaclesEditor(),HintsSimulationTab.HAS_HEAVY_OBSTACLES,XMLTagsUsed.HAS_HEAVY_OBSTACLES),
	    Is_frame_grabbing_active(PlaceInTreeView.THIRD,SimulationTreeEditors.addIsFrameGrabbingActiveEditor(),HintsSimulationTab.IS_FRAME_GRABBING_ACTIVE,XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE),

	  //Second Level Hierarchy    
	  Physics_parameters(PlaceInTreeView.SECOND,null,HintsSimulationTab.PHYSICS_PARAMETERS,XMLTagsUsed.PHYSICS_PARAMETERS),
	    //Third Level Hierarchy
	    Damping(PlaceInTreeView.THIRD,SimulationTreeEditors.addDampingEditor(),HintsSimulationTab.DAMPING,XMLTagsUsed.DAMPING),
	            //Linear_velocity(PlaceInTreeView.NOT_USED,null,nullXMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY),
	            //Angular_velocity(PlaceInTreeView.NOT_USED,null,XMLTagsUsed.WORLD_DAMPING_ANGULAR_VELOCITY),	            
	    Realistic_collision(PlaceInTreeView.THIRD,SimulationTreeEditors.addRealisticCollisionEditor(),HintsSimulationTab.REALISTIC_COLLISION,XMLTagsUsed.REALISTIC_COLLISION),
	    Gravity(PlaceInTreeView.THIRD,SimulationTreeEditors.addGravityEditor(),HintsSimulationTab.GRAVITY,XMLTagsUsed.GRAVITY),
	    Plane_material(PlaceInTreeView.THIRD,SimulationTreeEditors.addPlaneMaterialEditor(),HintsSimulationTab.PLANE_MATERIAL,XMLTagsUsed.PLANE_MATERIAL),
	    Maintain_rotational_joint_positions(PlaceInTreeView.THIRD,SimulationTreeEditors.addMaintainRotationalJointPositionsEditor(),HintsSimulationTab.MAINTAIN_ROTATIONAL_JOINT_POSITIONS,XMLTagsUsed.MAINTAIN_ROTATIONAL_JOINT_POSITIONS),
	    Constraint_force_mixing(PlaceInTreeView.THIRD,SimulationTreeEditors.addConstraintForceMixEditor(),HintsSimulationTab.CONSTRAINT_FORCE_MIXING,XMLTagsUsed.CONSTRAINT_FORCE_MIX),
	    Error_reduction_parameter(PlaceInTreeView.THIRD,SimulationTreeEditors.addErrorReductionParameterEditor(),HintsSimulationTab.ERROR_REDUCTION_PARAMETER,XMLTagsUsed.ERROR_REDUCTION_PARAMETER),
	    Use_module_event_queue(PlaceInTreeView.THIRD,SimulationTreeEditors.addUseModuleEventQueueEditor(),HintsSimulationTab.USE_MODULE_EVENT_QUEUE,XMLTagsUsed.USE_MODULE_EVENT_QUEUE),
	    Synchronize_with_controllers(PlaceInTreeView.THIRD,SimulationTreeEditors.addSynchronizeWithControllersEditor(),HintsSimulationTab.SYNCHRONIZE_WITH_CONTROLLERS,XMLTagsUsed.SYNC_WITH_CONTROLLERS),
	    Physics_simulation_controller_step_factor(PlaceInTreeView.THIRD,SimulationTreeEditors.addPhysicsSimulationControllerStepFactor(),HintsSimulationTab.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR,XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR)
	  ;

	/**
	 * The tag used in xml file saving/loading
	 */
	private XMLTagsUsed xmlTagsUsed;
	
    /**
     * 
     */
    private PlaceInTreeView placeInHierarchy;

    /**
     * 
     */
    private DefaultMutableTreeNode defaultMutableTreeNode;
    
    private javax.swing.JPanel jPanelEditor;
    
    private HintsSimulationTab hint; 



	public HintsSimulationTab getHint() {
		return hint;
	}



	public javax.swing.JPanel getJPanelEditor() {
		return jPanelEditor;
	}
	


	/**
	 * Contains the names of nodes displayed in Simulation tab tree view.
     * Moreover, associates each name with the tag used during saving/or loading of simulation(also robot).
	 * @param xmlTagsUsed,The tag used in xml file saving/loading
	 */
	SimulationTabTreeNodes(PlaceInTreeView placeInHierarchy,javax.swing.JPanel jPanelEditor, HintsSimulationTab hint,XMLTagsUsed xmlTagsUsed){
		this.placeInHierarchy = placeInHierarchy;
		this.jPanelEditor = jPanelEditor;
		this.hint =hint;
		this.xmlTagsUsed=xmlTagsUsed;
	}
	
	public PlaceInTreeView getPlaceInHierarchy() {
		return placeInHierarchy;
	}
	
	public DefaultMutableTreeNode getDefaultMutableTreeNode() {
		return defaultMutableTreeNode;
	}



	public void setDefaultMutableTreeNode(DefaultMutableTreeNode defaultMutableTreeNode) {
		this.defaultMutableTreeNode = defaultMutableTreeNode;
	}
	
	public String getName(){
		return this.toString().replace("_", " ");
	}
	
	
	/**
	 * The constants for placement of tree nodes in the tree view (hierarchical structure).
	 * @author Konstantinas
	 */
	public enum PlaceInTreeView{
		FIRST,// first in the hierarchy (or root node)
		SECOND,// second in the hierarchy (or child of the first)
		THIRD ,// third in the hierarchy (or child of the second)
		
		NOT_USED ,//Keyword for nodes not used directly in the tree view or used
	}
 
}
