/**
 * 
 */
package ussr.samples.atron.natives;

import ussr.description.Robot;
import ussr.model.Controller;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.simulations.ATRONSnakeSimulation;

/**
 * Snake simulation using native controller
 * 
 * @author ups
 *
 */
public class ATRONNativeSnakeSimulation extends ATRONSnakeSimulation {
	public static void main(String argv[]) {
		new ATRONNativeSnakeSimulation().main();
	}

	@Override
	protected Robot getRobot() {
		return new ATRON() {
			public Controller createController() {
				return new ATRONNativeController("snakeController");
		}
	};
}
}
