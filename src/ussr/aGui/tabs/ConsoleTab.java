package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of Console tab. 
 * @author Konstantinas
 */
public class ConsoleTab extends Tabs {

	public ConsoleTab(String tabTitle,JMESimulation jmeSimulation){
		this.tabTitle = tabTitle;		
		this.jmeSimulation = jmeSimulation;
		this.jPanel1000 = new javax.swing.JPanel();
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the panel.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		
		jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1000.add(jScrollPane1);
		
	}
	
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JScrollPane jScrollPane1;


	

}
