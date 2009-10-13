package ussr.aGui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Konstantinas
 */
public abstract class GuiFrames extends javax.swing.JFrame implements GuiInter {


	/**
	 * The dimension of the screen of display
	 */
	public Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	

	public final int TOOLBAR_HEIGHT = 30;

	public final int PADDING = 30;

	public final int TAB_PANE_HEIGHT1 = 500;//THINK HERE
	public final int TAB_PANE_HEIGHT2 = 100;


	/**
	 *  Width of window. 
	 */
	//public static int windowWidth = 0;


	/**
	 * Sets the size of the frame equal to the screen size
	 */
	public  void setSizeFullScreen(javax.swing.JFrame frame, ArrayList<javax.swing.JComponent> components){         
		frame.setSize((int)SCREEN_DIMENSION.getWidth(),(int) SCREEN_DIMENSION.getHeight());
		for (int index =0;index<components.size();index++ ){
			components.get(index).setPreferredSize(new Dimension((int)SCREEN_DIMENSION.getWidth()-PADDING,components.get(index).getHeight()));
		}
		//windowWidth =(int)SCREEN_DIMENSION.getWidth()-PADDING; 
	}


	/**
	 * Sets the size of the frame equal to the half of the screen size
	 */
	public  void setSizeHalfScreen(javax.swing.JFrame frame){
		frame.setSize((int)SCREEN_DIMENSION.getWidth()/2,(int) SCREEN_DIMENSION.getHeight()/2);
	}


	public void setDimensionsOf(javax.swing.JFrame frame,int width, int height){
		frame.setSize(width,height);
	}
	
	/**
	 *  Height of window. 
	 */
	private int windowHeight=0;
	
	
	
	public void setSizeAccordingComponents(javax.swing.JFrame frame, ArrayList<javax.swing.JComponent> components){
		for (int  index =0;index<components.size();index++){
			windowHeight = windowHeight + components.get(index).getHeight();			
     	}
		frame.setSize((int)SCREEN_DIMENSION.getWidth()/2,windowHeight+2*PADDING);
		
	}

	/**
	 * Sets USSR icon in the top-left corner of the frame. 
	 * @param frame, the gui frame.
	 */
	public void setUSSRicon(javax.swing.JFrame frame){
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(DIRECTORY_USSR_ICON));
	}


	/**
	 * Changes the look of component to generic (for all platforms)
	 * @param awtComponent, the GUI component for example frame
	 */
	public void changeToSetLookAndFeel(Component awtComponent){
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
	 * Starts the windows(frames) of GUI.
	 * Follows Strategy pattern. 
	 */
	public abstract void activate();

	/**
	 * Initializes visual appearance of the frames(windows).
	 * Follows Strategy pattern.
	 */
	protected abstract void initComponents();

}

