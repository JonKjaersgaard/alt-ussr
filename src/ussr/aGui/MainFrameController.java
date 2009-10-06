package ussr.aGui;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import ussr.aGui.fileChooser.appearance.FileChooserOpenFrame;
import ussr.aGui.fileChooser.appearance.FileChooserSaveFrame;
import ussr.aGui.fileChooser.controller.FileChooserControllerInter;
import ussr.aGui.fileChooser.controller.FileChooserXMLController;
import ussr.aGui.fileChooser.controller.NewFileChooserController;
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

public class MainFrameController {

	private static GuiInter gui;	
	
	private static ArrayList <String> fileExtensions = new ArrayList<String>();
	
	
	
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
		fileExtensions.add(".xml");//Repeating code
		//fileExtensions.add(".txt");//For debugging
		
		FileChooserControllerInter fcXMLController = new FileChooserXMLController();//Repeating code
		//FileChooserControllerInter fcNEWController = new NewFileChooserController();
		
		ArrayList<FileChooserControllerInter> fcControllers = new ArrayList<FileChooserControllerInter>();
		fcControllers.add(fcXMLController);
		//fcControllers.add(fcNEWController);//For debugging
		
		gui = new FileChooserOpenFrame(fileExtensions,fcControllers);		
		gui.activate(); 
    }
	
	/**
	 * Opens file chooser in the form of Save dialog
	 * @param evt
	 */
	public static void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
		fileExtensions.add(".xml");//Repeating code
		//fileExtensions.add(".txt");//For debugging
		
		FileChooserControllerInter fcXMLController = new FileChooserXMLController();//Repeating code
		//FileChooserControllerInter fcNEWController = new NewFileChooserController();//For debugging
		
		ArrayList<FileChooserControllerInter> fcControllers = new ArrayList<FileChooserControllerInter>();
		fcControllers.add(fcXMLController);
		//fcControllers.add(fcNEWController);//For debugging
		
		gui= new FileChooserSaveFrame(fileExtensions,fcControllers);
		gui.activate(); 
    }
	
	 public static void jTextField1FocusGained(JTextField jTextField1) {
		 jTextField1.setText("");
	    }
	
	 
	 static int timesPressed =0;
	 public static void jButton1ActionPerformed(JButton jButton1) {
		 timesPressed++;        
		if (timesPressed == 1){
			jButton1.setIcon(new javax.swing.ImageIcon(GuiInter.DIRECTORY_ICONS + GuiInter.PAUSE));
			
		}else if (timesPressed ==2){
			jButton1.setIcon(new javax.swing.ImageIcon(GuiInter.DIRECTORY_ICONS + GuiInter.PLAY));
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
	 
		 
	    
}
