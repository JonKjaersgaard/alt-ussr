package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of NEW tab implemented by YOU. Please leave this class alone for
 * future developers and use the copy of it.  
 * @author Konstantinas
 */
public class NewTab extends Tabs {

	public NewTab(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation){
		this.firstTabbedPane = firstTabbedPane;
		this.tabTitle = tabTitle;
		this.jComponent = new javax.swing.JPanel();
		this.jmeSimulation = jmeSimulation;
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		// TODO Auto-generated method stub
		
	}
	
	


	

}
