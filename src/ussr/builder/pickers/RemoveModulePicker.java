package ussr.builder.pickers;

import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * Removes (Deletes) the module selected(picked) with the mouse from simulation environment
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

		int moduleID = component.getModel().getID();		
		int nrComponents= simulation.getModules().get(moduleID).getNumberOfComponents();

		for (int com=0; com<nrComponents;com++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(com);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
				part.detachAllChildren();	            
			}        
		}	
	}

}