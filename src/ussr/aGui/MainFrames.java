package ussr.aGui;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ussr.aGui.enumerations.MainFrameComponentsText;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;
import ussr.aGui.tabs.TabsInter;

/**
 * Holds methods and constants common for different design(visual appearance) of main GUI window (MainFrames). 
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public abstract class MainFrames extends GuiFrames implements MainFramesInter {
	/**
	 * The main GUI window.
	 */
	protected static MainFrames mainFrame;
	
	/**
	 * Insets of main frame border.
	 */
	protected Insets insets; 
	
	/**
	 * File choosers in the form of Open and Save dialogs respectively.
	 */
	protected static FileChooserFrameInter fcOpenFrame,fcSaveFrame;

	/**
	 * Container for keeping main GUI window components, the height of which determine the height of the window.  
	 */
	protected ArrayList<javax.swing.JComponent> components = new ArrayList<javax.swing.JComponent>();

	/**
	 * Containers for keeping all tabs plugged-in the main GUI window(MainFrame), tabs in the first and second tabbed panes respectively.
	 */
	protected ArrayList<TabsInter> allTabs,tabsFirstTabbedPane,tabsSecondTabbedPane;

	/**
	 * Defines visual appearance common to all instances of main GUI window.  
	 */
	public MainFrames(){
		filterPopulateTabs();
		initFileChoosers ();// initialize visual appearance of file choosers. Why here, because then they are responding faster to user generated events, because they are compiled earlier).
		initFrameProperties();	
	}


	/**
	 * Filters out and populates the tabs assigned to the first and second tabbed panes, into separate array lists. 
	 */
	public void filterPopulateTabs(){
		tabsFirstTabbedPane = new ArrayList<TabsInter>();
		tabsSecondTabbedPane = new ArrayList<TabsInter>();
		allTabs = new ArrayList<TabsInter>();

		for (int tabNr=0; tabNr<TABS.length;tabNr++){
			TabsInter currentTab = TABS[tabNr]; 
			if (currentTab.isFirstTabbedPane()){
				tabsFirstTabbedPane.add(currentTab);	
			}else {
				tabsSecondTabbedPane.add(currentTab);
			}
			allTabs.add(currentTab);
		}
	}	

	/**
	 * Initializes file choosers in forms: 1)Open, 2)Save and 3) Save as dialog.
	 */
	public void initFileChoosers () {	

		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.DEFAULT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);

		fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DEFAULT_DIRECTORY);	
		fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DEFAULT_DIRECTORY);
	}

	/**
	 * Initializes the visual appearance the main GUI windows.
	 * Follows Strategy  pattern.
	 */
	public abstract void initComponents();	

	/**
	 * Initializes the basic properties of the Frame common to all MainFrame instances.
	 * For example: top-left icon,title and so on.
	 */
	public void initFrameProperties(){		
		setUSSRicon(this);
		setTitle("Unified Simulator for Self-Reconfigurable Robots");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		pack();
		this.insets = this.getInsets();		
	}


	/**
	 * Initializes visual appearance (view) of menu bar.
	 * @param width, the width of menu bar.
	 * @param height, the height of menu bar.
	 */
	public void initJMenuBar(int width, int height){

		jMenuBarMain = new javax.swing.JMenuBar();	
		jMenuBarMain.setPreferredSize(new Dimension(width,height));
	
		jMenuFile = new javax.swing.JMenu();
		jMenuRender = new javax.swing.JMenu();		

		jMenuItemOpen = new javax.swing.JMenuItem();

		jMenuItemExit = new javax.swing.JMenuItem();		
		jMenuItemSave = new javax.swing.JMenuItem();
		
		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();			

		jCheckBoxMenuItemPhysics = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemWireFrame = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuBounds = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemNormals = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemLights = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuBufferDepth = new javax.swing.JCheckBoxMenuItem();

		jMenuFile.setText(MainFrameComponentsText.File.toString());

		jMenuItemOpen.setText(MainFrameComponentsText.Open.toString());
		jMenuItemOpen.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+OPEN_SMALL));
		//jMenuItemOpen.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OFF_LINE_SMALL));		
		jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.openActionPerformed(fcOpenFrame);
			}
		});

		jMenuFile.add(jMenuItemOpen);   

		jMenuFile.add(jSeparator2);

		jMenuItemSave.setText(MainFrameComponentsText.Save.toString());
		jMenuItemSave.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+SAVE_SMALL));
		//jMenuItemSave.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE_SMALL));	
		jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.saveActionPerformed(fcSaveFrame);
			}
		});

		jMenuFile.add(jMenuItemSave);

		jMenuFile.add(jSeparator1);

		jMenuItemExit.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+EXIT));
		jMenuItemExit.setText(MainFrameComponentsText.Exit.toString());
		jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jMenuItemExitActionPerformed();
			}
		});
		jMenuFile.add(jMenuItemExit);

		jMenuBarMain.add(jMenuFile);

		jMenuRender.setText(MainFrameComponentsText.Render.toString());
		jCheckBoxMenuItemPhysics.setSelected(false);
		jCheckBoxMenuItemPhysics.setText(MainFrameComponentsText.Physics.toString());
		jCheckBoxMenuItemPhysics.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemPhysicsActionPerformed(jCheckBoxMenuItemPhysics);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemPhysics);

		jCheckBoxMenuItemWireFrame.setSelected(false);
		jCheckBoxMenuItemWireFrame.setText(MainFrameComponentsText.Wire_Frame.toString().replace("_", " "));
		jCheckBoxMenuItemWireFrame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemWireFrameActionPerformed(jCheckBoxMenuItemWireFrame);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemWireFrame);

		jCheckBoxMenuBounds.setSelected(false);
		jCheckBoxMenuBounds.setText(MainFrameComponentsText.Bounds.toString());
		jCheckBoxMenuBounds.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuBoundsActionPerformed(jCheckBoxMenuBounds);
			}
		});
		jMenuRender.add(jCheckBoxMenuBounds);

		jCheckBoxMenuItemNormals.setSelected(false);
		jCheckBoxMenuItemNormals.setText(MainFrameComponentsText.Normals.toString());
		jCheckBoxMenuItemNormals.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemNormalsActionPerformed(jCheckBoxMenuItemNormals);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemNormals);

		jCheckBoxMenuItemLights.setSelected(false);
		jCheckBoxMenuItemLights.setText(MainFrameComponentsText.Lights.toString());
		jCheckBoxMenuItemLights.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemLightsActionPerformed(jCheckBoxMenuItemLights);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemLights);

		jCheckBoxMenuBufferDepth.setSelected(false);
		jCheckBoxMenuBufferDepth.setText(MainFrameComponentsText.Buffer_Depth.toString().replace("_", " "));
		jCheckBoxMenuBufferDepth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuBufferDepthActionPerformed(jCheckBoxMenuBufferDepth);
			}
		});

		jMenuRender.add(jCheckBoxMenuBufferDepth);

		jMenuBarMain.add(jMenuRender);

		setJMenuBar(jMenuBarMain); 
	}


	/**
	 * Returns the main menu bar of GUI window.
	 * @return jMenuBarMain, the main menu bar of GUI window.
	 */
	public static javax.swing.JMenuBar getJMenuBarMain() {
		return jMenuBarMain;
	}


	/**
	 * Initializes the tool bar for general control
	 * @param width, tool bar width.
	 * @param height, tool bar height.
	 */
	public void initJToolbarGeneralControl(int width,int height){

		/*Description of toolbar*/
		jToolBarGeneralControl = new javax.swing.JToolBar();
		jToolBarGeneralControl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.setRollover(true);
		jToolBarGeneralControl.setFloatable(false);
		jToolBarGeneralControl.setToolTipText("General Control");
		jToolBarGeneralControl.setPreferredSize(new Dimension(width,height));

		/*Instantiation of components*/
		jButtonRunRealTime = new javax.swing.JButton();
		jButtonRunStepByStep = new javax.swing.JButton();		
		jButtonRunFast = new javax.swing.JButton();
		jButtonPause = new javax.swing.JButton();
		jButtonTerminate = new javax.swing.JButton();
		jToolBarSeparator3 = new javax.swing.JToolBar.Separator();			
		jToolBarSeparator4 = new javax.swing.JToolBar.Separator();
		jToolBarSeparator5 = new javax.swing.JToolBar.Separator();
		jToggleButtonConstructRobot = new javax.swing.JToggleButton();
		jToggleButtonVisualizer = new javax.swing.JToggleButton();

		/*Description of components*/
		jButtonRunRealTime.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_REAL_TIME));
		jButtonRunRealTime.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonRunRealTime.setToolTipText("Run real time");		
		jButtonRunRealTime.setFocusable(false);    
		jButtonRunRealTime.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunRealTime.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRunRealTimeActionPerformed();        	  
			}
		});

		jToolBarGeneralControl.add(jButtonRunRealTime);

		jButtonRunFast.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_FAST));
		jButtonRunFast.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonRunFast.setToolTipText("Run fast");
		jButtonRunFast.setFocusable(false);
		jButtonRunFast.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunFast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRunFastActionPerformed();        	  
			}
		});

		jToolBarGeneralControl.add(jButtonRunFast);

		jButtonRunStepByStep.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + STEP_BY_STEP));
		jButtonRunStepByStep.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonRunStepByStep.setToolTipText("Run step by step");
		//jButtonRunStepByStep.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButtonRunStepByStep.setFocusable(false);
		jButtonRunStepByStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonRunStepByStep.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonRunStepByStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButtonRunStepByStep.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRunStepByStepActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonRunStepByStep);

		jButtonPause.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PAUSE));
		jButtonPause.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonPause.setToolTipText("Pause");
		jButtonPause.setFocusable(false);   
		jButtonPause.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonPauseActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonPause);

		jButtonTerminate.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + TERMINATE));
		jButtonTerminate.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonTerminate.setToolTipText("Terminate");
		jButtonTerminate.setFocusable(false);   
		jButtonTerminate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonTerminateActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonTerminate);

		jToolBarSeparator3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jToolBarSeparator3);

		jToolBarGeneralControl.add(initSaveButton(fcSaveFrame));
		jToolBarGeneralControl.add(initOpenButton(fcOpenFrame));

		jToolBarSeparator4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jToolBarSeparator4);


		jToggleButtonConstructRobot.setToolTipText("Construct Robot");
		jToggleButtonConstructRobot.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + CONSTRUCT_ROBOT ));
		jToggleButtonConstructRobot.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jToggleButtonConstructRobot.setFocusable(false);		
		jToggleButtonConstructRobot.setPreferredSize(new java.awt.Dimension(30, 30));
		jToggleButtonConstructRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				MainFrameSeparateController.jButtonConstructRobotActionPerformed(jToggleButtonConstructRobot,jTabbedPaneFirst, allTabs );

			}
		});
		jToolBarGeneralControl.add(jToggleButtonConstructRobot);	

		jToggleButtonVisualizer.setToolTipText(MainFrameComponentsText.Visualize_communication_of_modules.toString().replace("_", ""));
		jToggleButtonVisualizer.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + VISUALIZER ));
		jToggleButtonVisualizer.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jToggleButtonVisualizer.setFocusable(false);
		jToggleButtonVisualizer.setEnabled(false);
		jToggleButtonVisualizer.setPreferredSize(new java.awt.Dimension(30, 30));
		jToggleButtonVisualizer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonVisualizerActionPerformed(jToggleButtonVisualizer,jTabbedPaneFirst, allTabs);
			}
		});
		jToolBarGeneralControl.add(jToggleButtonVisualizer);

		jToolBarSeparator5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jToolBarSeparator5);
		
		getContentPane().add(jToolBarGeneralControl);
	}


	/**
	 * Returns the button for opening Tab called Visualize Communication of modules. 
	 * @return the button for opening Tab called Visualize Communication of modules.
	 */
	public static javax.swing.JToggleButton getJToggleButtonVisualizer() {
		return jToggleButtonVisualizer;
	}


	/**
	 * Initializes and returns the button called Save.
	 * @param fcSaveFrame, the file chooser frame to associate the button with.
	 * @return the button called Save.
	 */
	public static javax.swing.JButton initSaveButton(final FileChooserFrameInter fcSaveFrame ){
		jButtonSave = new javax.swing.JButton();		
		jButtonSave.setToolTipText("Save");
		jButtonSave.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + SAVE));
		jButtonSave.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));		
		jButtonSave.setFocusable(false);
		jButtonSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonSave.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButtonSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.saveActionPerformed(fcSaveFrame);
			}
		});
		return jButtonSave;
	}

	/**
	 * Initializes and returns the button called Open.
	 * @param fcOpenFrame,the file chooser frame to associate the button with.
	 * @return the button called Open.
	 */
	public static javax.swing.JButton  initOpenButton(final FileChooserFrameInter fcOpenFrame){
		jButtonOpen = new javax.swing.JButton();
		jButtonOpen.setToolTipText("Open");
		jButtonOpen.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OPEN));
		jButtonOpen.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));	
		jButtonOpen.setFocusable(false);		
		jButtonOpen.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.openActionPerformed(fcOpenFrame);
			}
		});
		return jButtonOpen;
	}

	/**
	 * Initializes resizing of both tabbed panes.
	 */
	public void initializeTabbedPanesResizing(){
		ComponentResizer componentResizer = new ComponentResizer();
		componentResizer.setSnapSize(new Dimension(10,10));
		componentResizer.registerComponent(jTabbedPaneFirst,jTabbedPaneSecond);
	}
	

	/**
	 * Initializes and returns the first tabbed pane (from the top in main GUI window).
	 * @param width, the width of first tabbed pane.
	 * @param height, the height of first tabbed pane.
	 * @return the first tabbed pane.
	 */
	public javax.swing.JTabbedPane initFirstTabbedPane(int width,int height){
		
		jTabbedPaneFirst  = new javax.swing.JTabbedPane();
		jTabbedPaneFirst.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPaneFirst.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jTabbedPaneFirst.setPreferredSize(new Dimension(width, height));		
		jTabbedPaneFirst.setFocusable(false);
		
		addTabs(tabsFirstTabbedPane,jTabbedPaneFirst);//Plug in tabs in tabbed pane 		
		getContentPane().add(jTabbedPaneFirst);		
		return jTabbedPaneFirst;
	}


	/**
	 * Plugs in tabs in tabbed panes.
	 * @param tabsContainer, the container of tabs.
	 * @param jTabbedPane, tabbed pane to plug tabs into.
	 */
	private void  addTabs(final ArrayList<TabsInter> tabsContainer ,final javax.swing.JTabbedPane jTabbedPane){

		for (int index =0; index < tabsContainer.size(); index++){
			TabsInter currentTab = tabsContainer.get(index);				

			if (currentTab.getImageIconDirectory()==null &&currentTab.isInitiallyVisible()){			
				jTabbedPane.addTab(currentTab.getTabTitle(),currentTab.getJComponent());

			}else if (currentTab.isInitiallyVisible()){				
				jTabbedPane.addTab(currentTab.getTabTitle(),new javax.swing.ImageIcon(currentTab.getImageIconDirectory()),currentTab.getJComponent());
			}		
		}	
	}


	/**
	 * Initializes second tabbed pane from the top in the design of main GUI window.
	 * @param width, the width of tabbed pane.
	 * @param height,the height of tabbed pane.
	 */
	public void initSecondTabbedPane(int width, int height){
		jTabbedPaneSecond = new javax.swing.JTabbedPane();
		jTabbedPaneSecond.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPaneSecond.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jTabbedPaneSecond.setPreferredSize(new Dimension(width, height));
		addTabs(tabsSecondTabbedPane,jTabbedPaneSecond);//Plug in tabs in tabbed pane and check boxes in menu bar		
		getContentPane().add(jTabbedPaneSecond);
	}
	
	/**
	 * Starts the main GUI window (frame).
	 * Follows strategy pattern. 
	 */
	public abstract void activate();

    /*Getters and setters*/
	/**
	 * Enables and disables menu components opening file choosers. 
	 * @param enable, true for enabled.
	 */
	public static void setSaveOpenEnabled (boolean enable){
		jButtonSave.setEnabled(enable);
		jMenuItemOpen.setEnabled(enable);
		jMenuItemSave.setEnabled(enable);	
		jButtonOpen.setEnabled(enable);
	} 

	/**
	 * Returns second tabbed pane from the top in the design of main GUI window.
	 * @return second tabbed pane from the top in the design of main GUI window.
	 */
	public static javax.swing.JTabbedPane getJTabbedPaneSecond() {
		return jTabbedPaneSecond;
	}

	/**
	 * Returns first tabbed pane from the top in the design of main GUI window.
	 * @return first tabbed pane from the top in the design of main GUI window.
	 */
	public static javax.swing.JTabbedPane getJTabbedPaneFirst() {
		return jTabbedPaneFirst;
	}	


	/**
	 * Returns the toolbar for general control of simulation and mainframe.
	 * @return jToolBarGeneralControl,the toolbar for general control of simulation and mainframe.
	 */
	public static javax.swing.JToolBar getJToolBarGeneralControl() {
		return jToolBarGeneralControl;
	}

	
	/*Declaration of MainFrame components*/
	private static javax.swing.JMenuBar jMenuBarMain;
	private javax.swing.JMenu jMenuFile,jMenuRender;

	private javax.swing.JMenuItem jMenuItemExit;
	private static javax.swing.JMenuItem jMenuItemOpen,jMenuItemSave;

	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemPhysics,jCheckBoxMenuItemWireFrame,jCheckBoxMenuBounds,
	                                     jCheckBoxMenuItemNormals,jCheckBoxMenuItemLights,jCheckBoxMenuBufferDepth;
	                                 
	private javax.swing.JSeparator jSeparator1,jSeparator2;

	private javax.swing.JToolBar.Separator jToolBarSeparator3,jToolBarSeparator4,jToolBarSeparator5;

	private static javax.swing.JToolBar jToolBarGeneralControl;
	
	private javax.swing.JButton jButtonRunRealTime,jButtonRunStepByStep,jButtonRunFast,
	                           jButtonPause,jButtonTerminate;
	private static javax.swing.JButton jButtonSave,jButtonOpen;
	private static javax.swing.JToggleButton jToggleButtonConstructRobot,jToggleButtonVisualizer;

	private static javax.swing.JTabbedPane jTabbedPaneFirst,jTabbedPaneSecond;
}

