package ussr.physics.jme.cameraHandlers;

import java.util.ArrayList;

import ussr.model.Module;

import com.jme.input.ChaseCamera;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;

public class RobotCameraHandler extends ChaseCamera {

	Spatial target;
	ArrayList<DynamicPhysicsNode> robotNodes;
	public RobotCameraHandler(Camera cam, ArrayList<DynamicPhysicsNode> robotNodes) {
		super(cam, null);
		target = new Node();
		super.setTarget(target);
		dampingK = 12f;;
	    springK = 36f;;
	    maxDistance =1.0f;
	    minDistance = 0.5f;;
	    enableSpring = false;
	    stayBehindTarget = false;
	    maintainAzimuth = false;
	    
	    this.robotNodes = robotNodes;
	    removeAction(mouseLook);
	    idealSphereCoords = new Vector3f((mouseLook.getMaxRollOut()-mouseLook.getMinRollOut()) / 2f, 1, mouseLook.getMaxAscent() * .5f);
	}
	public void update(float time) {
		target.setLocalTranslation(getRobotCM());
		super.update(time);
	}
	 public Vector3f getRobotCM() {
		 Vector3f cm = new Vector3f();
		 for(Spatial s : robotNodes) {
			 cm = cm.addLocal(s.getLocalTranslation());
		 }	
		 cm = cm.multLocal(1.0f/robotNodes.size());
		 return cm;
	 }
 }