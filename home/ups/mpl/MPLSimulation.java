package mpl;


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
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
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
    private static final String COUNTERCW_TAG = "#cc#_";
    private static final String CLOCKWISE_TAG = "#cw#_";
    private static final String BLOCKER_TAG = "#block#_";
    private static final String SPINNER_TAG = "#spin#_";
    private static final String COUNTER_SPINNER_TAG = "#counterspin#_";
    private static final String CONVEYOR_TAG = "conveyor_";
    private static final String ATRON_SMOOTH = "ATRON smooth";
    private static final String ATRON_CONVEYOR = "ATRON conveyor";

    // Messages
    private static final byte[] MSG_DISCONNECT_HERE = new byte[] { (byte)87 };
    private static final byte MSG_DISCONNECT_HERE_SIZE = 1;
    private static final byte[] MSG_LIFT_ME = new byte[] { (byte)29 };
    private static final byte MSG_LIFT_ME_SIZE = 1;
    private static final byte LIFTING_CONNECTOR = 4;

    public enum ConveyorElement {
        PLAIN, ROTATING_CLOCKWISE, ROTATING_COUNTERCW, BLOCKER, SPINNER, COUNTER_SPINNER;
        static ConveyorElement fromChar(char c) {
            switch(c) {
            case 'P': return PLAIN;
            case 'R': return ROTATING_CLOCKWISE;
            case 'r': return ROTATING_COUNTERCW;
            case 'b': return BLOCKER;
            case 's': return SPINNER;
            case 'S': return COUNTER_SPINNER;
            default: throw new Error("Undefined conveyor element: "+c);
            }
        }
    }

    private List<ConveyorElement> layout;
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
        GeneParser p = new GeneParser(1,new char[] { 'P', 'r'});
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
        layout = new ArrayList<ConveyorElement>();
        while(true) {
            int nextChar = reader.read();
            if(nextChar==-1) break;
            if(Character.isWhitespace(nextChar)) continue;
            layout.add(ConveyorElement.fromChar((char)nextChar));
        }
        System.out.println("Read gene string of length "+layout.size());
    }

    protected Robot getRobot() {
        return new ConveyorATRON();
    }

    /**
     * Controller for the conveyor simulation
     * 
     * @author ups
     */
    private class PassiveATRON extends ATRON {
        public Controller createController() { return new PassiveController(); }
        protected class PassiveController extends ATRONController {
            private int lift = -1;

            @Override
            public void activate() {
                yield();
                while(true) {
                    synchronized(this) {
                        try {
                            this.wait();
                        } catch(InterruptedException e) {
                            throw new Error("passive controller interrupted");
                        }
                    }
                    if(lift>-1) {
                        System.out.println("Lift requested from port "+lift);
                        disconnectOthersSameHemi(lift);
                        this.rotateDegrees(-90);
                        while(this.isRotating()) yield();
                        magicGlobalLiftingModuleCounter--;
                        synchronized(magicGlobalLiftingModuleSignal) {
                            magicGlobalLiftingModuleSignal.notifyAll();
                        }
                        lift = -1;
                    }
                }
            }

            protected void disconnectOthersSameHemi(int connector) {
                int c_min, c_max;
                if(connector<4) { c_min = 0; c_max = 3; }
                else { c_min = 4; c_max = 7; }
                for(int c = c_min; c<=c_max; c++)
                    if(c!=connector) this.symmetricDisconnect(c);
                boolean allDisconnected = false;
                while(!allDisconnected) {
                    allDisconnected = true;
                    for(int c = c_min; c<=c_max; c++)
                        if(c!=connector && this.isConnected(c)) allDisconnected = false;
                    yield();
                }
            }

            protected void symmetricDisconnect(int connector) {
                this.sendMessage(MSG_DISCONNECT_HERE, MSG_DISCONNECT_HERE_SIZE, (byte)connector);
                this.disconnect(connector);
            }

            /* (non-Javadoc)
             * @see ussr.samples.atron.ATRONController#handleMessage(byte[], int, int)
             */
            @Override
            public void handleMessage(byte[] message, int messageSize, int channel) {
                if(Arrays.equals(message,MSG_DISCONNECT_HERE))
                    this.disconnect(channel);
                else if(Arrays.equals(message, MSG_LIFT_ME)) {
                    System.out.println("Blocking behavior received");
                    lift = channel;
                    synchronized(this) { this.notify(); }
                } else
                    System.err.println("Unknown message received");
            }

        };
    }

    private class ConveyorATRON extends PassiveATRON {

        public Controller createController() { return new ConveyorController(); }

        protected class ConveyorController extends PassiveController {

            @Override
            public void activate() {
                yield();
                String name = this.module.getProperty("name");
                System.out.println("Disconnecting "+name);
                this.symmetricDisconnect(0);
                this.symmetricDisconnect(1);
                this.symmetricDisconnect(2);
                this.symmetricDisconnect(3);
                while(isConnected(0)||isConnected(1)||isConnected(2)||isConnected(3)) yield();
                if(name.indexOf(CLOCKWISE_TAG)>0 || name.indexOf(COUNTERCW_TAG)>0) {
                    synchronized(magicGlobalLiftingModuleSignal) {
                        while(magicGlobalLiftingModuleCounter>0)
                            try {
                                magicGlobalLiftingModuleSignal.wait();
                            } catch(InterruptedException exn) {
                                throw new Error("Unexpected interruption");
                            }
                    }
                    if(name.indexOf(CLOCKWISE_TAG)>0)
                        this.rotateContinuous(1);
                    else
                        this.rotateContinuous(-1);
                }
                else if(name.indexOf(BLOCKER_TAG)>0)
                    blockingBehavior(name);
                else throw new Error("No behavior for "+name);
            }

            private void blockingBehavior(String name) {
                System.out.println("Blocking behavior activated");
                this.disconnectOthersSameHemi(LIFTING_CONNECTOR);
                if(name.indexOf(SPINNER_TAG)>0)
                    this.rotateContinuous(1);
                else if(name.indexOf(COUNTER_SPINNER_TAG)>0)
                    this.rotateContinuous(-1);
                this.sendMessage(MSG_LIFT_ME, MSG_LIFT_ME_SIZE, LIFTING_CONNECTOR);
            }

        }
    }

    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON conveyor = new ConveyorATRON();
        conveyor.setRubberRing();
        simulation.setRobot(conveyor, ATRON_CONVEYOR);
        ATRON smooth = new PassiveATRON();
        smooth.setSmooth();
        simulation.setRobot(smooth, ATRON_SMOOTH);
        simulation.subscribePhysicsTimestep(itemGenerator);
    }

    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsNamedLattice(100,0,5,1,3,0,10, new ATRONBuilder.Namer() {
            private int count = 0;
            public String name(int number, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                if(ly==2) {
                    ConveyorElement element = count<layout.size() ? layout.get(count++) : ConveyorElement.PLAIN;
                    if(element==ConveyorElement.ROTATING_CLOCKWISE)
                        return CONVEYOR_TAG+CLOCKWISE_TAG+number;
                    else if(element==ConveyorElement.ROTATING_COUNTERCW)
                        return CONVEYOR_TAG+COUNTERCW_TAG+number;
                    else if(element==ConveyorElement.BLOCKER) {
                        magicGlobalLiftingModuleCounter++;
                        return CONVEYOR_TAG+BLOCKER_TAG+number;
                    } else if(element==ConveyorElement.SPINNER) { 
                        magicGlobalLiftingModuleCounter++;
                        return CONVEYOR_TAG+BLOCKER_TAG+SPINNER_TAG+number;
                    } else if(element==ConveyorElement.COUNTER_SPINNER) { 
                        magicGlobalLiftingModuleCounter++;
                        return CONVEYOR_TAG+BLOCKER_TAG+COUNTER_SPINNER_TAG+number;
                    } else if(!(element==ConveyorElement.PLAIN))
                        throw new Error("Unknown element type: "+element);
                }
                return "--plain"+number;
            }
        }, new ATRONBuilder.ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                if(name.startsWith(CONVEYOR_TAG))
                    return ATRON_CONVEYOR;
                return ATRON_SMOOTH;
            }
        },ATRON.UNIT, new VectorDescription(0,-0.54f,0),false);
        return positions;
    }

    protected void changeWorldHook(WorldDescription world) {
        itemGenerator.prepareWorld(world);
    }

    public static void main(String argv[]) {
        PhysicsParameters.get().setResolutionFactor(1);
        PhysicsFactory.getOptions().setStartPaused(false);
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
    
}
