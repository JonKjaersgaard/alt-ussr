package ussr.aGui.tabs;

import javax.swing.JScrollPane;

/**
 * Defines visual appearance of Console tab. 
 * @author Konstantinas
 */
public class ConsoleTab extends Tabs  {
	
	/**
	 * Defines visual appearance of the tab called "Console".
	 * @param initiallyVisible, true if the tab is visible after activation of main GUI window. 
	 * @param firstTabbedPane,true if the tab is visible after activation of main GUI window. 
	 * @param tabTitle, the title of the tab
	 * @param imageIconDirectory,the directory for icon displayed in the top-left corner of the tab.
	 */
	public ConsoleTab(boolean initiallyVisible,boolean firstTabbedPane, String tabTitle,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,imageIconDirectory);
		
		/*JComponent, which will be added to the tab in the main Window.*/
		super.jComponent = new javax.swing.JScrollPane();
		initComponents();		
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy pattern.
     */
	public void initComponents() {		
	     jTextAreaConsole = new javax.swing.JTextArea();
		((JScrollPane) super.jComponent).setViewportView(jTextAreaConsole);// add text area into scroll pane and return it.
	}
	
	/**
	 * Returns text area component of the tab.
	 * @return jTextAreaConsole, the component of the Tab.
	 */
	public static javax.swing.JTextArea getJTextAreaConsole() {
		return jTextAreaConsole;
	}

	private static javax.swing.JTextArea jTextAreaConsole;
}
