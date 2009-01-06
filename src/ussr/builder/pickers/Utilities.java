package ussr.builder.pickers;

import java.util.Random;

/**
 * Contains methods frequently used for intermediate calculations and so on.
 * @author Konstantinas
 *
 */
public class Utilities {

	/**
	 * Gives the random integer from the assigned interval
	 * @param min, lowest limit
	 * @param max, highest limit
	 * @return, random integer
	 */
	public int randomIntFromInterval(int min, int max) {	
		return min + (new Random()).nextInt(max-min);
	}

}
