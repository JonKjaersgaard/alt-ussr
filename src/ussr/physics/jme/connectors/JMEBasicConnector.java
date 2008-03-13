/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.connectors;

import java.awt.Color;
import java.util.ArrayList;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.RobotDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.ConnectorBehaviorHandler;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsQuaternionHolder;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;

/**
 * Abstract class providing basic connector functionality
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class JMEBasicConnector implements JMEConnector, PhysicsObserver {
	protected volatile Connector model;
    protected volatile DynamicPhysicsNode node;
    protected volatile JMESimulation world;
    protected volatile JMEConnectorGeometry connectorGeometry;
    protected volatile JMEConnectorAligner connectorAligner;
    protected volatile String name;
    protected volatile JMEModuleComponent module;
    protected volatile float lastUpdateTime = -1;
    protected volatile float updateDeltaTime = 10;
    protected volatile ArrayList<JMEConnector> connectedConnectors = new ArrayList<JMEConnector>();
    protected volatile ArrayList<JMEConnector> proximateConnectors = new ArrayList<JMEConnector>();
    protected volatile Joint connection = null;
    protected volatile float timeToConnect = 0;
    protected volatile float timeToDisconnect = 0;
	
    public JMEBasicConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName, JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
    	this.world = world;
        this.module = component;
        this.name = baseName;
        this.node = moduleNode;
        this.connectorGeometry = new JMEConnectorGeometry(position, node, world, component, description);
        this.connectorAligner =  new JMEConnectorAligner(this, world, component);
    	world.subscribePhysicsTimestep(this);
    }
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
    	if((lastUpdateTime+updateDeltaTime)<world.getTime()) { 
    		update();
    		lastUpdateTime = world.getTime();
    	}
    }
	public void setUpdateFrequency(float updateFrequency) {
		updateDeltaTime = 1.0f/updateFrequency;
	}
	
	public void setTimeToConnect(float time) {
		timeToConnect = time;
	}
	public void setTimeToDisconnect(float time) {
		timeToDisconnect = time;
	}
	
	/**
	 * Position in world coordinates
	 * @return position
	 */
	public Vector3f getPos() {
		Vector3f pos = node.getLocalRotation().mult(connectorGeometry.getSpatial().getLocalTranslation()).add(node.getLocalTranslation());
		return pos;
	}
	/**
	 * Position in module coordinates
	 * @return position
	 */
	public Vector3f getPosRel() {
		return connectorGeometry.getLocalPosition();
	}
	/**
	 * Orientation relative to module
	 * @return orientation
	 */
	public Quaternion getRotRel() {
		return connectorGeometry.getLocalRotation();
	}
	/**
	 * Rotation in world coordinates
	 * @return rotaion
	 */
	public Quaternion getRot() {
		return node.getLocalRotation().mult(getRotRel());
	}
	public JMEConnectorGeometry getConnectorGeometry() {
		return connectorGeometry;
	}
	
	public JMEConnectorAligner getConnectorAligner() {
		return connectorAligner;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Set the rotation realtive to the module
	 */
	public void setRotation(PhysicsQuaternionHolder rot) {
		connectorGeometry.getSpatial().getLocalRotation().set(new Quaternion((Quaternion)rot.get()));
	}
	public void setPosition(VectorDescription position)  {
		System.out.println("position set");
		throw new RuntimeException();
	}
	public void setRotation(RotationDescription rotation) {
		System.out.println("rotation set");
		throw new RuntimeException();
	}
	
	/**
	 * Position in the world (global)  
	 * @return position
	 */
	public VectorDescription getPosition() {
		return new VectorDescription(getPos());
	}
	/**
	 * Orientation in the world (global)  
	 * @return orientation
	 */
	public RotationDescription getRotation() {
		return new RotationDescription(getRot());
	}
	
	public void setConnectorColor(Color color) {
		connectorGeometry.setConnectorColor(color);
	}
	
	
	boolean connecting = false;
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#connect(ussr.physics.PhysicsConnector)
     */
    public synchronized void connect() {
    	if(!connectedConnectors.equals(proximateConnectors)&&!connecting) {
    		connecting = true;
    		if(!proximateConnectors.isEmpty())
    			//System.out.println("Dist before connect = "+getPos().distance(proximateConnectors.get(0).getPos()));
    		
    		new AlignAndConnectManager(timeToConnect);
    	}
    }
	private class AlignAndConnectManager implements PhysicsObserver{
    	float timeToConnect;
    	float startTime;
    	public AlignAndConnectManager(float timeToConnect) {
    		this.timeToConnect = timeToConnect;
    		startTime = world.getTime();
    		world.subscribePhysicsTimestep(this);
    	}
    	public void physicsTimeStepHook(PhysicsSimulation simulation) {
			if((startTime+timeToConnect)>simulation.getTime()) { //correct misalignment period
				alignNow();
			}
			else { //connect now
				connectNow();
				simulation.unsubscribePhysicsTimestep(this);
			}
		}
    }
    
    protected synchronized void alignNow() {
    	synchronized (JMESimulation.physicsLock) {
	    	if(hasProximateConnector()) {
	    		for(int i=0;i<proximateConnectors.size();i++) {
    				JMEConnector c = proximateConnectors.get(i);
		    		if(connectorAligner.canAlign(c)&&!isSameModule(c)) {
		    			connectorAligner.align(c);
		    		}
	    		}
	    	}
    	}
    }
    
    public synchronized void connectNow() {
    	synchronized (JMESimulation.physicsLock) {
    		boolean connected = false;
	    	if(hasProximateConnector()) {
	    		for(int i=0;i<proximateConnectors.size();i++) {
    				JMEConnector c = proximateConnectors.get(i);
		    		if(canConnectNow(c)&&!isSameModule(c)&&!isConnectedTo(c)) {
		    			connectTo(c);
		    			c.update();
			    		update();
			    		connected = true;
		    		}
	    		}
	    	}
	    	if(connected) System.out.println("Connected success");
	    	else System.out.println("Connected failure");
	    	connecting = false;
    	}
    	
    }
    
    private boolean isConnectedTo(JMEConnector c) {
		return connectedConnectors.contains(c);
	}
	private boolean isSameModule(JMEConnector c) {
    	return getNode().equals(c.getNode());
	}
    
	/* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#disconnect(ussr.physics.PhysicsConnector)
     */
    public synchronized void disconnectNow() {
    	new Thread () {
    		public void run() {
    			synchronized (JMESimulation.physicsLock) {
    	    		if(isConnected()) {
    	    			for(int i=0;i<connectedConnectors.size();i++) {
    	    				JMEConnector c = connectedConnectors.get(i);
    	    				if(canDisconnectFrom(c)) {
    			    			disconnectFrom(c);
    			    			c.update();
    				    		update();
    			    		}
    	    			}
    	    		}
    	    	}
    		}
    	}.start();
    }
    public synchronized void disconnect() {
    	new WaitAndDisconnectManager(timeToDisconnect);
    }    
    private class WaitAndDisconnectManager implements PhysicsObserver {
    	float timeToConnect;
    	float startTime;
    	public WaitAndDisconnectManager(float timeToConnect) {
    		this.timeToConnect = timeToConnect;
    		startTime = world.getTime();
    		world.subscribePhysicsTimestep(this);
    	}
    	public void physicsTimeStepHook(PhysicsSimulation simulation) {
			if((startTime+timeToConnect)<=simulation.getTime()) { //correct misalignment period
				disconnectNow();
				simulation.unsubscribePhysicsTimestep(this);
			}
		}
    }
    

	/* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#getNode()
     */
    public DynamicPhysicsNode getNode() { return node; }

	protected synchronized void addConnectedConnector(JMEConnector c) {
		if(!connectedConnectors.contains(c))
			connectedConnectors.add(c);
	}
	protected synchronized void addProximateConnector(JMEConnector c) {
		if(!proximateConnectors.contains(c))
			proximateConnectors.add(c);
	}
	public void removeConnectedConnector(JMEConnector c) {
		connectedConnectors.remove(c);
	}

	protected synchronized void setConnection(Joint c) {
		connection = c;
	}
	
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#reset()
     */
    public synchronized void reset() {
    	connectedConnectors.clear();
    	proximateConnectors.clear();
    	
     }
    
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#hasProximateConnector()
     */
    public boolean hasProximateConnector() {
        return !proximateConnectors.isEmpty();
    }
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#isConnected()
     */
    public synchronized boolean isConnected() {
        return !connectedConnectors.isEmpty();
    }

    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#isDisconnected()
     */
    public boolean isDisconnected() {
    	return connectedConnectors.isEmpty();
	}
    
	/* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#setModel(ussr.model.Connector)
     */
    public void setModel(Connector connector) {
        this.model = connector;        
    }
    
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#getModel()
     */
    public Connector getModel() {
        return this.model;        
    }
    
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#toString()
     */
    public String toString() {
        return "JMEConnector<"+node.hashCode()+">";
    }
    public void clearDynamics() {
		getNode().clearDynamics();
	}
    
    protected void updateProximiteConnectors(float ignoreDistance) {
		ArrayList<JMEConnector> nearbyConnectors = getNearbyConnectors(ignoreDistance);
		proximateConnectors.clear();
		for(JMEConnector c : nearbyConnectors) {
			if(canConnectTo(c)&&!isSameModule(c)) {
				addProximateConnector(c);
			}
		}
    }
    
    /* (non-Javadoc)
     * @see ussr.physics.jme.JMEConnector#getAvailableConnectors()
     */
    public synchronized ArrayList<JMEConnector> getNearbyConnectors(float maxDist) {
    	Vector3f myPos = getPos();
    	JMEConnector other;
    	ArrayList<JMEConnector> nearbyConnectors = new ArrayList<JMEConnector>();
    	for(Module m: world.getModules()) {
			if(module.getModel().getID()!=m.getID())
			for(Connector c: m.getConnectors()) {
				other = (JMEConnector) c.getPhysics().get(0);
				if(myPos.distance(other.getPos())<maxDist) {
					nearbyConnectors.add((JMEConnector)c.getPhysics().get(0));
				}
			}
		}
    	return nearbyConnectors;
    }
    
    //protected abstract void align(JMEConnector connector);
    public abstract boolean canConnectTo(JMEConnector connector);
    public abstract boolean canConnectNow(JMEConnector connector);
    public abstract void connectTo(JMEConnector connector);
    
    public abstract boolean canDisconnectFrom(JMEConnector connector);
    public abstract void disconnectFrom(JMEConnector connector);
    
    public void setConnectorBehaviorHandler(ConnectorBehaviorHandler handler) {
        throw new Error("Setting connection behavior handler not supported for connectors of type "+this.getClass().getName());
    }
}
