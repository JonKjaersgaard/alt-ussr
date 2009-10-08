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
	                    SAVE ="save.jpg";
	
	
	/**
	 * Starts the main GUI window (frame) during the simulation.
	 * This can be achieved by pressing "O" on keyboard.
	 * @param simulation, the basic graphical simulation
	 */
	public void activateDuringSimulation(final JMEBasicGraphicalSimulation simulation, final ArrayList<String> namesTabs );
	
	public void addTab(String tabName);
	
}
