/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import java.util.logging.Level;

import com.jme.util.LoggingSystem;

/**
 * Container for various logging-related functionality in the simulator.
 * 
 * TODO: extend with a concept of a logging level
 * 
 * @author ups
 *
 */
public class PhysicsLogger {

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
        LoggingSystem.getLogger().setLevel( Level.WARNING );
    }

}
