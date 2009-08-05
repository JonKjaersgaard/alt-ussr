package ussr.samples.ckbot;

import ussr.physics.jme.actuators.JMERotationalActuator;

public class CKBotSpringSampleController1 extends CKBotController {
	float next_angle = 30;
	boolean newmessage = false;
	
	public CKBotSpringSampleController1() {
	}
	
	public void activate() {
		while(module.getSimulation().isPaused()) Thread.yield();
		
		while(true) {
			try {
				//sendMessage(new byte[] {'a'},(byte)4,(byte)0);
				//while(!newmessage) yield();
				Thread.sleep(1000);
				goToPosition(45);
				while(module.getSimulation().getTime()%5 > 0.5);
				System.out.println("Module #: " + module.getID() + "Time: " + module.getSimulation().getTime());
				windupSpring(0);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	public void waitForIt() {
		while(module.getSimulation().getTime()%10000 < 10) yield();
	}
	
	/*public void goToPosition(float angle) {
		float current = getEncoderPosition();
		float goal = 0.5f + (0.5f*angle)/90f;
		//System.out.println("Go to angle: " + angle);
		do {
		  if (goal > current) module.getActuators().get(0).activate(1);
		  else module.getActuators().get(0).activate(-1);
		  Thread.yield();
		  current = getEncoderPosition();
		} while(Math.abs(goal-current) > 0.001f);
		module.getActuators().get(0).disactivate();
	}*/
	
	public void goToPosition(float angle) {
		float current = getEncoderPosition();
		float goal = 0.5f + (0.5f*angle)/90f;
		if (goal > current) rotateTo(goal);
		else rotateTo(-goal);
	}
	
	public void windupSpring(float angle) {
		//System.out.println("Release!");
		((JMERotationalActuator) (module.getActuators().get(0).getPhysics().get(0))).setControlParameters(10f, 5, (float)-Math.PI/2, (float)Math.PI/2);
		goToPosition(angle);
		((JMERotationalActuator) (module.getActuators().get(0).getPhysics().get(0))).setControlParameters(1f, 1, (float)-Math.PI/2, (float)Math.PI/2);
	}
	
    public float getEncoderPosition() {
    	return (float)(module.getActuators().get(0).getEncoderValue());
    }
	
    public void rotateTo(float goal) {
        do {
            module.getActuators().get(0).setDesiredPosition(goal);
            yield();
        } while(isRotating()&&blocking);
	}
    
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	switch(message[0]) {
    	case 'a': next_angle=80; 
    		System.out.println("Module# " + module.getID() + "- Set angle=80"); break;
    	case 's': next_angle=25; 
    		System.out.println("Module# " + module.getID() + "- Set angle=25"); break;
    	default: System.out.println("Module# " + module.getID() + "- Unknown message"); break;
    	}
    	newmessage = true;
    }

}
