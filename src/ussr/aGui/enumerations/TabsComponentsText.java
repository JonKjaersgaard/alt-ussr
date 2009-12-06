package ussr.aGui.enumerations;

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
	
	Operations_on_existing_modules,
	
	Add_new_modules,
	
	/*METHOD: setToolTipText()*/
	Start_constructing_new_robot,
	Module_rotation_tools,
	
	Rotate_opposite,
	Available_rotations,
	Standard_rotations,
	
	Generic_tools,
	Move,
	Delete_or_Remove,
	Color_Connectors,
	Vary_module_type_or_properties,
	
	Construction_tools,
	On_selected_connector,
	On_chosen_connector_number,	
	On_all_connectors,
	Jump_from_connector_to_connector,
	
	
	
}
