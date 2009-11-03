package ussr.aGui;

import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.additionalResources.HintPanelInter;
import ussr.aGui.tabs.additionalResources.HintPanelTypes;
import ussr.aGui.tabs.views.constructionTabs.ConstructRobotTab;
import ussr.builder.SupportedModularRobots;
import ussr.builder.helpers.BuilderHelper;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
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
	public static void openActionPerformed(FramesInter fcOpenFrame) {
		fcOpenFrame.activate();		
	}

	/**
	 * Opens file chooser in the form of Save dialog
	 * 
	 */
	public static void saveActionPerformed(FramesInter fcSaveFrame) {				
		fcSaveFrame.activate();				
	}

	private static int timesSelected =0;

	/**
	 * Controls running simulation in real time.
	 * @param jmeSimulation
	 */
	public static void jButtonRunRealTimeActionPerformed(JMESimulation jmeSimulation) {
		ConstructRobotTab.setTabEnabled(false);

		timesSelected++;
		if (jmeSimulation.isPaused()){// Start simulation in real time, if simulation is in paused state
			jmeSimulation.setRealtime(true);
			jmeSimulation.setPause(false);				
		}else if (jmeSimulation.isRealtime()==false){//if simulation is running fast, then run it in real time
			jmeSimulation.setRealtime(true);
		}	
		connectModules(jmeSimulation);
		jmeSimulation.setPicker(new PhysicsPicker());
	}


	/**
	 * Controls running simulation in fast time.
	 * @param jmeSimulation
	 */
	public static void jButtonRunFastActionPerformed(JMESimulation jmeSimulation) {
		ConstructRobotTab.setTabEnabled(false);		
	
		if (jmeSimulation.isPaused()){// Start simulation  fast, if simulation is in paused state
			jmeSimulation.setRealtime(false);
			jmeSimulation.setPause(false);				
		}else if (jmeSimulation.isRealtime()==true){//if simulation in real time, then run it fast
			jmeSimulation.setRealtime(false);
		}	
		timesSelected++;
		connectModules(jmeSimulation);
		jmeSimulation.setPicker(new PhysicsPicker());
	}

	private static void connectModules(JMESimulation jmeSimulation){

		if (timesSelected==1){
			BuilderHelper.connectAllModules(jmeSimulation);	
			/*Disable GUI components responsible for opening file choosers, because it is possible to load
			 *simulation from XML file only in static state of simulation.*/ 
			MainFrame.setSaveOpenEnabled(false);
		}		
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
		}else {                   
			jCheckBoxMenuItem6.setSelected(false);
			jmeSimulation.setShowDepth(false);			
		}  

	}


	/**
	 * Controls running simulation in step by step fashion.
	 * @param jmeSimulation
	 */
	public static void jButtonRunStepByStepActionPerformed(JMESimulation jmeSimulation) {       	
		ConstructRobotTab.setTabEnabled(false);		
		timesSelected++;
		connectModules(jmeSimulation);

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


	/**
	 * @param jToggleButtonConstructRobot
	 * @param jTabbedPaneFirst
	 * @param tabs
	 */
	public static void jButtonConstructRobotActionPerformed(JToggleButton jToggleButtonConstructRobot, JTabbedPane jTabbedPaneFirst, ArrayList<TabsInter> tabs ) {
		
		if (jToggleButtonConstructRobot.isSelected()){
			jTabbedPaneFirst.addTab(tabs.get(0).getTabTitle(),new javax.swing.ImageIcon(tabs.get(0).getImageIconDirectory()),tabs.get(0).getJComponent());
			jTabbedPaneFirst.addTab(tabs.get(1).getTabTitle(),new javax.swing.ImageIcon(tabs.get(1).getImageIconDirectory()),tabs.get(1).getJComponent());
		}else{
			for (int index=0; index < jTabbedPaneFirst.getTabCount(); index++){
				String tabTitle = jTabbedPaneFirst.getTitleAt(index);
				if (tabTitle.equalsIgnoreCase(tabs.get(0).getTabTitle())){
					jTabbedPaneFirst.removeTabAt(index);
					index--;
				}else if (tabTitle.equalsIgnoreCase(tabs.get(1).getTabTitle())){
					jTabbedPaneFirst.removeTabAt(index);
				}
			}
		}
				
		MainFrame.changeToLookAndFeel(jTabbedPaneFirst);
	}

	public static void jButtonVisualizerActionPerformed(JToggleButton toggleButtonVisualizer, JTabbedPane jTabbedPaneFirst, ArrayList<TabsInter> tabs) {
		if (toggleButtonVisualizer.isSelected()){
			jTabbedPaneFirst.addTab(tabs.get(2).getTabTitle(),new javax.swing.ImageIcon(tabs.get(2).getImageIconDirectory()),tabs.get(2).getJComponent());
			
		}else{
			for (int index=0; index < jTabbedPaneFirst.getTabCount(); index++){
				String tabTitle = jTabbedPaneFirst.getTitleAt(index);
				if (tabTitle.equalsIgnoreCase(tabs.get(2).getTabTitle())){
					jTabbedPaneFirst.removeTabAt(index);
				}
			}
		}
				
		MainFrame.changeToLookAndFeel(jTabbedPaneFirst);
		
	}


}
