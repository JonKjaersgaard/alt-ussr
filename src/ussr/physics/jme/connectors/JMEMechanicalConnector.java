package ussr.physics.jme.connectors;

import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.RobotDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;

/**
 * Abstract class implementing mechanical connection functionality 
 * 
 * @author ups
 */
public abstract class JMEMechanicalConnector extends JMEBasicConnector  {
	/**
     * The abstract connector represented by this JME entity
     */
    
	public static final int UNISEX 	= 0;
	public static final int MALE 	= 1;
	public static final int FEMALE 	= 2;
	
	protected int connectorType = UNISEX;
    protected volatile float maxConnectDistance;
    

    public JMEMechanicalConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName, JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
    	super(position, moduleNode, baseName, world, component, description);
    	super.setUpdateFrequency(1f/0.3f); //every 300ms
    	this.maxConnectDistance = description.getMaxConnectionDistance();
    }

    /*
     * Update the state of the connector 
     * 1) Which nearby connectors can be connected to
     * 2) Check that nothing is physically off the mark
     * 3) Update the color to reflect its state
     */
    public synchronized void update() {
    	updateProximiteConnectors(maxConnectDistance);
    	updateConnectorsIntegrity();
		updateColor();
		updateHook();
    }
    
    /**
     * Override to provide a per-physics timestep update functionality in the connector 
     */
    protected void updateHook() { }

    private void updateConnectorsIntegrity() {
    	for(JMEConnector c: connectedConnectors) {
    		if(isConnected()&&c.getPos().distance(getPos())>5*maxConnectDistance) {
    			System.out.println("Warning connected from "+getName()+" module to "+c.getName()+" beyond saftyRegion - distance between connectors = "+c.getPos().distance(getPos()));
    		}
    	}
    	if(proximateConnectors.size()>1) {
    	    handleProximateConnectorsOverflow();
    	}
    	if(connectedConnectors.size()>1) {
    	    handleConnectedConnectorsOverflow();
    	}
	}
    
    protected void handleConnectedConnectorsOverflow() {
        System.err.println(module.toString()+": This module is connected to "+connectedConnectors.size()+" neighbors at connector! "+this);
    }

    protected void handleProximateConnectorsOverflow() {
        System.err.println(module.toString()+": This module has "+proximateConnectors.size()+" neighbors at connector! "+this);
    }
    
    protected void updateColor() {
    	if(isConnected()) {
    		connectorGeometry.setConnectorVisibility(true);
    		connectorGeometry.resetColor();
    	}
    	else connectorGeometry.setConnectorVisibility(false); 
    }
    
    public void disconnectFrom(JMEConnector connector) {
    	((JMEBasicConnector)connector).removeConnectedConnector(this);
    	removeConnectedConnector(connector);
    	if(this.connection!=null) {
    		if(connection.isActive())	{ 
    			connection.setActive(false);
    			connection.detach();
    		} 
    	}	
    }
    public synchronized void reset() {
    	super.reset();
    	if(connection!=null) connection.reset();
    }
    
    public synchronized void connectTo(JMEConnector jc2) {
    	//System.out.println("Dist at connect="+getPos().distance(jc2.getPos()));
    	
    	
    	JMEBasicConnector otherConnector = (JMEBasicConnector) jc2;
		if(connection==null) connection = world.getPhysicsSpace().createJoint();
		else connection.reset();
//		if(PhysicsParameters.get().hasMechanicalConnectorSpringiness()) connection.setSpring(PhysicsParameters.get().getMechanicalConnectorConstant(), PhysicsParameters.get().getMechanicalConnectorDamping());
		connection.setAnchor(connectorGeometry.getLocalPosition());
		connection.attach(getNode(),otherConnector.getNode());

		//getNode().getWorldTranslation().add(getNode().getWorldTranslation().add(otherConnector.getNode().getWorldTranslation()));
    	
    	connection.setActive(true);
    	otherConnector.setConnection(connection);
    	addAxis(connection);
    	addConnectedConnector(otherConnector);
    	otherConnector.addConnectedConnector(this);
    }
    public void setMaxConnectDistance(float distance) {
    	maxConnectDistance = distance;
    }
    public void setConnectorType(int type) {
    	if(type==UNISEX||type==MALE||type==FEMALE) 
    		connectorType = type;
    	else throw new RuntimeException("Unknown Connector type "+type);
    }
    public int getConnectorType() {
    	return connectorType;
    }
    
    protected abstract void addAxis(Joint connection);
}
