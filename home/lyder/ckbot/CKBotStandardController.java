package ckbot;

public class CKBotStandardController extends CKBotController {

	public CKBotStandardController() {	
	}
	
	public void activate() {
		connectAll();
		while(module.getSimulation().isPaused()) Thread.yield();
		module.getActuators().get(0).activate(-1);
		while(true) {
    		try {
    			Thread.sleep(1000);
    			module.getActuators().get(0).activate(1);
                Thread.sleep(2000);
                module.getActuators().get(0).activate(-1);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
	}
	
    public void rotateTo(float goal) {
        do {
            module.getActuators().get(0).activate(goal);
            yield();
        } while(isRotating()&&blocking);
	}
    
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }

}
