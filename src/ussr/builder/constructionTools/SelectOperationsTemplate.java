package ussr.builder.constructionTools;

import ussr.description.geometry.VectorDescription;


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
	 * Adds default (the first) construction module at specified position.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param type, the type of modular robot. For example: ATRON,MTRAN or OdinBall.
	 * @param modulePosition, the position of module in simulation environment.
	 */
	public abstract void addDefaultConstructionModule(String type, VectorDescription modulePosition);	
	
	/**
	 * Adds default (the first) construction module at specified position.
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "addDefaultConstructionModule(String type, VectorDescription modulePosition)".	 
	 * @param type, the type of modular robot. For example: ATRON,MTRAN or OdinBall.
	 * @param modulePosition, the position of the module in simulation environment.
	 * @param moduleRotation, the rotation of the module.
	 * @param colorsComponents, the colours of components constituting the module.
	 * @param colorsConectors, the colours of connectors on the module.
	 */
	//public abstract void addDefaultModule(String type,VectorDescription modulePosition,RotationDescription moduleRotation, List<Color> colorsComponents,ArrayList<Color> colorsConectors);
		
	/**
	 * Adds the new module on connector. This is for both: selected connector on the module in simulation environment
	 * and connector passed as a variable and later selected module in simulation environment.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param toolSpecification,object containing information about modular robot, selected or chosen connector
	 * number,selected module, simulation and so on.  
	 */
	public abstract void addNewModuleOnConnector(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Creates and returns new module, depending on which is needed: the copy module or different one. 	 
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "addNewModuleOnConnector(ConstructionToolSpecification toolSpecification)".	 
	 * @param selectedModule,the module selected in simulation environment. 
	 */
	//public abstract Module createNewModule(Module selectedModule);	
	
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
	 * Additional method for implementing unique properties of modular robots. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.In Odin case this is replacement of modules by just selecting them in simulation
	 * environment. 
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.  
	 * @param toolSpecification,object containing information about modular robot, selected module, simulation
	 * and so on.
	 */
	public abstract void variateModule(ConstructionToolSpecification toolSpecification);
	
	/**
	 * Additional method for implementing unique properties of modular robots. Like for example
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.In Odin case this is replacement of modules by just selecting them in simulation
	 * environment. 
	 * This method is so-called "Primitive operation" for above TEMPLATE method, called "variateModule(ConstructionToolSpecification toolSpecification)".	    
	 * @param selectedModule,the module selected in simulation environment.	
	 */
	//public abstract void variateSpecificModule(Module selectedModule);	
	
	/**
	 * TODO
	 * @param toolSpecification
	 */
//TODO
	public abstract void moveModuleOnNextConnector(ConstructionToolSpecification toolSpecification);
		
}
