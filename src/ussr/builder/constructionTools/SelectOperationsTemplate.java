package ussr.builder.constructionTools;




/**
 * Supports construction of modular robots morphologies on the level of modules, rather than
 * on the level of components of the modules.Is introducing operations implemented according to TEMPLATE method pattern.
 * Currently supports ATRON,MTRAN and Odin modular robots.
 * @author Konstantinas
 */
public interface SelectOperationsTemplate {
	
	/**
	 * Returns the lower level construction object for modular robot morphology.
	 * The construction is on the level of components.
	 */
	public abstract ConstructionTemplate getConstruction();
	
	/**
	 * Adds the new module on connector. This is for both: selected connector on the module in simulation environment
	 * and connector passed as a variable and later selected module in simulation environment.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param toolSpecification,object containing information about modular robot, selected or chosen connector
	 * number,selected module, simulation and so on.  
	 */
	public abstract void addNewModuleOnConnector(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Adds the new modules on all connectors of the module selected in simulation environment
	 * This method is common to children classes.
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void addModulesOnAllConnectors(ConstructionToolSpecification toolSpecification);
		
	/**
	 * Rotates the module selected in simulation environment with opposite rotation.
	 * This method is common to children classes.
	 * @param toolSpecification, object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void rotateModuleWithOppositeRotation(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Rotates the module selected in simulation environment with standard rotation passed as a String
	 * This method is common to children classes.
	 * @param toolSpecification, object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void rotateModuleStandardRotation(ConstructionToolSpecification toolSpecification, String standardRotationName);
	
	/**
	 * Rotates the module with different rotation for each selection of the mouse.
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void rotateModuleStandardRotationInLoop(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Additional method for implementing unique properties of modular robots. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.In Odin case this is replacement of modules by just selecting them in simulation
	 * environment. 
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.  
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void variateModule(ConstructionToolSpecification toolSpecification);	

}
