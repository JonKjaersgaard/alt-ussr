package ussr.physics.jme.pickers;

import ussr.physics.jme.JMEModuleComponent;
import com.jme.math.Vector3f;
import com.jme.scene.batch.GeomBatch;
import com.jmex.physics.DynamicPhysicsNode;

public class DisplacementPicker extends CustomizedPicker {

    @Override
    protected void pickModuleComponent(JMEModuleComponent component) {
        System.out.println("Found: "+component.getModel().getProperty("name"));
        
        for(DynamicPhysicsNode part: component.getNodes())
            part.setLocalTranslation(part.getLocalTranslation().add(new Vector3f(0.2f,0.2f,0.2f)));
            
    }

	@Override
	protected void pickTarget(GeomBatch target) {
		// TODO Auto-generated method stub
		
	}	
}
