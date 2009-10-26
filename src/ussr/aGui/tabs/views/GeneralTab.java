package ussr.aGui.tabs.views;

import ussr.aGui.tabs.Tabs;
import ussr.aGui.tabs.controllers.GeneralTabController;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of NEW tab implemented by YOU. Please leave this class alone for
 * future developers and use the copy of it.  
 * @author Konstantinas
 */
public class GeneralTab extends Tabs {

	public GeneralTab(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation, String imageIconDirectory){
		super(firstTabbedPane,tabTitle,jmeSimulation,imageIconDirectory);
		
		super.jComponent = new javax.swing.JPanel();
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		 jSpinner1 = new javax.swing.JSpinner();
		 
		 
		 
		 jLabel1 = new javax.swing.JLabel();
		 jLabel1.setText("Plane size");
		 
		 jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 10));
		 jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
	            public void stateChanged(javax.swing.event.ChangeEvent evt) {
	            	GeneralTabController.jSpinner1StateChanged(evt,jmeSimulation);
	            }
	        });
		/* jSpinner1.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                GeneralTabController.jSpinner1MouseClicked(jSpinner1);
	            }
	        });*/

		 
		 
		 
		 
		 
	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( super.jComponent);
	        super.jComponent.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel1)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(304, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel1))
	                .addContainerGap(269, Short.MAX_VALUE))
	        );

	      
	}
	
	private javax.swing.JLabel jLabel1;
    private javax.swing.JSpinner jSpinner1;

	

}
