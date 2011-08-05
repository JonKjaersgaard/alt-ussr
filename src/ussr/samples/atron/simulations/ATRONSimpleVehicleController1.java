/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleVehicleController1 extends ATRONController {
	
    /**
     * Constants for communication
     */
    private static final byte MSG_OBJECT_NEARBY = 1;
    private static final byte MSG_OBJECT_GONE = 2;
    /**
     * Seconds to wait while going in reverse
     */
    private static final float WAIT_TIME = 4;

    /**
     * Messages used for communication
     */
    private static final byte[] MESSAGE_OBJECT_NEARBY = new byte[] { MSG_OBJECT_NEARBY };
    private static final byte[] MESSAGE_OBJECT_GONE = new byte[] { MSG_OBJECT_GONE};

    /**
     * Direction the wheels are rotation (-1 = forwards)
     */
    private float dir = -1;
    /**
     * Boolean indicating the current behavior of this module
     */
    private boolean goingForwards = true;
    /**
     * Time at which reversing started (to keep track of when it should stop)
     */
    private float startReverseTime = 0;
    /**
     * Name, used to identify the module
     */
    private String name;
    /**
     * @see ussr.model.ControllerImpl#activate()
     */

    /**
     * Main method for each module
     */
    public void activate() {
        // Setup, initialization
    	setup();
        name = module.getProperty("name");
        if(name.equals("driver0")) {
            module.getSensors().get(1).setSensitivity(0.5f);
            module.getSensors().get(3).setSensitivity(0.5f);
        }
        // Continuous behavior
        while(true) {
            if(name.contains("Right")) rotateContinuous(dir);
            if(name.contains("Left")) rotateContinuous(-dir);
            if(name.equals("driver0")) {
                driverBehavior();
            }
            yield();
        }
    }

    /**
     * Behavior activated for front ("driver") module: while going forwards check for proximity,
     * while going backwards check to see when we should stop doing it again
     */
    private void driverBehavior() {
        if(goingForwards) {
            checkForProximity(1);
            checkForProximity(3);
        } else if(module.getSimulation().getTime()>startReverseTime+WAIT_TIME){
            goingForwards = true;
            for(int k=0; k<8; k++) super.sendMessage(MESSAGE_OBJECT_GONE, (byte)MESSAGE_OBJECT_GONE.length, (byte)k);
        }
    }

    /**
     * Check for proximity on a specific connector of the driver module, if there is react by
     * resetting the timer and sending out messages that make the modules start to reverse
     * @param i
     */
    private void checkForProximity(int i) {
        if(isObjectNearby(i)) {
            System.out.println("there is an object nearby "+module.getSensors().get(i).readValue());
            for(int k=0; k<8; k++) super.sendMessage(MESSAGE_OBJECT_NEARBY, (byte)MESSAGE_OBJECT_NEARBY.length, (byte)k);
            goingForwards = false;
            startReverseTime = module.getSimulation().getTime();
        }
    }

    /**
     * Handler invoked when a message arrives, directly reverses direction depending on module identity or
     * resets when done reversing
     */
    @Override
    public void handleMessage(byte[] message, int messageSize, int channel) {
        if(message.length>0) {
            if(message[0]==MSG_OBJECT_NEARBY) {
                if(name.contains("Right"))
                    dir = 1;
                else
                    dir = 0.5f;
            }
            else if(message[0]==MSG_OBJECT_GONE) dir = -1;
            else System.out.println("Warning: did not understand message");
        }
    }
    
}
