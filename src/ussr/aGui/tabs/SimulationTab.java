package ussr.aGui.tabs;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import ussr.aGui.enumerations.TextureDescriptions;
import ussr.aGui.enumerations.TreeElements;
import ussr.aGui.tabs.additionalResources.CheckBoxEditor;
import ussr.aGui.tabs.additionalResources.SpinnerEditor;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;


/**
 * Defines visual appearance of the tab called Simulation.
 * @author Konstantinas
 */
public class SimulationTab extends Tabs {


	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	public  GridBagConstraints gridBagConstraints = new GridBagConstraints();

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
		//jScrollPane3 = new javax.swing.JScrollPane();
		//jScrollPane4 = new javax.swing.JScrollPane();

		jScrollPaneTree = new javax.swing.JScrollPane();

		jPanelEditor = new javax.swing.JPanel(new GridBagLayout());

		DefaultMutableTreeNode firstNodeHierarchy = new DefaultMutableTreeNode(TreeElements.Simulation.toString());

		
		DefaultMutableTreeNode secondNodeHierarchyPhysicsSimulationStepSize =  new DefaultMutableTreeNode(TreeElements.Physics_Simulation_Step_Size.toString().replace("_", " "));
		firstNodeHierarchy.add(secondNodeHierarchyPhysicsSimulationStepSize);
		
		DefaultMutableTreeNode secondNodeHierarchyRobots =  new DefaultMutableTreeNode(TreeElements.Robots.toString());
		firstNodeHierarchy.add(secondNodeHierarchyRobots);
		
		DefaultMutableTreeNode thirdNodeHierarchyRobot =  new DefaultMutableTreeNode(TreeElements.Robot.toString());
		secondNodeHierarchyRobots.add(thirdNodeHierarchyRobot);
		
		DefaultMutableTreeNode fourthNodeHierarchyRobotType = new DefaultMutableTreeNode(TreeElements.Type.toString());
		thirdNodeHierarchyRobot.add(fourthNodeHierarchyRobotType);		
		DefaultMutableTreeNode fourthNodeHierarchyMorphologyLocation = new DefaultMutableTreeNode(TreeElements.Morphology_Location.toString().replace("_", " "));
		thirdNodeHierarchyRobot.add(fourthNodeHierarchyMorphologyLocation);		
		DefaultMutableTreeNode fourthNodeHierarchyControllerLocation = new DefaultMutableTreeNode(TreeElements.Controller_Location.toString().replace("_", " "));
		thirdNodeHierarchyRobot.add(fourthNodeHierarchyControllerLocation);


		DefaultMutableTreeNode secondNodeHierarchyWorldDescription = new DefaultMutableTreeNode(TreeElements.World_Description.toString().replace("_", " "));
		firstNodeHierarchy.add(secondNodeHierarchyWorldDescription);

		DefaultMutableTreeNode thirdNodeHierarchyPlaneSize =  new DefaultMutableTreeNode(TreeElements.Plane_Size.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyPlaneSize);
		DefaultMutableTreeNode thirdNodeHierarchyPlaneTexture =  new DefaultMutableTreeNode(TreeElements.Plane_Texture.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyPlaneTexture);
		DefaultMutableTreeNode thirdNodeHierarchyCameraPosition = new DefaultMutableTreeNode(TreeElements.Camera_Position.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyCameraPosition);
		DefaultMutableTreeNode thirdNodeHierarchyTheWorldIsFlat = new DefaultMutableTreeNode(TreeElements.The_World_Is_Flat.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyTheWorldIsFlat);
		DefaultMutableTreeNode thirdNodeHierarchyHasBackgroundScenery = new DefaultMutableTreeNode(TreeElements.Has_Background_Scenery.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyHasBackgroundScenery);
		DefaultMutableTreeNode thirdNodeHierarchyHasHeavyObstacles = new DefaultMutableTreeNode(TreeElements.Has_Heavy_Obstacles.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyHasHeavyObstacles);
		DefaultMutableTreeNode thirdNodeHierarchyIsFrameGrabbingActive = new DefaultMutableTreeNode(TreeElements.Is_Frame_Grabbing_Active.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyIsFrameGrabbingActive);

		DefaultMutableTreeNode secondNodeHierarchyPhysicsParameters = new DefaultMutableTreeNode(TreeElements.Physics_Parameters.toString().replace("_", " "));
		firstNodeHierarchy.add(secondNodeHierarchyPhysicsParameters);
		
		DefaultMutableTreeNode thirdNodeHierarchyDamping= new DefaultMutableTreeNode(TreeElements.Damping.toString());
		secondNodeHierarchyPhysicsParameters.add(thirdNodeHierarchyDamping);

		DefaultMutableTreeNode fourthNodeHierarchyDampingLinearVelocity = new DefaultMutableTreeNode(TreeElements.Linear_Velocity.toString().replace("_", " "));
		thirdNodeHierarchyDamping.add(fourthNodeHierarchyDampingLinearVelocity);
		
		DefaultMutableTreeNode fourthNodeHierarchyAngularLinearVelocity = new DefaultMutableTreeNode(TreeElements.Angular_Velocity.toString().replace("_", " "));
		thirdNodeHierarchyDamping.add(fourthNodeHierarchyAngularLinearVelocity);



		jTree1 = new javax.swing.JTree(firstNodeHierarchy);

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

					/*	BookInfo book = (BookInfo)nodeInfo;
					displayURL(book.bookURL);*/
				} /*else {
					displayURL(helpURL); 
				}*/

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


	}

	public static javax.swing.JPanel getJPanelEditor() {
	
		return jPanelEditor;
	}

	public static void setTabVisible(boolean visible) {
		jScrollPaneTree.setVisible(visible);
		jPanelEditor.setVisible(visible);
	}

	
	
	public static void addPlaneSizeEditor(){

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		jSpinnerPlaneSize = new javax.swing.JSpinner();
		jSpinnerPlaneSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 1));
		SimulationTabController.setJSpinnerPlaneSizeValue(jSpinnerPlaneSize);

		jPanelEditor.add(jSpinnerPlaneSize,gridBagConstraints);

		jToolBarPlaneControl = new javax.swing.JToolBar();
		jToolBarPlaneControl.setRollover(true);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		
		jButtonPlaneTiltRight = new javax.swing.JButton();
		jButtonPlaneTiltRight.setPreferredSize(new Dimension(30,30));
		jButtonPlaneTiltRight.setIcon(new ImageIcon(DIRECTORY_ICONS+PLANE_TITL_RIGHT));
		jButtonPlaneTiltRight.setFocusable(false);
		jButtonPlaneTiltRight.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {            	
				SimulationTabController.jButtonPlaneTiltRightActionPerformed();            	
			}
		});        

		jToolBarPlaneControl.add(jButtonPlaneTiltRight);
		
		jPanelEditor.add(jToolBarPlaneControl,gridBagConstraints);

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
		JCheckBoxTheWorldIsFlat =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxTheWorldIsFlat(JCheckBoxTheWorldIsFlat);
	
		jPanelEditor.add(JCheckBoxTheWorldIsFlat);
	}

	
	public static void addHasBackgroundSceneryEditor(){
		JCheckBoxHasBackgroundScenery =  new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxHasBackgroundScenery(JCheckBoxHasBackgroundScenery);
		jPanelEditor.add(JCheckBoxHasBackgroundScenery);
	}
	
	public static void addHasHeavyObstaclesEditor(){
		jCheckBoxHasHeavyObstacles = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedjCheckBoxHasHeavyObstacles(jCheckBoxHasHeavyObstacles);
		
		jPanelEditor.add(jCheckBoxHasHeavyObstacles);
		
	}
	
	public static void addIsFrameGrabbingActiveEditor(){
		JCheckBoxIsFrameGrabbingActive = new javax.swing.JCheckBox ();
		SimulationTabController.setSelectedJCheckBoxIsFrameGrabbingActive(JCheckBoxIsFrameGrabbingActive);
		
		jPanelEditor.add(JCheckBoxIsFrameGrabbingActive);
		
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



	private static javax.swing.JTree jTree1;
	private static javax.swing.JScrollPane jScrollPaneTree;

	private static javax.swing.JButton jButtonPlaneTiltRight;
	private static javax.swing.JSpinner  jSpinnerPlaneSize ;
	private static javax.swing.JComboBox jComboBoxPlaneTexture;
	private static javax.swing.JComboBox jComboBoxCameraPosition;
	
	private static javax.swing.JCheckBox JCheckBoxTheWorldIsFlat,JCheckBoxHasBackgroundScenery, jCheckBoxHasHeavyObstacles,
	JCheckBoxIsFrameGrabbingActive;
	

	private static javax.swing.JToolBar jToolBarPlaneControl;
	
	private static javax.swing.JLabel jLabelRobotType;

	private static javax.swing.JPanel jPanelEditor;
	private static javax.swing.JSpinner jSpinnerDampingLinearVelocity, jSpinnerDampingAngularVelocity,
	jSpinnerPhysicsSimulationStepSize;

	

	



}
