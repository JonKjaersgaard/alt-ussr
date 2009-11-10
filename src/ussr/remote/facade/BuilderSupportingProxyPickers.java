package ussr.remote.facade;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;

import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.enums.ConstructionTools;
import ussr.builder.enums.LabelingTools;
import ussr.builder.genericTools.ColorConnectors;
import ussr.builder.genericTools.RemoveModule;
import ussr.builder.labelingTools.LabeledEntities;
import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.physics.jme.pickers.Picker;

/**
 * Unicast pickers (left side of the mouse selectors) supported by builder.
 * @author Konstantinas
 *
 */
public enum BuilderSupportingProxyPickers{

	/**
	 * Move modules with the mouse in simulation environment during simulation runtime.
	 */
	DEFAULT(new PhysicsPicker()),
	
	/**
	 * Remove module in static state of simulation (also works during simulation runtime, however that is not recommended).
	 */
	REMOVE_MODULE(new RemoveModule()),
	
	/**
	 * Move module with movement of the mouse in static state of simulation (hold left side of the mouse).
	 */
	MOVE_MODULE(new PhysicsPicker(true,true)),	
	
	/**
	 * Rotates the module with opposite rotation to current one.
	 */
	ROTATE_MODULE_OPPOSITE(new ConstructionToolSpecification(ConstructionTools.OPPOSITE_ROTATION)),

	/**
	 * Colors connectors on the module with color coding.
	 */
	COLOR_CONNECTORS(new ColorConnectors()),
	
	/**
	 * Adds new module on connector selected by user.
	 */
	ON_SELECTED_CONNECTOR(new ConstructionToolSpecification(ConstructionTools.ON_SELECTED_CONNECTOR)),
	
	/**
	 * Adds new modules on all connectors of selected module.
	 */
	ON_ALL_CONNECTORS(new ConstructionToolSpecification(ConstructionTools.ON_ALL_CONNECTORS)),
	
	/**
	 * This tool is used to implement robot specific properties. Like for instance for Odin switching  module type by selecting module in simulation environment.
	 */
	VARIATE_MODULE_PROPERTIES (new ConstructionToolSpecification(ConstructionTools.VARIATE_PROPERTIES)),
	
	/**
	 * With each selection of module in simulation environment rotates it with available (standard) rotation.
	 */
	ROTATE_MODULE_STANDARD_IN_LOOP(new ConstructionToolSpecification(ConstructionTools.STANDARD_ROTATIONS_IN_LOOP)),
	
	//TODO
	READ_MODULE_LABELS (new LabelingToolSpecification(LabeledEntities.MODULE, LabelingTools.READ_LABELS)),
	
	//TODO
	READ_CONNECTOR_LABELS (new LabelingToolSpecification(LabeledEntities.CONNECTOR,LabelingTools.READ_LABELS)),
	
	//TODO
	READ_SENSOR_LABELS(new LabelingToolSpecification(LabeledEntities.SENSOR,LabelingTools.READ_LABELS)),
	
	;
	
	/**
	 * The picker supported by builder.
	 */
	private Picker picker;
	
	/**
	 * Contains pickers (left side of the mouse selectors) supported by builder.
	 * @param picker, the picker supported by builder.
	 */
	BuilderSupportingProxyPickers(Picker picker){
		this.picker = picker;
	}
	/**
	 * Returns the picker supported by builder.
	 * @return, the picker supported by builder.
	 */
	public Picker getPicker(){
		return this.picker;
	}
			
	
}
