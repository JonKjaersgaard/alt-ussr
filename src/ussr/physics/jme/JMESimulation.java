package ussr.physics.jme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.TransmissionType;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.ATRON;
import ussr.samples.StickyBot;
import ussr.util.Pair;

import com.jme.bounding.BoundingBox;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.util.PhysicsPicker;
import com.jme.app.AbstractGame;
import com.jme.util.geom.Debugger;
import com.jme.input.FirstPersonHandler;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsSpace;
import com.jme.renderer.Renderer;
import com.jme.image.Texture;
import com.jme.renderer.Camera;
import com.jme.scene.Text;
import com.jme.scene.state.LightState;
import com.jme.scene.state.WireframeState;
import com.jme.util.Timer;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.input.KeyBindingManager;
import com.jme.system.JmeException;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.input.joystick.JoystickInput;
import com.jme.util.TextureManager;
import com.jme.light.PointLight;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.scene.SceneElement;
import com.jme.scene.state.ZBufferState;
import com.jme.input.InputSystem;

/**
 * @author ups
 */
public class JMESimulation extends AbstractGame implements PhysicsSimulation {

    public Map<String, JMEConnector> connectorRegistry = new HashMap<String, JMEConnector>();
    public Set<Joint> dynamicJoints = new HashSet<Joint>();
    private Robot robot;
    private WorldDescription worldDescription;
    private List<JMEModuleComponent> modules = new ArrayList<JMEModuleComponent>();
    
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

    protected boolean pause;
    
    protected void simpleInitGame() {
        // Create underlying plane
        final StaticPhysicsNode staticPlane = createPlane(worldDescription.getPlaneSize());
        
        // Create obstacle boxes
        final List<DynamicPhysicsNode> obstacleBoxes = new ArrayList<DynamicPhysicsNode>();
        for(int i=0; i<worldDescription.getObstacles().size();i++)
            obstacleBoxes.add(createBox());

        // Create modules
        for(int i=0; i<worldDescription.getNumberOfModules(); i++) {
            final Module module = new Module();
            module.setController(robot.createController());
            int j=0;
            for(GeometryDescription geometry: robot.getDescription().getModuleGeometry()) {
                JMEModuleComponent physicsModule = new JMEModuleComponent(this,robot,geometry,"module#"+Integer.toString(i)+"."+(j++),module);
                module.addComponent(physicsModule);
                modules.add(physicsModule);
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
                int offset = 5;
                for(JMEModuleComponent m: modules) {
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

    
    public final void start() {
        LoggingSystem.getLogger().log(Level.INFO, "Application started.");
        try {
            getAttributes();

            if (!finished) {
                initSystem();

                assertDisplayCreated();
 
                initGame();

                // main loop
                while (!finished && !display.isClosing()) {
                	
                    // handle input events prior to updating the scene
                    // - some applications may want to put this into update of
                    // the game state
                        InputSystem.update();

                    // update game state, do not use interpolation parameter
                    update(-1.0f);

                    // render, do not use interpolation parameter
                   
                    	render(-1.0f);
                        // swap buffers
                        display.getRenderer().displayBackBuffer();
  
                    Thread.yield();
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
    
    protected void assignKeys()
    {       
    	/** Assign key P to action "toggle_pause". */
        KeyBindingManager.getKeyBindingManager().set( "toggle_pause",
                KeyInput.KEY_P );
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
        updateBuffer.append( "FPS: " ).append( (int) timer.getFrameRate() ).append(
                " - " );
        updateBuffer.append( display.getRenderer().getStatistics( tempBuffer ) );
        /** Send the fps to our fps bar at the bottom. */
        fps.print( updateBuffer );

        handleKeys();

        if ( !pause ) {
       		getPhysicsSpace().update(physicsSimulationStepSize);
        }

        input.update(tpf);

        if ( !pause ) {
            cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
            rootNode.updateGeometricState(tpf, true );
        }
    }

    protected void cameraPerspective() {
        cam.setFrustumPerspective( 45.0f, (float) display.getWidth()
                / (float) display.getHeight(), 1, 1000 );
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
        planeNode.getLocalTranslation().set( 0, -5, 0 );
        rootNode.attachChild( planeNode );
        planeNode.generatePhysicsGeometry();
        return planeNode;
    }

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
        Vector3f loc = new Vector3f( 0.0f, 0.0f, 25.0f );
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
        FirstPersonHandler firstPersonHandler = new FirstPersonHandler( cam, 50, 1 );
        input = firstPersonHandler;

        /** Sets the title of our display. */
        display.setTitle( "USSR" );
        /**
         * Signal to the renderer that it should keep track of rendering
         * information.
         */
        display.getRenderer().enableStatistics( true );
        
        
        
        assignKeys();

        /** Create a basic input controller. */
        cameraInputHandler = new FirstPersonHandler( cam, 50, 1 );
        input = new InputHandler();
        input.addToAttachedHandlers( cameraInputHandler );
 
        /** Get a high resolution timer for FPS updates. */
        timer = Timer.getTimer();

        setPhysicsSpace( PhysicsSpace.create() );

         input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                    showPhysics = !showPhysics;
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_V, InputHandler.AXIS_NONE, false );
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
            for(JMEModuleComponent target: modules) {
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

 }

