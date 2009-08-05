package mpl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
public class MPLSimulation extends GenericATRONSimulation {

    // Naming of modules (used to control behavior)
    static final String COUNTERCW_TAG = "#cc#_";
    static final String CLOCKWISE_TAG = "#cw#_";
    static final String BLOCKER_TAG = "#block#_";
    static final String SPINNER_TAG = "#spin#_";
    static final String COUNTER_SPINNER_TAG = "#counterspin#_";
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

    private List<Element> layout;
    private EventGenerator itemGenerator;
    private int magicGlobalLiftingModuleCounter = 0;
    private Object magicGlobalLiftingModuleSignal = new Object();

    public MPLSimulation(String geneSpec, String targetFileName, String outputFileName) {
        try {
            if(geneSpec.startsWith("@"))
                parseGeneFromFile(geneSpec);
            else
                readGeneFromFile(geneSpec);
            VectorDescription target = readTargetFromFile(targetFileName);
            itemGenerator = new EventGenerator(target, outputFileName);
        } catch(IOException exception) {
            throw new Error("Unable to initialize: "+exception);
        }
    }

    private void parseGeneFromFile(String geneSpec) {
        GeneParser p = Configuration.createGeneParser();
        layout = p.parse(geneSpec.substring(1));
    }

    private VectorDescription readTargetFromFile(String targetFileName) throws FileNotFoundException, IOException {
        File file = new File(targetFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        float[] target = new float[3];
        for(int i=0; i<3; i++) {
            String line = reader.readLine();
            if(line==null) throw new Error("Missing number");
            target[i] = Float.parseFloat(line);
        }
        return new VectorDescription(target[0],target[1],target[2]);
    }

    private void readGeneFromFile(String geneFileName) throws FileNotFoundException, IOException {
        File file = new File(geneFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        layout = new ArrayList<Element>();
        while(true) {
            int nextChar = reader.read();
            if(nextChar==-1) break;
            if(Character.isWhitespace(nextChar)) continue;
            layout.add(Element.fromChar((char)nextChar));
        }
        System.out.println("Read gene string of length "+layout.size());
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
        simulation.subscribePhysicsTimestep(itemGenerator);
    }

    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsNamedLattice(Configuration.PLANE_MAX_MODULES,0,Configuration.PLANE_MAX_X,1,3,0,Configuration.PLANE_MAX_Z, new ATRONBuilder.Namer() {
            private int count = 0;
            public String name(int number, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                if(ly==2) {
                    Element element = count<layout.size() ? layout.get(count++) : Element.PLAIN;
                    if(element==Element.ROTATING_CLOCKWISE)
                        return CONVEYOR_TAG+CLOCKWISE_TAG+number;
                    else if(element==Element.ROTATING_COUNTERCW)
                        return CONVEYOR_TAG+COUNTERCW_TAG+number;
                    else if(element==Element.BLOCKER) {
                        setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
                        return CONVEYOR_TAG+BLOCKER_TAG+number;
                    } else if(element==Element.SPINNER) { 
                        setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
                        return CONVEYOR_TAG+BLOCKER_TAG+SPINNER_TAG+number;
                    } else if(element==Element.COUNTER_SPINNER) { 
                        setMagicGlobalLiftingModuleCounter(getMagicGlobalLiftingModuleCounter() + 1);
                        return CONVEYOR_TAG+BLOCKER_TAG+COUNTER_SPINNER_TAG+number;
                    } else if(!(element==Element.PLAIN))
                        throw new Error("Unknown element type: "+element);
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
        },ATRON.UNIT, Configuration.PLANE_POSITION,false);
        return positions;
    }

    protected void changeWorldHook(WorldDescription world) {
        itemGenerator.prepareWorld(world);
    }

    public static void main(String argv[]) {
        PhysicsParameters.get().setResolutionFactor(1);
        PhysicsFactory.getOptions().setStartPaused(false);
        PhysicsParameters.get().setPhysicsSimulationStepSize(Configuration.TIME_STEP_SIZE);
        String inputGene = parseParam(argv,"gene","home/ups/mpl/test.gene");
        String targetPos = parseParam(argv,"target","home/ups/mpl/test.goal");
        String outputFile = parseParam(argv,"output","home/ups/mpl/test.output");
        String homeDir = parseParam(argv,"home",null); 
        if(homeDir!=null) PhysicsFactory.getOptions().setResourceDirectory(homeDir);
        MPLSimulation main = new MPLSimulation(inputGene,targetPos,outputFile);
        main.main();
    }

    private static String parseParam(String[] argv, String name, String defaultValue) {
        for(int i=0; i<argv.length; i++) {
            if(argv[i].startsWith(name+'=')) {
                return argv[i].substring(argv[i].indexOf('=')+1);
            }
        }
        return defaultValue;
    }

    public void setMagicGlobalLiftingModuleCounter(
            int magicGlobalLiftingModuleCounter) {
        this.magicGlobalLiftingModuleCounter = magicGlobalLiftingModuleCounter;
    }

    public int getMagicGlobalLiftingModuleCounter() {
        return magicGlobalLiftingModuleCounter;
    }

    public Object getMagicGlobalLiftingModuleSignal() {
        return magicGlobalLiftingModuleSignal;
    }
    
}
