/**
 * 
 */
package mpl;

import ussr.model.Controller;

class ActiveATRON extends PassiveATRON {

    ActiveATRON(MPLSimulation simulation) {
        super(simulation);
    }

    public Controller createController() { return new ActiveController(); }

    protected class ActiveController extends PassiveController {

        @Override
        public void activate() {
            yield();
            String name = this.module.getProperty("name");
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

        private void blockingBehavior(String name) {
            System.out.println("Blocking behavior activated");
            this.disconnectOthersSameHemi(MPLSimulation.LIFTING_CONNECTOR);
            if(name.indexOf(MPLSimulation.SPINNER_TAG)>0)
                this.rotateContinuous(1);
            else if(name.indexOf(MPLSimulation.COUNTER_SPINNER_TAG)>0)
                this.rotateContinuous(-1);
            this.sendMessage(MPLSimulation.MSG_LIFT_ME, MPLSimulation.MSG_LIFT_ME_SIZE, MPLSimulation.LIFTING_CONNECTOR);
        }

    }
}