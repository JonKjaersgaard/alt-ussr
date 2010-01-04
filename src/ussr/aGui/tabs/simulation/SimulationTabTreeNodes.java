package ussr.aGui.tabs.simulation;

import javax.swing.tree.DefaultMutableTreeNode;

import ussr.aGui.enumerations.hintpanel.HintsSimulationTab;
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
	SIMULATION(PlaceInTreeView.FIRST,null,HintsSimulationTab.SIMULATION,XMLTagsUsed.SIMULATION),
	
	//Second Level Hierarchy	
	  //Often used from Physics Parameters
	  PHYSICS_SIMULATION_STEP_SIZE(PlaceInTreeView.SECOND,SimulationTreeEditors.addPhysicsSimulationStepSizeEditor(),HintsSimulationTab.PHYSICS_SIMULATION_STEP_SIZE,XMLTagsUsed.PHYSICS_SIMULATION_STEP_SIZE),
	  RESOLUTION_FACTOR(PlaceInTreeView.SECOND,SimulationTreeEditors.addResolutionFactorEditor(),HintsSimulationTab.RESOLUTION_FACTOR,XMLTagsUsed.RESOLUTION_FACTOR),

	   //Second Level Hierarchy
	  ROBOTS(PlaceInTreeView.SECOND,SimulationTreeEditors.ROBOTS_EDITOR,HintsSimulationTab.ROBOTS,XMLTagsUsed.ROBOTS),

	  //Third Level Hierarchy
	     ROBOT_NR(PlaceInTreeView.NOT_USED,null,null,XMLTagsUsed.ROBOT_NR),
     
	  //Second Level Hierarchy
	  WORLD_DESCRIPTION(PlaceInTreeView.SECOND,null,HintsSimulationTab.WORLD_DESCRIPTION,XMLTagsUsed.WORLD_DESCRIPTION), 
	    //Third Level Hierarchy
	    PLANE_SIZE(PlaceInTreeView.THIRD,SimulationTreeEditors.addPlaneSizeEditor(),HintsSimulationTab.PLANE_SIZE,XMLTagsUsed.PLANE_SIZE),
	    PLANE_TEXTURE(PlaceInTreeView.THIRD,SimulationTreeEditors.addPlaneTextureEditor(),HintsSimulationTab.PLANE_TEXTURE,XMLTagsUsed.PLANE_TEXTURE),
	    CAMERA_POSITION(PlaceInTreeView.THIRD,SimulationTreeEditors.addCameraPositionEditor(),HintsSimulationTab.CAMERA_POSITION,XMLTagsUsed.CAMERA_POSITION),
	    THE_WORLD_IS_FLAT(PlaceInTreeView.THIRD,SimulationTreeEditors.addTheWorldIsFlatEditor(),HintsSimulationTab.THE_WORLD_IS_FLAT,XMLTagsUsed.THE_WORLD_IS_FLAT),
	    HAS_BACKGROUND_SCENERY(PlaceInTreeView.THIRD,SimulationTreeEditors.addHasBackgroundSceneryEditor(),HintsSimulationTab.HAS_BACKGROUND_SCENERY,XMLTagsUsed.HAS_BACKGROUND_SCENERY),
	    HAS_HEAVY_OBSTACLES(PlaceInTreeView.THIRD,SimulationTreeEditors.addHasHeavyObstaclesEditor(),HintsSimulationTab.HAS_HEAVY_OBSTACLES,XMLTagsUsed.HAS_HEAVY_OBSTACLES),
	    IS_FRAME_GRABBING_ACTIVE(PlaceInTreeView.THIRD,SimulationTreeEditors.addIsFrameGrabbingActiveEditor(),HintsSimulationTab.IS_FRAME_GRABBING_ACTIVE,XMLTagsUsed.IS_FRAME_GRABBING_ACTIVE),

	  //Second Level Hierarchy    
	  PHYSICS_PARAMETERS(PlaceInTreeView.SECOND,null,HintsSimulationTab.PHYSICS_PARAMETERS,XMLTagsUsed.PHYSICS_PARAMETERS),
	    //Third Level Hierarchy
	    WORLD_DAMPING(PlaceInTreeView.THIRD,SimulationTreeEditors.addDampingEditor(),HintsSimulationTab.DAMPING,XMLTagsUsed.DAMPING),
	            //Linear_velocity(PlaceInTreeView.NOT_USED,null,nullXMLTagsUsed.WORLD_DAMPING_LINEAR_VELOCITY),
	            //Angular_velocity(PlaceInTreeView.NOT_USED,null,XMLTagsUsed.WORLD_DAMPING_ANGULAR_VELOCITY),	            
	    REALISTIC_COLLISION(PlaceInTreeView.THIRD,SimulationTreeEditors.addRealisticCollisionEditor(),HintsSimulationTab.REALISTIC_COLLISION,XMLTagsUsed.REALISTIC_COLLISION),
	    GRAVITY(PlaceInTreeView.THIRD,SimulationTreeEditors.addGravityEditor(),HintsSimulationTab.GRAVITY,XMLTagsUsed.GRAVITY),
	    PLANE_MATERIAL(PlaceInTreeView.THIRD,SimulationTreeEditors.addPlaneMaterialEditor(),HintsSimulationTab.PLANE_MATERIAL,XMLTagsUsed.PLANE_MATERIAL),
	    MAINTAIN_ROTATIONAL_JOINT_POSITIONS(PlaceInTreeView.THIRD,SimulationTreeEditors.addMaintainRotationalJointPositionsEditor(),HintsSimulationTab.MAINTAIN_ROTATIONAL_JOINT_POSITIONS,XMLTagsUsed.MAINTAIN_ROTATIONAL_JOINT_POSITIONS),
	    CONSTRAINT_FORCE_MIXING(PlaceInTreeView.THIRD,SimulationTreeEditors.addConstraintForceMixEditor(),HintsSimulationTab.CONSTRAINT_FORCE_MIXING,XMLTagsUsed.CONSTRAINT_FORCE_MIX),
	    ERROR_REDUCTION_PARAMETER(PlaceInTreeView.THIRD,SimulationTreeEditors.addErrorReductionParameterEditor(),HintsSimulationTab.ERROR_REDUCTION_PARAMETER,XMLTagsUsed.ERROR_REDUCTION_PARAMETER),
	    USE_MODULE_EVENT_QUEUE(PlaceInTreeView.THIRD,SimulationTreeEditors.addUseModuleEventQueueEditor(),HintsSimulationTab.USE_MODULE_EVENT_QUEUE,XMLTagsUsed.USE_MODULE_EVENT_QUEUE),
	    SYHCNRONIZE_WITH_CONTROLLERS(PlaceInTreeView.THIRD,SimulationTreeEditors.addSynchronizeWithControllersEditor(),HintsSimulationTab.SYNCHRONIZE_WITH_CONTROLLERS,XMLTagsUsed.SYNC_WITH_CONTROLLERS),
	    PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR(PlaceInTreeView.THIRD,SimulationTreeEditors.addPhysicsSimulationControllerStepFactor(),HintsSimulationTab.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR,XMLTagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR)
	  ;

	
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
	 * Returns the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
	 * underscore is replaced with space.
	 * @return the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
	 * underscore is replaced with space.
	 */
	public String getUserFriendlyName(){
		char[] characters = this.toString().replace("_", " ").toLowerCase().toCharArray();
		String name = (characters[0]+"").toUpperCase();
        for (int index =1;index<characters.length;index++){
        	name = name+characters[index];
        }		 
		return name;
	}	
 
}
