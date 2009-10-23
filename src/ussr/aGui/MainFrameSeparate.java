package ussr.aGui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import ussr.aGui.tabs.TabsInter;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;

public class MainFrameSeparate extends MainFrame {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Starts the main GUI window (frame) during simulation. Simulation starts first and after that main GUI window is started.
	 * This can be achieved by pressing "O" on keyboard after starting the simulation. 
	 * @param jmeSimulation, the physical simulation.
	 * @param tabs, the tabs to plug in into main window's tabbed panes.
	 */
	public MainFrameSeparate(JMEBasicGraphicalSimulation jmeSimulation, ArrayList<TabsInter> tabs){
		super.jmeSimulation = (JMESimulation) jmeSimulation;	
		super.tabs = tabs;
		
		initFileChoosers();// initialize visual appearance of file choosers. Why here, because then they are responding faster to user generated events, because they are compiled earlier).
		
		//TODO MOVE ME
		// add tabs
		for (int index=0; index<super.tabs.size();index++){
			if (super.tabs.get(index).isFirstTabbedPane()){
				super.tabsFirstTabbedPane.add(super.tabs.get(index));	
			}else {
				super.tabsSecondTabbedPane.add(super.tabs.get(index));
			}
			//System.out.println("Some");
		}
		
	    initComponents();
	    changeInstanceFlagListener();//Change the instance flag to true. Meaning the window is once instantiated.
		windowResizingListener();//Resize the main GUI window according to dimension of it's components, if user is maximizing or restoring it down.		
	}	
	
	
	@Override
	public void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		initFrameProperties();
		initJMenuBar();
		initJToolbarGeneralControl((int)SCREEN_DIMENSION.getWidth()/2,COMMON_HEIGHT);//TODO CHANGE null with default simulation
		
		initFirstTabbbedPane();
		initSecondTabbedPane((int)SCREEN_DIMENSION.getWidth()/2, TAB_PANE_HEIGHT2);
		
		pack(); 
		changeToLookAndFeel(this);
		
		components.add(jMenuBarMain);
		components.add(jToolBarGeneralControl);
		components.add(jTabbedPaneFirst);
		components.add(jTabbedPane3);
		
		setFrameHeightAccordingComponents(this,(int)SCREEN_DIMENSION.getWidth()/2+PADDING,components); 
	}
	
	
	/**
	 * Instance flag for current frame, used to keep track that only one instance of the frame is instantiated.
	 * This is for running the main GUI window during simulation (separate from simulation window).
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
	 * Returns true if the frame(main GUI window) is already instantiated.
	 * @return true, if the frame(main GUI window) is already instantiated.
	 */
	public static boolean isInstanceFlag() {
		return instanceFlag;
	}
	
	
	/**
	 * 
	 */
	private void windowResizingListener(){
		this.addWindowStateListener (new WindowAdapter() {	
			public void windowStateChanged(WindowEvent event) {
/*THINK MORE HERE*/
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


	@Override
	public void activate() {
			java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {            	
			mainFrame = new MainFrameSeparate(jmeSimulation,tabs);
			mainFrame.setVisible(true);

		}
	});
		
	}


	

}
