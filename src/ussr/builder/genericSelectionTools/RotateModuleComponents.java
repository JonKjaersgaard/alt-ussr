package ussr.builder.genericSelectionTools;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.scene.Spatial;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.pickers.CustomizedPicker;

public class RotateModuleComponents extends CustomizedPicker {

	/**
	 *  One of the Cartesian coordinates, like X, Y or Z.
	 */
	private String coordinate;
	
	/**
	 *  The rotation angle in degrees
	 */
	private float angle;
	
	/**
	 * Rotates the component around specific coordinate with specified angle.
	 * @param coordinate, one of the Cartesian coordinates, like X, Y or Z.
	 * @param angle, the rotation angle in degrees
	 */
	public RotateModuleComponents(String coordinate, float angle){
		this.coordinate = coordinate;
		this.angle = angle;
	}
	
	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Handles identification of selected component of the module and rotation of it. 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		
		Quaternion componentRotation = component.getRotation().getRotation();		
		Matrix3f componentRot = new  Matrix3f( );
		componentRot.set(componentRotation);
		
		Matrix3f rotationMatrix = null;
		if (coordinate.equalsIgnoreCase("X")){
			rotationMatrix = BuilderHelper.rotateAround(componentRot,"X",angle);
		}else if (coordinate.equalsIgnoreCase("Y")){
			rotationMatrix = BuilderHelper.rotateAround(componentRot,"Y",angle);
		}else if (coordinate.equalsIgnoreCase("Z")){
			rotationMatrix = BuilderHelper.rotateAround(componentRot,"Z",angle);
		}		
		Quaternion rotationQaut = new Quaternion();
		rotationQaut.fromRotationMatrix(rotationMatrix);		

		component.setRotation(new RotationDescription(rotationQaut));		
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look above).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	protected void pickTarget(Spatial target) {}	
}
