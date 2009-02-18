package ussr.builder.construction;

import ussr.model.Module;

/**
 * Supports construction of morphology of modular robots,like: ATRON, MTRAN and Odin.   
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public interface ConstructionStrategy {	
	
	/**
	 * Moves newMovableModule according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * @param connectorNr, the connector number on selected module
	 * @param selectedModule,the module selected in simulation environment
	 * @param newMovableModule, the new module to move respectively to selected one
	 */
	public  void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule);
	
	/**
	 * Rotates selected module according to its initial rotation with opposite rotation.
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void rotateOpposite(Module selectedModule);
	
	/**
	 * Rotates selected  module with standard rotations, passed as a string. 
	 * @param selectedModule, the module selected in simulation environment 
	 * @param rotationName, the name of standard rotation of the module
	 */
	public void rotateSpecifically(Module selectedModule, String rotationName);
	
//FIXME UNDER DEVELOPMENT
	public void variate(Module selectedModule);
	
}
