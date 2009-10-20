package ussr.aGui;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.ArrayList;
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
public abstract class GuiFrames extends javax.swing.JFrame implements FramesInter {

	/**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;	
	
	/**
	 *  Initial height of the frame. 
	 */
	private int frameHeight=0;	
	
	/**
	 * Sets the height of the frame according to the height of each its component.
	 * @param frame, the frame to set the height to. 
	 * @param width, desired width of the frame.
	 * @param components, JComponents influencing the height of the frame.
	 */
	public void setFrameHeightAccordingComponents(javax.swing.JFrame frame,int width, ArrayList<javax.swing.JComponent> components){
	
		for (int  index =0;index<components.size();index++){
			frameHeight = frameHeight + components.get(index).getHeight();			
     	}
		frame.setSize(width,frameHeight+2*PADDING);// add padding to cover for JMenuBar and distances between components. 
		
	}

	/**
	 * Sets USSR icon in the top-left corner of the frame. 
	 * @param frame, the GUI frame to set icon to.
	 */
	public void setUSSRicon(javax.swing.JFrame frame){
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
	public abstract void initComponents();
	
	/**
	 * Starts the windows(frames) of GUI.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();

}

