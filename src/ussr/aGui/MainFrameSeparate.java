package ussr.aGui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;

import ussr.aGui.enumerations.ComponentsFrame;
import ussr.aGui.enumerations.MainFrameComponentsText;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.physics.PhysicsParameters;
import ussr.physics.jme.JMEBasicGraphicalSimulation;


/**
 * Defines visual appearance of the main GUI frame (window), separate from simulation environment.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class MainFrameSeparate extends MainFrames {
	/**
     * The width and height of this frame.
     */
    private final int FRAME_WIDTH_HALF = (int)SCREEN_VIABLE_WIDTH/2,
                      FRAME_HEIGHT = (int)SCREEN_VIABLE_HEIGHT;
 
    /**
     * Common width of main(first hierarchy) components(containers) of the frame in case when the frame is in maximized state.
     */
    private final int CONTAINER_WIDTH = (int)SCREEN_VIABLE_WIDTH-insets.right -insets.left+2*HORIZONTAL_GAPS;
    
    /**
     * Common width of main(first hierarchy) components(containers) of the frame in case when the frame is not in maximized state.
     */
    private final int CONTAINER_WIDTH_HALF = FRAME_WIDTH_HALF-insets.right -insets.left-2*HORIZONTAL_GAPS;
    
	/**
	 * Height of the second tabbed pane.
	 */
	public static final int TABBED_PANE2_HEIGHT = 125;
    
    /**
	 * Height of the first tabbed pane. 
	 * Calculate is so that the height of it is dependable on the height of each component of the frame, including dimensions of frame border and gaps between components.
	 */
	public final int TABBED_PANE1_HEIGHT = (int)((FRAME_HEIGHT-MENU_BAR_HEIGHT-HORIZONTAL_TOOLBAR_HEIGHT-TABBED_PANE2_HEIGHT- insets.top-insets.bottom -4*VERTICAL_GAPS));
	
	/**
	 * X position of simulation window
	 */
	public static final int simWindowX = (int)SCREEN_VIABLE_WIDTH/2+2*HORIZONTAL_GAPS;

	/**
	 * Y position of simulation window
	 */
	public static final int simWindowY = 5;
	
	/**
	 * Defines visual appearance of the main GUI frame (window), separate from simulation environment.
	 */
	public MainFrameSeparate(){
		super();		
		initComponents();
		addWindowListeners();//Override default window listeners.
	}	


	@Override
	public void initComponents() {
		
		/*initialize layout*/
		java.awt.FlowLayout flowLayout = new java.awt.FlowLayout();
		flowLayout.setHgap(HORIZONTAL_GAPS);
		flowLayout.setVgap(VERTICAL_GAPS);		
		getContentPane().setLayout(flowLayout);	
		this.setSize(new Dimension(FRAME_WIDTH_HALF,FRAME_HEIGHT));
		
		/*initialize the main containers of the frame*/
		initJMenuBar(CONTAINER_WIDTH_HALF,MENU_BAR_HEIGHT);		
		initJToolbarGeneralControl(CONTAINER_WIDTH_HALF,HORIZONTAL_TOOLBAR_HEIGHT);
		//initSplitPane(CONTAINER_WIDTH_HALF,TABBED_PANE1_HEIGHT,CONTAINER_WIDTH_HALF, TABBED_PANE2_HEIGHT);
		initFirstTabbedPane(CONTAINER_WIDTH_HALF,TABBED_PANE1_HEIGHT);
		initSecondTabbedPane(CONTAINER_WIDTH_HALF, TABBED_PANE2_HEIGHT);
		//TODO DECIDE IF IT IS NEEDED
		//initializeTabbedPanesResizing();	
		//gettCursor();
		
		
		changeToLookAndFeel(this);
		
		/*Add components into container, which affect the width of main window when it is maximized and restored down to its initial size. */
		//JMenuBar is an exception here.
		components.add(getJToolBarGeneralControl());
		components.add(getJTabbedPaneFirst());
		components.add(getJTabbedPaneSecond());	
	}

	/**
	 * Overrides the window listeners for events like window state change (maximized and restored down to its initial state) and
	 * and window closing event.
	 */
	private void addWindowListeners(){
		this.addWindowStateListener (new WindowAdapter() {	
			public void windowStateChanged(WindowEvent event) {				
				int newState = event.getNewState();				
				switch(newState){
				case WINDOW_MAXIMIZED_STATE:
					changeWidthOfMajorComponents(components,CONTAINER_WIDTH);
					break;
				case WINDOW_RESTORED_STATE:
					changeWidthOfMajorComponents(components,CONTAINER_WIDTH_HALF);
					break;
				/*avoid overriding the rest states, because in this case they matching quite well.*/
				}
			}			
		}
		);	
		this.addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				/*Special exit, in order to check if remote simulation is still running */
				MainFrameSeparateController.jMenuItemExitActionPerformed();                     
			}
		}
		);
	}
	
	
	/**
	 * Changes the width of each component in the array and keeps the same height.
	 * @param components, the components to change the width to.
	 * @param width, new width of components.
	 */
	private void changeWidthOfMajorComponents(ArrayList<JComponent> components,int width){
		for(int index=0;index<components.size();index++){						
			components.get(index).setPreferredSize(new Dimension(width,components.get(index).getHeight()));
		}
	}
	
	/**
	 * Starts main GUI frame(window) separate from simulation environment, in separate thread.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {		
		java.awt.EventQueue.invokeLater(new Runnable(){
		//new Thread(){
			public void run() {				
				mainFrame = new MainFrameSeparate();
				mainFrame.setVisible(true);
				setMainFrameSeparateEnabled(true,false);
			}
		//}.start();
		});		
	}

	/**
	 * Activates main GUI frame(window) separate from simulation environment, in separate thread.
	 * @param args, passed arguments.
	 */
	@Override
	public void activate() {
		MainFrameSeparate.main(null);
	}

	/**
	 * Controls custom enabling of the main frame. Disables components so that the user have to load the simulation from xml file first.
	 * When simulation is loaded do not allows to load new simulation. 
	 * @param beforeSimulationStarted
	 * @param enabled, true for main frame to be enabled. 
	 */
	public static void setMainFrameSeparateEnabled(boolean beforeSimulationStarted,boolean enabled){
		if (beforeSimulationStarted){
		setJMenuBarMainEnabled(enabled);
		setJToolBarGeneralControlEnabled(enabled);
		setJTabbedPaneFirstEnabled(enabled);
		}else{
			getJMenuItemOpen().setEnabled(false);
			getJButtonOpen().setEnabled(false);
		}
	}
	
	
	/**
	 * Controls custom enabling of MenuBar components
	 * @param enabled, true for enabled. 
	 */
	public static void setJMenuBarMainEnabled(boolean enabled) {
		int amountComponents = getJMenuBarMain().getComponents().length;
		for (int component=0; component<amountComponents;component++){
			JComponent currentComponent = (JComponent)getJMenuBarMain().getComponent(component);
			String componentClassName = currentComponent.getClass().getName();
			
			if (componentClassName.contains(ComponentsFrame.JMenu.toString())){
				JMenu currentJMenu =(JMenu)currentComponent;
				int amountJMenuItems = currentJMenu.getMenuComponentCount();
				
				for (int jMenuItem=0;jMenuItem<amountJMenuItems;jMenuItem++){
					if (currentJMenu.getMenuComponent(jMenuItem).getClass().getName().contains(ComponentsFrame.JSeparator.toString())){
						
					}else{
						JMenuItem currentJMenuItem = (JMenuItem) currentJMenu.getMenuComponent(jMenuItem);
						String jMenuItemText =currentJMenuItem.getText(); 
						if (jMenuItemText.contains(MainFrameComponentsText.OPEN.toString())||jMenuItemText.contains(MainFrameComponentsText.EXIT.toString())){
							//do nothing
						}else{
							currentJMenuItem.setEnabled(enabled);
						}
					}				
				}				
			}
		}
	}
	
	/**
	 * Controls custom enabling of components in tool bar for general control.
	 * @param enabled,true for enabled. 
	 */
	public static void setJToolBarGeneralControlEnabled(boolean enabled) {
		int amountComponents = getJToolBarGeneralControl().getComponents().length;
		for (int component=0; component<amountComponents;component++){
			JComponent currentComponent = (JComponent)getJToolBarGeneralControl().getComponent(component);
			String componentClassName = currentComponent.getClass().getName();
			
			if (componentClassName.contains(ComponentsFrame.JToolBar$Separator.toString())){
				//do nothing
			}else if(componentClassName.contains(ComponentsFrame.JToggleButton.toString())){
				JToggleButton currentToggleJButton = (JToggleButton)currentComponent;
			/*	if (currentToggleJButton.getToolTipText().contains(MainFrameComponentsText.VISUALIZE_COMMUNICATION_OF_MODULES.getUserFriendlyName())){
					//do nothing
				}else{	*/			
				currentToggleJButton.setEnabled(enabled);
				//}
			}else if (componentClassName.contains(ComponentsFrame.JButton.toString())){
				JButton currentJButton = (JButton)currentComponent;
				String currentJButtonText = currentJButton.getToolTipText();
				if (currentJButtonText.contains(MainFrameComponentsText.OPEN.getUserFriendlyName())){
					//do nothing
				}else{
					currentJButton.setEnabled(enabled);
				}
			}
		}
	}

}
