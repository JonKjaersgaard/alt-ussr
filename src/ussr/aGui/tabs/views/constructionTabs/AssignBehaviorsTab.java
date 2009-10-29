package ussr.aGui.tabs.views.constructionTabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.font.TextAttribute;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "2 Step: Assign Behaviors".  
 * @author Konstantinas
 */
public class AssignBehaviorsTab extends Tabs {

	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * The dimensions of the List component.
	 */
	private final int J_LIST_WIDTH = 250, J_LIST_HEIGHT = 200;


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
		super.fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
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

		jLabel10002 = new javax.swing.JLabel();
		jLabel10003 = new javax.swing.JLabel();
		jLabel1000 = new javax.swing.JLabel();
		jLabel10004 = new javax.swing.JLabel();	
		jLabel10005 = new javax.swing.JLabel();	

		jList1 = new javax.swing.JList();

		jScrollPane2 = new javax.swing.JScrollPane();	

		labelingPanel = new javax.swing.JPanel();

		/*Description of components*/		
		/*		jLabel10003.setText("Filter out for:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jLabel10003,gridBagConstraints);*/

		/*	jLabel10002.setText("Choose controller beneath and select module(s).When done run simulation.");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jLabel10002,gridBagConstraints);*/

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
		radionButtonATRON.setText("ATRON");	
		radionButtonATRON.setFocusable(true);// direct the user to what should be done first
		radionButtonATRON.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
		radionButtonATRON.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});

		jToolBar1.add(radionButtonATRON);
		buttonGroup.add(radionButtonATRON);

		radionButtonODIN =  new JRadioButton();
		radionButtonODIN.setText("Odin");
		radionButtonODIN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonODIN,jmeSimulation);
			}
		});

		jToolBar1.add(radionButtonODIN);
		buttonGroup.add(radionButtonODIN);

		radioButtonMTRAN =  new JRadioButton();
		radioButtonMTRAN.setText("MTran");
		radioButtonMTRAN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});

		jToolBar1.add(radioButtonMTRAN);
		buttonGroup.add(radioButtonMTRAN);

		radionButtonCKBOTSTANDARD =  new JRadioButton();
		radionButtonCKBOTSTANDARD.setText("CKBotStandard");
		radionButtonCKBOTSTANDARD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonCKBOTSTANDARD,jmeSimulation);
			}
		});

		jToolBar1.add(radionButtonCKBOTSTANDARD);
		buttonGroup.add(radionButtonCKBOTSTANDARD);			

		super.jComponent.add(jToolBar1,gridBagConstraints);			


		jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jList1.setPreferredSize(new java.awt.Dimension(J_LIST_WIDTH, J_LIST_HEIGHT));	
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

		TitledBorder displayTitle;
		displayTitle = BorderFactory.createTitledBorder("Labeling of entities");
		displayTitle.setTitleJustification(TitledBorder.CENTER);
		labelingPanel.setBorder(displayTitle);
		labelingPanel.setPreferredSize(new Dimension(200,200));

		/*External layout of panel inside main panel (jComponent)*/
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;


		jToolBar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar2.setFloatable(false);//user can not make the tool bar to float
		jToolBar2.setRollover(true);// the buttons inside are roll over
		jToolBar2.setToolTipText("Entities for labeling");
		jToolBar2.setPreferredSize(new Dimension(jToolBar1Width,J_LIST_HEIGHT-70));
		jToolBar2.setOrientation(JToolBar.VERTICAL);		 

		final ButtonGroup buttonGroupEntities = new ButtonGroup() ;

		radioButtonModule =  new JRadioButton();		
		radioButtonModule.setText("Module");	
		radioButtonModule.setFocusable(true);// direct the user to what should be done first
		radioButtonModule.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
		radioButtonModule.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
			}
		});			

		//jToolBar2.add(radioButtonModule);
		buttonGroupEntities.add(radioButtonModule);

		radionButtonConnector =  new JRadioButton();
		radionButtonConnector.setText("Connector");
		radionButtonConnector.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonODIN,jmeSimulation);
			}
		});

		//jToolBar2.add(radionButtonConnector);
		buttonGroupEntities.add(radionButtonConnector);

		radioButtonSensors =  new JRadioButton();
		radioButtonSensors.setText("Sensors");
		radioButtonSensors.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//AssignBehaviorsTabController.jButtonGroupActionPerformed(radioButtonMTRAN,jmeSimulation);
			}
		});

		//jToolBar2.add(radioButtonSensor);
		buttonGroupEntities.add(radioButtonSensors);

		/*	final ButtonGroup buttonGroupTypesSensors = new ButtonGroup() ;

			radioButtonProximitySensor =  new JRadioButton();		
			radioButtonProximitySensor.setText("Proximity");	
			radioButtonProximitySensor.setSelected(true);//set initially selected so that jList will containe already filtered out controllers
			radioButtonProximitySensor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					//AssignBehaviorsTabController.jButtonGroupActionPerformed(radionButtonATRON,jmeSimulation);
				}
			});
			gridBagConstraintsInternal.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraintsInternal.gridx = 1;
			gridBagConstraintsInternal.gridy = 4;
			gridBagConstraintsInternal.insets = new Insets(0,50,0,0);


			jToolBar2.add(radioButtonProximitySensor,gridBagConstraintsInternal);

			buttonGroupTypesSensors.add(radioButtonProximitySensor);*/

		jToolBar3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar3.setFloatable(false);//user can not make the tool bar to float
		jToolBar3.setRollover(true);// the buttons inside are roll over
		jToolBar3.setToolTipText("Types of sensors");
		jToolBar3.setPreferredSize(new Dimension(jToolBar1Width-20,J_LIST_HEIGHT-50));
		jToolBar3.setOrientation(JToolBar.VERTICAL);

		//jToolBar2.add(jToolBar3);

		/*Internal layout of current panel (labelingPanel)*/
		GroupLayout jToolBar2Layout = new GroupLayout(jToolBar2);
		jToolBar2.setLayout(jToolBar2Layout);



		jToolBar2Layout.setHorizontalGroup(
				jToolBar2Layout.createSequentialGroup()
				//Forces preferred side of component
				.addGroup(jToolBar2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(radioButtonModule,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(radionButtonConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(radioButtonSensors,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
											.addComponent(jToolBar3,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										

				));

		jToolBar2Layout.setVerticalGroup(
				jToolBar2Layout.createSequentialGroup()
				.addGroup(jToolBar2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(radioButtonModule,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
								.addComponent(radionButtonConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)	
										.addComponent(radioButtonSensors,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jToolBar3,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
									
		);



		labelingPanel.add(jToolBar2,gridBagConstraints);

		super.jComponent.add(labelingPanel,gridBagConstraints);





		//gridBagConstraintsInternal.insets = new Insets(10,0,0,5); // some space on the right



		/*jLabel10005.setText("Entity to label");
		jLabel10005.setVisible(true);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		//jLabel10005.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ATTENTION));		
		super.jComponent.add(jLabel10005,gridBagConstraints);	*/	












		/*jLabel1000.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + INFORMATION));		
		jLabel1000.setText("It also possible to assign controllers during simulation run time.");
		jLabel1000.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		jLabel1000.setVisible(false);
		//gridBagConstraints.weighty = 0.5;   //request any extra vertical space
		super.jComponent.add(jLabel1000,gridBagConstraints);*/

		/*jLabel10005 = new javax.swing.JLabel();		
		jLabel10005.setText("Assign them in simulation run time.");
		jLabel10005.setFont( new Font("Times New Roman", Font.BOLD, 12));
		jLabel10005.setVisible(false);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		jLabel10005.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ATTENTION));		

		jComponent.add(jLabel10005,gridBagConstraints);	*/

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

	private javax.swing.JScrollPane jScrollPane2;

	/*Labels*/
	private static javax.swing.JLabel jLabel10002;
	private static javax.swing.JLabel jLabel10003;
	private static javax.swing.JLabel jLabel10004;
	private static javax.swing.JLabel jLabel10005;
	private static javax.swing.JLabel jLabel1000;	

	/*Radio Buttons*/
	private static  javax.swing.AbstractButton radionButtonATRON;
	private static javax.swing.AbstractButton  radioButtonMTRAN;
	private static javax.swing.AbstractButton radionButtonODIN;
	private static javax.swing.AbstractButton radionButtonCKBOTSTANDARD;

	private static javax.swing.AbstractButton radioButtonModule;
	private static javax.swing.AbstractButton radionButtonConnector;
	private static javax.swing.AbstractButton  radioButtonSensors;
	private static javax.swing.AbstractButton radioButtonProximitySensor;

	private static javax.swing.JToolBar jToolBar1;
	private static javax.swing.JToolBar jToolBar2;
	private static javax.swing.JToolBar jToolBar3;
}
