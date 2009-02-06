package ussr.builder.construction;

import com.jme.math.Vector3f;
import ussr.description.geometry.RotationDescription;

/**
 * @author Konstantinas
 *
 */
public class ModuleMapEntry {

	private int connectorNr;
	private int componentNr;
	private RotationDescription initialRotation;
	private RotationDescription newRotation;
	private Vector3f newPosition;	
	
	public ModuleMapEntry(int connectorNr, RotationDescription initialRotation, RotationDescription newRotation, Vector3f newPosition ){
		this.connectorNr = connectorNr;
		this.initialRotation = initialRotation;
		this.newRotation = newRotation;
		this.newPosition = newPosition;		
	}
	
	public ModuleMapEntry(int connectorNr,int componentNr,  RotationDescription initialRotation, RotationDescription newRotation, Vector3f newPosition ){
		this.connectorNr = connectorNr;
		this.componentNr = componentNr;
		this.initialRotation = initialRotation;
		this.newRotation = newRotation;
		this.newPosition = newPosition;		
	}

	public int getConnectorNr() {
		return connectorNr;
	}
	
	public RotationDescription getInitialRotation() {
		return initialRotation;
	}

	public Vector3f getNewPosition() {
		return newPosition;
	}

	public RotationDescription getNewRotation() {
		return newRotation;
	}

	public int getComponentNr() {
		return componentNr;
	} 
	
	
}
