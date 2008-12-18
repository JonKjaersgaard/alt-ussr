/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.tests;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.List;

import com.jme.math.Matrix3f;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Sensor;
import ussr.physics.jme.JMEModuleComponent;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRONController;
import ussr.util.LiveTopologyDumper;
import ussr.util.XMLTopologyWriter;

/**
 * A simple controller for an ATRON car that reports data from the proximity sensors
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONCarController1 extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	yield();
    	String name = module.getProperty("name");
    	if(name.startsWith("wheel1")) { 
    	    LiveTopologyDumper dumper = new LiveTopologyDumper(this.module);
    	    System.out.println("Live dump:");
    	    dumper.dump(new XMLTopologyWriter(new PrintWriter(System.out)));
    	}
    }
}
