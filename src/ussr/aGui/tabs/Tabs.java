package ussr.aGui.tabs;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;

import ussr.aGui.MainFrames;
import ussr.aGui.fileChoosing.fileChooser.FileChooserCustomized;
import ussr.aGui.fileChoosing.fileChooser.FileChooserCustomizedInter;

/**
 * Supports definitions of visual appearance for tabs plugged-in the main GUI window.
 * @author Konstantinas
 */
public abstract class Tabs implements TabsInter {
	
	/**
	 * Location of the tab in the main GUI frame. True if it is the first tabbed pane. 
	 */
	protected boolean firstTabbedPane;
	
	/**
	 * The directory for icon displayed in the top-left corner of the tab.
	 */
	protected String imageIconDirectory;
	
	/**
	 * True if the tab is visible after activation of main GUI window.
	 */
	protected boolean initiallyVisible;
	
	/**
	 * The title of the tab.
	 */
	protected String tabTitle;
	
	/**
	 * The main component, which is the container for all components situated in the tab. 
	 * For example panel. In other words, tab contains panel and panel contains other components.
	 * The panel is jComponent.	
	 */
	protected javax.swing.JComponent jComponent;	
	
    /**
     * Initializes the visual appearance of all components in the panel.
     * Follows Strategy pattern.
     */
    protected abstract void initComponents();
  
    /**
     * Supports definitions of visual appearance for tabs plugged-in the main GUI window.
     * @param initiallyVisible,true if the tab is visible after activation of main GUI window.
     * @param firstTabbedPane, location of the tab in the main GUI frame. True if it is the first tabbed pane. 
     * @param tabTitle, the title of the tab.
     * @param imageIconDirectory, the directory for icon displayed in the top-left corner of the tab.
     */
    protected Tabs(boolean initiallyVisible ,boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
    	this.initiallyVisible= initiallyVisible;
    	this.firstTabbedPane = firstTabbedPane;
    	this.tabTitle = tabTitle;
    	this.imageIconDirectory = imageIconDirectory;  	
    }
    
    /**
	 * Returns JComponent, which is the main container of components situated in the tab.
	 * @return JComponent, which is the main container of components situated in the tab.
	 */
    public  javax.swing.JComponent getJComponent() {
		return jComponent;
	}
    
    //FIXME DECIDE
    public ArrayList<javax.swing.JComponent> getComponents(){
    	ArrayList<javax.swing.JComponent> components = new ArrayList<javax.swing.JComponent>();
    	for (int compon=0;compon<jComponent.getComponents().length;compon++){
    		components.add((JComponent) jComponent.getComponent(compon));
    	}
    	return components;
    }
    
	/**
	 * Getter method common for all tabs and is used by GUI during addition of new tab.
	 * @return tabTitle, the title of the tab.
	 */
	public String getTabTitle() {
		return tabTitle;
	}    
	
	/**
	 * Returns the directory where the icon of the tab is located.
	 * @return the directory where the icon of the tab is located.
	 */
	public String getImageIconDirectory() {
		return imageIconDirectory;
	}

	/**
	 * Returns true if the tab is situated in the first tabbed pane of main GUI window, else it is situated in second.
	 * @return true if the tab is situated in the first tabbed pane of main GUI window, else it is situated in second.
	 */
	public boolean isFirstTabbedPane() {
		return firstTabbedPane;
	}
	
	/**
	 * Returns true if the tab is visible when main GUI window is activated.
	 * @return true if the tab is visible when main GUI window is activated.
	 */
	public boolean isInitiallyVisible() {
		return initiallyVisible;
	}
	
	
	public  JButton initSaveButton(){
		/*Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);

		FileChooserFrameInter fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DEFAULT_DIRECTORY);
		return MainFrames.initSaveButton(fcSaveFrame);*/
		return MainFrames.initSaveButton(FileChooserCustomizedInter.FC_FRAME_SAVE_ROBOT);
	}
	
	public static JButton initOpenButton(){
		/*Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);		
		FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DEFAULT_DIRECTORY);*/
		//return MainFrames.initOpenButton(fcOpenFrame);
		return MainFrames.initOpenButton(FileChooserCustomizedInter.FC_FRAME_OPEN_ROBOT);
	}
	
}
