package ussr.builder.genericTools;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Geometry;
import com.jmex.physics.DynamicPhysicsNode;
import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

public class RotateModulesComponents extends CustomizedPicker {

	/**
	 *  One of the Cartesian coordinates, like X, Y or Z.
	 */
	private String coordinate;	

	/**
	 *  The rotation angle in degrees
	 */
	private float angle;

	private String entityToMove;

	private Matrix3f rotationMatrix;

	/**
	 * Rotates the component around specific coordinate with specified angle.
	 * @param coordinate, one of the Cartesian coordinates, like X, Y or Z.
	 * @param angle, the rotation angle in degrees
	 */
	public RotateModulesComponents(String coordinate, float angle, String entityToMove){		
		this.coordinate = coordinate;
		this.angle = angle;
		this.entityToMove = entityToMove;
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Handles identification of selected component of the module and rotation of it. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {		
		if (entityToMove.equalsIgnoreCase("Component")){
			rotateComponent(component);	
		}else if (entityToMove.equalsIgnoreCase("Module")){			
			Module selectedModule = component.getModel();	
			VectorDescription modulePosition= selectedModule.getPhysics().get(1).getPosition();

			int amountComponents = selectedModule.getNumberOfComponents();
			for (int com =0; com<amountComponents; com++){
				JMEModuleComponent comp  = (JMEModuleComponent) selectedModule.getComponent(com);				 
				rotateComponent(comp);				
				
				Vector3f v = rotationMatrix.mult(comp.getLocalPosition().getVector());
				
			    for(DynamicPhysicsNode part: comp.getNodes()){												
					part.setLocalTranslation(v);					
				}
	//FIXME WHY DO YOU MOVE MODULE INTO 0,0,0 POSITION, BUT DO NOT KEEP IT IN THE SAME ONE?
			}
			//selectedModule.setPosition(modulePosition);			
		}
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look above).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	protected void pickTarget(Geometry target) {}

	/**
	 * @param component
	 */
	private void rotateComponent(JMEModuleComponent component){
		Quaternion componentRotation = component.getRotation().getRotation();		 
		Matrix3f componentRot = new  Matrix3f( );
		componentRot.set(componentRotation);					
		rotationMatrix = BuilderHelper.rotateAround(componentRot,coordinate,angle);			
		Quaternion rotationQaut = new Quaternion();
		rotationQaut.fromRotationMatrix(rotationMatrix);					
		component.setRotation(new RotationDescription(rotationQaut));
	}
}
