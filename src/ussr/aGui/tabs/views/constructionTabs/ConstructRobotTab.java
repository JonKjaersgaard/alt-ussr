package ussr.aGui.tabs.views.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import ussr.aGui.FramesInter;
import ussr.aGui.GuiFrames;
import ussr.aGui.MainFrames;
import ussr.aGui.MainFramesInter;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.additionalResources.HintPanelTypes;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.views.constructionTabs.ConstructionTabsInter.ModularRobotsNames;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "1 Step: Construct Robot".  
 * @author Konstantinas
 */
public class ConstructRobotTab extends ConstructionTabs implements ConstructRobotTabInter {

	/**
	 * The container for radio buttons of supported modular robots (ATRON,Odin and so on).
	 */
	private static ArrayList<AbstractButton> jRadioButtons =  new ArrayList<AbstractButton>() ;	

	/**
	 * Container for buttons contained in the tab, except radio buttons.
	 *//*
	private static ArrayList<javax.swing.JButton> jButtons =  new ArrayList<javax.swing.JButton>() ;*/

	/**
	 * The constants of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();	
	

	/**
	 * Defines visual appearance of the tab called "1 Step: Construct Robot".
	 * TODO
	 * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane.
	 * @param tabTitle, the title of the tab.
	 * @param jmeSimulation, the physical simulation.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public ConstructRobotTab(boolean initiallyVisible, boolean firstTabbedPane,String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());	
	
		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	protected void initComponents(){

		/*Instantiation of components*/		
		jButtonStartNewRobot = new javax.swing.JButton();
		
		jToolBarSaveLoad = new javax.swing.JToolBar();
		jToolBarGenericTools = new javax.swing.JToolBar();
		jToolBarRotationTools = new javax.swing.JToolBar();
		jToolBarConstructionTools = new javax.swing.JToolBar();

		jButtonDelete =   new javax.swing.JButton();
		jButtonMove =   new javax.swing.JButton();	
		jButtonColorConnetors =   new javax.swing.JButton();
		jButtonOppositeRotation =   new javax.swing.JButton();
		jButtonOnSelectedConnector =   new javax.swing.JButton();
		jButtonConnectAllModules = new javax.swing.JButton();
		jButtonJumpFromConnToConnector = new javax.swing.JButton();
		jButtonOnNextConnector = new javax.swing.JButton();
		jButtonOnPreviousConnector = new javax.swing.JButton();
		
		buttonGroupModularRobots = new ButtonGroup();
		radionButtonATRON =  new JRadioButton();
		radionButtonODIN =  new JRadioButton();
		radioButtonMTRAN =  new JRadioButton();
		radionButtonCKBOTSTANDARD =  new JRadioButton();

		jLabelOperations = new javax.swing.JLabel();	

		jComboBoxEntity = new javax.swing.JComboBox();
		jComboBoxStandardRotations = new javax.swing.JComboBox();
		jComboBoxNrConnectorsConstructionTool = new javax.swing.JComboBox();

		jSeparator1 = new javax.swing.JToolBar.Separator();
		jSeparator2 = new javax.swing.JToolBar.Separator();	

		/*Description of components */		
		jButtonStartNewRobot.setText("Start new robot");
		jButtonStartNewRobot.setRolloverEnabled(true);
		jButtonStartNewRobot.setToolTipText(TOOL_TIP_TEXTS[0]);
		jButtonStartNewRobot.setFocusable(true);
		jButtonStartNewRobot.setPreferredSize(new Dimension(110,GuiFrames.COMMON_HEIGHT+2));
        jButtonStartNewRobot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {            	
            	ConstructRobotTabController.jButtonStartNewRobotActionPerformed(jmeSimulation);            	
            }
        });        
        gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_START;// position on the first line start
        gridBagConstraints.gridx = 0;// x goes from left to the right of the screen
		gridBagConstraints.gridy = 0;// y goes from top to the bottom of the screen
		gridBagConstraints.insets = new Insets(0,0,15,50);  //make some space on the right so that button moves to the left and at the bottom (not so crowded)
		super.jComponent.add(jButtonStartNewRobot,gridBagConstraints);
	
		jToolBarSaveLoad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarSaveLoad.setFloatable(false);//user can not make the tool bar to float
		jToolBarSaveLoad.setRollover(true);// the components inside are roll over
		jToolBarSaveLoad.setToolTipText(TOOL_TIP_TEXTS[1]);
		jToolBarSaveLoad.setPreferredSize(new Dimension(60,GuiFrames.COMMON_HEIGHT+2));
		gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_END;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0,0,15,-25);  
		
		/*Reuse the buttons for saving and loading  already initialized in the main window*/
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);
	
		FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DIRECTORY_ROBOTS);	
		FileChooserFrameInter fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DIRECTORY_ROBOTS);
		
		jToolBarSaveLoad.add(MainFrames.initSaveButton(fcSaveFrame));
		jToolBarSaveLoad.add(MainFrames.initOpenButton(fcOpenFrame));
		
		super.jComponent.add(jToolBarSaveLoad,gridBagConstraints);				
		
		radionButtonATRON.setFocusable(false);
		radionButtonATRON.setText(ModularRobotsNames.ATRON.toString());
		radionButtonATRON.setEnabled(false);
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0,0,0,0);//reset
		super.jComponent.add(radionButtonATRON,gridBagConstraints);
		buttonGroupModularRobots.add(radionButtonATRON);
		jRadioButtons.add(radionButtonATRON);
		
		radionButtonODIN.setText(ModularRobotsNames.Odin.toString());
		radionButtonODIN.setFocusable(false);
		radionButtonODIN.setEnabled(false);
		radionButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radionButtonODIN,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		super.jComponent.add(radionButtonODIN,gridBagConstraints);
		buttonGroupModularRobots.add(radionButtonODIN);
		jRadioButtons.add(radionButtonODIN);
		
		radioButtonMTRAN.setText(ModularRobotsNames.MTRAN.toString());
		radioButtonMTRAN.setFocusable(false);
		radioButtonMTRAN.setEnabled(false);
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		super.jComponent.add(radioButtonMTRAN,gridBagConstraints);
		buttonGroupModularRobots.add(radioButtonMTRAN);
		jRadioButtons.add(radioButtonMTRAN);
		
		
		radionButtonCKBOTSTANDARD.setText(ModularRobotsNames.CKBotStandard.toString());
		radionButtonCKBOTSTANDARD.setFocusable(false);
		radionButtonCKBOTSTANDARD.setEnabled(false);
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				ConstructRobotTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 40; 
		super.jComponent.add(radionButtonCKBOTSTANDARD,gridBagConstraints);
		buttonGroupModularRobots.add(radionButtonCKBOTSTANDARD);	
		jRadioButtons.add(radionButtonCKBOTSTANDARD);		

		jLabelOperations.setText("Operations on existing modules:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;// reset grid width
		gridBagConstraints.insets = new Insets(0,0,10,0);  
		
		super.jComponent.add(jLabelOperations,gridBagConstraints);
		
		jToolBarRotationTools.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarRotationTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarRotationTools.setRollover(true);// the components inside are roll over
		jToolBarRotationTools.setToolTipText("Module rotation tools");
		jToolBarRotationTools.setPreferredSize(new Dimension(100,GuiFrames.COMMON_HEIGHT+2));
		
		/*External layout of the toolbar in the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(0,3,10,18);  
		

		jButtonOppositeRotation.setToolTipText(TOOL_TIP_TEXTS[2]);		
		jButtonOppositeRotation.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OPPOSITE));
		jButtonOppositeRotation.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));
		jButtonOppositeRotation.setFocusable(false); 
		jButtonOppositeRotation.setEnabled(false);	
		jButtonOppositeRotation.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT-3));	
		jButtonOppositeRotation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonOppositeRotation);
				ConstructRobotTabController.jButtonOppositeRotationActionPerformed(jmeSimulation);
			}
		});

		jComboBoxStandardRotations.setToolTipText(TOOL_TIP_TEXTS[3]);
		jComboBoxStandardRotations.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBoxStandardRotations.setPreferredSize(new java.awt.Dimension(140, GuiFrames.COMMON_HEIGHT-3));
		jComboBoxStandardRotations.setEnabled(false);
		jComboBoxStandardRotations.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				ConstructRobotTabController.jComboBoxStandardRotationsActionPerformed(jmeSimulation);
			}
		});

		/*Internal layout of the toolbar*/
		GroupLayout jToolBarRotationToolsLayout = new GroupLayout(jToolBarRotationTools);
		jToolBarRotationTools.setLayout(jToolBarRotationToolsLayout);


		jToolBarRotationToolsLayout.setHorizontalGroup(
				jToolBarRotationToolsLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButtonOppositeRotation,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jComboBoxStandardRotations,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)				
		);

		jToolBarRotationToolsLayout.setVerticalGroup(
				jToolBarRotationToolsLayout.createSequentialGroup()
				.addGroup(jToolBarRotationToolsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonOppositeRotation,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBoxStandardRotations,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))						
		);

		super.jComponent.add(jToolBarRotationTools,gridBagConstraints);
		
		jComboBoxEntity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Module", "Robot" }));
		jComboBoxEntity.setPreferredSize(new java.awt.Dimension(65, GuiFrames.COMMON_HEIGHT));
		jComboBoxEntity.setEnabled(false);
		jComboBoxEntity.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxEntityActionPerformed(jComboBoxEntity,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(0,0,0,0); 
		
		super.jComponent.add(jComboBoxEntity,gridBagConstraints);

		jToolBarGenericTools.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGenericTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarGenericTools.setRollover(true);// the buttons inside are roll over
		jToolBarGenericTools.setToolTipText("Generic tools");
		jToolBarGenericTools.setPreferredSize(new Dimension(195,GuiFrames.COMMON_HEIGHT));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.insets = new Insets(8,3,10,18);  

		jButtonMove.setToolTipText(TOOL_TIP_TEXTS[4]);		
		jButtonMove.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + MOVE));
		jButtonMove.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));
		jButtonMove.setFocusable(false); 
		jButtonMove.setEnabled(false);
		jButtonMove.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT));	
		jButtonMove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonMove);
				ConstructRobotTabController.jButtonMoveActionPerformed(jmeSimulation);
			}
		});
		
		jToolBarGenericTools.add(jButtonMove);

		jButtonDelete.setToolTipText(TOOL_TIP_TEXTS[5]);
		jButtonDelete.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + DELETE));
		jButtonDelete.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonDelete.setFocusable(false);
		jButtonDelete.setEnabled(false);
		jButtonDelete.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT));
		jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonDelete);
				ConstructRobotTabController.jButtonDeleteActionPerformed(jmeSimulation);
			}
		});
		jToolBarGenericTools.add(jButtonDelete);		

		jButtonColorConnetors.setToolTipText(TOOL_TIP_TEXTS[6]);
		jButtonColorConnetors.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + COLOUR_CONNECTORS));
		jButtonColorConnetors.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonColorConnetors.setFocusable(false);
		jButtonColorConnetors.setEnabled(false);
		jButtonColorConnetors.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH));
		jButtonColorConnetors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonColorConnetors);
				ConstructRobotTabController.jButtonColorConnectorsActionPerformed(jmeSimulation);
			}
		});
		
		jToolBarGenericTools.add(jButtonColorConnetors);	

		super.jComponent.add(jToolBarGenericTools,gridBagConstraints);


		jLabelAddNewModules = new javax.swing.JLabel();		
		jLabelAddNewModules.setText("Add new modules:");	
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.insets = new Insets(0,0,10,10);  //make some space at the bottom and right
		super.jComponent.add(jLabelAddNewModules,gridBagConstraints);

		jToolBarConstructionTools.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarConstructionTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarConstructionTools.setRollover(true);// the buttons inside are roll over
		jToolBarConstructionTools.setToolTipText(TOOL_TIP_TEXTS[7]);
		jToolBarConstructionTools.setPreferredSize(new Dimension(195,GuiFrames.COMMON_HEIGHT+2));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(0,0,10,18);  

		jButtonOnSelectedConnector.setToolTipText(TOOL_TIP_TEXTS[8]);
		jButtonOnSelectedConnector.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ON_SELECTED_CONNECTOR));
		jButtonOnSelectedConnector.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonOnSelectedConnector.setFocusable(false);
		jButtonOnSelectedConnector.setEnabled(false);
		jButtonOnSelectedConnector.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH-3));
		jButtonOnSelectedConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonOnSelectedConnector);
				ConstructRobotTabController.jButtonOnSelectedConnectorActionPerformed(jmeSimulation);
			}
		});	

		jComboBoxNrConnectorsConstructionTool.setToolTipText(TOOL_TIP_TEXTS[9]);
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBoxNrConnectorsConstructionTool.setPreferredSize(new java.awt.Dimension(60, GuiFrames.COMMON_HEIGHT-4));
		jComboBoxNrConnectorsConstructionTool.setEnabled(false);
		jComboBoxNrConnectorsConstructionTool.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxNrConnectorsConstructionToolActionPerformed(jComboBoxNrConnectorsConstructionTool,jmeSimulation);
			}
		});		

		jButtonConnectAllModules.setToolTipText(TOOL_TIP_TEXTS[10]);
		jButtonConnectAllModules.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + CONNECT_ALL_MODULES));
		jButtonConnectAllModules.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonConnectAllModules.setFocusable(false);
		jButtonConnectAllModules.setEnabled(false);
		jButtonConnectAllModules.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH-3));
		jButtonConnectAllModules.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonConnectAllModules);
				ConstructRobotTabController.jButtonConnectAllModulesActionPerformed(jmeSimulation);
			}
		});	

		jSeparator1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jSeparator1.setPreferredSize(new Dimension(6,30));

		jButtonOnPreviousConnector.setToolTipText(TOOL_TIP_TEXTS[11]);
		jButtonOnPreviousConnector.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PREVIOUS));
		jButtonOnPreviousConnector.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonOnPreviousConnector.setFocusable(false);
		jButtonOnPreviousConnector.setEnabled(false);
		jButtonOnPreviousConnector.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH-3));
		jButtonOnPreviousConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonOnPreviousConnector);				
				ConstructRobotTabController.jButtonOnPreviousConnectorActionPerformed(jmeSimulation);
			}
		});		

		jButtonJumpFromConnToConnector.setToolTipText(TOOL_TIP_TEXTS[12]);
		jButtonJumpFromConnToConnector.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + JUMP_FROM_CONN_TO_CONNECTOR));
		jButtonJumpFromConnToConnector.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonJumpFromConnToConnector.setFocusable(false);
		jButtonJumpFromConnToConnector.setEnabled(false);
		jButtonJumpFromConnToConnector.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH-3));
		jButtonJumpFromConnToConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonJumpFromConnToConnector);
				ConstructRobotTabController.jButtonJumpFromConnToConnectorActionPerformed(jmeSimulation);
			}
		});	

		jButtonOnNextConnector.setToolTipText(TOOL_TIP_TEXTS[13]);
		jButtonOnNextConnector.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NEXT));
		jButtonOnNextConnector.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));		
		jButtonOnNextConnector.setFocusable(false);
		jButtonOnNextConnector.setEnabled(false);
		jButtonOnNextConnector.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.BUTTONS_WIDTH-3));
		jButtonOnNextConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonOnNextConnector);
				ConstructRobotTabController.jButtonOnNextConnectorActionPerformed(jmeSimulation);
			}
		});
	
		jSeparator2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jSeparator2.setPreferredSize(new Dimension(6,30));


		/*Internal layout of the toolbar*/
		GroupLayout jToolBarConstructionToolsLayout = new GroupLayout(jToolBarConstructionTools);
		jToolBarConstructionTools.setLayout(jToolBarConstructionToolsLayout);

		jToolBarConstructionToolsLayout.setHorizontalGroup(
				jToolBarConstructionToolsLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButtonOnSelectedConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jComboBoxNrConnectorsConstructionTool,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)				
								.addComponent(jButtonConnectAllModules,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										//Forces preferred side of component and also specifies it explicitly. For instance:6. 
										.addComponent(jSeparator1,GroupLayout.PREFERRED_SIZE, 6,
												GroupLayout.PREFERRED_SIZE)
												.addComponent(jButtonOnPreviousConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
														.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
														.addComponent(jButtonOnNextConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
														.addComponent(jSeparator2,GroupLayout.PREFERRED_SIZE, 6,
																GroupLayout.PREFERRED_SIZE)
							
		);

		jToolBarConstructionToolsLayout.setVerticalGroup(
				jToolBarConstructionToolsLayout.createSequentialGroup()
				.addGroup(jToolBarConstructionToolsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonOnSelectedConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBoxNrConnectorsConstructionTool,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jButtonConnectAllModules,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
												//Forces preferred side of component and also specifies it explicitly. For instance:28. 
												.addComponent(jSeparator1,GroupLayout.PREFERRED_SIZE, 28,
														GroupLayout.PREFERRED_SIZE)
														.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
																.addComponent(jButtonOnNextConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(jButtonOnPreviousConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																.addComponent(jSeparator2,GroupLayout.PREFERRED_SIZE, 28,
																		GroupLayout.PREFERRED_SIZE))
																		
		);
		super.jComponent.add(jToolBarConstructionTools,gridBagConstraints);
		
		/*Display for hints. Feedback to the user.*/
		hintPanel =  initHintPanel(430,120,HintPanelInter.builInHintsConstrucRobotTab[0]);			
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 5;
		super.jComponent.add(hintPanel,gridBagConstraints);
			
	}
	
	
	/*Getters and setters*/
	
	/**
	 * Enables and disables radio buttons with names of modular robots.
	 * @param enabled, true if enabled.
	 */
	public static void setRadioButtonsEnabled(boolean enabled){
		for (AbstractButton radioButton: jRadioButtons ){
			radioButton.setEnabled(enabled);
		}
	}
	
	/**
	 * Enables  and disables  buttons for moving module from one connector onto another.
	 * @param enabled, true if enabled.
	 */
	public static void setEnabledButtonsArrows(boolean enabled){
		jButtonOnNextConnector.setEnabled(enabled);
		jButtonOnPreviousConnector.setEnabled(enabled);
	}	

	/**
	 * Enables and disables the tool bar containing generic tools for manipulating modules.
	 * @param enable,true for tool bar to be disabled. 
	 */
	public static void setEnabledGenericToolBar(boolean enable){
		jButtonDelete.setEnabled(enable);
		jButtonMove.setEnabled(enable);
		jButtonColorConnetors.setEnabled(enable);
	}

	/**
	 * Returns button for moving module or robot.
	 * @return jButtonMove, the button for moving module or robot.
	 */
	public static javax.swing.JButton getJButtonMove() {
		return jButtonMove;
	}


	/**
	 * Enables and disables the tool bar containing the tools for constructing the shape of modular robot(Add new modules).
	 * @param enable,true for tool bar to be disabled. 
	 */
	public static void setEnabledConstructionToolsToolBar(boolean enable){
		jButtonOnSelectedConnector.setEnabled(enable);
		jComboBoxNrConnectorsConstructionTool.setEnabled(enable);
		jButtonConnectAllModules.setEnabled(enable);
		jButtonJumpFromConnToConnector.setEnabled(enable);
		jButtonOnNextConnector.setEnabled(enable);
		jButtonOnPreviousConnector.setEnabled(enable);
	}

	/**
	 * Enables and disables tool bar containing tools for applying rotation to module(s).
	 * @param enable, true for tool bar to be disabled.
	 */
	public static void setEnabledRotationToolBar(boolean enable){
		jButtonOppositeRotation.setEnabled(enable);
		jComboBoxStandardRotations.setEnabled(enable);	
	}	

	/**
	 * Returns JComboBox, containing choice of module or robot
	 * @return jComboBoxEntity, containing choice of module or robot.
	 */
	public static javax.swing.JComboBox getJComboBoxEntity() {
		return jComboBoxEntity;
	}

	/**
	 * Returns JComboBox, containing robot specific(standard) rotations.
	 * @return jComboBoxStandardRotations, containing robot specific(standard) rotations.
	 */
	public static javax.swing.JComboBox getjComboBoxStandardRotations() {
		return jComboBoxStandardRotations;
	}

	/**
	 * Returns JComboBox, containing numbers of connectors(on modular robot) for construction tool using them.
	 * @return jComboBoxNrConnectorsConstructionTool, containing numbers of connectors(on modular robot) for construction tool using them.
	 */
	public static javax.swing.JComboBox getJComboBoxNrConnectorsConstructionTool() {
		return jComboBoxNrConnectorsConstructionTool;
	}
	
	/**
	 * Returns radio button for ATRON modular robot.
	 * @return radionButtonATRON, radio button for ATRON modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonATRON() {
		return radionButtonATRON;
	}
	
	/**
	 * Returns radio button for MTRAN modular robot.
	 * @return radioButtonMTRAN, radio button for MTRAN modular robot.
	 */
	public static javax.swing.AbstractButton getRadioButtonMTRAN() {
		return radioButtonMTRAN;
	}
	
	/**
	 * Returns radio button for ODIN modular robot.
	 * @return radionButtonODIN, radio button for ODIN modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonODIN() {
		return radionButtonODIN;
	}
	
	/**
	 * Returns radio button for CKBOTSTANDARD modular robot.
	 * @return radionButtonCKBOTSTANDARD, radio button for CKBOTSTANDARD modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonCKBOTSTANDARD() {
		return radionButtonCKBOTSTANDARD;
	}
		
	/**
	 * Returns button for starting construction of new modular robot.
	 * @return jButtonStartNewRobot, button for starting construction of new modular robot.
	 */
	public static javax.swing.JButton getJButtonStartNewRobot() {
		return jButtonStartNewRobot;
	}
	
	
	/**
	 * Enables or disables the tab;
	 * @param enabled, true if the tab is enabled.
	 */
	public static void setTabEnabled (boolean enabled){
		getJButtonStartNewRobot().setEnabled(enabled);
		setRadioButtonsEnabled(enabled);		
		setEnabledRotationToolBar(enabled);
		setEnabledGenericToolBar(enabled);		
		setEnabledConstructionToolsToolBar(enabled);
		for (int button=0; button<getJToolBarSaveLoad().getComponentCount();button++ ){
			getJToolBarSaveLoad().getComponent(button).setEnabled(enabled);
		}
		getHintPanel().setType(HintPanelTypes.ATTENTION);
		ConstructRobotTab.getHintPanel().setText(HintPanelInter.builInHintsConstrucRobotTab[12]);
	}
	
	

	/**
	 * @return jToolBarSaveLoad, the toolbar containing buttons for saving and loading XML.
	 */
	public static javax.swing.JToolBar getJToolBarSaveLoad() {
		return jToolBarSaveLoad;
	}
	
	public static HintPanel getHintPanel(){
		return hintPanel;
	}

	/*Declaration of tab components*/
	private static javax.swing.JComboBox jComboBoxEntity,
    jComboBoxStandardRotations,jComboBoxNrConnectorsConstructionTool;	
	
	private static javax.swing.JLabel jLabelAddNewModules,jLabelOperations;
	
	private static ButtonGroup buttonGroupModularRobots;	

	private static  javax.swing.AbstractButton radionButtonATRON,
	radioButtonMTRAN,radionButtonODIN, radionButtonCKBOTSTANDARD;

	private  static javax.swing.JButton jButtonStartNewRobot,jButtonDelete,
	jButtonMove,jButtonColorConnetors,jButtonOppositeRotation,jButtonOnSelectedConnector,
	jButtonConnectAllModules,jButtonJumpFromConnToConnector,jButtonOnNextConnector,
	jButtonOnPreviousConnector;
	
	private static javax.swing.JToolBar jToolBarSaveLoad,jToolBarGenericTools,
	jToolBarRotationTools,jToolBarConstructionTools;

	private javax.swing.JToolBar.Separator jSeparator1,jSeparator2;
	
	private static HintPanel hintPanel;

	
}
