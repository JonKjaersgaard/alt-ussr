/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.mtran;

import java.util.Random;

/**
 * A simple controller for the ODIN robot, ossilates OdinMuscles with a random start state 
 * 
 * @author david
 *
 */
public class MTRANCommTestController extends MTRANController {
	
	private static final float MTRAN_POS_0 = 0.01f;
	private static final float MTRAN_POS_1 = 1-MTRAN_POS_0;
    static Random rand = new Random(System.currentTimeMillis());
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    public MTRANCommTestController(String type) {
    	setBlocking(true);
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    private boolean gotMessage = false;
    public void activate() {
    	yield();
    	
    	System.out.println("MTRAN RUNNING "+module.getID());
    	    	
    	switch(module.getID()) {
    	case 1:
    	    this.rotateTo(MTRAN_POS_0, 1);
    	    while(this.isRotating(1)) yield();
    	    for(byte i=0; i<6; i++) this.sendMessage(new byte[] { 34 }, (byte)1, i);
    	    break;  
    	case 0:
    	    module.setColor(java.awt.Color.YELLOW);
    	    while(!gotMessage) yield();
    	    System.out.println("0 rotating");
    	    this.rotateTo(MTRAN_POS_0, 0);
    	    this.rotateTo(MTRAN_POS_0,1);
    	    break;
    	}
	}
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
        System.out.println("Module "+module.getID()+" got message");
        gotMessage = true;
   	}
}
