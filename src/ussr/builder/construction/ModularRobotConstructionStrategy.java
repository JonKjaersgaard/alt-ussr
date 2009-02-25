package ussr.builder.construction;

import ussr.description.geometry.RotationDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * The main responsibility of this class  is to support children classes with common methods
 * for construction of the morphology of modular robots. Moreover act as abstract parent class
 * in Strategy pattern and define the abstract methods (algorithms) common to children classes. 
 * However implementation  of algorithms are differing for each modular robot in children classes and
 * a a result enforce implementation of these methods to newly supported modular robots.  
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
//      2) FIX EXISTING IMPROVEMENTS 
public abstract class ModularRobotConstructionStrategy implements ConstructionStrategy {	
	
	/**
	 * Mathematical constant pi
	 */
	public final static float pi = (float)Math.PI;

	/**
	 * Moves newMovableModule according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * @param connectorNr, the connector number on selected module.
	 * @param selectedModule,  the module selected in simulation environment.
	 * @param newMovableModule, the new module to move respectively to selected one.
	 */	
	public abstract void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule);
		
	/**
	 * Updates and returns the array of objects containing the information about all available initial rotations 
	 * of modular robot module and  rotations together with positions of newly added module with respect to specific 
	 * connector number and selected module.	 
	 * @param x, the amount of x coordinate of current position of component.
	 * @param y, the amount of y coordinate of current position of component.
	 * @param z, the amount of z coordinate of current position of component.
	 * @return moduleMap, updated array of objects.
	 */
	public abstract ModuleMapEntry[] updateModuleMap(float x, float y, float z);
		
	/**
	 * Rotates selected  module according to its initial rotation with opposite rotation.	
	 * @param selectedModule,the module selected in simulation environment.	
	 */	
	public abstract void rotateOpposite(Module selectedModule);
	
	/**
	 * Rotates selected  module with standard rotations, passed as a string.
	 * @param selectedModule,the module selected in simulation environment.	 
	 * @param rotationName,the name of standard rotation of the module.
	 */
	public abstract void rotateSpecifically(Module selectedModule, String rotationName);
	
	/**
	 * Additional method for implementing unique properties of modular robots modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * @param selectedModule,the module selected in simulation environment.	
	 */	
	public abstract void variate(Module selectedModule);
	
	/**
	 * Moves movable component of the module to new local position and assigns new rotation to the same component.
	 * @param movableModuleComponent, the current component of the module to move. 
	 * @param newRotation, the new local rotation to assign to the component.
	 * @param newPosition,the new local position to translate the component to.
	 */
	public void moveModuleComponent(JMEModuleComponent movableModuleComponent,RotationDescription  newRotation, Vector3f newPosition){
		for(DynamicPhysicsNode part: movableModuleComponent.getNodes()){ 
			part.setLocalRotation(newRotation.getRotation());											
			part.setLocalTranslation(newPosition);
		}
	}
	
	/**
	 * Rotates the component of a module with specific rotation quaternion.
	 * @param moduleComponent, the component of the module.
	 * @param quaternion, the rotation quaternion to rotate the component with.
	 */
	public void rotateModuleComponent(JMEModuleComponent moduleComponent, Quaternion quaternion){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalRotation(quaternion);
		}
	}
	
	/**
	 * Moves the component of module to new local position.
	 * @param moduleComponent, the component of the module.
	 * @param newPosition, the new local position to assign the component to.
	 */
//FIXME CURRENTLY USED ONLY IN MTRAN CASE (CONSIDER MAKING IT PRIVATE IN MTRANCONSTRUCTION)
	public void translateModuleComponent(JMEModuleComponent moduleComponent, Vector3f newPosition){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalTranslation(newPosition);
		}		
	}
}
