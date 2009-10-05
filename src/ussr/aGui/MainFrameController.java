package ussr.aGui;

import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ussr.builder.BuilderHelper;
import ussr.builder.BuilderMultiRobotSimulation;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

public class MainFrameController {

	private static Gui gui;	
	
	/**
	 * Executes closing of main window(frame)
	 * @param frame,
	 */
	public static void jMenuItem1ActionPerformed(javax.swing.JFrame frame) {
		//frame.setVisible(false);// closes current window and simulation window
		//System.exit(1);// closes current window and simulation window
		frame.dispose();// closes only the current window		
	} 
	
	
	/**
	 * Opens file chooser in the form of Open dialog
	 * @param evt
	 */
	public static void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		gui= new FileChooserOpen();
		gui.activate(); 
    }
	
	/**
	 * Opens file chooser in the form of Save dialog
	 * @param evt
	 */
	public static void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
		gui= new FileChooserSave();
		gui.activate(); 
    }
	
	 public static void jTextField1FocusGained(JTextField jTextField1) {
		 jTextField1.setText("");
	    }
	
	 
	 static int timesPressed =0;
	 public static void jButton1ActionPerformed(JButton jButton1) {
		 timesPressed++;        
		if (timesPressed == 1){
			jButton1.setIcon(new javax.swing.ImageIcon(Gui.DIRECTORY_ICONS + Gui.PAUSE));
			
		}else if (timesPressed ==2){
			jButton1.setIcon(new javax.swing.ImageIcon(Gui.DIRECTORY_ICONS + Gui.PLAY));
		}
		 
		 /*if (JME_simulation.isPaused()==false){ 
				//guiHelper.passTo(AssistantjTextField, "Simulation is in static state");// informing user
				//JME_simulation.setPause(true);                       
				jButton1.setIcon(new javax.swing.ImageIcon(directoryForIcons + pauseIcon));

			}else{
				timesPressed++;
				if (timesPressed ==1){ // First time is pressed connect all the modules in the morphology
					BuilderHelper.connectAllModules(JME_simulation);		
				}
				guiHelper.passTo(AssistantjTextField, "Simulation running");// informing user
				pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + playIcon));
				JME_simulation.setPause(false);
			}*/
	    }
	 
	 
	public static void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		 //BuilderMultiRobotSimulation.main(null);
		ATRONSnakeSimulation.main(null);
			
	 }
		 
		/* SwingUtilities.invokeLater(new Runnable()
	      {
			 public void run()

	        {

				 BuilderMultiRobotSimulation.main(null);        

	        }

	      });*/
		 
		 
		

		 
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
