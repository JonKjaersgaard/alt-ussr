package rar;

import rar.EightToCarRobustnessBatch.Parameters;
import ussr.remote.facade.ParameterHolder;
import ussr.samples.atron.ATRONController;

public class ATRONStateMachineAPI extends ATRONController implements CommunicationProvider {
    private StateMachine machine;
    private boolean started = false;
    private ExperimentResultRegistrant registrant;
    
    public ATRONStateMachineAPI(StateMachine machine, ExperimentResultRegistrant registrant) {
        this.machine = machine;
        this.registrant = registrant;
        machine.stateManager.setCommunicationProvider(this);
        machine.setAPI(this);
    }
    
    @Override
    public void activate() {
        EightToCarRobustnessBatch.Parameters p = (EightToCarRobustnessBatch.Parameters)ParameterHolder.get();
        Integer seedMaybe = p.seedMaybe;
        super.setCommFailureRisk(p.minR, p.maxR, p.completeR, p.completeDegree, seedMaybe);
        started = true;
        machine.initialize(getMyID());
        machine.activate();
    }

    @Override
    public void handleMessage(byte[] message, int messageSize, int channel) {
        machine.stateManager.receive(message);
    }

    @Override
    public void broadcastMessage(byte[] message) {
        if(!started) return;
        for(int c=0; c<8; c++)
            this.sendMessage(message, (byte)message.length, (byte)c);
    }

    public void rotateDirToInDegrees(int to, boolean direction) {
        int real;
        switch(to) {
        case 0: real=180; break;
        case 108: real=90; break;
        case 216: real=0; break;
        case 324: real=270; break;
        default: throw new Error("Unknown rotation: "+to);
        }
        System.out.println("Rotating to "+to+" remapped to "+real);
        super.rotateDirToInDegrees(real, direction?-1:1);
    }

    @Override
    public int getAngularPositionDegrees() {
        return (int)(super.readEncoderPosition()*432);
    }

    
    protected int getMyID() {
        String id = this.getName().substring(1);
        return Integer.parseInt(id);
    }

    public void reportResult(boolean b) {
        registrant.reportResult(b);
    }
    
    public void reportEvent(String string, float time) {
        registrant.reportEvent(string,time);
    }
    public float getTime() {
        return module.getSimulation().getTime();
    }

}
