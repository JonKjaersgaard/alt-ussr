package ussr.aGui;

import java.lang.reflect.Method;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ussr.builder.BuilderMultiRobotSimulation;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;

public class MainFrameController {

	/**
	 * The physical simulation
	 */	   
	private JMESimulation JmeSimulation = new JMESimulation(null, null);
	
	/**
	 * FileChooser object as save dialog window
	 */
	private FileChooser fileChooserSave;

	/**
	 * FileChooser object as open dialog window
	 */
	private FileChooser fileChooserOpen;
	
	
	//private JMEBasicGraphicalSimulation JME_simulation ;
	
	
	
	/**
	 * Executes closing of main window(frame)
	 * @param evt, event of selecting the element in GUI
	 */
	public static void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(1);	     
	} 
	
	
	public static void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		FileChooser fileChooserOpen = new FileChooser();
		fileChooserOpen.activate();
		//fileChooserOpen.activate();   
    }
	
	 public static void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		 SwingUtilities.invokeLater(new Runnable()
	      {
			 public void run()

	        {

				 BuilderMultiRobotSimulation.main(null);        

	        }

	      });

		 
		 //BuilderMultiRobotSimulation bd = new BuilderMultiRobotSimulation();
			//bd.main(null);
		 
		 /*private void some;
		 SwingWorker worker = new SwingWorker(){

			 
		
				@Override
				protected void doInBackground() throws Exception {
					
						
						return BuilderMultiRobotSimulation.main(null);
				   

				
				}
				
			}; */
			
			
		 
		 
		 
		 
			            	     
	 
		 
	    }
}
