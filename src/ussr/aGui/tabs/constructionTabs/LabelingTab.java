package ussr.aGui.tabs.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import ussr.aGui.FramesInter;
import ussr.aGui.enumerations.tabs.EntitiesForLabelingText;
import ussr.aGui.enumerations.tabs.TabsIcons;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.LabelingTabController;

/**
 * Defines visual appearance of tab named as Labeling. 
 * @author Konstantinas
 */
public class LabelingTab extends ConstructionTabs{

	/**
	 * The constants of grid bag layout used during design of the tab.
	 * This layout is used often during design of GUI, because of its flexibility to positioning components of GUI. 
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();	

	/**
	 * Defines visual appearance of tab named as Labeling.
	 * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane,location of the tab in the main GUI frame. True if it is the first tabbed pane. 
	 * @param tabTitle, the title of the tab.
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public LabelingTab(boolean initiallyVisible ,boolean firstTabbedPane, String tabTitle, String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);

		/*JComponent, is the main container of the tab.*/
		super.jComponent = new javax.swing.JPanel();
		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {
		
		jPanelEntitiesToLabel =  new javax.swing.JPanel(new GridBagLayout());
		
		jToolBarEntitiesForLabeling = new javax.swing.JToolBar();
		jToolBarTypesSensors = new javax.swing.JToolBar();
		jToolBarControlOfLabels = new javax.swing.JToolBar();
		
		jButtonReadLabels = new javax.swing.JButton();
		jButtonAssignLabels = new javax.swing.JButton();
		
		radioButtonModule =  new javax.swing.JRadioButton();
		radionButtonConnector =  new javax.swing.JRadioButton();
		radioButtonSensors =  new javax.swing.JRadioButton();
		radioButtonProximitySensor =  new javax.swing.JRadioButton();
		
		jPanelLabeling = new javax.swing.JPanel();	
		
		jTableLabels = new javax.swing.JTable();
		jScrollPaneTableCurrentLabels = new javax.swing.JScrollPane();

	/*	gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;	*/

		GridBagConstraints gridBagConstraintsEntitiesToLabel = new GridBagConstraints();
		TitledBorder displayTitle1;
		displayTitle1 = BorderFactory.createTitledBorder("Choose entity");
		displayTitle1.setTitleJustification(TitledBorder.CENTER);		
		jPanelEntitiesToLabel.setBorder(displayTitle1);
		jPanelEntitiesToLabel.setPreferredSize(new Dimension(100,130));
		//gridBagConstraintsLabelingPanel.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraintsLabelingPanel.gridx = 0;
		//gridBagConstraintsLabelingPanel.gridy = 0;

		//jToolBarEntitiesForLabeling.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarEntitiesForLabeling.setFloatable(false);//user can not make the tool bar to float
		jToolBarEntitiesForLabeling.setRollover(true);// the buttons inside are roll over
		jToolBarEntitiesForLabeling.setToolTipText("Entities for labeling");
		jToolBarEntitiesForLabeling.setPreferredSize(new Dimension(105,110));
		jToolBarEntitiesForLabeling.setOrientation(JToolBar.VERTICAL);		 
		gridBagConstraintsEntitiesToLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsEntitiesToLabel.gridx = 0;
		gridBagConstraintsEntitiesToLabel.gridy = 0;		


		final ButtonGroup buttonGroupEntities = new ButtonGroup() ;

			
		radioButtonModule.setText(EntitiesForLabelingText.Module.toString());	
		radioButtonModule.setFocusable(true);// direct the user to what should be done first
		radioButtonModule.setSelected(false);
		radioButtonModule.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LabelingTabController.radioButtonGroupEntitiesActionPerformed(radioButtonModule);
			}
		});			

		jToolBarEntitiesForLabeling.add(radioButtonModule);
		buttonGroupEntities.add(radioButtonModule);
		//jRadioButtonsLabelledEntities.add(radioButtonModule);
		
		radionButtonConnector.setText(EntitiesForLabelingText.Connector.toString());
		radionButtonConnector.setSelected(false);
		radionButtonConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LabelingTabController.radioButtonGroupEntitiesActionPerformed(radionButtonConnector);
			}
		});

		jToolBarEntitiesForLabeling.add(radionButtonConnector);
		buttonGroupEntities.add(radionButtonConnector);
		//jRadioButtonsLabelledEntities.add(radionButtonConnector);

		radioButtonSensors.setText(EntitiesForLabelingText.Sensors.toString());
		radioButtonSensors.setSelected(false);
		radioButtonSensors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LabelingTabController.radioButtonGroupEntitiesActionPerformed(radioButtonSensors);
			}
		});

		jToolBarEntitiesForLabeling.add(radioButtonSensors);
		buttonGroupEntities.add(radioButtonSensors);
		//jRadioButtonsLabelledEntities.add(radioButtonSensors);

		jPanelEntitiesToLabel.add(jToolBarEntitiesForLabeling,gridBagConstraintsEntitiesToLabel);

		//jToolBarTypesSensors.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarTypesSensors.setFloatable(false);//user can not make the tool bar to float
		jToolBarTypesSensors.setRollover(true);// the buttons inside are roll over
		jToolBarTypesSensors.setToolTipText("Entities for labeling");
		jToolBarTypesSensors.setVisible(false);
		jToolBarTypesSensors.setPreferredSize(new Dimension(105,110));
		jToolBarTypesSensors.setOrientation(JToolBar.VERTICAL);
		gridBagConstraintsEntitiesToLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsEntitiesToLabel.gridx = 0;
		gridBagConstraintsEntitiesToLabel.gridy = 1;
		gridBagConstraintsEntitiesToLabel.insets = new Insets (0,10,0,0);

				
		radioButtonProximitySensor.setText(EntitiesForLabelingText.Proximity.toString());	
		radioButtonProximitySensor.setSelected(false);
		radioButtonProximitySensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LabelingTabController.radioButtonGroupEntitiesActionPerformed(radioButtonProximitySensor);
			}
		});
		jToolBarTypesSensors.add(radioButtonProximitySensor);
		buttonGroupEntities.add(radioButtonProximitySensor);
		//jRadioButtonsLabelledEntities.add(radioButtonProximitySensor);

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
				LabelingTabController.jButtonReadLabelsActionPerformed();
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
				LabelingTabController.jButtonAssignLabelsActionPerformed();
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

	}
	
	/**
	 * Returns the tool bar containing entities for labeling.
	 * @return the tool bar containing entities for labeling.
	 */
	public static javax.swing.JToolBar getJToolBarEntitiesForLabeling() {
		return jToolBarEntitiesForLabeling;
	}

	/**
	 * Returns the tool bar containing types of sensors for labeling.
	 * @return the tool bar containing types of sensors for labeling.
	 */
	public static javax.swing.JToolBar getJToolBarTypesSensors() {
		return jToolBarTypesSensors;
	}
	/**
	 * Returns the table for displaying labels of entities.
	 * @return the table for displaying labels of entities.
	 */
	public static javax.swing.JTable getJTableLabels() {
		return jTableLabels;
	}
	
	/**
	 * Enables and disables the buttons for reading and assigning labels.
	 * @param enabled, true if enabled.
	 */
	public static void setEnabledControlButtons(boolean enabled){
		jButtonReadLabels.setEnabled(enabled);
		jButtonAssignLabels.setEnabled(enabled);
	}
	
	/**
	 * Returns the panel containing control of labels.
	 * @return panel,the panel containing control of labels.
	 */
	public static javax.swing.JPanel getLabelingPanel() {
		return jPanelLabeling;
	}
	
	
	private javax.swing.JPanel jPanelEntitiesToLabel;
	
	private static javax.swing.JToolBar jToolBarEntitiesForLabeling,jToolBarTypesSensors,
	                                    jToolBarControlOfLabels;
	
	private static  javax.swing.AbstractButton radioButtonModule,radionButtonConnector,
	radioButtonSensors, radioButtonProximitySensor;
	
	private  static javax.swing.JButton jButtonReadLabels,jButtonAssignLabels;
	
	private static javax.swing.JTable jTableLabels;
	
	private javax.swing.JScrollPane jScrollPaneTableCurrentLabels;
	
	private static javax.swing.JPanel jPanelLabeling;
}
