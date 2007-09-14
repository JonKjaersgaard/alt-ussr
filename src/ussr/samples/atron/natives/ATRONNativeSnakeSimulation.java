/**
 * 
 */
package ussr.samples.atron.natives;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONSnakeSimulation;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
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
