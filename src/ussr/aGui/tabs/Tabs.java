package ussr.aGui.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;

import ussr.aGui.MainFrames;
import ussr.aGui.fileChooser.views.FileChooserFrameInter;
import ussr.aGui.fileChooser.views.FileChooserOpenFrame;
import ussr.aGui.fileChooser.views.FileChooserSaveFrame;

/**
 * Supports definitions of visual appearance for tabs pluged-in the main GUI window.
 * @author Konstantinas
 */
public abstract class Tabs implements TabsInter {
	
	/**
	 * 
	 */
	protected boolean firstTabbedPane;
	
	/**
	 * 
	 */
	protected String imageIconDirectory;
	
	/**
	 * 
	 */
	protected boolean initiallyVisible;
	
	/**
	 * The title of the tab.
	 */
	protected String tabTitle;

	
	/**
	 * The main component, which is the container for all components situated in the tab. 
	 * For example panel. In other words, tab contains panel and panel contains other components.	
	 */
	protected javax.swing.JComponent jComponent;	
	
    /**
     * Initializes the visual appearance of all components in the panel.
     * Follows Strategy pattern.
     */
    protected abstract void initComponents();
    
    /**
     * TODO
     * @param firstTabbedPane
     * @param tabTitle
     */
    protected Tabs(boolean initiallyVisible ,boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
    	this.initiallyVisible= initiallyVisible;
    	this.firstTabbedPane = firstTabbedPane;
    	this.tabTitle = tabTitle;
    	this.imageIconDirectory = imageIconDirectory; 
    	
    }
    
    /**
     * Getter method common for all tabs and is used by GUI during addition of new tab.
     * UPDATE
     */
    public  javax.swing.JComponent getJComponent() {
		return jComponent;
	}
    
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
	
	public String getImageIconDirectory() {
		return imageIconDirectory;
	}

	public boolean isFirstTabbedPane() {
		return firstTabbedPane;
	}
	
	
	public boolean isInitiallyVisible() {
		return initiallyVisible;
	}
	
	
	public  JButton initSaveButton(){
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);

		FileChooserFrameInter fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DEFAULT_DIRECTORY);
		return MainFrames.initSaveButton(fcSaveFrame);
	}
	
	public static JButton initOpenButton(){
		Map<String,String> fileDescriptionsAndExtensions= new HashMap<String,String>();
		fileDescriptionsAndExtensions.put(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION, FileChooserFrameInter.DEFAULT_FILE_EXTENSION);
		FileChooserFrameInter fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,FileChooserFrameInter.FC_XML_CONTROLLER,FileChooserFrameInter.DEFAULT_DIRECTORY);
		return MainFrames.initOpenButton(fcOpenFrame);
	}
	
	public static javax.swing.JLabel createNewLabel(String labelText){
		javax.swing.JLabel newLabel =  new javax.swing.JLabel();
		newLabel.setText(labelText+" ");
		return newLabel;
	}
	
}
