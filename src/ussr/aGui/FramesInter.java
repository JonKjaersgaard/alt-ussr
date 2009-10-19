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
	 * Height of tool bars.
	 */
	public final int TOOLBARS_HEIGHT = 30;
	
	/**
	 * Padding for adding space around components of the JFrame. 
	 */
	public final int PADDING = 30;
	
	/**
	 * Sets USSR icon in the top-left corner of the frame. 
	 * @param frame, the GUI frame to set icon to.
	 */
	public abstract void setUSSRicon(javax.swing.JFrame frame);

	/**
	 * Sets the height of the frame according to the height of each its component.
	 * @param frame, the frame to set the height to. 
	 * @param width, desired width of the frame.
	 * @param components, JComponents influencing the height of the frame.
	 */
	public abstract void setFrameHeightAccordingComponents(javax.swing.JFrame frame,int width, ArrayList<javax.swing.JComponent> components);

	/**
	 * Changes the look of component to generic (for all platforms)
	 * @param awtComponent, the GUI component for example frame
	 */
	public abstract void changeToLookAndFeel(Component awtComponent);

	/**
	 * Starts the windows(frames) of GUI.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();


}
