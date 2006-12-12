/*Copyright*/
package ussr.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import ussr.sandbox.TheThing.Module.ModuleCollisionAction;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import com.jme.input.util.SyntheticButton;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.contact.ContactInfo;
import com.jmex.physics.util.PhysicsPicker;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * @author Irrisor
 */
public class TheThing extends SimplePhysicsGame {

    private Map<String,DynamicPhysicsNode> connectorRegistry = new HashMap<String,DynamicPhysicsNode>();
    private Set<Joint> dynamicJoints = new HashSet<Joint>();
    private boolean connectorsAreActive = false;
    private long lastConnectorToggleTime = -1;
    
    class Module {
        public class ModuleCollisionAction implements InputActionInterface {

            public void performAction(InputActionEvent evt) {
                if(!connectorsAreActive) return;
                ContactInfo contactInfo = ( (ContactInfo) evt.getTriggerData() );
                String g1 = contactInfo.getGeometry1().getName();
                String g2 = contactInfo.getGeometry2().getName();
                if(connectorRegistry.containsKey(g1) && connectorRegistry.containsKey(g2)) {
                    Joint join = getPhysicsSpace().createJoint();
                    join.attach(connectorRegistry.get(g1),connectorRegistry.get(g2));
                    dynamicJoints.add(join);
                }
            }

        }

        private List<DynamicPhysicsNode> dynamicNodes = new ArrayList<DynamicPhysicsNode>();
        public Module(String name) {
            // Create central module node
            DynamicPhysicsNode moduleNode = getPhysicsSpace().createDynamicNode();
            dynamicNodes.add(moduleNode);
            // Create visual appearance
            Sphere meshSphere = new Sphere( name, 9, 9, 2 );
            meshSphere.setModelBound( new BoundingSphere() );
            meshSphere.updateModelBound();
            moduleNode.attachChild( meshSphere );
            // Finalize
            moduleNode.generatePhysicsGeometry();
            rootNode.attachChild( moduleNode );
            moduleNode.computeMass();
            // Create connectors
            addConnector(new Vector3f(-2.0f, 0.0f, 0),moduleNode,name);
            addConnector(new Vector3f(2.0f, 0.0f, 0),moduleNode,name);
            addConnector(new Vector3f(0.0f, 2.0f, 0),moduleNode,name);
            addConnector(new Vector3f(0.0f, -2.0f, 0),moduleNode,name);
            addConnector(new Vector3f(0.0f, 0.0f, -2.0f),moduleNode,name);
            addConnector(new Vector3f(0.0f, 0f, 2.0f),moduleNode,name);
        }
        
        private void addConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName) {
            // Create connector node
            DynamicPhysicsNode connector = getPhysicsSpace().createDynamicNode();
            dynamicNodes.add(connector);
            // Create visual appearance
            Sphere meshSphere = new Sphere( baseName+position.toString(), 9, 9, 1 );
            connectorRegistry.put(meshSphere.getName(),connector);
            meshSphere.getLocalTranslation().set( position );
            meshSphere.setModelBound( new BoundingSphere() );
            meshSphere.updateModelBound();
            connector.attachChild( meshSphere );
            // Finalize
            connector.generatePhysicsGeometry();
            rootNode.attachChild( connector );
            connector.computeMass();
            // Joint
            Joint connect = getPhysicsSpace().createJoint();
            connect.attach(moduleNode, connector);
            // Collision handler
            final SyntheticButton collisionEventHandler = connector.getCollisionEventHandler();
            input.addAction( new ModuleCollisionAction(),
                    collisionEventHandler.getDeviceName(), collisionEventHandler.getIndex(),
                    InputHandler.AXIS_NONE, false );
        }

        /**
         * @return the dynamicNode
         */
        public List<DynamicPhysicsNode> getNodes() {
            return dynamicNodes;
        }

        
    }
    
    protected void simpleInitGame() {
        // Create underlying plane
        final StaticPhysicsNode staticPlane = createPlane(25);
        
        // Create obstacle box
        final DynamicPhysicsNode obstacleBox = createBox();

        // Create module
        final List<Module> modules = new ArrayList<Module>();
        for(int i=0; i<7; i++)
            modules.add(new Module("module#"+Integer.toString(i)));
        
        showPhysics = true;

        // Reset action and keybinding
        final InputAction resetAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                for(Joint j: dynamicJoints) j.detach();
                dynamicJoints = new HashSet<Joint>();
                int offset = 5;
                for(Module m: modules) {
                    for(DynamicPhysicsNode dynamicNode: m.getNodes()) {
                        dynamicNode.getLocalTranslation().set( 0, offset, 0 );
                        dynamicNode.getLocalRotation().set( 0, 0, 0, 1 );
                        dynamicNode.clearDynamics();
                    }
                    offset += 5;
               }

                obstacleBox.getLocalTranslation().set( 0, -2.5f, 0 );
                obstacleBox.getLocalRotation().set( 0, 0, 0, 1 );
                obstacleBox.clearDynamics();
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

    private DynamicPhysicsNode createBox() {
        Box meshBox3 = new Box( "plane", new Vector3f(), 2, 2, 2 );
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
        TriMesh trimesh = new Box( "obstacle-box", new Vector3f(), size, 0.5f, size );
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

    public static void main( String[] args ) {
        System.out.println("java.library.path="+System.getProperty("java.library.path"));
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        new TheThing().start();
    }
}

/*
 * $log$
 */

