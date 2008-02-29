/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.odin;

import java.util.Random;

import ussr.model.ActBasedController;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the ODIN robot, oscillates OdinMuscles with a random start state 
 * 
 * @author david
 *
 */
public class OdinActBasedController extends OdinController implements ActBasedController {
	
	static Random rand = new Random(System.currentTimeMillis());
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    public OdinActBasedController(String type) {
    	this.type =type;
    	timeOffset = 100*rand.nextFloat();
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        initializationActStep();
        while(true) singleActStep();
	}
    public void muscleControl() {
        if(!GenericSimulation.getActuatorsAreActive()) { ussrYield(); return; }
        float time = module.getSimulation().getTime()+timeOffset;
        actuate((float)(Math.sin(time)+1)/2f);
        module.getSimulation().waitForPhysicsStep(false);
        if((lastTime+1)<module.getSimulation().getTime()) {
            if(color==0) msg[0] = 'r';
            if(color==1) msg[0] = 'g';
            if(color==2) msg[0] = 'b';
            color=(color+1)%3;
            sendMessage(msg, (byte)msg.length,(byte)0);
            sendMessage(msg, (byte)msg.length,(byte)1);
            lastTime = module.getSimulation().getTime();
        }
    }
    public void ballControl() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new Error("unexpected");
        }
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
   		if(message[0]=='r') setColor(1, 0, 0);
   		if(message[0]=='g') setColor(0, 1, 0);
   		if(message[0]=='b') setColor(0, 0, 1);
    }
    public boolean singleActStep() {
        if(type=="OdinMuscle") muscleControl();
        if(type=="OdinBall") ballControl();
        return true;
    }
    private float lastTime;
    public void initializationActStep() {
        while(module.getSimulation().isPaused()) Thread.yield();
        delay(1000);
        lastTime = module.getSimulation().getTime();
    }
}
