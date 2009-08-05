package ussr.samples.ckbot;

import ussr.physics.jme.actuators.JMERotationalActuator;

public class CKBotDummyController extends CKBotController {
	
	public CKBotDummyController() {
	}
	
	public void activate() {
		while(module.getSimulation().isPaused()) Thread.yield();
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				
			}
		}
	}
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	System.out.println("Recieved message");
    }

}
