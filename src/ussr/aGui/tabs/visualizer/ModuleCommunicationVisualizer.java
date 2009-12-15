package ussr.aGui.tabs.visualizer;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import ussr.aGui.MainFramesInter;
import ussr.aGui.enumerations.tabs.VisualizerPacketFormats;
import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.controllers.ModuleCommunicationVisualizerController;


/**
 * Defines visual appearance of the tab called "Communication Visualizer".   
 * @author Konstantinas (Adapted Brian's code)
 */
public class ModuleCommunicationVisualizer extends Tabs {

	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * Defines visual appearance of the tab called "Communication Visualizer".   
	 * @param initiallyVisible,true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane from the top. 
	 * @param tabTitle, the title of the tab
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public ModuleCommunicationVisualizer(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);		

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());

		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	@SuppressWarnings("serial")
	public void initComponents() {

		/*Instantiation of components*/		
		jScrollPaneCommunicationDisplay = new JScrollPane();
		jScrollPaneNew = new JScrollPane();
		jScrollPacketFormat = new JScrollPane();		

		jTableModules = new javax.swing.JTable();		

		jToolBar2 =  new javax.swing.JToolBar();

		jButtonRun = new JButton();
		jButtonReset = new JButton();
		jButtonModules = new JButton();

		jListPacketFormats = new javax.swing.JList();
		
		jLabel1000 = new javax.swing.JLabel();
		jLabel1001 = new javax.swing.JLabel();
		jComboBoxNrPacketFilters = new javax.swing.JComboBox();

		jSeparator1 = new javax.swing.JToolBar.Separator();
		jCheckBoxDisplayLegendControl =  new javax.swing.JCheckBox(); 

		jPanelLegend =  new javax.swing.JPanel();		

		/*Description of components */	
		TitledBorder displayTitle;
		displayTitle = BorderFactory.createTitledBorder("Display for Communication of Modules");
		displayTitle.setTitleJustification(TitledBorder.CENTER);
		jScrollPaneCommunicationDisplay.setBorder(displayTitle);
		jScrollPaneCommunicationDisplay.setToolTipText("Display for Communication of Modules");
		jScrollPaneCommunicationDisplay.setPreferredSize(new Dimension(800, 2000));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;		
		gridBagConstraints.ipady = 320;
		gridBagConstraints.ipadx = 550;

		/*	jLabel1001.setText("Make sure that in simulation there are atleast two modules  and press run beneath ");
		jLabel1001.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + INFORMATION));
	    jLabel1001.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));		
		jLabel1001.setVisible(true);
		jScrollPane.setViewportView(jLabel1001);

		jLabel1000.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ERROR));		
		//jLabel1000.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));		
		jLabel1000.setVisible(false);	*/	


		super.jComponent.add(jScrollPaneCommunicationDisplay,gridBagConstraints);

		jCheckBoxDisplayLegendControl.setText("Display Legend-Control");
		jCheckBoxDisplayLegendControl.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModuleCommunicationVisualizerController.jCheckBoxDisplayLegend(jCheckBoxDisplayLegendControl);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.CENTER;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.ipady = 0;//reset
		gridBagConstraints.ipadx = 0;//reset
		super.jComponent.add(jCheckBoxDisplayLegendControl,gridBagConstraints);

		jPanelLegend.setVisible(false);//initially invisible
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.ipady = 50;
		gridBagConstraints.ipadx = 550;//600;//make it wide		

		jToolBar2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar2.setRollover(true);
		jToolBar2.setFloatable(false);
		jToolBar2.setToolTipText("Visualizer Control");
		jToolBar2.setPreferredSize(new Dimension(30,60));	
		jToolBar2.setOrientation(JToolBar.VERTICAL);

		jButtonReset.setIcon(new javax.swing.ImageIcon(TabsInter.DIRECTORY_ICONS + TabsInter.RESET));
		//jButtonReset.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));
		jButtonReset.setPreferredSize(new Dimension(30,30));
		jButtonReset.setToolTipText("Reset Display Area");
		jButtonReset.setFocusable(true);
		jButtonReset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModuleCommunicationVisualizerController.jButtonResetActionPerformed( jScrollPaneCommunicationDisplay);
			}
		});	
		jToolBar2.add(jButtonReset);
		jPanelLegend.add(jToolBar2);	


		jTableModules.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {//none rows

				},
				new String [] {
						"Module ID", "Include"//Column names
				}
		){
			@SuppressWarnings("unchecked")
			Class[] types = new Class [] {
				java.lang.String.class, java.lang.Boolean.class
			};
			boolean[] canEdit = new boolean [] {
					false, true
			};



			@SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		});

		jTableModules.setCellSelectionEnabled(true);
		jTableModules.setDragEnabled(false);
		jTableModules.getTableHeader().setReorderingAllowed(false);
		jTableModules.setPreferredSize(new Dimension(200,300));
		jScrollPaneNew.setViewportView(jTableModules);
		jTableModules.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jScrollPaneNew.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Filter out for", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		jScrollPaneNew.setPreferredSize(new Dimension(160,110));

		jPanelLegend.add(jScrollPaneNew);	

		jListPacketFormats.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jListPacketFormats.setPreferredSize(new java.awt.Dimension(135, 70));
		jListPacketFormats.setModel(new javax.swing.AbstractListModel() {
			VisualizerPacketFormats[] strings = VisualizerPacketFormats.values();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		jListPacketFormats.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				ModuleCommunicationVisualizerController.jListPacketFormatsMouseReleased( );
			}
		});


		jScrollPacketFormat.setViewportView(jListPacketFormats);
		jScrollPacketFormat.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Packet Fromats", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
		jScrollPacketFormat.setPreferredSize(new Dimension(140,110));


		jPanelLegend.add(jScrollPacketFormat);



		/*	Internal layout of the panel
		GroupLayout jPanelLegendLayout = new GroupLayout(jPanelLegend);
		jPanelLegend.setLayout(jPanelLegendLayout);

		jPanelLegendLayout.setAutoCreateGaps(true);
		jPanelLegendLayout.setHorizontalGroup(
				jPanelLegendLayout.createSequentialGroup()
				.addComponent(jToolBar2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)	
				.addComponent(jScrollPaneNew,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)						
						.addComponent(jScrollPacketFormat,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)							
		);

		jPanelLegendLayout.setVerticalGroup(
				jPanelLegendLayout.createSequentialGroup()
				.addGroup(jPanelLegendLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jToolBar2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPaneNew,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPacketFormat,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))												
		);*/		

		super.jComponent.add(jPanelLegend,gridBagConstraints);

		/*jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setRollover(true);
		jToolBar1.setFloatable(false);
		jToolBar1.setToolTipText("Visualizer Control");
		jToolBar1.setPreferredSize(new Dimension(30,30));	   
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_END;
		gridBagConstraints.ipady = 8;//make it tall
		//gridBagConstraints.ipadx = 300;//make it wide


		jButtonRun.setIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.RUN_REAL_TIME));
		jButtonRun.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));
		jButtonRun.setPreferredSize(new Dimension(30,30));
		jButtonRun.setToolTipText("Run");
		jButtonRun.setFocusable(true);
		jButtonRun.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModuleCommunicationVisualizerController.jButtonRunActionPerformed(jScrollPane);
			}
		});
		jToolBar1.add(jButtonRun);


		jButtonReset.setIcon(new javax.swing.ImageIcon(TabsInter.DIRECTORY_ICONS + TabsInter.RESET));
		jButtonReset.setDisabledIcon(new javax.swing.ImageIcon(MainFramesInter.DIRECTORY_ICONS + MainFramesInter.NO_ENTRANCE));
		jButtonReset.setPreferredSize(new Dimension(30,30));
		jButtonReset.setToolTipText("Reset Display Area");
		jButtonReset.setFocusable(true);
		jButtonReset.setEnabled(false);
		jButtonReset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModuleCommunicationVisualizerController.jButtonResetActionPerformed( jScrollPane);
			}
		});
		jToolBar1.add(jButtonReset);

		jSeparator1.setSize(2, 5);//FIXME
		jToolBar1.add(jSeparator1);

		jCheckBoxDisplayLegendControl.setText("Display Legend");
		jCheckBoxDisplayLegendControl.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModuleCommunicationVisualizerController.jCheckBoxShowLabelControlActionPerformed(jCheckBoxDisplayLegendControl);
			}
		});
		jToolBar1.add(jCheckBoxDisplayLegendControl);*/


		//	    jComboBoxNrPacketFilters.setToolTipText("SOME");
		//	    jComboBoxNrPacketFilters.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
		//	    jComboBoxNrPacketFilters.setPreferredSize(new java.awt.Dimension(50, GuiFrames.COMMON_HEIGHT-4));
		//	    jComboBoxNrPacketFilters.setEnabled(false);
		/*    jComboBoxNrPacketFilters.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructRobotTabController.jComboBoxNrConnectorsConstructionToolActionPerformed(jComboBoxNrConnectorsConstructionTool,jmeSimulation);
			}
		});*/		


		/*Internal layout of the toolbar*/
		/*		GroupLayout jToolBar1Layout = new GroupLayout(jToolBar1);
		jToolBar1.setLayout(jToolBar1Layout);

		jToolBar1Layout.setHorizontalGroup(
				jToolBar1Layout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jButtonRun,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jButtonReset,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)				
								.addComponent(jComboBoxNrPacketFilters,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										//Forces preferred side of component and also specifies it explicitly. For instance:6. 
										//.addComponent(jSeparator1,GroupLayout.PREFERRED_SIZE, 6,
											//	GroupLayout.PREFERRED_SIZE)
												//.addComponent(jButtonOnPreviousConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													//	GroupLayout.PREFERRED_SIZE)
														//.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
															//			GroupLayout.PREFERRED_SIZE)
														//.addComponent(jButtonOnNextConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
															//			GroupLayout.PREFERRED_SIZE)
														//.addComponent(jSeparator2,GroupLayout.PREFERRED_SIZE, 6,
															//	GroupLayout.PREFERRED_SIZE)

		);

		jToolBar1Layout.setVerticalGroup(
				jToolBar1Layout.createSequentialGroup()
				.addGroup(jToolBar1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonRun,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonReset,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboBoxNrPacketFilters,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
												//Forces preferred side of component and also specifies it explicitly. For instance:28. 
												//.addComponent(jSeparator1,GroupLayout.PREFERRED_SIZE, 28,
													//	GroupLayout.PREFERRED_SIZE)
														//.addComponent(jButtonJumpFromConnToConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
															//	GroupLayout.PREFERRED_SIZE)
																//.addComponent(jButtonOnNextConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																//		GroupLayout.PREFERRED_SIZE)
																//.addComponent(jButtonOnPreviousConnector,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																	//			GroupLayout.PREFERRED_SIZE)
																//.addComponent(jSeparator2,GroupLayout.PREFERRED_SIZE, 28,
																		GroupLayout.PREFERRED_SIZE))

		);*/
		//super.jComponent.add(jToolBar1,gridBagConstraints);




		/*jTableCurrentLabels.setModel(new javax.swing.table.DefaultTableModel(
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
		jTableCurrentLabels.setPreferredSize(new Dimension(20,20));
		jScrollPaneNew.setViewportView(jTableCurrentLabels);
		jTableCurrentLabels.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jScrollPaneNew.setPreferredSize(new Dimension(20,20));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipady = 100;//2000;//make it tall
		gridBagConstraints.ipadx = 50;//600;//make it wide	
		super.jComponent.add(jScrollPaneNew,gridBagConstraints);

		jToolBarEntitiesForLabeling.setFloatable(false);//user can not make the tool bar to float
		jToolBarEntitiesForLabeling.setRollover(true);// the buttons inside are roll over
		jToolBarEntitiesForLabeling.setToolTipText("Entities for labeling");
		jToolBarEntitiesForLabeling.setPreferredSize(new Dimension(20,20));
		jToolBarEntitiesForLabeling.setOrientation(JToolBar.VERTICAL);		 
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;


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
		super.jComponent.add(jToolBarEntitiesForLabeling,gridBagConstraints);
		buttonGroupEntities.add(radioButtonModule);*/



















		/* //this.jButtonUpdate.setIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.RUN_REAL_TIME));
	    jButtonModules.setText("Modules");
	    //jButtonReset.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    jButtonModules.setPreferredSize(new Dimension(30,30));
	    jButtonModules.setToolTipText("Filter out for modules");
	    jButtonModules.setFocusable(true);
	    jButtonModules.setEnabled(true);
	    jButtonModules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	ModuleCommunicationVisualizerController.jButtonModulesActionPerformed( jmeSimulation, jScrollPane);
            }
        });

	    this.jToolBar1.add(jButtonModules);	*/	    

		//super.jComponent.add(jToolBar1,gridBagConstraints);	 




	}

	/**
	 * Returns the scroll pane of display for communication of modules.  
	 * @return Returns the scroll pane of display for communication of modules. 
	 */
	public static javax.swing.JScrollPane getJScrollPane() {
		return jScrollPaneCommunicationDisplay;
	}

	public static javax.swing.JTable getJTableModules() {
		return jTableModules;
	}

	/*	public static javax.swing.JToolBar getJToolBar1() {
		return jToolBar1;
	}*/

	public static javax.swing.JPanel getJPanelLabeling() {
		return jPanelLegend;
	}

	/*Getters*/
	public static javax.swing.JLabel getJLabel1000() {
		return jLabel1000;
	}

	public static javax.swing.JLabel getJLabel1001() {
		return jLabel1001;
	}

	public static javax.swing.JButton getJButtonRun() {
		return jButtonRun;
	}

	public static javax.swing.JButton getJButtonReset() {
		return jButtonReset;
	}




	/*Declaration of components*/
	private  javax.swing.JButton  modulesButton;
	private  javax.swing.JButton  packetsButton;

	private  static javax.swing.JButton  jButtonRun;
	private  static javax.swing.JButton  jButtonReset;
	private  static javax.swing.JButton  jButtonModules;

	private static javax.swing.JList jListPacketFormats;	

	private static javax.swing.JScrollPane  jScrollPaneCommunicationDisplay;
	private static javax.swing.JScrollPane  jScrollPaneNew;
	private static javax.swing.JScrollPane  jScrollPacketFormat;

	private static javax.swing.JToolBar jToolBar2;
	private javax.swing.JToolBar  jToolBarEntitiesForLabeling;

	private static javax.swing.JLabel jLabel1000;	
	private static javax.swing.JLabel jLabel1001;
	private static javax.swing.JTable jTableModules;

	private static javax.swing.JComboBox jComboBoxNrPacketFilters;

	private static javax.swing.AbstractButton radioButtonModule;

	private javax.swing.JToolBar.Separator jSeparator1;	

	private javax.swing.JCheckBox jCheckBoxDisplayLegendControl;
	private static javax.swing.JPanel jPanelLegend;

}
