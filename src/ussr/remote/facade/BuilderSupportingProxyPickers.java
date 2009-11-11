package ussr.remote.facade;

import java.io.Serializable;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;

import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.enumerations.ConstructionTools;
import ussr.builder.enumerations.LabelingTools;
import ussr.builder.genericTools.ColorModuleConnectors;
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
public class BuilderSupportingProxyPickers {
	/*private PickerSelection picker;
	private Object[] arguments;
	
	public static BuilderSupportingProxyPickers createDefault() {
		BuilderSupportingProxyPickers object = new BuilderSupportingProxyPickers();
		object.picker = PickerSelection.DEFAULT;
		return object;
	}
	
	public static BuilderSupportingProxyPickers createConstructionToolSpec(ConstructionTools a, String b) {
		BuilderSupportingProxyPickers object = new BuilderSupportingProxyPickers();
		object.picker = PickerSelection.CONSTRUCTION_TOOL_SPEC;
		object.arguments = new Object[2];
		object.arguments[0] = a;
		object.arguments[1] = b;
		return object;		
	}
	
public static enum PickerSelection implements Serializable {

	*//**
	 * Move modules with the mouse in simulation environment during simulation runtime.
	 *//*
	DEFAULT,
	
	*//**
	 * Remove module in static state of simulation (also works during simulation runtime, however that is not recommended).
	 *//*
	REMOVE_MODULE(new RemoveModule()),
	
	*//**
	 * Move module with movement of the mouse in static state of simulation (hold left side of the mouse).
	 *//*
	MOVE_MODULE,	
	
	*//**
	 * Rotates the module with opposite rotation to current one.
	 *//*
	ROTATE_MODULE_OPPOSITE(new ConstructionToolSpecification(ConstructionTools.OPPOSITE_ROTATION)),

	*//**
	 * Colors connectors on the module with color coding.
	 *//*
	COLOR_CONNECTORS(new ColorConnectors()),
	
	*//**
	 * Adds new module on connector selected by user.
	 *//*
	ON_SELECTED_CONNECTOR(new ConstructionToolSpecification(ConstructionTools.ON_SELECTED_CONNECTOR)),
	
	*//**
	 * Adds new modules on all connectors of selected module.
	 *//*
	ON_ALL_CONNECTORS(new ConstructionToolSpecification(ConstructionTools.ON_ALL_CONNECTORS)),
	
	*//**
	 * This tool is used to implement robot specific properties. Like for instance for Odin switching  module type by selecting module in simulation environment.
	 *//*
	VARIATE_MODULE_PROPERTIES (new ConstructionToolSpecification(ConstructionTools.VARIATE_PROPERTIES)),
	
	*//**
	 * With each selection of module in simulation environment rotates it with available (standard) rotation.
	 *//*
	ROTATE_MODULE_STANDARD_IN_LOOP(new ConstructionToolSpecification(ConstructionTools.STANDARD_ROTATIONS_IN_LOOP)),
	
	*//**
	 * 
	 *//*
	READ_MODULE_LABELS (new LabelingToolSpecification(LabeledEntities.MODULE, LabelingTools.READ_LABELS)),
	
	*//**
	 * 
	 *//*
	READ_CONNECTOR_LABELS (new LabelingToolSpecification(LabeledEntities.CONNECTOR,LabelingTools.READ_LABELS)),
	
	*//**
	 * 
	 *//*
	READ_SENSOR_LABELS(new LabelingToolSpecification(LabeledEntities.SENSOR,LabelingTools.READ_LABELS)),

	CONSTRUCTION_TOOL_SPEC,

	;
	
	*//**
	 * The picker supported by builder.
	 *//*
	private Picker picker;
	
	*//**
	 * Contains pickers (left side of the mouse selectors) supported by builder.
	 * @param picker, the picker supported by builder.
	 *//*
	BuilderSupportingProxyPickers(Picker picker){
		this.picker = picker;
	}
	*//**
	 * Returns the picker supported by builder.
	 * @return, the picker supported by builder.
	 *//*
	public Picker getPicker(){
		return this.picker;
	}
}
	public Picker toPicker() {
		if(picker==PickerSelection.DEFAULT)
			return new PhysicsPicker();
		else if(picker==PickerSelection.MOVE_MODULE)
			return new PhysicsPicker(true,true);
		else if(picker==PickerSelection.CONSTRUCTION_TOOL_SPEC)
			return new ConstructionToolSpecification((ConstructionTools)arguments[0],(String)arguments[1]);
		else
			throw new Error("Conversion to picker object not supported for "+this);
	}*/
}
