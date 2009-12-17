package ussr.aGui.tabs.simulation;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;

import javax.swing.border.TitledBorder;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.tabs.SimulationTabComponentsText;
import ussr.aGui.enumerations.tabs.SimulationTabTreeNodes;
import ussr.aGui.enumerations.tabs.TabsIcons;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.helpers.hintPanel.HintPanel;

import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.builder.helpers.StringProcessingHelper;
import ussr.builder.simulationLoader.SimulationSpecification;



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
		jScrollPaneTreeSimulation = new javax.swing.JScrollPane();
		jPanelEditor = new javax.swing.JPanel(new GridBagLayout());
		jTreeSimulation = new javax.swing.JTree();
		
		
		DefaultTreeModel treeModel = new DefaultTreeModel(initializeSimulationRootNode());
		jTreeSimulation.setModel(treeModel);
		//jTreeSimulation.setShowsRootHandles(true);
		//jTreeSimulation.setEditable(true);
		
		ImageIcon closedIcon = TabsIcons.EXPANSION_CLOSED_SMALL.getImageIcon();
		ImageIcon openIcon = TabsIcons.EXPANSION_OPENED_SMALL.getImageIcon();
		ImageIcon leafIcon = TabsIcons.FINAL_LEAF_SMALL.getImageIcon();
		if (openIcon != null) {
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();		    
			renderer.setClosedIcon(closedIcon);
			renderer.setOpenIcon(openIcon);
			renderer.setLeafIcon(leafIcon);
			jTreeSimulation.setCellRenderer(renderer);
		}
		jTreeSimulation.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		jTreeSimulation.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				jTreeSimulation.getLastSelectedPathComponent();
				if (node == null)
					/*Nothing is selected.*/	
					return;
				Object nodeInfo = node.getUserObject();
				if (node.isLeaf()||node.isNodeRelated(firstNodeHierarchySimulation)) {
					System.out.println(node.getPath()[node.getPath().length-1].toString());
					SimulationTabController.jTreeItemSelectedActionPerformed(node.getPath()[node.getPath().length-1].toString());
				} 
			}
		});

		jScrollPaneTreeSimulation.setViewportView(jTreeSimulation);
		jScrollPaneTreeSimulation.setVisible(false);
		jScrollPaneTreeSimulation.setPreferredSize(new Dimension(300,300));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jScrollPaneTreeSimulation,gridBagConstraints);

		TitledBorder displayTitle;
		displayTitle = BorderFactory.createTitledBorder(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Edit_value));
		displayTitle.setTitleJustification(TitledBorder.CENTER);
		jPanelEditor.setBorder(displayTitle);
		jPanelEditor.setVisible(false);
		jPanelEditor.setPreferredSize(new Dimension(300,300));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;		

		super.jComponent.add(jPanelEditor,gridBagConstraints);

		hintPanel = new HintPanel(600, HINT_PANEL_HEIGHT);
		hintPanel.setBorderTitle("Display for hints");

		hintPanel.setText("Would be nice to add a short description of each element in the tree :).");
		hintPanel.setVisible(false);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth=2;

		super.jComponent.add(hintPanel,gridBagConstraints);
	}

	/**
	 * Initializes simulation root tree node including all sub nodes.
	 * @return simulation root tree node including all sub nodes.
	 */
	private DefaultMutableTreeNode initializeSimulationRootNode(){
		
		for(int index =0;index<SimulationTabTreeNodes.values().length;index++){
			SimulationTabTreeNodes currentNode = SimulationTabTreeNodes.values()[index];
			switch(currentNode.getPlaceInHierarchy()){
			case FIRST:
				firstNodeHierarchySimulation = new DefaultMutableTreeNode(StringProcessingHelper.replaceUnderscoreWithSpace(currentNode));
				//set default mutable tree node for future easier reference.
				currentNode.setDefaultMutableTreeNode(firstNodeHierarchySimulation);
			break;
			case SECOND:
				secondNodeHierarchy = new DefaultMutableTreeNode(StringProcessingHelper.replaceUnderscoreWithSpace(currentNode));
				currentNode.setDefaultMutableTreeNode(secondNodeHierarchy);
				firstNodeHierarchySimulation.add(secondNodeHierarchy);
				break;
			case THIRD:
				thirdNodeHierarchy = new DefaultMutableTreeNode(StringProcessingHelper.replaceUnderscoreWithSpace(currentNode));
				currentNode.setDefaultMutableTreeNode(thirdNodeHierarchy);
				secondNodeHierarchy.add(thirdNodeHierarchy);
				break;
			case NOT_USED://do nothing
				break;
			default: throw new Error("Place in hierarchy "+ currentNode.getPlaceInHierarchy()+ " is not supported yet.");
			}
		}
		return firstNodeHierarchySimulation;
	}

	/**
	 * 
	 */
	private static boolean firstTime = true;
	
	private static int robotNumber;
	
	/**
	 * @param simulationSpecification
	 */
	public static void addRobotNode(SimulationSpecification simulationSpecification){
		int nrRobotsInSimulation = simulationSpecification.getRobotsInSimulation().size();
		
		DefaultMutableTreeNode robotsNode = SimulationTabTreeNodes.Robots.getDefaultMutableTreeNode();
		if (firstTime){
			firstTime=false;
			for (int robotNr=1;robotNr<nrRobotsInSimulation+1;robotNr++){
				DefaultMutableTreeNode thirdNodeHierarchyRobot =  new DefaultMutableTreeNode(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Robot_Nr)+"."+robotNr);
				robotsNode.add(thirdNodeHierarchyRobot);
				//secondNodeHierarchyRobots.add(thirdNodeHierarchyRobot);
				robotNumber =robotNr;
			}
		}else{
			robotNumber++;
			DefaultMutableTreeNode thirdNodeHierarchyRobot =  new DefaultMutableTreeNode(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabTreeNodes.Robot_Nr)+"."+robotNumber);
        	DefaultTreeModel model = (DefaultTreeModel)jTreeSimulation.getModel();
        	model.insertNodeInto(thirdNodeHierarchyRobot, robotsNode, robotsNode.getChildCount());	
		}
		
		jTreeSimulation.revalidate();
		jTreeSimulation.repaint();
		jScrollPaneTreeSimulation.setViewportView(jTreeSimulation);
	} 


	public static void addRobotsEditor(){
		jPanelEditor.add(createNewLabel("Load new robot "));
		jPanelEditor.add(initOpenButton());
	}



	public static void addRobotEditor(){
		//jPanelEditor.add(createNewLabel("Robot"));
		jPanelMoveRobot = new javax.swing.JPanel(new GridBagLayout());
		jButtonYpositive = new javax.swing.JButton();
		jButtonYnegative = new javax.swing.JButton();
		jButtonXpositive = new javax.swing.JButton();
		jButtonXnegative = new javax.swing.JButton();
		jButtonZpositive = new javax.swing.JButton();
		jButtonZnegative = new javax.swing.JButton();
		jSpinnerCoordinateValue = new javax.swing.JSpinner();

		GridBagConstraints gridBagConstraintsMoveRobot = new GridBagConstraints();
		TitledBorder displayTitle1;
		displayTitle1 = BorderFactory.createTitledBorder("New position");
		displayTitle1.setTitleJustification(TitledBorder.CENTER);
		jPanelMoveRobot.setBorder(displayTitle1);
		
		jButtonYpositive.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_up));
		jButtonYpositive.setIcon(TabsIcons.Y_POSITIVE_BIG.getImageIcon());
		jButtonYpositive.setRolloverIcon(TabsIcons.Y_POSITIVE_ROLLOVER_BIG.getImageIcon());					
		jButtonYpositive.setFocusable(false);		
		jButtonYpositive.setPreferredSize(new Dimension(45,30));
		jButtonYpositive.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonYpositive);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 1;
		gridBagConstraintsMoveRobot.gridy = 0;	

		jPanelMoveRobot.add(jButtonYpositive,gridBagConstraintsMoveRobot);


		jButtonXnegative.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_left));
		jButtonXnegative.setIcon(TabsIcons.X_NEGATIVE_BIG.getImageIcon());	
		jButtonXnegative.setRolloverIcon(TabsIcons.X_NEGATIVE_ROLLOVER_BIG.getImageIcon());			
		jButtonXnegative.setFocusable(false);		
		jButtonXnegative.setPreferredSize(new Dimension(45,30));
		jButtonXnegative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonXnegative);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 0;
		gridBagConstraintsMoveRobot.gridy = 1;	
		jPanelMoveRobot.add(jButtonXnegative,gridBagConstraintsMoveRobot);

		jButtonZpositive.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_closer));
		jButtonZpositive.setIcon(TabsIcons.Z_POSITIVE_BIG.getImageIcon());	
		jButtonZpositive.setRolloverIcon(TabsIcons.Z_POSITIVE_ROLLOVER_BIG.getImageIcon());		
		jButtonZpositive.setFocusable(false);		
		jButtonZpositive.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonZpositive.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonZpositive);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 0;
		gridBagConstraintsMoveRobot.gridy = 2;	
		jPanelMoveRobot.add(jButtonZpositive,gridBagConstraintsMoveRobot);


		jSpinnerCoordinateValue = new javax.swing.JSpinner();
		jSpinnerCoordinateValue.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.1f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(0.01f)));
		jSpinnerCoordinateValue.setPreferredSize(new Dimension(45,30));
		jSpinnerCoordinateValue.addChangeListener(new javax.swing.event.ChangeListener() {
	            public void stateChanged(javax.swing.event.ChangeEvent evt) {
	              SimulationTabController.setjSpinnerCoordinateValue(Float.parseFloat(jSpinnerCoordinateValue.getValue().toString()));
	            }
	        });
		
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 1;
		gridBagConstraintsMoveRobot.gridy = 1;

		jPanelMoveRobot.add(jSpinnerCoordinateValue,gridBagConstraintsMoveRobot);

		jButtonZnegative.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_away));
		jButtonZnegative.setIcon(TabsIcons.Z_NEGATIVE_BIG.getImageIcon());				
		jButtonZnegative.setRolloverIcon(TabsIcons.Z_NEGATIVE_ROLLOVER_BIG.getImageIcon());	
		jButtonZnegative.setFocusable(false);		
		jButtonZnegative.setPreferredSize(new Dimension(45,30));
		jButtonZnegative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonZnegative);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 2;
		gridBagConstraintsMoveRobot.gridy = 0;	
		jPanelMoveRobot.add(jButtonZnegative,gridBagConstraintsMoveRobot);


		jButtonXpositive.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_right));
		jButtonXpositive.setIcon(TabsIcons.X_POSITIVE_BIG.getImageIcon());	
		jButtonXpositive.setRolloverIcon(TabsIcons.X_POSITIVE_ROLLOVER_BIG.getImageIcon());		
		jButtonXpositive.setFocusable(false);		
		jButtonXpositive.setPreferredSize(new Dimension(45,30));
		jButtonXpositive.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonXpositive);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 2;
		gridBagConstraintsMoveRobot.gridy = 1;	
		jPanelMoveRobot.add(jButtonXpositive,gridBagConstraintsMoveRobot);



		jButtonYnegative.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(SimulationTabComponentsText.Move_down));
		jButtonYnegative.setIcon(TabsIcons.Y_NEGATIVE_BIG.getImageIcon());	
		jButtonYnegative.setRolloverIcon(TabsIcons.Y_NEGATIVE_ROLLOVER_BIG.getImageIcon());		
		jButtonYnegative.setFocusable(false);		
		jButtonYnegative.setPreferredSize(new Dimension(45,30));
		jButtonYnegative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SimulationTabController.jButtonsCoordinateArrowsActionPerformed(jButtonYnegative);
			}
		});
		gridBagConstraintsMoveRobot.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMoveRobot.gridx = 1;
		gridBagConstraintsMoveRobot.gridy = 2;	
		jPanelMoveRobot.add(jButtonYnegative,gridBagConstraintsMoveRobot);


		jPanelEditor.add(jPanelMoveRobot);
	}

	public static javax.swing.JPanel getJPanelEditor() {

		return jPanelEditor;
	}

	public static void setTabVisible(boolean visible) {
		jScrollPaneTreeSimulation.setVisible(visible);
		jPanelEditor.setVisible(visible);
		hintPanel.setVisible(visible);
	}

	public static void addMorphologyEditor() {
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);
		//System.out.println("BOOO"+RobotSpecification.getMorphologyLocation());

		//FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,TemporaryRobotSpecification.getMorphologyLocation());
		//fcOpenFrame.setSelectedFile(new File("some.xml"));

		//jPanelEditor.add(MainFrames.initOpenButton(fcOpenFrame));
	}

	private static String robotMorphologyLocation;

	/**
	 * Creates new label with specified text.
	 * @param labelText, the text of the label
	 * @return new label with specified text.
	 */
	public static javax.swing.JLabel createNewLabel(String labelText){
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


	public static javax.swing.JSpinner getJSpinnerCoordinateValue() {
		return jSpinnerCoordinateValue;
	}

	private static javax.swing.JTree jTreeSimulation;
	private static javax.swing.JScrollPane jScrollPaneTreeSimulation;
	private static javax.swing.JButton jButtonOpenMorphology,jButtonYpositive,jButtonYnegative,
	jButtonXpositive,jButtonXnegative,
	jButtonZpositive,jButtonZnegative;

	private static javax.swing.JSpinner  jSpinnerPlaneSize ;
	private static javax.swing.JComboBox jComboBoxPlaneTexture;
	private static javax.swing.JComboBox jComboBoxCameraPosition;

	private static javax.swing.JCheckBox jCheckBoxTheWorldIsFlat,jCheckBoxHasBackgroundScenery, jCheckBoxHasHeavyObstacles,
	jCheckBoxIsFrameGrabbingActive,jCheckBoxRealisticCollision,jCheckBoxUseMouseEventQueue,
	jCheckBoxSynchronizeWithControllers;

	private static javax.swing.JLabel jLabelRobotType;

	private static javax.swing.JLabel newLabel;

	private static javax.swing.JPanel jPanelEditor;
	private static javax.swing.JPanel jPanelMoveRobot;
	private static javax.swing.JSpinner jSpinnerDampingLinearVelocity, jSpinnerDampingAngularVelocity,
	jSpinnerPhysicsSimulationStepSize, jSpinnerGravity,jSpinnerConstraintForceMix,
	jSpinnerErrorReductionParameter, jSpinnerResolutionFactor, jPhysicsSimulationControllerStepFactor,
	jSpinnerCoordinateValue;

	private static  DefaultMutableTreeNode firstNodeHierarchySimulation,secondNodeHierarchyRobots,secondNodeHierarchy,thirdNodeHierarchy;



}
