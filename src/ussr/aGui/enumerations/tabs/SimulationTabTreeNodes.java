package ussr.aGui.enumerations.tabs;

import javax.swing.tree.DefaultMutableTreeNode;

import ussr.aGui.enumerations.hintpanel.HintsSimulationTab;
import ussr.aGui.tabs.simulation.SimulationTreeEditors;
import ussr.builder.enumerations.XMLTagsUsed;

/**
 * Contains the names of nodes displayed in Simulation tab tree view.
 * Moreover, associates each name with its: 1)placement in the tree view (for instance: FIRST- is parent of SECOND - which in turn is parent of THIRD),
 * 2) Call for editor displayed in GUI, 3) Call for hint displayed in Display for hints and 4) XML tag used to save and load simulation.
 * 
 * NOTE: If you want to add new node, just define it here by defining each of above elements.
 * 
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
     *  The placement of tree node in the tree view.
     */
    private PlaceInTreeView placeInHierarchy;
    
    /**
     * The panel defining visual appearance of edit value part of GUI(SimulationTab)
     */
    private javax.swing.JPanel jPanelEditor;
    
	/**
	 * The tag used in xml file saving/loading.
	 */
	private XMLTagsUsed xmlTagsUsed;
	
    /**
     * The hint displayed in display for hits.
     */
    private HintsSimulationTab hintSimulationTab; 

	/**
     *  The tree node as defined in implementation of tree view.
     */
    private DefaultMutableTreeNode defaultMutableTreeNode;

	/**
	 * Contains the names of nodes displayed in Simulation tab tree view.
     * Moreover, associates each name with its: 1)placement in the tree view (for instance: FIRST- is parent of SECOND - which in turn is parent of THIRD),
     * 2) Call for editor displayed in GUI, 3) Call for hint displayed in Display for hints and 4) XML tag used to save and load simulation.
     * 
     * NOTE: If you want to add new node, just define it here by defining each of above values.
	 * @param placeInHierarchy, the placement of tree node in the tree view.
	 * @param jPanelEditor, the panel defining visual appearance of edit value part of GUI(SimulationTab).
	 * @param hintSimulationTab, the hint displayed in display for hits.
	 * @param xmlTagsUsed, the tag used in xml file saving/loading.
	 */
	SimulationTabTreeNodes(PlaceInTreeView placeInHierarchy,javax.swing.JPanel jPanelEditor, HintsSimulationTab hintSimulationTab,XMLTagsUsed xmlTagsUsed){
		this.placeInHierarchy = placeInHierarchy;
		this.jPanelEditor = jPanelEditor;
		this.hintSimulationTab =hintSimulationTab;
		this.xmlTagsUsed=xmlTagsUsed;
	}
	
	/**
	 * Returns the placement of tree node in the tree view.
	 * @return the placement of tree node in the tree view.
	 */
	public PlaceInTreeView getPlaceInHierarchy() {
		return placeInHierarchy;
	}
	
	/**
	 * Returns the hint displayed in display for hits.
	 * @return the hint displayed in display for hits.
	 */
	public HintsSimulationTab getHintSimulationTab() {
		return hintSimulationTab;
	}

	/**
	 * Returns the panel defining visual appearance of edit value part of GUI(SimulationTab).
	 * @return the panel defining visual appearance of edit value part of GUI(SimulationTab).
	 */
	public javax.swing.JPanel getJPanelEditor() {
		return jPanelEditor;
	}
	
	/**
	 * Returns the tree node as defined in implementation of tree view.
	 * @return the tree node as defined in implementation of tree view.
	 */
	public DefaultMutableTreeNode getDefaultMutableTreeNode() {
		return defaultMutableTreeNode;
	}

	/**
	 * Sets the tree node as defined in implementation of tree view.
	 * @param defaultMutableTreeNode, the tree node as defined in implementation of tree view.
	 */
	public void setDefaultMutableTreeNode(DefaultMutableTreeNode defaultMutableTreeNode) {
		this.defaultMutableTreeNode = defaultMutableTreeNode;
	}
	
	/**
	 * Returns the name of chosen enumeration with underscore replaced with space.
	 * @return the name of chosen enumeration with underscore replaced with space.
	 */
	public String getUserFriendlyName(){
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
		
		NOT_USED ,//Keyword for nodes not used directly in the tree view
	}
 
}
