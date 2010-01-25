package ussr.aGui.fileChoosing.jFileChooser;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import ussr.aGui.GuiFrames;
import ussr.aGui.fileChoosing.jFileChooser.controller.JFileChooserControllerInter;


/**
 * Defines visual appearance of the file chooser in several dialog forms: Open and Save for now.
 * Limits the file choosing to extensions specified in the map of file descriptions and extensions.   
 * @author Konstantinas
 */

@SuppressWarnings("serial")
public abstract class JFileChooserCustomized extends JFileChooser implements JFileChooserCustomizedInter {
	
	/**
	 * The file chooser appearance, which is integrated into the frame.
	 */
	protected javax.swing.JFileChooser jFileChooserCustomized;

	/**
	 * Controller for file chooser.
	 */
	protected JFileChooserControllerInter fileChooserController;
	
	/**
	 * Map containing mapping of file description to file extension.
	 */
	protected Map<String, String> fileDescriptionsAndExtensions;
	
	
	/**
	 * Defines visual appearance of the file chooser in several dialog forms: Open and Save for now.
     * Limits the file choosing to extensions specified in the map of file descriptions and extensions. 
	 * @param fileDescriptionsAndExtensions,map containing mapping of file description to file extension.
	 * @param fileChooserController, controller for file chooser.
	 */
	public JFileChooserCustomized(Map<String, String> fileDescriptionsAndExtensions, JFileChooserControllerInter fileChooserController){
		this.fileDescriptionsAndExtensions= fileDescriptionsAndExtensions;
		this.fileChooserController= fileChooserController;
		initComponents();
	}
	
	/** 
	 * This method is called from within the constructor to initialize the form(frame) of the file chooser.
	 */	
	public void initComponents() {
     
    	try {
			jFileChooserCustomized = new javax.swing.JFileChooser(FILE_IN_CURRENT_DIRECTORY.getCanonicalPath().toString()+DEFAULT_RELATIVE_DIRECTORY);
		} catch (IOException e) {
			throw new Error("Failed to locate  default directory for storing XML files in USSR folder structure, named as: " + DEFAULT_RELATIVE_DIRECTORY);
		}    		
        jFileChooserCustomized.setAcceptAllFileFilterUsed(false);
        jFileChooserCustomized.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooserCustomized.setSize(new Dimension(580,450));
       
        GuiFrames.changeToLookAndFeel(jFileChooserCustomized);	
		
	}	


	
	/**
	 * Changes default file filters with ones specified in the map. 
	 */
	public void setFilesToFilterOutWithDescription(){
		Iterator<String> keyIterator = fileDescriptionsAndExtensions.keySet().iterator();
		Iterator<String> valueIterator = fileDescriptionsAndExtensions.values().iterator();
		while(keyIterator.hasNext()){
			jFileChooserCustomized.setFileFilter(new FileFilterCustomized (keyIterator.next(),valueIterator.next()));			
		}
	}
	
	/**
	 * Sets file extensions(with descriptions) for file chooser to filter.
	 * @param fileDescriptionsAndExtensions, map containing mapping of file description to file extension.
	 */
	public void setFileFiltersWithDescriptions(Map<String, String> fileDescriptionsAndExtensions){
		this.fileDescriptionsAndExtensions = fileDescriptionsAndExtensions;
	}
	
	/**
	 * Returns the file filter selected by user.
	 * @return the file filter selected by user.
	 */
	public FileFilterCustomized getSelectedFileFilter(){
		return (FileFilterCustomized) jFileChooserCustomized.getFileFilter();
	}
	
}
