package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import ussr.aGui.helpers.hintPanel.HintPanel;
import ussr.aGui.helpers.hintPanel.HintPanelInter;
import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignBehaviorsTab extends ConstructionTabs implements AssignBehaviorsTabInter {
	
	/**
	 * Used to display hints to the user. Feedback to the user.
	 */
	private static HintPanel hintPanel;
	
	/**
	 * 
	 */
	//private static ArrayList<AbstractButton> jRadioButtonsLabelledEntities =  new ArrayList<AbstractButton>() ;	

	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 150, J_LIST_HEIGHT = 180;


	private final int jToolBar1Width = 125;

	/**
	 * The constrains of grid bag layout used during design of both tabs.
	 */
	public  GridBagConstraints gridBagConstraints = new GridBagConstraints();
	
	/**
	 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".
	 * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane from the top. 
	 * @param tabTitle, the title of the tab.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public AssignBehaviorsTab(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
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

		/*Instantiation of components*/
		jToolBarFilterForModularRobot = new javax.swing.JToolBar();
		
		jListAvailableControllers = new javax.swing.JList();

		jScrollPaneAvailableControllers = new javax.swing.JScrollPane();

		jPanelAssignBehaviors = new javax.swing.JPanel();		
		
		jCheckBoxShowLabelControl = new javax.swing.JCheckBox();
		
		radionButtonATRON =  new JRadioButton();
		radioButtonODIN =  new JRadioButton();
		radioButtonMTRAN =  new JRadioButton();
		radionButtonCKBOTSTANDARD =  new JRadioButton();

		/*Description of components*/		
		gridBagConstraints.fill = GridBagConstraints.FIRST_LINE_END;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		jToolBarSaveLoad = initSaveLoadJToolbar();
		super.jComponent.add(jToolBarSaveLoad,gridBagConstraints);	
		
		jToolBarFilterForModularRobot.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Filter out for:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));		
		jToolBarFilterForModularRobot.setFloatable(false);//user can not make the tool bar to float
		jToolBarFilterForModularRobot.setRollover(true);// the buttons inside are roll over
		jToolBarFilterForModularRobot.setToolTipText("Filter out for");
		jToolBarFilterForModularRobot.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT-50));
		jToolBarFilterForModularRobot.setOrientation(JToolBar.VERTICAL);		

		final ButtonGroup buttonGroup = new ButtonGroup() ;
	
		radionButtonATRON.setText(ModularRobotsNames.ATRON.toString());	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON);
			}
		});

		jToolBarFilterForModularRobot.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);

		radioButtonODIN.setText(ModularRobotsNames.Odin.toString());
		radioButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonODIN);
			}
		});

		jToolBarFilterForModularRobot.add(radioButtonODIN);
		buttonGroup.add(radioButtonODIN);

		radioButtonMTRAN.setText(ModularRobotsNames.MTRAN.toString());
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN);
			}
		});

		jToolBarFilterForModularRobot.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD.setText(ModularRobotsNames.CKBotStandard.toString());
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD);
			}
		});

		jToolBarFilterForModularRobot.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);			
		
		jListAvailableControllers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jListAvailableControllers.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH+60, J_LIST_HEIGHT));	
		jListAvailableControllers.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				AssignBehaviorsTabController.jListAvailableControllersMouseReleased( jListAvailableControllers);
			}
		});		
		jScrollPaneAvailableControllers.setViewportView(jListAvailableControllers);
		jScrollPaneAvailableControllers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Available behaviors", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));

			
		/*Internal layout of the panel*/
		GroupLayout jPanelAssignBehaviorsLayout = new GroupLayout(jPanelAssignBehaviors);
		jPanelAssignBehaviors.setLayout(jPanelAssignBehaviorsLayout);

		jPanelAssignBehaviorsLayout.setAutoCreateGaps(true);
		jPanelAssignBehaviorsLayout.setHorizontalGroup(
				jPanelAssignBehaviorsLayout.createSequentialGroup()			
				.addComponent(jToolBarFilterForModularRobot,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)						
						.addComponent(jScrollPaneAvailableControllers,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)							
		);

		jPanelAssignBehaviorsLayout.setVerticalGroup(
				jPanelAssignBehaviorsLayout.createSequentialGroup()
				.addGroup(jPanelAssignBehaviorsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jToolBarFilterForModularRobot,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPaneAvailableControllers,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))												
		);
		/*External layout of the panel*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		
		super.jComponent.add(jPanelAssignBehaviors,gridBagConstraints);		

		/*jCheckBoxShowLabelControl.setText("Show labeling control");
		jCheckBoxShowLabelControl.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jCheckBoxShowLabelControlActionPerformed(jCheckBoxShowLabelControl);
			}
		});
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		super.jComponent.add(jCheckBoxShowLabelControl,gridBagConstraints);	*/

		/*GridBagConstraints gridBagConstraintsLabelingPanel = new GridBagConstraints();
		jPanelLabeling.setPreferredSize(new Dimension(200,160));
		jPanelLabeling.setVisible(false);//initially invisible

		External layout of labelingPanel inside main panel (jComponent)
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;	

		GridBagConstraints gridBagConstraintsEntitiesToLabel = new GridBagConstraints();
		TitledBorder displayTitle1;
		displayTitle1 = BorderFactory.createTitledBorder("Choose entity");
		displayTitle1.setTitleJustification(TitledBorder.CENTER);		
		jPanelEntitiesToLabel.setBorder(displayTitle1);
		jPanelEntitiesToLabel.setPreferredSize(new Dimension(100,130));
		gridBagConstraintsLabelingPanel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsLabelingPanel.gridx = 0;
		gridBagConstraintsLabelingPanel.gridy = 0;

		//jToolBarEntitiesForLabeling.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarEntitiesForLabeling.setFloatable(false);//user can not make the tool bar to float
		jToolBarEntitiesForLabeling.setRollover(true);// the buttons inside are roll over
		jToolBarEntitiesForLabeling.setToolTipText("Entities for labeling");
		jToolBarEntitiesForLabeling.setPreferredSize(new Dimension(jToolBar1Width-20,J_LIST_HEIGHT-70));
		jToolBarEntitiesForLabeling.setOrientation(JToolBar.VERTICAL);		 
		gridBagConstraintsEntitiesToLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsEntitiesToLabel.gridx = 0;
		gridBagConstraintsEntitiesToLabel.gridy = 0;		


		final ButtonGroup buttonGroupEntities = new ButtonGroup() ;

		radioButtonModule =  new JRadioButton();		
		radioButtonModule.setText(EntitiesForLabelingText.Module.toString());	
		radioButtonModule.setFocusable(true);// direct the user to what should be done first
		radioButtonModule.setSelected(false);
		radioButtonModule.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonModule);
			}
		});			

		jToolBarEntitiesForLabeling.add(radioButtonModule);
		buttonGroupEntities.add(radioButtonModule);
		jRadioButtonsLabelledEntities.add(radioButtonModule);

		radionButtonConnector =  new JRadioButton();
		radionButtonConnector.setText(EntitiesForLabelingText.Connector.toString());
		radionButtonConnector.setSelected(false);
		radionButtonConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radionButtonConnector);
			}
		});

		jToolBarEntitiesForLabeling.add(radionButtonConnector);
		buttonGroupEntities.add(radionButtonConnector);
		jRadioButtonsLabelledEntities.add(radionButtonConnector);

		radioButtonSensors =  new JRadioButton();
		radioButtonSensors.setText(EntitiesForLabelingText.Sensors.toString());
		radioButtonSensors.setSelected(false);
		radioButtonSensors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonSensors);
			}
		});

		jToolBarEntitiesForLabeling.add(radioButtonSensors);
		buttonGroupEntities.add(radioButtonSensors);
		jRadioButtonsLabelledEntities.add(radioButtonSensors);

		jPanelEntitiesToLabel.add(jToolBarEntitiesForLabeling,gridBagConstraintsEntitiesToLabel);

		//jToolBarTypesSensors.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarTypesSensors.setFloatable(false);//user can not make the tool bar to float
		jToolBarTypesSensors.setRollover(true);// the buttons inside are roll over
		jToolBarTypesSensors.setToolTipText("Entities for labeling");
		jToolBarTypesSensors.setVisible(false);
		jToolBarTypesSensors.setPreferredSize(new Dimension(jToolBar1Width-20,J_LIST_HEIGHT-70));
		jToolBarTypesSensors.setOrientation(JToolBar.VERTICAL);
		gridBagConstraintsEntitiesToLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsEntitiesToLabel.gridx = 0;
		gridBagConstraintsEntitiesToLabel.gridy = 1;
		gridBagConstraintsEntitiesToLabel.insets = new Insets (0,10,0,0);

		radioButtonProximitySensor =  new JRadioButton();		
		radioButtonProximitySensor.setText(EntitiesForLabelingText.Proximity.toString());	
		radioButtonProximitySensor.setSelected(false);
		radioButtonProximitySensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonProximitySensor);
			}
		});
		jToolBarTypesSensors.add(radioButtonProximitySensor);
		buttonGroupEntities.add(radioButtonProximitySensor);
		jRadioButtonsLabelledEntities.add(radioButtonProximitySensor);

		jPanelEntitiesToLabel.add(jToolBarTypesSensors,gridBagConstraintsEntitiesToLabel);


		jTableLabels.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
						//Initially empty rows
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},						
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
				},
				new String [] {
						"Module labels"//Column name
				}
		));
		jTableLabels.setCellSelectionEnabled(true);
		jTableLabels.setDragEnabled(false);
		jTableLabels.getTableHeader().setReorderingAllowed(false);
		jTableLabels.setPreferredSize(new Dimension(150,150));
		jScrollPaneTableCurrentLabels.setViewportView(jTableLabels);
		jTableLabels.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jScrollPaneTableCurrentLabels.setPreferredSize(new Dimension(150,130));

		//jToolBarControlOfLabels.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarControlOfLabels.setFloatable(false);//user can not make the tool bar to float
		jToolBarControlOfLabels.setRollover(true);// the buttons inside are roll over
		jToolBarControlOfLabels.setToolTipText("Control of labels");
		jToolBarControlOfLabels.setPreferredSize(new Dimension(40,80));
		jToolBarControlOfLabels.setOrientation(JToolBar.VERTICAL);		

		jButtonReadLabels.setToolTipText("Read Labels");		
		jButtonReadLabels.setIcon(TabsIcons.READ_LABELS.getImageIcon());
		jButtonReadLabels.setRolloverIcon(TabsIcons.READ_LABELS_ROLLOVER.getImageIcon());		
		jButtonReadLabels.setDisabledIcon(TabsIcons.READ_LABELS_DISABLED.getImageIcon());
		jButtonReadLabels.setFocusable(false); 
		jButtonReadLabels.setEnabled(false);	
		jButtonReadLabels.setPreferredSize(FramesInter.BUTTON_DIMENSION);	
		jButtonReadLabels.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonReadLabels);
				AssignBehaviorsTabController.jButtonReadLabelsActionPerformed();
			}
		});	
		jToolBarControlOfLabels.add(jButtonReadLabels);	

		jButtonAssignLabels.setToolTipText("Assign Labels");		
		jButtonAssignLabels.setIcon(TabsIcons.ASSIGN_LABELS.getImageIcon());
		jButtonAssignLabels.setRolloverIcon(TabsIcons.ASSIGN_LABELS_ROLLOVER.getImageIcon());		
		jButtonAssignLabels.setDisabledIcon(TabsIcons.ASSIGN_LABELS_DISABLED.getImageIcon());
		jButtonAssignLabels.setFocusable(false); 
		jButtonAssignLabels.setEnabled(false);	
		jButtonAssignLabels.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonAssignLabels);
				AssignBehaviorsTabController.jButtonAssignLabelsActionPerformed();
			}
		});			

		jToolBarControlOfLabels.add(jButtonAssignLabels);			


		GroupLayout labelingPanelLayoutLayout = new GroupLayout(jPanelLabeling);
		jPanelLabeling.setLayout(labelingPanelLayoutLayout);

		labelingPanelLayoutLayout.setAutoCreateGaps(true);
		labelingPanelLayoutLayout.setHorizontalGroup(
				labelingPanelLayoutLayout.createSequentialGroup()
				//Forces preferred side of component
				.addGap(20)
				.addComponent(jPanelEntitiesToLabel,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addComponent(jScrollPaneTableCurrentLabels,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addGap(20)
								.addComponent(jToolBarControlOfLabels,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)	
		);

		labelingPanelLayoutLayout.setVerticalGroup(
				labelingPanelLayoutLayout.createSequentialGroup()
				.addGroup(labelingPanelLayoutLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jPanelEntitiesToLabel,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPaneTableCurrentLabels,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jToolBarControlOfLabels,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))				
		);

		super.jComponent.add(jPanelLabeling,gridBagConstraints);*/

		
		/*Display for hints. Feedback to the user.*/
		hintPanel = initHintPanel(400,HINT_PANEL_HEIGHT,HintPanelInter.builInHintsAssignBehaviorTab[0]);		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		super.jComponent.add(hintPanel,gridBagConstraints);	

	}

/*Getters and Setter*/
	/**
	 * Returns the list for displaying available controllers for user to choose from.
	 * @return the list for displaying available controllers for user to choose from.
	 */
	public static javax.swing.JList getJListAvailableControllers() {
		return jListAvailableControllers;
	}
	
	/**
	 * Returns hint panel, which is used to display feedback to the user.
	 * @return hint panel, which is used to display feedback to the user.
	 */
	public static HintPanel getHintPanel(){
		return hintPanel;
	}

	
	/**
	 * Enables or disables the tab;
	 * @param enabled, true if the tab is enabled.
	 */
	public static void setTabEnabled (boolean enabled){
//MORE HERE
		for (int button=0; button<jToolBarSaveLoad.getComponentCount();button++ ){
			JButton jButton = (JButton)jToolBarSaveLoad.getComponent(button);
			jButton.setEnabled(enabled);
		}
	}
	
	public static javax.swing.AbstractButton getRadionButtonCKBOTSTANDARD() {
		return radionButtonCKBOTSTANDARD;
	}

	public static javax.swing.AbstractButton getRadioButtonODIN() {
		return radioButtonODIN;
	}

	public static javax.swing.AbstractButton getRadioButtonMTRAN() {
		return radioButtonMTRAN;
	}

	public static javax.swing.AbstractButton getRadionButtonATRON() {
		return radionButtonATRON;
	}

	
	
	
	
	/*Declaration of components*/
	private static javax.swing.JList jListAvailableControllers;	
	private javax.swing.JPanel jPanelAssignBehaviors;

	private javax.swing.JScrollPane jScrollPaneAvailableControllers;
	
	private static  javax.swing.AbstractButton radionButtonATRON,radioButtonMTRAN,
	radioButtonODIN,radionButtonCKBOTSTANDARD;

	private static javax.swing.JToolBar jToolBarFilterForModularRobot,jToolBarSaveLoad;

	private javax.swing.JCheckBox jCheckBoxShowLabelControl;
}
