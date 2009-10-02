package ussr.aGui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Konstantinas
 */
public abstract class GuiFrame extends javax.swing.JFrame {

     public Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    

     /**
	 * Sets the size of the frame equal to the screen size
	 */
     public  void setSizeEqualToScreen(javax.swing.JFrame frame){         
         frame.setSize((int)dimension.getWidth(),(int) dimension.getHeight());
     }


      /**
	 * Sets the size of the frame equal to the half of the screen size
	 */
     public  void setSizeHalfScreen(javax.swing.JFrame frame){
         frame.setSize((int)dimension.getWidth()/2,(int) dimension.getHeight()/2);
     }
     
     
     /**
 	 * Changes the look of component to generic (for all platforms)
 	 * @param awtComponent, the component for example frame
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
     
  
}

