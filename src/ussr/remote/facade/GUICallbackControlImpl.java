package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.aGui.MainFrameSeparate;
import ussr.aGui.MainFrames;
import ussr.aGui.controllers.GeneralController;
import ussr.aGui.tabs.constructionTabs.AssignableControllersEditors;
import ussr.aGui.tabs.constructionTabs.ConstructRobotTab;
import ussr.aGui.tabs.controllers.AssignControllerTabController;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.controllers.AssignLabelsTabController;
import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.aGui.tabs.designHelpers.hintPanel.HintPanelTypes;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.builder.enumerations.SupportedModularRobots;
import ussr.builder.enumerations.tools.ConstructionTools;
import ussr.builder.simulationLoader.SimulationSpecification;


/**
 * Wrapper for a GUI call backs allowing it to be used as a remote object.
 * (Used on the GUI side.)
 * @author Konstantinas
 */
public class GUICallbackControlImpl extends UnicastRemoteObject implements GUICallbackControl {

	/**
	 * Wrapper for a GUI call backs allowing it to be used as a remote object.
	 * (Used on the GUI side.)
	 * @throws RemoteException
	 */
	public GUICallbackControlImpl() throws RemoteException {
	}

	/**
	 * Adapts Construct Robot tab to the tool chosen by user. To be more precise disables and enables relevant components of the tab. 
	 * @param chosenTool,the tool chosen by the user in Construct Robot tab.
	 */
	public void adaptConstructRobotTabToChosenTool(ConstructionTools chosenTool)throws RemoteException{
		ConstructRobotTabController.adaptConstructRobotTabToChosenTool(chosenTool);
	};

	/**
	 * Populates the table in Assign behavior tab with labels of entity(module, connector and so on) selected in simulation environment.
	 * @param labels, the string of labels separated by comma to populate the table with.
	 */
	public void updateTableWithLabels(String labels) throws RemoteException{
		AssignLabelsTabController.updateTableLabels(labels);
	}

	/**
	 * Sets the global ID of the module selected in simulation environment.
	 * @param selectedModuleID, the global ID of the module selected in simulation environment.
	 */
	public void setSelectedModuleID(int selectedModuleID)throws RemoteException{
		ConstructRobotTabController.setSelectedModuleID(selectedModuleID);
	}

	/**
	 * Adapts construct robot to the module type selected in simulation environment.
	 * @param supportedModularRobot, the type of modular robot to adapt to.
	 */
	public void adaptConstructRobotTabToSelectedModuleType(SupportedModularRobots supportedModularRobot)throws RemoteException{
		ConstructRobotTabController.adaptTabToSelectedModule(supportedModularRobot);		
	}

	/**
	 * Updates the hint panel in the tab called Assign Behaviors by changing icon and text of it.
	 * @param hintPanelTypes, the type of hint panel.
	 * @param text, the text for hint panel to display.
	 */
	public void updateHintPanelAssignBehaviorsTab(HintPanelTypes hintPanelTypes, String text)throws RemoteException{
		AssignControllerTabController.updateHintPanel(hintPanelTypes, text);
	}

	/**
	 * Calls back GUI in order to indicate new module addition in simulation environment.
	 */
	public void newModuleAdded()throws RemoteException{
		ConstructRobotTabController.newModuleAdded();		
	}

	/**
	 * Returns an object containing description of simulation and entities in it form GUI side.
	 * @return an object containing description of simulation and entities in it form GUI side.
	 */
	public SimulationSpecification getSimulationSpecification() throws RemoteException{
		return SimulationTabController.getSimulationSpecification();
	}

	/**
	 * The flag used to indicate that robots are loaded from or not simulation xml file.
	 * Robots can be loaded from simulation and separate robot xml files.
	 */
	private static boolean fromSimulationXMLFile= false;

	/**
	 * Sets the flag indicating that robots are loaded from or not simulation xml file.
	 * @param fromSimulationXMLFile, the flag indicating that robots are loaded from or not simulation xml file.
	 */
	public static void setFromSimulationXMLFile(boolean fromSimulationXMLFile) {
		GUICallbackControlImpl.fromSimulationXMLFile = fromSimulationXMLFile;
	}

	/**
	 * Updates the GUI and simulation specification in case when new robot is loaded from GUI.
	 * @param simulationSpecification, object describing simulation and objects in it.
	 */
	public void newRobotLoaded(SimulationSpecification simulationSpecification)throws RemoteException{
		SimulationTab.addRobotNodes(simulationSpecification,fromSimulationXMLFile,true);
		SimulationTabController.setSimulationSpecification(simulationSpecification);
	}

	
	/**
	 * Returns default construction module type chosen by user in GUI.
	 * @return default construction module type chosen by user in GUI.
	 */
	public String getDefaultConstructionModuleType()throws RemoteException{
		return ConstructRobotTab.getDefaultConstructionModuleType();
	}

	/**
	 * Returns the value in jSpinner associated with controller "rotate continuous" for Atron.
	 * @return the value in jSpinner associated with controller "rotate continuous" for Atron.
	 * @throws RemoteException
	 */
	public Float getValueJSpinnerAtronSpeedRotateContinuous()throws RemoteException{
		return AssignableControllersEditors.getValueJSpinnerAtronSpeedRotateContinuous();
	}
	
	/**
	 * Returns the value in jSpinner associated with controller "rotate degrees" for Atron.
	 * @return the value in jSpinner associated with controller "rotate degrees" for Atron.
	 */
	public Integer getValuejSpinnerAtronRotateDegrees()throws RemoteException{
		return AssignableControllersEditors.getValuejSpinnerAtronRotateDegrees();
	}

	/**
	 * Returns the nr. of connector selected by user.
	 * @return the nr. of connector selected by user.
	 */
	public Integer getValueAtronNrsConnectors() throws RemoteException {	
		return AssignableControllersEditors.getValueAtronNrsConnectors();
	}

	/**
	 * Returns the value in jSpinner associated with controller actuate continuously for Odin.
	 * @return the value in jSpinner associated with controller actuate continuously for Odin.
	 */
	public Float getValuejSpinnerOdinActuateContinuously() throws RemoteException {		
		return AssignableControllersEditors.getValuejSpinnerOdinActuateContinuously();
	}	
	
	/**
	 * Returns selected number of actuator associated with controller(rotate continuous) implemented for MTRAN.
	 * @return selected number of actuator associated with controller(rotate continuous) implemented for MTRAN.
	 */
	public int getSelectedjComboBoxMtranNrsActuators() throws RemoteException {		
		return AssignableControllersEditors.getSelectedjComboBoxMtranNrsActuators();
	}
	
	/**
	 * Returns the value of jSpinner associated with controller rotate continuously for M-Tran.
	 * @return the value of jSpinner associated with controller rotate continuously for M-Tran.
	 * @throws RemoteException
	 */
	public int getValuejSpinnerMtranRotateContinuously() throws RemoteException {		
		return AssignableControllersEditors.getValuejSpinnerMtranRotateContinuously();
	}
	
	/**
	 * Sets remote simulation to null and disables GUI in relation to that.
	 * @throws RemoteException
	 */
	public void setRemoteSimulationToNull()throws RemoteException {
		GeneralController.setRemotePhysicsSimulation(null);
		if (MainFrames.getMainFrame() != null){
			MainFrameSeparate.setMainFrameSeparateEnabled(false);
			SimulationTab.setTabVisible(false);
		}	
	}
	
	/**
	 * Sets the name of default construction module type in Construct Robot Tab.
	 * @param defaultConstructionModuleType,the name of default module type. 
	 */
	public void setSelectedDefaultConstructionModule(Object defaultConstructionModuleType)throws RemoteException{
		ConstructRobotTab.setSelectedDefaultConstructionModule(defaultConstructionModuleType);
	}
}
