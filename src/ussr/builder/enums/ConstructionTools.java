package ussr.builder.enums;

/**
 *  Tools supported by builder(Quick Prototyping of Simulation Scenarios) and 
 *  used for construction of supported modular robots morphology.  
 * @author Konstantinas
 */
public enum ConstructionTools {
	/*The tool where user selects the connector on the module and new module is added on it*/
	ON_SELECTED_CONNECTOR,
	/*The tool where connector number should be passed as a parameter. As a result the module
	 * is added on this connector */
	ON_CHOSEN_CONNECTOR_NR,
	/*The tool where user selects the module and all possible modules are added to the connectors
	 * of selected module */
	ON_ALL_CONNECTORS,
	/*The tool where user selects the module and new module is added at some predefined connector
	 * on the selected module, later user can move the module from one connector on selected module to another */
	LOOP,
	/*The tool where user selects the module and the module is rotated with one of the standard
	 * rotations, which is passed as parameter  */
	STANDARD_ROTATIONS,
	/*The tool where user selects the module and the module is rotated with opposite rotation */
	OPPOSITE_ROTATION,
	/*The tool for varying unique properties of modular robots modules */
	VARIATE_PROPERTIES,
	
	STANDARD_ROTATIONS_IN_LOOP,
	ADD_DEFAULT_CONSTRUCTION_MODULE;
}
