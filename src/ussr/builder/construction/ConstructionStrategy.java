package ussr.builder.construction;

import ussr.model.Module;

/**
 * Supports construction of morphology of modular robots,like: ATRON, MTRAN and Odin.
 * Here the support is based on construction on the level of module components.   
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
public interface ConstructionStrategy {	
	
	/**
	 * Moves newMovableModule according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * @param connectorNr, the connector number on selected module.
	 * @param selectedModule,the module selected in simulation environment.
	 * @param newMovableModule, the new module to move respectively to selected one.
	 */
	public abstract void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule);
	
	/**
	 * Updates and returns the array of objects containing the information about all available initial rotations 
	 * of modular robot module and  rotations together with positions of newly added module with respect to specific 
	 * connector number of selected module.	 
	 * @param x, the amount of x coordinate of current position of component.
	 * @param y, the amount of y coordinate of current position of component.
	 * @param z, the amount of z coordinate of current position of component.
	 * @return moduleMap, updated array of objects.
	 */
	public abstract ModuleMapEntry[] updateModuleMap(float x, float y, float z);	
	
	/**
	 * Rotates selected module according to its initial rotation with opposite rotation.
	 * @param selectedModule, the module selected in simulation environment.
	 */
	public abstract void rotateOpposite(Module selectedModule);
	
	/**
	 * Rotates selected  module with standard rotations, passed as a string. 
	 * @param selectedModule, the module selected in simulation environment.
	 * @param rotationName, the name of standard rotation of the module.
	 */
	public abstract void rotateSpecifically(Module selectedModule, String rotationName);	
	
	/**
	 * Additional method for implementing unique properties of modular robots modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * @param selectedModule, the module selected in simulation environment.
	 */
	public abstract void variate(Module selectedModule);
	
}
