package ussr.aGui.enumerations.tabs;

/**
 * Contains text elements presented to the user in Tabs. Such as text on GUI elements and tool tips text.
 * NOTE NR1: underscore is later replaced with space. 
 * NOTE NR2: if you want to change specific text, just refactor it and keep in mind that underscore is used instead of space. 
 * @author Konstantinas
 */
public enum TabsComponentsText {

	/*CONSTRUCT ROBOT TAB*/
	
	/*METHOD: setText()*/
	Start_new_robot,
	
	Add_and_adjust_first_module,
	
	Operations_on_existing,
	
	Add_new_modules,
	Change_module_type,
	
	/*METHOD: setToolTipText()*/
	General_control,
	
	Choose_supported_modular_robot,
	
	Start_constructing_new_robot,
	Adjust_module_properties,
	
	Rotate_opposite,
	Available_rotations,
	Standard_rotations,
	
	Generic_tools,
	Operations_for_changing_module_type, 
	
	Move_module,
	Delete_or_Remove,
	Color_module_connectors,
	Vary_module_type_or_properties,
	
	Addition_of_new_modules,
	On_selected_connector,
	On_chosen_connector_number,	
	On_all_connectors,
	Jump_from_connector_to_connector,
	
	/*ASSIGN BEHAVIOR(CONTROLLER) TAB*/
		
	/*METHOD: setToolTipText()*/
	Filter_out_for,
	
	
}
