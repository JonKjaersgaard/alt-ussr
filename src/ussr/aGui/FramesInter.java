package ussr.aGui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.border.Border;


/**
 * Supports any GUI frame implemented for USSR with common resources and operations.
 * @author Konstantinas
 */
public interface FramesInter {

	/**
	 * Directory for USSR icon.
	 */
	public final String DIRECTORY_USSR_ICON = "resources/mainFrame/icons/jpg/ussrIcon.png";

	/**
	 * Title for USSR simulator.
	 */
	public final String USSR_TITLE = "Unified Simulator for Self-Reconfigurable Robots";

	/**
	 * Screen(display) size.
	 */
	public Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Width of the screen(display). Fails to work on several displays and is covering task bar.
	 */
	public final int SCREEN_WIDTH = (int)SCREEN_SIZE.getWidth();

	/**
	 * Height of the screen(display). Fails to work on several displays and is covering task bar.
	 */
	public final int SCREEN_HEIGHT = (int)SCREEN_SIZE.getHeight();

	/**
	 * Bounds of the screen (not including task bar). Works even with several displays.
	 */
	public final Rectangle MAX_SCREEN_VIABLE_BOUNDS = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

	/**
	 * Width and height of the screen (not including task bar).Works even with several displays.
	 */
	public final double SCREEN_VIABLE_WIDTH = MAX_SCREEN_VIABLE_BOUNDS.getWidth(),
	SCREEN_VIABLE_HEIGHT = MAX_SCREEN_VIABLE_BOUNDS.getHeight();

	/**
	 * Height of menu bar.
	 */
	public final int MENU_BAR_HEIGHT = 20;

	/**
	 * Horizontal and vertical gaps between components of the frame. 
	 */
	public final int HORIZONTAL_GAPS = 3,
	VERTICAL_GAPS = 3;
	/**
	 * The height of the toolbar oriented horizontally.
	 */
	public final int HORIZONTAL_TOOLBAR_HEIGHT = 30;

	/**
	 * Numerical representation of window in maximized state. 
	 */
	public final int WINDOW_MAXIMIZED_STATE = 6;

	/**
	 * Numerical representation of window in restored down to its initial state. 
	 */
	public final int WINDOW_RESTORED_STATE = 0;

	/**
	 * Numerical representation of window in minimized state. 
	 */
	public final int WINDOW_MINIMIZED_STATE = 1;

	/**
	 * Dominating dimension of buttons with icons.
	 */
	public final Dimension BUTTON_DIMENSION = new Dimension (30,30);

	/**
	 * Dominating tool bar border of type raised.
	 */
	public final Border TOOLBAR_BORDER = javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED);

	/**
	 * Starts the windows(frames) of GUI.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();


}
