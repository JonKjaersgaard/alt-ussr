package ussr.aGui.tabs.views;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import ussr.aGui.tabs.Tabs;

import ussr.physics.jme.JMESimulation;

/**

 * @author Konstantinas
 */
public class SimulationTab extends Tabs {


	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	public  GridBagConstraints gridBagConstraints = new GridBagConstraints();

	public SimulationTab(boolean initiallyVisible, boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation, String imageIconDirectory){
		super(initiallyVisible,firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);

		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());
		initComponents();
	}

	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents() {


		jScrollPane3 = new javax.swing.JScrollPane();


		jTable1 = new javax.swing.JTable();        



		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {//rows

				},
				new String [] {
						"World Description", "Value", "PhysicsParameters", "Value"
				}
		));
		jScrollPane3.setViewportView(jTable1);
		gridBagConstraints.fill = GridBagConstraints.PAGE_START;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		super.jComponent.add(jScrollPane3,gridBagConstraints);	



	}

	public static javax.swing.JTable getJTable1() {
		return jTable1;
	}

	private javax.swing.JLabel jLabel1;
	private javax.swing.JSpinner jSpinner1;

	private javax.swing.JScrollPane jScrollPane3;


	private static javax.swing.JTable jTable1 ;



}
