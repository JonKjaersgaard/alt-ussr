package mpl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import ussr.model.Controller;
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

    private static final String COUNTERCW_TAG = "cc_";
    private static final String CLOCKWISE_TAG = "cw_";
    private static final String CONVEYOR_TAG = "conveyor_";
    private static final String ATRON_SMOOTH = "ATRON smooth";
    private static final String ATRON_CONVEYOR = "ATRON conveyor";

    public enum ConveyorElement {
        PLAIN, ROTATING_CLOCKWISE, ROTATING_COUNTERCW;
        static ConveyorElement fromChar(char c) {
            switch(c) {
            case 'P': return PLAIN;
            case 'R': return ROTATING_CLOCKWISE;
            case 'r': return ROTATING_COUNTERCW;
            default: throw new Error("Undefined conveyor element: "+c);
            }
        }
    }

    private List<ConveyorElement> layout;
    private ItemGenerator itemGenerator = new ItemGenerator();

    public MPLSimulation(String geneFileName) {
        try {
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
        } catch(IOException exception) {
            throw new Error("Unable to open gene file "+geneFileName+": "+exception);
        }
    }

    protected Robot getRobot() {
        return new ConveyorATRON();
    }

    /**
     * Controller for the conveyor simulation
     * 
     * @author ups
     */
    private static class PassiveATRON extends ATRON {
        public Controller createController() { return new PassiveController(); }
        protected static class PassiveController extends ATRONController {

            @Override
            public void activate() { 
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch(InterruptedException e) {
                    throw new Error("passive controller interrupted");
                }
            }

            /* (non-Javadoc)
             * @see ussr.samples.atron.ATRONController#handleMessage(byte[], int, int)
             */
            @Override
            public void handleMessage(byte[] message, int messageSize, int channel) {
                if(message.length==1 && message[0]==87)
                    this.disconnect(channel);
            }

        };
    }

    private static class ConveyorATRON extends PassiveATRON {

        public Controller createController() { return new ConveyorController(); }

        protected class ConveyorController extends PassiveController {

            @Override
            public void activate() {
                String name = this.module.getProperty("name");
                System.out.println("Disconnecting "+name);
                this.dodisconnect(0);
                this.dodisconnect(1);
                this.dodisconnect(2);
                this.dodisconnect(3);
                while(isConnected(0)||isConnected(1)||isConnected(2)||isConnected(3)) yield();
                if(name.indexOf(CLOCKWISE_TAG)>0)
                    this.rotateContinuous(1);
                else
                    this.rotateContinuous(-1);
            }

            void dodisconnect(int connector) {
                this.sendMessage(new byte[] { (byte)87 }, (byte)1, (byte)connector);
                this.disconnect(connector);
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
        MPLSimulation main = new MPLSimulation("home/ups/mpl/test.gene");
        main.main();
    }

}
