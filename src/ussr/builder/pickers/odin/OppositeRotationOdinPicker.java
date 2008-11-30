package ussr.builder.pickers.odin;

import com.jme.math.Quaternion;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.description.geometry.RotationDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.samples.atron.ATRON;
//TODO CONSIDER BALL ROTATION AND MOVING ROTATIONS INTO UPPER CLASS
/**
 * Rotates the picked Odin module with opposite rotation
 * @author Konstantinas
 *
 */
public class OppositeRotationOdinPicker extends CustomizedPicker {

	/**
	 * The physical simulation
	 */	
	private JMESimulation simulation;
	/**
	 * Mathematical constant pi
	 */
	public final float pi = (float)Math.PI;

	/**
	 * A number of different rotations of Structure link or Telescoping link with respect to connector of CCP joint .
	 * Here:rotation0 is rotation of Structure link with respect to connector number 0 and 11 of CCP joint
	 *      rotation1 - connector number 1 and 10
	 *      rotation2 - connector number 2 and 9 
	 *      rotation3 - connector number 3 and 8  
	 *      rotation4 - connector number 4 and 7
	 *      rotation5 - connector number 5 and 6     
	 */
	public final RotationDescription rotation0= new RotationDescription(0,0,pi/4);
	public final RotationDescription rotation0opposite= new RotationDescription(0,0,5*pi/4); //opposite means rotate around the same coordinate with pi	
	public final RotationDescription rotation1 = new RotationDescription(0,-pi/4,0);
	public final RotationDescription rotation1opposite= new RotationDescription(0,-5*pi/4,0);
	public final RotationDescription rotation2= new RotationDescription(0,pi/4,0);
	public final RotationDescription rotation2opposite= new RotationDescription(0,5*pi/4,0);
	public final RotationDescription rotation3= new RotationDescription(0,0,-pi/4);
	public final RotationDescription rotation3opposite= new RotationDescription(0,0,-5*pi/4);
	public final RotationDescription rotation4 = new RotationDescription(0,pi/4,-pi/2);
	public final RotationDescription rotation4opposite = new RotationDescription(0,5*pi/4,-5*pi/2);
	public final RotationDescription rotation5= new RotationDescription(0,-pi/4,-pi/2);
	public final RotationDescription rotation5opposite= new RotationDescription(0,-5*pi/4,-5*pi/2);


	/**
	 * Constructor called in QPSS main window  
	 * @param simulation, the physical simulation
	 */
	public OppositeRotationOdinPicker(JMESimulation simulation) {
		this.simulation = simulation;
	}

	/* Not used
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {}

	/* Handles identification of picked Odin module and rotates it with opposite rotation 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {		
		int moduleID = component.getModel().getID();
		int nrComponents =simulation.getModules().get(moduleID).getColorList().size();//Hack amount of colors = amount of components

		for (int c=0; c<nrComponents;c++){
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(c);			
			Quaternion  rotationQuaternion = simulation.getModules().get(moduleID).getComponent(c).getRotation().getRotation();

			if (rotationQuaternion.equals(rotation0.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation0opposite.getRotation());				
			} else if (rotationQuaternion.equals(rotation0opposite.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation0.getRotation());				
			}else if (rotationQuaternion.equals(rotation1.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation1opposite.getRotation());	
			}else if (rotationQuaternion.equals(rotation1opposite.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation1.getRotation());	
			}else if (rotationQuaternion.equals(rotation2.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation2opposite.getRotation());	
			}else if (rotationQuaternion.equals(rotation2opposite.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation2.getRotation());	
			}else if (rotationQuaternion.equals(rotation3.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation3opposite.getRotation());	
			}else if (rotationQuaternion.equals(rotation3opposite.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation3.getRotation());	
			}else if (rotationQuaternion.equals(rotation4.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation4opposite.getRotation());	
			}else if (rotationQuaternion.equals(rotation4opposite.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation4.getRotation());	
			}else if (rotationQuaternion.equals(rotation5.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation5opposite.getRotation());	
			}else if (rotationQuaternion.equals(rotation5opposite.getRotation())){
				rotateATRONModuleComponent(moduleComponent,rotation5.getRotation());	
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

