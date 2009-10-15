package ussr.aGui.tabs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ussr.aGui.tabs.controller.CommunicationVisualizerController;
import ussr.comm.monitors.visualtracker.DrawingCanvas;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of   
 * @author Konstantinas (Improved Brian's code)
 */
public class CommunicationVisualizer extends Tabs {


	
	
	

	
	public CommunicationVisualizer(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		this.firstTabbedPane = firstTabbedPane;
		this.tabTitle = tabTitle;		
		this.jmeSimulation = jmeSimulation;
		this.imageIconDirectory =imageIconDirectory; 
		
		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		this.jComponent = new javax.swing.JPanel(new GridLayout(2, 0));
		
		//modules = this.jmeSimulation.getModules();
		//numberOfModules = modules.size();
		initComponents();
		
		//start();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		/*Instantiation of components*/	
		
		jButtonRun = new JButton();
		
		//canvas = new DrawingCanvas(jmeSimulation, CANVAS_ROWS, numberOfModules);
		jScrollPane = new JScrollPane();
		
		
				
	   
	    jScrollPane.setPreferredSize(new Dimension(900, 2000));
	    jScrollPane.setBorder(BorderFactory.createTitledBorder("Screen"));
	    jComponent.add(jScrollPane);
	  
	    jButtonRun.setText("Run");
	    jButtonRun.setToolTipText("Run");
	    jButtonRun.setFocusable(false);
	    jButtonRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    jButtonRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	    jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
                //jButtonRunActionPerformed(jmeSimulation,jScrollPane);
            	CommunicationVisualizerController.jButtonRunActionPerformed( jmeSimulation, jScrollPane);
            }
        });
	    jComponent.add(jButtonRun); 
	
	    
	    
	    
	    
	    
	    modulesButton = new JButton("Modules");
	    modulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/*HERE*///modulesButtonAction(event);
			}		
		});
	   // jComponent.add(modulesButton);
	    
	    packetsButton = new JButton("Packets");
	    packetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/*HERE*///packetsButtonAction(event);
			}		
		});	
	   // jComponent.add(packetsButton);
	    
	/*    startSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startSimulationButtonAction(event);
			}
		});	*/
	    
	   
			/*JScrollPane scrollPane = new JScrollPane(canvas);
			scrollPane.setPreferredSize(new Dimension(900, 2000));				
			JPanel panel = new JPanel(new BorderLayout());
			panel.setPreferredSize(new Dimension(900, 1000));
			//panel.add(startSimulationButton, BorderLayout.SOUTH);
			panel.add(scrollPane, BorderLayout.CENTER);		
			panel.setBorder(BorderFactory.createTitledBorder("Module Communication"));*/
		
		
	    	
	}

	
	
	private  javax.swing.JButton  modulesButton;
	private  javax.swing.JButton  packetsButton;
	private  javax.swing.JButton  jButtonRun;
	
	private static javax.swing.JScrollPane  jScrollPane;
	private  javax.swing.JPanel jPanel;
	
	
	private  static DrawingCanvas canvas;


	
	
	
}
