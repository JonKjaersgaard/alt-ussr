/**
 * Run parameters: "name serverIP portNo" e.g. "wheel1 127.0.0.1 10001"  
 * 
 *   @author lamik06
 */
package ussr.samples.atron.network;

public class ATRONNetworkControllerMain {
	public static void main(String[] args) {
		ATRONNetworkController networkController = new ATRONNetworkController(args);
		networkController.loop();
	}
}

