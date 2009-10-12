package ussr.aGui.tabs;


import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of Console tab. 
 * @author Konstantinas
 */
public class ConsoleTab extends Tabs {

	public ConsoleTab(boolean firstTabbedPane, String tabTitle, JMESimulation jmeSimulation){
		this.firstTabbedPane = firstTabbedPane;
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
		
		jTextArea1.setColumns(100);		
		//
        jTextArea1.setRows(200);
        
		//jTextArea1.setPreferredSize(new Dimension(jPanel1000.getWidth(),jPanel1000.getHeight()));
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1000.add(jScrollPane1);
		
	}
	
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JScrollPane jScrollPane1;


	

}
