package ussr.aGui;



/**
 * @author Konstantinas
 *
 */
public interface Gui {
	
	/**
	 * The directory for keeping jpg icons used in the GUI design.
	 */
	public final String DIRECTORY_ICONS = "resources/mainFrame/icons/jpg/";

	/**
	 * The names of the icons used in GUI
	 */
	public final String PLAY = "play.jpg", PAUSE = "pause.jpg";
	
/*	*//**
	 * Changes the look of component to generic (for all platforms)
	 * @param awtComponent, the GUI component for example frame
	 *//*
	public void changeToSetLookAndFeel(Component awtComponent);*/
	
	/**
 	 * Starts the windows(frames) of GUI.
 	 * Follows strategy pattern. 
 	 */
 	public abstract void activate();
	
	
}
