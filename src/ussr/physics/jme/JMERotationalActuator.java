/**
 * 
 */
package ussr.physics.jme;

import ussr.model.Actuator;
import ussr.model.PhysicsActuator;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;


/**
 * @author david
 *
 */
public class JMERotationalActuator implements PhysicsActuator {

	private Actuator model;
    private DynamicPhysicsNode node1;
    private DynamicPhysicsNode node2;
    private JMESimulation world;
    private JMEModuleComponent module;
    private Joint joint;
    private String name;
    
    public JMERotationalActuator(JMESimulation world, String baseName) {
        this.world = world;
        this.name = baseName;
    }
    public void setControlParameters(float maxTorque, float maxVelocity, float lowerLimit, float upperLimit) {
    	
    }
    public void attach(DynamicPhysicsNode d1, DynamicPhysicsNode d2) {
    	node1=d1; node2=d2;
    	//node2.setMass(1000);
    	node1.setAffectedByGravity(false);
    	node2.setAffectedByGravity(false);
    	if(joint==null)  {
    		joint = world.getPhysicsSpace().createJoint();
    		/*axis.setAvailableAcceleration(0);
    		axis.setDesiredVelocity(0);
    		axis.setPositionMaximum(3.14f);
    		axis.setPositionMinimum(-3.14f);
    		axis.setRelativeToSecondObject(false);*/
    		joint.attach(node1,node2);
    		//final RotationalJointAxis axis = joint.createRotationalAxis();
    		RotationalJointAxis axisX = joint.createRotationalAxis();
    		RotationalJointAxis axisY = joint.createRotationalAxis();
    		RotationalJointAxis axisZ = joint.createRotationalAxis();
    		axisX.setDirection(new Vector3f(1,0,0));
    		axisY.setDirection(new Vector3f(0,1,0));
    		axisZ.setDirection(new Vector3f(0,0,1));
    		
    		axisX.setAvailableAcceleration(100);
    		axisY.setAvailableAcceleration(100);
    		axisZ.setAvailableAcceleration(100);
    		//axisX.setDesiredVelocity(1)
    		System.out.println("acc "+axisX.getAvailableAcceleration());
    		System.out.println("vel "+axisX.getDesiredVelocity());
    		
    	/*	final Joint jointForZ = world.getPhysicsSpace().createJoint();
    		final RotationalJointAxis rotationalAxisZ = jointForZ.createRotationalAxis();
    		rotationalAxisZ.setDirection( new Vector3f( 0, 0, 1 ) );
    		       
    		final RotationalJointAxis rotationalAxisX = jointForZ.createRotationalAxis();
    		rotationalAxisX.setDirection( new Vector3f( 1, 0, 0 ) );
*/
    		//jointForZ.attach( dynamicNode );
    		
    		//joint.setAnchor( new Vector3f( 0, 0, 0 ) );
    		
    		//joint.setAnchor(new Vector3f(1,0,0));
    		//joint.createTranslationalAxis();
    		
    	}
    }
    /**
     * encoder value in degree
     * @return encodervalue in degree
     */
    public float getEncoderValue() {
    	//some calculation
    	return 0;
    }

	/**
	 * make the actuator rotate towards a goalValue (degree) 
	 * @see ussr.model.PhysicsActuator#activate(float)
	 */
	public boolean activate(float goalValue) {
		//node1.setAffectedByGravity(false);
		//node2.setAffectedByGravity(false);
		/*System.out.println("Rotational Actuator activated");
		Vector3f COM1 = new Vector3f();
		Vector3f COM2 = new Vector3f();
		node2.getCenterOfMass(COM2);
		System.out.println("COM = "+node1.getLocalTranslation()+" "+COM2);*/
		System.out.println("node1.getLocalTranslation()= "+node1.getLocalTranslation());
		System.out.println("node1.getLocalRotation()= "+node1.getLocalRotation());
		node1.addTorque(new Vector3f(-1,0,0));
		node2.addTorque(new Vector3f(1,0,0));
//		node1.addForce(new Vector3f(-1,0,0), new Vector3f(-1,0,0));
		//node2.addForce(new Vector3f(0,-25,0), new Vector3f(1,0,0));
		//joint.getAxes().get(0).setRelativeToSecondObject(true);
		//joint.getAxes().get(0).setDesiredVelocity(1f);
		//joint.getAxes().get(0).setDirection(new Vector3f(1,0,0));
		//joint.getAxes().get(0).setAvailableAcceleration(100);
		
		System.out.println("acc = "+joint.getAxes().get(0).getAvailableAcceleration());
		System.out.println("pos = "+joint.getAxes().get(0).getPosition());
		//node2.setMass(1000);
		//System.out.println("pos = "+);
		return false;
	}

	/** (non-Javadoc)
	 * @see ussr.model.PhysicsActuator#disactivate()
	 */
	public void disactivate() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see ussr.model.PhysicsActuator#isActive()
	 */
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ussr.model.PhysicsActuator#setModel(ussr.model.Actuator)
	 */
	public void setModel(Actuator actuator) {
		// TODO Auto-generated method stub

	}

}
