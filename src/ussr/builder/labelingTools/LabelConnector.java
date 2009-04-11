package ussr.builder.labelingTools;

import ussr.model.Connector;
import ussr.model.Entity;

/**
 * Supports labeling of connectors on the modules. The precondition is that
 * the connector is selected with the mouse in simulation environment.
 * @author Konstantinas
 */
public class LabelConnector extends LabelEntity {
	
	/**
	 * Returns the connector number selected on the module in simulation environment.
	 * This method is so-called "Primitive operation" for above TEMPLATE methods. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 * @return connector, the connector number selected on the module in simulation environment. 
	 */
	public Entity getCurrentEntity(LabelingToolSpecification specification) {
		int connectorNr = specification.getSelectedConnectorNr();		
		Connector connector =specification.getSelectedModule().getConnectors().get(connectorNr); 
		return connector;
	}		

}
