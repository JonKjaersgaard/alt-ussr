package ussr.aGui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;
import ussr.aGui.tabs.TabsInter;

import ussr.physics.jme.JMESimulation;

/**
 * 
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public abstract class MainFrames extends GuiFrames implements MainFramesInter {
	
	/**
	 * Height of the second tabbed pane.
	 */
	public final int TAB_PANE_HEIGHT2 = 100;

	/**
	 * Height of the first tabbed pane.
	 */
	public final int TAB_PANE_HEIGHT1 = (int)(SCREEN_DIMENSION.getHeight()-COMMON_HEIGHT-TAB_PANE_HEIGHT2-4*PADDING);
	
	/**
	 * The main GUI window.
	 */
	protected static MainFrames mainFrame;	
	
	/**
	 * The physical simulation.
	 */	   
	protected JMESimulation jmeSimulation;	
		
	/**
	 * File choosers in the form of Open and Save  dialogs respectively.
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
	 * For example: top-left icon,title and layout manager.
	 */
	public void initFrameProperties(){		
		setUSSRicon(this);
		setTitle("Unified Simulator for Self-Reconfigurable Robots");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);		
	}
	
	
	/**
	 * Initializes visual appearance(view) of menu bar.
	 */
	public void initJMenuBar(){
		
		jMenuBarMain = new javax.swing.JMenuBar();		

		jMenuFile = new javax.swing.JMenu();
		jMenuView = new javax.swing.JMenu();
		jMenuRender = new javax.swing.JMenu();		

		jMenuItemOpen = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();

		jMenuItemExit = new javax.swing.JMenuItem();		
		jMenuItemSave = new javax.swing.JMenuItem();
		jMenuItemSaveAs = new javax.swing.JMenuItem();

		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();			

		jCheckBoxMenuItemPhysics = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemWireFrame = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuBounds = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemNormals = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemLights = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuBufferDepth = new javax.swing.JCheckBoxMenuItem();
		
		jMenuFile.setText("File");

		jMenuItemOpen.setText("Open");
		jMenuItemOpen.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+OPEN_SMALL));
		//jMenuItemOpen.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OFF_LINE_SMALL));		
		jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.openActionPerformed(fcOpenFrame);
			}
		});

		jMenuFile.add(jMenuItemOpen);   

		jMenuFile.add(jSeparator2);

		jMenuItemSave.setText("Save");
		jMenuItemSave.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+SAVE_SMALL));
		//jMenuItemSave.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OFF_LINE_SMALL));		
		jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.saveActionPerformed(fcSaveFrame);
			}
		});

		jMenuFile.add(jMenuItemSave);

		jMenuFile.add(jSeparator1);

		jMenuItemExit.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+EXIT));
		jMenuItemExit.setText("Exit");
		jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItemExitActionPerformed();
			}
		});
		jMenuFile.add(jMenuItemExit);

		jMenuBarMain.add(jMenuFile);

		jMenuRender.setText("Render");
		jCheckBoxMenuItemPhysics.setSelected(false);
		jCheckBoxMenuItemPhysics.setText("Physics");
		jCheckBoxMenuItemPhysics.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItemPhysicsActionPerformed(jCheckBoxMenuItemPhysics);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemPhysics);

		jCheckBoxMenuItemWireFrame.setSelected(false);
		jCheckBoxMenuItemWireFrame.setText("Wire Frame");
		jCheckBoxMenuItemWireFrame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItemWireFrameActionPerformed(jCheckBoxMenuItemWireFrame);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemWireFrame);

		jCheckBoxMenuBounds.setSelected(false);
		jCheckBoxMenuBounds.setText("Bounds");
		jCheckBoxMenuBounds.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuBoundsActionPerformed(jCheckBoxMenuBounds);
			}
		});
		jMenuRender.add(jCheckBoxMenuBounds);

		jCheckBoxMenuItemNormals.setSelected(false);
		jCheckBoxMenuItemNormals.setText("Normals");
		jCheckBoxMenuItemNormals.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItemNormalsActionPerformed(jCheckBoxMenuItemNormals);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemNormals);

		jCheckBoxMenuItemLights.setSelected(false);
		jCheckBoxMenuItemLights.setText("Lights");
		jCheckBoxMenuItemLights.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItemLightsActionPerformed(jCheckBoxMenuItemLights);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemLights);

		jCheckBoxMenuBufferDepth.setSelected(false);
		jCheckBoxMenuBufferDepth.setText("Buffer Depth");
		jCheckBoxMenuBufferDepth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuBufferDepthActionPerformed(jCheckBoxMenuBufferDepth);
			}
		});

		jMenuRender.add(jCheckBoxMenuBufferDepth);

		jMenuBarMain.add(jMenuRender);
		
		setJMenuBar(jMenuBarMain); 
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
		jSeparator3 = new javax.swing.JToolBar.Separator();			
		jSeparator4 = new javax.swing.JToolBar.Separator();
		jSeparator5 = new javax.swing.JToolBar.Separator();
		jToggleButtonConstructRobot = new javax.swing.JToggleButton();
		jToggleButtonVisualizer = new javax.swing.JToggleButton();
		
		/*Description of components*/
		jButtonRunRealTime.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_REAL_TIME));
		jButtonRunRealTime.setToolTipText("Run real time");		
		jButtonRunRealTime.setFocusable(false);    
		jButtonRunRealTime.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunRealTime.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunRealTimeActionPerformed();        	  
			}
		});
		jToolBarGeneralControl.add(jButtonRunRealTime);

		jButtonRunFast.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_FAST));
		jButtonRunFast.setToolTipText("Run fast");
		jButtonRunFast.setFocusable(false);
		jButtonRunFast.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunFast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunFastActionPerformed();        	  
			}
		});

		jToolBarGeneralControl.add(jButtonRunFast);

		jButtonRunStepByStep.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + STEP_BY_STEP));
		jButtonRunStepByStep.setToolTipText("Run step by step");
		//jButtonRunStepByStep.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButtonRunStepByStep.setFocusable(false);
		jButtonRunStepByStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonRunStepByStep.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonRunStepByStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButtonRunStepByStep.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunStepByStepActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonRunStepByStep);

		jButtonPause.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PAUSE));
		jButtonPause.setToolTipText("Pause");
		jButtonPause.setFocusable(false);   
		jButtonPause.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonPauseActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonPause);
		
		jButtonTerminate.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + TERMINATE));
		jButtonTerminate.setToolTipText("Terminate");
		jButtonTerminate.setFocusable(false);   
		jButtonTerminate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonTerminateActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonTerminate);
		
		jSeparator3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jSeparator3);
		
		jToolBarGeneralControl.add(initSaveButton(fcSaveFrame));
		jToolBarGeneralControl.add(initOpenButton(fcOpenFrame));
		
		jSeparator4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jSeparator4);
		
		
		jToggleButtonConstructRobot.setToolTipText("Construct Robot");
		jToggleButtonConstructRobot.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + CONSTRUCT_ROBOT ));
		jToggleButtonConstructRobot.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jToggleButtonConstructRobot.setFocusable(false);		
		jToggleButtonConstructRobot.setPreferredSize(new java.awt.Dimension(30, 30));
		jToggleButtonConstructRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				MainFrameController.jButtonConstructRobotActionPerformed(jToggleButtonConstructRobot,jTabbedPaneFirst, allTabs );
				
			}
		});
		jToolBarGeneralControl.add(jToggleButtonConstructRobot);	
		
		
		
		jToggleButtonVisualizer.setToolTipText("Visualize communication of modules");
		jToggleButtonVisualizer.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + VISUALIZER ));
		jToggleButtonVisualizer.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jToggleButtonVisualizer.setFocusable(false);		
		jToggleButtonVisualizer.setPreferredSize(new java.awt.Dimension(30, 30));
		jToggleButtonVisualizer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonVisualizerActionPerformed(jToggleButtonVisualizer,jTabbedPaneFirst, allTabs);
			}
		});
		jToolBarGeneralControl.add(jToggleButtonVisualizer);
		
		jSeparator5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jSeparator5);
		

		getContentPane().add(jToolBarGeneralControl);
	}
	
	
	public static javax.swing.JButton  initSaveButton(final FileChooserFrameInter fcSaveFrame ){
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
				MainFrameController.saveActionPerformed(fcSaveFrame);
			}
		});
		return jButtonSave;
	}
	
	public static javax.swing.JButton  initOpenButton(final FileChooserFrameInter fcOpenFrame){
		jButtonOpen = new javax.swing.JButton();
		jButtonOpen.setToolTipText("Open");
		jButtonOpen.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OPEN));
		jButtonOpen.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));	
		jButtonOpen.setFocusable(false);		
		jButtonOpen.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.openActionPerformed(fcOpenFrame);
			}
		});
		return jButtonOpen;
		
	}
	
	/**
	 * @return
	 */
	public javax.swing.JTabbedPane initFirstTabbbedPane( ){
		jTabbedPaneFirst  = new javax.swing.JTabbedPane();
		jTabbedPaneFirst.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPaneFirst.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jTabbedPaneFirst.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2, TAB_PANE_HEIGHT1));		
		jTabbedPaneFirst.setFocusable(false);
		addTabs(tabsFirstTabbedPane,jTabbedPaneFirst);//Plug in tabs in tabbed pane and check boxes in menu bar		
	
		getContentPane().add(jTabbedPaneFirst);
		return jTabbedPaneFirst;
	}
    
	
	
	

	
	private void  addTabs(final ArrayList<TabsInter> tabsContainer ,final javax.swing.JTabbedPane jTabbedPane){
		
		for (int index =0; index < tabsContainer.size(); index++){
		//	System.out.println("S:" +tabsContainer.size());
			TabsInter currentTab = tabsContainer.get(index);				
			
			if (currentTab.getImageIconDirectory()==null &&currentTab.isInitiallyVisible()){			
				jTabbedPane.addTab(currentTab.getTabTitle(),currentTab.getJComponent());
				
			}else if (currentTab.isInitiallyVisible()){				
				jTabbedPane.addTab(currentTab.getTabTitle(),new javax.swing.ImageIcon(currentTab.getImageIconDirectory()),currentTab.getJComponent());
				
			}		
			
		}	
	}
	
	
	public void initSecondTabbedPane(int width, int height){
		jTabbedPane3 = new javax.swing.JTabbedPane();
		jTabbedPane3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane3.setPreferredSize(new Dimension(width, height));
		addTabs(tabsSecondTabbedPane,jTabbedPane3);//Plug in tabs in tabbed pane and check boxes in menu bar		
		getContentPane().add(jTabbedPane3);
	}
   

	/**
	 * Enables and disables menu components opening file choosers. 
	 * @param state
	 */
	public static void setSaveOpenEnabled (boolean state){
		jButtonSave.setEnabled(state);
		jMenuItemOpen.setEnabled(state);
		jMenuItemSave.setEnabled(state);	
		jButtonOpen.setEnabled(state);
	} 
	

	/**
	 * Starts the main GUI window (frame).
	 * Follows strategy pattern. 
	 */
	public abstract void activate();

	

	




	/*Declaration of MainFrame components*/
	public javax.swing.JMenuBar jMenuBarMain;

	public javax.swing.JMenu jMenuFile;
	public javax.swing.JMenu jMenuView;
	public javax.swing.JMenu jMenuRender;

	public javax.swing.JMenuItem jMenuItemExit;
	public static javax.swing.JMenuItem jMenuItemOpen;
	public static javax.swing.JMenuItem jMenuItemSave;
	public static javax.swing.JMenuItem jMenuItemSaveAs;
	public javax.swing.JMenuItem jMenuItem4;

	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemPhysics;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemWireFrame;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuBounds;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemNormals;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemLights;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuBufferDepth;	
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemNew;

	public javax.swing.JSeparator jSeparator1;
	public javax.swing.JSeparator jSeparator2;
	
	public javax.swing.JToolBar.Separator jSeparator3;
	public javax.swing.JToolBar.Separator jSeparator4;
	public javax.swing.JToolBar.Separator jSeparator5;

	public javax.swing.JToolBar jToolBar1;
	public javax.swing.JToolBar jToolBarGeneralControl;

	public javax.swing.JButton jButtonRunRealTime;
	public javax.swing.JButton jButtonRunStepByStep;
	public static javax.swing.JButton jButtonSave;
	public static javax.swing.JButton jButtonOpen;
	public static javax.swing.JToggleButton jToggleButtonConstructRobot;
	public javax.swing.JToggleButton jToggleButtonVisualizer;
	
	public javax.swing.JButton jButtonRunFast;
	public javax.swing.JButton jButtonPause;
	public javax.swing.JButton jButtonTerminate;

	public javax.swing.JLabel jLabel1;
	public javax.swing.JLabel jLabel3;
	public javax.swing.JLabel jLabel5;
	public javax.swing.JTextField jTextField1;

	public static javax.swing.JTabbedPane jTabbedPaneFirst;
	public static javax.swing.JTabbedPane jTabbedPane3;



	public javax.swing.JSplitPane jSplitPane1;

}
