/**
 * 
 */
package morphingProductionLine.abstractConveyor;

import java.util.Arrays;

import ussr.model.Controller;

class ActiveATRON extends PassiveATRON {

    ActiveATRON(AbstractMPLSimulation simulation) {
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
                if(isBlockerModule(name))
                    decrementMagicCounter();
                return;
            }
            System.out.println("Activating "+name);
            if(passiveSideIsConnected()) {
                disconnectActiveSide();
                if(isRotatorModule(name))
                    rotatorBehavior(name);
                else if(isBlockerModule(name))
                    blockingBehavior(name);
                else throw new Error("No behavior for "+name);
            } else {
                System.out.println("Safety: avoiding disconnected for "+name);
                if(isBlockerModule(name))
                    decrementMagicCounter();
            }
        }

        protected boolean isBlockerModule(String name) {
            return name.indexOf(AbstractMPLSimulation.BLOCKER_TAG)>0;
        }

        protected void rotatorBehavior(String name) throws Error {
            {
                synchronized(ActiveATRON.this.simulation.getMagicGlobalLiftingModuleSignal()) {
                    while(ActiveATRON.this.simulation.getMagicGlobalLiftingModuleCounter()>0)
                        try {
                            ActiveATRON.this.simulation.getMagicGlobalLiftingModuleSignal().wait();
                        } catch(InterruptedException exn) {
                            throw new Error("Unexpected interruption");
                        }
                }
                if(name.indexOf(AbstractMPLSimulation.CLOCKWISE_TAG)>0)
                    this.rotateContinuous(1);
                else
                    this.rotateContinuous(-1);
            }
        }

        protected boolean isRotatorModule(String name) {
            return name.indexOf(AbstractMPLSimulation.CLOCKWISE_TAG)>0 || name.indexOf(AbstractMPLSimulation.COUNTERCW_TAG)>0;
        }

        protected void disconnectActiveSide() {
            this.symmetricDisconnect(0);
            this.symmetricDisconnect(1);
            this.symmetricDisconnect(2);
            this.symmetricDisconnect(3);
            while(isConnected(0)||isConnected(1)||isConnected(2)||isConnected(3)) yield();
        }

        protected boolean passiveSideIsConnected() {
            return isConnected(4)||isConnected(5)||isConnected(6)||isConnected(7);
        }

        private int unknownNeighborCount = 0;
        private int activeNeighborCount = 0;
        private boolean hasActiveNeighbor() {
            for(int c=0; c<8; c++) {
                if(this.isConnected(c)) {
                    this.sendMessage(AbstractMPLSimulation.MSG_IS_ACTIVE_QUERY, (byte)1, (byte)c);
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
            if(Arrays.equals(message,AbstractMPLSimulation.MSG_CONFIRM_ACTIVE)) {
                synchronized(this) {
                    activeNeighborCount++;
                    unknownNeighborCount--;
                    this.notify();
                }
            } else if(Arrays.equals(message,AbstractMPLSimulation.MSG_CONFIRM_PASSIVE)) {
                synchronized(this) {
                    unknownNeighborCount--;
                    this.notify();
                }
            } else
                throw new Error("Unknown message received");
        }
        
        private void blockingBehavior(String name) {
            this.disconnectOthersSameHemi(AbstractMPLSimulation.LIFTING_CONNECTOR);
            if(name.indexOf(AbstractMPLSimulation.SPINNER_TAG)>0)
                this.rotateContinuous(1);
            else if(name.indexOf(AbstractMPLSimulation.COUNTER_SPINNER_TAG)>0)
                this.rotateContinuous(-1);
            this.sendMessage(AbstractMPLSimulation.MSG_LIFT_ME, AbstractMPLSimulation.MSG_LIFT_ME_SIZE, AbstractMPLSimulation.LIFTING_CONNECTOR);
        }

    }
}