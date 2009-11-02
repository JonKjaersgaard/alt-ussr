package ussr.aGui.tabs.views.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import ussr.aGui.FramesInter;
import ussr.aGui.GuiFrames;
import ussr.aGui.MainFrameInter;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignBehaviorsTab extends ConstructionTabs implements AssignBehaviorsTabInter {


	
	private static HintPanel hintPanel;
	
	/**
	 * 
	 */
	private static ArrayList<AbstractButton> jRadioButtonsLabelledEntities =  new ArrayList<AbstractButton>() ;	


	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 150, J_LIST_HEIGHT = 195;


	private final int jToolBar1Width = 125;

	/**
	 * The constrains of grid bag layout used during design of both tabs.
	 */
	public  GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".
	 * TODO
	 * @param firstTabbedPane,
	 * @param tabTitle, the title of the tab
	 * @param jmeSimulation, the physical simulation.
	 * @param imageIconDirectory
	 */
	public AssignBehaviorsTab(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);	

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		this.jComponent = new javax.swing.JPanel(new GridBagLayout());

		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {

		/*Instantiation of components*/
		jToolBarFilterForModularRobot = new javax.swing.JToolBar();
		jToolBarEntitiesForLabeling = new javax.swing.JToolBar();
		jToolBarTypesSensors = new javax.swing.JToolBar();
		jToolBarControlOfLabels = new javax.swing.JToolBar();

		jLabel1000 = new javax.swing.JLabel();			
		jLabel10005 = new javax.swing.JLabel();	

		jListAvailableControllers = new javax.swing.JList();

		jScrollPaneAvailableControllers = new javax.swing.JScrollPane();
		jScrollPaneTableCurrentLabels = new javax.swing.JScrollPane();

		jPanelLabeling = new javax.swing.JPanel();
		jPanelEntitiesToLabel =  new javax.swing.JPanel(new GridBagLayout());		

		jTableCurrentLabels = new javax.swing.JTable();

		jButtonReadLabels = new javax.swing.JButton();
		jButtonAssignLabels = new javax.swing.JButton();
		jCheckBoxShowLabelControl = new javax.swing.JCheckBox();		

		jComboBox1 = new JComboBox();

		/*Description of components*/
		jToolBarFilterForModularRobot.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Filter out for:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));		
		jToolBarFilterForModularRobot.setFloatable(false);//user can not make the tool bar to float
		jToolBarFilterForModularRobot.setRollover(true);// the buttons inside are roll over
		jToolBarFilterForModularRobot.setToolTipText("Filter out for");
		jToolBarFilterForModularRobot.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT-70));
		jToolBarFilterForModularRobot.setOrientation(JToolBar.VERTICAL);		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0,0,0,5); // some space on the right

		final ButtonGroup buttonGroup = new ButtonGroup() ;

		radionButtonATRON =  new JRadioButton();		
		radionButtonATRON.setText(ModularRobotsNames.ATRON.toString());	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});

		jToolBarFilterForModularRobot.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);

		radioButtonODIN =  new JRadioButton();
		radioButtonODIN.setText(ModularRobotsNames.Odin.toString());
		radioButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonODIN,jmeSimulation);
			}
		});

		jToolBarFilterForModularRobot.add(radioButtonODIN);
		buttonGroup.add(radioButtonODIN);

		radioButtonMTRAN =  new JRadioButton();
		radioButtonMTRAN.setText(ModularRobotsNames.MTRAN.toString());
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});

		jToolBarFilterForModularRobot.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD =  new JRadioButton();
		radionButtonCKBOTSTANDARD.setText(ModularRobotsNames.CKBotStandard.toString());
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD,jmeSimulation);
			}
		});

		jToolBarFilterForModularRobot.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);			

		super.jComponent.add(jToolBarFilterForModularRobot,gridBagConstraints);			


		jListAvailableControllers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jListAvailableControllers.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH+60, J_LIST_HEIGHT));	
		jListAvailableControllers.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				AssignBehaviorsTabController.jList1MouseReleased( jListAvailableControllers,jmeSimulation);
			}
		});
		jScrollPaneAvailableControllers.setViewportView(jListAvailableControllers);
		jScrollPaneAvailableControllers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Available behaviors", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jScrollPaneAvailableControllers,gridBagConstraints);		

		jCheckBoxShowLabelControl.setText("Show labeling control");
		jCheckBoxShowLabelControl.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jCheckBoxShowLabelControlActionPerformed(jCheckBoxShowLabelControl);
			}
		});
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		super.jComponent.add(jCheckBoxShowLabelControl,gridBagConstraints);	

		GridBagConstraints gridBagConstraintsLabelingPanel = new GridBagConstraints();
		TitledBorder displayTitle;
		displayTitle = BorderFactory.createTitledBorder("Labeling of entities");
		displayTitle.setTitleJustification(TitledBorder.CENTER);
		jPanelLabeling.setBorder(displayTitle);
		jPanelLabeling.setPreferredSize(new Dimension(200,180));
		jPanelLabeling.setVisible(false);//initially invisible

		/*External layout of labelingPanel inside main panel (jComponent)*/
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
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonModule,jmeSimulation);
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
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radionButtonConnector,jmeSimulation);
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
				//jToolBar3.setVisible(false);
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonSensors,jmeSimulation);
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
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonProximitySensor,jmeSimulation);
			}
		});
		jToolBarTypesSensors.add(radioButtonProximitySensor);
		buttonGroupEntities.add(radioButtonProximitySensor);
		jRadioButtonsLabelledEntities.add(radioButtonProximitySensor);

		jPanelEntitiesToLabel.add(jToolBarTypesSensors,gridBagConstraintsEntitiesToLabel);


		jTableCurrentLabels.setModel(new javax.swing.table.DefaultTableModel(
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
		jTableCurrentLabels.setCellSelectionEnabled(true);
		jTableCurrentLabels.setDragEnabled(false);
		jTableCurrentLabels.getTableHeader().setReorderingAllowed(false);
		jTableCurrentLabels.setPreferredSize(new Dimension(150,150));
		jScrollPaneTableCurrentLabels.setViewportView(jTableCurrentLabels);
		jTableCurrentLabels.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jScrollPaneTableCurrentLabels.setPreferredSize(new Dimension(150,150));

		//jToolBarControlOfLabels.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarControlOfLabels.setFloatable(false);//user can not make the tool bar to float
		jToolBarControlOfLabels.setRollover(true);// the buttons inside are roll over
		jToolBarControlOfLabels.setToolTipText("Control of labels");
		jToolBarControlOfLabels.setPreferredSize(new Dimension(40,80));
		jToolBarControlOfLabels.setOrientation(JToolBar.VERTICAL);		

		jButtonReadLabels.setToolTipText("Read Labels");		
		jButtonReadLabels.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + READ_LABELS));
		jButtonReadLabels.setDisabledIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.NO_ENTRANCE));
		jButtonReadLabels.setFocusable(false); 
		jButtonReadLabels.setEnabled(false);	
		jButtonReadLabels.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT-3));	
		jButtonReadLabels.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setSelectionDeselection(jButtonReadLabels);
				AssignBehaviorsTabController.jButtonReadLabelsActionPerformed();
			}
		});	
		jToolBarControlOfLabels.add(jButtonReadLabels);	

		jButtonAssignLabels.setToolTipText("Assign Labels");		
		jButtonAssignLabels.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ASSIGN_LABELS));
		jButtonAssignLabels.setDisabledIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.NO_ENTRANCE));
		jButtonAssignLabels.setFocusable(false); 
		jButtonAssignLabels.setEnabled(false);	
		jButtonAssignLabels.setPreferredSize(new java.awt.Dimension(FramesInter.BUTTONS_WIDTH, FramesInter.COMMON_HEIGHT-3));	
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

		super.jComponent.add(jPanelLabeling,gridBagConstraints);

		
		/*Display for hints. Feedback to the user.*/
		hintPanel = initHintPanel(400,100,HintPanelInter.builInHintsAssignBehaviorTab[0]);		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		super.jComponent.add(hintPanel,gridBagConstraints);	

	}


	/**
	 * Returns the panel containing control of labels.
	 * @return panel,the panel containing control of labels.
	 */
	public static javax.swing.JPanel getLabelingPanel() {
		return jPanelLabeling;
	}

	/**
	 * Enables and disables the buttons for reading and assigning labels.
	 * @param enabled, true if enabled.
	 */
	public static void setEnabledControlButtons(boolean enabled){
		jButtonReadLabels.setEnabled(enabled);
		jButtonAssignLabels.setEnabled(enabled);
	}

	public static void setComboBox1(javax.swing.JComboBox comboBox1) {
		AssignBehaviorsTab.jComboBox1 = comboBox1;
	}

	public static javax.swing.JComboBox getComboBox1() {
		return jComboBox1;
	}

	public static javax.swing.JTable getJTable1() {
		return jTable1;
	}

	public static javax.swing.JToolBar getJToolBar2() {
		return jToolBarEntitiesForLabeling;
	}

	public static javax.swing.JToolBar getJToolBar3() {
		return jToolBarTypesSensors;
	}

	public static javax.swing.JTable getJTable2() {
		return jTableCurrentLabels;
	}

	public static javax.swing.JList getJList1() {
		return jListAvailableControllers;
	}

	public static javax.swing.JLabel getJLabel10005() {
		return jLabel10005;
	}
	public static javax.swing.JLabel getJLabel1000() {
		return jLabel1000;
	}
	
	public static HintPanel getHintPanel(){
		return hintPanel;
	}



	/*Declaration of components*/
	private static javax.swing.JList jListAvailableControllers;	


	private static javax.swing.JPanel jPanelLabeling;
	private javax.swing.JPanel jPanelEntitiesToLabel;

	private javax.swing.JScrollPane jScrollPaneAvailableControllers;
	private javax.swing.JScrollPane jScrollPaneTableCurrentLabels;

	/*Labels*/

	private static javax.swing.JLabel jLabel10005;
	private static javax.swing.JLabel jLabel1000;	


	private static  javax.swing.JComboBox jComboBox1;


	/*Radio Buttons*/
	private static  javax.swing.AbstractButton radionButtonATRON;
	private static javax.swing.AbstractButton  radioButtonMTRAN;
	private static javax.swing.AbstractButton radioButtonODIN;
	private static javax.swing.AbstractButton radionButtonCKBOTSTANDARD;

	private static javax.swing.AbstractButton radioButtonModule;
	private static javax.swing.AbstractButton radionButtonConnector;
	private static javax.swing.AbstractButton  radioButtonSensors;
	private static javax.swing.AbstractButton radioButtonProximitySensor;
	private static javax.swing.AbstractButton radioButtonEmpty;

	private  static javax.swing.JButton jButtonReadLabels;
	private  static javax.swing.JButton jButtonAssignLabels;

	private static javax.swing.JToolBar jToolBarFilterForModularRobot;
	private static javax.swing.JToolBar jToolBarEntitiesForLabeling;
	private static javax.swing.JToolBar jToolBarTypesSensors;

	private static javax.swing.JToolBar jToolBarControlOfLabels;

	private static javax.swing.JTable jTable1;
	private static javax.swing.JTable jTableCurrentLabels;

	

	private javax.swing.JCheckBox jCheckBoxShowLabelControl;

	

}
