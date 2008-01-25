/**
 * 
 */
package ussr.physics.jme.actuators;

import ussr.model.Actuator;
import ussr.model.PhysicsActuator;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.jme.JMESimulation;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.PhysicsSpace;
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
    private PIDController controller;
	private float maxVelocity = 0.01f;
	private float maxAcceleration = 0.01f;
	private float maxStopAcceleration = maxAcceleration;
	private float maxError = 0.001f;
	private JointAxis axis;
	private boolean active = false;
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
    		joint = (OdeJoint)((OdePhysicsSpace)world.getPhysicsSpace()).createJoint();
    		
    		axis = joint.createRotationalAxis();
    		
    		joint.attach(node1,node2);
    		joint.setAnchor(d1.getLocalRotation().mult(d2.getLocalTranslation()));
    		axis.setDirection(new Vector3f(1,0,0));
    		disactivate();
    		setControlParameters(9.82f,1f,-(float)Math.PI,(float)Math.PI); //default parameters
    	}
    }
    
    
    public void setDirection(float x,float y, float z) {
    	axis.setDirection(new Vector3f(x,y,z));
    }
	public void setErrorThreshold(float maxError) {
		this.maxError = maxError;
		
	}
    public void reset() {
    	disactivate();
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
	 * Make the actuator rotate towards a goal [0-1] percent 
	 * If -1 or +1 is given the actuator raotates full speed in the
	 * corresponding direction  
	 * @see ussr.model.PhysicsActuator#activate(float)
	 */
	public boolean activate(float goal) {
		active = true;
		axis.setAvailableAcceleration(maxAcceleration);
		if(Float.isNaN(getEncoderValue())||Float.isInfinite(getEncoderValue())) {
			PhysicsLogger.log("Actuator is not yet setup!");
			return false;
		}
		if(goal==-1 && unlimitedRotation) axis.setDesiredVelocity(-maxVelocity); 
		else if(goal==1  && unlimitedRotation) axis.setDesiredVelocity(maxVelocity);
		else { //go for position
			float error = goal-getEncoderValue();
			//System.out.println("goal = "+goal+" current = "+getEncoderValue()+" error = "+error);
			if(Math.abs(error)<maxError) {
				disactivate(); //at goal stop
			}
			else {
				float output = Math.abs(controller.getOutput(goal, getEncoderValue()));
				output = (Math.abs(error)<0.5?1:-1)*(error>0?1:-1)*(output>1?1:output);
				float desiredVel =  maxVelocity*output;
				if(Math.abs(axis.getVelocity())>Math.abs(desiredVel)) { //to stop from overshooting
					desiredVel = 0;
					//System.out.println("Stop 1");
					axis.setAvailableAcceleration(maxStopAcceleration);
				}
				if(axis.getVelocity()!=0.0f&&(axis.getVelocity()/Math.abs(axis.getVelocity())!=desiredVel/Math.abs(desiredVel))) { //to stop from overshooting
					desiredVel = 0;
					//System.out.println("Stop 2");
					axis.setAvailableAcceleration(maxStopAcceleration);
				}
				
				axis.setDesiredVelocity(desiredVel);
				
				//System.out.println(node1.getName()+": POS "+output+" vs. "+((Math.abs(error)<0.5?1:-1)*error/Math.abs(error)));
				//System.out.println(node1.getName()+": VEL "+axis.getDesiredVelocity()+" vs. "+axis.getVelocity());
			}
			/*else if(Math.abs(error)<0.5 ) { //go clockwise direction
				axis.setDesiredVelocity(maxVelocity*error/Math.abs(error));
				
			}
			else { //go counterclockwise direction
				axis.setDesiredVelocity(-maxVelocity*error/Math.abs(error));
			}*/
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
}
