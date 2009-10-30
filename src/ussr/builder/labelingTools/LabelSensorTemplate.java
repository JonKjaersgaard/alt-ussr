package ussr.builder.labelingTools;

import ussr.model.Entity;
import ussr.model.Sensor;

/**
 * Supports labeling of sensors on the modules. Here it is assumed that sensors are
 * mounted on the connectors. E.g connector =sensor. The precondition is that
 * the connector is selected with the mouse in simulation environment.
 * @author Konstantinas
 */
public class LabelSensorTemplate extends LabelEntityTemplate {
	
	/**
	 * Returns the connector number selected on the module in simulation environment.
	 * This method is so-called "Primitive operation" for above TEMPLATE methods. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 * @return sensor, the sensor on the connector number selected on the module in simulation environment. 
	 */
	public Entity getCurrentEntity(LabelingToolSpecification specification) {
		int connectorNr = specification.getSelectedConnectorNr();
		if (connectorNr == 1000 ){
			return null;
		}else		
		return (Sensor)specification.getSelectedModule().getSensors().get(connectorNr);
	}		

}
