/**
 * 
 */
package mpl;

import java.util.Arrays;

import ussr.model.Controller;

class ActiveATRON extends PassiveATRON {

    ActiveATRON(MPLSimulation simulation) {
        super(simulation);
        isActive = true;
    }

    public Controller createController() { return new ActiveController(); }

    protected class ActiveController extends PassiveController {

        @Override
        public void activate() {
            yield();
            String name = this.module.getProperty("name");
            if(hasActiveNeighbor()) {
                System.out.println("Safety: aborting active behavior due to overcrowding for "+name);
                if(name.indexOf(MPLSimulation.BLOCKER_TAG)>0)
                    decrementMagicCounter();
                return;
            }
            System.out.println("Activating "+name);
            //System.out.println("Disconnecting "+name);
            if(isConnected(4)||isConnected(5)||isConnected(6)||isConnected(7)) {
                this.symmetricDisconnect(0);
                this.symmetricDisconnect(1);
                this.symmetricDisconnect(2);
                this.symmetricDisconnect(3);
                while(isConnected(0)||isConnected(1)||isConnected(2)||isConnected(3)) yield();
                if(name.indexOf(MPLSimulation.CLOCKWISE_TAG)>0 || name.indexOf(MPLSimulation.COUNTERCW_TAG)>0) {
                    synchronized(ActiveATRON.this.simulation.getMagicGlobalLiftingModuleSignal()) {
                        while(ActiveATRON.this.simulation.getMagicGlobalLiftingModuleCounter()>0)
                            try {
                                ActiveATRON.this.simulation.getMagicGlobalLiftingModuleSignal().wait();
                            } catch(InterruptedException exn) {
                                throw new Error("Unexpected interruption");
                            }
                    }
                    if(name.indexOf(MPLSimulation.CLOCKWISE_TAG)>0)
                        this.rotateContinuous(1);
                    else
                        this.rotateContinuous(-1);
                }
                else if(name.indexOf(MPLSimulation.BLOCKER_TAG)>0)
                    blockingBehavior(name);
                else throw new Error("No behavior for "+name);
            } else {
                System.out.println("Safety: avoiding disconnected for "+name);
                if(name.indexOf(MPLSimulation.BLOCKER_TAG)>0)
                    decrementMagicCounter();
            }
        }

        private int unknownNeighborCount = 0;
        private int activeNeighborCount = 0;
        private boolean hasActiveNeighbor() {
            for(int c=0; c<8; c++) {
                if(this.isConnected(c)) {
                    this.sendMessage(MPLSimulation.MSG_IS_ACTIVE_QUERY, (byte)1, (byte)c);
                    unknownNeighborCount++;
                }
            }
            while(unknownNeighborCount>0) {
                synchronized(this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new Error("Unexpected interruption");
                    }
                }
            }
            return activeNeighborCount >0;
        }

        @Override
        protected void handleMessageHook(byte[] message, int c) {
            if(Arrays.equals(message,MPLSimulation.MSG_CONFIRM_ACTIVE)) {
                synchronized(this) {
                    activeNeighborCount++;
                    unknownNeighborCount--;
                    this.notify();
                }
            } else if(Arrays.equals(message,MPLSimulation.MSG_CONFIRM_PASSIVE)) {
                synchronized(this) {
                    unknownNeighborCount--;
                    this.notify();
                }
            } else
                throw new Error("Unknown message received");
        }
        
        private void blockingBehavior(String name) {
            this.disconnectOthersSameHemi(MPLSimulation.LIFTING_CONNECTOR);
            if(name.indexOf(MPLSimulation.SPINNER_TAG)>0)
                this.rotateContinuous(1);
            else if(name.indexOf(MPLSimulation.COUNTER_SPINNER_TAG)>0)
                this.rotateContinuous(-1);
            this.sendMessage(MPLSimulation.MSG_LIFT_ME, MPLSimulation.MSG_LIFT_ME_SIZE, MPLSimulation.LIFTING_CONNECTOR);
        }

    }
}