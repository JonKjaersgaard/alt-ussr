package ussr.aGui;

import ussr.physics.jme.JMEBasicGraphicalSimulation;

public interface MainFrameInter {

	/**
	 * Starts the main GUI window (frame) during the simulation.
	 * This can be achieved by pressing "O" on keyboard.
	 * @param simulation, the basic graphical simulation
	 */
	public void activateDuringSimulation(final JMEBasicGraphicalSimulation simulation);
	
}
