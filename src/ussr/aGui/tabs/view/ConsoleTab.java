package ussr.aGui.tabs.view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
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
	
	


	public ConsoleTab(boolean firstTabbedPane, String tabTitle, JMESimulation jmeSimulation){
		this.firstTabbedPane = firstTabbedPane;
		this.tabTitle = tabTitle;		
		this.jmeSimulation = jmeSimulation;
		this.jComponent = new javax.swing.JScrollPane();//JComponent, which will be added to the tab in the main Window.
		initComponents();
	}

	/**
     * Initializes the visual appearance of all components in the tab.
     * Follows Strategy  pattern.
     */
	public void initComponents() {
		
		//jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		
		jTextArea1.setColumns(10);		
		//
		jTextArea1.setRows(1000);
		
		
		
		//System.S
        
     /*   gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		//jTextArea1.setPreferredSize(new Dimension(jPanel1000.getWidth(),jPanel1000.getHeight()));
       
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;*/

		((JScrollPane) jComponent).setViewportView(jTextArea1);
		
		
/*		  File f2 = new File("resources/mainFrame/HERE.txt");
		  PrintStream newps = null;
		  try {
			 newps = new PrintStream(new FileOutputStream(f2));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(newps);
       System.out.println("Some");*/
		 
		//PrintStream printStream = new PrintStream();
		
		
		//System.set
		
	/*	String s = "";
		PrintStream printStream = null;
	try {
		 printStream = new PrintStream();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	System.setOut();
	jTextArea1.setText(s);*/
        //jPanel1000.add(jTextArea1,gridBagConstraints);
		//System.set
	}
	
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JScrollPane jScrollPane1;


	

}
