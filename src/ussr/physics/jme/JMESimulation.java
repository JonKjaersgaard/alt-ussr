package ussr.physics.jme;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.ImageIcon;

import jmetest.flagrushtut.Lesson3;
import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.TransmissionType;
import ussr.model.Actuator;
import ussr.model.Connector;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.model.PhysicsActuator;
import ussr.model.Sensor;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.AtronShape;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.util.Pair;

import com.jme.app.AbstractGame;
import com.jme.app.BaseSimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.InputSystem;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.joystick.JoystickInput;
import com.jme.light.PointLight;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.Skybox;
import com.jme.scene.Text;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.WireframeState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jme.util.Timer;
import com.jme.util.geom.Debugger;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.impl.ode.OdePhysicsSpace;
import com.jmex.physics.material.Material;
import com.jmex.physics.util.PhysicsPicker;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.MidPointHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

/**
 * @author ups
 */
public class JMESimulation extends AbstractGame implements PhysicsSimulation {

    public Map<String, JMEConnector> connectorRegistry = new HashMap<String, JMEConnector>();
    public Set<Joint> dynamicJoints = new HashSet<Joint>();
    //private Robot robot;
    Hashtable<String,Robot> robots = new Hashtable<String,Robot>();
    private WorldDescription worldDescription;
    private List<JMEModuleComponent> moduleComponents = new ArrayList<JMEModuleComponent>();
    private List<Module> modules = new ArrayList<Module>();
    private Map<DynamicPhysicsNode,Set<TriMesh>> geometryMap = new HashMap<DynamicPhysicsNode,Set<TriMesh>>();
    
    protected float physicsSimulationStepSize=0.01f;
    private PhysicsSpace physicsSpace;
    protected InputHandler cameraInputHandler;
   
   
    /**
     * The camera that we see through.
     */
    protected Camera cam;

    /**
     * The root of our normal scene graph.
     */
    protected Node rootNode;

    /**
     * Handles our mouse/keyboard input.
     */
    protected InputHandler input;

    /**
     * High resolution timer for jME.
     */
    protected Timer timer;

    /**
     * The root node of our text.
     */
    protected Node fpsNode;

    /**
     * Displays all the lovely information at the bottom.
     */
    protected Text fps;

    /**
     * Alpha bits to use for the renderer. Must be set in the constructor.
     */
    protected int alphaBits = 0;

    /**
     * Depth bits to use for the renderer. Must be set in the constructor.
     */
    protected int depthBits = 8;

    /**
     * Stencil bits to use for the renderer. Must be set in the constructor.
     */
    protected int stencilBits = 0;

    /**
     * Number of samples to use for the multisample buffer. Must be set in the constructor.
     */
    protected int samples = 0;

    /**
     * Simply an easy way to get at timer.getTimePerFrame(). Also saves time so
     * you don't call it more than once per frame.
     */
    protected float tpf;

    /**
     * True if the renderer should display the depth buffer.
     */
    protected boolean showDepth = false;

    /**
     * True if the renderer should display bounds.
     */
    protected boolean showBounds = false;

    /**
     * True if the rnederer should display normals.
     */
    protected boolean showNormals = false;

    /**
     * A wirestate to turn on and off for the rootNode
     */
    protected WireframeState wireState;

    /**
     * A lightstate to turn on and off for the rootNode
     */
    protected LightState lightState;

    /**
     * Location of the font for jME's text at the bottom
     */
    public static String fontLocation = Text.DEFAULT_FONT;

    /**
     * This is used to display print text.
     */
    protected StringBuffer updateBuffer = new StringBuffer( 30 );

    /**
     * This is used to recieve getStatistics calls.
     */
    protected StringBuffer tempBuffer = new StringBuffer();

    protected  TerrainBlock tb;
    
    protected boolean pause;
    protected boolean grapFrames=false;
    
    protected void simpleInitGame() {
        // Create underlying plane or terrain
        final StaticPhysicsNode staticPlane = createPlane(worldDescription.getPlaneSize());
    	//final StaticPhysicsNode staticPlane = createTerrain(worldDescription.getPlaneSize()); //david
        
        createSky();

        // Create obstacle boxes
        final List<DynamicPhysicsNode> obstacleBoxes = new ArrayList<DynamicPhysicsNode>();
        for(int i=0; i<worldDescription.getObstacles().size();i++)
            obstacleBoxes.add(createBox());
        
        // Create modules
        for(int i=0; i<worldDescription.getNumberOfModules(); i++) {
            final Module module = new Module();
            String robotType = (worldDescription.getModulePositions().size()>0)?worldDescription.getModulePositions().get(i).getType():"default";
            Robot robot = robots.get(robotType);
            module.setController(robot.createController());
            modules.add(module);
        	if(robot.getDescription().getType()=="ATRON") {
	           	//create ATRON
        		if(robot.getDescription().getModuleGeometry().size()!=2) throw new RuntimeException("Not an ATRON");
            	AtronShape northShape = (AtronShape) robot.getDescription().getModuleGeometry().get(0);
            	AtronShape southShape = (AtronShape) robot.getDescription().getModuleGeometry().get(1);
            	
            	
            	DynamicPhysicsNode northNode = getPhysicsSpace().createDynamicNode();
	            JMEModuleComponent northComponent = new JMEModuleComponent(this,robot,northShape,"module#"+Integer.toString(i)+".north",module,northNode);
	            northNode.setName("AtronNorth");

	            
	            DynamicPhysicsNode southNode = getPhysicsSpace().createDynamicNode();
	            JMEModuleComponent southComponent = new JMEModuleComponent(this,robot,southShape,"module#"+Integer.toString(i)+".south",module,southNode);
	            southNode.setName("AtronSouth");
	            
	            float unit = (float) (0.045f/Math.sqrt(2)); //4.5cm from center of mass to connector
	            northComponent.addConnector("Connector 0", new Vector3f(  unit,  unit, -unit));
	            northComponent.addConnector("Connector 1", new Vector3f( -unit,  unit, -unit ));
	            northComponent.addConnector("Connector 2", new Vector3f( -unit, -unit, -unit ));
	            northComponent.addConnector("Connector 3", new Vector3f(  unit, -unit, -unit ));
	            
	            southComponent.addConnector("Connector 4", new Vector3f(  unit,  unit,  unit ));
	            southComponent.addConnector("Connector 5", new Vector3f( -unit,  unit,  unit ));
	            southComponent.addConnector("Connector 6", new Vector3f( -unit, -unit,  unit ));
	            southComponent.addConnector("Connector 7", new Vector3f(  unit, -unit,  unit ));
	            
                module.addComponent(northComponent);
                module.addComponent(southComponent); //hvad skal håndteres ved fx placering af moduler?
                
                moduleComponents.add(northComponent);
                moduleComponents.add(southComponent);
                northNode.setMaterial(Material.CONCRETE);
                southNode.setMaterial(Material.CONCRETE);
                northNode.setMass(0.400f); //800 grams in total
                southNode.setMass(0.400f);
                
                JMERotationalActuator centerActuator = new JMERotationalActuator(this,"center");
                module.addActuator(new Actuator(centerActuator));
                centerActuator.attach(southNode,northNode);
                centerActuator.setControlParameters(500, 2f, 0, 0); //100N, 0.2 m/s or rad/s?, no rotational limits
                centerActuator.setDirection(0, 0, 1);
                centerActuator.activate(10);
                
                System.out.println("center c ="+centerActuator.getEncoderValue());
                System.out.println("Connector mass = "+southComponent.getNodes().get(0).getChildren().get(1).getClass()+" "+southNode.getMass());

/*                DynamicPhysicsNode atronTest1 = getPhysicsSpace().createDynamicNode();
                rootNode.attachChild( atronTest1 );
                TriMesh atronTest1Mesh = JMEDescriptionHelper.createShape(atronTest1, "", northShape);
                atronTest1.generatePhysicsGeometry();
                atronTest1.getLocalTranslation().addLocal( 0, 0.3f, 0 );
                atronTest1.setMass(0.01f);
                atronTest1.setAffectedByGravity(false);
                JMEDescriptionHelper.setColor(this,atronTest1Mesh,Color.RED);
                
                
                DynamicPhysicsNode armLimb1 = getPhysicsSpace().createDynamicNode();
                rootNode.attachChild( armLimb1 );
                TriMesh atronTest2Mesh = JMEDescriptionHelper.createShape(armLimb1, "", southShape);
                armLimb1.generatePhysicsGeometry();
                armLimb1.getLocalTranslation().addLocal(0, 0.3f, 0);
                armLimb1.setMass(0.01f);
                armLimb1.setAffectedByGravity(false);
                JMEDescriptionHelper.setColor(this,atronTest2Mesh,Color.BLUE);
                

                JMERotationalActuator testActuator = new JMERotationalActuator(this,"center");
                testActuator.attach(armLimb1,atronTest1);
                testActuator.setDirection(0, 0, 1);
                testActuator.activate(10);*/
                
        	}
        	else if(robot.getDescription().getType()=="OdinMuscle") {
            	//create OdinMuscle
            	CylinderShape externalCylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
            	CylinderShape internalCylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(1);
            	ConeShape externalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(2);
            	ConeShape internalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(3);
            	
           	   	DynamicPhysicsNode externalNode = this.getPhysicsSpace().createDynamicNode();
           	   	DynamicPhysicsNode internalNode = this.getPhysicsSpace().createDynamicNode();
           	   	
	            JMEModuleComponent externalComponent = new JMEModuleComponent(this,robot,externalCylinderShape,"module#"+Integer.toString(i)+".north",module,externalNode);
	            JMEModuleComponent externalCone = new JMEModuleComponent(this,robot,externalConeShape,"module#"+Integer.toString(i)+".north",module,externalNode);

	            JMEModuleComponent internalComponent = new JMEModuleComponent(this,robot,internalCylinderShape,"module#"+Integer.toString(i)+".south",module,internalNode);
	            JMEModuleComponent internalCone = new JMEModuleComponent(this,robot,internalConeShape,"module#"+Integer.toString(i)+".south",module,internalNode);
	           
	            float unit = 0.06f/2f+0.035f; 
	            externalComponent.addConnector("Connector 1", new Vector3f(-unit, 0, 0));
	            internalComponent.addConnector("Connector 2", new Vector3f(unit, 0, 0));
	            
	            
                module.addComponent(externalComponent);
                module.addComponent(externalCone);
                module.addComponent(internalComponent); 
                module.addComponent(internalCone);
                

                JMELinearActuator centerActuator = new JMELinearActuator(this,"center");
                module.addActuator(new Actuator(centerActuator));
                centerActuator.attach(externalNode,internalNode);
                //centerActuator.setControlParameters(9.82f,0.06f/0.25f,0f,0.06f); //odin muscle parametre - way too fast!
                centerActuator.setControlParameters(0.5f*9.82f,0.06f,0f,0.06f); //odin muscle parametre - way too fast!
                
                JMETiltSensor tiltX = new JMETiltSensor(this,"tiltX",'x',externalNode);
                JMETiltSensor tiltY = new JMETiltSensor(this,"tiltY",'y',externalNode);
                JMETiltSensor tiltZ = new JMETiltSensor(this,"tiltZ",'z',externalNode);
                module.addSensor(new Sensor(tiltX));
                module.addSensor(new Sensor(tiltY)); 
                module.addSensor(new Sensor(tiltZ)); 
                
                
                moduleComponents.add(externalComponent);
                moduleComponents.add(internalComponent);
                externalNode.setMass(0.025f); //50 grams in total?
                internalNode.setMass(0.025f);
	        }
			else if(robot.getDescription().getType()=="OdinBall") {
	            DynamicPhysicsNode moduleNode = this.getPhysicsSpace().createDynamicNode();            
	            int j=0;
	            for(GeometryDescription geometry: robot.getDescription().getModuleGeometry()) {
	                JMEModuleComponent physicsModule = new JMEModuleComponent(this,robot,geometry,"module#"+Integer.toString(i)+"."+(j++),module,moduleNode);
	                module.addComponent(physicsModule);
	                moduleComponents.add(physicsModule);
	            }
	            moduleNode.setMass(0.020f); //20 grams?
	        }
	    	else if(robot.getDescription().getModuleGeometry().size()==1) {
	            // Create central module node
	            DynamicPhysicsNode moduleNode = this.getPhysicsSpace().createDynamicNode();            
	            int j=0;
	            for(GeometryDescription geometry: robot.getDescription().getModuleGeometry()) {
	                JMEModuleComponent physicsModule = new JMEModuleComponent(this,robot,geometry,"module#"+Integer.toString(i)+"."+(j++),module,moduleNode);
	                module.addComponent(physicsModule);
	                moduleComponents.add(physicsModule);
	            }
	    	}
	    	else {
	    		throw new RuntimeException("Module type can not be constructed");
	    	}
	        new Thread() {
	            public void run() {
	                module.getController().activate();
	                PhysicsLogger.log("Warning: unexpected controller exit");
	            }
	        }.start();
        }
        
        //showPhysics = true;

        // Reset action and keybinding
        final InputAction resetAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                for(Joint j: dynamicJoints) j.detach();
                dynamicJoints = new HashSet<Joint>();
                if(worldDescription.getModulePositions().size()>0)
                    placeModules();
                else
                    generateModuleStackPlacement();
                
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
    long mainLoopCounter=0;
    public final void start() {
        LoggingSystem.getLogger().log(Level.INFO, "Application started.");
        try {
            getAttributes();

            if (!finished) {
                initSystem();

                assertDisplayCreated();
 
                initGame();

                readWorldParameters();

                // main loop
                while (!finished && !display.isClosing()) {
                    if ( !pause ) { 
                    	physicsStep(); // 1 call to = 32ms (one example setup)
                    }
                    if(mainLoopCounter%5==0) { // 1 call to = 16ms (same example setup)
                    	InputSystem.update();
                    	update(-1.0f);
	                	render(-1.0f);
	                    display.getRenderer().displayBackBuffer();// swap buffers
	                    if(grapFrames) {
	                    	grapFrame();
	                    }
                    }
                    mainLoopCounter++;
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        cleanup();
        LoggingSystem.getLogger().log(Level.INFO, "Application ending.");

        if (display != null)
            display.reset();
        quit();
    }
    /**
     * Get the time (physical) in seconds since the simulation started
     */
    public float getTime() {
		return physicsSteps*physicsSimulationStepSize;
	}
    
    private static final float FAROUT_DISTANCE = 50f;
    
    private void readWorldParameters() {
        if(worldDescription.getCameraPosition()==WorldDescription.CameraPosition.FAROUT) {
            cam.setLocation(cam.getLocation().add(0, 0, FAROUT_DISTANCE));
        }
    }

    protected void assignKeys()
    {       
    	/** Assign key P to action "toggle_pause". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_pause",
                KeyInput.KEY_P );
        /** Assign key P to action "toggle_pause". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_frame_grapping",
                KeyInput.KEY_G );
        /** Assign key T to action "toggle_wire". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_wire",
                KeyInput.KEY_T );
        /** Assign key L to action "toggle_lights". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_lights",
                KeyInput.KEY_L );
        /** Assign key B to action "toggle_bounds". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_bounds",
                KeyInput.KEY_B );
        /** Assign key N to action "toggle_normals". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_normals",
                KeyInput.KEY_N );
        /** Assign key C to action "camera_out". */
        KeyBindingManager.getKeyBindingManager().set( "camera_out",
                KeyInput.KEY_C );
        KeyBindingManager.getKeyBindingManager().set( "screen_shot",
                KeyInput.KEY_F1 );
        KeyBindingManager.getKeyBindingManager().set( "exit",
                KeyInput.KEY_ESCAPE );
        KeyBindingManager.getKeyBindingManager().set( "parallel_projection",
                KeyInput.KEY_F2 );
        KeyBindingManager.getKeyBindingManager().set( "toggle_depth",
                KeyInput.KEY_F3 );
        KeyBindingManager.getKeyBindingManager().set("mem_report",
                KeyInput.KEY_R);  	
    }
    
    protected void handleKeys()
    {        /** If toggle_pause is a valid command (via key p), change pause. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_frame_grapping", false ) ) {
            grapFrames = !grapFrames;
        }
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_pause", false ) ) {
            pause = !pause;
        }

        /** If toggle_wire is a valid command (via key T), change wirestates. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_wire", false ) ) {
            wireState.setEnabled( !wireState.isEnabled() );
            rootNode.updateRenderState();
        }
        /** If toggle_lights is a valid command (via key L), change lightstate. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_lights", false ) ) {
            lightState.setEnabled( !lightState.isEnabled() );
            rootNode.updateRenderState();
        }
        /** If toggle_bounds is a valid command (via key B), change bounds. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_bounds", false ) ) {
            showBounds = !showBounds;
        }
        /** If toggle_depth is a valid command (via key F3), change depth. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_depth", false ) ) {
            showDepth = !showDepth;
        }

        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_normals", false ) ) {
            showNormals = !showNormals;
        }
        /** If camera_out is a valid command (via key C), show camera location. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "camera_out", false ) ) {
            System.err.println( "Camera at: "
                    + display.getRenderer().getCamera().getLocation() );
        }

        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "screen_shot", false ) ) {
            display.getRenderer().takeScreenShot( "SimpleGameScreenShot" );
        }

        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "parallel_projection", false ) ) {
            if ( cam.isParallelProjection() ) {
                cameraPerspective();
            }
            else {
                cameraParallel();
            }
        }

        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "mem_report", false ) ) {
            long totMem = Runtime.getRuntime().totalMemory();
            long freeMem = Runtime.getRuntime().freeMemory();
            long maxMem = Runtime.getRuntime().maxMemory();
            
            System.err.println("|*|*|  Memory Stats  |*|*|");
            System.err.println("Total memory: "+(totMem>>10)+" kb");
            System.err.println("Free memory: "+(freeMem>>10)+" kb");
            System.err.println("Max memory: "+(maxMem>>10)+" kb");
        }

        if ( KeyBindingManager.getKeyBindingManager().isValidCommand( "exit",
                false ) ) {
            finish();
        }
    }
    
    /**
     * Called every frame to update scene information.
     *
     * @param interpolation unused in this implementation
     * @see BaseSimpleGame#update(float interpolation)
     */
    @Override
    protected final void update( float interpolation ) {
    	
        // disable input as we want it to be updated _after_ physics
        // in your application derived from BaseGame you can simply make the call to InputHandler.update later
        // in your game loop instead of this disabling and reenabling

        /** Recalculate the framerate. */
        timer.update();
        /** Update tpf to time per frame according to the Timer. */
        tpf = timer.getTimePerFrame();

        /** Check for key/mouse updates. */
        updateInput();

        // Execute updateQueue item
        GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();
        
        updateBuffer.setLength( 0 );
        updateBuffer.append( "FPS: " ).append( (int) timer.getFrameRate() ).append(" - " );
        //updateBuffer.append( display.getRenderer().getStatistics( tempBuffer ) ).append(" - " );;
        String timeStr= Float.toString(getTime());
        updateBuffer.append( "RT: " ).append( timeStr.subSequence(0, timeStr.indexOf('.')+2) ).append(" sec - " );
        updateBuffer.append( "TS: " ).append( (int) physicsSteps );
        /** Send the fps to our fps bar at the bottom. */
        fps.print( updateBuffer );

        handleKeys();
        input.update(tpf);
        
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
        rootNode.updateGeometricState(tpf, true );
        
    }
    private long physicsSteps = 0;
    private final void physicsStep() {
    	getPhysicsSpace().update(physicsSimulationStepSize);
    	physicsSteps++;
    }

    protected void cameraPerspective() {
        cam.setFrustumPerspective( 45.0f, (float) display.getWidth()
                / (float) display.getHeight(), 0.01f, 1000 );
        cam.setParallelProjection( false );
        cam.update();
    }

    protected void cameraParallel() {
        cam.setParallelProjection( true );
        float aspect = (float) display.getWidth() / display.getHeight();
        cam.setFrustum( -100, 1000, -50 * aspect, 50 * aspect, -50, 50 );
        cam.update();
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
        planeNode.getLocalTranslation().set( 0, -1f, 0 );
        rootNode.attachChild( planeNode );
        planeNode.generatePhysicsGeometry();
        planeNode.setMaterial(Material.DEFAULT);
        
        Texture tex = TextureManager.loadTexture(JMESimulation.class.getClassLoader().getResource("myGrass2.jpg"),Texture.MM_LINEAR_LINEAR,Texture.FM_LINEAR);
        tex.setWrap(Texture.WM_WRAP_S_WRAP_T);
        tex.setScale(new Vector3f(10f,10f,0f));
        TextureState ts = display.getRenderer().createTextureState();
        ts.setTexture(tex, 0);
        planeNode.setRenderState(ts);
        return planeNode;
    }
    /**
	 * build the height map and terrain block.
	 */
	private StaticPhysicsNode createTerrain(int size) { //change to hillhweightmap - looks better
		// Generate a random terrain data
//		 Generate a random terrain data
		MidPointHeightMap heightMap = new MidPointHeightMap(64, 1f);
		// Scale the data
		Vector3f terrainScale = new Vector3f(1, 0.03f, 1);
		// create a terrainblock
		tb = new TerrainBlock("Terrain", heightMap.getSize(), terrainScale,
				heightMap.getHeightMap(), new Vector3f(-32, -10, -32), false);
		tb.setModelBound(new BoundingBox());
		tb.updateModelBound();
 
		// generate a terrain texture with 3 textures
		ProceduralTextureGenerator pt = new ProceduralTextureGenerator(
				heightMap);
		pt.addTexture(new ImageIcon(Lesson3.class.getClassLoader()
				.getResource("grassb.png")), -255, 0, 255);
		pt.addTexture(new ImageIcon(Lesson3.class.getClassLoader()
				.getResource("dirt.jpg")), 0, 128, 255);
		pt.addTexture(new ImageIcon(Lesson3.class.getClassLoader()
				.getResource("highest.jpg")), 128, 255,
				384);
		pt.createTexture(32);
		
		// assign the texture to the terrain
		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);
		Texture t1 = TextureManager.loadTexture(pt.getImageIcon().getImage(),
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true);
		ts.setTexture(t1, 0);
		tb.setRenderState(ts);
		
		final StaticPhysicsNode terrainNode = getPhysicsSpace().createStaticNode();
		//terrainNode.attachChild(tb);
		//rootNode.attachChild(tb);
		
		terrainNode.attachChild( tb );
		tb.setModelBound( new BoundingBox() );
		tb.updateModelBound();
        //terrainNode.getLocalTranslation().set( 0, -100, 0 );
        rootNode.attachChild( terrainNode );
        terrainNode.generatePhysicsGeometry();
        return terrainNode;
        
		//return tb;
	}
    /**
     * buildSkyBox creates a new skybox object with all the proper textures. The
     * textures used are the standard skybox textures from all the tests.
     *
     */
	private StaticPhysicsNode createSky() {
		Skybox skybox = new Skybox("skybox", 100, 100, 100);
 
        Texture north = TextureManager.loadTexture(
            JMESimulation.class.getClassLoader().getResource(
            "jmetest/data/texture/north.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture south = TextureManager.loadTexture(
        		JMESimulation.class.getClassLoader().getResource(
            "jmetest/data/texture/south.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture east = TextureManager.loadTexture(
 
        		JMESimulation.class.getClassLoader().getResource(
            "jmetest/data/texture/east.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture west = TextureManager.loadTexture(
        		JMESimulation.class.getClassLoader().getResource(
            "jmetest/data/texture/west.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture up = TextureManager.loadTexture(
        		JMESimulation.class.getClassLoader().getResource(
            "jmetest/data/texture/top.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture down = TextureManager.loadTexture(
        		JMESimulation.class.getClassLoader().getResource(
            "jmetest/data/texture/bottom.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
 
        skybox.setTexture(Skybox.NORTH, north);
        skybox.setTexture(Skybox.WEST, west);
        skybox.setTexture(Skybox.SOUTH, south);
        skybox.setTexture(Skybox.EAST, east);
        skybox.setTexture(Skybox.UP, up);
        skybox.setTexture(Skybox.DOWN, down);
        skybox.preloadTextures();
        
        rootNode.attachChild( skybox );
        
        return null;
	}
    protected void simpleUpdate() {
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
    }

 
    public Node getRootNode() { return rootNode; }

    public InputHandler getInput() {
        return input;
    }

    public void setRobot(Robot bot) { //remove this method?
    	robots.put("default",bot);
    }
    public void setRobot(Robot bot, String type) {
		robots.put(type, bot);
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


    protected void initSystem() {

        LoggingSystem.getLogger().log( Level.INFO, getVersion());
        
         try {
            /**
             * Get a DisplaySystem acording to the renderer selected in the
             * startup box.
             */
            display = DisplaySystem.getDisplaySystem( properties.getRenderer() );
            LoggingSystem.getLogger().log( Level.INFO, "Running on: "+display.getAdapter()+"\nDriver version: "+display.getDriverVersion());
            
            display.setMinDepthBits( depthBits );
            display.setMinStencilBits( stencilBits );
            display.setMinAlphaBits( alphaBits );
            display.setMinSamples( samples );

            /** Create a window with the startup box's information. */
            display.createWindow( properties.getWidth(), properties.getHeight(),
                    properties.getDepth(), properties.getFreq(), properties
                    .getFullscreen() );
            /**
             * Create a camera specific to the DisplaySystem that works with the
             * display's width and height
             */
            cam = display.getRenderer().createCamera( display.getWidth(),
                    display.getHeight() );

        } catch ( JmeException e ) {
            /**
             * If the displaysystem can't be initialized correctly, exit
             * instantly.
             */
            e.printStackTrace();
            System.exit( 1 );
        }

        /** Set a black background. */
        display.getRenderer().setBackgroundColor( ColorRGBA.lightGray );

        /** Set up how our camera sees. */
        cameraPerspective();
        Vector3f loc = new Vector3f( 0.0f, 0.0f, 2.0f );
        Vector3f left = new Vector3f( -1.0f, 0.0f, 0.0f );
        Vector3f up = new Vector3f( 0.0f, 1.0f, 0.0f );
        Vector3f dir = new Vector3f( 0.0f, 0f, -1.0f );
        /** Move our camera to a correct place and orientation. */
        cam.setFrame( loc, left, up, dir );
        /** Signal that we've changed our camera's location/frustum. */
        cam.update();
        /** Assign the camera to this renderer. */
        display.getRenderer().setCamera( cam );

        /** Create a basic input controller. */
        FirstPersonHandler firstPersonHandler = new FirstPersonHandler( cam, 1f, 1 );
        input = firstPersonHandler;

        /** Sets the title of our display. */
        display.setTitle( "USSR - Unified Simulator for Self-Reconfigurable Robots" );
        /**
         * Signal to the renderer that it should keep track of rendering
         * information.
         */
        display.getRenderer().enableStatistics( true );
        
        
        
        assignKeys();

        /** Create a basic input controller. */
        cameraInputHandler = new FirstPersonHandler( cam, 1f, 1 );
        input = new InputHandler();
        input.addToAttachedHandlers( cameraInputHandler );
        
        /*if(cam.getLocation().y < (tb.getHeight(cam.getLocation())+2)) {
            cam.getLocation().y = tb.getHeight(cam.getLocation()) + 2;
            cam.update();
        }*/
        
        /** Get a high resolution timer for FPS updates. */
        timer = Timer.getTimer();
        setPhysicsSpace( PhysicsSpace.create() );
        ((OdePhysicsSpace) getPhysicsSpace()).setStepSize(physicsSimulationStepSize);
        ((OdePhysicsSpace) getPhysicsSpace()).setStepFunction(OdePhysicsSpace.SF_STEP_FAST); //or OdePhysicsSpace.SF_STEP_QUICK  
        

        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                    showPhysics = !showPhysics;
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_V, InputHandler.AXIS_NONE, false );
        
        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                    pause = true; 
                    physicsStep();
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_T, InputHandler.AXIS_NONE, false );
        
        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                	grapFrame();
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_F, InputHandler.AXIS_NONE, false );
        
         input.addAction( new InputAction() {
		    public void performAction( InputActionEvent evt ) {
		    	if(evt.getTriggerName()=="Wheel") {
	    			cam.setLocation(cam.getLocation().add(0, -0.1f*evt.getTriggerDelta(), 0));
		    	}
		    }
		}, InputHandler.DEVICE_MOUSE, AWTMouseInput.WHEEL_AMP, InputHandler.AXIS_ALL,false);
         
	 }
    private long frameCount=0;
    protected void grapFrame() {
    	if(frameCount==0) { //delete content of and create frame directory
    		File dir = new File("frames");
    		if(dir.isDirectory()) {
    			dir.renameTo(new File("frames"+System.currentTimeMillis()));
    		}
    		dir.mkdir();
    	}
    	display.getRenderer().takeScreenShot("frames/frame"+frameCount);
    	frameCount++;
	}

	
    
public RenderState color2jme(Color color) {
        float red = ((float)color.getRed())/255.0f;
        float green = ((float)color.getGreen())/255.0f;
        float blue = ((float)color.getBlue())/255.0f;
        float alpha = ((float)color.getAlpha())/255.0f;
        ColorRGBA jmecolor = new ColorRGBA(red,green,blue,alpha);
        final MaterialState materialState = display.getRenderer().createMaterialState();
        materialState.setDiffuse( jmecolor );
        return materialState;
    }

    public void sendMessage(TransmissionType type, Entity emitter, float range, Packet data) {
        if(!(emitter instanceof Module)||type!=TransmissionType.RADIO) throw new Error("not supported yet");
        for(Object component: ((Module)emitter).getPhysics()) {
            DynamicPhysicsNode source = ((JMEModuleComponent)component).getModuleNode();
            for(JMEModuleComponent target: moduleComponents) {
                if(source.getLocalTranslation().distance(target.getModuleNode().getLocalTranslation())<range)
                    for(Receiver receiver: target.getModel().getReceivers())
                        if(receiver.isCompatible(type)) {
                            receiver.receive(data);
                        }
            }
        }
    }
    
    /**
     * @return the physics space for this simple game
     */
    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }

    /**
     * @param physicsSpace The physics space for this simple game
     */
	protected void setPhysicsSpace(PhysicsSpace physicsSpace) {
		if ( physicsSpace != this.physicsSpace ) {
			if ( this.physicsSpace != null )
	       		this.physicsSpace.delete();
			this.physicsSpace = physicsSpace;
		}
	}

	protected void updateInput() {
	        // don't input here but after physics update
	    }

	    protected boolean showPhysics;

	    /**
	     * This is called every frame in BaseGame.start(), after update()
	     *
	     * @param interpolation unused in this implementation
	     * @see com.jme.app.AbstractGame#render(float interpolation)
	     */
	    protected final void render( float interpolation ) {
	        Renderer r = display.getRenderer();
	        /** Reset display's tracking information for number of triangles/vertexes */
	        r.clearStatistics();
	        /** Clears the previously rendered information. */
	        r.clearBuffers();
	        
	        // Execute renderQueue item
	        GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).execute();

	        preRender();
	        
	        /** Draw the rootNode and all its children. */
	        r.draw( rootNode );

	        /** Draw the fps node to show the fancy information at the bottom. */
	        r.draw( fpsNode );

	        if ( showDepth ) {
	            r.renderQueue();
	            Debugger.drawBuffer( Texture.RTT_SOURCE_DEPTH, Debugger.NORTHEAST, r );
	        }

	        doDebug(r);
	    }

	    protected void preRender() {

	    }

	    protected void doDebug(Renderer r) {
	        if ( showBounds ) {
	            Debugger.drawBounds( rootNode, r, true );
	        }

	        if ( showNormals ) {
	            Debugger.drawNormals( rootNode, r );
	        }

	        if ( showPhysics ) {
	            PhysicsDebugger.drawPhysics( getPhysicsSpace(), r );
	        }

	        if (showDepth) {
	            r.renderQueue();
	            Debugger.drawBuffer(Texture.RTT_SOURCE_DEPTH, Debugger.NORTHEAST, r);
	        }
	    }
	    
	    protected void cleanup() {
	        LoggingSystem.getLogger().log( Level.INFO, "Cleaning up resources." );

	        TextureManager.doTextureCleanup();
	        KeyInput.destroyIfInitalized();
	        MouseInput.destroyIfInitalized();
	        JoystickInput.destroyIfInitalized();
	    }
	    protected void initGame() {
	    	
	        /** Create rootNode */
	        rootNode = new Node( "rootNode" );

	        /**
	         * Create a wirestate to toggle on and off. Starts disabled with default
	         * width of 1 pixel.
	         */
	        	
	        wireState = display.getRenderer().createWireframeState();
	        wireState.setEnabled( false );
	        rootNode.setRenderState( wireState );
	        

	        /**
	         * Create a ZBuffer to display pixels closest to the camera above
	         * farther ones.
	         */
	        ZBufferState buf = display.getRenderer().createZBufferState();
	        buf.setEnabled( true );
	        buf.setFunction( ZBufferState.CF_LEQUAL );
	        rootNode.setRenderState( buf );
	        

	        // Then our font Text object.
	        /** This is what will actually have the text at the bottom. */
	        fps = Text.createDefaultTextLabel( "FPS label" );
	        fps.setCullMode( SceneElement.CULL_NEVER );
	        fps.setTextureCombineMode( TextureState.REPLACE );
	        //fps.setLocalScale(0.9f);
	        

	        // Finally, a stand alone node (not attached to root on purpose)
	        fpsNode = new Node( "FPS node" );
	        fpsNode.setRenderState( fps.getRenderState( RenderState.RS_ALPHA ) );
	        fpsNode.setRenderState( fps.getRenderState( RenderState.RS_TEXTURE ) );
	        fpsNode.attachChild( fps );
	        fpsNode.setCullMode( SceneElement.CULL_NEVER );

	        // ---- LIGHTS
	        /** Set up a basic, default light. */
	        PointLight light = new PointLight();
	        light.setDiffuse( new ColorRGBA( 0.75f, 0.75f, 0.75f, 0.75f ) );
	        light.setAmbient( new ColorRGBA( 0.5f, 0.5f, 0.5f, 1.0f ) );
	        light.setLocation( new Vector3f( 100, 100, 100 ) );
	        light.setEnabled( true );

	        /** Attach the light to a lightState and the lightState to rootNode. */
	        lightState = display.getRenderer().createLightState();
	        lightState.setEnabled( true );
	        lightState.attach( light );
	        rootNode.setRenderState( lightState );

	        /** Let derived classes initialize. */
	        simpleInitGame();

	        timer.reset();

	        /**
	         * Update geometric and rendering information for both the rootNode and
	         * fpsNode.
	         */
	        rootNode.updateGeometricState( 0.0f, true );
	        rootNode.updateRenderState();
	        fpsNode.updateGeometricState( 0.0f, true );
	        fpsNode.updateRenderState();
	    }
	    
	    protected void quit() {
	        System.exit( 0 );
	    }    
	    
	    protected void reinit() {
	        //do nothing
	    }


        /**
         * 
         */
        private void generateModuleStackPlacement() {
            int offset = 5;
            for(JMEModuleComponent m: moduleComponents) {
                m.reset();
                for(DynamicPhysicsNode dynamicNode: m.getNodes()) {
                    dynamicNode.getLocalTranslation().set( 0, offset, 0 );
                    dynamicNode.getLocalRotation().set( 0, 0, 0, 1 );
                    dynamicNode.clearDynamics();
                }
                offset += 5;
            }
        }

        /**
         * 
         */
        private void placeModules() {
            Iterator<WorldDescription.ModulePosition> positions = this.worldDescription.getModulePositions().iterator();
            Map<String,Module> registry = new HashMap<String,Module>();
            for(Module module: modules) {
                WorldDescription.ModulePosition p = positions.next();
                module.setProperty("name", p.getName());
                registry.put(p.getName(), module);
                List<? extends PhysicsEntity> components = module.getPhysics();
                // Reset each component
                for(PhysicsEntity pe: components)
                    ((JMEModuleComponent)pe).reset();
                if(false) {
	                // Reset physics node (HARDCODED: assumes one physics node per module!!!)
	                JMEModuleComponent c1 = (JMEModuleComponent)components.get(0);
	                if(components.size()>1) {
	                    if(c1.getModuleNode()!=((JMEModuleComponent)components.get(1)).getModuleNode())
	                        throw new Error("not supported yet");
	                }
	                DynamicPhysicsNode node = c1.getModuleNode();
	                node.getLocalTranslation().set(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
	                node.setLocalRotation(p.getRotation().getRotation());
	                node.clearDynamics();
                }
                else {
                	for(PhysicsEntity c1: components) { //works if CAD ATRON
	                	DynamicPhysicsNode node = ((JMEModuleComponent)c1).getModuleNode();
		                node.getLocalTranslation().set(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
		                node.setLocalRotation(new Quaternion(p.getRotation().getRotation()));
	                	node.clearDynamics();
                	}
                }
                for(Actuator actuator:  module.getActuators()) { 
                	((PhysicsActuator)actuator.getPhysics().get(0)).reset();
                }
            }
            //String robotType = worldDescription.getModulePositions().get(0).getType();
//            if(robots.get("default").getDescription().getConnectorType()!=RobotDescription.ConnectorType.MECHANICAL_CONNECTOR) {
//                if(this.worldDescription.getConnections().size()>0) PhysicsLogger.log("Warning: connector initialization ignored");
//                return;
//            }
            // The following only works for mechanical connectors
            // HARDCODED: assumes one physics per connector
            for(WorldDescription.Connection connection: this.worldDescription.getConnections()) {
                Module m1 = registry.get(connection.getModule1());
                Module m2 = registry.get(connection.getModule2());
                if(m1.getID()==m2.getID()) {
                	throw new RuntimeException("Module("+m1.getID()+") can not connect to itself("+m2.getID()+")");
                }
                int c1i = connection.getConnector1();
                int c2i = connection.getConnector2();
                if(c1i==-1||c2i==-1) {
                	c1i = findBestConnection(m1,m2);
                	c2i = findBestConnection(m2,m1);
                }
                if(c1i!=-1||c2i!=-1) {
	                Connector c1 = m1.getConnectors().get(c1i);
	                Connector c2 = m2.getConnectors().get(c2i);
	                if((c1.getPhysics().get(0) instanceof JMEMechanicalConnector)&&(c2.getPhysics().get(0) instanceof JMEMechanicalConnector)) {
	                	JMEMechanicalConnector jc1 = (JMEMechanicalConnector)c1.getPhysics().get(0);
	                    JMEMechanicalConnector jc2 = (JMEMechanicalConnector)c2.getPhysics().get(0);
	                    jc1.connectTo(jc2);
	                    jc1.setConnectorColor(Color.cyan);
	                    jc2.setConnectorColor(Color.cyan);
	                	
	                }
	                else {
	                	PhysicsLogger.log("Warning: connector initialization ignored");
	                }
                }
            }
        }

        private int findBestConnection(Module m1, Module m2) {
        	ArrayList<Vector3f> m1Cpos = connectorPos(m1);
        	ArrayList<Vector3f> m2Cpos = connectorPos(m2);
        //	System.out.println("Finding connections from module "+m1.getID()+" to module "+m2.getID());
        //	System.out.println("M1 Pos = "+((JMEModuleComponent)m1.getPhysics().get(0)).getModuleNode().getLocalTranslation());
        //	System.out.println("M2 Pos = "+((JMEModuleComponent)m2.getPhysics().get(0)).getModuleNode().getLocalTranslation());
        //	System.out.println("Module dist = "+(((JMEModuleComponent)m1.getPhysics().get(0)).getModuleNode().getLocalTranslation()).distance(((JMEModuleComponent)m2.getPhysics().get(0)).getModuleNode().getLocalTranslation()));
        	int connector = -1;
        	for(int i=0;i<m1Cpos.size();i++) {
        		for(int j=0;j<m2Cpos.size();j++) {
            		float d = m1Cpos.get(i).distance(m2Cpos.get(j));
            		if(d<0.005) {
            			//System.out.println("dist from "+i+" to "+j+" is "+d);
            			connector = i;
            		}
            		
            	}
        	}
        	return connector;
		}

		private ArrayList<Vector3f> connectorPos(Module m) {
			Vector3f mPos = ((JMEModuleComponent)m.getPhysics().get(0)).getModuleNode().getLocalTranslation();
			//System.out.println("Module "+m.getID()+" at ("+mPos.x+", "+mPos.y+", "+mPos.z+") has the following connectors");
			ArrayList<Vector3f> cPos = new ArrayList<Vector3f>();
			int index =0;
			for(Connector c: m.getConnectors()) {
				Vector3f absCpos = new Vector3f();
				Vector3f cpos = ((JMEMechanicalConnector)c.getPhysics().get(0)).getPos();
				//absCpos = absCpos.add(mPos);
				absCpos = absCpos.add(cpos);
				//System.out.println(" "+index+" at ("+absCpos.x+", "+absCpos.y+", "+absCpos.z+")");
				cPos.add(absCpos);
				index++;
    		}
			return cPos;
		}

		public void setPause(boolean pause) {
            this.pause = pause;
        }
        public boolean isPaused() {
            return pause||physicsSteps==0; //dont run controller in timestep 0 since ODE is not yet correctly setup (ugly hack?)
        }


        public synchronized void associateGeometry(DynamicPhysicsNode moduleNode, TriMesh shape) {
            Set<TriMesh> associated = geometryMap.get(moduleNode);
            if(associated==null) {
                associated = new HashSet<TriMesh>();
                geometryMap.put(moduleNode, associated);
            }
            associated.add(shape);
        }


        public Set<TriMesh> getNodeGeometries(DynamicPhysicsNode node) {
            Set<TriMesh> result = geometryMap.get(node);
            if(result!=null) return result;
            return Collections.emptySet();
        }
 }

