package ussr.aGui.tabs;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import ussr.aGui.MainFramesInter;
import ussr.aGui.enumerations.SimulationTabTreeNodes;
import ussr.aGui.enumerations.TabsIcons;
import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.helpers.RobotSpecification;
import ussr.aGui.helpers.hintPanel.HintPanel;
import ussr.aGui.helpers.hintPanel.HintPanelTypes;

import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.builder.helpers.StringProcessingHelper;

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
		//super.jComponent.setPreferredSize(new Dimension(MainFrames.getMainFrameViableWidth(),300));
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
		
		//Third in hierarchy
		DefaultMutableTreeNode thirdNodeHierarchyRealisticCollision = new DefaultMutableTreeNode(SimulationTabTreeNodes.Realistic_collision.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyRealisticCollision);
		DefaultMutableTreeNode thirdNodeHierarchyGravity = new DefaultMutableTreeNode(SimulationTabTreeNodes.Gravity.toString());
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyGravity);
		DefaultMutableTreeNode thirdNodeHierarchyConstraintForceMix = new DefaultMutableTreeNode(SimulationTabTreeNodes.Constraint_force_mixing.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyConstraintForceMix);
		DefaultMutableTreeNode thirdNodeHierarchyErrorReductionParameter = new DefaultMutableTreeNode(SimulationTabTreeNodes.Error_reduction_parameter.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyErrorReductionParameter);
		DefaultMutableTreeNode thirdNodeHierarchyUseMouseEventQueue = new DefaultMutableTreeNode(SimulationTabTreeNodes.Use_module_event_queue.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyUseMouseEventQueue);
		DefaultMutableTreeNode thirdNodeHierarchySynchronizeWithControllers = new DefaultMutableTreeNode(SimulationTabTreeNodes.Synchronize_with_controllers.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchySynchronizeWithControllers);
		DefaultMutableTreeNode thirdNodeHierarchyPhysicsSimulationControllerStepFactor = new DefaultMutableTreeNode(SimulationTabTreeNodes.Physics_simulation_controller_step_factor.toString().replace("_", " "));
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyPhysicsSimulationControllerStepFactor );


		jTree1 = new javax.swing.JTree(firstNodeHierarchySimulation);

		
		ImageIcon closedIcon = TabsIcons.EXPANSION_CLOSED_SMALL.getImageIcon();
		ImageIcon openIcon = TabsIcons.EXPANSION_OPENED_SMALL.getImageIcon();
		ImageIcon leafIcon = TabsIcons.FINAL_LEAF_SMALL.getImageIcon();
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
		
		hintPanel = new HintPanel(600, HINT_PANEL_HEIGHT);
		//hintPanel.setType(HintPanelTypes.INFORMATION);
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
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Plane_size)));
		jSpinnerPlaneSize = new javax.swing.JSpinner();
		jSpinnerPlaneSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 1));
		SimulationTabController.setJSpinnerPlaneSizeValue(jSpinnerPlaneSize);
		jPanelEditor.add(jSpinnerPlaneSize);
	}
	
	
	
	public static void addPlaneTextureEditor(){
		GridBagConstraints gridBagConstraintsTexture = new GridBagConstraints();
		
		gridBagConstraintsTexture.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsTexture.gridx = 0;
		gridBagConstraintsTexture.gridy = 0;
		
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Plane_texture)),gridBagConstraintsTexture);
		
		
		jComboBoxPlaneTexture = new javax.swing.JComboBox(); 
		jComboBoxPlaneTexture.setModel(new DefaultComboBoxModel(TextureDescriptions.values()));
		
		gridBagConstraintsTexture.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsTexture.gridx = 1;
		gridBagConstraintsTexture.gridy = 0;
		
		jPanelEditor.add(jComboBoxPlaneTexture,gridBagConstraintsTexture);
		
		javax.swing.JPanel previewPanel = new javax.swing.JPanel(new GridBagLayout());
		previewPanel.setPreferredSize(new Dimension(100,100));
		previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Preview", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		gridBagConstraintsTexture.fill = GridBagConstraints.CENTER;
		gridBagConstraintsTexture.gridx = 0;
		gridBagConstraintsTexture.gridy = 1;
		gridBagConstraintsTexture.gridwidth =2;
		gridBagConstraintsTexture.insets = new Insets(10,0,0,0);
		
		javax.swing.JLabel iconLabel = new javax.swing.JLabel();
		
		SimulationTabController.setSelectedJComboBoxPlaneTexture(jComboBoxPlaneTexture,iconLabel);
		
		previewPanel.add(iconLabel);
		
		
		
		
		jPanelEditor.add(previewPanel,gridBagConstraintsTexture);
	}
	
	public static void addCameraPositionEditor(){

		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Camera_position)));
		jComboBoxCameraPosition = new javax.swing.JComboBox(); 
		jComboBoxCameraPosition.setModel(new DefaultComboBoxModel(CameraPosition.values()));
		SimulationTabController.setSelectedJComboBoxCameraPosition(jComboBoxCameraPosition);
		jPanelEditor.add(jComboBoxCameraPosition);

	}
	
	public static void addTheWorldIsFlatEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.The_world_is_flat)));
		jCheckBoxTheWorldIsFlat =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxTheWorldIsFlat(jCheckBoxTheWorldIsFlat);
	
		jPanelEditor.add(jCheckBoxTheWorldIsFlat);
	}

	
	public static void addHasBackgroundSceneryEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Has_background_scenery)));
		jCheckBoxHasBackgroundScenery =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxHasBackgroundScenery(jCheckBoxHasBackgroundScenery);
		jPanelEditor.add(jCheckBoxHasBackgroundScenery);
	}
	
	public static void addHasHeavyObstaclesEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Has_heavy_obstacles)));
		jCheckBoxHasHeavyObstacles = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxHasHeavyObstacles(jCheckBoxHasHeavyObstacles);
		
		jPanelEditor.add(jCheckBoxHasHeavyObstacles);
		
	}
	
	public static void addIsFrameGrabbingActiveEditor(){
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Is_frame_grabbing_active)));
		jCheckBoxIsFrameGrabbingActive = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxIsFrameGrabbingActive(jCheckBoxIsFrameGrabbingActive);
		
		jPanelEditor.add(jCheckBoxIsFrameGrabbingActive);
		
	}
	
	public static void addRobotTypeEditor(){
		jLabelRobotType = new javax.swing.JLabel(); 
		SimulationTabController.setJLabelRobotType(jLabelRobotType);
		
		jPanelEditor.add(jLabelRobotType);
		
	}
	
	public static void addDampingEditor(){
		
		GridBagConstraints gridBagConstraintsDamping = new GridBagConstraints();
		
			
		gridBagConstraintsDamping.fill = GridBagConstraints.CENTER;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 0;
		gridBagConstraintsDamping.gridwidth = 2;
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Damping)),gridBagConstraintsDamping);
	
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 1;
		gridBagConstraintsDamping.gridwidth = 1;
		gridBagConstraintsDamping.insets = new Insets(20,0,0,0);
		
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Linear_velocity)),gridBagConstraintsDamping);
		
		jSpinnerDampingLinearVelocity = new javax.swing.JSpinner();
		jSpinnerDampingLinearVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingLinearVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerDampingLinearVelocity(jSpinnerDampingLinearVelocity);		
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 1;
		gridBagConstraintsDamping.gridy = 1;
		jPanelEditor.add(jSpinnerDampingLinearVelocity,gridBagConstraintsDamping);
		
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 0;
		gridBagConstraintsDamping.gridy = 2;
		gridBagConstraintsDamping.insets = new Insets(10,0,0,0);
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Angular_velocity)),gridBagConstraintsDamping);
		
		jSpinnerDampingAngularVelocity = new javax.swing.JSpinner();
		jSpinnerDampingAngularVelocity.setPreferredSize(new Dimension(60,20));
		jSpinnerDampingAngularVelocity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerDampingAngularVelocity(jSpinnerDampingAngularVelocity);
		gridBagConstraintsDamping.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsDamping.gridx = 1;
		gridBagConstraintsDamping.gridy = 2;
		gridBagConstraintsDamping.insets = new Insets(10,0,0,0);
		jPanelEditor.add(jSpinnerDampingAngularVelocity,gridBagConstraintsDamping);	
		
		
	}

	
	
	public static void addPhysicsSimulationStepSizeEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Physics_simulation_step_size)));
		jSpinnerPhysicsSimulationStepSize = new javax.swing.JSpinner();
		jSpinnerPhysicsSimulationStepSize.setPreferredSize(new Dimension(60,20));
		jSpinnerPhysicsSimulationStepSize.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerPhysicsSimulationStepSize(jSpinnerPhysicsSimulationStepSize);		
		jPanelEditor.add(jSpinnerPhysicsSimulationStepSize);	
		
	}
	
	public static void addRealisticCollisionEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Realistic_collision)));
		jCheckBoxRealisticCollision = new javax.swing.JCheckBox ();
        SimulationTabController.setSelectedJCheckBoxRealisticCollision(jCheckBoxRealisticCollision);		
		jPanelEditor.add(jCheckBoxRealisticCollision);
		
	}
	
	public static void addGravityEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Gravity)));
		jSpinnerGravity = new javax.swing.JSpinner();
		jSpinnerGravity.setPreferredSize(new Dimension(60,20));
		jSpinnerGravity.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(-100.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerGravity(jSpinnerGravity);		
		jPanelEditor.add(jSpinnerGravity);	
		
	}

	public static void addConstraintForceMixEditor() {
		
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Constraint_force_mixing)));
		jSpinnerConstraintForceMix = new javax.swing.JSpinner();
		jSpinnerConstraintForceMix.setPreferredSize(new Dimension(60,20));
		jSpinnerConstraintForceMix.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
		SimulationTabController.setValuejSpinnerConstraintForceMix(jSpinnerConstraintForceMix);		
		jPanelEditor.add(jSpinnerConstraintForceMix);		
	}
	
	public static void addErrorReductionParameterEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Error_reduction_parameter)));
		jSpinnerErrorReductionParameter = new javax.swing.JSpinner();
		jSpinnerErrorReductionParameter.setPreferredSize(new Dimension(60,20));
		jSpinnerErrorReductionParameter.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
		SimulationTabController.setValueJSpinnerErrorReductionParameter(jSpinnerErrorReductionParameter);		
		jPanelEditor.add(jSpinnerErrorReductionParameter);	
		
	}
	
	public static void addResolutionFactorEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Resolution_Factor)));
		jSpinnerResolutionFactor = new javax.swing.JSpinner();
		jSpinnerResolutionFactor.setPreferredSize(new Dimension(60,20));
		jSpinnerResolutionFactor.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
		SimulationTabController.setValueJSpinnerResolutionFactor(jSpinnerResolutionFactor);		
		jPanelEditor.add(jSpinnerResolutionFactor);			
	}
	
	public static void addUseMouseEventQueueEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Use_module_event_queue)));
		jCheckBoxUseMouseEventQueue = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxUseMouseEventQueue(jCheckBoxUseMouseEventQueue);		
		jPanelEditor.add(jCheckBoxUseMouseEventQueue);		
	}
	
	public static void addSynchronizeWithControllersEditor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Synchronize_with_controllers)));
		jCheckBoxSynchronizeWithControllers = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxSynchronizeWithControllers(jCheckBoxSynchronizeWithControllers);		
		jPanelEditor.add(jCheckBoxSynchronizeWithControllers);	
		
	}
	
	public static void addPhysicsSimulationControllerStepFactor() {
		jPanelEditor.add(createNewLabel(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Physics_simulation_controller_step_factor)));
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
	
	private static javax.swing.JLabel createNewLabel(String labelText){
		newLabel =  new javax.swing.JLabel();
		newLabel.setText(labelText+" ");
		return newLabel;
	}


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
	
	private static javax.swing.JLabel newLabel,jLabelLinearVelocity,jLabelAngularVelocity,jLabelDamping,jLabelRealisticCoallision;

	private static javax.swing.JPanel jPanelEditor;
	private static javax.swing.JSpinner jSpinnerDampingLinearVelocity, jSpinnerDampingAngularVelocity,
	jSpinnerPhysicsSimulationStepSize, jSpinnerGravity,jSpinnerConstraintForceMix,
	jSpinnerErrorReductionParameter, jSpinnerResolutionFactor, jPhysicsSimulationControllerStepFactor;
	

	
}
