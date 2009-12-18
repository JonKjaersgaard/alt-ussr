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
import ussr.aGui.enumerations.tabs.SimulationTabTreeNodes;
import ussr.aGui.enumerations.tabs.TabsComponentsText;
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
		displayTitle = BorderFactory.createTitledBorder(TabsComponentsText.EDIT_VALUE.getUserFriendlyName());
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
				firstNodeHierarchySimulation = new DefaultMutableTreeNode(currentNode.getUserFriendlyName());
				//set default mutable tree node for future easier reference.
				currentNode.setDefaultMutableTreeNode(firstNodeHierarchySimulation);
			break;
			case SECOND:
				secondNodeHierarchy = new DefaultMutableTreeNode(currentNode.getUserFriendlyName());
				currentNode.setDefaultMutableTreeNode(secondNodeHierarchy);
				firstNodeHierarchySimulation.add(secondNodeHierarchy);
				break;
			case THIRD:
				thirdNodeHierarchy = new DefaultMutableTreeNode(currentNode.getUserFriendlyName());
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
		
		DefaultMutableTreeNode robotsNode = SimulationTabTreeNodes.ROBOTS.getDefaultMutableTreeNode();
		if (firstTime){
			firstTime=false;
			for (int robotNr=1;robotNr<nrRobotsInSimulation+1;robotNr++){
				DefaultMutableTreeNode thirdNodeHierarchyRobot =  new DefaultMutableTreeNode(SimulationTabTreeNodes.ROBOT_NR.getUserFriendlyName()+"."+robotNr);
				robotsNode.add(thirdNodeHierarchyRobot);
				//secondNodeHierarchyRobots.add(thirdNodeHierarchyRobot);
				robotNumber =robotNr;
			}
		}else{
			robotNumber++;
			DefaultMutableTreeNode thirdNodeHierarchyRobot =  new DefaultMutableTreeNode(SimulationTabTreeNodes.ROBOT_NR.getUserFriendlyName()+"."+robotNumber);
        	DefaultTreeModel model = (DefaultTreeModel)jTreeSimulation.getModel();
        	model.insertNodeInto(thirdNodeHierarchyRobot, robotsNode, robotsNode.getChildCount());	
		}
		
		jTreeSimulation.revalidate();
		jTreeSimulation.repaint();
		jScrollPaneTreeSimulation.setViewportView(jTreeSimulation);
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
