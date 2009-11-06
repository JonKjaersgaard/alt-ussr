package ussr.remote.facade;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.SupportedModularRobots;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.ConstructionTools;
import ussr.builder.genericTools.ColorConnectors;
import ussr.builder.genericTools.RemoveModule;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.physics.jme.pickers.Picker;

/**
 * Pickers (left side of the mouse selectors) supported by builder.
 * @author Konstantinas
 *
 */
public enum BuilderSupportingPickers {

	
	/**
	 * Move objects in simulation environment during simulation runtime.
	 */
	DEFAULT(new PhysicsPicker()),
	
	/**
	 * Remove module in static state of simulation (also works during simulation runtime, however that is not recommended).
	 */
	REMOVE_MODULE(new RemoveModule()),
	
	/**
	 * Move module with movement of the mouse (hold left side of the mouse).
	 */
	MOVE_MODULE(new PhysicsPicker(true,true)),	
	
	//ROTATE_OPPOSITE(new ConstructionToolSpecification(ConstructRobotTabController.getBuilderControl().getJMESimulation(),ConstructRobotTabController.getChosenMRname(),ConstructionTools.OPPOSITE_ROTATION)),
	
	/**
	 * Colors connectors on the module with color coding.
	 */
	COLOR_CONNECTORS(new ColorConnectors());
	
	private Picker picker;
	BuilderSupportingPickers(Picker picker){
		this.picker = picker;
	}
	public Picker getPicker(){
		return this.picker;
	}
	
/*	BuilderSupportingPickers(class className){
	
	}*/
		
	
	
}
