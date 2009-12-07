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
import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.HintsConstructRobotTab;
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
public class ConstructRobotTab extends ConstructionTabs implements ConstructRobotTabInter {

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
		jButtonStartNewRobot = new javax.swing.JButton();
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
		jButtonVariateModuleProperties = new javax.swing.JButton();
		jButtonAvailableRotationsLoop = new javax.swing.JButton();

		buttonGroupModularRobots = new ButtonGroup();
		radioButtonATRON =  new JRadioButton();
		radioButtonODIN =  new JRadioButton();
		radioButtonMTRAN =  new JRadioButton();
		radioButtonCKBOTSTANDARD =  new JRadioButton();

		jLabelOperations = new javax.swing.JLabel();	

		jComboBoxEntity = new javax.swing.JComboBox();
		jComboBoxStandardRotations = new javax.swing.JComboBox();
		jComboBoxNrConnectorsConstructionTool = new javax.swing.JComboBox();

		jSeparator2 = new javax.swing.JToolBar.Separator();	

		/*Description of components */		
		jButtonStartNewRobot.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Start_new_robot));
		jButtonStartNewRobot.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Start_constructing_new_robot));
		jButtonStartNewRobot.setFocusable(false);
		jButtonStartNewRobot.setPreferredSize(new Dimension(110,32));
		jButtonStartNewRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonStartNewRobot);
				ConstructRobotTabController.jButtonStartNewRobotActionPerformed();            	
			}
		});        
		gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_START;// position on the first line start
		gridBagConstraints.gridx = 0;// x goes from left to the right of the screen
		gridBagConstraints.gridy = 0;// y goes from top to the bottom of the screen
		gridBagConstraints.insets = new Insets(0,0,15,50);  //make some space on the right so that button moves to the left and at the bottom (not so crowded)
		super.jComponent.add(jButtonStartNewRobot,gridBagConstraints);

		/*Layout for SavaLoadJToolbar */
		gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_END;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0,0,15,-25);  

		jToolBarSaveLoad = initSaveLoadJToolbar();
		super.jComponent.add(jToolBarSaveLoad,gridBagConstraints);				

		radioButtonATRON.setFocusable(false);
		radioButtonATRON.setText(ModularRobotsNames.ATRON.toString());
		radioButtonATRON.setEnabled(false);
		radioButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jButtonGroupActionPerformed(radioButtonATRON);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0,0,0,0);//reset
		super.jComponent.add(radioButtonATRON,gridBagConstraints);
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
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;

		super.jComponent.add(radioButtonODIN,gridBagConstraints);
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
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;

		super.jComponent.add(radioButtonMTRAN,gridBagConstraints);
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
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;

		super.jComponent.add(radioButtonCKBOTSTANDARD,gridBagConstraints);
		buttonGroupModularRobots.add(radioButtonCKBOTSTANDARD);	
		jRadioButtons.add(radioButtonCKBOTSTANDARD);		

		jLabelOperations.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Operations_on_existing_modules));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;// reset grid width
		gridBagConstraints.insets = new Insets(0,0,10,0);  

		super.jComponent.add(jLabelOperations,gridBagConstraints);

		jToolBarRotationTools.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarRotationTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarRotationTools.setRollover(true);// the components inside are roll over
		jToolBarRotationTools.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Module_rotation_tools));
		jToolBarRotationTools.setPreferredSize(new Dimension(100,35));

		/*External layout of the toolbar in the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(0,3,10,18);  

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
		GroupLayout jToolBarRotationToolsLayout = new GroupLayout(jToolBarRotationTools);
		jToolBarRotationTools.setLayout(jToolBarRotationToolsLayout);

		jToolBarRotationToolsLayout.setHorizontalGroup(
				jToolBarRotationToolsLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButtonOppositeRotation,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jButtonAvailableRotationsLoop,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBoxStandardRotations,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
		);

		jToolBarRotationToolsLayout.setVerticalGroup(
				jToolBarRotationToolsLayout.createSequentialGroup()
				.addGroup(jToolBarRotationToolsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonOppositeRotation,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonAvailableRotationsLoop,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboBoxStandardRotations,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))						
		);

		super.jComponent.add(jToolBarRotationTools,gridBagConstraints);

		jComboBoxEntity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Module"/*, "Robot"*/ }));
		jComboBoxEntity.setPreferredSize(new java.awt.Dimension(65, 30));
		jComboBoxEntity.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxEntityActionPerformed(jComboBoxEntity);
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
		jToolBarGenericTools.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Generic_tools));
		jToolBarGenericTools.setPreferredSize(new Dimension(195,30));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.insets = new Insets(8,3,10,18);  

		jButtonMove.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Move));	
		jButtonMove.setIcon(TabsIcons.MOVE.getImageIcon());
		jButtonMove.setSelectedIcon(TabsIcons.MOVE.getImageIcon());
		jButtonMove.setRolloverIcon(TabsIcons.MOVE_ROLLOVER.getImageIcon());		
		jButtonMove.setDisabledIcon(TabsIcons.MOVE_DISABLED.getImageIcon());		
		jButtonMove.setFocusable(false); 
		jButtonMove.setEnabled(false);
		jButtonMove.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		jButtonMove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonMove);
				ConstructRobotTabController.jButtonMoveActionPerformed();
			}
		});

		jToolBarGenericTools.add(jButtonMove);

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

		jButtonColorConnetors.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Color_Connectors));
		jButtonColorConnetors.setIcon(TabsIcons.COLOR_CONNECTORS.getImageIcon());
		jButtonColorConnetors.setSelectedIcon(TabsIcons.COLOR_CONNECTORS.getImageIcon());
		jButtonColorConnetors.setRolloverIcon(TabsIcons.COLOR_CONNECTORS_ROLLOVER.getImageIcon());
		jButtonColorConnetors.setDisabledIcon(TabsIcons.COLOR_CONNECTORS_DISABLED.getImageIcon());		
		jButtonColorConnetors.setFocusable(false);
		jButtonColorConnetors.setEnabled(false);
		jButtonColorConnetors.setPreferredSize(FramesInter.BUTTON_DIMENSION);
		jButtonColorConnetors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonColorConnetors);
				ConstructRobotTabController.jButtonColorConnectorsActionPerformed();
			}
		});

		jToolBarGenericTools.add(jButtonColorConnetors);		

		super.jComponent.add(jToolBarGenericTools,gridBagConstraints);

		jLabelAddNewModules = new javax.swing.JLabel();		
		jLabelAddNewModules.setText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Add_new_modules));	
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.insets = new Insets(0,0,10,10);  //make some space at the bottom and right
		super.jComponent.add(jLabelAddNewModules,gridBagConstraints);

		jToolBarConstructionTools.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarConstructionTools.setFloatable(false);//user can not make the tool bar to float
		jToolBarConstructionTools.setRollover(true);// the buttons inside are roll over
		jToolBarConstructionTools.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.Construction_tools));
		jToolBarConstructionTools.setPreferredSize(new Dimension(195,35));		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;		
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(0,0,10,18);  

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

		jComboBoxNrConnectorsConstructionTool.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(TabsComponentsText.On_chosen_connector_number));
		jComboBoxNrConnectorsConstructionTool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		jComboBoxNrConnectorsConstructionTool.setPreferredSize(new java.awt.Dimension(60, 26));
		jComboBoxNrConnectorsConstructionTool.setEnabled(false);
		jComboBoxNrConnectorsConstructionTool.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxNrConnectorsConstructionToolActionPerformed(jComboBoxNrConnectorsConstructionTool);
			}
		});	
		
		//TODO
		//ComboBoxRenderer renderer = new ComboBoxRenderer();
		//renderer.setPreferredSize(new Dimension(200, 130));

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

		jSeparator2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jSeparator2.setPreferredSize(new Dimension(6,30));

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
										.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
												.addComponent(jSeparator2,GroupLayout.PREFERRED_SIZE, 6,
														GroupLayout.PREFERRED_SIZE)
														.addComponent(jButtonVariateModuleProperties,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
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
												.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
														.addComponent(jSeparator2,GroupLayout.PREFERRED_SIZE, 28,
																GroupLayout.PREFERRED_SIZE)
																.addComponent(jButtonVariateModuleProperties,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))

		);
		super.jComponent.add(jToolBarConstructionTools,gridBagConstraints);

		/*Display for hints. Feedback to the user.*/
		hintPanel =  initHintPanel(430,HINT_PANEL_HEIGHT,HintsConstructRobotTab.DEFAULT.getHintText());			
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 5;
		super.jComponent.add(hintPanel,gridBagConstraints);

	}	

	/*Getters and setters*/

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
		jButtonMove.setEnabled(enabled);
		jButtonColorConnetors.setEnabled(enabled);
		jButtonVariateModuleProperties.setEnabled(enabled);
	}

	/**
	 * Enables and disables all tool bars in the tab.
	 * @param enable,true for tool bars to be enabled. 
	 */
	public static void setEnabledAllToolBars(boolean enabled){
		setEnabledRotationToolBar(enabled);
		setEnabledGenericToolBar(enabled);
		setEnabledConstructionToolsToolBar(enabled);
	};

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
	}

	/**
	 * Enables and disables tool bar containing tools for applying rotation to module(s).
	 * @param enable, true for tool bar to be disabled.
	 */
	public static void setEnabledRotationToolBar(boolean enable){
		jButtonOppositeRotation.setEnabled(enable);
		jComboBoxStandardRotations.setEnabled(enable);
		jButtonAvailableRotationsLoop.setEnabled(enable);
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
		getJButtonStartNewRobot().setEnabled(enabled);
		setRadioButtonsEnabled(enabled);		
		setEnabledRotationToolBar(enabled);
		setEnabledGenericToolBar(enabled);		
		setEnabledConstructionToolsToolBar(enabled);
		for (int button=0; button<jToolBarSaveLoad.getComponentCount();button++ ){
			JButton jButton = (JButton)jToolBarSaveLoad.getComponent(button);
			jButton.setEnabled(enabled);
		}
		getHintPanel().setType(HintPanelTypes.ATTENTION);
		ConstructRobotTab.getHintPanel().setText(HintsConstructRobotTab.TAB_NOT_AVAILABLE.getHintText());
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

	private static javax.swing.JLabel jLabelAddNewModules,jLabelOperations;

	private static ButtonGroup buttonGroupModularRobots;	

	private static  javax.swing.AbstractButton radioButtonATRON,
	radioButtonMTRAN,radioButtonODIN, radioButtonCKBOTSTANDARD;

	private static javax.swing.JButton jButtonStartNewRobot,jButtonDelete,
	jButtonMove,jButtonColorConnetors,jButtonOppositeRotation,jButtonOnSelectedConnector,
	jButtonConnectAllModules,jButtonJumpFromConnToConnector,jButtonVariateModuleProperties,
	jButtonAvailableRotationsLoop;

	private static javax.swing.JToolBar jToolBarGenericTools,
	jToolBarRotationTools,jToolBarConstructionTools,jToolBarSaveLoad;

	private javax.swing.JToolBar.Separator jSeparator2;

	private static HintPanel hintPanel;
}
