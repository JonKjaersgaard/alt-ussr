package ussr.builder;

import java.util.Random;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import com.jme.math.Matrix3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * The main responsibility of this class is to house common methods and constants used frequently
 * by other classes.
 * @author Konstantinas
 *
 */
public class BuilderHelper {

	/**
	 * The builder specific prefix, which is unique for builder.
	 */
	public static final String builderPrefix = "ussr.builder.";
	
	/**
	 * The module stem used in both builder and all over ussr.
	 */
	public static final String moduleStem = "module.";
	
	/**
	 * The module deletion suffix, used to indicate the state of module deletion. 
	 */
	public static final String moduleDeletionSuffix = "deletionKey";	
	
	/**
	 * The module deletion key, which indicates that the module was deleted or not in static or dynamic states of simulation.
	 * The format is:"ussr.builder.module.deletionKey". 
	 */
	public static final String moduleDeletionKey = builderPrefix +moduleStem + moduleDeletionSuffix;
	
	/**
	 * The module deletion value, which indicates that the module was deleted in static or dynamic states of simulation.
	 */
	public static final String moduleDeletionValue = "deleted";
	
//MAYBE CAN BE RECEIVED FROM USSR INTERNALLY
	public static final String ussrPrefix = "ussr.";
	
	public static final String moduleTypeSuffix ="type";
	
	// The property called "ussr.module.type" was implemented by Ulrik.
	public static final String moduleTypeKey = ussrPrefix + moduleStem+ moduleTypeSuffix; 
	
	public static final String connectorStem = "connector_";
	
	public static final String connectorSuffix = "number";

	// The property called "ussr.connector_number" was implemented by Ulrik.	
	public static final String moduleConnectorNrKey = ussrPrefix +connectorStem + connectorSuffix;
	
	public static final String moduleNameKey = "name";
	
	private static final Random generator = new Random();

	/**
	 * Returns module deletion key, which is of format: "ussr.builder.module.deletionKey".
	 * @return module deletion key, which is of format: "ussr.builder.module.deletionKey". 
	 */
	public static String getModuleDeletionKey() {
		return moduleDeletionKey;
	}

	/**
	 * Returns module deletion value, which is of format:"deleted".
	 * @return module deletion value, which is of format:"deleted".
	 */
	public static String getModuleDeletionValue() {
		return moduleDeletionValue;
	}

	public static String getModuleTypeKey() {
		return moduleTypeKey;
	}

	public static String getModuleConnectorNrKey() {
		return moduleConnectorNrKey;
	}

	public static String getModuleNameKey() {
		return moduleNameKey;
	}
	
	public static int getRandomInt(){
		return generator.nextInt();
		
	}
	
	
	/**
	 * Removes (deletes) the module from simulation environment
	 * @param selectedModule, module to remove (delete)
	 */
	public void deleteModule(Module selectedModule){
		/* Flag to indicate that the information about module should not be saved in XML*/
		selectedModule.setProperty(BuilderHelper.getModuleDeletionKey(), BuilderHelper.getModuleDeletionValue());	
		
		int nrComponents= selectedModule.getNumberOfComponents();		
		for (int compon=0; compon<nrComponents;compon++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)selectedModule.getComponent(compon);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
				int amountNodes = moduleComponent.getNodes().size();
				for (int node=0; node<amountNodes; node++ ){ //removes bounds and visuals
					moduleComponent.getNodes().get(node).removeFromParent();
				}						
				part.setIsCollidable(false);
				part.setActive(false);							
				part.detachAllChildren();//removes visual						
			}        
		}		
	}	
}
