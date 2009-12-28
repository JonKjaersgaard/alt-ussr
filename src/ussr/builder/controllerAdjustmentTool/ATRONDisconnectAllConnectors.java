package ussr.builder.controllerAdjustmentTool;

import java.rmi.RemoteException;

import ussr.aGui.tabs.constructionTabs.AssignableControllersEditors;
import ussr.builder.controllerAdjustmentTool.ControllerStrategy;
import ussr.builder.helpers.BuilderHelper;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.remote.facade.RemotePhysicsSimulationImpl;
import ussr.samples.atron.ATRONController;

public class ATRONDisconnectAllConnectors extends ControllerStrategy {

	/**
	 * The controller class providing the ATRON API
	 */
	private ATRONController controller;	

	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){	
		controller = (ATRONController)selectedModule.getController();
		controller.disconnectAll();		
		//BuilderHelper.deleteModule(selectedModule);//Maybe there is no need for that.
		
	}
}
