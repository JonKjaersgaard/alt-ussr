package ussr.builder.labelingTools;

import ussr.model.Entity;
import ussr.model.Module;


/**
 * Supports labeling of modules. The precondition is that
 * the module is selected with the mouse in simulation environment.
 * @author Konstantinas
 */
public class LabelModuleTemplate extends LabelEntityTemplate {
	
	/**
	 * Returns the method selected in simulation environment.
	 * This method is so-called "Primitive operation" for above TEMPLATE methods. 
	 * @param specification, object containing information about entity to label, the name of the label and so on.
	 * @return selectedModule, the module selected in simulation environment. 
	 */
	public Entity getCurrentEntity(LabelingToolSpecification specification) {
		Module selectedModule = specification.getSelectedModule();		
		return selectedModule;
	}
		
}
