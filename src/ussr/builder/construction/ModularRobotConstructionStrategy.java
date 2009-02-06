package ussr.builder.construction;

import ussr.description.geometry.RotationDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * The main responsibility of this class is to support children classes with common methods
 * for methods to construct the morphology of modular robots. Moreover act as abstract class
 * in Strategy pattern and define the algorithms common to children classes. 
 * @author Konstantinas
 * 
 */
public abstract class ModularRobotConstructionStrategy implements ConstructionStrategy {	
	
	/**
	 * Mathematical constant pi
	 */
	public final float pi = (float)Math.PI;

	public abstract void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule);
	
	public abstract void rotateOpposite(Module selectedModule);
	
	public abstract void rotateSpecifically(Module selectedModule, String rotationName);
	
	
	/**
	 * Moves current component of the module to new local position and assigns new rotation to the same component
	 * @param movableModuleComponent, the current component of the module to move 
	 * @param newRotation, the new local rotation to assign to the component
	 * @param newPosition,the new local position to translate the component to
	 */
	public void moveModuleComponent(JMEModuleComponent movableModuleComponent,RotationDescription  newRotation, Vector3f newPosition){
		for(DynamicPhysicsNode part: movableModuleComponent.getNodes()){ 
			part.setLocalRotation(newRotation.getRotation());											
			part.setLocalTranslation(newPosition);
		}
	}
	
	/**
	 * Rotates the component of a module with specific rotation quaternion
	 * @param moduleComponent, the component of the module
	 * @param quaternion, the rotation quaternion to rotate the component with
	 */
	public void rotateModuleComponent(JMEModuleComponent moduleComponent, Quaternion quaternion){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalRotation(quaternion);
		}
	}
	
	/**
	 * @param moduleComponent
	 * @param newPosition
	 */
	public void translateModuleComponent(JMEModuleComponent moduleComponent, Vector3f newPosition){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalTranslation(newPosition);
		}
		
	}
}
