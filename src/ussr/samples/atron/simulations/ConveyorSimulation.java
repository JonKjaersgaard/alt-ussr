/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
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
public class ConveyorSimulation extends GenericATRONSimulation {

    public static void main(String argv[]) {
        ConveyorSimulation main = new ConveyorSimulation();
        //main.connection_acceptance_range = 0.02f;
        main.main();
    }
    
    protected Robot getRobot() {
        return new ConveyorATRON();
    }
    
    /**
     * Controller for the conveyor simulation
     * 
     * @author ups
     */
    private static class ConveyorATRON extends ATRON {
        public ConveyorATRON() {
            this.setSuper();
        }
            public Controller createController() {
                return new ATRONController() {

                    @Override
                    public void activate() {
                        while(module.getSimulation().isPaused()) Thread.yield();
                        String name = this.module.getProperty("name");
                        if(name.startsWith("conveyor")) {
                            System.out.println("Disconnecting "+name);
                            this.dodisconnect(0);
                            this.dodisconnect(2);
                            this.dodisconnect(4);
                            //this.dodisconnect(6);
                            while(true) {
                                do Thread.yield(); while(module.getSimulation().isPaused());
                                this.rotateContinuous(1);
                            }
                        } else {
                            try {
                                Thread.sleep(Long.MAX_VALUE);
                            } catch(InterruptedException e) {
                                throw new Error("foo!");
                            }
                        }
                    }
                    
                    void dodisconnect(int connector) {
                        this.sendMessage(new byte[] { (byte)87 }, (byte)1, (byte)connector);
                        this.disconnect(connector);
                        try {
                            Thread.sleep(100);
                        } catch(InterruptedException e) {
                            throw new Error("foo!");
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
    }
                    
    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON smooth = new ConveyorATRON();
        smooth.setSmooth();
        simulation.setRobot(smooth, "ATRON smooth");
    }


    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsNamedLattice(100,6,1,7, new ATRONBuilder.Namer() {
            //private boolean everyOther = false;
            private int count = 0;
            public String name(int number, VectorDescription pos, RotationDescription rot) {
                if(Math.abs(pos.getZ()-0.25)<0.02) {
                    String result;
                    if(count>0 && count<4)
                        result = "conveyor"+number;
                    else
                        result = "--passive"+number;
                    count++;
                    //everyOther = !everyOther;
                    return result;
                } else
                    return "--plain"+number;
            }
        }, new ATRONBuilder.ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot) {
                if(!name.startsWith("conveyor")) {
                    if(Math.abs(pos.getZ())<0.02 || Math.abs(pos.getZ())>0.4)
                        return null;
                    return "ATRON smooth";
                }
                return null;
            }
        },ATRON.UNIT);
        if(false) {
            positions.add(new ModulePosition("shoulder", new VectorDescription(3*ATRON.UNIT,1*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_UD));
            positions.add(new ModulePosition("elbow", new VectorDescription(2*ATRON.UNIT,2*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_EW));
            positions.add(new ModulePosition("hand", new VectorDescription(3*ATRON.UNIT,3*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_UD));
            positions.add(new ModulePosition("finger99", new VectorDescription(4*ATRON.UNIT,4*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_EW));
        }
        return positions;
    }

    protected void changeWorldHook(WorldDescription world) {
        BoxDescription[] boxes = new BoxDescription[3];
        boxes[0] = new BoxDescription(new VectorDescription(0.3f,-0.3f,-0.01f),new VectorDescription(0.6f,0.22f,0.2f),true);
        boxes[1] = new BoxDescription(new VectorDescription(0.3f,-0.3f,0.5f),new VectorDescription(0.6f,0.22f,0.2f),true);
        boxes[2] = new BoxDescription(new VectorDescription(0.45f,0.07f,0.25f),new VectorDescription(0.3f,0.01f,0.1f),false);
        world.setBigObstacles(boxes);
    }

}
