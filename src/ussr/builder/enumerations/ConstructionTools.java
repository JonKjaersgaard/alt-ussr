package ussr.builder.enumerations;

/**
 *  Tools supported by builder and used for construction of supported modular robots morphology.  
 * @author Konstantinas
 */
public enum ConstructionTools {
	/*The tool where user selects the connector on the module and new module is added on it*/
	NEW_MODULE_ON_SELECTED_CONNECTOR,
	/*The tool where connector number should be passed as a parameter. As a result the module
	 * is added on this connector */
	ON_CHOSEN_CONNECTOR_NR,
	/*The tool where user selects the module and all possible modules are added to the connectors
	 * of selected module */
	NEW_MODULES_ON_ALL_CONNECTORS,
	/*The tool where user selects the module and new module is added at some predefined connector
	 * on the selected module, later user can move the module from one connector on selected module to another */
	MOVE_MODULE_FROM_CON_TO_CON,
	/*The tool where user selects the module and the module is rotated with one of the standard
	 * rotations, which is passed as parameter  */
	STANDARD_ROTATIONS,
	/*The tool where user selects the module and the module is rotated with opposite rotation */
	MODULE_OPPOSITE_ROTATION,
	/*The tool for varying unique properties of modular robots modules */
	VARIATE_MODULE_OR_PROPERTIES,
	/*The tool were the module is rotated with different rotation with each time it is selected*/
	AVAILABLE_ROTATIONS,	
	/*For adding first(initial) construction module*/
	ADD_DEFAULT_CONSTRUCTION_MODULE;
}
