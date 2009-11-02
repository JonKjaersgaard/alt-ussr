package ussr.aGui.tabs.views;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JScrollPane;
import ussr.aGui.tabs.Tabs;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of Console tab. 
 * @author Konstantinas
 */
public class ConsoleTab extends Tabs  {
	
	
	public ConsoleTab(boolean initiallyVisible,boolean firstTabbedPane, String tabTitle, JMESimulation jmeSimulation,String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);
		
		/*JComponent, which will be added to the tab in the main Window.*/
		super.jComponent = new javax.swing.JScrollPane();
		initComponents();		
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		this.jTextArea1 = new javax.swing.JTextArea();
		((JScrollPane) super.jComponent).setViewportView(jTextArea1);// add text area into scroll pane and return it.
		//RedirectSystemOutput redirectedSystemOutput = new RedirectSystemOutput(true, true,null/*"resources/mainFrame/HERE.txt"*/,this.jTextArea1);
		
	}
	
	private javax.swing.JTextArea jTextArea1;


}
