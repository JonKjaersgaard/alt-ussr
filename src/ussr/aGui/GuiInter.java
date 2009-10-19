package ussr.aGui;

import java.awt.Component;



/**
 * @author Konstantinas
 *
 */
public interface GuiInter {

	/**
	 * USSR icon.
	 */
	public final String DIRECTORY_USSR_ICON = "resources/mainFrame/icons/jpg/ussrIcon.jpg";


	/**
	 * Changes the look of component to generic (for all platforms)
	 * @param awtComponent, the GUI component for example frame
	 */
	public void changeToSetLookAndFeel(Component awtComponent);

	/**
	 * Starts the windows(frames) of GUI.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();


}
