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
    private float epsilon = 0.01f;
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
    	axis.setPositionMaximum(upperLimit);
    	axis.setPositionMinimum(lowerLimit);
    }
    /**
     * Attach the linear acutator between two DynamicPhysicsNode 
     * @param DynamicPhysicsNode d1
     * @param DynamicPhysicsNode d2
     */
    public void attach(DynamicPhysicsNode d1, DynamicPhysicsNode d2) {
    	node1=d1; node2=d2;
    	node1.setAffectedByGravity(false);
    	node2.setAffectedByGravity(false);
    	if(joint==null)  {
    		joint = world.getPhysicsSpace().createJoint();
    		joint.attach(node1,node2);
    		axis = joint.createTranslationalAxis();
    		axis.setDirection(new Vector3f(-1,0,0));
    		setControlParameters(0.1f,0.05f,-1f,1f); //default parameters
    	}
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
		float error = goal-getEncoderValue();
		/*if(error>epsilon) {
			axis.setDesiredVelocity(maxVelocity);
		}
		else if(error<-epsilon) {
			axis.setDesiredVelocity(-maxVelocity);
		}
		else {
			axis.setDesiredVelocity(0);
			//System.out.println("Already There pos = "+getEncoderValue()+" error = "+error);
		}*/
		axis.setDesiredVelocity(-maxVelocity);
		//System.out.println("acc = "+joint.getAxes().get(0).getAvailableAcceleration());
		System.out.println("Velocity = "+axis.getVelocity());
		System.out.println("pos = "+getEncoderValue()+" error "+error);
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
