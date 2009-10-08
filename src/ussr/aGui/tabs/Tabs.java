package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

public abstract class Tabs implements TabsInter {

	//protected javax.swing.JTabbedPane jTabbedPane;
	
	protected javax.swing.JPanel jPanel1000;
	
	protected String tabTitle;
	
	/**
	 * The physical simulation
	 */	   
	protected JMESimulation jmeSimulation;

    public abstract void initComponents();
    
    public  javax.swing.JPanel getJPanel1000() {
		return jPanel1000;
	}
    
 

    
}
