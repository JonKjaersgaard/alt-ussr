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

	private byte state = 0;
	private float goal0 = 0.5f, goal1 = 0.5f;
    
    public MTRANCommTestController(String type) {
    	//setBlocking(true);
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	yield();
    	
    	System.out.println("MTRAN RUNNING "+module.getID());
    	    	
    	loop: while(true) {
    	    switch(module.getID()) {
    	    case 0: behavior_0(); break;
    	    case 1: behavior_1(); break;
    	    case 2: behavior_2(); break;
    	    case 6: behavior_6(); break;
    	    default: defaultAction();
    	    }
    	}
    }
    
    public void behavior_0() {
        switch(state) {
        case 0:
            module.setColor(java.awt.Color.YELLOW);
            setState(1);
            break;
        case 3:
            System.out.println("Trying connect "+isConnected(0));
            this.connect(0);
        default:
            defaultAction();
        }
    }

    public void behavior_1() { }

    public void behavior_2() {
        switch(state) {
        case 1:
            doRotateTo(1, 0);
            doRotateTo(0, 1);
    	    setState(2);
    	    break;
        default:
            defaultAction();
        }
    }
    
    public void behavior_6() {
        switch(state) {
        case 2:
            doRotateTo(1,0);
            doRotateTo(0,1);
            while(this.isRotating(0)||this.isRotating(1)) yield();
            setState(3);
        default:
            defaultAction();
        }
    }
    
    public void setState(int state) {
        this.state = (byte)state;
        for(byte c=0; c<6; c++)
            super.sendMessage(new byte[] { 87, (byte)state }, (byte)2, c);
    }

    public void doRotateTo(float goal, int actuator) {
        if(actuator==0) goal0 = goal;
        else if(actuator==1) goal1 = goal;
        this.rotateToThroughMidpoint(goal, actuator);
    }

    public void defaultAction() {
        this.rotateToThroughMidpoint(goal0, 0);
        this.rotateToThroughMidpoint(goal1, 1);
    }
    
    public void handleMessage(byte[] message, int messageSize, int incoming) {
        if(message.length==2 && message[0]==87) {
            byte newState = message[1];
            if(newState>state) {
                state = newState;
                for(byte c=0; c<6; c++)
                    if(c!=incoming) super.sendMessage(new byte[] { 87, (byte)state }, (byte)2, c);
            }
        }
   	}
}
