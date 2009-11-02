package ussr.aGui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * Supports any GUI frame implemented for USSR with common resources and operations.
 * @author Konstantinas
 */
public interface FramesInter {

	/**
	 * The directory for USSR icon.
	 */
	public final String DIRECTORY_USSR_ICON = "resources/mainFrame/icons/jpg/ussrIcon.jpg";
	
	/**
	 * Screen dimension.
	 */
	public Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * Dominating(common) height of most components.
	 */
	public final int COMMON_HEIGHT = 33;
	
	
	/**
	 * Width and height of buttons.
	 */
	public final int BUTTONS_WIDTH =30;
	
	/**
	 * Padding for adding space around components of the JFrame. 
	 */
	public final int PADDING = 30;
	
	/**
	 * Starts the windows(frames) of GUI.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();
	
	/**
	 * Initializes visual appearance of the frames(windows).
	 * Follows Strategy pattern.
	 */
	public abstract void initComponents();


}
