package ussr.aGui.tabs.views.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import ussr.aGui.GuiFrames;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.additionalResources.HintPanel;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignBehaviorsTab extends Tabs implements AssignBehaviorsTabInter {


	/**
	 * 
	 */
	private static ArrayList<AbstractButton> jRadioButtonsLabelledEntities =  new ArrayList<AbstractButton>() ;	




	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 250, J_LIST_HEIGHT = 195;


	private final int jToolBar1Width = 125;

	/**
	 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".
	 * @param firstTabbedPane,
	 * @param tabTitle, the title of the tab
	 * @param jmeSimulation, the physical simulation.
	 * @param imageIconDirectory
	 */
	public AssignBehaviorsTab(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		super(firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);	

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		this.jComponent = new javax.swing.JPanel(new GridBagLayout());
		//super.fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {

		/*Instantiation of components*/
		jToolBar1 = new javax.swing.JToolBar();
		jToolBar2 = new javax.swing.JToolBar();
		jToolBar3 = new javax.swing.JToolBar();
		jToolBar4 = new javax.swing.JToolBar();

		jLabel10002 = new javax.swing.JLabel();
		jLabel10003 = new javax.swing.JLabel();
		jLabel1000 = new javax.swing.JLabel();
		jLabel10004 = new javax.swing.JLabel();	
		jLabel10005 = new javax.swing.JLabel();	

		jList1 = new javax.swing.JList();

		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane4 = new javax.swing.JScrollPane();

		//labelingPanel = new javax.swing.JPanel(new GridBagLayout());
		labelingPanel = new javax.swing.JPanel();
		entitiesToLabel =  new javax.swing.JPanel(new GridBagLayout());		
		jTable2 = new javax.swing.JTable();
		
		
		comboBox1 = new JComboBox();
		
		/*Display for hints. Feedback to the user.*/
		hintPanel  = new HintPanel(430,100);//custom panel
		

		/*Description of components*/	

		jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Filter out for:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		//jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);//user can not make the tool bar to float
		jToolBar1.setRollover(true);// the buttons inside are roll over
		jToolBar1.setToolTipText("Filter out for");
		jToolBar1.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT-70));
		jToolBar1.setOrientation(JToolBar.VERTICAL);		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0,0,0,5); // some space on the right


		final ButtonGroup buttonGroup = new ButtonGroup() ;

		radionButtonATRON =  new JRadioButton();		
		radionButtonATRON.setText(TabJComponentsText.ATRON.toString());	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});

		jToolBar1.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);


		radioButtonODIN =  new JRadioButton();
		radioButtonODIN.setText(TabJComponentsText.Odin.toString());
		radioButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonODIN,jmeSimulation);
			}
		});

		jToolBar1.add(radioButtonODIN);
		buttonGroup.add(radioButtonODIN);

		radioButtonMTRAN =  new JRadioButton();
		radioButtonMTRAN.setText(TabJComponentsText.MTran.toString());
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});

		jToolBar1.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD =  new JRadioButton();
		radionButtonCKBOTSTANDARD.setText(TabJComponentsText.CKBotStandard.toString());
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD,jmeSimulation);
			}
		});

		jToolBar1.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);			

		super.jComponent.add(jToolBar1,gridBagConstraints);			


		jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jList1.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH+60, J_LIST_HEIGHT));	
		AssignBehaviorsTabController.loadExistingControllers(jList1);

		jList1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				AssignBehaviorsTabController.jList1MouseReleased( jList1,jmeSimulation);
			}
		});
		jScrollPane2.setViewportView(jList1);
		jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Available controllers", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jScrollPane2,gridBagConstraints);	

		/*Initially update the list with controllers available for ATRON*/
		AssignBehaviorsTabController.updateList(jList1, AssignBehaviorsTabController.filterOut("ATRON"));

		GridBagConstraints gridBagConstraintsLabelingPanel = new GridBagConstraints();
		TitledBorder displayTitle;
		displayTitle = BorderFactory.createTitledBorder("Labeling of entities");
		displayTitle.setTitleJustification(TitledBorder.CENTER);
		labelingPanel.setBorder(displayTitle);
		labelingPanel.setPreferredSize(new Dimension(200,180));

		/*External layout of labelingPanel inside main panel (jComponent)*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;	

		GridBagConstraints gridBagConstraintsEntitiesToLabel = new GridBagConstraints();
		TitledBorder displayTitle1;
		displayTitle1 = BorderFactory.createTitledBorder("Choose entity");
		displayTitle1.setTitleJustification(TitledBorder.CENTER);		
		entitiesToLabel.setBorder(displayTitle1);
		entitiesToLabel.setPreferredSize(new Dimension(100,130));
		gridBagConstraintsLabelingPanel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsLabelingPanel.gridx = 0;
		gridBagConstraintsLabelingPanel.gridy = 0;

		jToolBar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar2.setFloatable(false);//user can not make the tool bar to float
		jToolBar2.setRollover(true);// the buttons inside are roll over
		jToolBar2.setToolTipText("Entities for labeling");
		jToolBar2.setPreferredSize(new Dimension(jToolBar1Width-20,J_LIST_HEIGHT-70));
		jToolBar2.setOrientation(JToolBar.VERTICAL);		 
		gridBagConstraintsEntitiesToLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsEntitiesToLabel.gridx = 0;
		gridBagConstraintsEntitiesToLabel.gridy = 0;		


		final ButtonGroup buttonGroupEntities = new ButtonGroup() ;

		radioButtonModule =  new JRadioButton();		
		radioButtonModule.setText(TabJComponentsText.Module.toString());	
		radioButtonModule.setFocusable(true);// direct the user to what should be done first
		radioButtonModule.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
		radioButtonModule.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonModule);
			}
		});			

		jToolBar2.add(radioButtonModule);
		buttonGroupEntities.add(radioButtonModule);
		jRadioButtonsLabelledEntities.add(radioButtonModule);

		radionButtonConnector =  new JRadioButton();
		radionButtonConnector.setText(TabJComponentsText.Connector.toString());
		radionButtonConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radionButtonConnector);
			}
		});

		jToolBar2.add(radionButtonConnector);
		buttonGroupEntities.add(radionButtonConnector);
		jRadioButtonsLabelledEntities.add(radionButtonConnector);

		radioButtonSensors =  new JRadioButton();
		radioButtonSensors.setText(TabJComponentsText.Sensors.toString());
		radioButtonSensors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//jToolBar3.setVisible(false);
				AssignBehaviorsTabController.radioButtonGroupEntitiesActionPerformed(radioButtonSensors);
			}
		});

		jToolBar2.add(radioButtonSensors);
		buttonGroupEntities.add(radioButtonSensors);
		jRadioButtonsLabelledEntities.add(radioButtonSensors);

		entitiesToLabel.add(jToolBar2,gridBagConstraintsEntitiesToLabel);

		jToolBar3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar3.setFloatable(false);//user can not make the tool bar to float
		jToolBar3.setRollover(true);// the buttons inside are roll over
		jToolBar3.setToolTipText("Entities for labeling");
		jToolBar3.setVisible(false);
		jToolBar3.setPreferredSize(new Dimension(jToolBar1Width-20,J_LIST_HEIGHT-70));
		jToolBar3.setOrientation(JToolBar.VERTICAL);
		gridBagConstraintsEntitiesToLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsEntitiesToLabel.gridx = 0;
		gridBagConstraintsEntitiesToLabel.gridy = 1;
		gridBagConstraintsEntitiesToLabel.insets = new Insets (0,10,0,0);

		radioButtonProximitySensor =  new JRadioButton();		
		radioButtonProximitySensor.setText(TabJComponentsText.Proximity.toString());	
		radioButtonProximitySensor.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
		radioButtonProximitySensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});
		jToolBar3.add(radioButtonProximitySensor);
		buttonGroupEntities.add(radioButtonProximitySensor);
		jRadioButtonsLabelledEntities.add(radioButtonSensors);

		entitiesToLabel.add(jToolBar3,gridBagConstraintsEntitiesToLabel);


		/*Customize the first table*/



	 
		comboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "MTRAN", "ODIN","CKBOT" }));
		
		final DefaultCellEditor dce1 = new DefaultCellEditor( comboBox1 );
		
		GuiFrames.changeToLookAndFeel(comboBox1);

		jTable1 = new javax.swing.JTable(){

			public TableCellEditor getCellEditor(int row, int column)
			{
				int modelColumn = convertColumnIndexToModel( column );

				if (modelColumn == 0 && row == 0)
					return dce1;
				else
					return super.getCellEditor(row, column);
			}

		}; 
		
		
		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
						//Initially empty rows
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
				},
				new String [] {
						"Add label from pallete" //Column name
				}
		));
		
		jTable1.setCellSelectionEnabled(true);
		jTable1.setDragEnabled(false);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.setPreferredSize(new Dimension(150,150));
		jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jScrollPane3.setViewportView(jTable1);
		jScrollPane3.setPreferredSize(new Dimension(150,150));
		
		
		
		
		//System.out.println("Out"+  );

		//jToolBar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar4.setFloatable(false);//user can not make the tool bar to float
		jToolBar4.setRollover(true);// the buttons inside are roll over
		jToolBar4.setToolTipText("Manipulation of labels");
		jToolBar4.setPreferredSize(new Dimension(40,50));
		jToolBar4.setOrientation(JToolBar.VERTICAL);		 

		radioButtonEmpty =  new JRadioButton();		
		radioButtonEmpty.setText("E");	
		radioButtonEmpty.setFocusable(true);// direct the user to what should be done first
		radioButtonEmpty.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
		radioButtonEmpty.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});			

		jToolBar4.add(radioButtonEmpty);		

		jTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
						//Initially empty rows
						{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
				},
				new String [] {
						"Module labels"//Column name
				}
		));



		jTable2.setCellSelectionEnabled(true);
		jTable2.setDragEnabled(false);
		jTable2.getTableHeader().setReorderingAllowed(false);
		jTable2.setPreferredSize(new Dimension(150,150));
		jScrollPane4.setViewportView(jTable2);
		jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jScrollPane4.setPreferredSize(new Dimension(150,150));


		GroupLayout labelingPanelLayoutLayout = new GroupLayout(labelingPanel);
		labelingPanel.setLayout(labelingPanelLayoutLayout);

		labelingPanelLayoutLayout.setAutoCreateGaps(true);
		labelingPanelLayoutLayout.setHorizontalGroup(
				labelingPanelLayoutLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(entitiesToLabel,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane3,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jToolBar4,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jScrollPane4,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)	

		);

		labelingPanelLayoutLayout.setVerticalGroup(
				labelingPanelLayoutLayout.createSequentialGroup()
				.addGroup(labelingPanelLayoutLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(entitiesToLabel,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane3,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jToolBar4,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
												.addComponent(jScrollPane4,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))				

		);
		
		super.jComponent.add(labelingPanel,gridBagConstraints);
		
		hintPanel.setText(HintPanelInter.builInHintsConstrucRobotTab[0]);
		hintPanel.setBorderTitle("Display for hints");		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;		
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		super.jComponent.add(hintPanel,gridBagConstraints);	

	}




	public static void setComboBox1(javax.swing.JComboBox comboBox1) {
		AssignBehaviorsTab.comboBox1 = comboBox1;
	}

	public static javax.swing.JComboBox getComboBox1() {
		return comboBox1;
	}

	public static javax.swing.JTable getJTable1() {
		return jTable1;
	}

	public static javax.swing.JToolBar getJToolBar2() {
		return jToolBar2;
	}

	public static javax.swing.JToolBar getJToolBar3() {
		return jToolBar3;
	}

	public static javax.swing.JTable getJTable2() {
		return jTable2;
	}

	public static javax.swing.JList getJList1() {
		return jList1;
	}

	public static javax.swing.JLabel getJLabel10005() {
		return jLabel10005;
	}
	public static javax.swing.JLabel getJLabel1000() {
		return jLabel1000;
	}



	/*Declaration of components*/
	private static javax.swing.JList jList1;	


	private javax.swing.JPanel labelingPanel;
	private javax.swing.JPanel entitiesToLabel;

	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;

	/*Labels*/
	private static javax.swing.JLabel jLabel10002;
	private static javax.swing.JLabel jLabel10003;
	private static javax.swing.JLabel jLabel10004;
	private static javax.swing.JLabel jLabel10005;
	private static javax.swing.JLabel jLabel1000;	
	

	private static  javax.swing.JComboBox comboBox1;
	

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

	private static javax.swing.JToolBar jToolBar1;
	private static javax.swing.JToolBar jToolBar2;
	private static javax.swing.JToolBar jToolBar3;

	private static javax.swing.JToolBar jToolBar4;

	private static javax.swing.JTable jTable1;
	private static javax.swing.JTable jTable2;
	
	private static HintPanel hintPanel;


}
