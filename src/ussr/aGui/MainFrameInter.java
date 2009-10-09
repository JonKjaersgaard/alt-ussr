package ussr.aGui;

import java.util.ArrayList;

import ussr.physics.jme.JMEBasicGraphicalSimulation;

public interface MainFrameInter {

	/**
	 * The directory for keeping jpg icons used in the GUI design.
	 */
	public final String DIRECTORY_ICONS = "resources/mainFrame/icons/jpg/";

	/**
	 * The names of the icons used in GUI
	 */
	public final String RUN_REAL_TIME = "runRealTime.jpg",RUN_FAST = "runFast.jpg", PAUSE = "pause.jpg", STEP_BY_STEP ="stepByStep.jpg",
	                    SAVE ="save.jpg", EXIT = "exit.jpg";
	
	
	/**
	 * Starts the main GUI window (frame) during the simulation.
	 * This can be achieved by pressing "O" on keyboard.
	 */
	public void activateDuringSimulation( );

	
}
