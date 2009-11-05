package ussr.aGui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.views.constructionTabs.ConstructRobotTab;
import ussr.builder.helpers.BuilderHelper;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.remote.ConsoleSimulationExample;
import ussr.remote.GUISimulationAdapter;
import ussr.remote.facade.RemotePhysicsSimulation;


public class MainFrameController {


	/**
	 * The remote(running of separate JVM than GUI) physics simulation.
	 */
	private static RemotePhysicsSimulation remotePhysicsSimulation;

	/**
	 * Executes closing of main window(frame)by terminating Java Virtual Machine.
	 */
	public static void jMenuItemExitActionPerformed() {	
		System.exit(0);	
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

	/**
	 * Keeps track for how many times simulation was started, so that only first time all connectors on the modules will be connected.
	 */
	private static int timesSelected =0;

	/**
	 * Starts running remote simulation in real time.
	 */
	public static void jButtonRunRealTimeActionPerformed() {
		ConstructRobotTab.setTabEnabled(false);
	
		try {
			if (remotePhysicsSimulation.isPaused()){// Start simulation in real time, if simulation is in paused state
				remotePhysicsSimulation.setPause(false);				
			}
			remotePhysicsSimulation.setRealtime(true);
		} catch (RemoteException e) {
			throw new Error ("Pausing or running remote simulation in real time failed, due to remote exception");
		}

		timesSelected++;

		//connectModules(jmeSimulation);
		//jmeSimulation.setPicker(new PhysicsPicker());*/
	}


	/**
	 * Starts running remote simulation in fast mode.
	 */
	public static void jButtonRunFastActionPerformed() {

		ConstructRobotTab.setTabEnabled(false);		

		try {
			if (remotePhysicsSimulation.isPaused()){// Start simulation  fast, if simulation is in paused state
				remotePhysicsSimulation.setPause(false);				
			}
			remotePhysicsSimulation.setRealtime(false);
		} catch (RemoteException e) {
			throw new Error ("Pausing or running remote simulation in fast mode failed, due to remote exception");
		}

		timesSelected++;
		//connectModules(jmeSimulation);
		//jmeSimulation.setPicker(new PhysicsPicker());
	}

	private static void connectModules(JMESimulation jmeSimulation){

		if (timesSelected==1){
			BuilderHelper.connectAllModules(jmeSimulation);	
			/*Disable GUI components responsible for opening file choosers, because it is possible to load
			 *simulation from XML file only in static state of simulation.*/ 
			MainFrames.setSaveOpenEnabled(false);
		}		
	}

	/**
	 * Executes running remote simulation in step by step fashion.
	 */
	public static void jButtonRunStepByStepActionPerformed() {       	
		ConstructRobotTab.setTabEnabled(false);		
		timesSelected++;
		//connectModules(jmeSimulation);

		try {
			remotePhysicsSimulation.setPause(true);
			remotePhysicsSimulation.setSingleStep(true);
		} catch (RemoteException e) {
			throw new Error ("Running remote simulation in single step mode failed, due to remote exception");
		}		
	}
	
	
	/**
	 * Pauses remote simulation.
	 */
	public static void jButtonPauseActionPerformed() {       	
			try {
				if (remotePhysicsSimulation.isPaused()==false){
					remotePhysicsSimulation.setPause(true);
				}
			} catch (RemoteException e) {
				throw new Error ("Pausing remote simulation failed, due to remote exception");
			}
	}
	
	public static void jButtonTerminateActionPerformed() {
		try {
			remotePhysicsSimulation.stop();			
		} catch (RemoteException e) {
			throw new Error ("Termination of remote simulation failed, due to remote exception");
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
	 * Adds or removes tabs for construction of modular robot morphology.
	 * @param jToggleButtonConstructRobot, the toggle button in main frame.
	 * @param jTabbedPaneFirst, the tabbed pane used as container for construction tabs.
	 * @param tabs, container of all tabs in main frame.
	 */
	public static void jButtonConstructRobotActionPerformed(JToggleButton jToggleButtonConstructRobot, JTabbedPane jTabbedPaneFirst, ArrayList<TabsInter> tabs ) {

		/*Check if tabs are defined*/
		TabsInter constructRobotTab = getTabByTitle(MainFramesInter.CONSTRUCT_ROBOT_TAB_TITLE,tabs);
		TabsInter assignBehaviorsTab = getTabByTitle(MainFramesInter.ASSIGN_BEHAVIORS_TAB_TITLE,tabs);


		if (jToggleButtonConstructRobot.isSelected()){
			/*Add tabs for construction of modular robot*/
			jTabbedPaneFirst.addTab(constructRobotTab.getTabTitle(),new javax.swing.ImageIcon(constructRobotTab.getImageIconDirectory()),constructRobotTab.getJComponent());
			jTabbedPaneFirst.addTab(assignBehaviorsTab.getTabTitle(),new javax.swing.ImageIcon(assignBehaviorsTab.getImageIconDirectory()),assignBehaviorsTab.getJComponent());

			/*Update look and feel for newly added tabs*/		
			MainFrames.changeToLookAndFeel(constructRobotTab.getJComponent());
			MainFrames.changeToLookAndFeel(assignBehaviorsTab.getJComponent());
		}else{
			/*Identify and remove tabs for construction of modular robot*/
			for (int index=0; index < jTabbedPaneFirst.getTabCount(); index++){
				String tabTitle = jTabbedPaneFirst.getTitleAt(index);
				if (tabTitle.equals(MainFramesInter.CONSTRUCT_ROBOT_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
					index--;
				}else if (tabTitle.equalsIgnoreCase(MainFramesInter.ASSIGN_BEHAVIORS_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
				}
			}
		}

	}

	/**
	 * Checks if the tab with specific title is in the container for tabs.
	 * @param tabTitle, the title of the tab to look for.
	 * @param tabs, container of all tabs in main frame.
	 * @return foundTab, the tab with the title to look for.
	 */
	private static TabsInter getTabByTitle( String tabTitle,ArrayList<TabsInter> tabs){
		TabsInter foundTab = null;
		for (int tabNr=0;tabNr<tabs.size();tabNr++){
			String currentTabTitle = tabs.get(tabNr).getTabTitle();
			if (currentTabTitle.equals(tabTitle)){
				foundTab = tabs.get(tabNr);
			}
		}

		if (foundTab == null){
			throw new Error("The tab with title ("+ tabTitle + ") was not found");
		}
		return foundTab;
	}

	/**
	 * Adds or removes tab for visualization of communication between modules.
	 * @param toggleButtonVisualizer, the toggle button in main frame.
	 * @param jTabbedPaneFirst,the tabbed pane used as container for construction tabs.
	 * @param tabs, container of all tabs in main frame.
	 */
	public static void jButtonVisualizerActionPerformed(JToggleButton toggleButtonVisualizer, JTabbedPane jTabbedPaneFirst, ArrayList<TabsInter> tabs) {
		/*Check if tab is defined*/
		TabsInter moduleCommunicationVisualizerTab = getTabByTitle(MainFramesInter.MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE,tabs);

		if (toggleButtonVisualizer.isSelected()){
			/*Add tabs for visualizing module communication*/
			jTabbedPaneFirst.addTab(moduleCommunicationVisualizerTab.getTabTitle(),new javax.swing.ImageIcon(moduleCommunicationVisualizerTab.getImageIconDirectory()),moduleCommunicationVisualizerTab.getJComponent());
			/*Update look and feel for all tabs*/			
			MainFrames.changeToLookAndFeel(moduleCommunicationVisualizerTab.getJComponent());
		}else{
			/*Identify and remove the tab for visualizing module communication*/
			for (int index=0; index < jTabbedPaneFirst.getTabCount(); index++){
				String tabTitle = jTabbedPaneFirst.getTitleAt(index);
				if (tabTitle.equals(MainFramesInter.MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
				}
			}
		}		
	}



	/**
	 * Sets remote physics simulation for this controller.
	 * @param remotePhysicsSimulation, the remote physics simulation.
	 */
	public static void setRemotePhysicsSimulation(RemotePhysicsSimulation remotePhysicsSimulation) {
		MainFrameController.remotePhysicsSimulation = remotePhysicsSimulation;
	}

	

}
