package ussr.aGui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

import com.jme.input.KeyInput;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvasImplementor;
import com.jme.system.lwjgl.LWJGLSystemProvider;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;
import com.jmex.awt.lwjgl.LWJGLCanvas;

import ussr.aGui.tabs.view.ConsoleTab;
import ussr.aGui.tabs.view.NewTab;
import ussr.aGui.tabs.view.TabsInter;

public class MainFrameAllInOne extends MainFrame {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Canvas for integrating JME simulation window into Swing GUI. 
	 */
	private  LWJGLCanvas canvas = null;
	
	/**
	 * Implementation for JME canvas.
	 */
	private JMECanvasImplementor impl;
	

	/**
	 * Displays the view(appearance) of main GUI, where all components of the design are available in one JFrame. 
	 */
	public MainFrameAllInOne() {
		initFileChoosers();// initialize visual appearance of file choosers. Why here, because then they are responding faster to user generated events, because they are compiled earlier). 
		setDefaultAppearanceOfTabs();// add default tabs
		initComponents();
	}
	
	public void initComponents(){
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		initFrameProperties();
		initJMenuBar();
		initJToolbarSimulationControl((int)SCREEN_DIMENSION.getWidth()-PADDING,TOOLBAR_HEIGHT);//TODO CHANGE null with default simulation
		
		jSplitPane1 = new javax.swing.JSplitPane();
		jSplitPane1.setLeftComponent(initFirstTabbbedPane());
		jSplitPane1.setRightComponent(initJmeSimulationCanvas((int)SCREEN_DIMENSION.getWidth()/2-PADDING/2,TAB_PANE_HEIGHT1));
		getContentPane().add(jSplitPane1);
		
		initSecondTabbedPane((int)SCREEN_DIMENSION.getWidth()-PADDING, TAB_PANE_HEIGHT2);	
		pack(); 
		changeToSetLookAndFeel(this);
		
		components.add(jMenuBarMain);
		components.add(jToolBarSimulationControl);
		components.add(jSplitPane1);
		components.add(jTabbedPane3);
		
		setFrameHeightAccordingComponents(this,(int)SCREEN_DIMENSION.getWidth(),components); 
		
	}
	
	/**
	 * Displays the view(appearance) of main GUI, where all swing components of the design are available in one JFrame. 
	 * @param args, array of arguments.
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new MainFrameAllInOne();				
				mainFrame.setVisible(true);
			}
		});		
	}
	
	
	/**
	 * Adds default tabs into container. 
	 */
	private void setDefaultAppearanceOfTabs(){
		/*MORE HERE, THINK ABOUT DEFAULT SIMULATION*/
		//tabs.add(new ConstructionTab("1 Step: Construct Robot (Interactive User Guide)",jmeSimulationDefault));//Build in tab
		//tabs.add(new AssignBehavioursTab("2 Step: Assign Behaviour (Interactive User Guide)",jmeSimulationDefault));//Build in tab
		tabsSecondTabbedPane.add(new ConsoleTab(false,"Console", null,TabsInter.DIRECTORY_ICONS+ TabsInter.CONSOLE));
		tabsFirstTabbedPane.add(new NewTab(true,"YOUR NEW TAB",null,null));//YOUR NEW TAB
		tabsFirstTabbedPane.add(new NewTab(true,"YOUR NEW TAB1",null,null));//YOUR NEW TAB1
	}
	
	/**
	 * Initializes JME simulation canvas.
	 */
	private LWJGLCanvas initJmeSimulationCanvas(int width, int height){
		// make the canvas:
		DisplaySystem display = DisplaySystem.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
		display.registerCanvasConstructor("AWT", LWJGLAWTCanvasConstructor.class);
		canvas = (LWJGLCanvas)display.createCanvas(width, height);
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
		impl = new MyImplementor(width, height);
		canvas.setImplementor(impl);

		canvas.setBounds(0, 0, width, height);
		//canvase
		//canvas.setFocusable(true);

		//getContentPane().add(canvas);
		return canvas;
	}	
}
