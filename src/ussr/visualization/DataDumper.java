package ussr.visualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.AxisRods;

import ussr.model.Module;
import ussr.physics.PhysicsModuleComponent;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRONKinematicModel;
import ussr.samples.atron.natives.ATRONNativeTinyOSController;
import ussr.samples.odin.natives.OdinNativeTinyOSController;

public class DataDumper implements PhysicsObserver {

	public void init(int numberOfModules) {
		for(int i=0; i<numberOfModules; i++) {
			;
		}
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		
		
		/* stub to grab data from the sim, mostly for debugging purposes */
	
		List<Module> moduleList = ((JMESimulation)simulation).getModules();
		for(Module mod: moduleList) {

			// cruse solution, needs to superclass the tinyos controllers ...
			if ((mod.getController()) instanceof ATRONNativeTinyOSController)
				( (ATRONNativeTinyOSController)(mod.getController() )).native_stub(0);
			else if ((mod.getController()) instanceof OdinNativeTinyOSController)
				( (OdinNativeTinyOSController)(mod.getController() )).native_stub(0);

		}
	 
	}
	

}
