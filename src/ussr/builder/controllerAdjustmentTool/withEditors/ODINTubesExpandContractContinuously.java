package ussr.builder.controllerAdjustmentTool.withEditors;

import java.rmi.RemoteException;

import ussr.builder.controllerAdjustmentTool.ControllerStrategy;
import ussr.model.Module;
import ussr.remote.facade.RemotePhysicsSimulationImpl;
import ussr.samples.odin.OdinController;

public class ODINTubesExpandContractContinuously extends ControllerStrategy {

	/**
	 * Controller implementation for Odin modular robot.
	 */
	private OdinController controller;	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){		
		controller = (OdinController)selectedModule.getController();
		Float value; 
		try {
			value = RemotePhysicsSimulationImpl.getGUICallbackControl().getValuejSpinnerActuateContinuously();
		} catch (RemoteException e) {
			throw new Error("Failed to receive value in class: "+ this.getClass().getCanonicalName());
		}
			controller.actuateContinuous(value);				
	}	
}
