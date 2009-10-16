package ussr.aGui.tabs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import ussr.aGui.MainFrameInter;
import ussr.aGui.tabs.controller.CommunicationVisualizerController;
import ussr.comm.monitors.visualtracker.DrawingCanvas;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of   
 * @author Konstantinas (Adapted Brian's code)
 */
public class CommunicationVisualizer extends Tabs {

	/**
	 * The constrains of grid bag layout used during design of the tab.
	 */
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	
		
	public CommunicationVisualizer(boolean firstTabbedPane, String tabTitle,JMESimulation jmeSimulation,String imageIconDirectory){
		super(firstTabbedPane,tabTitle,jmeSimulation);		
		super.imageIconDirectory =imageIconDirectory; 
		
		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		super.jComponent = new javax.swing.JPanel(new GridBagLayout());
		super.fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		/*Instantiation of components*/		
		jScrollPane = new JScrollPane();
		
		this.jButtonRun = new JButton();
		
		this.jToolBar1 = new JToolBar();
		
		jLabel1000 = new javax.swing.JLabel();
		jLabel1001 = new javax.swing.JLabel();
				
	   
	    jScrollPane.setPreferredSize(new Dimension(900, 2000));
		//jScrollPane.setPreferredSize(new Dimension(1200, 4000));		
	    jScrollPane.setBorder(BorderFactory.createTitledBorder("Display Area"));
	    jScrollPane.setToolTipText("Display Area");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		//gridBagConstraints.gridwidth =10;
		gridBagConstraints.ipady = 350;//2000;//make it tall
		gridBagConstraints.ipadx = 600;//600;//make it wide			
		
		jLabel1001.setText("Make sure that in simulation there are atleast two modules  and press run beneath ");
		jLabel1001.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + INFORMATION));
		jLabel1001.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));		
		jLabel1001.setVisible(true);
		jScrollPane.setViewportView(jLabel1001);
		
		jLabel1000.setIcon(new javax.swing.ImageIcon(DIRECTORY_ICONS + ERROR));		
		jLabel1000.setFont( new Font("Times New Roman", Font.PLAIN, 12).deriveFont(fontAttributes));		
		jLabel1000.setVisible(false);		
		
		super.jComponent.add(jScrollPane,gridBagConstraints);
	    
	    jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    jToolBar1.setRollover(true);
	    jToolBar1.setFloatable(false);
	    jToolBar1.setToolTipText("Visualizer Control");
	    jToolBar1.setPreferredSize(new Dimension(30,30));	   
	  

	    jButtonRun.setIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.RUN_REAL_TIME));
	    jButtonRun.setPreferredSize(new Dimension(30,30));
	    jButtonRun.setToolTipText("Run");
	    jButtonRun.setFocusable(true);
	    jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	CommunicationVisualizerController.jButtonRunActionPerformed( jmeSimulation, jScrollPane);
            }
        });
	    
	    jToolBar1.add(jButtonRun);
	    
	    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_END;
		gridBagConstraints.ipady = 10;//make it tall
		//gridBagConstraints.ipadx = 300;//make it wide
		super.jComponent.add(jToolBar1,gridBagConstraints);	    
	    
	    
		
		
		
		
		
		
		
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

	/*Getters*/
	public static javax.swing.JLabel getJLabel1000() {
		return jLabel1000;
	}

	public static javax.swing.JLabel getJLabel1001() {
		return jLabel1001;
	}
	
	
	/*Declaration of components*/
	private  javax.swing.JButton  modulesButton;
	private  javax.swing.JButton  packetsButton;
	
	private  javax.swing.JButton  jButtonRun;
	
	private static javax.swing.JScrollPane  jScrollPane;	

	private javax.swing.JToolBar jToolBar1;
	
	private static javax.swing.JLabel jLabel1000;
	
	private static javax.swing.JLabel jLabel1001;
	
}
