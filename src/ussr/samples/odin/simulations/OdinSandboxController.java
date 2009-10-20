/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

import java.util.Random;

import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinController;

/**
 * A simple controller for the ODIN robot, oscillates OdinMuscles with a random start state 
 * 
 * @author david
 *
 */
public class OdinSandboxController extends OdinController {
	
	static Random rand = new Random(System.currentTimeMillis());
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    public OdinSandboxController(String type) {
    	this.type =type;
    	timeOffset = 100*rand.nextFloat();
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	delay(1000);
    	if(type=="OdinMuscle") muscleControl();
    	if(type=="OdinBall") ballControl();
	}
    public void muscleControl() {
    	float lastTime = module.getSimulation().getTime();
    	while(true) {
            if(!GenericSimulation.getActuatorsAreActive()) { yield(); continue; }
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
    }
    public void ballControl() {
    	while(true) {
        	try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
   		if(message[0]=='r') setColor(1, 0, 0);
   		if(message[0]=='g') setColor(0, 1, 0);
   		if(message[0]=='b') setColor(0, 0, 1);
    }
}
