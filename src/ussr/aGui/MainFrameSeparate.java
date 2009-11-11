package ussr.aGui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ussr.builder.BuilderMultiRobotPreSimulation;
import ussr.remote.ConsoleSimulationExample;
import ussr.remote.GUISimulationAdapter;
/**
 * Defines visual appearance of the main GUI frame (window), separate from simulation environment.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class MainFrameSeparate extends MainFrames {


	/**
	 * Defines visual appearance of the main GUI frame (window), separate from simulation environment.
	 */
	public MainFrameSeparate(){
		super();
		initComponents();
		windowResizingListener();//Resize the main GUI window according to dimension of it's components, if user is maximizing or restoring it down.
	}	


	@Override
	public void initComponents() {
		getContentPane().setLayout(new java.awt.FlowLayout());
		initJMenuBar();
		initJToolbarGeneralControl((int)SCREEN_DIMENSION.getWidth()/2,COMMON_HEIGHT);

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

	/**
	 * Starts main GUI frame(window) separate from simulation environment, in separate thread.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {
		
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() {				
				mainFrame = new MainFrameSeparate();
				mainFrame.setVisible(true);	
			}
		});
		//runDefaultRemoteSimulation();
	}

/*	private static void runDefaultRemoteSimulation(){
		new Thread() {
			public void run() {
				GUISimulationAdapter.main(null);
			}
		}.start();
	}*/

	@Override
	public void activate() {
		throw new Error("Main GUI frame activation is not supported. Use main method instead.");
	}


}
