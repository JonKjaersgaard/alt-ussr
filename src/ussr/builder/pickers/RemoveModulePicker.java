package ussr.builder.pickers;

import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * Removes (Deletes) the module selected with the left side of the mouse from simulation environment
 * @author Konstantinas
 *
 */
public class RemoveModulePicker  extends CustomizedPicker{

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation,the physical simulation
	 */
	public RemoveModulePicker(JMESimulation simulation) {
		this.simulation = simulation;		
	}	

	/* Not used
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */	
	@Override
	protected void pickTarget(Spatial target) {	}

	/* Method executed when the module is selected with the mouse in simulation environment. Handles identification of selected module and removal of its components 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {

		int selectedModuleID = component.getModel().getID();
		Module selectedModule = simulation.getModules().get(selectedModuleID);
		selectedModule.setProperty("ussr.module.deletionFlag", "deleted");//Flag to indicate that the information about module should not be saved in XML (Hack)		
		int nrComponents= selectedModule.getNumberOfComponents();
		
		for (int compon=0; compon<nrComponents;compon++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(selectedModuleID).getComponent(compon);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
				int amountNodes = moduleComponent.getNodes().size();
				for (int node=0; node<amountNodes; node++ ){ //removes bounds and visuals
					moduleComponent.getNodes().get(node).removeFromParent();
				}				
				part.lock();//Freezes everything			
				part.setIsCollidable(false);
				part.setActive(false);
				//part.setCullMode(1);				
				part.detachAllChildren();//removes visual
				// LOOK INTO CREATE METHOD AND REMOVE THE LIST,lIST				
			}        
		}	
	}

}