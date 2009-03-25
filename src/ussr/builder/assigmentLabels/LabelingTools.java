package ussr.builder.assigmentLabels;

/**
 * Tools supported by builder(Quick Prototyping of Simulation Scenarios) and 
 * used for labeling entities like for example modules and connectors in  modular robot's morphology.  
 * @author Konstantinas
 */
public enum LabelingTools {
/*The tool where user selects the module in simulation environment and it is labeled
 * with the label passed as parameter*/	
LABEL_MODULE,
/*The tool where user selects the connector on the module in simulation environment and it is labeled
 * with the label passed as parameter*/
LABEL_CONNECTOR,
/*The tool where user selects the module or connector on the module in simulation environment and 
 * the label passed as parameter is removed from the all labels assigned to it*/
DELETE_LABEL,
/*The tool where user selects the module or connector on the module in simulation environment and 
 * all labels assigned to it are displayed to the user*/
READ_LABELS
}
