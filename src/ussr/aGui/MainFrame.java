package ussr.aGui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;

import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;
import ussr.aGui.fileChooser.controllers.FileChooserXMLController;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveAsFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;
import ussr.aGui.tabs.TabsInter;

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
	public final int TAB_PANE_HEIGHT1 = (int)(SCREEN_DIMENSION.getHeight()-COMMON_HEIGHT-TAB_PANE_HEIGHT2-4*PADDING);
	
	/**
	 * The main GUI window.
	 */
	protected static MainFrame mainFrame;	
	
	/**
	 * The physical simulation.
	 */	   
	protected JMESimulation jmeSimulation;	
		
	/**
	 * File choosers in the form of Open,Save and Save as dialogs respectively.
	 */
	protected static  FramesInter fcOpenFrame,fcSaveFrame,fcSaveAsFrame;

	
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
	
		fcOpenFrame = new FileChooserOpenFrame(fileExtensions,fcXMLController);	
		fcSaveFrame = new FileChooserSaveFrame(fileExtensions,fcXMLController);
		
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put("Simulation Configuration", ".xml");
		fileDescriptionsAndExtensions.put("Robot Morphology", ".xml");
		
		fcSaveAsFrame = new FileChooserSaveAsFrame(fileDescriptionsAndExtensions,fcXMLController);
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
		jMenuItemSaveAs = new javax.swing.JMenuItem();

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

		jMenuItemSaveAs.setText("Save As...");
		//jMenuItemSaveAs.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+SAVE_SMALL));
		//jMenuItemSave.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OFF_LINE_SMALL));		
		jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.saveActionPerformed(fcSaveAsFrame);
			}
		});
		
		jMenuFile.add(jMenuItemSaveAs);
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
		jSeparator3 = new javax.swing.JToolBar.Separator();			
		jSeparator4 = new javax.swing.JToolBar.Separator();
		jButtonConstructRobot = new javax.swing.JButton();
		
		/*Description of components*/
		jButtonRunRealTime.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_REAL_TIME));
		jButtonRunRealTime.setToolTipText("Run real time");		
		jButtonRunRealTime.setFocusable(false);    
		jButtonRunRealTime.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunRealTime.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunRealTimeActionPerformed(jmeSimulation);        	  
			}
		});
		jToolBarGeneralControl.add(jButtonRunRealTime);

		jButtonRunFast.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_FAST));
		jButtonRunFast.setToolTipText("Run fast");
		jButtonRunFast.setFocusable(false);
		jButtonRunFast.setPreferredSize(new java.awt.Dimension(30, 30));      
		jButtonRunFast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonRunFastActionPerformed(jmeSimulation);        	  
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
				MainFrameController.jButtonRunStepByStepActionPerformed(jmeSimulation);
			}
		});
		jToolBarGeneralControl.add(jButtonRunStepByStep);

		jButtonPause.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + PAUSE));
		jButtonPause.setToolTipText("Pause");
		jButtonPause.setFocusable(false);   
		jButtonPause.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jButtonPauseActionPerformed(jmeSimulation);
			}
		});
		jToolBarGeneralControl.add(jButtonPause);
		
		jSeparator3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jSeparator3);
		
		jToolBarGeneralControl.add(initSaveButton());
		jToolBarGeneralControl.add(initOpenButton());
		
		jSeparator4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarGeneralControl.add(jSeparator4);
		
		
		jButtonConstructRobot.setToolTipText("Construct Robot");
		jButtonConstructRobot.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + CONSTRUCT_ROBOT ));
		jButtonConstructRobot.setDisabledIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + NO_ENTRANCE));
		jButtonConstructRobot.setFocusable(false);		
		jButtonConstructRobot.setPreferredSize(new java.awt.Dimension(30, 30));
		jButtonConstructRobot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.constructRobotActionPerformed(jButtonConstructRobot,jTabbedPaneFirst, tabs, checkBoxMenuItems );
			}
		});
		jToolBarGeneralControl.add(jButtonConstructRobot);	
		

		getContentPane().add(jToolBarGeneralControl);
	}
	
	
	public static javax.swing.JButton  initSaveButton(){
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
	
	public static javax.swing.JButton  initOpenButton(){
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
		initTabsAndCheckBoxes(true,tabsFirstTabbedPane,jTabbedPaneFirst,jMenuIntearctionTabs);//Plug in tabs in tabbed pane and check boxes in menu bar		
	
		getContentPane().add(jTabbedPaneFirst);
		return jTabbedPaneFirst;
	}
    
	static ArrayList<javax.swing.JCheckBoxMenuItem> checkBoxMenuItems = new ArrayList<javax.swing.JCheckBoxMenuItem>();
	
	private void  initTabsAndCheckBoxes(boolean first,final ArrayList<TabsInter> tabsContainer ,final javax.swing.JTabbedPane jTabbedPane,javax.swing.JMenu jMenu){
		
		for (int index =0; index < tabsContainer.size(); index++){
		//	System.out.println("S:" +tabsContainer.size());
			TabsInter currentTab = tabsContainer.get(index);	
			
			
		
			
			if (currentTab.getImageIconDirectory()==null){			
				jTabbedPane.addTab(currentTab.getTabTitle(),currentTab.getJComponent());				
			}else{				
				jTabbedPane.addTab(currentTab.getTabTitle(),new javax.swing.ImageIcon(currentTab.getImageIconDirectory()),currentTab.getJComponent());
			}		
			
			

			jCheckBoxMenuItemNew = new javax.swing.JCheckBoxMenuItem();
			jCheckBoxMenuItemNew.setSelected(true);
			jCheckBoxMenuItemNew.setText(currentTab.getTabTitle());
			checkBoxMenuItems.add(jCheckBoxMenuItemNew);
			jCheckBoxMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					MainFrameController.jCheckBoxMenuItemActionPerformedNew(evt,checkBoxMenuItems, jTabbedPane,tabsContainer);
					
				}
			});
			jMenu.add(jCheckBoxMenuItemNew);
		//	System.out.println("Sss:" +checkBoxMenuItems.size());
			
		}	
		
		
		
		if (first){//Initially remove tabs for constructing robot
			AssignBehavior = (JCheckBoxMenuItem)jMenu.getMenuComponent(1);
			AssignBehavior.setSelected(false);
			checkBoxMenuItems.get(1).setSelected(false);
			jTabbedPane.removeTabAt(1);
			
			ConstructRobot = (JCheckBoxMenuItem)jMenu.getMenuComponent(0);
			ConstructRobot.setSelected(false);
			checkBoxMenuItems.get(0).setSelected(false);			
			jTabbedPane.removeTabAt(0);
			
		}
		//System.out.println("First:" +tabsFirstTabbedPane.size());
		//System.out.println("Second:" +tabsSecondTabbedPane.size());
	}
	
	public static ArrayList<javax.swing.JCheckBoxMenuItem> getCheckBoxMenuItems() {
		return checkBoxMenuItems;
	}

	private static JCheckBoxMenuItem AssignBehavior;
	private static JCheckBoxMenuItem ConstructRobot;
	
	public static JCheckBoxMenuItem getAssignBehavior() {
		return AssignBehavior;
	}

	public static JCheckBoxMenuItem getConstructRobot() {
		return ConstructRobot;
	}

	public void initSecondTabbedPane(int width, int height){
		jTabbedPane3 = new javax.swing.JTabbedPane();
		jTabbedPane3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane3.setPreferredSize(new Dimension(width, height));
		initTabsAndCheckBoxes(false, tabsSecondTabbedPane,jTabbedPane3,jMenu5);//Plug in tabs in tabbed pane and check boxes in menu bar		
		getContentPane().add(jTabbedPane3);
	}
   
	

	public javax.swing.JTabbedPane getJTabbedPane1() {
		return jTabbedPaneFirst;
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
	
	public static void setConstructionEnabled(boolean enabled){
		jButtonConstructRobot.setEnabled(false);		
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
	public static javax.swing.JMenuItem jMenuItemSaveAs;
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
	
	public javax.swing.JToolBar.Separator jSeparator3;
	public javax.swing.JToolBar.Separator jSeparator4;

	public javax.swing.JToolBar jToolBar1;
	public javax.swing.JToolBar jToolBarGeneralControl;

	public javax.swing.JButton jButtonRunRealTime;
	public javax.swing.JButton jButtonRunStepByStep;
	public static javax.swing.JButton jButtonSave;
	public static javax.swing.JButton jButtonOpen;
	public static javax.swing.JButton jButtonConstructRobot;
	
	public javax.swing.JButton jButtonRunFast;
	public javax.swing.JButton jButtonPause;

	public javax.swing.JLabel jLabel1;
	public javax.swing.JLabel jLabel3;
	public javax.swing.JLabel jLabel5;
	public javax.swing.JTextField jTextField1;

	public static javax.swing.JTabbedPane jTabbedPaneFirst;
	public static javax.swing.JTabbedPane jTabbedPane3;



	public javax.swing.JSplitPane jSplitPane1;

}

