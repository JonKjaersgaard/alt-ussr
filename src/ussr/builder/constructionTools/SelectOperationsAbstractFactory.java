package ussr.builder.constructionTools;

import ussr.builder.enumerations.SupportedModularRobots;
import ussr.physics.jme.JMESimulation;

/**
 * The main responsibility of this class is to act as abstract factory and create specific object of
 * selection(common) operations for particular modular robot. 
 * @author Konstantinas
 */
public class SelectOperationsAbstractFactory {

	/**
	 * The interface to construction of modular robot morphology. This one is on the level of modules of modular robot.  
	 */
	private SelectOperationsTemplate selectOperations;
	
	/**
	 * Assigns and returns specific object of selection(common) operations according to specific modular robot 
	 * name passed as a string.	 
	 * @param simulation, the physical simulation.
	 * @param modularRobotName, the name of the modular robot. 
	 * @return selectOperations,the object of selection (common) operations.  
	 * @throws error, if modular robot is not supported yet or the name of it is misspelled. 
	 */
	public SelectOperationsTemplate getSelectOperations(JMESimulation simulation, SupportedModularRobots modularRobotName){
		
		switch(modularRobotName){
		case ATRON:
			selectOperations = new ATRONOperationsTemplate(simulation); 
			break;			
		case MTRAN:
			selectOperations = new MTRANOperationsTemplate(simulation); 
			break;
		case ODIN:
			selectOperations = new OdinOperationsTemplate(simulation);
			break;
		case CKBOT_STANDARD:
			selectOperations = new CKBotOperationsTemplate(simulation);
			break;
			default: throw new Error("Modular robot named as" +modularRobotName.toString()+ "is not supported yet or the name of it is misspelled");
		}
		return selectOperations;		
	}
}
