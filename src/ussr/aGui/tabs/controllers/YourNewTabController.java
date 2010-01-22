package ussr.aGui.tabs.controllers;

import javax.swing.AbstractButton;
import javax.swing.JButton;

/**
 * @author Konstantinas
 *
 */
public class YourNewTabController {

	private static final String COMMON_OUT= "Button pressed: ";
	/**
	 * Prints out the text on the button selected by user.
	 * @param jButtonStartNewRobot, the component in GUI.
	 */
	public static void jButtonStartNewRobotActionPerformed(JButton jButtonStartNewRobot) {
		System.out.println(COMMON_OUT+ jButtonStartNewRobot.getText());	
	}

	/**
	 * Prints out the text on the button selected by user.
	 * @param radionButtonATRON, the component in GUI.
	 */
	public static void radionButtonATRONActionPerformed(AbstractButton radionButtonATRON) {
		System.out.println(COMMON_OUT + radionButtonATRON.getText());
		
	}

	/**
	 * Prints out the text on the button selected by user.
	 * @param radioButtonMTRAN,the component in GUI.
	 */
	public static void radionButtonMTRANActionPerformed(
			AbstractButton radioButtonMTRAN) {
		System.out.println(COMMON_OUT + radioButtonMTRAN.getText());
		
	}

	/**
	 * Prints out the text on the button selected by user.
	 * @param radionButtonODIN,the component in GUI.
	 */
	public static void radionButtonOdinActionPerformed(
			AbstractButton radionButtonODIN) {
		System.out.println(COMMON_OUT + radionButtonODIN.getText());
		
	}

}
