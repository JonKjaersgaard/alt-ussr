package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of Console tab. 
 * @author Konstantinas
 */
public class ConsoleTab extends Tabs {

	public ConsoleTab(String tabTitle,JMESimulation jmeSimulation){
		this.tabTitle = tabTitle;
		this.jPanel1000 = new javax.swing.JPanel();
		this.jmeSimulation = jmeSimulation;
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the panel.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		
	}
	
	


	

}
