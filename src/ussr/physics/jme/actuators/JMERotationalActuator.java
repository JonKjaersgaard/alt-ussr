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
import ussr.model.Actuator.Direction;
import ussr.physics.PIDController;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.impl.ode.DynamicPhysicsNodeImpl;
import com.jmex.physics.impl.ode.OdePhysicsSpace;
import com.jmex.physics.impl.ode.joints.OdeJoint;


/**
 * Rotational actuator for the JME based simulation
 * 
 * @author david
 *
 */
public class JMERotationalActuator implements PhysicsActuator {

    private DynamicPhysicsNode node1;
    private DynamicPhysicsNode node2;
    private JMESimulation world;
    private Joint joint;
    private String name;
    private PIDController controller;
	private float maxVelocity = 0.01f;
	private float maxAcceleration = 0.01f;
	private float maxStopAcceleration = maxAcceleration;
	private float maxError = 0.001f;
	private JointAxis axis;
	private boolean active = false;
	private Vector3f startAxis = new Vector3f();
	private boolean unlimitedRotation=false;
    public JMERotationalActuator(JMESimulation world, String baseName) {
        this.world = world;
        this.name = baseName;
        this.controller = new PIDController();
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
    	this.maxAcceleration = maxAcceleration;
    	this.maxStopAcceleration = maxAcceleration;
    	axis.setAvailableAcceleration(maxAcceleration);
    	if(upperLimit==lowerLimit) {
    		axis.setPositionMaximum((float) Math.PI);
    		axis.setPositionMinimum((float)-Math.PI);
    		unlimitedRotation = true;
    	}
    	else {
    		axis.setPositionMaximum(upperLimit);
    		axis.setPositionMinimum(lowerLimit);
    		float pi = (float)Math.PI;
    		
    		if(upperLimit==pi&&lowerLimit==-pi) unlimitedRotation = true;
    		else unlimitedRotation = false;
    	}
    }
    public void setPIDParameters(float Kp, float Ki, float Kd) {
    	controller = new PIDController(Kp, Ki, Kd);
    }
    public void setMaxStopAcceleration(float maxStopAcceleration) {
    	this.maxStopAcceleration =maxStopAcceleration;
    }
    /**
     * Attach the linear acutator between two DynamicPhysicsNode 
     * @param DynamicPhysicsNode d1
     * @param DynamicPhysicsNode d2
     */
    public void attach(DynamicPhysicsNode d1, DynamicPhysicsNode d2) {
    	node1=d1; node2=d2;
    	if(joint==null)  {
    		
    		joint = world.getPhysicsSpace().createJoint();
    		//TODO JME2 change joint = (OdeJoint)((OdePhysicsSpace)world.getPhysicsSpace()).createJoint();
    		
    		axis = joint.createRotationalAxis();
    		
    		joint.attach(node1,node2);
    		joint.setAnchor(d1.getLocalRotation().mult(d2.getLocalTranslation()));
    		axis.setDirection(new Vector3f(1,0,0));
    		startAxis.set(1, 0, 0);
    		disactivate();
    		setControlParameters(9.82f,1f,-(float)Math.PI,(float)Math.PI); //default parameters
    	}
    }
    
    
    public void setDirection(float x,float y, float z) {
    	startAxis.set(x, y, z);
    	axis.setDirection(new Vector3f(x,y,z));
    }
	public void setErrorThreshold(float maxError) {
		this.maxError = maxError;
	}
    public void reset() {
    	disactivate();
    	setDirection(startAxis.x, startAxis.y, startAxis.z);
    	//TODO this method has not been full debugged check if problems with actuators rotatation 
    	Vector3f newAxis = node1.getLocalRotation().toRotationMatrix().mult(axis.getDirection(null).mult(-1));
    	axis.setDirection(newAxis);
    	
    	/*Vector3f[] axes = new Vector3f[]{new Vector3f(),new Vector3f(),new Vector3f()};
    	node1.getLocalRotation().toAxes(axes);
    	axis.setDirection(axes[2].mult(-1));*/
	}
	
	
    /**
     * encoder value in percent
     * @return encoder value in percent
     */
    public float getEncoderValue() {
    	return 1f-(axis.getPositionMaximum()-axis.getPosition())/(axis.getPositionMaximum()-axis.getPositionMinimum());
    }

    /**
	 * Make the actuator rotate with a given velocity [-1 to +1]
	 * If -1 or +1 is given the actuator rotates full speed in the
	 * corresponding direction  
	 * @see ussr.model.PhysicsActuator#activate(float)
	 */
    public boolean setDesiredVelocity(float goalVel) {
    	if(Float.isNaN(getEncoderValue())||Float.isInfinite(getEncoderValue())) {
			PhysicsLogger.log("Rotational Actuator is not yet setup!");
			return false;
		}
    	if(goalVel<-1||goalVel>1){
			PhysicsLogger.log("Rotational Actuator Velocity Value out of range! "+goalVel);
			return false;
		}
    	
    	active = true;
    	axis.setAvailableAcceleration(maxAcceleration);
		float desiredVel =  maxVelocity*goalVel;
		if(Math.abs(axis.getVelocity())>Math.abs(desiredVel)) { //to stop from overshooting
			desiredVel = 0;
			axis.setAvailableAcceleration(maxStopAcceleration);
		}
		if(axis.getVelocity()!=0.0f&&(axis.getVelocity()/Math.abs(axis.getVelocity())!=desiredVel/Math.abs(desiredVel))) { //to stop from overshooting
			desiredVel = 0;
			axis.setAvailableAcceleration(maxStopAcceleration);
		}
		axis.setDesiredVelocity(desiredVel);
		return true;
	}
    
    /**
     * Make the actuator rotate towards a goal [0-1] percent 
     * @see ussr.model.PhysicsActuator#setDesiredPosition(float)
     */
    public boolean setDesiredPosition(float goalPos) {
        return setDesiredPosition(goalPos,Direction.ANY); 
    }

    /**
	 * Make the actuator rotate towards a goal [0-1] percent going in a specific direction 
	 * @see ussr.model.PhysicsActuator#setDesiredPosition(float)
	 */
	public boolean setDesiredPosition(float goalPos, Direction direction) {
		if(Float.isNaN(getEncoderValue())||Float.isInfinite(getEncoderValue())) {
			PhysicsLogger.log("Rotational Actuator is not yet setup!");
			return false;
		}
		if(goalPos<0||goalPos>1){
			PhysicsLogger.log("Rotational Actuator Position Value out of range! "+goalPos);
			return false;
		}
		active = true;
		axis.setAvailableAcceleration(maxAcceleration);
		float error = goalPos-getEncoderValue();
		if(Math.abs(error)<maxError) {
			disactivate(); //at goal stop
		}
		else {
			//TODO bug MTRAN does not work reliably with this controller - why?
			float output = Math.abs(controller.getOutput(goalPos, getEncoderValue()));
			output = (Math.abs(error)<0.5?1:-1)*(error>0?1:-1)*(output>1?1:output);
			if(direction==Direction.NEGATIVE)
			    output = -Math.abs(output);
			else if(direction==Direction.POSITIVE)
			    output = Math.abs(output);
			setDesiredVelocity(output);
		}
		return true;
	}
	/** 
	 * Relax the linear actuator - can this be done always?
	 * @see ussr.model.PhysicsActuator#disactivate()
	 */
	public void disactivate() {
		active = false;
		axis.setDesiredVelocity(0);
	}

	/* (non-Javadoc)
	 * @see ussr.model.PhysicsActuator#isActive()
	 */
	public boolean isActive() {
		return active;
	}

	/* (non-Javadoc)
	 * @see ussr.model.PhysicsActuator#setModel(ussr.model.Actuator)
	 */
	public void setModel(Actuator actuator) {
		// TODO Auto-generated method stub

	}
	public String toString() {
		return "JMERotationalActuator: "+name;
	}
	public VectorDescription getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	public RotationDescription getRotation() {
		// TODO Auto-generated method stub
		return null;
	}
	public void moveTo(VectorDescription position, RotationDescription rotation) {
		throw new Error("Method not implemented");
	}

    public void poke() {
        joint.setSpring(1E20f, 1E20f);
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
