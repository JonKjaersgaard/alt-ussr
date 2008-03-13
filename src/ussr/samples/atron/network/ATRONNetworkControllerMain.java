/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.network;

public class ATRONNetworkControllerMain {
	public static void main(String[] args) {
		ATRONNetworkController networkController = new ATRONNetworkController(args);
		networkController.loop();
	}
}

