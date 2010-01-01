package ussr.builder.controllerAdjustmentTool.withEditors;

import java.rmi.RemoteException;

import ussr.builder.controllerAdjustmentTool.ControllerStrategy;
import ussr.model.Module;
import ussr.remote.facade.RemotePhysicsSimulationImpl;
import ussr.samples.mtran.MTRANController;


public class MTRANRotateContinuously extends ControllerStrategy {

	/**
	 * The controller class providing ATRON API
	 */
	private MTRANController controller;	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){		
		controller = (MTRANController)selectedModule.getController();
		int velocity;
		int actuator;
		try {
			velocity = RemotePhysicsSimulationImpl.getGUICallbackControl().getValuejSpinnerMtranRotateContinuously();
			actuator = RemotePhysicsSimulationImpl.getGUICallbackControl().getSelectedjComboBoxMtranNrsActuators();
		} catch (RemoteException e) {
			throw new Error("Failed to receive value in class: "+ this.getClass().getCanonicalName());
		}
		
		controller.rotateContinuous(velocity, actuator);
	}	
}
