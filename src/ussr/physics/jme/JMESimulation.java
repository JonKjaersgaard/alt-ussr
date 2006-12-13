package ussr.physics.jme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import ussr.description.GeometryDescription;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.model.Robot;
import ussr.sandbox.StickyBot;

import com.jme.bounding.BoundingBox;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.util.PhysicsPicker;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * @author ups
 */
public class JMESimulation extends SimplePhysicsGame {

    public Map<String, DynamicPhysicsNode> connectorRegistry = new HashMap<String, DynamicPhysicsNode>();
    public Set<Joint> dynamicJoints = new HashSet<Joint>();
    public boolean connectorsAreActive = false;
    public long lastConnectorToggleTime = -1;
    private int numberOfModules;
    private Robot robot;
    private WorldDescription worldDescription;

    protected void simpleInitGame() {
        // Create underlying plane
        final StaticPhysicsNode staticPlane = createPlane(worldDescription.getPlaneSize());
        
        // Create obstacle box
        final List<DynamicPhysicsNode> obstacleBoxes = new ArrayList<DynamicPhysicsNode>();
        for(int i=0; i<worldDescription.getObstacles().size();i++)
            obstacleBoxes.add(createBox());

        // Create module
        final List<JMEModule> modules = new ArrayList<JMEModule>();
        for(int i=0; i<worldDescription.getNumberOfModules(); i++)
            modules.add(new JMEModule(this,robot,"module#"+Integer.toString(i)));
        
        showPhysics = true;

        // Reset action and keybinding
        final InputAction resetAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                for(Joint j: dynamicJoints) j.detach();
                dynamicJoints = new HashSet<Joint>();
                int offset = 5;
                for(JMEModule m: modules) {
                    for(DynamicPhysicsNode dynamicNode: m.getNodes()) {
                        dynamicNode.getLocalTranslation().set( 0, offset, 0 );
                        dynamicNode.getLocalRotation().set( 0, 0, 0, 1 );
                        dynamicNode.clearDynamics();
                    }
                    offset += 5;
               }

                Iterator<VectorDescription> positions = worldDescription.getObstacles().iterator();
                for(DynamicPhysicsNode box: obstacleBoxes) {
                    VectorDescription p = positions.next();
                  box.getLocalTranslation().set( p.getX(), p.getY(), p.getZ() );
                  box.getLocalRotation().set( 0, 0, 0, 1 );
                  box.clearDynamics();
                }
            }
        };
        input.addAction( resetAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_R, InputHandler.AXIS_NONE, false );
        resetAction.performAction( null );

        // Global connector activation toggle 
        InputAction toggleAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if(System.currentTimeMillis()-lastConnectorToggleTime<1000) return;
                lastConnectorToggleTime = System.currentTimeMillis();
                connectorsAreActive = !connectorsAreActive;
                if(connectorsAreActive) System.out.println("Connectors are now active");
                else System.out.println("Connectors are now inactive");
            }
        };
        input.addAction( toggleAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_Z, InputHandler.AXIS_NONE, false );

        MouseInput.get().setCursorVisible( true );
        new PhysicsPicker( input, rootNode, getPhysicsSpace() );
    }

    private int obstacleCounter = 0;
    private DynamicPhysicsNode createBox() {
        Box meshBox3 = new Box( "obstacle#"+(obstacleCounter++), new Vector3f(), 2, 2, 2 );
        meshBox3.setModelBound( new BoundingBox() );
        meshBox3.updateModelBound();
        final DynamicPhysicsNode dynamicNode3 = getPhysicsSpace().createDynamicNode();
        dynamicNode3.attachChild( meshBox3 );
        dynamicNode3.generatePhysicsGeometry();
        rootNode.attachChild( dynamicNode3 );
        dynamicNode3.computeMass();
        return dynamicNode3;
    }

    private StaticPhysicsNode createPlane(int size) {
        final StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();
        TriMesh trimesh = new Box( "plane", new Vector3f(), size, 0.5f, size );
        staticNode.attachChild( trimesh );
        trimesh.setModelBound( new BoundingBox() );
        trimesh.updateModelBound();
        staticNode.getLocalTranslation().set( 0, -5, 0 );
        rootNode.attachChild( staticNode );
        staticNode.generatePhysicsGeometry();
        return staticNode;
    }

    @Override
    protected void simpleUpdate() {
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
    }

 
    public Node getRootNode() { return rootNode; }

    public InputHandler getInput() {
        return input;
    }

    public void setNumberOfModules(int nModules) {
        this.numberOfModules = nModules;
    }

    public void setRobot(Robot bot) {
        this.robot = bot;
    }

    public void setWorld(WorldDescription world) {
        this.worldDescription = world;        
    }

  
 }

/*
 * $log$
 */

