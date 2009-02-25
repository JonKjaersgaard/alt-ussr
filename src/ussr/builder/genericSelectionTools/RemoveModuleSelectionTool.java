package ussr.builder.genericSelectionTools;

import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.builder.BuilderUtilities;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * Removes (Deletes) the module selected with the left side of the mouse in simulation environment
 * @author Konstantinas
 */
//FIXME 1) UPDATE COMMENTS 
public class RemoveModuleSelectionTool  extends CustomizedPicker{

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;

	/**
	 * Removes the module selected in simulation environment
	 * @param simulation,the physical simulation
	 */
	public RemoveModuleSelectionTool(JMESimulation simulation) {
		this.simulation = simulation;		
	}
	
	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look beneath).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	protected void pickTarget(Spatial target) {	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Handles identification of selected module and removal of its components. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */	
	protected void pickModuleComponent(JMEModuleComponent component) {		
		Module selectedModule = component.getModel();
		deleteModule(selectedModule);		
	}
	
	/**
	 * Removes (deletes) the module from simulation environment
	 * @param selectedModule, module to remove (delete)
	 */
	public void deleteModule(Module selectedModule){
		/* Flag to indicate that the information about module should not be saved in XML*/
		selectedModule.setProperty(BuilderUtilities.getModuleDeletionKey(), BuilderUtilities.getModuleDeletionValue());	
		
		int nrComponents= selectedModule.getNumberOfComponents();		
		for (int compon=0; compon<nrComponents;compon++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)selectedModule.getComponent(compon);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
				int amountNodes = moduleComponent.getNodes().size();
				for (int node=0; node<amountNodes; node++ ){ //removes bounds and visuals
					moduleComponent.getNodes().get(node).removeFromParent();
				}				
				part.lock();//Freezes everything			
				part.setIsCollidable(false);
				part.setActive(false);							
				part.detachAllChildren();//removes visual						
			}        
		}		
	}
}