package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of NEW tab implemented by YOU. Please leave this class alone for
 * future developers and use the copy of it.  
 * @author Konstantinas
 */
public class NewTab extends Tabs {

	public NewTab(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation, String imageIconDirectory){
		super(firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);
		
		super.jComponent = new javax.swing.JPanel();
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
