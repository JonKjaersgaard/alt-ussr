package ussr.physics.jme;

import java.awt.Color;
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

import ussr.model.Actuator;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.model.PhysicsActuator;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsSimulationHelper;
import ussr.physics.PhysicsParameters;
import ussr.physics.jme.robots.JMEModuleFactory;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.impl.ode.OdePhysicsSpace;
import com.jmex.physics.util.PhysicsPicker;

/**
 * @author ups
 */
public class JMESimulation extends JMEBasicGraphicalSimulation implements PhysicsSimulation {

    public Map<String, JMEConnector> connectorRegistry = new HashMap<String, JMEConnector>();
    public Set<Joint> dynamicJoints = new HashSet<Joint>();
    //private Robot robot;
    Hashtable<String,Robot> robots = new Hashtable<String,Robot>();
    private WorldDescription worldDescription;
    private List<JMEModuleComponent> moduleComponents = new ArrayList<JMEModuleComponent>();
    private List<Module> modules = new ArrayList<Module>();
    private Map<DynamicPhysicsNode,Set<TriMesh>> geometryMap = new HashMap<DynamicPhysicsNode,Set<TriMesh>>();
    private ArrayList<Thread> moduleThreads = new ArrayList<Thread>();
    
    protected float gravity; // Set from ussr.physics.SimulationParameters
    static class Lock extends Object {}
    static public Lock physicsLock = new Lock(); //should be used every time physics space is changed 
   
    protected List<PhysicsNode> obstacleBoxes;
    
    List<PhysicsNode> getObstacles() { return obstacleBoxes; }
    
    private JMEGeometryHelper helper = new JMEGeometryHelper(this);
    
    public PhysicsSimulationHelper getHelper() {
        return helper;
    }

    private JMEFactoryHelper factory;

    public JMESimulation(JMEModuleFactory[] factories) {
        PhysicsParameters parameters = PhysicsParameters.get();
        this.gravity = parameters.getGravity();
        this.physicsSimulationStepSize = parameters.getPhysicsSimulationStepSize();
        factory = new JMEFactoryHelper(this,factories);
    }
    
    protected void simpleInitGame() {
        // Create underlying plane or terrain
        setStaticPlane(helper.createPlane(worldDescription.getPlaneSize(),worldDescription.getPlaneTexture()));
    	//final StaticPhysicsNode staticPlane = createTerrain(worldDescription.getPlaneSize()); //david
        
        createSky();
        setGravity(gravity);

        setPhysicsErrorParameters(10E-5f, 0.8f); 
        
        
		
        // Create obstacle boxes
        obstacleBoxes = new ArrayList<PhysicsNode>();
        for(int i=0; i<worldDescription.getObstacles().size();i++)
            obstacleBoxes.add(helper.createBox(0.05f,0.05f,0.05f,0,false)); //compute mass from size
        for(int i=0; i<worldDescription.getBigObstacles().size();i++) {
            VectorDescription size = worldDescription.getBigObstacles().get(i).getSize();
            boolean isHeavy = worldDescription.getBigObstacles().get(i).getIsHeavy();
            boolean isStatic = worldDescription.getBigObstacles().get(i).getIsStatic();
            float mass = worldDescription.getBigObstacles().get(i).getMass();
            if(isHeavy)	obstacleBoxes.add(helper.createBox(size.getX(),size.getY(),size.getZ(),800f,isStatic));
            else obstacleBoxes.add(helper.createBox(size.getX(),size.getY(),size.getZ(),mass,isStatic));
        }
        
        // Create modules
        for(int i=0; i<worldDescription.getNumberOfModules(); i++) {
            final Module module = new Module();
            String robotType = (worldDescription.getModulePositions().size()>0)?worldDescription.getModulePositions().get(i).getType():"default";
            Robot robot = robots.get(robotType);
            WorldDescription.ModulePosition position = worldDescription.getModulePositions().get(i);
            String module_name = position.getName();
        	factory.createModule(i, module, robot, module_name);
        	module.setController(robot.createController());
            modules.add(module);
        	
            Thread moduleThread = new Thread() {
	            public void run() {
	                module.getController().activate();
	                PhysicsLogger.log("Warning: unexpected controller exit");
	            }
	        };
	        //moduleThread.setPriority(Thread.NORM_PRIORITY-1);
	        
	        moduleThreads.add(moduleThread);
	        moduleThread.start();
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
                    helper.generateModuleStackPlacement();
                
                List<VectorDescription> combinedPosList = new LinkedList<VectorDescription>();
                List<RotationDescription> combinedRotList = new LinkedList<RotationDescription>();
                
                for(VectorDescription elm: worldDescription.getObstacles()) {
                	combinedPosList.add(elm);
                	combinedRotList.add(new RotationDescription(new Quaternion()));                	
                }
                for(WorldDescription.BoxDescription elm: worldDescription.getBigObstacles()) {
                	combinedPosList.add(elm.getPosition());
                	combinedRotList.add(elm.getRotation());
                }
                Iterator<VectorDescription> positions = combinedPosList.iterator();
                Iterator<RotationDescription> rotations = combinedRotList.iterator();
                for(PhysicsNode box: obstacleBoxes) {
                	VectorDescription p = positions.next();
                	RotationDescription r = rotations.next();
                	box.getLocalTranslation().set( p.getX(), p.getY(), p.getZ() );
                	//box.getLocalRotation().set( 0, 0, 0, 1 );
                	box.getLocalRotation().set(r.getRotation());
                	if(box instanceof DynamicPhysicsNode) ((DynamicPhysicsNode)box).clearDynamics();
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


    private void setPhysicsErrorParameters(float cfm, float erp) {
    	((OdePhysicsSpace)getPhysicsSpace()).getODEJavaWorld().setConstraintForceMix(cfm); //default = 10E-5f, typical = [10E-9, 1]
		((OdePhysicsSpace)getPhysicsSpace()).getODEJavaWorld().setErrorReductionParameter(erp); //default = 0.2, typical = [0.1,0.8]
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
                long startTime = System.currentTimeMillis();
                while (!finished && !getDisplay().isClosing()) {
                    //if(!pause) Thread.sleep(100);
                	boolean physicsStep = false;
                    if ( !pause ||singleStep ) {
                    	physicsCallBack();
                    	physicsStep(); // 1 call to = 32ms (one example setup)
                    	physicsStep = true;
                    	waitForPhysicsStep(true);
                    	//System.out.println(getTime()+" : "+(System.currentTimeMillis()-startTime)/1000.0);
                    }
                    
                    KeyInput.get().update();
                    if(mainLoopCounter%5==0 ||singleStep) { // 1 call to = 16ms (same example setup)
                    	MouseInput.get().update(); //InputSystem.update();
                    	update(-1.0f);
	                	render(-1.0f);
	                    getDisplay().getRenderer().displayBackBuffer();// swap buffers
	                    if(grapFrames) {
	                    	grapFrame();
	                    }
                    }
                    mainLoopCounter++;
                    Thread.yield();
                    if(singleStep&&physicsStep) singleStep = false;
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        cleanup();
        LoggingSystem.getLogger().log(Level.INFO, "Application ending.");

       
        if (getDisplay() != null)
            getDisplay().reset();
        //display.close();
        
       	quit();
    }
    public void stop() {
    	finished = true;
    }

	private static final float FAROUT_DISTANCE = 50f;
    
    private void readWorldParameters() {
        if(worldDescription.getCameraPosition()==WorldDescription.CameraPosition.FAROUT) {
            cam.setLocation(cam.getLocation().add(0, 0, FAROUT_DISTANCE));
        }
    }

    private final void physicsStep() {
    	synchronized(physicsLock) {
    		getPhysicsSpace().update(physicsSimulationStepSize);
	    	physicsSteps++;
    	}
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
                	for(PhysicsEntity c1: components) { //works if CAD ATRON
	                	DynamicPhysicsNode node = ((JMEModuleComponent)c1).getModuleNode();
		                node.getLocalTranslation().set(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
		                node.setLocalRotation(new Quaternion(p.getRotation().getRotation()));
	                	node.clearDynamics();
                	}
                for(Actuator actuator:  module.getActuators()) { 
                	((PhysicsActuator)actuator.getPhysics().get(0)).reset();
                }
            }
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
                	c1i = helper.findBestConnection(m1,m2);
                	c2i = helper.findBestConnection(m2,m1);
                }
                if(c1i!=-1||c2i!=-1) {
	                Connector c1 = m1.getConnectors().get(c1i);
	                Connector c2 = m2.getConnectors().get(c2i);
                    PhysicsLogger.displayInfo("Connecting "+m1.getProperty("name")+"<"+c1i+":"+c1.getProperty("name")+"> to "+m2.getProperty("name")+"<"+c2i+":"+c2.getProperty("name")+">");
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
        public List<Module> getModules() {
        	return modules;
        }
		public void setGravity(float g) {
			gravity = g;
			getPhysicsSpace().setDirectionalGravity(new Vector3f(0,gravity,0));			
		}

		public synchronized void waitForPhysicsStep(boolean notify) {
        	if(notify) {
        		notifyAll();
        	}
        	else {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
		}
		
		
		private ArrayList<PhysicsObserver> physicsObservers = new ArrayList<PhysicsObserver>();
		public void subscribePhysicsTimestep(PhysicsObserver observer) {
			physicsObservers.add(observer);
		}
		
		private void physicsCallBack() {
			for(PhysicsObserver observer:physicsObservers) {
				observer.physicsTimeStepHook(this);
			}
		}

        public DisplaySystem getDisplay() {
            return display;
        }

        public void setModuleComponents(List<JMEModuleComponent> moduleComponents) {
            this.moduleComponents = moduleComponents;
        }

        public List<JMEModuleComponent> getModuleComponents() {
            return moduleComponents;
        }

		

		
 }

