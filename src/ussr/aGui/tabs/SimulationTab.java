package ussr.aGui.tabs;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Map;



import javax.swing.BorderFactory;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import javax.swing.border.TitledBorder;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import ussr.aGui.MainFrames;
import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.enumerations.SimulationTabTreeNodes;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.helpers.RobotSpecification;

import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelTypes;
import ussr.aGui.tabs.controllers.SimulationTabController;

import ussr.description.setup.WorldDescription.CameraPosition;


/**
 * Defines visual appearance of the tab called Simulation.
 * @author Konstantinas
 */
public class SimulationTab extends Tabs {


	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * Defines visual appearance of the tab called "Simulation".
	 * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane from the top.
	 * @param tabTitle, the title of the tab.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public SimulationTab(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle, String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());
		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {
		
		jScrollPaneTree = new javax.swing.JScrollPane();

		jPanelEditor = new javax.swing.JPanel(new GridBagLayout());

		//First in hierarchy
		DefaultMutableTreeNode firstNodeHierarchySimulation = new DefaultMutableTreeNode(SimulationTabTreeNodes.Simulation.toString());

		//Second in hierarchy
		DefaultMutableTreeNode secondNodeHierarchyPhysicsSimulationStepSize =  new DefaultMutableTreeNode(SimulationTabTreeNodes.Physics_simulation_step_size.toString().replace("_", " "));
		firstNodeHierarchySimulation.add(secondNodeHierarchyPhysicsSimulationStepSize);
		DefaultMutableTreeNode secondNodeHierarchyResolutionFactor =  new DefaultMutableTreeNode(SimulationTabTreeNodes.Resolution_Factor.toString().replace("_", " "));
		firstNodeHierarchySimulation.add(secondNodeHierarchyResolutionFactor);
		
		DefaultMutableTreeNode secondNodeHierarchyRobots =  new DefaultMutableTreeNode(SimulationTabTreeNodes.Robots.toString());
		firstNodeHierarchySimulation.add(secondNodeHierarchyRobots);
		
		//Third in hierarchy
		DefaultMutableTreeNode thirdNodeHierarchyRobot =  new DefaultMutableTreeNode(SimulationTabTreeNodes.Robot.toString());
		secondNodeHierarchyRobots.add(thirdNodeHierarchyRobot);
		
		//Fourth in hierarchy
		DefaultMutableTreeNode fourthNodeHierarchyRobotType = new DefaultMutableTreeNode(SimulationTabTreeNodes.Type.toString());
		thirdNodeHierarchyRobot.add(fourthNodeHierarchyRobotType);		
		DefaultMutableTreeNode fourthNodeHierarchyMorphologyLocation = new DefaultMutableTreeNode(SimulationTabTreeNodes.Morphology.toString().replace("_", " "));
		thirdNodeHierarchyRobot.add(fourthNodeHierarchyMorphologyLocation);		
		DefaultMutableTreeNode fourthNodeHierarchyControllerLocation = new DefaultMutableTreeNode(SimulationTabTreeNodes.Controller.toString().replace("_", " "));
		thirdNodeHierarchyRobot.add(fourthNodeHierarchyControllerLocation);

		//Second in hierarchy
		DefaultMutableTreeNode secondNodeHierarchyWorldDescription = new DefaultMutableTreeNode(SimulationTabTreeNodes.World_description.toString().replace("_", " "));
		firstNodeHierarchySimulation.add(secondNodeHierarchyWorldDescription);

		//Third in hierarchy
		DefaultMutableTreeNode thirdNodeHierarchyPlaneSize =  new DefaultMutableTreeNode(SimulationTabTreeNodes.Plane_size.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyPlaneSize);
		DefaultMutableTreeNode thirdNodeHierarchyPlaneTexture =  new DefaultMutableTreeNode(SimulationTabTreeNodes.Plane_texture.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyPlaneTexture);
		DefaultMutableTreeNode thirdNodeHierarchyCameraPosition = new DefaultMutableTreeNode(SimulationTabTreeNodes.Camera_position.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyCameraPosition);
		DefaultMutableTreeNode thirdNodeHierarchyTheWorldIsFlat = new DefaultMutableTreeNode(SimulationTabTreeNodes.The_world_is_flat.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyTheWorldIsFlat);
		DefaultMutableTreeNode thirdNodeHierarchyHasBackgroundScenery = new DefaultMutableTreeNode(SimulationTabTreeNodes.Has_background_scenery.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyHasBackgroundScenery);
		DefaultMutableTreeNode thirdNodeHierarchyHasHeavyObstacles = new DefaultMutableTreeNode(SimulationTabTreeNodes.Has_heavy_obstacles.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyHasHeavyObstacles);
		DefaultMutableTreeNode thirdNodeHierarchyIsFrameGrabbingActive = new DefaultMutableTreeNode(SimulationTabTreeNodes.Is_frame_grabbing_active.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyIsFrameGrabbingActive);

		//Second in hierarchy
		DefaultMutableTreeNode secondNodeHierarchyPhysicsParameters = new DefaultMutableTreeNode(SimulationTabTreeNodes.Physics_parameters.toString().replace("_", " "));
		firstNodeHierarchySimulation.add(secondNodeHierarchyPhysicsParameters);
		
		//Third in hierarchy
		DefaultMutableTreeNode thirdNodeHierarchyDamping= new DefaultMutableTreeNode(SimulationTabTreeNodes.Damping.toString());
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyDamping);
		
		//Fourth in hierarchy
		DefaultMutableTreeNode fourthNodeHierarchyDampingLinearVelocity = new DefaultMutableTreeNode(SimulationTabTreeNodes.Linear_velocity.toString().replace("_", " "));
		thirdNodeHierarchyDamping.add(fourthNodeHierarchyDampingLinearVelocity);
		
		DefaultMutableTreeNode fourthNodeHierarchyAngularVelocity = new DefaultMutableTreeNode(SimulationTabTreeNodes.Angular_velocity.toString().replace("_", " "));
		thirdNodeHierarchyDamping.add(fourthNodeHierarchyAngularVelocity);
		
		//Third in hierarchy
		DefaultMutableTreeNode thirdNodeHierarchyRealisticCollision = new DefaultMutableTreeNode(SimulationTabTreeNodes.Realistic_collision.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyRealisticCollision);
		DefaultMutableTreeNode thirdNodeHierarchyGravity = new DefaultMutableTreeNode(SimulationTabTreeNodes.Gravity.toString());
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyGravity);
		DefaultMutableTreeNode thirdNodeHierarchyConstraintForceMix = new DefaultMutableTreeNode(SimulationTabTreeNodes.Constraint_force_mix.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyConstraintForceMix);
		DefaultMutableTreeNode thirdNodeHierarchyErrorReductionParameter = new DefaultMutableTreeNode(SimulationTabTreeNodes.Error_reduction_parameter.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyErrorReductionParameter);
		DefaultMutableTreeNode thirdNodeHierarchyUseMouseEventQueue = new DefaultMutableTreeNode(SimulationTabTreeNodes.Use_mouse_event_queue.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyUseMouseEventQueue);
		DefaultMutableTreeNode thirdNodeHierarchySynchronizeWithControllers = new DefaultMutableTreeNode(SimulationTabTreeNodes.Synchronize_with_controllers.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchySynchronizeWithControllers);
		DefaultMutableTreeNode thirdNodeHierarchyPhysicsSimulationControllerStepFactor = new DefaultMutableTreeNode(SimulationTabTreeNodes.Physics_simulation_controller_step_factor.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyPhysicsSimulationControllerStepFactor );


		jTree1 = new javax.swing.JTree(firstNodeHierarchySimulation);

		ImageIcon closedIcon = new ImageIcon(DIRECTORY_ICONS + EXPANSION_CLOSED);
		ImageIcon openIcon = new ImageIcon(DIRECTORY_ICONS + EXPANSION_OPENED);
		ImageIcon leafIcon = new ImageIcon(DIRECTORY_ICONS + FINAL_LEAF);

		if (openIcon != null) {
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();		    
			renderer.setClosedIcon(closedIcon);
			renderer.setOpenIcon(openIcon);
			renderer.setLeafIcon(leafIcon);
			jTree1.setCellRenderer(renderer);
		}

		jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				jTree1.getLastSelectedPathComponent();

				if (node == null)
					//Nothing is selected.	
					return;

				Object nodeInfo = node.getUserObject();
				if (node.isLeaf()) {
					System.out.println(node.getPath()[node.getPath().length-1].toString());
					SimulationTabController.jTreeItemSelectedActionPerformed(node.getPath()[node.getPath().length-1].toString());
				} 

			}
		});
		
		


		jScrollPaneTree.setViewportView(jTree1);
		jScrollPaneTree.setVisible(false);
		jScrollPaneTree.setPreferredSize(new Dimension(300,300));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jScrollPaneTree,gridBagConstraints);


		TitledBorder displayTitle;
		displayTitle = BorderFactory.createTitledBorder("Edit Value");
		displayTitle.setTitleJustification(TitledBorder.CENTER);
		jPanelEditor.setBorder(displayTitle);
		jPanelEditor.setVisible(false);
		jPanelEditor.setPreferredSize(new Dimension(300,300));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;		

		super.jComponent.add(jPanelEditor,gridBagConstraints);
		
		hintPanel = new HintPanel(600, 100);
		hintPanel.setType(HintPanelTypes.INFORMATION);
		hintPanel.setBorderTitle("Display for hints");
		
		hintPanel.setText("Would be nice to add a short description of each element in the tree :).");
		hintPanel.setVisible(false);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth=2;
		
		super.jComponent.add(hintPanel,gridBagConstraints);
		


	}

	public static javax.swing.JPanel getJPanelEditor() {
	
		return jPanelEditor;
	}

	public static void setTabVisible(boolean visible) {
		jScrollPaneTree.setVisible(visible);
		jPanelEditor.setVisible(visible);
         hintPanel.setVisible(visible);
	}

	
	
	public static void addPlaneSizeEditor(){

		jSpinnerPlaneSize = new javax.swing.JSpinner();
		jSpinnerPlaneSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 1));
		SimulationTabController.setJSpinnerPlaneSizeValue(jSpinnerPlaneSize);

		jPanelEditor.add(jSpinnerPlaneSize);
	}
	
	
	
	public static void addPlaneTextureEditor(){

		jComboBoxPlaneTexture = new javax.swing.JComboBox(); 
		jComboBoxPlaneTexture.setModel(new DefaultComboBoxModel(TextureDescriptions.values()));
		SimulationTabController.setSelectedJComboBoxPlaneTexture(jComboBoxPlaneTexture);
		jPanelEditor.add(jComboBoxPlaneTexture);

	}
	
	public static void addCameraPositionEditor(){

		jComboBoxCameraPosition = new javax.swing.JComboBox(); 
		jComboBoxCameraPosition.setModel(new DefaultComboBoxModel(CameraPosition.values()));
		SimulationTabController.setSelectedJComboBoxCameraPosition(jComboBoxCameraPosition);
		jPanelEditor.add(jComboBoxCameraPosition);

	}
	
	public static void addTheWorldIsFlatEditor(){
		jCheckBoxTheWorldIsFlat =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxTheWorldIsFlat(jCheckBoxTheWorldIsFlat);
	
		jPanelEditor.add(jCheckBoxTheWorldIsFlat);
	}

	
	public static void addHasBackgroundSceneryEditor(){
		jCheckBoxHasBackgroundScenery =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxHasBackgroundScenery(jCheckBoxHasBackgroundScenery);
		jPanelEditor.add(jCheckBoxHasBackgroundScenery);
	}
	
	public static void addHasHeavyObstaclesEditor(){
		jCheckBoxHasHeavyObstacles = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxHasHeavyObstacles(jCheckBoxHasHeavyObstacles);
		
		jPanelEditor.add(jCheckBoxHasHeavyObstacles);
		
	}
	
	public static void addIsFrameGrabbingActiveEditor(){
		jCheckBoxIsFrameGrabbingActive = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxIsFrameGrabbingActive(jCheckBoxIsFrameGrabbingActive);
		
		jPanelEditor.add(jCheckBoxIsFrameGrabbingActive);
		
	}
	
	public static void addRobotTypeEditor(){
		jLabelRobotType = new javax.swing.JLabel(); 
		SimulationTabController.setJLabelRobotType(jLabelRobotType);
		
		jPanelEditor.add(jLabelRobotType);
		
	}
	
	public static void addDampingLinearVelocityEditor(){
		jSpinnerDampingLinearVelocity = new javax.swing.JSpinner();
		jSpinnerDampingLinearVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingLinearVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerDampingLinearVelocity(jSpinnerDampingLinearVelocity);		
		jPanelEditor.add(jSpinnerDampingLinearVelocity);		
	}

	public static void addDampingAngularVelocityEditor() {
		jSpinnerDampingAngularVelocity = new javax.swing.JSpinner();
		jSpinnerDampingAngularVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingAngularVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerDampingAngularVelocity(jSpinnerDampingAngularVelocity);		
		jPanelEditor.add(jSpinnerDampingAngularVelocity);	
		
	}
	
	
	public static void addPhysicsSimulationStepSizeEditor() {
		jSpinnerPhysicsSimulationStepSize = new javax.swing.JSpinner();
		jSpinnerPhysicsSimulationStepSize.setPreferredSize(new Dimension(60,20));
		jSpinnerPhysicsSimulationStepSize.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerPhysicsSimulationStepSize(jSpinnerPhysicsSimulationStepSize);		
		jPanelEditor.add(jSpinnerPhysicsSimulationStepSize);	
		
	}
	
	public static void addRealisticCollisionEditor() {
		jCheckBoxRealisticCollision = new javax.swing.JCheckBox ();
       SimulationTabController.setSelectedJCheckBoxRealisticCollision(jCheckBoxRealisticCollision);		
		jPanelEditor.add(jCheckBoxRealisticCollision);
		
	}
	
	public static void addGravityEditor() {
		jSpinnerGravity = new javax.swing.JSpinner();
		jSpinnerGravity.setPreferredSize(new Dimension(60,20));
		jSpinnerGravity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-100.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerGravity(jSpinnerGravity);		
		jPanelEditor.add(jSpinnerGravity);	
		
	}

	public static void addConstraintForceMixEditor() {
		jSpinnerConstraintForceMix = new javax.swing.JSpinner();
		jSpinnerConstraintForceMix.setPreferredSize(new Dimension(60,20));
		jSpinnerConstraintForceMix.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerConstraintForceMix(jSpinnerConstraintForceMix);		
		jPanelEditor.add(jSpinnerConstraintForceMix);		
	}
	
	public static void addErrorReductionParameterEditor() {
		jSpinnerErrorReductionParameter = new javax.swing.JSpinner();
		jSpinnerErrorReductionParameter.setPreferredSize(new Dimension(60,20));
		jSpinnerErrorReductionParameter.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		SimulationTabController.setValueJSpinnerErrorReductionParameter(jSpinnerErrorReductionParameter);		
		jPanelEditor.add(jSpinnerErrorReductionParameter);	
		
	}
	
	public static void addResolutionFactorEditor() {
		jSpinnerResolutionFactor = new javax.swing.JSpinner();
		jSpinnerResolutionFactor.setPreferredSize(new Dimension(60,20));
		jSpinnerResolutionFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
		SimulationTabController.setValueJSpinnerResolutionFactor(jSpinnerResolutionFactor);		
		jPanelEditor.add(jSpinnerResolutionFactor);			
	}
	
	public static void addUseMouseEventQueueEditor() {
		jCheckBoxUseMouseEventQueue = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxUseMouseEventQueue(jCheckBoxUseMouseEventQueue);		
		jPanelEditor.add(jCheckBoxUseMouseEventQueue);		
	}
	
	public static void addSynchronizeWithControllersEditor() {
		jCheckBoxSynchronizeWithControllers = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxSynchronizeWithControllers(jCheckBoxSynchronizeWithControllers);		
		jPanelEditor.add(jCheckBoxSynchronizeWithControllers);	
		
	}
	
	public static void addPhysicsSimulationControllerStepFactor() {
		jPhysicsSimulationControllerStepFactor = new javax.swing.JSpinner();
		jPhysicsSimulationControllerStepFactor.setPreferredSize(new Dimension(60,20));
		jPhysicsSimulationControllerStepFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
		SimulationTabController.setValuejPhysicsSimulationControllerStepFactor(jPhysicsSimulationControllerStepFactor);		
		jPanelEditor.add(jPhysicsSimulationControllerStepFactor);	
		
	}
	

	
	

	public static void addMorphologyEditor() {
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);
        //System.out.println("BOOO"+RobotSpecification.getMorphologyLocation());
		
		FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,RobotSpecification.getMorphologyLocation());
		fcOpenFrame.setSelectedFile(new File("some.xml"));
		
		jPanelEditor.add(MainFrames.initOpenButton(fcOpenFrame));
	}
	
	private static String robotMorphologyLocation;
	
	


	public static void setRobotMorphologyLocation(String robotMorphologyLocation) {
		SimulationTab.robotMorphologyLocation = robotMorphologyLocation;
	}


    private static HintPanel  hintPanel;
	
	public static HintPanel getHintPanel() {
		return hintPanel;
	}

	private static javax.swing.JTree jTree1;
	private static javax.swing.JScrollPane jScrollPaneTree;
	private static javax.swing.JButton jButtonOpenMorphology ;

	private static javax.swing.JSpinner  jSpinnerPlaneSize ;
	private static javax.swing.JComboBox jComboBoxPlaneTexture;
	private static javax.swing.JComboBox jComboBoxCameraPosition;
	
	private static javax.swing.JCheckBox jCheckBoxTheWorldIsFlat,jCheckBoxHasBackgroundScenery, jCheckBoxHasHeavyObstacles,
	jCheckBoxIsFrameGrabbingActive,jCheckBoxRealisticCollision,jCheckBoxUseMouseEventQueue,
	jCheckBoxSynchronizeWithControllers;
	
	private static javax.swing.JLabel jLabelRobotType;

	private static javax.swing.JPanel jPanelEditor;
	private static javax.swing.JSpinner jSpinnerDampingLinearVelocity, jSpinnerDampingAngularVelocity,
	jSpinnerPhysicsSimulationStepSize, jSpinnerGravity,jSpinnerConstraintForceMix,
	jSpinnerErrorReductionParameter, jSpinnerResolutionFactor, jPhysicsSimulationControllerStepFactor;
	

	
}
