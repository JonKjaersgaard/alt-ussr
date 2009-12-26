package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JRadioButton;


import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.hintpanel.HintsConstructRobotTab;
import ussr.aGui.enumerations.tabs.TabsComponentsText;
import ussr.aGui.enumerations.tabs.TabsIcons;
import ussr.aGui.enumerations.tabs.IconsNumbersConnectors;
import ussr.aGui.helpers.ComboBoxRenderer;
import ussr.aGui.helpers.hintPanel.HintPanel;
import ussr.aGui.helpers.hintPanel.HintPanelTypes;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.enumerations.ATRONStandardRotations;
import ussr.builder.enumerations.CKBotStandardRotations;
import ussr.builder.enumerations.MTRANStandardRotations;
import ussr.builder.enumerations.OdinTypesModules;
import ussr.builder.enumerations.SupportedModularRobots;


/**
 * Defines visual appearance of the tab called "1 Step: Construct Robot".  
 * @author Konstantinas
 */
public class ConstructRobotTab extends ConstructionTabs {

	/**
	 * The container for radio buttons of supported modular robots (ATRON,Odin and so on).
	 */
	private static ArrayList<AbstractButton> jRadioButtons =  new ArrayList<AbstractButton>() ;	

	/**
	 * The custom renderers for comboBox with numbers of connectors and colors beside them for each modular robot.
	 */
	private final static ComboBoxRenderer ATRON_RENDERER =  new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.ATRON_CONNECTORS),
                                          ODIN_RENDERER = new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.ODIN_BALL_CONNECTORS), 
                                          MTRAN_RENDERER = new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.MTRAN_CONNECTORS), 
                                          CKBOT_STANDARD_RENDERER =  new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.CKBOTSTANDARD_CONNECTORS);
	/**
	 * The constants of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	

	/**
	 * Defines visual appearance of the tab called "1 Step: Construct Robot".
	 * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane from the top.
	 * @param tabTitle, the title of the tab.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public ConstructRobotTab(boolean initiallyVisible, boolean firstTabbedPane,String tabTitle,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);
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
			
		jToolBarGeneralControl = new javax.swing.JToolBar();
		jToolBarSupportedModularRobots = new javax.swing.JToolBar();
		jToolBarGenericTools = new javax.swing.JToolBar();
		jToolBarFirstModuleTools = new javax.swing.JToolBar();
		jToolBarConstructionTools = new javax.swing.JToolBar();
		jToolBarChangeModuleType = new javax.swing.JToolBar();

		
		jButtonStartNewRobot = new javax.swing.JButton();
		jButtonOpen = initOpenButton();
		jButtonSave = initSaveButton();		
		jButtonDelete =   new javax.swing.JButton();
		jButtonMoveModule = new javax.swing.JButton();		
		jButtonOppositeRotation =   new javax.swing.JButton();
		jButtonOnSelectedConnector =   new javax.swing.JButton();
		jButtonConnectAllModules = new javax.swing.JButton();
		jButtonJumpFromConnToConnector = new javax.swing.JButton();
		jButtonVariateModuleProperties = new javax.swing.JButton();
		jButtonAvailableRotationsLoop = new javax.swing.JButton();
		
		jToggleButtonColorConnetors =   new javax.swing.JToggleButton();

		buttonGroupModularRobots = new ButtonGroup();
		radioButtonATRON =  new JRadioButton();
		radioButtonODIN =  new JRadioButton();
		radioButtonMTRAN =  new JRadioButton();
		radioButtonCKBOTSTANDARD =  new JRadioButton();

		jLabelFirstModule = new javax.swing.JLabel();
		jLabelAddNewModules = new javax.swing.JLabel();	
		jLabelOperations = new javax.swing.JLabel();
		jLabelChangeModuleType =  new javax.swing.JLabel();

		jComboBoxEntity = new javax.swing.JComboBox();
		jComboBoxStandardRotations = new javax.swing.JComboBox();
		jComboBoxNrConnectorsConstructionTool = new javax.swing.JComboBox();
		jComboBoxConstructionDefaultModuleType = new javax.swing.JComboBox();

		jSeparator2 = new javax.swing.JToolBar.Separator();
		jSeparator3 = new javax.swing.JToolBar.Separator();
		
		/*Description of components */	
		jToolBarGeneralControl.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarGeneralControl.setToolTipText(TabsComponentsText.GENERAL_CONTROL.getUserFriendlyName());
		jToolBarGeneralControl.setFloatable(false);//user can not make the tool bar to float
		jToolBarGeneralControl.setRollover(true);// the components inside are roll over
		jToolBarGeneralControl.setPreferredSize(new Dimension(375,FramesInter.HORIZONTAL_TOOLBAR_HEIGHT));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;// position on the first line start
		gridBagConstraints.gridx = 0;// x goes from left to the right of the screen
		gridBagConstraints.gridy = 0;// y goes from top to the bottom of the screen
		gridBagConstraints.gridwidth = 3;		
		
		jToolBarGeneralControl.add(jButtonOpen);		
		jToolBarGeneralControl.add(jButtonSave);		
		
		jButtonStartNewRobot.setIcon(TabsIcons.NEW_ROBOT.getImageIcon());
		jButtonStartNewRobot.setSelectedIcon(TabsIcons.NEW_ROBOT.getImageIcon());
		jButtonStartNewRobot.setRolloverIcon(TabsIcons.NEW_ROBOT_ROLLOVER.getImageIcon());
		jButtonStartNewRobot.setDisabledIcon(TabsIcons.NEW_ROBOT_DISABLED.getImageIcon());
		jButtonStartNewRobot.setToolTipText(TabsComponentsText.START_CONSTRUCTING_NEW_ROBOT.getUserFriendlyName());
		jButtonStartNewRobot.setFocusable(false);
		jButtonStartNewRobot.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonStartNewRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonStartNewRobotActionPerformed();            	
			}
		});
		jToolBarGeneralControl.add(jButtonStartNewRobot);
		
		jSeparator3.setPreferredSize(new Dimension(6,30));
		jToolBarGeneralControl.add(jSeparator3);
		
		jToggleButtonColorConnetors.setToolTipText(TabsComponentsText.COLOR_MODULE_CONNECTORS.getUserFriendlyName());
		jToggleButtonColorConnetors.setIcon(TabsIcons.COLOR_CONNECTORS.getImageIcon());
		jToggleButtonColorConnetors.setSelectedIcon(TabsIcons.COLOR_CONNECTORS.getImageIcon());
		jToggleButtonColorConnetors.setRolloverIcon(TabsIcons.COLOR_CONNECTORS_ROLLOVER.getImageIcon());
		jToggleButtonColorConnetors.setDisabledIcon(TabsIcons.COLOR_CONNECTORS_DISABLED.getImageIcon());		
		jToggleButtonColorConnetors.setFocusable(false);
		jToggleButtonColorConnetors.setEnabled(true);
		jToggleButtonColorConnetors.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jToggleButtonColorConnetors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonColorConnectorsActionPerformed(jToggleButtonColorConnetors);
			}
		});
		
		jToolBarGeneralControl.add(jToggleButtonColorConnetors);
		
		super.jComponent.add(jToolBarGeneralControl,gridBagConstraints);
		
		jLabelFirstModule.setText(TabsComponentsText.ADD_AND_ADJUST_FIRST_MODULE.getUserFriendlyName());
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(10,0,0,10);  
		super.jComponent.add(jLabelFirstModule,gridBagConstraints);
		
		
		//jToolBarSupportedModularRobots.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarSupportedModularRobots.setFloatable(false);//user can not make the tool bar to float
		jToolBarSupportedModularRobots.setRollover(true);// the components inside are roll over
		jToolBarSupportedModularRobots.setToolTipText(TabsComponentsText.CHOOSE_SUPPORTED_MODULAR_ROBOT.getUserFriendlyName());
		jToolBarSupportedModularRobots.setPreferredSize(new Dimension(275,FramesInter.HORIZONTAL_TOOLBAR_HEIGHT));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0,0,0,0);  		
		
		radioButtonATRON.setFocusable(false);
		radioButtonATRON.setText(SupportedModularRobots.ATRON.getUserFriendlyName());
		radioButtonATRON.setEnabled(false);
		radioButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonATRON);
			}
		});
		jToolBarSupportedModularRobots.add(radioButtonATRON);
		buttonGroupModularRobots.add(radioButtonATRON);
		jRadioButtons.add(radioButtonATRON);

		radioButtonODIN.setText(SupportedModularRobots.ODIN.getUserFriendlyName());
		radioButtonODIN.setFocusable(false);
		radioButtonODIN.setEnabled(false);
		radioButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonODIN);
			}
		});
		jToolBarSupportedModularRobots.add(radioButtonODIN);
		buttonGroupModularRobots.add(radioButtonODIN);
		jRadioButtons.add(radioButtonODIN);

		radioButtonMTRAN.setText(SupportedModularRobots.MTRAN.getUserFriendlyName());
		radioButtonMTRAN.setFocusable(false);
		radioButtonMTRAN.setEnabled(false);
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonMTRAN);
			}
		});
		jToolBarSupportedModularRobots.add(radioButtonMTRAN);
		buttonGroupModularRobots.add(radioButtonMTRAN);
		jRadioButtons.add(radioButtonMTRAN);

		radioButtonCKBOTSTANDARD.setText(SupportedModularRobots.CKBOT_STANDARD.getUserFriendlyName());
		radioButtonCKBOTSTANDARD.setFocusable(false);
		radioButtonCKBOTSTANDARD.setEnabled(false);
		radioButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonCKBOTSTANDARD);
			}
		});
		jToolBarSupportedModularRobots.add(radioButtonCKBOTSTANDARD);
		buttonGroupModularRobots.add(radioButtonCKBOTSTANDARD);	
		jRadioButtons.add(radioButtonCKBOTSTANDARD);
		
		super.jComponent.add(jToolBarSupportedModularRobots,gridBagConstraints);
	
	
		jToolBarFirstModuleTools.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarFirstModuleTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarFirstModuleTools.setRollover(true);// the components inside are roll over
		jToolBarFirstModuleTools.setToolTipText(TabsComponentsText.ADJUST_MODULE_PROPERTIES.getUserFriendlyName());
		jToolBarFirstModuleTools.setPreferredSize(new Dimension(100,35));

		/*External layout of the toolbar in the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth =2;
		gridBagConstraints.insets = new Insets(0,0,0,0); 
		
		jButtonMoveModule.setToolTipText(TabsComponentsText.MOVE_MODULE.getUserFriendlyName());	
		jButtonMoveModule.setIcon(TabsIcons.MOVE.getImageIcon());
		jButtonMoveModule.setSelectedIcon(TabsIcons.MOVE.getImageIcon());
		jButtonMoveModule.setRolloverIcon(TabsIcons.MOVE_ROLLOVER.getImageIcon());		
		jButtonMoveModule.setDisabledIcon(TabsIcons.MOVE_DISABLED.getImageIcon());		
		jButtonMoveModule.setFocusable(false); 
		jButtonMoveModule.setEnabled(true);
		jButtonMoveModule.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		jButtonMoveModule.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonMoveModule);
				ConstructRobotTabController.jButtonMoveActionPerformed();
			}
		});

		jButtonAvailableRotationsLoop.setToolTipText(TabsComponentsText.AVAILABLE_ROTATIONS.getUserFriendlyName());
		jButtonAvailableRotationsLoop.setIcon(TabsIcons.AVAILABLE_ROTATIONS.getImageIcon());
		jButtonAvailableRotationsLoop.setRolloverIcon(TabsIcons.AVAILABLE_ROTATIONS_ROLLOVER.getImageIcon());
		jButtonAvailableRotationsLoop.setSelectedIcon(TabsIcons.AVAILABLE_ROTATIONS_SELECTED.getImageIcon());
		jButtonAvailableRotationsLoop.setDisabledIcon(TabsIcons.AVAILABLE_ROTATIONS_DISABLED.getImageIcon());
		jButtonAvailableRotationsLoop.setFocusable(false); 		
		jButtonAvailableRotationsLoop.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		jButtonAvailableRotationsLoop.setEnabled(false);
		jButtonAvailableRotationsLoop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonAvailableRotationsLoop);
				ConstructRobotTabController.jButtonStandardRotationsLoopActionPerformed();
			}
		});

		jComboBoxStandardRotations.setToolTipText(TabsComponentsText.STANDARD_ROTATIONS.getUserFriendlyName());
		jComboBoxStandardRotations.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBoxStandardRotations.setPreferredSize(new java.awt.Dimension(140, 30));
		jComboBoxStandardRotations.setEnabled(false);
		jComboBoxStandardRotations.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				ConstructRobotTabController.jComboBoxStandardRotationsActionPerformed(jComboBoxStandardRotations);
			}
		});

		/*Internal layout of the toolbar*/
		GroupLayout jToolBarRotationToolsLayout = new GroupLayout(jToolBarFirstModuleTools);
		jToolBarFirstModuleTools.setLayout(jToolBarRotationToolsLayout);
		
		jToolBarRotationToolsLayout.setHorizontalGroup(
				jToolBarRotationToolsLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButtonMoveModule,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(jButtonAvailableRotationsLoop,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBoxStandardRotations,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
		);

		jToolBarRotationToolsLayout.setVerticalGroup(
				jToolBarRotationToolsLayout.createSequentialGroup()
				.addGroup(jToolBarRotationToolsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonMoveModule,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonAvailableRotationsLoop,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboBoxStandardRotations,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))						
		);

		super.jComponent.add(jToolBarFirstModuleTools,gridBagConstraints);
		
		jLabelAddNewModules.setText(TabsComponentsText.ADD_NEW_MODULES.getUserFriendlyName());	
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(20,0,0,0); 
		super.jComponent.add(jLabelAddNewModules,gridBagConstraints);

		jToolBarConstructionTools.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarConstructionTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarConstructionTools.setRollover(true);// the buttons inside are roll over
		jToolBarConstructionTools.setToolTipText(TabsComponentsText.ADDITION_OF_NEW_MODULES.getUserFriendlyName());
		jToolBarConstructionTools.setPreferredSize(new Dimension(195,35));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(20,0,0,0); 

		jButtonOnSelectedConnector.setToolTipText(TabsComponentsText.ON_SELECTED_CONNECTOR.getUserFriendlyName());
		jButtonOnSelectedConnector.setIcon(TabsIcons.ON_SELECTED_CONNECTOR.getImageIcon());
		jButtonOnSelectedConnector.setSelectedIcon(TabsIcons.ON_SELECTED_CONNECTOR_ROLLOVER.getImageIcon());
		jButtonOnSelectedConnector.setRolloverIcon(TabsIcons.ON_SELECTED_CONNECTOR_ROLLOVER.getImageIcon());
		jButtonOnSelectedConnector.setDisabledIcon(TabsIcons.ON_SELECTED_CONNECTOR_DISABLED.getImageIcon());		
		jButtonOnSelectedConnector.setFocusable(false);
		jButtonOnSelectedConnector.setEnabled(false);
		jButtonOnSelectedConnector.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonOnSelectedConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonOnSelectedConnector);
				ConstructRobotTabController.jButtonOnSelectedConnectorActionPerformed();
			}
		});	

		jButtonConnectAllModules.setToolTipText(TabsComponentsText.ON_ALL_CONNECTORS.getUserFriendlyName());
		jButtonConnectAllModules.setIcon(TabsIcons.CONNECT_ALL_MODULES.getImageIcon());
		jButtonConnectAllModules.setSelectedIcon(TabsIcons.CONNECT_ALL_MODULES_ROLLOVER.getImageIcon());
		jButtonConnectAllModules.setRolloverIcon(TabsIcons.CONNECT_ALL_MODULES_ROLLOVER.getImageIcon());
		jButtonConnectAllModules.setDisabledIcon(TabsIcons.CONNECT_ALL_MODULES_DISABLED.getImageIcon());
		jButtonConnectAllModules.setFocusable(false);
		jButtonConnectAllModules.setEnabled(false);
		jButtonConnectAllModules.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonConnectAllModules.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonConnectAllModules);
				ConstructRobotTabController.jButtonConnectAllModulesActionPerformed();
			}
		});	

		jButtonJumpFromConnToConnector.setToolTipText(TabsComponentsText.JUMP_FROM_CONNECTOR_TO_CONNECTOR.getUserFriendlyName());		
		jButtonJumpFromConnToConnector.setIcon(TabsIcons.JUMP_FROM_CON_TO_CON.getImageIcon());
		jButtonJumpFromConnToConnector.setSelectedIcon(TabsIcons.JUMP_FROM_CON_TO_CON_ROLLOVER.getImageIcon());
		jButtonJumpFromConnToConnector.setRolloverIcon(TabsIcons.JUMP_FROM_CON_TO_CON_ROLLOVER.getImageIcon());
		jButtonJumpFromConnToConnector.setDisabledIcon(TabsIcons.JUMP_FROM_CON_TO_CON_DISABLED.getImageIcon());		
		jButtonJumpFromConnToConnector.setFocusable(false);
		jButtonJumpFromConnToConnector.setEnabled(false);
		jButtonJumpFromConnToConnector.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonJumpFromConnToConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonJumpFromConnToConnector);
				ConstructRobotTabController.jButtonJumpFromConnToConnectorActionPerformed();
			}
		});	

		jComboBoxNrConnectorsConstructionTool.setToolTipText(TabsComponentsText.ON_CHOSEN_CONNECTOR_NUMBER.getUserFriendlyName());
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBoxNrConnectorsConstructionTool.setPreferredSize(new java.awt.Dimension(60, 26));
		jComboBoxNrConnectorsConstructionTool.setEnabled(false);
		jComboBoxNrConnectorsConstructionTool.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxNrConnectorsConstructionToolActionPerformed(jComboBoxNrConnectorsConstructionTool);
			}
		});	

		/*Internal layout of the toolbar*/
		GroupLayout jToolBarConstructionToolsLayout = new GroupLayout(jToolBarConstructionTools);
		jToolBarConstructionTools.setLayout(jToolBarConstructionToolsLayout);

		jToolBarConstructionToolsLayout.setHorizontalGroup(
				jToolBarConstructionToolsLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButtonOnSelectedConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)								
						.addComponent(jButtonConnectAllModules,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								//Forces preferred side of component and also specifies it explicitly. For instance:6. 
								.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboBoxNrConnectorsConstructionTool,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)				

		);

		jToolBarConstructionToolsLayout.setVerticalGroup(
				jToolBarConstructionToolsLayout.createSequentialGroup()
				.addGroup(jToolBarConstructionToolsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonOnSelectedConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)							
								.addComponent(jButtonConnectAllModules,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										//Forces preferred side of component and also specifies it explicitly. For instance:28. 
										.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
												.addComponent(jComboBoxNrConnectorsConstructionTool,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))

		);
		super.jComponent.add(jToolBarConstructionTools,gridBagConstraints);
		
		jLabelChangeModuleType.setText(TabsComponentsText.CHANGE_MODULE_TYPE.getUserFriendlyName());	
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(5,0,0,0); 
		super.jComponent.add(jLabelChangeModuleType,gridBagConstraints);
		
	
		jToolBarChangeModuleType.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarChangeModuleType.setFloatable(false);//user can not make the tool bar to float
		jToolBarChangeModuleType.setRollover(true);// the buttons inside are roll over
		jToolBarChangeModuleType.setToolTipText(TabsComponentsText.OPERATIONS_FOR_CHANGING_MODULE_TYPE.getUserFriendlyName());
		jToolBarChangeModuleType.setPreferredSize(new Dimension(195,FramesInter.HORIZONTAL_TOOLBAR_HEIGHT+5));
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth =2;
		
		jComboBoxConstructionDefaultModuleType.setToolTipText(TabsComponentsText.DEFAULT_NEW_MODULE_TYPE.getUserFriendlyName());
		jComboBoxConstructionDefaultModuleType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBoxConstructionDefaultModuleType.setPreferredSize(new java.awt.Dimension(90, 28));
		jComboBoxConstructionDefaultModuleType.setVisible(false);
		jComboBoxConstructionDefaultModuleType.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				setDefaultConstructionModuleType(jComboBoxConstructionDefaultModuleType.getSelectedItem().toString());
			}
		});
		
		jButtonVariateModuleProperties.setToolTipText(TabsComponentsText.VARY_MODULE_TYPE_OR_PROPERTIES.getUserFriendlyName());
		jButtonVariateModuleProperties.setIcon(TabsIcons.VARY_PROPERTIES.getImageIcon());
		jButtonVariateModuleProperties.setSelectedIcon(TabsIcons.VARY_PROPERTIES.getImageIcon());
		jButtonVariateModuleProperties.setRolloverIcon(TabsIcons.VARY_PROPERTIES_ROLLOVER.getImageIcon());		
		jButtonVariateModuleProperties.setDisabledIcon(TabsIcons.VARY_PROPERTIES_DISABLED.getImageIcon());		
		jButtonVariateModuleProperties.setFocusable(false);
		jButtonVariateModuleProperties.setEnabled(true);
		jButtonVariateModuleProperties.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonVariateModuleProperties.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonVariateModuleProperties);
				ConstructRobotTabController.jButtonVariateModulePropertiesActionPerformed();
			}
		});		

		/*Internal layout of the toolbar*/
		GroupLayout jToolBarjToolBarChangeModuleTypeLayout = new GroupLayout(jToolBarChangeModuleType);
		jToolBarChangeModuleType.setLayout(jToolBarjToolBarChangeModuleTypeLayout);

		jToolBarjToolBarChangeModuleTypeLayout.setHorizontalGroup(
				jToolBarjToolBarChangeModuleTypeLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jComboBoxConstructionDefaultModuleType,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)								
						.addComponent(jButtonVariateModuleProperties,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)			

		);

		jToolBarjToolBarChangeModuleTypeLayout.setVerticalGroup(
				jToolBarjToolBarChangeModuleTypeLayout.createSequentialGroup()
				.addGroup(jToolBarjToolBarChangeModuleTypeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBoxConstructionDefaultModuleType,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)							
								.addComponent(jButtonVariateModuleProperties,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
		);
		super.jComponent.add(jToolBarChangeModuleType,gridBagConstraints);	
		
		jLabelOperations.setText(TabsComponentsText.OPERATIONS_ON_EXISTING.getUserFriendlyName());
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 1;

		super.jComponent.add(jLabelOperations,gridBagConstraints);				

		jComboBoxEntity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Module" /*,"Robot" */}));
		jComboBoxEntity.setPreferredSize(new java.awt.Dimension(65, 30));
		jComboBoxEntity.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxEntityActionPerformed(jComboBoxEntity);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 1;
	
		gridBagConstraints.insets = new Insets(10,0,0,0); 

		super.jComponent.add(jComboBoxEntity,gridBagConstraints);

		jToolBarGenericTools.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarGenericTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarGenericTools.setRollover(true);// the buttons inside are roll over
		jToolBarGenericTools.setToolTipText(TabsComponentsText.GENERIC_TOOLS.getUserFriendlyName());
		jToolBarGenericTools.setPreferredSize(new Dimension(125,30));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;		
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 2;

		jButtonDelete.setToolTipText(TabsComponentsText.DELETE_OR_REMOVE.getUserFriendlyName());
		jButtonDelete.setIcon(TabsIcons.DELETE.getImageIcon());
		jButtonDelete.setSelectedIcon(TabsIcons.DELETE.getImageIcon());
		jButtonDelete.setRolloverIcon(TabsIcons.DELETE_ROLLOVER.getImageIcon());		
		jButtonDelete.setDisabledIcon(TabsIcons.DELETE_DISABLED.getImageIcon());		
		jButtonDelete.setFocusable(false);
		jButtonDelete.setEnabled(false);
		jButtonDelete.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonDelete);
				ConstructRobotTabController.jButtonDeleteActionPerformed();
			}
		});
		jToolBarGenericTools.add(jButtonDelete);		
		
		jButtonOppositeRotation.setToolTipText(TabsComponentsText.ROTATE_OPPOSITE.getUserFriendlyName());		
		jButtonOppositeRotation.setIcon(TabsIcons.OPPOSITE_ROTATION.getImageIcon());
		jButtonOppositeRotation.setSelectedIcon(TabsIcons.OPPOSITE_ROTATION_ROLLOVER.getImageIcon());
		jButtonOppositeRotation.setRolloverIcon(TabsIcons.OPPOSITE_ROTATION_ROLLOVER.getImageIcon());	
		jButtonOppositeRotation.setDisabledIcon(TabsIcons.OPPOSITE_ROTATION_DISABLED.getImageIcon());
		jButtonOppositeRotation.setEnabled(false);	
		jButtonOppositeRotation.setFocusable(false); 
		jButtonOppositeRotation.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		jButtonOppositeRotation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonOppositeRotation);
				ConstructRobotTabController.jButtonOppositeRotationActionPerformed();
			}
		});
		
		jToolBarGenericTools.add(jButtonOppositeRotation);

		super.jComponent.add(jToolBarGenericTools,gridBagConstraints);	
		

		/*Display for hints. Feedback to the user.*/
		hintPanel =  initHintPanel(430,HINT_PANEL_HEIGHT,HintsConstructRobotTab.DEFAULT.getHintText());			
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 3;
		super.jComponent.add(hintPanel,gridBagConstraints);
	}	

	/*Getters and setters*/
	
	/*public static javax.swing.JComboBox getJComboBoxModuleType() {
		return jComboBoxModuleType;
	}*/

	/**
	 * Returns the button associated with functionality for coloring module connectors. 
	 * @return the button associated with functionality for coloring module connectors.
	 */
	public static javax.swing.JToggleButton getJToggleButtonColorConnetors() {
		return jToggleButtonColorConnetors;
	}

	/**
	 * Returns the button for moving first(initial) module.
	 * @return the button for moving first(initial) module.
	 */
/*	public static javax.swing.JButton getJButtonMoveModule() {
		return jButtonMoveModule;
	}*/
	/**
	 * Returns the button for initialization of variate module properties tool.
	 * @return the button for initialization of variate module properties tool.
	 */
	public static javax.swing.JButton getJButtonVariateModuleProperties() {
		return jButtonVariateModuleProperties;
	}

	/**
	 * Returns the button for initialization of available rotations tool.
	 * @return the button for initialization of available rotations tool.
	 */
/*	public static javax.swing.JButton getJButtonAvailableRotationsLoop() {
		return jButtonAvailableRotationsLoop;
	}*/

	/**
	 * Returns the button for initialization of opposite rotation tool. 
	 * @return the button for initialization of opposite rotation tool.
	 */
/*	public static javax.swing.JButton getJButtonOppositeRotation() {
		return jButtonOppositeRotation;
	}*/

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
	 * Returns the button group of radio buttons representing supported modular robots.
	 * @return the button group of radio buttons representing supported modular robots.
	 */
	public static ButtonGroup getButtonGroupModularRobots() {
		return buttonGroupModularRobots;
	}

	/**
	 * Enables and disables the tool bar containing generic tools for manipulating modules.
	 * @param enable,true for tool bar to be enabled. 
	 */
	public static void setEnabledGenericToolBar(boolean enabled){	
		jButtonDelete.setEnabled(enabled);
		jButtonOppositeRotation.setEnabled(enabled);
	}

	/**
	 * Enables and disables all tool bars in the tab.
	 * @param enable,true for tool bars to be enabled. 
	 */
	public static void setEnabledAllToolBars(boolean enabled){
		setEnabledFirstModuleToolBar(enabled);
		setEnabledGenericToolBar(enabled);
		setEnabledConstructionToolsToolBar(enabled);
	};

	/**
	 * Enables and disables the tool bar containing the tools for constructing the shape of modular robot(Add new modules).
	 * @param enabled,true for tool bar to be disabled. 
	 */
	public static void setEnabledConstructionToolsToolBar(boolean enabled){
		jButtonOnSelectedConnector.setEnabled(enabled);
		jComboBoxNrConnectorsConstructionTool.setEnabled(enabled);
		jButtonConnectAllModules.setEnabled(enabled);
		jButtonJumpFromConnToConnector.setEnabled(enabled);
	}

	/**
	 * Enables and disables tool bar containing tools for applying rotation to module(s).
	 * @param enabled, true for tool bar to be disabled.
	 */
	public static void setEnabledFirstModuleToolBar(boolean enabled){
		jComboBoxStandardRotations.setEnabled(enabled);
		jButtonAvailableRotationsLoop.setEnabled(enabled);
		jButtonMoveModule.setEnabled(enabled);
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
/*	public static javax.swing.JComboBox getjComboBoxStandardRotations() {
		return jComboBoxStandardRotations;
	}*/

	/**
	 * Returns JComboBox, containing numbers of connectors(on modular robot) for construction tool using them.
	 * @return jComboBoxNrConnectorsConstructionTool, containing numbers of connectors(on modular robot) for construction tool using them.
	 */
	/*public static javax.swing.JComboBox getJComboBoxNrConnectorsConstructionTool() {
		return jComboBoxNrConnectorsConstructionTool;
	}*/

	/**
	 * Returns radio button for ATRON modular robot.
	 * @return radionButtonATRON, radio button for ATRON modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonATRON() {
		return radioButtonATRON;
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
		return radioButtonODIN;
	}

	/**
	 * Returns radio button for CKBOTSTANDARD modular robot.
	 * @return radionButtonCKBOTSTANDARD, radio button for CKBOTSTANDARD modular robot.
	 */
	public static javax.swing.AbstractButton getRadionButtonCKBOTSTANDARD() {
		return radioButtonCKBOTSTANDARD;
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
		setEnabledGeneralToolBar(enabled);
		setRadioButtonsEnabled(enabled);		
		setEnabledFirstModuleToolBar(enabled);
		setEnabledGenericToolBar(enabled);		
		setEnabledConstructionToolsToolBar(enabled);
		if (enabled==false){
			getHintPanel().setType(HintPanelTypes.ATTENTION);
			ConstructRobotTab.getHintPanel().setText(HintsConstructRobotTab.TAB_NOT_AVAILABLE.getHintText());
		}
		jComboBoxConstructionDefaultModuleType.setEnabled(enabled);
		jButtonVariateModuleProperties.setEnabled(enabled);
	}
	
	/**
	 * Enables or disables the table for general control.
	 * @param enabled, true for enabled.
	 */
	public static void setEnabledGeneralToolBar(boolean enabled){
		jButtonOpen.setEnabled(enabled);
		jButtonSave.setEnabled(enabled);
		jButtonStartNewRobot.setEnabled(enabled);
		jToggleButtonColorConnetors.setEnabled(enabled);		
	}
	
	public static void setVisibleFirstModuleOperations(boolean visible){
		jLabelFirstModule.setVisible(visible);
		jToolBarSupportedModularRobots.setVisible(visible);
		jToolBarFirstModuleTools.setVisible(visible);
		
	}
	
	/**
	 * Returns hint panel, which is used to display feedback to the user.
	 * @return hint panel, which is used to display feedback to the user.
	 */
	public static HintPanel getHintPanel(){
		return hintPanel;
	}

	/**
	 * Returns the button for initializing construction tool named as MOVE_MODULE_FROM_CON_TO_CON. 
	 * @return the button for initializing construction tool named as MOVE_MODULE_FROM_CON_TO_CON.
	 */
	public static javax.swing.JButton getJButtonJumpFromConnToConnector() {
		return jButtonJumpFromConnToConnector;
	}
	
	/**
	 * Adapts tab to ATRON modular robot
	 */
	public static void adaptTabToATRON(){
		jButtonVariateModuleProperties.setEnabled(false);
		jComboBoxStandardRotations.setModel(new javax.swing.DefaultComboBoxModel(ATRONStandardRotations.getAllInUserFriendlyFormat()));	
		jComboBoxNrConnectorsConstructionTool.setRenderer(ATRON_RENDERER);
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(SupportedModularRobots.ATRON_CONNECTORS));
		jComboBoxConstructionDefaultModuleType.setVisible(false);
	}
	
	
	/**
	 * Adapts tab to MTRAN modular robot
	 */
	public static void adaptTabToMTRAN(){
		jButtonVariateModuleProperties.setEnabled(true);
		jButtonOppositeRotation.setEnabled(false);
		jButtonMoveModule.setEnabled(false);
		jComboBoxStandardRotations.setModel(new javax.swing.DefaultComboBoxModel(MTRANStandardRotations.values()));
		jComboBoxNrConnectorsConstructionTool.setRenderer(MTRAN_RENDERER);
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(SupportedModularRobots.MTRAN_CONNECTORS));
		jComboBoxConstructionDefaultModuleType.setVisible(false);
	}
	
	/**
	 * The module type to use as default construction (new module). So far is relevant only for Odin.
	 */
	private static String defaultConstructionModuleType;
	
	/**
	 * Sets the default construction module type(new module).
	 * @param defaultConstructionModuleType, the module type to use as default construction (new module). So far is relevant only for Odin.
	 */
	public static void setDefaultConstructionModuleType(
			String defaultConstructionModuleType) {
		ConstructRobotTab.defaultConstructionModuleType = defaultConstructionModuleType;
	}

	/**
	 * Returns the default construction module type(new module).
	 * @return, the module type to use as default construction (new module). So far is relevant only for Odin.
	 */
	public static String getDefaultConstructionModuleType(){
		return defaultConstructionModuleType;
		
	}	
	
	
	/**
	 * Adapts tab for Odin modular robot
	 */
	public static void adaptTabToOdin(){
		//ConstructRobotTab.setEnabledRotationToolBar(false);// for Odin not yet relevant
		jButtonVariateModuleProperties.setEnabled(true);
		jButtonAvailableRotationsLoop.setEnabled(false);
		jComboBoxStandardRotations.setEnabled(false);
		jButtonOppositeRotation.setEnabled(true);
		
		Object selectedItem = jComboBoxConstructionDefaultModuleType.getSelectedItem();
		jComboBoxConstructionDefaultModuleType.setVisible(true);
		jComboBoxConstructionDefaultModuleType.setModel(new javax.swing.DefaultComboBoxModel(OdinTypesModules.getAllInUserFriendlyFormat(1)));
		jComboBoxConstructionDefaultModuleType.setSelectedItem(selectedItem);
		setDefaultConstructionModuleType(selectedItem.toString());
		jComboBoxNrConnectorsConstructionTool.setRenderer(ODIN_RENDERER);		
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(SupportedModularRobots.ODIN_BALL_CONNECTORS));
	}
	
	
	/**
	 * Adapts tab for CKBOTSTANDARD modular robot
	 */
	public static void adaptTabToCKBOTSTANDARD(){
		jButtonVariateModuleProperties.setEnabled(true);
		jComboBoxStandardRotations.setModel(new javax.swing.DefaultComboBoxModel( CKBotStandardRotations.values() ));
		jComboBoxNrConnectorsConstructionTool.setRenderer(CKBOT_STANDARD_RENDERER);
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(SupportedModularRobots.CKBOTSTANDARD_CONNECTORS));
		jComboBoxConstructionDefaultModuleType.setVisible(false);
	}
	
	/**
	 * Disables and enables Gui components in case when any of construction tools are selected(chosen).
	 */
	public static void adaptToconstructionToolSelected(){		
		jButtonAvailableRotationsLoop.setEnabled(false);
		jComboBoxStandardRotations.setEnabled(false);
		jButtonOppositeRotation.setEnabled(true);
		jButtonMoveModule.setEnabled(false);
		ConstructRobotTab.setVisibleFirstModuleOperations(false);
	}	
	

	/*Declaration of tab components*/
	private static javax.swing.JComboBox jComboBoxEntity,jComboBoxConstructionDefaultModuleType,
	jComboBoxStandardRotations,jComboBoxNrConnectorsConstructionTool;	

	private static javax.swing.JLabel jLabelFirstModule, jLabelAddNewModules,jLabelOperations, 
	                                  jLabelChangeModuleType;

	private static ButtonGroup buttonGroupModularRobots;	

	private static  javax.swing.AbstractButton radioButtonATRON,
	radioButtonMTRAN,radioButtonODIN, radioButtonCKBOTSTANDARD;

	private static javax.swing.JButton jButtonStartNewRobot,jButtonDelete, jButtonMoveModule,jButtonOppositeRotation,jButtonOnSelectedConnector,
	jButtonConnectAllModules,jButtonJumpFromConnToConnector,jButtonVariateModuleProperties,
	jButtonAvailableRotationsLoop,jButtonSave,jButtonOpen;

	private static javax.swing.JToolBar jToolBarGeneralControl,jToolBarChangeModuleType,jToolBarSupportedModularRobots,jToolBarGenericTools,
	jToolBarFirstModuleTools,jToolBarConstructionTools;

	private javax.swing.JToolBar.Separator jSeparator2,jSeparator3;
	
	private static javax.swing.JToggleButton jToggleButtonColorConnetors;

	private static HintPanel hintPanel;
}
