package ussr.builder.constructionTools;

import ussr.builder.helpers.ModuleRotationMapEntryHelper;
import ussr.model.Module;

/**
 * Currently supports construction of morphology of modular robots,like: ATRON, MTRAN and Odin, however
 * also supports ability to expand support to other modular robots. 
 * Here the support is based on construction of morphology on the level of module and its components,
 * with emphasis on components. Is introducing operations implemented according to TEMPLATE method pattern.    
 * @author Konstantinas 
 */
public interface ConstructionTemplate {	
	
	/**
	 * Moves newMovableModule according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param connectorNr, the connector number on selected module.
	 * @param selectedModule,  the module selected in simulation environment.
	 * @param newMovableModule, the new module to move respectively to selected one.
	 */	
	public void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule, boolean loopFlag);
		
	/**
	 * Updates the array of objects containing the information about all available initial rotations 
	 * of modular robot module and  rotations together with positions of newly added module with respect to specific 
	 * connector number on selected module and selected module itself.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)".	 
	 * @param x, the amount of x coordinate of current position of component.
	 * @param y, the amount of y coordinate of current position of component.
	 * @param z, the amount of z coordinate of current position of component. 
	 */
	//public abstract void updateModuleMap(float x, float y, float z);	
	
	/**
	 * Moves newMovableModuleComponent according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module component, and so on.
	 * This method is so-called "Primitive operation" for above TEMPLATE method,called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)". 
	 * @param connectorNr, the connector number on selected module.
	 * @param selectedModule,  the module selected in simulation environment.
	 * @param movableModuleComponent, the new module component to move respectively to selected one.
	 * @param rotationQuatComponent, the rotation of current component of selected module.	 
	 */	
	//public abstract void moveModuleComponentAccording(int connectorNr,Module selectedModule, JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent);
	
	/**
	 * Rotates selected  module according to its initial rotation with opposite rotation.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.	
	 * @param selectedModule,the module selected in simulation environment.	 
	 */
	public void rotateModuleOpposite(Module selectedModule);	
	
	/**
	 * Rotates selected  module component according to its initial rotation with opposite rotation.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "rotateModuleOpposite(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */
	//public abstract void rotateComponentOpposite(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent);
		
	/**
	 * Rotates selected  module with standard rotations, passed as a string.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param selectedModule,the module selected in simulation environment.
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */
	public void rotateModuleSpecifically(Module selectedModule,String rotationName);
	
	
	/**
	 * Rotates selected module component with standard rotations, passed as a string.
	 * This method is so-called "Primitive Operation" for above TEMPLATE method, called "rotateModuleSpecifically(Module selectedModule,String rotationName)". 
	 * @param selectedModuleComponent,the module component selected in simulation environment.	 
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */	
	//public abstract void rotateComponentSpecifically(JMEModuleComponent selectedModuleComponent, String rotationName);	
		
	/**
	 * Additional method for implementing unique properties of modular robots modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param selectedModule,the module selected in simulation environment.		
	 */
	public void variateModuleProperties(Module selectedModule);	
	
	/**
	 * Additional method for implementing unique properties of modular robots  components of the modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "variateModuleProperties(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */
	public abstract ModuleRotationMapEntryHelper[] getMODULE_ROTATION_MAP();
	//public abstract void variateComponentProperties(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent);
//FIME SHOULD I MOVE COMMON METHODS TO INTERFACE?
}
