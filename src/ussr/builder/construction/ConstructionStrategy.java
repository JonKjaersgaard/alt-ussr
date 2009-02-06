package ussr.builder.construction;

import ussr.model.Module;

/**
 * @author Konstantinas
 *
 */
public interface ConstructionStrategy {
	
	
	/**
	 * 
	 * @param connectorNr
	 * @param selectedModule
	 * @param newMovableModule
	 */
	public abstract void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule);
	
	/**
	 * @param selectedModule
	 */
	public abstract void rotateOpposite(Module selectedModule);
	
	/**
	 * @param selectedModule
	 * @param rotationName
	 */
	public abstract void rotateSpecifically(Module selectedModule, String rotationName);
}
