package mpl;

import java.util.Arrays;

import ussr.model.Controller;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;

/**
 * Controller for the conveyor simulation
 * 
 * @author ups
 */
class PassiveATRON extends ATRON {
    /**
     * 
     */
    protected final MPLSimulation simulation;
    protected boolean isActive = false;
    /**
     * @param simulation
     */
    PassiveATRON(MPLSimulation simulation) {
        this.simulation = simulation;
    }
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
                    //System.out.println("Lift requested from port "+lift);
                    this.disconnectOthersSameHemi(lift);
                    this.rotateDegrees(-90);
                    while(this.isRotating()) yield();
                    decrementMagicCounter();
                    lift = -1;
                }
            }
        }

        protected void decrementMagicCounter() {
            synchronized(PassiveATRON.this.simulation.getMagicGlobalLiftingModuleSignal()) {
                PassiveATRON.this.simulation.setMagicGlobalLiftingModuleCounter(PassiveATRON.this.simulation.getMagicGlobalLiftingModuleCounter() - 1);
                PassiveATRON.this.simulation.getMagicGlobalLiftingModuleSignal().notifyAll();
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
            this.sendMessage(MPLSimulation.MSG_DISCONNECT_HERE, MPLSimulation.MSG_DISCONNECT_HERE_SIZE, (byte)connector);
            this.disconnect(connector);
        }

        /* (non-Javadoc)
         * @see ussr.samples.atron.ATRONController#handleMessage(byte[], int, int)
         */
        @Override
        public void handleMessage(byte[] message, int messageSize, int channel) {
            if(Arrays.equals(message,MPLSimulation.MSG_DISCONNECT_HERE))
                this.disconnect(channel);
            else if(Arrays.equals(message, MPLSimulation.MSG_LIFT_ME)) {
                //System.out.println("Blocking behavior received");
                lift = channel;
                synchronized(this) { this.notify(); }
            } else if(Arrays.equals(message, MPLSimulation.MSG_IS_ACTIVE_QUERY)) { 
                if(isActive)
                    this.sendMessage(MPLSimulation.MSG_CONFIRM_ACTIVE, (byte)1, (byte)channel);
                else
                    this.sendMessage(MPLSimulation.MSG_CONFIRM_PASSIVE, (byte)1, (byte)channel);
            } else
                handleMessageHook(message,channel);
        }
        
        protected void handleMessageHook(byte[] message, int channel) {
            throw new Error("Unknown message received");
        }

    };
}