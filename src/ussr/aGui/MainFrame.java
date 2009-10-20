package ussr.aGui;

import java.awt.Dimension;
import java.util.ArrayList;

import ussr.aGui.fileChooser.controller.FileChooserControllerInter;
import ussr.aGui.fileChooser.controller.FileChooserXMLController;
import ussr.aGui.fileChooser.view.FileChooserOpenFrame;
import ussr.aGui.fileChooser.view.FileChooserSaveFrame;
import ussr.aGui.tabs.view.TabsInter;

import ussr.physics.jme.JMESimulation;

/**
 * 
 * @author Konstantinas
 */
public abstract class MainFrame extends GuiFrames implements MainFrameInter {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Height of the second tabbed pane.
	 */
	public final int TAB_PANE_HEIGHT2 = 100;

	/**
	 * Height of the first tabbed pane.
	 */
	public final int TAB_PANE_HEIGHT1 = (int)(SCREEN_DIMENSION.getHeight()-TOOLBARS_HEIGHT-TAB_PANE_HEIGHT2-4*PADDING);
	
	/**
	 * The main GUI window.
	 */
	protected static MainFrame mainFrame;	
	
	/**
	 * The physical simulation.
	 */	   
	protected JMESimulation jmeSimulation;	
		
	/**
	 * File chooser in the form of Open dialog.
	 */
	protected  FramesInter fcOpenFrame;

	/**
	 * File chooser in the form of Save dialog.
	 */
	protected  FramesInter fcSaveFrame;	
	
	/**
	 * Container for keeping main GUI window components, the height of which determine the height of the window.  
	 */
	protected ArrayList<javax.swing.JComponent> components = new ArrayList<javax.swing.JComponent>();
	
	/**
	 * Container for keeping all tabs pluged-in the main GUI window(MainFrame).
	 */
	protected  ArrayList<TabsInter> tabs = new ArrayList <TabsInter>();
	
	
	protected ArrayList<TabsInter> tabsFirstTabbedPane = new ArrayList <TabsInter>();
	
	protected  ArrayList<TabsInter> tabsSecondTabbedPane = new ArrayList <TabsInter>();
	
	
	
	/**
	 * Initializes file choosers in two forms: 1)Open and 2)Save dialog.
	 */
	public void initFileChoosers () {	
		ArrayList <String> fileExtensions = new ArrayList<String>();
		fileExtensions.add(".xml");//TODO MOVE TO fc INTERFACE

		FileChooserControllerInter fcXMLController = new FileChooserXMLController(this.jmeSimulation);
		ArrayList<FileChooserControllerInter> fcControllers = new ArrayList<FileChooserControllerInter>();
		fcControllers.add(fcXMLController);

		fcOpenFrame = new FileChooserOpenFrame(fileExtensions,fcControllers);	
		fcSaveFrame = new FileChooserSaveFrame(fileExtensions,fcControllers);
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
		getContentPane().setLayout(new java.awt.FlowLayout());		
	}
	
	
	/**
	 * Initializes visual appearance(view) of menu bar.
	 */
	public void initJMenuBar(){
		
		jMenuBarMain = new javax.swing.JMenuBar();		

		jMenuFile = new javax.swing.JMenu();
		jMenuView = new javax.swing.JMenu();
		jMenuRender = new javax.swing.JMenu();
		jMenuIntearctionTabs = new javax.swing.JMenu();
		jMenu5 = new javax.swing.JMenu();		

		jMenuItemOpen = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();

		jMenuItemExit = new javax.swing.JMenuItem();		
		jMenuItemSave = new javax.swing.JMenuItem();

		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();			

		jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem6 = new javax.swing.JCheckBoxMenuItem();
		
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
				MainFrameController.jMenuItem1ActionPerformed(mainFrame);
			}
		});
		jMenuFile.add(jMenuItemExit);

		jMenuBarMain.add(jMenuFile);

		jMenuView.setText("View");
		jMenuIntearctionTabs.setText("Interaction Tabs");

		jMenuView.add(jMenuIntearctionTabs);
		
		jMenu5.setText("Output Tabs");
		jMenuView.add(jMenu5);

		jMenuBarMain.add(jMenuView); 

		jMenuRender.setText("Render");
		jCheckBoxMenuItem1.setSelected(false);
		jCheckBoxMenuItem1.setText("Physics");
		jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem1ActionPerformed(jCheckBoxMenuItem1,jmeSimulation);
			}
		});
		jMenuRender.add(jCheckBoxMenuItem1);

		jCheckBoxMenuItem2.setSelected(false);
		jCheckBoxMenuItem2.setText("Wire Frame");
		jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem2ActionPerformed(jCheckBoxMenuItem2,jmeSimulation);
			}
		});
		jMenuRender.add(jCheckBoxMenuItem2);

		jCheckBoxMenuItem3.setSelected(false);
		jCheckBoxMenuItem3.setText("Bounds");
		jCheckBoxMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem3ActionPerformed(jCheckBoxMenuItem3,jmeSimulation);
			}
		});
		jMenuRender.add(jCheckBoxMenuItem3);

		jCheckBoxMenuItem4.setSelected(false);
		jCheckBoxMenuItem4.setText("Normals");
		jCheckBoxMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem4ActionPerformed(jCheckBoxMenuItem4,jmeSimulation);
			}
		});
		jMenuRender.add(jCheckBoxMenuItem4);

		jCheckBoxMenuItem5.setSelected(false);
		jCheckBoxMenuItem5.setText("Lights");
		jCheckBoxMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem5ActionPerformed(jCheckBoxMenuItem5,jmeSimulation);
			}
		});
		jMenuRender.add(jCheckBoxMenuItem5);

		jCheckBoxMenuItem6.setSelected(false);
		jCheckBoxMenuItem6.setText("Buffer Depth");
		jCheckBoxMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem6ActionPerformed(jCheckBoxMenuItem6,jmeSimulation);
			}
		});

		jMenuRender.add(jCheckBoxMenuItem6);

		jMenuBarMain.add(jMenuRender);
		
		setJMenuBar(jMenuBarMain); 
	}
	
	/**
	 * Initializes the tool bar for simulation control
	 * @param width, tool bar width.
	 * @param height, tool bar height.
	 */
	public void initJToolbarSimulationControl(int width,int height){
		jToolBarSimulationControl = new javax.swing.JToolBar();

		jButtonRunRealTime = new javax.swing.JButton();
		jButtonRunStepByStep = new javax.swing.JButton();
		jButtonSave = new javax.swing.JButton();
		jButtonOpen = new javax.swing.JButton();
		jButtonRunFast = new javax.swing.JButton();
		jButtonPause = new javax.swing.JButton();
		
		jToolBarSimulationControl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarSimulationControl.setRollover(true);
		jToolBarSimulationControl.setFloatable(false);
		jToolBarSimulationControl.setToolTipText("Simulation Control");
		jToolBarSimulationControl.setPreferredSize(new Dimension(width,height));
		

		jButtonRunRealTime.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_REAL_TIME));
		jButtonRunRealTime.setToolTipText("Run real time");
		//jButtonRunRealTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButtonRunRealTime.setFocusable(false);    
		jButtonRunRealTime.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunRealTime.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButton1ActionPerformed(jmeSimulation);        	  
			}
		});
		jToolBarSimulationControl.add(jButtonRunRealTime);

		jButtonRunFast.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_FAST));
		jButtonRunFast.setToolTipText("Run fast");
		jButtonRunFast.setFocusable(false);
		jButtonRunFast.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunFast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunFastActionPerformed(jmeSimulation);        	  
			}
		});

		jToolBarSimulationControl.add(jButtonRunFast);

		jButtonRunStepByStep.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + STEP_BY_STEP));
		jButtonRunStepByStep.setToolTipText("Run step by step");
		//jButtonRunStepByStep.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
		jButtonRunStepByStep.setFocusable(false);
		jButtonRunStepByStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonRunStepByStep.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonRunStepByStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButtonRunStepByStep.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunStepByStepActionPerformed(jmeSimulation);
			}
		});
		jToolBarSimulationControl.add(jButtonRunStepByStep);

		jButtonPause.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PAUSE));
		jButtonPause.setToolTipText("Pause");
		jButtonPause.setFocusable(false);   
		jButtonPause.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonPauseActionPerformed(jmeSimulation);
			}
		});
		jToolBarSimulationControl.add(jButtonPause);

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
		jToolBarSimulationControl.add(jButtonSave);

		jButtonOpen.setToolTipText("Open");
		jButtonOpen.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OPEN));
		jButtonOpen.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonOpen.setFocusable(true);		
		jButtonOpen.setFocusable(false);		
		jButtonOpen.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.openActionPerformed(fcOpenFrame);
			}
		});
		jToolBarSimulationControl.add(jButtonOpen);

		getContentPane().add(jToolBarSimulationControl);
	}

	
	
	public javax.swing.JTabbedPane initFirstTabbbedPane( ){
		jTabbedPaneInteraction  = new javax.swing.JTabbedPane();
		jTabbedPaneInteraction.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPaneInteraction.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jTabbedPaneInteraction.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2, TAB_PANE_HEIGHT1));		
		jTabbedPaneInteraction.setFocusable(false);		
		initTabsAndCheckBoxes(tabsFirstTabbedPane,jTabbedPaneInteraction,jMenuIntearctionTabs);//Plug in tabs in tabbed pane and check boxes in menu bar		

		getContentPane().add(jTabbedPaneInteraction);
		return jTabbedPaneInteraction;
	}
	
	private void initTabsAndCheckBoxes(final ArrayList<TabsInter>tabs ,final javax.swing.JTabbedPane jTabbedPane,javax.swing.JMenu jMenu){
		final ArrayList<javax.swing.JCheckBoxMenuItem> checkBoxMenuItems = new ArrayList<javax.swing.JCheckBoxMenuItem>();
		for (int index =0; index < tabs.size(); index++){					  
			TabsInter currentTab = tabs.get(index);
			if (tabs.get(index).getImageIconDirectory()==null){
				jTabbedPane.addTab(currentTab.getTabTitle(),currentTab.getJComponent());				
			}else{
				jTabbedPane.addTab(currentTab.getTabTitle(),new javax.swing.ImageIcon(currentTab.getImageIconDirectory()),currentTab.getJComponent());
			}

			jCheckBoxMenuItemNew = new javax.swing.JCheckBoxMenuItem();
			jCheckBoxMenuItemNew.setSelected(true);
			jCheckBoxMenuItemNew.setText(tabs.get(index).getTabTitle());
			checkBoxMenuItems.add(jCheckBoxMenuItemNew);
			jCheckBoxMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					MainFrameController.jCheckBoxMenuItemActionPerformedNew(checkBoxMenuItems, jTabbedPane,tabs);
				}
			});
			jMenu.add(jCheckBoxMenuItemNew);			
		}
	}
	
	public void initSecondTabbedPane(int width, int height){
		jTabbedPane3 = new javax.swing.JTabbedPane();
		jTabbedPane3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane3.setPreferredSize(new Dimension(width, height));
		initTabsAndCheckBoxes(tabsSecondTabbedPane,jTabbedPane3,jMenu5);//Plug in tabs in tabbed pane and check boxes in menu bar		
		getContentPane().add(jTabbedPane3);
	}
   
	

	public javax.swing.JTabbedPane getJTabbedPane1() {
		return jTabbedPaneInteraction;
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
	public javax.swing.JMenu jMenuIntearctionTabs;
	public javax.swing.JMenu jMenu5;


	public javax.swing.JMenuItem jMenuItemExit;
	public static javax.swing.JMenuItem jMenuItemOpen;
	public static javax.swing.JMenuItem jMenuItemSave;
	public javax.swing.JMenuItem jMenuItem4;

	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem6;	
	public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemNew;

	public javax.swing.JSeparator jSeparator1;
	public javax.swing.JSeparator jSeparator2;

	public javax.swing.JToolBar jToolBar1;
	public javax.swing.JToolBar jToolBarSimulationControl;

	public javax.swing.JButton jButtonRunRealTime;
	public javax.swing.JButton jButtonRunStepByStep;
	public static javax.swing.JButton jButtonSave;
	public static javax.swing.JButton jButtonOpen;

	public javax.swing.JButton jButtonRunFast;
	public javax.swing.JButton jButtonPause;

	public javax.swing.JLabel jLabel1;
	public javax.swing.JLabel jLabel3;
	public javax.swing.JLabel jLabel5;
	public javax.swing.JTextField jTextField1;

	public static javax.swing.JTabbedPane jTabbedPaneInteraction;
	public static javax.swing.JTabbedPane jTabbedPane3;



	public javax.swing.JSplitPane jSplitPane1;

}

