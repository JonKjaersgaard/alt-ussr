package ASESocketTest;

import com.jme.math.Vector3f;

import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;

public class CMTracker {

	PhysicsSimulation simulation;
	
	public CMTracker(PhysicsSimulation simulation) {
		this.simulation = simulation;
	}
	
	public Vector3f getRobotCM() {
    	Vector3f cm = new Vector3f();
    	int nModulesInRobot = 0;
        for(int i=0;i<simulation.getModules().size();i++) {
    		cm = cm.addLocal((Vector3f)simulation.getHelper().getModulePos(simulation.getModules().get(i)).get());
    		nModulesInRobot++;
        }
        cm = cm.multLocal(1.0f/nModulesInRobot);
        return cm;
    }
	
}
