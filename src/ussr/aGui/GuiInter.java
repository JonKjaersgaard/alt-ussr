package ussr.aGui;



/**
 * @author Konstantinas
 *
 */
public interface GuiInter {
	
	
	
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
