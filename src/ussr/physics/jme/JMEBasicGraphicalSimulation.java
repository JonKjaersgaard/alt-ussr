/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.SimulationGadget;
import ussr.physics.PhysicsFactory.Options;
import ussr.util.Pair;

import com.jme.app.AbstractGame;
import com.jme.app.BaseSimpleGame;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
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
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.impl.ode.OdePhysicsSpace;
import com.jmex.terrain.TerrainBlock;

/**
 * The basic graphical and user interface elements of a simulation.  Declares certain
 * field that are used in JMESimulation to define the behavior of the physical simulation.
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class JMEBasicGraphicalSimulation extends AbstractGame {

    protected static final Color[] obstacleColors = { Color.GRAY,
            Color.LIGHT_GRAY, Color.PINK, Color.YELLOW };
    /**
     * Location of the font for jME's text at the bottom
     */
    public static String fontLocation = Text.DEFAULT_FONT;
    /**
     * Alpha bits to use for the renderer. Must be set in the constructor.
     */
    protected int alphaBits = 0;
    /**
     * The camera that we see through.
     */
    protected Camera cam;
    protected InputHandler cameraInputHandler;
    /**
     * Depth bits to use for the renderer. Must be set in the constructor.
     */
    protected int depthBits = 8;
    /**
     * Displays all the lovely information at the bottom.
     */
    protected Text fps;
    /**
     * The root node of our text.
     */
    protected Node fpsNode;
    protected long frameCount = 0;
    protected boolean grapFrames = false;
    /**
     * Handles our mouse/keyboard input.
     */
    protected InputHandler input;
    protected List<Pair<String, PhysicsSimulation.Handler>> inputHandlers = new LinkedList<Pair<String, PhysicsSimulation.Handler>>();
    /**
     * A lightstate to turn on and off for the rootNode
     */
    protected LightState lightState;
    protected boolean scroll_on_mouse_wheel = true;
    /**
     * True if the renderer should display bounds.
     */
    protected boolean showBounds = false;
    /**
     * True if the renderer should display the depth buffer.
     */
    protected boolean showDepth = false;
    /**
     * True if the rnederer should display normals.
     */
    protected boolean showNormals = false;
    protected boolean showPhysics;
    /**
     * Stencil bits to use for the renderer. Must be set in the constructor.
     */
    protected int stencilBits = 0;
    protected TerrainBlock tb;
    /**
     * This is used to recieve getStatistics calls.
     */
    protected StringBuffer tempBuffer = new StringBuffer();
    /**
     * Simply an easy way to get at timer.getTimePerFrame(). Also saves time so you don't call it more than once per frame.
     */
    protected float tpf;
    /**
     * This is used to display print text.
     */
    protected StringBuffer updateBuffer = new StringBuffer(30);
    /**
     * A wirestate to turn on and off for the rootNode
     */
    protected WireframeState wireState;
    /**
     * Number of samples to use for the multisample buffer. Must be set in the constructor.
     */
    protected int samples = 0;
    /**
     * The root of our normal scene graph.
     */
    protected Node rootNode;
    /**
     * High resolution timer for jME.
     */
    protected Timer timer;
    protected boolean pause;
    protected boolean singleStep = false;
    int tip_plane_axis = 1;
    protected PhysicsSpace physicsSpace;
    private StaticPhysicsNode staticPlane;
    private boolean exitOnQuit;
    public JMEBasicGraphicalSimulation(Options options) {
        exitOnQuit = options.getExitOnQuit();
    }
    protected void assignKeys() {       
    	/** Assign key P to action "toggle_pause". */
        //KeyBindingManager.getKeyBindingManager().set( "toggle_pause",
        //        KeyInput.KEY_P );
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
        KeyBindingManager.getKeyBindingManager().set("display_debug_shell", KeyInput.KEY_U);
    }
    protected void handleKeys() {        /** If toggle_pause is a valid command (via key p), change pause. */
        if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_frame_grapping", false ) ) {
            grapFrames = !grapFrames;
        }
        /*if ( KeyBindingManager.getKeyBindingManager().isValidCommand(
                "toggle_pause", false ) ) {
            pause = !pause;
        }*/
    
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
        
        if(KeyBindingManager.getKeyBindingManager().isValidCommand("display_debug_shell", false)) {
            DebugShell.activate(this);
        }
    }
    protected void cameraPerspective() {
        if(cam!=null) {
        	cam.setFrustumPerspective( 45.0f, (float) display.getWidth()
                / (float) display.getHeight(), 0.01f, 1000 );
        	cam.setParallelProjection( false );
        	cam.update();
        }
    }
    protected void cameraParallel() {
    	if(cam!=null) {
	        cam.setParallelProjection( true );
	        float aspect = (float) display.getWidth() / display.getHeight();
	        cam.setFrustum( -100, 1000, -50 * aspect, 50 * aspect, -50, 50 );
	        cam.update();
    	}
    }
    public synchronized void addInputHandler(String keyName, final PhysicsSimulation.Handler handler) {
        if(inputHandlers==null) throw new Error("Input handlers cannot be added after simulation has been started");
        inputHandlers.add(new Pair<String,PhysicsSimulation.Handler>(keyName,handler));
    }
    protected synchronized void doAddInputHandlers() {
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
    protected void grapFrame() {
    	if(frameCount==0) { //delete content of and create frame directory
    		File dir = new File("frames");
    		if(dir.isDirectory()) {
    			dir.renameTo(new File("frames"+System.currentTimeMillis()));
    		}
    		dir.mkdir();
    	}
    	String name = (new Long(frameCount+1000000)).toString().substring(1);
    	display.getRenderer().takeScreenShot("frames/frame"+name);
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
        materialState.setAmbient(jmecolor );
        //materialState.setShininess(0);
        //materialState.setEmissive(jmecolor);
        
        return materialState;
        
       /* AlphaState astate = display.getRenderer().createAlphaState();
        astate.setBlendEnabled(true);					
        astate.setSrcFunction(AlphaState.SB_SRC_ALPHA);	
        astate.setDstFunction(AlphaState.DB_ONE);		
        astate.setTestEnabled(true);					
        astate.setTestFunction(AlphaState.TF_GREATER);	
        astate.setEnabled(true);
        return astate; 
        */
    }
    protected void updateInput() {
        // don't input here but after physics update
    }
    /**
     * This is called every frame in BaseGame.start(), after update()
     *
     * @param interpolation unused in this implementation
     * @see com.jme.app.AbstractGame#render(float interpolation)
     */
    protected final void render(float interpolation) {
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
    protected abstract void simpleInitGame();
    /**
     * Called every frame to update scene information.
     *
     * @param interpolation unused in this implementation
     * @see BaseSimpleGame#update(float interpolation)
     */
    @Override
    protected final void update(float interpolation) {
    	
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
        updateBuffer.append( "TS: " ).append( (int) getPhysicsSteps() );
        /** Send the fps to our fps bar at the bottom. */
        fps.print( updateBuffer );
    
        handleKeys();
        input.update(tpf);
        
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
        rootNode.updateGeometricState(tpf, true );
        
    }
    /**
     * Get the time (physical) in seconds since the simulation started
     */
    public float getTime() {
    	return getPhysicsSteps()*PhysicsParameters.get().getPhysicsSimulationStepSize();
    }
    
    public abstract long getPhysicsSteps();
    public abstract float getPhysicsSimulationStepSize();

    private static String resourcePathPrefix = "";
    public static void setResourcePathPrefix(String prefix) {
        resourcePathPrefix = prefix;
    }
    
    public static String setupPath(String path) {
        return resourcePathPrefix+path;
    }
    
    /**
     * buildSkyBox creates a new skybox object with all the proper textures. The
     * textures used are the standard skybox textures from all the tests.
     *
     */
    protected StaticPhysicsNode createSky(WorldDescription world) {
    	Skybox skybox = new Skybox("skybox", 100, 100, 100);
    
    	Texture north,south,east,west,up,down;
    	boolean clouds=world.hasBackgroundScenery();
    	if(clouds) {
	    	north = TextureManager.loadTexture(setupPath("resources/north.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
	        south = TextureManager.loadTexture(setupPath("resources/south.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
	        east = TextureManager.loadTexture(setupPath("resources/east.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
	        west = TextureManager.loadTexture(setupPath("resources/west.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
	        up = TextureManager.loadTexture(setupPath("resources/top.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
	        down = TextureManager.loadTexture(setupPath("resources/bottom.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
    	}
    	else {
    		north = TextureManager.loadTexture(setupPath("resources/white.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
            south = TextureManager.loadTexture(setupPath("resources/white.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
            east = TextureManager.loadTexture(setupPath("resources/white.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
            west = TextureManager.loadTexture(setupPath("resources/white.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
            up = TextureManager.loadTexture(setupPath("resources/white.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
            down = TextureManager.loadTexture(setupPath("resources/white.jpg"),Texture.MM_LINEAR,Texture.FM_LINEAR);
    	}
    	
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
    protected void initSystem() {
    	
        LoggingSystem.getLogger().log( Level.INFO, getVersion());
         try {
            /**
             * Get a DisplaySystem acording to the renderer selected in the
             * startup box.
             */
            display = DisplaySystem.getDisplaySystem( properties.getRenderer() );
            try {
                String displayInfo = display.getAdapter();
                LoggingSystem.getLogger().log( Level.INFO, "Running on: "+displayInfo+"\nDriver version: "+display.getDriverVersion());
            } catch(UnsatisfiedLinkError e) { 
                System.err.println("Unable to link native libraries, path = "+System.getProperty("java.library.path"));
                e.printStackTrace();
                throw new Error("Unable to link native libraries");
            }
            
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
        ((OdePhysicsSpace) getPhysicsSpace()).setStepSize(PhysicsParameters.get().getPhysicsSimulationStepSize());
        ((OdePhysicsSpace) getPhysicsSpace()).setUpdateRate(1f/PhysicsParameters.get().getPhysicsSimulationStepSize());
        ((OdePhysicsSpace) getPhysicsSpace()).setStepFunction(OdePhysicsSpace.SF_STEP_QUICK); //OdePhysicsSpace.SF_STEP_FAST or OdePhysicsSpace.SF_STEP_QUICK  or OdePhysicsSpace.SF_STEP_SIMULATION  
    
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
                	System.out.println("step");
                	if(!singleStep) {
                    	pause = true;
                        singleStep = true;
                	}
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_SPACE, InputHandler.AXIS_NONE, false );
       
        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                	pause = !pause;
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_P, InputHandler.AXIS_NONE, false );
        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                	grapFrame();
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_F, InputHandler.AXIS_NONE, false );
        
         input.addAction( new InputAction() {
    	    public void performAction( InputActionEvent evt ) {
    	    	if(scroll_on_mouse_wheel && evt.getTriggerName()=="Wheel") {
        			cam.setLocation(cam.getLocation().add(0, -0.1f*evt.getTriggerDelta(), 0));
    	    	}
    	    }
    	}, InputHandler.DEVICE_MOUSE, AWTMouseInput.WHEEL_AMP, InputHandler.AXIS_ALL,false);
         
        input.addAction( new InputAction() {
            public void performAction(InputActionEvent evt) {
                if(evt.getTriggerPressed()) scroll_on_mouse_wheel = !scroll_on_mouse_wheel;
                System.out.println("Scroll on mouse wheel = "+scroll_on_mouse_wheel);
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_COMMA, InputHandler.AXIS_NONE, false);
    
        input.addAction( new InputAction() {
            public void performAction(InputActionEvent evt) {
                if(evt.getTriggerPressed()) tip_plane_axis = 1;
                System.out.println("Plane tip in X direction");
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_1, InputHandler.AXIS_NONE, false);
        
        input.addAction( new InputAction() {
            public void performAction(InputActionEvent evt) {
                if(evt.getTriggerPressed()) tip_plane_axis = 2;
                System.out.println("Plane tip in Y direction");
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_2, InputHandler.AXIS_NONE, false);
        
        input.addAction( new InputAction() {
            public void performAction(InputActionEvent evt) {
                if(evt.getTriggerPressed()) tip_plane_axis = 3;
                System.out.println("Plane tip in Z direction");
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_3, InputHandler.AXIS_NONE, false);
        
        input.addAction( new InputAction() {
            public void performAction(InputActionEvent evt) {
                tip_plane(tip_plane_axis,-1);
                System.out.println("Negative plane tip");
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_4, InputHandler.AXIS_NONE, false);
        
        input.addAction( new InputAction() {
            public void performAction(InputActionEvent evt) {
                tip_plane(tip_plane_axis,1);
                System.out.println("Positive plane tip");
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_5, InputHandler.AXIS_NONE, false);
        
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
    protected void tip_plane(int plane_axis, int direction) {
        Quaternion rotate = staticPlane.getLocalRotation();
        float angles[] = new float[3]; 
        rotate.toAngles(angles);
        angles[plane_axis-1] += 0.01*Math.PI*direction;
        rotate.fromAngles(angles);
        staticPlane.setLocalRotation(rotate);
    }
    /**
     * @return the physics space for this simple game
     */
    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }
    protected void cleanup() {
        LoggingSystem.getLogger().log( Level.INFO, "Cleaning up resources." );
    
        TextureManager.doTextureCleanup();
        KeyInput.destroyIfInitalized();
        MouseInput.destroyIfInitalized();
        JoystickInput.destroyIfInitalized();
    }
    protected void quit() {
        if(exitOnQuit) System.exit( 0 );
    	//getPhysicsSpace().delete();
        
    }
    protected void reinit() {
        //do nothing
    }
    public Node getRootNode() { return rootNode; }
    public InputHandler getInput() {
        return input;
    }
    public boolean isPaused() {
        return pause||getPhysicsSteps()==0; //dont run controller in timestep 0 since ODE is not yet correctly setup (ugly hack?)
    }
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    protected void simpleUpdate() {
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
    }
    public void setStaticPlane(StaticPhysicsNode staticPlane) {
        this.staticPlane = staticPlane;
    }
    public StaticPhysicsNode getStaticPlane() {
        return staticPlane;
    }

    public void addGadget(SimulationGadget gadget) {
        DebugShell.addGadget(gadget);
    }
}
