package ussr.builder.genericTools;

import com.jme.scene.Geometry;

import ussr.builder.helpers.BuilderHelper;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * Removes (Deletes) the module selected with the left side of the mouse in simulation environment.
 * @author Konstantinas
 */
public class RemoveModule  extends CustomizedPicker{
  
	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look beneath).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	protected void pickTarget(Geometry target,JMESimulation jmeSimulation) {		
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Handles identification of selected module and removal of its components. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */	
	protected void pickModuleComponent(JMEModuleComponent component) {	
		Module selectedModule = component.getModel();		
		BuilderHelper.deleteModule(selectedModule,true);	//delete visual
	}	
}