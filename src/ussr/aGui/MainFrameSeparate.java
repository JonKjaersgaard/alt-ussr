package ussr.aGui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;

import ussr.aGui.enumerations.ComponentsFrame;
import ussr.aGui.enumerations.MainFrameComponentsText;


/**
 * Defines visual appearance of the main GUI frame (window), separate from simulation environment.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class MainFrameSeparate extends MainFrames {

    /**
     * The width and height of this frame.
     */
    private final int FRAME_WIDTH = (int)SCREEN_VIABLE_WIDTH/2,
                      FRAME_HEIGHT = (int)SCREEN_VIABLE_HEIGHT;
    
    /**
     * Horizontal and vertical gaps between components of the frame. 
     */
    private final int HORIZONTAL_GAPS = 6,
                      VERTICAL_GAPS = 6;
    /**
     * Common width of main(first hierarchy) components(containers) of the frame.
     */
    private final int CONTAINER_WIDTH = FRAME_WIDTH-super.insets.right -super.insets.left-2*HORIZONTAL_GAPS;
    
    /**
     * Height of menu bar.
     */
    private final int MENU_BAR_HEIGHT = 20;
    
	/**
	 * Height of the second tabbed pane.
	 */
	public final int TABBED_PANE2_HEIGHT = 125;
    
    /**
	 * Height of the first tabbed pane. 
	 * Calculate is so that the height of it is dependable on the height of each component of the frame, including dimensions of frame border and gaps between components.
	 */
	public final int TABBED_PANE1_HEIGHT = (int)((FRAME_HEIGHT-MENU_BAR_HEIGHT-HORIZONTAL_TOOLBAR_HEIGHT-TABBED_PANE2_HEIGHT- super.insets.top-super.insets.bottom -4*VERTICAL_GAPS));
	
	
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
		
		/*initialize the main containers of the frame*/
		initJMenuBar(CONTAINER_WIDTH,MENU_BAR_HEIGHT);
		initJToolbarGeneralControl(CONTAINER_WIDTH,HORIZONTAL_TOOLBAR_HEIGHT);
		initFirstTabbedPane(CONTAINER_WIDTH,TABBED_PANE1_HEIGHT);
		initSecondTabbedPane(CONTAINER_WIDTH, TABBED_PANE2_HEIGHT);
		initializeTabbedPanesResizing();		
		//setResizable(false);//FOR TESTING. RESIZING WORKS HOWEVER NEEDS MORE ATTENTION		
		
		this.setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		changeToLookAndFeel(this);
		
		components.add(getJMenuBarMain());
		components.add(getJToolBarGeneralControl());
		components.add(getJTabbedPaneFirst());
		components.add(getJTabbedPaneSecond());
		
		 
		
//		setFrameHeightAccordingComponents(this,FRAME_WIDTH,components);
		
		
	}

	/**
	 * 
	 */
	private void addWindowListeners(){
		this.addWindowStateListener (new WindowAdapter() {	
			public void windowStateChanged(WindowEvent event) {
				/*THINK MORE HERE*/
				int newState = event.getNewState();
				//System.out.println("State:"+ newState);
				if (newState == 6){//Window maximized
					for(int index=0;index<components.size();index++){
						components.get(index).setPreferredSize(new Dimension((int)SCREEN_SIZE.getWidth()-PADDING/2,components.get(index).getHeight()));
					}			    		
				}else if(newState == 0){//Window restored down to its initial dimension.
					for(int index=0;index<components.size();index++){
						components.get(index).setPreferredSize(new Dimension((int)SCREEN_SIZE.getWidth()/2-PADDING,components.get(index).getHeight()));
					}
				}
			}			
		}
		);	
		
		
		addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				MainFrameSeparateController.jMenuItemExitActionPerformed();                     
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
				setMainFrameSeparateEnabled(false);
			}
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
	 * @param enabled, true for main frame to be enabled. 
	 */
	public static void setMainFrameSeparateEnabled(boolean enabled){
		setJMenuBarMainEnabled(enabled);
		setJToolBarGeneralControlEnabled(enabled);
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
						if (jMenuItemText.contains(MainFrameComponentsText.Open.toString())||jMenuItemText.contains(MainFrameComponentsText.Exit.toString())){
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
				if (currentToggleJButton.getToolTipText().contains(MainFrameComponentsText.Visualize_communication_of_modules.toString().replace("_", ""))){
					//do nothing
				}else{				
				currentToggleJButton.setEnabled(enabled);
				}
			}else if (componentClassName.contains(ComponentsFrame.JButton.toString())){
				JButton currentJButton = (JButton)currentComponent;
				String currentJButtonText = currentJButton.getToolTipText();
				if (currentJButtonText.contains(MainFrameComponentsText.Open.toString())){
					//do nothing
				}else{
					currentJButton.setEnabled(enabled);
				}
			}
		}
	}
}
