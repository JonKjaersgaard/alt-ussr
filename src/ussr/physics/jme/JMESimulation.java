package ussr.physics.jme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import ussr.description.GeometryDescription;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.model.Robot;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.sandbox.StickyBot;
import ussr.util.Pair;

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
public class JMESimulation extends SimplePhysicsGame implements PhysicsSimulation {

    public Map<String, JMEConnector> connectorRegistry = new HashMap<String, JMEConnector>();
    public Set<Joint> dynamicJoints = new HashSet<Joint>();
    private Robot robot;
    private WorldDescription worldDescription;

    protected void simpleInitGame() {
        // Create underlying plane
        final StaticPhysicsNode staticPlane = createPlane(worldDescription.getPlaneSize());
        
        // Create obstacle boxes
        final List<DynamicPhysicsNode> obstacleBoxes = new ArrayList<DynamicPhysicsNode>();
        for(int i=0; i<worldDescription.getObstacles().size();i++)
            obstacleBoxes.add(createBox());

        // Create modules
        final List<JMEModule> modules = new ArrayList<JMEModule>();
        for(int i=0; i<worldDescription.getNumberOfModules(); i++) {
            final JMEModule physicsModule = new JMEModule(this,robot,"module#"+Integer.toString(i));
            modules.add(physicsModule);
            new Thread() {
                public void run() {
                    physicsModule.getModel().getController().activate();
                    PhysicsLogger.log("Warning: unexpected controller exit");
                }
            }.start();
        }
        
        showPhysics = true;

        // Reset action and keybinding
        final InputAction resetAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                for(Joint j: dynamicJoints) j.detach();
                dynamicJoints = new HashSet<Joint>();
                int offset = 5;
                for(JMEModule m: modules) {
                    m.reset();
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

        // Add any external input handlers
        doAddInputHandlers();
        
         MouseInput.get().setCursorVisible( true );
        new PhysicsPicker( input, rootNode, getPhysicsSpace() );
    }

    private int obstacleCounter = 0;
    private synchronized DynamicPhysicsNode createBox() {
        Box meshBox = new Box( "obstacle#"+(obstacleCounter++), new Vector3f(), 2, 2, 2 );
        meshBox.setModelBound( new BoundingBox() );
        meshBox.updateModelBound();
        final DynamicPhysicsNode boxNode = getPhysicsSpace().createDynamicNode();
        boxNode.attachChild( meshBox );
        boxNode.generatePhysicsGeometry();
        rootNode.attachChild( boxNode );
        boxNode.computeMass();
        return boxNode;
    }

    private StaticPhysicsNode createPlane(int size) {
        final StaticPhysicsNode planeNode = getPhysicsSpace().createStaticNode();
        TriMesh planeBox = new Box( "plane", new Vector3f(), size, 0.5f, size );
        planeNode.attachChild( planeBox );
        planeBox.setModelBound( new BoundingBox() );
        planeBox.updateModelBound();
        planeNode.getLocalTranslation().set( 0, -5, 0 );
        rootNode.attachChild( planeNode );
        planeNode.generatePhysicsGeometry();
        return planeNode;
    }

    @Override
    protected void simpleUpdate() {
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
    }

 
    public Node getRootNode() { return rootNode; }

    public InputHandler getInput() {
        return input;
    }

    public void setRobot(Robot bot) {
        this.robot = bot;
    }

    public void setWorld(WorldDescription world) {
        this.worldDescription = world;        
    }

    private List<Pair<String,PhysicsSimulation.Handler>> inputHandlers = new LinkedList<Pair<String,PhysicsSimulation.Handler>>();
    public synchronized void addInputHandler(String keyName, final PhysicsSimulation.Handler handler) {
        if(inputHandlers==null) throw new Error("Input handlers cannot be added after simulation has been started");
        inputHandlers.add(new Pair<String,PhysicsSimulation.Handler>(keyName,handler));
    }

    private synchronized void doAddInputHandlers() {
        assert inputHandlers != null;
        for(Pair <String,PhysicsSimulation.Handler> entry: inputHandlers) {
            final String keyName = entry.fst();
            final PhysicsSimulation.Handler handler = entry.snd();            
            InputAction action = new InputAction() {
                public void performAction( InputActionEvent evt ) {
                    handler.handle();
                }
            };
            input.addAction( action, InputHandler.DEVICE_KEYBOARD, JMEKeyTranslator.translate(keyName), InputHandler.AXIS_NONE, false );
        }
        inputHandlers = null;
    }

 }

