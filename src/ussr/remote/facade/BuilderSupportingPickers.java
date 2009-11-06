package ussr.remote.facade;

import ussr.builder.genericTools.ColorConnectors;
import ussr.builder.genericTools.RemoveModule;
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
	
	//ON_SELECTED_CONNECTOR ();
	
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
}
