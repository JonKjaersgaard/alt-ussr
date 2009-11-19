package ussr.aGui.tabs;


import ussr.physics.jme.JMESimulation;

/**
 * Supports definitions of visual appearance for tabs pluged-in the main GUI window.
 * @author Konstantinas
 */
public abstract class Tabs implements TabsInter {
	
	protected boolean firstTabbedPane;
	
	protected String imageIconDirectory;
	
	protected boolean initiallyVisible;
	
	//protected Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
	

	


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
	
}
