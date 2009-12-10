package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.Renderer;

import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.HintsConstructRobotTab;
import ussr.aGui.enumerations.ModularRobotsNames;
import ussr.aGui.enumerations.TabsComponentsText;
import ussr.aGui.enumerations.TabsIcons;
import ussr.aGui.helpers.hintPanel.HintPanel;
import ussr.aGui.helpers.hintPanel.HintPanelTypes;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.helpers.StringProcessingHelper;


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

		jSeparator2 = new javax.swing.JToolBar.Separator();
		jSeparator3 = new javax.swing.JToolBar.Separator();
		
		/*Description of components */	
		jToolBarGeneralControl.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarGeneralControl.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.General_control));
		jToolBarGeneralControl.setFloatable(false);//user can not make the tool bar to float
		jToolBarGeneralControl.setRollover(true);// the components inside are roll over
		jToolBarGeneralControl.setPreferredSize(new Dimension(375,FramesInter.HORIZONTAL_TOOLBAR_HEIGHT));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;// position on the first line start
		gridBagConstraints.gridx = 0;// x goes from left to the right of the screen
		gridBagConstraints.gridy = 0;// y goes from top to the bottom of the screen
		gridBagConstraints.gridwidth = 3;		
		
		jToolBarGeneralControl.add(jButtonOpen);		
		jToolBarGeneralControl.add(jButtonSave);
		jSeparator3.setPreferredSize(new Dimension(6,30));
		jToolBarGeneralControl.add(jSeparator3);
		
		jButtonStartNewRobot.setIcon(TabsIcons.NEW_ROBOT.getImageIcon());
		jButtonStartNewRobot.setSelectedIcon(TabsIcons.NEW_ROBOT.getImageIcon());
		jButtonStartNewRobot.setRolloverIcon(TabsIcons.NEW_ROBOT_ROLLOVER.getImageIcon());
		jButtonStartNewRobot.setDisabledIcon(TabsIcons.NEW_ROBOT_DISABLED.getImageIcon());
		jButtonStartNewRobot.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Start_constructing_new_robot));
		jButtonStartNewRobot.setFocusable(false);
		jButtonStartNewRobot.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonStartNewRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonStartNewRobotActionPerformed();            	
			}
		});
		jToolBarGeneralControl.add(jButtonStartNewRobot);
		
		jToggleButtonColorConnetors.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Color_module_connectors));
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
		
		jLabelFirstModule.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Add_and_adjust_first_module));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(10,0,0,10);  
		super.jComponent.add(jLabelFirstModule,gridBagConstraints);
		
		
		//jToolBarSupportedModularRobots.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarSupportedModularRobots.setFloatable(false);//user can not make the tool bar to float
		jToolBarSupportedModularRobots.setRollover(true);// the components inside are roll over
		jToolBarSupportedModularRobots.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Choose_supported_modular_robot));
		jToolBarSupportedModularRobots.setPreferredSize(new Dimension(275,FramesInter.HORIZONTAL_TOOLBAR_HEIGHT));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0,0,0,0);  		
		
		radioButtonATRON.setFocusable(false);
		radioButtonATRON.setText(ModularRobotsNames.ATRON.toString());
		radioButtonATRON.setEnabled(false);
		radioButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonATRON);
			}
		});
		jToolBarSupportedModularRobots.add(radioButtonATRON);
		buttonGroupModularRobots.add(radioButtonATRON);
		jRadioButtons.add(radioButtonATRON);

		radioButtonODIN.setText(ModularRobotsNames.Odin.toString());
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

		radioButtonMTRAN.setText(ModularRobotsNames.MTRAN.toString());
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

		radioButtonCKBOTSTANDARD.setText(ModularRobotsNames.CKBotStandard.toString());
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
		jToolBarFirstModuleTools.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Adjust_module_properties));
		jToolBarFirstModuleTools.setPreferredSize(new Dimension(100,35));

		/*External layout of the toolbar in the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth =2;
		gridBagConstraints.insets = new Insets(0,0,0,0); 
		
		jButtonMoveModule.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Move_module));	
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

		jButtonAvailableRotationsLoop.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Available_rotations));
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

		jComboBoxStandardRotations.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Standard_rotations));
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
		
		jLabelAddNewModules.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Add_new_modules));	
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(20,0,0,0); 
		super.jComponent.add(jLabelAddNewModules,gridBagConstraints);

		jToolBarConstructionTools.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarConstructionTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarConstructionTools.setRollover(true);// the buttons inside are roll over
		jToolBarConstructionTools.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Addition_of_new_modules));
		jToolBarConstructionTools.setPreferredSize(new Dimension(195,35));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(20,0,0,0); 

		jButtonOnSelectedConnector.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.On_selected_connector));
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

		jButtonConnectAllModules.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.On_all_connectors));
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

		jButtonJumpFromConnToConnector.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Jump_from_connector_to_connector));		
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

		jComboBoxNrConnectorsConstructionTool.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.On_chosen_connector_number));
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
		
		jLabelChangeModuleType.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Change_module_type));	
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(5,0,0,0); 
		super.jComponent.add(jLabelChangeModuleType,gridBagConstraints);
		
	
		jToolBarChangeModuleType.setBorder(FramesInter.TOOLBAR_BORDER);
		jToolBarChangeModuleType.setFloatable(false);//user can not make the tool bar to float
		jToolBarChangeModuleType.setRollover(true);// the buttons inside are roll over
		jToolBarChangeModuleType.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Operations_for_changing_module_type));
		jToolBarChangeModuleType.setPreferredSize(new Dimension(195,30));
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth =2;
		
		jButtonVariateModuleProperties.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Vary_module_type_or_properties));
		jButtonVariateModuleProperties.setIcon(TabsIcons.VARY_PROPERTIES.getImageIcon());
		jButtonVariateModuleProperties.setSelectedIcon(TabsIcons.VARY_PROPERTIES.getImageIcon());
		jButtonVariateModuleProperties.setRolloverIcon(TabsIcons.VARY_PROPERTIES_ROLLOVER.getImageIcon());		
		jButtonVariateModuleProperties.setDisabledIcon(TabsIcons.VARY_PROPERTIES_DISABLED.getImageIcon());		
		jButtonVariateModuleProperties.setFocusable(false);
		jButtonVariateModuleProperties.setEnabled(false);
		jButtonVariateModuleProperties.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonVariateModuleProperties.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonVariateModuleProperties);
				ConstructRobotTabController.jButtonVariateModulePropertiesActionPerformed();
			}
		});
		
		jToolBarChangeModuleType.add(jButtonVariateModuleProperties);
		
		super.jComponent.add(jToolBarChangeModuleType,gridBagConstraints);	
		
		jLabelOperations.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Operations_on_existing));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 1;
		//gridBagConstraints.insets = new Insets(10,0,0,0);  

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
		jToolBarGenericTools.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Generic_tools));
		jToolBarGenericTools.setPreferredSize(new Dimension(125,30));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;		
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 2;
		//gridBagConstraints.insets = new Insets(8,3,10,18);  

		jButtonDelete.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Delete_or_Remove));
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
		
		jButtonOppositeRotation.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Rotate_opposite));		
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
	public static javax.swing.JButton getJButtonMoveModule() {
		return jButtonMoveModule;
	}
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
	public static javax.swing.JButton getJButtonAvailableRotationsLoop() {
		return jButtonAvailableRotationsLoop;
	}

	/**
	 * Returns the button for initialization of opposite rotation tool. 
	 * @return the button for initialization of opposite rotation tool.
	 */
	public static javax.swing.JButton getJButtonOppositeRotation() {
		return jButtonOppositeRotation;
	}

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

	/*Declaration of tab components*/
	private static javax.swing.JComboBox jComboBoxEntity,
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
