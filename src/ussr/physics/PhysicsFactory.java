/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ussr.comm.CommunicationMonitor;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.robots.JMEATRONFactory;
import ussr.physics.jme.robots.JMEDefaultFactory;
import ussr.physics.jme.robots.JMEMTRANFactory;
import ussr.physics.jme.robots.JMEOdinFactory;
import ussr.samples.white.JMEWhiteFactory;
import ussr.util.TopologyWriter;
import ussr.physics.jme.robots.JMECKBotFactory;

/**
 * An factory for creating implementation-level objects used by higher-level parts of the
 * simulator (currently only the simulator itself).
 * 
 * @author ups
 * 
 */
public class PhysicsFactory {

	/**
	 * Options for creating the simulation
	 * @author ups
	 */
	public static class Options implements Cloneable {
	
		
		private boolean exitOnQuit = true;
		private TopologyWriter topologyWriter = new TopologyWriter() {
			public void addConnection(Module m1, Module m2) { ; }
			public void finish() { ; }
		};

		// Begin Horn
		//private Set<CommunicationMonitor> monitors = new HashSet<CommunicationMonitor>();
		// End Horn

		private Set<CommunicationMonitor> monitors = new HashSet<CommunicationMonitor>();
		private boolean startPaused = true;
		private boolean headless_nowindow = false;
		private boolean headless_nographics = false;
		private String resourceDirectory;
		private boolean saveWindowSettingOnExit = false;
		private int xPosition=-1;//default value to indicate that simulation window position was not yet set.
		private int yPosition=-1;//default value to indicate that simulation window position was not yet set.    

		public void set(Options other) {
			this.exitOnQuit = other.exitOnQuit;
			this.headless_nowindow = other.headless_nowindow;
			this.monitors = other.monitors;
			this.resourceDirectory = other.resourceDirectory;
			this.saveWindowSettingOnExit = other.saveWindowSettingOnExit;
			this.startPaused = other.startPaused;
			this.topologyWriter = other.topologyWriter;
		}

		public Options copy() { 
			try {
				return (Options)clone();
			} catch (CloneNotSupportedException e) {
				throw new Error("Internal error");
			} 
		}

		public boolean getExitOnQuit() {
			return exitOnQuit;
		}

		public void setExitOnQuit(boolean exitOnQuit) {
			this.exitOnQuit = exitOnQuit;
		}

		public TopologyWriter getTopologyWriter() {
			return topologyWriter;
		}

		public void setTopologyWriter(TopologyWriter writer) {
			this.topologyWriter = writer;
		}

		// Begin Horn
		/*
        public void addCommunicationMonitor(CommunicationMonitor monitor) {
            monitors.add(monitor);
        }
		 */
		// End Horn

		// Begin Horn
		/*
        public Set<CommunicationMonitor> getMonitors() {
            return monitors;
        }
		 */
		// End Horn

		public void addCommunicationMonitor(CommunicationMonitor monitor) {
			monitors.add(monitor);
		}
		public Set<CommunicationMonitor> getMonitors() {
			return monitors;
		}

		public void setStartPaused(boolean startPaused) {
			this.startPaused = startPaused;
		}

		public boolean getStartPaused() {
			return startPaused;
		}

		/**
		 * If headless the simulator will not draw graphics
		 */
		public void setHeadlessNoWindow(boolean headless) {
			this.headless_nowindow = headless;
			if(headless) this.headless_nographics = true; 
		}

		public boolean getHeadlessNoWindow() {
			return headless_nowindow;
		}

		public void setHeadlessNoGraphics(boolean headless) {
		    this.headless_nographics = headless;
		}
		
		public boolean getHeadlessNoGraphics() {
		    return headless_nographics;
		}

		public void setResourceDirectory(String homeDir) {
			resourceDirectory = homeDir;
		}

		public String getResourceDirectory() {
			return resourceDirectory;
		}

		public void setSaveWindowSettingOnExit(boolean saveWindowSettingOnExit) {
			this.saveWindowSettingOnExit = saveWindowSettingOnExit;
		}
		public boolean getSaveWindowSettingOnExit() {
			return saveWindowSettingOnExit;
		}

		/**
		 * Sets x and y positions of simulation window.
		 * @param x, x position of simulation window.
		 * @param y, y position of simulation window.
		 */
		public void setWindowPosition(int x, int y){
			this.xPosition = x;
			this.yPosition = y;
		}

		/**
		 * Returns x position of simulation window.
		 * @return x position of simulation window.
		 */
		public int getXPosition() {
			isNewWindowPositionSet();
			return xPosition;
		}

		/**
		 * Returns y position of simulation window.
		 * @return y position of simulation window.
		 */
		public int getYPosition() {
			isNewWindowPositionSet();
			return yPosition;		
		}

		/**
		 * Checks if simulation window position was set or not before trying to get it.
		 * @return true, for simulation window position was set.
		 */
		public boolean isNewWindowPositionSet(){
			if (xPosition ==-1||yPosition==-1){
				return false;
			}
			return true;
		}

	}

	private static final ModuleFactory[] INITIAL_FACTORIES = new ModuleFactory[] { new JMEATRONFactory(), new JMEOdinFactory(), new JMEMTRANFactory(), new JMECKBotFactory() };

	private static ArrayList<ModuleFactory> factories = new ArrayList<ModuleFactory>(Arrays.asList(INITIAL_FACTORIES));

	private static final Options options = new Options();

	/**
	 * Register a factory such that it can be used to create modules. 
	 * @param factory
	 */
	public synchronized static void addFactory(ModuleFactory factory) {
		factories.add(factory);
	}

	/**
	 * Create a new physics simulation.  Parameterized with the currently registered set of factories	
	 * and the current options.
	 * @return a new physics simulation
	 */
	public static PhysicsSimulation createSimulator() {
		return new JMESimulation(factories.toArray(INITIAL_FACTORIES),options.copy());
	}

	/**
	 * Register a default factory such that it can be used to create modules based
	 * on the robot description.
	 * @param robotPrefix the module type prefix that should use this factory 
	 * @see ussr.physics.jme.robots.JMEDefaultFactory
	 */
	public static void addDefaultFactory(String robotPrefix) {
		addFactory(new JMEDefaultFactory(robotPrefix));
	}

	/**
	 * Display debug information about the physics factory
	 * @return String representing the debug information
	 */
	public static String display() {
		StringBuffer result = new StringBuffer(PhysicsFactory.class.getName().toString()+"[ ");
		for(ModuleFactory factory: factories) {
			result.append(factory.getModulePrefix());
			result.append("* ");
		}
		result.append(']');
		return result.toString();
	}

	/**
	 * Obtain the options instance, used to set parameters for the simulator
	 * @return the options instance
	 */
	public static Options getOptions() {
		return options;
	}
}
