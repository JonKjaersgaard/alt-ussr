package morphingProductionLine.abstractConveyor;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.ATRONBuilder.ModuleSelector;
import ussr.samples.atron.ATRONBuilder.Namer;

/**
 * A simulation of an ATRON conveyor belt that moves a box. Preliminary!
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class AbstractMPLSimulation extends GenericATRONSimulation {
     
    // Naming of modules (used to control behavior)
    static final String COUNTERCW_TAG = "#cc#_";
    static final String CLOCKWISE_TAG = "#cw#_";
    static final String BLOCKER_TAG = "#block#_";
    static final String SPINNER_TAG = "#spin#_";
    static final String COUNTER_SPINNER_TAG = "#counterspin#_";
    static final String NONE_TAG = "#none_";
    static final String SPECIAL_TAG = "#special_";
    private static final String CONVEYOR_TAG = "conveyor_";
    private static final String ATRON_PASSIVE = "ATRON smooth";
    private static final String ATRON_CONVEYOR = "ATRON conveyor";
    private static final String ATRON_BASE = "ATRON base";

    // Messages
    static final byte[] MSG_DISCONNECT_HERE = new byte[] { 87 };
    static final byte MSG_DISCONNECT_HERE_SIZE = 1;
    static final byte[] MSG_LIFT_ME = new byte[] { 29 };
    static final byte MSG_LIFT_ME_SIZE = 1;
    static final byte LIFTING_CONNECTOR = 4;
    public static final byte[] MSG_IS_ACTIVE_QUERY = new byte[] { 13 };
    public static final byte[] MSG_CONFIRM_ACTIVE = new byte[] { 7 };
    public static final byte[] MSG_CONFIRM_PASSIVE = new byte[] { 9 };

    private int magicGlobalLiftingModuleCounter = 0;
    private Object magicGlobalLiftingModuleSignal = new Object();

    private AbstractEventGenerator eventGenerator;

    public void setEventGenerator(AbstractEventGenerator _eventGenerator) {
        if(_eventGenerator==null) throw new Error("Event generator null argument not allowed");
        this.eventGenerator = _eventGenerator;
    }
    
    protected Robot getRobot() {
        return new ActiveATRON(this);
    }

    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON conveyor = new ActiveATRON(this);
        conveyor.setRubberRing();
        simulation.setRobot(conveyor, ATRON_CONVEYOR);
        ATRON passive;
        passive = new PassiveATRON(this);
        if(Configuration.PASSIVE_MODULE_SMOOTH_ATRON) passive.setSmooth();
        simulation.setRobot(passive, ATRON_PASSIVE);
        ATRON base = new PassiveATRON(this);
        simulation.setRobot(base, ATRON_BASE);
        if(eventGenerator==null)
            throw new Error("Event generator not defined");
        else
            simulation.subscribePhysicsTimestep(eventGenerator);
    }

    protected abstract String getTransportLayerModuleBehavior(int id, int x, int z);

    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsNamedLattice(Configuration.PLANE_MAX_MODULES,0,Configuration.PLANE_MAX_X,1,3,0,Configuration.PLANE_MAX_Z, new ATRONBuilder.Namer() {
            private int count = 0;
            public String name(int number, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                if(ly==2) {
                    String spec = getTransportLayerModuleBehavior(count++,lx,lz);
                    if(spec!=null) return spec;
                }
                return "--plain"+number;
            }
        }, new ATRONBuilder.ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                if(name.startsWith(CONVEYOR_TAG))
                    return ATRON_CONVEYOR;
                else if(ly==1)
                    return ATRON_BASE;
                else
                    return ATRON_PASSIVE;
            }
        },ATRON.UNIT, Configuration.getPlanePosition(),false);
        return positions;
    }

    protected void changeWorldHook(WorldDescription world) {
        if(eventGenerator==null) throw new Error("Event generator not set");
        eventGenerator.prepareWorld(world);
    }

    public void main() {
        File block = new File("USSR_BLOCK");
        while(block.exists()) {
            System.out.println("Detected blocking file USSR_BLOCK, sleeping 1 minute");
            try {
                Thread.sleep(60*1000);
            } catch(InterruptedException exn) {
                throw new Error("Unexpected interruption");
            }
        }
        PhysicsParameters.get().setResolutionFactor(1);
        PhysicsFactory.getOptions().setStartPaused(false);
        PhysicsParameters.get().setPhysicsSimulationStepSize(Configuration.TIME_STEP_SIZE);
        super.main();
    }

    private static String parseParam(String[] argv, String name, String defaultValue) {
        for(int i=0; i<argv.length; i++) {
            if(argv[i].startsWith(name+'=')) {
                return argv[i].substring(argv[i].indexOf('=')+1);
            }
        }
        return defaultValue;
    }

    public void incMagicGlobalLiftingModuleCounter(int difference) {
        this.magicGlobalLiftingModuleCounter += difference;
    }
    
    public void setMagicGlobalLiftingModuleCounter(int magicGlobalLiftingModuleCounter) {
        this.magicGlobalLiftingModuleCounter = magicGlobalLiftingModuleCounter;
    }

    public int getMagicGlobalLiftingModuleCounter() {
        return magicGlobalLiftingModuleCounter;
    }

    public Object getMagicGlobalLiftingModuleSignal() {
        return magicGlobalLiftingModuleSignal;
    }
    
}
