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
	 * Rotates selected  module according to its initial rotation with opposite rotation.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.	
	 * @param selectedModule,the module selected in simulation environment.	 
	 */
	public void rotateModuleOpposite(Module selectedModule);	
			
	/**
	 * Rotates selected  module with standard rotations, passed as a string.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param selectedModule,the module selected in simulation environment.
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */
	public void rotateModuleSpecifically(Module selectedModule,String rotationName);
	
	/**
	 * Additional method for implementing unique properties of modular robots modules. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param selectedModule,the module selected in simulation environment.		
	 */
	public void variateModuleProperties(Module selectedModule);	
	
	/**
	 * Returns array of objects containing information about supported-specific rotations of modular robot.
	 */
	public abstract ModuleRotationMapEntryHelper[] getMODULE_ROTATION_MAP();
	
	public int[] getConnectors();
}
