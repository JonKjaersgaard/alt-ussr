/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;


/**
 * Container for various logging-related functionality in the simulator.
 * 
 * TODO: extend with a concept of a logging level
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class PhysicsLogger {
    private static boolean displayInfo = false;
    

    /**
     * Log an issue that may be of critical importance
     * @param string the log message
     */
    public static void log(String string) {
        System.err.println("LOG: "+string);        
    }

    /**
     * Log an issue not of critical importance
     * @param string the log message
     */
    public static void logNonCritical(String string) {
        ;        
    }

    /**
     * Set the logging level to the default level
     */
    public static void setDefaultLoggingLevel() {
        //TODO: changed thereLoggingSystem.getLogger().setLevel( Level.WARNING );
    }

    /**
     * Display information (not errors) about the running simulation 
     * @param string the information to display
     */
    public static void displayInfo(String string) {
        if(displayInfo) System.out.println("INFO: "+string);
    }
    
    /**
     * Select whether or not to display information about the running simulation
     * @param display true if information should be displayed, false otherwise
     * @see #displayInfo
     */
    public static void setDisplayInfo(boolean display) {
        displayInfo = display;
    }

}
