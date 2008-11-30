package ussr.physics.jme.pickers;

import ussr.physics.jme.JMEModuleComponent;

import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;

public class DisplacementPicker extends CustomizedPicker {

    @Override
    protected void pickModuleComponent(JMEModuleComponent component) {
        System.out.println("Found: "+component.getModel().getProperty("name"));
        
        for(DynamicPhysicsNode part: component.getNodes())
            part.setLocalTranslation(part.getLocalTranslation().add(new Vector3f(0.2f,0.2f,0.2f)));
            
    }

	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub
		
	}

}
