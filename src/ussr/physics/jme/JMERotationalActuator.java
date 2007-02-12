/**
 * 
 */
package ussr.physics.jme;

import ussr.model.Actuator;
import ussr.model.PhysicsActuator;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.impl.ode.DynamicPhysicsNodeImpl;
import com.jmex.physics.impl.ode.OdePhysicsSpace;
import com.jmex.physics.impl.ode.joints.OdeJoint;


/**
 * @author david
 *
 */
public class JMERotationalActuator implements PhysicsActuator {

    private DynamicPhysicsNode node1;
    private DynamicPhysicsNode node2;
    private JMESimulation world;
    private OdeJoint joint;
    private String name;
	private float maxVelocity = 0.01f;
	private JointAxis axis;
	
    public JMERotationalActuator(JMESimulation world, String baseName) {
        this.world = world;
        this.name = baseName;
    }
    /**
     * Set the control parameters of the actuator
     * @param maxAcceleration (m/s^2)
     * @param maxVelocity (m/s)
     * @param lowerLimit (m)
     * @param upperLimit (m)
     */
    public void setControlParameters(float maxAcceleration, float maxVelocity, float lowerLimit, float upperLimit) {
    	if(joint==null) throw new RuntimeException("You must attach dynamics nodes first");
    	this.maxVelocity = maxVelocity;
    	axis.setAvailableAcceleration(maxAcceleration);
    	if(upperLimit==lowerLimit) {
    		axis.setPositionMaximum((float) Math.PI);
    		axis.setPositionMinimum((float)-Math.PI);
    	}
    	else {
    		axis.setPositionMaximum(upperLimit);
    		axis.setPositionMinimum(lowerLimit);
    	}
    }
    /**
     * Attach the linear acutator between two DynamicPhysicsNode 
     * @param DynamicPhysicsNode d1
     * @param DynamicPhysicsNode d2
     */
    public void attach(DynamicPhysicsNode d1, DynamicPhysicsNode d2) {
    	node1=d1; node2=d2;
    	//node1.setAffectedByGravity(false);
    	//node2.setAffectedByGravity(false);
    	if(joint==null)  {
    		joint = (OdeJoint)((OdePhysicsSpace)world.getPhysicsSpace()).createJoint();
    		
    		axis = joint.createRotationalAxis();
    		joint.attach(node1,node2);
//    		node.getLocalRotation().mult(mesh.getLocalTranslation()).add(node.getLocalTranslation());
    		//joint.createRotationalAxis().setDirection(new Vector3f(0,1,0));
    		//joint.createRotationalAxis().setDirection(new Vector3f(0,0,1));
    		
    		//axis.setDirection(d1.getLocalRotation().inverse().mult(new Vector3f(0,1,0)));
    		axis.setDirection(new Vector3f(1,0,0));
    		disactivate();
    		setControlParameters(9.82f,1f,-(float)Math.PI,(float)Math.PI); //default parameters
    		
    		//Joint testJoint = world.getPhysicsSpace().createJoint();
    		//testJoint.attach(node2);
    	}
    }
    public void setDirection(float x,float y, float z) {
    	axis.setDirection(new Vector3f(x,y,z));
    }
    public void reset() {
    	disactivate();
    	Vector3f[] axes = new Vector3f[]{new Vector3f(),new Vector3f(),new Vector3f()};
    	node1.getLocalRotation().toAxes(axes);
    	axis.setDirection(axes[2].mult(-1));	
	}
    /**
     * encoder value in percent
     * @return encoder value in percent
     */
    public float getEncoderValue() {
    	return 1f-(axis.getPositionMaximum()-axis.getPosition())/(axis.getPositionMaximum()-axis.getPositionMinimum());
    }

	/**
	 * Make the actuator rotate towards a goal [0-1] percent of fully expanded 
	 * @see ussr.model.PhysicsActuator#activate(float)
	 */
	public boolean activate(float goal) {
		if(Float.isNaN(getEncoderValue())||Float.isInfinite(getEncoderValue())) {
			//System.out.println("Actuator is not yet setup!");
			return false;
		}
		float error = goal-getEncoderValue();
		if(goal>0) axis.setDesiredVelocity(maxVelocity);
		else axis.setDesiredVelocity(-maxVelocity);
		return true;
	}
	long tempCounter=0;
	/** 
	 * Relax the linear actuator - can this be done always?
	 * @see ussr.model.PhysicsActuator#disactivate()
	 */
	public void disactivate() {
		axis.setDesiredVelocity(0);
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
	public String toString() {
		return "JMELinearActuator: "+name;
	}
}
