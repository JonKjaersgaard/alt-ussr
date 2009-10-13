package ussr.aGui.tabs;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ussr.aGui.GuiFrames;
import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of Console tab. 
 * @author Konstantinas
 */
public class ConsoleTab extends Tabs {
	
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();


	public ConsoleTab(boolean firstTabbedPane, String tabTitle, JMESimulation jmeSimulation){
		this.firstTabbedPane = firstTabbedPane;
		this.tabTitle = tabTitle;		
		this.jmeSimulation = jmeSimulation;
		this.jComponent = new javax.swing.JScrollPane();
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the panel.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		//jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		
		jTextArea1.setColumns(10);		
		//
		jTextArea1.setRows(100);
        
     /*   gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		//jTextArea1.setPreferredSize(new Dimension(jPanel1000.getWidth(),jPanel1000.getHeight()));
       
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;*/

		((JScrollPane) jComponent).setViewportView(jTextArea1);
		
		
		
		
		//PrintStream printStream = new PrintStream(/*new FileOutputStream(jTextArea1)*/);
        //jPanel1000.add(jTextArea1,gridBagConstraints);
		//System.set
	}
	
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JScrollPane jScrollPane1;


	

}
