package ussr.builder.pickers.atron;

import com.jme.math.Quaternion;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.samples.atron.ATRON;

//TODO CONSIDER MAKING GENERIC SOLUTION MERGING EACH  THE SAME TIME OF CLASS FOR EACH ROBOT. SOME PROBLEMS EXIST WITH GENERIC SOLUTION
/**
 * Rotates the picked ATRON module with opposite rotation 
 * @author Konstantinas
 *
 */
public class OppositeRotationATRONPicker extends CustomizedPicker {

	/**
	 * The physical simulation
	 */	
	private JMESimulation simulation;

	/**
	 * Constructor called in QPSS main window  
	 * @param simulation, the physical simulation
	 */
	public OppositeRotationATRONPicker(JMESimulation simulation) {
		this.simulation = simulation;
	}

	/* Not used
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {}

	/* Handles identification of picked ATRON module and rotates it with opposite rotation 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {		
		int moduleID = component.getModel().getID();
		int nrComponents =simulation.getModules().get(moduleID).getColorList().size();//Hack amount of colors = amount of components

		for (int c=0; c<nrComponents;c++){
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(c);			
			Quaternion  rotation = simulation.getModules().get(moduleID).getComponent(c).getRotation().getRotation();

			if (rotation.equals(ATRON.ROTATION_EW.getRotation())){
				rotateATRONModuleComponent(moduleComponent,ATRON.ROTATION_WE.getRotation());				
			} else if (rotation.equals(ATRON.ROTATION_WE.getRotation())){
				rotateATRONModuleComponent(moduleComponent,ATRON.ROTATION_EW.getRotation());				
			}else if (rotation.equals(ATRON.ROTATION_DU.getRotation())){
				rotateATRONModuleComponent(moduleComponent,ATRON.ROTATION_UD.getRotation());	
			}else if (rotation.equals(ATRON.ROTATION_UD.getRotation())){
				rotateATRONModuleComponent(moduleComponent,ATRON.ROTATION_DU.getRotation());	
			}else if (rotation.equals(ATRON.ROTATION_SN.getRotation())){
				rotateATRONModuleComponent(moduleComponent,ATRON.ROTATION_NS.getRotation());	
			}else if (rotation.equals(ATRON.ROTATION_NS.getRotation())){
				rotateATRONModuleComponent(moduleComponent,ATRON.ROTATION_SN.getRotation());	
			}
		}		
	}

	/**
	 * Rotates the component of a module with specific rotation quaternion
	 * @param moduleComponent, the component of a module
	 * @param quaternion, the rotation quaternion to rotate the component
	 */
	private void rotateATRONModuleComponent(JMEModuleComponent moduleComponent, Quaternion quaternion){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalRotation(quaternion);
		}
	}
}

