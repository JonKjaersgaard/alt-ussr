package ussr.aGui.tabs;

/**
 * Supports definition of visual appearance(view in MVC pattern) of tabs, which are pluged-in main GUI window. 
 * @author Konstantinas
 */
public interface TabsInter {
	
	/**
     * Getter method common for all tabs and is used by GUI during addition of new tab.
     * UPDATE
     */
	public javax.swing.JComponent getJComponent();
	
	/**
	 * Getter method common for all tabs and is used by GUI during addition of new tab.
	 * @return tabTitle, the title of the tab.
	 */
	public String getTabTitle();
	
	
	public boolean isFirstTabbedPane();


}
