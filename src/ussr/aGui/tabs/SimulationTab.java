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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
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


/**

 * @author Konstantinas
 */
public class SimulationTab extends Tabs {


	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	public  GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * @param initiallyVisible
	 * @param firstTabbedPane
	 * @param tabTitle
	 * @param imageIconDirectory
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

		DefaultMutableTreeNode secondNodeHierarchyRobot =  new DefaultMutableTreeNode(TreeElements.Robot.toString());
		firstNodeHierarchy.add(secondNodeHierarchyRobot);




		DefaultMutableTreeNode secondNodeHierarchyWorldDescription = new DefaultMutableTreeNode(TreeElements.World_Description.toString().replace("_", " "));
		firstNodeHierarchy.add(secondNodeHierarchyWorldDescription);

		DefaultMutableTreeNode thirdNodeHierarchyPlaneSize =  new DefaultMutableTreeNode(TreeElements.Plane_Size.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyPlaneSize);
		DefaultMutableTreeNode thirdNodeHierarchyPlaneTexture =  new DefaultMutableTreeNode(TreeElements.Plane_Texture.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyPlaneTexture);
		DefaultMutableTreeNode thirdNodeHierarchyCameraPosition = new DefaultMutableTreeNode(TreeElements.Camera_Position.toString().replace("_", " "));
		secondNodeHierarchyWorldDescription.add(thirdNodeHierarchyCameraPosition);

		DefaultMutableTreeNode secondNodeHierarchyPhysicsParameters = new DefaultMutableTreeNode(TreeElements.Physics_Parameters.toString().replace("_", " "));
		firstNodeHierarchy.add(secondNodeHierarchyPhysicsParameters);




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
		

		
		jPanelEditor.add(jComboBoxPlaneTexture);

	}





	private static javax.swing.JTree jTree1;
	private static javax.swing.JScrollPane jScrollPaneTree;

	private static javax.swing.JButton jButtonPlaneTiltRight;
	private static javax.swing.JSpinner  jSpinnerPlaneSize ;
	private static javax.swing.JComboBox jComboBoxPlaneTexture;

	private static javax.swing.JToolBar jToolBarPlaneControl;

	private static javax.swing.JPanel jPanelEditor;



}
