/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.actuators;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Actuator;
import ussr.model.PhysicsActuator;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;


/**
 * Linear actuator for the JME based simulation
 * 
 * @author david
 *
 */
public class JMELinearActuator implements JMEActuator {

//	private Actuator model;
    private DynamicPhysicsNode node1;
    private DynamicPhysicsNode node2;
    private JMESimulation world;
//    private JMEModuleComponent module;
    private Joint joint;
    private String name;
	private float maxVelocity = 0.01f;
	private float maxError = 0.001f;
	private JointAxis axis;
	
    public JMELinearActuator(JMESimulation world, String baseName) {
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
    	//node1.setAffectedByGravity(true);
    	//node2.setAffectedByGravity(true);
    	if(joint==null)  {
    		joint = world.getPhysicsSpace().createJoint();
    		joint.attach(node1,node2);
    		axis = joint.createTranslationalAxis();
    		//axis.setDirection(new Vector3f(-1,0,0));
    		//axis.setDesiredVelocity(0);
    		reset();
    		setControlParameters(0.1f,0.05f,0f,0.11f); //default parameters
    	}
    }
    public void reset() {
    	disactivate();
    	Vector3f[] axes = new Vector3f[]{new Vector3f(),new Vector3f(),new Vector3f()};
    	node1.getLocalRotation().toAxes(axes);
    	axis.setDirection(axes[0].mult(-1));	
	}
    /**
     * encoder value in percent
     * @return encoder value in percent
     */
    public float getEncoderValue() {
    	return 1f-(axis.getPositionMaximum()-axis.getPosition())/(axis.getPositionMaximum()-axis.getPositionMinimum());
    }
	public void setErrorThreshold(float maxError) {
		this.maxError = maxError;
	}

	/**
	 * Make the actuator rotate towards a goal [0-1] percent of fully expanded 
	 * @see ussr.model.PhysicsActuator#activate(float)
	 */
	public boolean activate(float goal) {
		if(Float.isNaN(axis.getPosition())) {
			//System.out.println("Actuator is not yet setup!");
			return false;
		}
		//FIXME make a controllere here
		float error = goal-getEncoderValue();
		//System.out.println("error = "+error+" goal="+goal);
		axis.setDesiredVelocity(maxVelocity*error);
		//System.out.println("acc = "+joint.getAxes().get(0).getAvailableAcceleration());
		//if(tempCounter%5000==0)System.out.println("{"+goal+","+getEncoderValue()+"},");
		tempCounter++;
		return false;
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
	public VectorDescription getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	public RotationDescription getRotation() {
		// TODO Auto-generated method stub
		return null;
	}
    public void poke() {

    }
	public void setPosition(VectorDescription position) {
		// TODO Auto-generated method stub
		
	}
	public void setRotation(RotationDescription rotation) {
		// TODO Auto-generated method stub
		
	}
	public void clearDynamics() {
		node1.clearDynamics();
		node2.clearDynamics();
	}
	public void addExternalForce(float forceX, float forceY, float forceZ) {
		node1.addForce(new Vector3f(forceX,forceY,forceZ));
		node2.addForce(new Vector3f(forceX,forceY,forceZ));
	}
}
