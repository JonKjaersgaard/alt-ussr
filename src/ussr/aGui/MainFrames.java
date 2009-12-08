package ussr.aGui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.enumerations.MainFrameComponentsText;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;
import ussr.aGui.helpers.ComponentResizer;
import ussr.aGui.tabs.TabsInter;
import ussr.builder.helpers.StringProcessingHelper;

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
	protected static Insets insets; 
	
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
		setTitle(USSR_TITLE);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		pack();
		insets = this.getInsets();		
	}


	/**
	 * Initializes visual appearance (view) of menu bar.
	 * @param width, the width of menu bar.
	 * @param height, the height of menu bar.
	 */
	public void initJMenuBar(int width, int height){

		/*Instantiate components*/
		jMenuBarMain = new javax.swing.JMenuBar();			
	
		jMenuFile = new javax.swing.JMenu();
		jMenuItemOpen = new javax.swing.JMenuItem();
		jMenuItemExit = new javax.swing.JMenuItem();		
		jMenuItemSave = new javax.swing.JMenuItem();
		
		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();
		jSeparator3 = new javax.swing.JSeparator();
		
		jMenuRender = new javax.swing.JMenu();	
		
		jCheckBoxMenuItemPhysics = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemWireFrame = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuBounds = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemNormals = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemLights = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuBufferDepth = new javax.swing.JCheckBoxMenuItem();	
		
		jMenuWindow = new javax.swing.JMenu();
		jMenuHide = new javax.swing.JMenu();
		
		jCheckBoxMenuItemInteraction= new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItemDebugging = new javax.swing.JCheckBoxMenuItem();		
		jCheckBoxMenuItemDisplayForHints = new javax.swing.JCheckBoxMenuItem();

        /*Description of components*/
		jMenuBarMain.setPreferredSize(new Dimension(width,height));
		
		jMenuFile.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.File));

		jMenuItemOpen.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Open));
		jMenuItemOpen.setIcon(MainFrameIcons.OPEN_SMALL.getImageIcon());
		jMenuItemOpen.setDisabledIcon(MainFrameIcons.OPEN_SMALL_DISABLED.getImageIcon());		
		jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.openActionPerformed(fcOpenFrame);
			}
		});

		jMenuFile.add(jMenuItemOpen);   

		jMenuFile.add(jSeparator2);

		jMenuItemSave.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Save));
		jMenuItemSave.setIcon(MainFrameIcons.SAVE_SMALL.getImageIcon());
		jMenuItemSave.setDisabledIcon(MainFrameIcons.SAVE_SMALL_DISABLED.getImageIcon());	
		jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.saveActionPerformed(fcSaveFrame);
			}
		});

		jMenuFile.add(jMenuItemSave);

		jMenuFile.add(jSeparator1);
		
		jMenuItemExit.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Exit));
		jMenuItemExit.setIcon(MainFrameIcons.EXIT_SMALL.getImageIcon());		
		jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jMenuItemExitActionPerformed();
			}
		});
		jMenuFile.add(jMenuItemExit);

		jMenuBarMain.add(jMenuFile);

		jMenuRender.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Render));
		jCheckBoxMenuItemPhysics.setSelected(false);
		jCheckBoxMenuItemPhysics.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Physics));
		jCheckBoxMenuItemPhysics.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemPhysicsActionPerformed(jCheckBoxMenuItemPhysics);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemPhysics);

		jCheckBoxMenuItemWireFrame.setSelected(false);
		jCheckBoxMenuItemWireFrame.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Wire_frame));
		jCheckBoxMenuItemWireFrame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemWireFrameActionPerformed(jCheckBoxMenuItemWireFrame);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemWireFrame);

		jCheckBoxMenuBounds.setSelected(false);
		jCheckBoxMenuBounds.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Bounds));
		jCheckBoxMenuBounds.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuBoundsActionPerformed(jCheckBoxMenuBounds);
			}
		});
		jMenuRender.add(jCheckBoxMenuBounds);

		jCheckBoxMenuItemNormals.setSelected(false);
		jCheckBoxMenuItemNormals.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Normals));
		jCheckBoxMenuItemNormals.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemNormalsActionPerformed(jCheckBoxMenuItemNormals);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemNormals);

		jCheckBoxMenuItemLights.setSelected(false);
		jCheckBoxMenuItemLights.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Lights));
		jCheckBoxMenuItemLights.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemLightsActionPerformed(jCheckBoxMenuItemLights);
			}
		});
		jMenuRender.add(jCheckBoxMenuItemLights);

		jCheckBoxMenuBufferDepth.setSelected(false);
		jCheckBoxMenuBufferDepth.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Buffer_depth));
		jCheckBoxMenuBufferDepth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuBufferDepthActionPerformed(jCheckBoxMenuBufferDepth);
			}
		});

		jMenuRender.add(jCheckBoxMenuBufferDepth);

		jMenuBarMain.add(jMenuRender);		
		
		jMenuWindow.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Window));	
		
		jMenuHide.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Hide));
		
		jCheckBoxMenuItemInteraction.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Interaction));
		jCheckBoxMenuItemInteraction.setSelected(false);
		jCheckBoxMenuItemInteraction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.hideTabbedPanes1ActionPerformed(jCheckBoxMenuItemInteraction.isSelected(),jCheckBoxMenuItemDebugging.isSelected());
				mainFrame.repaint();
			}
		});
		jMenuHide.add(jCheckBoxMenuItemInteraction);
		
		jCheckBoxMenuItemDebugging.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Debugging));
		jCheckBoxMenuItemDebugging.setSelected(false);
		jCheckBoxMenuItemDebugging.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.hideTabbedPanes1ActionPerformed(jCheckBoxMenuItemInteraction.isSelected(),jCheckBoxMenuItemDebugging.isSelected());
				mainFrame.repaint();
			}
		});
		jMenuHide.add(jCheckBoxMenuItemDebugging);		
		
		//jMenuHide.add(jSeparator3);
		
		jCheckBoxMenuItemDisplayForHints.setText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Display_for_hints));
		jCheckBoxMenuItemDisplayForHints.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jCheckBoxMenuItemDisplayForHintsActionPerformed(jCheckBoxMenuItemDisplayForHints);
				mainFrame.repaint();
			}
		});
		
		jMenuHide.add(jCheckBoxMenuItemDisplayForHints);
		
		jMenuWindow.add(jMenuHide);
		
		jMenuBarMain.add(jMenuWindow);
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
		jToolBarGeneralControl.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.General_control));
		jToolBarGeneralControl.setPreferredSize(new Dimension(width,height));

		/*Instantiation of components*/
		jButtonRunRealTime = new javax.swing.JButton();
		jButtonRunStepByStep = new javax.swing.JButton();		
		jButtonRunFast = new javax.swing.JButton();
		jButtonPause = new javax.swing.JButton();
		jButtonTerminate = new javax.swing.JButton();
		jButtonRestart = new javax.swing.JButton();
		
		jToolBarSeparator3 = new javax.swing.JToolBar.Separator();			
		jToolBarSeparator4 = new javax.swing.JToolBar.Separator();
		jToolBarSeparator5 = new javax.swing.JToolBar.Separator();
		jToolBarSeparator6 = new javax.swing.JToolBar.Separator();
		
		jToggleButtonConstructRobot = new javax.swing.JToggleButton();
		jToggleButtonVisualizer = new javax.swing.JToggleButton();
		
		jToggleButtonMaximizeDebugging = new javax.swing.JToggleButton();
		jToggleButtonMaximizeInteraction = new javax.swing.JToggleButton();

		/*Description of components*/
		jToolBarGeneralControl.add(initOpenButton(fcOpenFrame));
		jToolBarGeneralControl.add(initSaveButton(fcSaveFrame));
		
		//jToolBarSeparator3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jToolBarSeparator3);
		
		jButtonRunRealTime.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Run_real_time));		
		jButtonRunRealTime.setIcon(MainFrameIcons.RUN_REAL_TIME.getImageIcon());
		jButtonRunRealTime.setRolloverIcon(MainFrameIcons.RUN_REAL_TIME_ROLLOVER.getImageIcon());		
		jButtonRunRealTime.setDisabledIcon(MainFrameIcons.RUN_REAL_TIME_DISABLED.getImageIcon());		
		jButtonRunRealTime.setFocusable(false);    
		jButtonRunRealTime.setPreferredSize(BUTTON_DIMENSION);      
		jButtonRunRealTime.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRunRealTimeActionPerformed();        	  
			}
		});
		jToolBarGeneralControl.add(jButtonRunRealTime);

		jButtonRunFast.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Run_fast));
		jButtonRunFast.setIcon(MainFrameIcons.RUN_FAST.getImageIcon());		
		jButtonRunFast.setRolloverIcon(MainFrameIcons.RUN_FAST_ROLLOVER.getImageIcon());
		jButtonRunFast.setDisabledIcon(MainFrameIcons.RUN_FAST_DISABLED.getImageIcon());		
		jButtonRunFast.setFocusable(false);
		jButtonRunFast.setPreferredSize(BUTTON_DIMENSION);      
		jButtonRunFast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRunFastActionPerformed();        	  
			}
		});
		jToolBarGeneralControl.add(jButtonRunFast);

		jButtonRunStepByStep.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Run_step_by_step));
		jButtonRunStepByStep.setIcon(MainFrameIcons.RUN_STEP_BY_STEP.getImageIcon());				
		jButtonRunStepByStep.setRolloverIcon(MainFrameIcons.RUN_STEP_BY_STEP_ROLLOVER.getImageIcon());
	    jButtonRunStepByStep.setDisabledIcon(MainFrameIcons.RUN_STEP_BY_STEP_DISABLED.getImageIcon());		
		jButtonRunStepByStep.setFocusable(false);
		jButtonRunStepByStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonRunStepByStep.setPreferredSize(BUTTON_DIMENSION);
		jButtonRunStepByStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButtonRunStepByStep.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRunStepByStepActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonRunStepByStep);

		jButtonPause.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Pause));
		jButtonPause.setIcon(MainFrameIcons.PAUSE.getImageIcon());
		jButtonPause.setRolloverIcon(MainFrameIcons.PAUSE_ROLLOVER.getImageIcon());
		jButtonPause.setDisabledIcon(MainFrameIcons.PAUSE_DISABLED.getImageIcon());	
		jButtonPause.setPreferredSize(BUTTON_DIMENSION);
		jButtonPause.setFocusable(false);   
		jButtonPause.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonPauseActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonPause);

		jButtonTerminate.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Terminate));
		jButtonTerminate.setIcon(MainFrameIcons.TERMINATE.getImageIcon());
		jButtonTerminate.setRolloverIcon(MainFrameIcons.TERMINATE_ROLLOVER.getImageIcon());		
		jButtonTerminate.setDisabledIcon(MainFrameIcons.TERMINATE_DISABLED.getImageIcon());
		jButtonTerminate.setPreferredSize(BUTTON_DIMENSION);
		jButtonTerminate.setFocusable(false);   
		jButtonTerminate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonTerminateActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonTerminate);
		
		jButtonRestart.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Restart));
		jButtonRestart.setIcon(MainFrameIcons.RESTART.getImageIcon());
		jButtonRestart.setRolloverIcon(MainFrameIcons.RESTART_ROLLOVER.getImageIcon());		
		jButtonRestart.setDisabledIcon(MainFrameIcons.RESTART_DISABLED.getImageIcon());
		jButtonRestart.setPreferredSize(BUTTON_DIMENSION);
		jButtonRestart.setFocusable(false);   
		jButtonRestart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonRestartActionPerformed();
			}
		});
		jToolBarGeneralControl.add(jButtonRestart);		
		
		//jToolBarSeparator4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jToolBarSeparator4);

		jToggleButtonConstructRobot.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Construct_robot));
		jToggleButtonConstructRobot.setIcon(MainFrameIcons.CONSTRUCT_ROBOT.getImageIcon());
		jToggleButtonConstructRobot.setSelectedIcon(MainFrameIcons.CONSTRUCT_ROBOT_ROLLOVER.getImageIcon());
		jToggleButtonConstructRobot.setRolloverIcon(MainFrameIcons.CONSTRUCT_ROBOT_ROLLOVER.getImageIcon());
		jToggleButtonConstructRobot.setDisabledIcon(MainFrameIcons.CONSTRUCT_ROBOT_DISABLED.getImageIcon());		
		jToggleButtonConstructRobot.setFocusable(false);		
		jToggleButtonConstructRobot.setPreferredSize(BUTTON_DIMENSION);
		jToggleButtonConstructRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				MainFrameSeparateController.jButtonConstructRobotActionPerformed(jToggleButtonConstructRobot,jTabbedPaneFirst, allTabs );

			}
		});
		jToolBarGeneralControl.add(jToggleButtonConstructRobot);	
		
		jToggleButtonVisualizer.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Visualize_communication_of_modules));
		jToggleButtonVisualizer.setIcon(MainFrameIcons.VISUALIZER.getImageIcon());
		jToggleButtonVisualizer.setSelectedIcon(MainFrameIcons.VISUALIZER_ROLLOVER.getImageIcon());		
		jToggleButtonVisualizer.setRolloverIcon(MainFrameIcons.VISUALIZER_ROLLOVER.getImageIcon());
	    jToggleButtonVisualizer.setDisabledIcon(MainFrameIcons.VISUALIZER_DISABLED.getImageIcon());
		jToggleButtonVisualizer.setFocusable(false);
		jToggleButtonVisualizer.setEnabled(false);
		jToggleButtonVisualizer.setPreferredSize(BUTTON_DIMENSION);
		jToggleButtonVisualizer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.jButtonVisualizerActionPerformed(jToggleButtonVisualizer,jTabbedPaneFirst, allTabs);
			}
		});
		jToolBarGeneralControl.add(jToggleButtonVisualizer);

		//jToolBarSeparator5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jToolBarSeparator5);
		
		jToggleButtonMaximizeInteraction.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Hide_debugging));
		jToggleButtonMaximizeInteraction.setIcon(MainFrameIcons.TABBED_PANES.getImageIcon());
		jToggleButtonMaximizeInteraction.setRolloverIcon(MainFrameIcons.INTERACTION_MAXIMIZED.getImageIcon());
		jToggleButtonMaximizeInteraction.setSelectedIcon(MainFrameIcons.INTERACTION_MAXIMIZED.getImageIcon()); 
		jToggleButtonMaximizeInteraction.setSelectedIcon(MainFrameIcons.TABBED_PANES_DISABLED.getImageIcon()); 
		jToggleButtonMaximizeInteraction.setSelected(false);
		jToggleButtonMaximizeInteraction.setFocusable(false);
		jToggleButtonMaximizeInteraction.setPreferredSize(BUTTON_DIMENSION);
		jToggleButtonMaximizeInteraction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.hideTabbedPanes1ActionPerformed(jToggleButtonMaximizeDebugging.isSelected(),jToggleButtonMaximizeInteraction.isSelected());
				mainFrame.repaint();
			}
		});
		
		jToolBarGeneralControl.add(jToggleButtonMaximizeInteraction);
		
		jToggleButtonMaximizeDebugging.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Hide_interaction));
		jToggleButtonMaximizeDebugging.setIcon(MainFrameIcons.TABBED_PANES.getImageIcon());
		jToggleButtonMaximizeDebugging.setRolloverIcon(MainFrameIcons.DEBUGGING_MAXIMIZED.getImageIcon());
		jToggleButtonMaximizeDebugging.setSelectedIcon(MainFrameIcons.DEBUGGING_MAXIMIZED.getImageIcon());
		jToggleButtonMaximizeDebugging.setDisabledIcon(MainFrameIcons.TABBED_PANES_DISABLED.getImageIcon());
		jToggleButtonMaximizeDebugging.setSelected(false);
		jToggleButtonMaximizeDebugging.setFocusable(false);
		jToggleButtonMaximizeDebugging.setPreferredSize(BUTTON_DIMENSION);
		jToggleButtonMaximizeDebugging.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameSeparateController.hideTabbedPanes1ActionPerformed(jToggleButtonMaximizeDebugging.isSelected(),jToggleButtonMaximizeInteraction.isSelected());
				mainFrame.repaint();
			}
		});
	
		jToolBarGeneralControl.add(jToggleButtonMaximizeDebugging);
		
		jToolBarGeneralControl.add(jToolBarSeparator6);
		
		getContentPane().add(jToolBarGeneralControl);
	}


	public static javax.swing.JToggleButton getJToggleButtonMaximizeInteraction() {
		return jToggleButtonMaximizeInteraction;
	}


	public static javax.swing.JToggleButton getJToggleButtonMaximizeDebugging() {
		return jToggleButtonMaximizeDebugging;
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
		jButtonSave.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Save));		
		jButtonSave.setIcon(MainFrameIcons.SAVE.getImageIcon());
		jButtonSave.setRolloverIcon(MainFrameIcons.SAVE_ROLLOVER.getImageIcon());
		jButtonSave.setDisabledIcon(MainFrameIcons.SAVE_DISABLED.getImageIcon());		
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
		jButtonOpen.setToolTipText(StringProcessingHelper.replaceUnderscoreWithSpace(MainFrameComponentsText.Open));
		
		jButtonOpen.setIcon(MainFrameIcons.OPEN.getImageIcon());
		jButtonOpen.setRolloverIcon(MainFrameIcons.OPEN_ROLLOVER.getImageIcon());		
		jButtonOpen.setDisabledIcon(MainFrameIcons.OPEN_DISABLED.getImageIcon());
		
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
		//jTabbedPaneFirst.setToolTipText(text);
		jTabbedPaneFirst.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPaneFirst.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jTabbedPaneFirst.setPreferredSize(new Dimension(width, height));		
		jTabbedPaneFirst.setFocusable(false);
		jTabbedPaneFirst.setEnabled(false);		
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
	
/*	public Cursor gettCursor(){
	 	Toolkit toolkit = Toolkit.getDefaultToolkit();        	
	 	java.awt.Image image = toolkit.getImage("resources/mainFrame/icons/jpg/ussrIcon.png");        	
	 	java.awt.Cursor c = toolkit.createCustomCursor(image , new java.awt.Point(10,10), "img");
	 setCursor(c);
	 return c;
	 	//mainPane.setCursor (c);
	}*/
	
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
	 * Controls visibility of the first tabbed pane.
	 * @param visible, true for being visible.
	 */
	public static void setJTabbedPaneFirstVisible(boolean visible) {
     jTabbedPaneFirst.setVisible(visible);
	}
	
	/**
	 * Controls visibility of the second tabbed pane.
	 * @param visible, true for being visible.
	 */
	public static void setJTabbedPaneSecondVisible(boolean visible) {
		 jTabbedPaneSecond.setVisible(visible);
	}
	
	/**
	 * Controls visibility of both tabbed panes.
	 * @param visible, true for being visible.
	 */
	public static void setTabbedPanesVisible(boolean visible){
		jTabbedPaneFirst.setVisible(visible);
		jTabbedPaneSecond.setVisible(visible);
	}
	
	/**
	 * Controls enabling of the first tabbed pane.
	 * @param enabled, true for being enabled.
	 */
	public static void setJTabbedPaneFirstEnabled(boolean enabled) {
		jTabbedPaneFirst.setEnabled(enabled);
	}

	/**
	 * Returns the toolbar for general control of simulation and mainframe.
	 * @return jToolBarGeneralControl,the toolbar for general control of simulation and mainframe.
	 */
	public static javax.swing.JToolBar getJToolBarGeneralControl() {
		return jToolBarGeneralControl;
	}
	
	/**
	 * Returns viable width of the frame (excluding border(decoration) and gaps between border and internal components). 
	 * @return viable width of the frame (excluding border(decoration) and gaps between border and internal components). 
	 */
	public static int getMainFrameViableWidth(){
		return mainFrame.getWidth() -insets.right-insets.left-2*HORIZONTAL_GAPS;
	}
	
	
	/**
	 * Returns Gui component for controlling visibility of tabbed pane for debugging.
	 * @return Gui component for controlling visibility of tabbed pane for debugging.
	 */
	public static javax.swing.JCheckBoxMenuItem getJCheckBoxMenuItemDebugging() {
		return jCheckBoxMenuItemDebugging;
	}

	/**
	 * Returns Gui component for controlling visibility of tabbed pane for interaction with simulation.
	 * @return Gui component for controlling visibility of tabbed pane for interaction with simulation.
	 */
	public static javax.swing.JCheckBoxMenuItem getJCheckBoxMenuItemInteraction() {
		return jCheckBoxMenuItemInteraction;
	}
	
	/**
	 * Controls the state of selection of components associated with the second tabbed pane.
	 * @param selected, true for selected.
	 */
	public static void setComponentsAssocWithFirstTabbedPaneSelected(boolean selected){
		jCheckBoxMenuItemInteraction.setSelected(selected);
		jToggleButtonMaximizeDebugging.setSelected(selected);
	}
	
	/**
	 * Controls the state of selection of components associated with the second tabbed pane.
	 * @param selected, true for selected.
	 */
	public static void setComponentsAssocWithSecondTabbedPaneSelected(boolean selected){
		jCheckBoxMenuItemDebugging.setSelected(selected);
		jToggleButtonMaximizeInteraction.setSelected(selected);
	}
		
	/*Declaration of MainFrame components*/
	private static javax.swing.JMenuBar jMenuBarMain;
	private javax.swing.JMenu jMenuFile,jMenuRender,jMenuWindow,jMenuHide;

	private javax.swing.JMenuItem jMenuItemExit;
	private static javax.swing.JMenuItem jMenuItemOpen,jMenuItemSave;

	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemPhysics,jCheckBoxMenuItemWireFrame,jCheckBoxMenuBounds,
	                                     jCheckBoxMenuItemNormals,jCheckBoxMenuItemLights,jCheckBoxMenuBufferDepth;

	private static javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemInteraction,jCheckBoxMenuItemDebugging,
	                                             jCheckBoxMenuItemDisplayForHints;
	                                              
	private javax.swing.JSeparator jSeparator1,jSeparator2,jSeparator3;

	private javax.swing.JToolBar.Separator jToolBarSeparator3,jToolBarSeparator4,jToolBarSeparator5,jToolBarSeparator6;

	private static javax.swing.JToolBar jToolBarGeneralControl;
	
	private javax.swing.JButton jButtonRunRealTime,jButtonRunStepByStep,jButtonRunFast,
	                           jButtonPause,jButtonTerminate,jButtonRestart;
	
	private static javax.swing.JButton jButtonSave,jButtonOpen;
	private static javax.swing.JToggleButton jToggleButtonConstructRobot,jToggleButtonVisualizer,
	                                         jToggleButtonMaximizeDebugging,jToggleButtonMaximizeInteraction;

	private static javax.swing.JTabbedPane jTabbedPaneFirst,jTabbedPaneSecond;
}

