package ussr.builder.construction;

import ussr.description.geometry.VectorDescription;

/**
 * Supports construction of modular robots morphologies on the level of modules, rather than
 * on the level of components of the modules. Also an interface to Context class in Strategy pattern, defining
 * common operations(methods) during construction of modular robot morphology. Currently supports ATRON,
 * MTRAN and Odin modular robots.
 * @author Konstantinas
 */
//TODO 1) UPDATE COMMENTS
//     2) REFACTOR moveModuleOnNextConnector() METHOD
public interface SelectOperationsStrategy {
	
	/**
	 * Adds the new module on connector. This is for both: selected connector on the module in simulation environment
	 * and connector passed as a variable and later selected module in simulation environment.
	 * @param toolSpecification, object containing information about modular robot, selected or chosen connector
	 * number,selected module, simulation and so on. 
	 */
	public abstract void addNewModuleOnConnector(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Adds the new modules on all connectors of the module selected in simulation environment.
	 * @param toolSpecification, object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void addModulesOnAllConnectors(ConstructionToolSpecification toolSpecification);
	
	/**
	 * TODO
	 * @param toolSpecification
	 */
	public abstract void moveModuleOnNextConnector(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Rotates the module selected in simulation environment with opposite rotation.
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void rotateModuleWithOppositeRotation(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Rotates the module selected in simulation environment with specific standard rotation.
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 * @param standardRotationName, the name of the rotation
	 */
	public abstract void rotateModuleStandardRotation(ConstructionToolSpecification toolSpecification, String standardRotationName);
	
	/**
	 * Additional method for implementing unique properties of modular robots. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.In Odin case this is replacement of modules by just selecting them in simulation
	 * environment.    
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void variateModule(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Adds default (the first) construction module at specified position.
	 * @param type, the type of modular robot. For example: ATRON,MTRAN or OdinBall.
	 * @param modulePosition, the position of module in simulation environment.
	 */
	public abstract void addDefaultConstructionModule(String type, VectorDescription modulePosition);
	
}
