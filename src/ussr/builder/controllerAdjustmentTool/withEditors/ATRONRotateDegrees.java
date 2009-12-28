package ussr.builder.controllerAdjustmentTool.withEditors;

import java.rmi.RemoteException;

import ussr.builder.controllerAdjustmentTool.ControllerStrategy;
import ussr.model.Module;
import ussr.remote.facade.RemotePhysicsSimulationImpl;
import ussr.samples.atron.ATRONController;

/**
 * @author Konstantinas
 */
public class ATRONRotateDegrees extends ControllerStrategy {

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
		
		int value; 
		try {
			value = RemotePhysicsSimulationImpl.getGUICallbackControl().getValuejSpinnerRotateDegrees();
		} catch (RemoteException e) {
			throw new Error("Failed to receive falue for in class: "+ this.getClass().getCanonicalName());
		}
		
		controller.rotateDegrees(value);	
	}
}
