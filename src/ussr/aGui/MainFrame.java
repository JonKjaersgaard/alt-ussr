package ussr.aGui;


import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import com.jme.input.KeyInput;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvasImplementor;
import com.jme.system.lwjgl.LWJGLSystemProvider;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;
import com.jmex.awt.lwjgl.LWJGLCanvas;

import ussr.aGui.fileChooser.appearance.FileChooserOpenFrame;
import ussr.aGui.fileChooser.appearance.FileChooserSaveFrame;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;
import ussr.aGui.fileChooser.controllers.FileChooserXMLController;
import ussr.aGui.tabs.AssignBehavioursTab;
import ussr.aGui.tabs.ConstructionTab;
import ussr.aGui.tabs.NewTab;
import ussr.aGui.tabs.TabsInter;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of main GUI window. 
 * @author Konstantinas
 */
public class MainFrame extends GuiFrames implements MainFrameInter {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The main GUI window.
	 */
	private static MainFrame mainFrame;	

	/**
	 * The physical simulation.
	 */	   
	private JMESimulation jmeSimulation;	

	/**
	 * File chooser in the form of Open dialog.
	 */
	private  GuiInter fcOpenFrame;

	/**
	 * File chooser in the form of Save dialog.
	 */
	private  GuiInter fcSaveFrame;

	/**
	 * Container for keeping tabs pluged-in the main GUI window(MainFrame).
	 */
	private  ArrayList<TabsInter> tabs = new ArrayList <TabsInter>();

	/**
	 * Container for keeping main GUI window components, the height of which determine the height of the window.  
	 */
	private ArrayList<javax.swing.JComponent> components = new ArrayList<javax.swing.JComponent>();  

	/**
	 * Canvas for integrating JME simulation window into Swing GUI. 
	 */
	private  LWJGLCanvas canvas = null;

	/**
	 * Implementation of JME canvas.
	 */
	private JMECanvasImplementor impl;

	/**
	 * Used to indicate that the main window is started stand alone (not during the simulation).
	 */
	private boolean standAlone;

	/**
	 * Starts the main GUI window before the simulation is started(stand alone).
	 * @param standAlone, Used to indicate that the main window is started stand alone (not during the simulation). 
	 */
	public MainFrame(boolean standAlone) {
		this.standAlone = standAlone;
		initFileChoosers();// initialize visual appearance of file choosers. Why here, because then they are responding faster to user generated events. 
		setDefaultAppearanceOfTabs();// add default tabs
		initComponents();//initialize visual appearance of main GUI window.	
		//setSizeFullScreen(this);
	}

	/**
	 * Adds default tabs into container. 
	 */
	private void setDefaultAppearanceOfTabs(){
		/*MORE HERE, THINK ABOUT DEFAULT SIMULATION*/
		//tabs.add(new ConstructionTab("1 Step: Construct Robot (Interactive User Guide)",jmeSimulationDefault));//Build in tab
		//tabs.add(new AssignBehavioursTab("2 Step: Assign Behaviour (Interactive User Guide)",jmeSimulationDefault));//Build in tab
		tabs.add(new NewTab("YOUR NEW TAB",null));//YOUR NEW TAB
		tabs.add(new NewTab("YOUR NEW TAB1",null));//YOUR NEW TAB1				
	}

	/**
	 * Starts the main GUI window (frame) during simulation. Simulation starts first and after that main GUI window is started.
	 * This can be achieved by pressing "O" on keyboard after starting the simulation. 
	 * @param jmeSimulation, the physical simulation.
	 * @param tabs, the tabs to plug in into main window's tabbed pane.
	 */
	public MainFrame(JMEBasicGraphicalSimulation jmeSimulation, ArrayList<TabsInter> tabs){
		this.jmeSimulation = (JMESimulation) jmeSimulation;
		this.tabs = tabs;
		initFileChoosers();// initialize visual appearance of file choosers. Why here, because then they are responding faster to user generated events. 		
		initComponents();//initialize visual appearance of main GUI window.	
		changeInstanceFlagListener();//Change the instance flag to true. Meaning the window is once instantiated.
		windowResizingListener();//Resize the main GUI window according to dimension of it's components, if user is maximizing or restoring it down.
	}

	/**
	 * Instance flag for current frame, used to keep track that only one instance of the frame is instantiated.
	 * This is for running the main GUI window during simulation.
	 */
	private static boolean instanceFlag = false;

	/**
	 * Changes the flag for window instantiation. If it was once instantiated it is true. When the window closes the flag is reset to false, 
	 * so that the window can be activated again.
	 */
	private void changeInstanceFlagListener(){
		instanceFlag = true;// the frame is instantiated
		// Overrides event for closing the frame, in order for the frame to do not open several times with several times pressing on the button on keyboard.		
		addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				instanceFlag = false; // reset the flag after closing the frame event appears
				event.getWindow().dispose();	                     
			}			
		}
		);		
	}

	/**
	 * 
	 */
	private void windowResizingListener(){
		this.addWindowStateListener (new WindowAdapter() {	
			public void windowStateChanged(WindowEvent event) {

				int newState = event.getNewState();
				if (newState == 6){//Window maximized
					for(int index=0;index<components.size();index++){
						components.get(index).setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING/2,components.get(index).getHeight()));
					}			    		
				}else if(newState == 0){//Window restored down to its initial dimension.
					for(int index=0;index<components.size();index++){
						components.get(index).setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,components.get(index).getHeight()));
					}
				}
			}
		}
		);		
	} 

	/**
	 * Initializes file choosers in two forms: 1)Open and 2)Save dialog.
	 */
	private  void initFileChoosers () {	
		ArrayList <String> fileExtensions = new ArrayList<String>();
		fileExtensions.add(".xml");

		FileChooserControllerInter fcXMLController = new FileChooserXMLController();
		ArrayList<FileChooserControllerInter> fcControllers = new ArrayList<FileChooserControllerInter>();
		fcControllers.add(fcXMLController);

		fcOpenFrame = new FileChooserOpenFrame(fileExtensions,fcControllers);	
		fcSaveFrame = new FileChooserSaveFrame(fileExtensions,fcControllers);
	}

	/**
	 * Initializes the visual appearance of all components in the main GUI window.
	 * Follows Strategy  pattern.
	 */
	protected void initComponents() {

		/*Instantiation of MainFrame components*/
		jMenuBar1 = new javax.swing.JMenuBar();		

		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		jMenu3 = new javax.swing.JMenu();
		jMenu4 = new javax.swing.JMenu();

		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();

		jMenuItem1 = new javax.swing.JMenuItem();		
		jMenuItem3 = new javax.swing.JMenuItem();

		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();			

		jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();
		jCheckBoxMenuItem6 = new javax.swing.JCheckBoxMenuItem();

		jToolBar1 = new javax.swing.JToolBar();
		jToolBarSimulationControl = new javax.swing.JToolBar();

		jButtonRunRealTime = new javax.swing.JButton();
		jButtonRunStepByStep = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButtonRunFast = new javax.swing.JButton();
		jButtonPause = new javax.swing.JButton();

		jLabel1 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();

		jTextField1 = new javax.swing.JTextField(); 

		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		jSplitPane1 = new javax.swing.JSplitPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUSSRicon(this);
		setTitle("Unified Simulator for Self-Reconfigurable Robots");
		getContentPane().setLayout(new java.awt.FlowLayout());	

		jToolBarSimulationControl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBarSimulationControl.setRollover(true);
		jToolBarSimulationControl.setFloatable(false);
		jToolBarSimulationControl.setToolTipText("Simulation Control");
		jToolBarSimulationControl.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		//jToolBar2.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,TOOLBAR_HEIGHT));

		jButtonRunRealTime.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + RUN_REAL_TIME));
		jButtonRunRealTime.setToolTipText("Run real time");
		jButtonRunRealTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
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
		jButtonRunStepByStep.setBorder(javax.swing.BorderFactory.createEtchedBorder());//On Vista does not work(test it)
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
				MainFrameController.jButton7ActionPerformed(jmeSimulation);
			}
		});
		jToolBarSimulationControl.add(jButtonPause);


		jButton3.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + SAVE));
		jButton3.setToolTipText("Save");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setPreferredSize(new java.awt.Dimension(30, 30));
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem3ActionPerformed(fcSaveFrame);
			}
		});
		jToolBarSimulationControl.add(jButton3);


		jButton4.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + OPEN));
		jButton4.setFocusable(true);
		jButton4.setToolTipText("Open");
		jButton4.setFocusable(false);		
		jButton4.setPreferredSize(new java.awt.Dimension(30, 30));
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem2ActionPerformed(fcOpenFrame);
			}
		});
		jToolBarSimulationControl.add(jButton4);

		getContentPane().add(jToolBarSimulationControl);



		//jLabel3.setText("Interaction-Input");
		// getContentPane().add(jLabel3);

		jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane1.setToolTipText("Interaction with simulation environment");
		jTabbedPane1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING, TAB_PANE_HEIGHT1));
		//jTabbedPane1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,TAB_PANE_HEIGHT1));
		jTabbedPane1.setFocusable(false);		

		final ArrayList<javax.swing.JCheckBoxMenuItem> checkBoxMenuItems = new ArrayList<javax.swing.JCheckBoxMenuItem>();
		for (int index =0; index < tabs.size(); index++){					  
			TabsInter currentTab = tabs.get(index); 
			jTabbedPane1.add(tabs.get(index).getTabTitle(),currentTab.getJPanel1000());

			jCheckBoxMenuItemNew = new javax.swing.JCheckBoxMenuItem();
			jCheckBoxMenuItemNew.setSelected(true);
			jCheckBoxMenuItemNew.setText(tabs.get(index).getTabTitle());
			checkBoxMenuItems.add(jCheckBoxMenuItemNew);
			jCheckBoxMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					MainFrameController.jCheckBoxMenuItemActionPerformedNew(checkBoxMenuItems, jTabbedPane1,tabs);
				}
			});
			jMenu4.add(jCheckBoxMenuItemNew);			
		}

		getContentPane().add(jTabbedPane1);		


		if (standAlone){// before simulation
			jSplitPane1.setLeftComponent(jTabbedPane1);
			jSplitPane1.setRightComponent(initJmeSimulationCanvas());
			getContentPane().add(jSplitPane1);
		}else{// During simulation
			getContentPane().add(jTabbedPane1);
		}




		//jTextArea1.setColumns(20);
		//jTextArea1.setRows(5);
		//jTextArea1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		//jScrollPane1.setViewportView(jTextArea1);

		//getContentPane().add(jScrollPane1);

		jTabbedPane3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane3.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING, TAB_PANE_HEIGHT2));
		//jTabbedPane3.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,TAB_PANE_HEIGHT1));
		getContentPane().add(jTabbedPane3);


		jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);//user can not make the tool bar to float
		jToolBar1.setRollover(true);// the buttons inside are roll over
		jToolBar1.setToolTipText("Command Line Interface");
		jToolBar1.setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()/2-PADDING,TOOLBAR_HEIGHT));
		getContentPane().add(jToolBar1);       

		jLabel1.setText(" CLI ");
		jLabel1.setToolTipText("Command Line Interface");
		jToolBar1.add(jLabel1);

		jTextField1.setText("Enter command");
		jTextField1.setToolTipText("Command Line Interface");
		jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				MainFrameController.jTextField1FocusGained( jTextField1 );
			}
		});
		jToolBar1.add(jTextField1);

		jMenu1.setText("File");

		jMenuItem2.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+OPEN_SMALL));
		jMenuItem2.setText("Open");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem2ActionPerformed(fcOpenFrame);
			}
		});

		jMenu1.add(jMenuItem2);

		jMenuItem4.setText("Open default");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				/*MainFrameController.*///jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem4);      

		jMenu1.add(jSeparator2);

		jMenuItem3.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+SAVE_SMALL));
		jMenuItem3.setText("Save");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem3ActionPerformed(fcSaveFrame);
			}
		});

		jMenu1.add(jMenuItem3);
		jMenu1.add(jSeparator1);

		jMenuItem1.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS+EXIT));
		jMenuItem1.setText("Exit");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jMenuItem1ActionPerformed(mainFrame);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("View");
		jMenu4.setText("Tabs");

		jMenu2.add(jMenu4);

		jMenuBar1.add(jMenu2);  


		jMenu3.setText("Render");
		jCheckBoxMenuItem1.setSelected(false);
		jCheckBoxMenuItem1.setText("Physics");
		jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem1ActionPerformed(jCheckBoxMenuItem1,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem1);

		jCheckBoxMenuItem2.setSelected(false);
		jCheckBoxMenuItem2.setText("Wire Frame");
		jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem2ActionPerformed(jCheckBoxMenuItem2,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem2);

		jCheckBoxMenuItem3.setSelected(false);
		jCheckBoxMenuItem3.setText("Bounds");
		jCheckBoxMenuItem3.setText("Bounds");
		jCheckBoxMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem3ActionPerformed(jCheckBoxMenuItem3,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem3);

		jCheckBoxMenuItem4.setSelected(false);
		jCheckBoxMenuItem4.setText("Normals");
		jCheckBoxMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem4ActionPerformed(jCheckBoxMenuItem4,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem4);

		jCheckBoxMenuItem5.setSelected(false);
		jCheckBoxMenuItem5.setText("Lights");
		jCheckBoxMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem5ActionPerformed(jCheckBoxMenuItem5,jmeSimulation);
			}
		});
		jMenu3.add(jCheckBoxMenuItem5);

		jCheckBoxMenuItem6.setSelected(false);
		jCheckBoxMenuItem6.setText("Buffer Depth");
		jCheckBoxMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameController.jCheckBoxMenuItem6ActionPerformed(jCheckBoxMenuItem6,jmeSimulation);
			}
		});

		jMenu3.add(jCheckBoxMenuItem6);

		jMenuBar1.add(jMenu3);

		setJMenuBar(jMenuBar1);

		pack();   

        /*Keep all components which should be expanded in width when the window is resized to full screen or restored down (height of components)*/
		components.add(jMenuBar1);
		components.add(jToolBarSimulationControl);
		components.add(jTabbedPane3);
		components.add(jToolBar1);

	//	for (int  index =0;index<components.size();index++){
		//	windowHeight = windowHeight+ components.get(index).getHeight();
			//System.out.println("height:"+heightComponents.get(index).getHeight());
//		}

		//setDimensionsOf(this,(int)SCREEN_DIMENSION.getWidth()/2,windowHeight+2*PADDING);
		
		
		if (standAlone){
			components.add(jSplitPane1);
			setSizeFullScreen(this, components);	
		}else{
			components.add(jTabbedPane1);
			setSizeAccordingComponents(this, components); 
		}
		//Move the window to the center.
		//setLocationRelativeTo(null);
		changeToSetLookAndFeel(this);// makes troubles with the borders of the buttons 
	}


	/**
	 * Initializes JME simulation canvas.
	 */
	private LWJGLCanvas initJmeSimulationCanvas(){
		// make the canvas:
		DisplaySystem display = DisplaySystem.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
		display.registerCanvasConstructor("AWT", LWJGLAWTCanvasConstructor.class);
		canvas = (LWJGLCanvas)display.createCanvas(200, 200);
		canvas.setUpdateInput(true);
		canvas.setTargetRate(60);		

		// add a listener... if window is resized, we can do something about
		// it.
		canvas.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				//doResize();
			}
		});

		// Setup key and mouse input
		KeyInput.setProvider(KeyInput.INPUT_AWT);
		KeyListener kl = (KeyListener) KeyInput.get();
		canvas.addKeyListener(kl);
		AWTMouseInput.setup(canvas, false);

		// Important! Here is where we add the guts to the panel:
		impl = new MyImplementor(200, 200);
		canvas.setImplementor(impl);

		canvas.setBounds(0, 0, 200, 200);
		//canvase
		//canvas.setFocusable(true);

		//getContentPane().add(canvas);
		return canvas;
	}

	public javax.swing.JTabbedPane getJTabbedPane1() {
		return jTabbedPane1;
	}

	/**
	 * Enables and disables menu components opening file choosers. 
	 * @param state
	 */
	public static void setSaveOpenEnabled (boolean state){
		jButton3.setEnabled(state);
		jMenuItem2.setEnabled(state);
		jMenuItem3.setEnabled(state);	
		jButton4.setEnabled(state);
	} 




	/**
	 * Starts the main GUI window (frame).
	 * Follows strategy pattern. 
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {            	
				mainFrame = new MainFrame(true);
				mainFrame.setVisible(true);

			}
		});
	}

	/**
	 * Starts the main GUI window (frame) after the simulation was started.
	 * This can be achieved by pressing "O" on keyboard.
	 * @param simulation, the basic graphical simulation.
	 */
	public void activateDuringSimulation(){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {            	
				mainFrame = new MainFrame(jmeSimulation,tabs);
				mainFrame.setVisible(true);
				jmeSimulation.setPause(true);// Force pausing of simulation
			}
		});
	}	

	/**
	 * Starts the main GUI window (frame) stand alone.
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new MainFrame(true);				
				mainFrame.setVisible(true);
			}
		});
	}

	/**
	 * Returns true if the frame(main GUI window) is already instantiated.
	 * @return true, if the frame(main GUI window) is already instantiated.
	 */
	public static boolean isInstanceFlag() {
		return instanceFlag;
	}


	/*Declaration of MainFrame components*/
	private javax.swing.JMenuBar jMenuBar1;

	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu4;	

	private javax.swing.JMenuItem jMenuItem1;
	private static javax.swing.JMenuItem jMenuItem2;
	private static javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;

	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem6;	
	private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemNew;

	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;

	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JToolBar jToolBarSimulationControl;

	private javax.swing.JButton jButtonRunRealTime;
	private javax.swing.JButton jButtonRunStepByStep;
	private static javax.swing.JButton jButton3;
	private static javax.swing.JButton jButton4;

	private javax.swing.JButton jButtonRunFast;
	private javax.swing.JButton jButtonPause;

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JTextField jTextField1;

	private static javax.swing.JTabbedPane jTabbedPane1  = new javax.swing.JTabbedPane();
	private static javax.swing.JTabbedPane jTabbedPane3 = new javax.swing.JTabbedPane();

	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JSplitPane jSplitPane1;

}

