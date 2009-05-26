package mpl;


import java.util.ArrayList;

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

    public static void main(String argv[]) {
        PhysicsParameters.get().setResolutionFactor(1);
        MPLSimulation main = new MPLSimulation();
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
                    yield();
                    setup();
                    String name = this.module.getProperty("name");
                    if(name.startsWith("conveyor")) {
                        System.out.println("Disconnecting "+name);
                        this.dodisconnect(0);
                        this.dodisconnect(2);
                        this.dodisconnect(4);
                        //this.dodisconnect(6);
                        this.rotateContinuous(1);
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
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsNamedLattice(100,0,5,1,3,0,10, new ATRONBuilder.Namer() {
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
                /*if(!name.startsWith("conveyor")) {
                    if(Math.abs(pos.getZ())<0.02 || Math.abs(pos.getZ())>0.4)
                        return null;
                    return "ATRON smooth";
                }*/
                return null;
            }
        },ATRON.UNIT, new VectorDescription(0,-0.5f,0),false);
        return positions;
    }

    protected void changeWorldHook(WorldDescription world) {
    }

}
