package ussr.aGui;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The main responsibility of the class is to support any GUI Frame implemented for USSR with
 * common methods.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public abstract class GuiFrames extends javax.swing.JFrame implements FramesInter {
	

	/**
	 * Sets USSR icon in the top-left corner of the frame. 
	 * @param frame, the GUI frame to set icon to.
	 */
	public static void setUSSRicon(javax.swing.JFrame frame){
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(DIRECTORY_USSR_ICON));
	}


	/**
	 * Changes the look of component to generic (for all platforms).
	 * @param awtComponent, the GUI component for example frame.
	 */
	public static void changeToLookAndFeel(Component awtComponent){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(awtComponent);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Initializes visual appearance of the frames(windows).
	 * Follows Strategy pattern.
	 */
	protected abstract void initComponents();
	
	/**
	 * Starts the windows(frames) of GUI.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();

}

