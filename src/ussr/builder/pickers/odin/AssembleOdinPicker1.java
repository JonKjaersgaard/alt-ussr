package ussr.builder.pickers.odin;

import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;
//TODO   MAKE THE PICKER WHICH BY SELECTING THE CONNECTOR ON ODIN ABLE TO PLACE THE MODULE THERE
//CURRENTLY THERE IS THE PROBLEM BECAUSE THERE IS NO WAY TO GET THE NUMBER OF CONNECTOR FROM TRIMESH
/**
 * 
 * @author Konstantinas
 *
 */
public class AssembleOdinPicker1 extends AssembleOdinPicker {

	public AssembleOdinPicker1(JMESimulation simulation) {
		super(simulation);
	}

	/* Method executed when the Odin module is selected with the mouse in simulation environment
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		System.out.println("Found: "+component.getModel().getProperty("name"));    

	}

	/* (non-Javadoc)
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {	}
}
