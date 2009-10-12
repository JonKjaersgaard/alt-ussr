package ussr.aGui;

import java.util.ArrayList;


import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextField;
import ussr.aGui.tabs.TabsInter;
import ussr.builder.BuilderHelper;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

public class MainFrameController {

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
	public static void openActionPerformed(GuiInter fcOpenFrame) {
		fcOpenFrame.activate();		
	}

	/**
	 * Opens file chooser in the form of Save dialog
	 * 
	 */
	public static void saveActionPerformed(GuiInter fcSaveFrame) {				
		fcSaveFrame.activate();				
	}

	public static void jTextField1FocusGained(JTextField jTextField1) {
		jTextField1.setText("");
	}


	/**
	 * Used to keep track when run button(real time or fast) is pressed first time.
	 */
	private static int timesPressed =0;

	/**
	 * Connects all modules (connectors), when the run button(real time or fast) is pressed first time. 
	 * @param jmeSimulation
	 */
	private static void connectConnectors(JMESimulation jmeSimulation){
		timesPressed++;
		if (timesPressed ==1){ // First time is pressed connect all modules in the morphology
			BuilderHelper.connectAllModules(jmeSimulation);				
		}
		/*Disable GUI components responsible for opening file choosers, because it is possible to load
		 *simulation from XML file only in static state of simulation.*/ 
		MainFrame.setSaveOpenEnabled(false);
		//ConstructionTab.setEnabled(false);
	};

	/**
	 * Controls running simulation in real time.
	 * @param jmeSimulation
	 */
	public static void jButton1ActionPerformed(JMESimulation jmeSimulation) {		 
		if (jmeSimulation.isPaused()){// Start simulation in real time, if simulation is in paused state
			jmeSimulation.setRealtime(true);
			jmeSimulation.setPause(false);				
		}else if (jmeSimulation.isRealtime()==false){//if simulation is running fast, then run it in real time
			jmeSimulation.setRealtime(true);
		}
		connectConnectors(jmeSimulation);// first time button pressed connect connectors on modules
	}



	/**
	 * Controls running simulation in fast time.
	 * @param jmeSimulation
	 */
	public static void jButtonRunFastActionPerformed(JMESimulation jmeSimulation) {		 
		if (jmeSimulation.isPaused()){// Start simulation  fast, if simulation is in paused state
			jmeSimulation.setRealtime(false);
			jmeSimulation.setPause(false);				
		}else if (jmeSimulation.isRealtime()==true){//if simulation in real time, then run it fast
			jmeSimulation.setRealtime(false);
		}
		connectConnectors(jmeSimulation);// first time button pressed connect connectors on modules
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
		}else {                   
			jCheckBoxMenuItem5.setSelected(false);
			jmeSimulation.getLightState().setEnabled(false);			
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
	 * Controls running simulation in step by step fashion.
	 * @param jmeSimulation
	 */
	public static void jButtonRunStepByStepActionPerformed(JMESimulation jmeSimulation) {       	
		jmeSimulation.setPause(true);
		jmeSimulation.setSingleStep(true);
	}



	/**
	 * Controls pausing of running simulation.
	 * @param jmeSimulation
	 */
	public static void jButtonPauseActionPerformed(JMESimulation jmeSimulation) {       	
		if (jmeSimulation.isPaused()==false)
			jmeSimulation.setPause(true);
	}


    static int i;

	public static void jCheckBoxMenuItemActionPerformedNew(ArrayList<javax.swing.JCheckBoxMenuItem> checkBoxMenuItems, javax.swing.JTabbedPane jTabbedPane1, ArrayList<TabsInter> tabs ) {

		for (int index = 0; index<tabs.size(); index++ ){
			if ( (checkBoxMenuItems.get(index).getText().equalsIgnoreCase(tabs.get(index).getTabTitle()))&&checkBoxMenuItems.get(index).isSelected()==false){
				checkBoxMenuItems.get(index).setSelected(false);
				//jTabbedPane1.setEnabledAt(index, false);
			 	if(jTabbedPane1.getTabCount()-1<index){
					i++;
                   System.out.println("Here;" +i);
				}else{
					jTabbedPane1.remove(index);
				}
				
				
			}else if((checkBoxMenuItems.get(index).getText().equalsIgnoreCase(tabs.get(index).getTabTitle()))&&checkBoxMenuItems.get(index).isSelected()==true){
				checkBoxMenuItems.get(index).setSelected(true);
				//jTabbedPane1.setEnabledAt(index, true);			
					jTabbedPane1.add(tabs.get(index).getTabTitle(),tabs.get(index).getJPanel1000());				

			}
			}
		

		}

	}
