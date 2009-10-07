package ussr.aGui;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextField;

import ussr.aGui.fileChooser.appearance.FileChooserOpenFrame;
import ussr.aGui.fileChooser.appearance.FileChooserSaveFrame;
import ussr.aGui.fileChooser.controller.FileChooserControllerInter;
import ussr.aGui.fileChooser.controller.FileChooserXMLController;
import ussr.aGui.fileChooser.controller.NewFileChooserController;
import ussr.builder.BuilderHelper;
import ussr.builder.SupportedModularRobots;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.ConstructionTools;
import ussr.builder.gui.FileChooser;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

public class MainFrameController {

	/**
	 * Interface for gui;
	 */
	private static GuiInter gui;
	

	

	//private static ArrayList <String> fileExtensions = new ArrayList<String>();


	
	

	/**
	 * Executes closing of main window(frame)
	 * @param frame,
	 */
	public static void jMenuItem1ActionPerformed(javax.swing.JFrame frame) {	
		frame.dispose();// closes only the current window		
	} 



	/**
	 * Opens file chooser in the form of Open dialog
	 * 
	 */
	public static void jMenuItem2ActionPerformed(GuiInter fcOpenFrame) {
		fcOpenFrame.activate();
	}

	/**
	 * Opens file chooser in the form of Save dialog
	 * 
	 */
	public static void jMenuItem3ActionPerformed(GuiInter fcSaveFrame, JMESimulation jmeSimulation) {
		fcSaveFrame.activate();		
	}

	public static void jTextField1FocusGained(JTextField jTextField1) {
		jTextField1.setText("");
	}


	static int timesPressed =0;
	public static void jButton1ActionPerformed(JButton jButton1, JMESimulation jmeSimulation) {		 
		if (jmeSimulation.isPaused()==false){ 
			//guiHelper.passTo(AssistantjTextField, "Simulation is in static state");// informing user
			jmeSimulation.setPause(true);                       
			jButton1.setIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.PAUSE));

		}else{
			timesPressed++;
			if (timesPressed ==1){ // First time is pressed connect all the modules in the morphology
				BuilderHelper.connectAllModules(jmeSimulation);				
			}
			//guiHelper.passTo(AssistantjTextField, "Simulation running");// informing user
			jButton1.setIcon(new javax.swing.ImageIcon(MainFrameInter.DIRECTORY_ICONS + MainFrameInter.PLAY));
			jmeSimulation.setPause(false);
		}
	}


	public static void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		//BuilderMultiRobotSimulation.main(null);
		ATRONSnakeSimulation.main(null);

	}   

	/**
	 * Renders or stops rendering the physics during simulation.
	 * @param jCheckBoxMenuItem1
	 * @param jmeSimulation
	 */
	public static void jCheckBoxMenuItem1ActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItem1  ,JMESimulation jmeSimulation) {       
		if (jmeSimulation.isShowPhysics() == false ){                             
			jCheckBoxMenuItem1.setSelected(true);
			jmeSimulation.setShowPhysics(true);
			//guiHelper.passTo(AssistantjTextField, "Rendering physics");// informing user
		}else {                         
			jCheckBoxMenuItem1.setSelected(false);
			jmeSimulation.setShowPhysics(false);
			//guiHelper.passTo(AssistantjTextField, "Stopped rendering physics");// informing user
		}   
	}


	/**
	 * Renders or stops rendering the wire state during simulation.
	 * @param jCheckBoxMenuItem2
	 * @param jmeSimulation
	 */
	public static void jCheckBoxMenuItem2ActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItem2, JMESimulation jmeSimulation) {

		if ( jmeSimulation.getWireState().isEnabled() == false ){        
			jCheckBoxMenuItem2.setSelected(true);
			jmeSimulation.getWireState().setEnabled(true);
			//guiHelper.passTo(AssistantjTextField, "Rendering wireframe");// informing user
		}else {            
			jCheckBoxMenuItem2.setSelected(false);
			jmeSimulation.getWireState().setEnabled(false);
			//guiHelper.passTo(AssistantjTextField, "Stopped rendering wireframe");// informing user
		}   
	}

	/**
	 * 
	 * Renders or stops rendering the bounds during simulation.
	 * @param jCheckBoxMenuItem3
	 * @param jmeSimulation
	 */
	public static void jCheckBoxMenuItem3ActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItem3, JMESimulation jmeSimulation) {       
		if ( jmeSimulation.isShowBounds() == false ){        
			jCheckBoxMenuItem3.setSelected(true);
			jmeSimulation.setShowBounds(true);
			//guiHelper.passTo(AssistantjTextField, "Rendering wireframe");// informing user
		}else {            
			jCheckBoxMenuItem3.setSelected(false);
			jmeSimulation.setShowBounds(false);
			//guiHelper.passTo(AssistantjTextField, "Stopped rendering wireframe");// informing user
		}   
	}
	
	 /**
	  * Renders or stops rendering the normals during simulation.
	 * @param jCheckBoxMenuItem4
	 * @param jmeSimulation
	 */
	public static void jCheckBoxMenuItem4ActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItem4, JMESimulation jmeSimulation) {    
			if ( jmeSimulation.isShowNormals() == false ){            
				jCheckBoxMenuItem4.setSelected(true);
				jmeSimulation.setShowNormals(true);
				//guiHelper.passTo(AssistantjTextField, "Rendering normals");// informing user
			}else {            
				jCheckBoxMenuItem4.setSelected(false);
				jmeSimulation.setShowNormals(false);
				//guiHelper.passTo(AssistantjTextField, "Stopped rendering normals");// informing user
			}      
	    }
	  
	  /**
	   * Renders or stops rendering the lights during simulation.
	 * @param jCheckBoxMenuItem5
	 * @param jmeSimulation
	 */
	public static void jCheckBoxMenuItem5ActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItem5, JMESimulation jmeSimulation) {   
			if (jmeSimulation.getLightState().isEnabled() == false ){                       
				jCheckBoxMenuItem5.setSelected(true);
				jmeSimulation.getLightState().setEnabled(true);
				//guiHelper.passTo(AssistantjTextField, "Rendering lights");// informing user
			}else {                   
				jCheckBoxMenuItem5.setSelected(false);
				jmeSimulation.getLightState().setEnabled(false);
				//guiHelper.passTo(AssistantjTextField, "Stopped rendering lights");// informing user
			}  
	    }

	/**
	 * Renders or stops rendering the depth of the buffer during simulation.
	 * @param jCheckBoxMenuItem6
	 * @param jmeSimulation
	 */
	public static void jCheckBoxMenuItem6ActionPerformed(
			JCheckBoxMenuItem jCheckBoxMenuItem6, JMESimulation jmeSimulation) {
		if (jmeSimulation.isShowDepth() == false ){                       
			jCheckBoxMenuItem6.setSelected(true);
			jmeSimulation.setShowDepth(true);
			//guiHelper.passTo(AssistantjTextField, "Rendering lights");// informing user
		}else {                   
			jCheckBoxMenuItem6.setSelected(false);
			jmeSimulation.setShowDepth(false);
			//guiHelper.passTo(AssistantjTextField, "Stopped rendering lights");// informing user
		}  
		
	}
	
	/**
	 * Initial simulation step
	 */
	private static int simulationStep =0;
	
	public static void jButton2ActionPerformed(JMESimulation jmeSimulation) {       
		simulationStep++;
		//guiHelper.passTo(AssistantjTextField, "Executed simulation step Nr: "+ simulationStep);
		jmeSimulation.setSingleStep(true);
	    }
	
	public static void jCheckBoxMenuItemActionPerformedNew(javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemNew, javax.swing.JTabbedPane jTabbedPaneNew ) {
		
		jTabbedPaneNew.setFocusable(true);
		//jCheckBoxMenuItemNew.setSelected(false);
		//jTabbedPaneNew.getComponent(0).setEnabled(false);
		/*   if (jCheckBoxMenuItemNew.isSelected()){
        	jTabbedPaneNew.setVisible(true);
        }else{
        	jTabbedPaneNew.setVisible(false);
        }*/
    }

}
