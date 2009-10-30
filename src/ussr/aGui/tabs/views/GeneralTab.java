package ussr.aGui.tabs.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
			jScrollPane3 = new javax.swing.JScrollPane();
			
			
	        jTable1 = new javax.swing.JTable();        
	        
		 
		 
		 jTable1.setModel(new javax.swing.table.DefaultTableModel(
		            new Object [][] {
		                {"Label"},
		                {"label1"},
		                {"labe1"},
		                {null},
		                {null},
		                {null},
		                {null}
		            },
		            new String [] {
		                "Add label from pallete"
		            }
		        ));
		        jTable1.setCellSelectionEnabled(true);
		        jTable1.setPreferredSize(new Dimension(100,100));
		        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		        jScrollPane3.setViewportView(jTable1);
		        jScrollPane3.setPreferredSize(new Dimension(100,100));
		       
		        
		      
		 
		 
		 
		 
		 
	 jLabel1 = new javax.swing.JLabel();
		 jLabel1.setText("Plane size");
		 
			/* jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 10));
		 jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
	            public void stateChanged(javax.swing.event.ChangeEvent evt) {
	            	GeneralTabController.jSpinner1StateChanged(evt,jmeSimulation);
	            }
	        });*/
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
	                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(304, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel1))
	                .addContainerGap(269, Short.MAX_VALUE))
	        );

	      
	}
	
	private javax.swing.JLabel jLabel1;
    private javax.swing.JSpinner jSpinner1;

	private javax.swing.JScrollPane jScrollPane3;
	
	
	private javax.swing.JTable jTable1 ;   
	

}
