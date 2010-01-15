package ussr.aGui.fileChooser.views;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import ussr.aGui.GuiFrames;
import ussr.aGui.fileChooser.FileChooserControllerInter;
import ussr.aGui.fileChooser.FileChooserCustomizedInter;
import ussr.aGui.fileChooser.FileFilterCustomized;


/**
 * Defines visual appearance of the file chooser in several dialog forms: Open and Save for now.
 * Limits the file choosing to extensions specified in the map of file descriptions and extensions.   
 * @author Konstantinas
 */

@SuppressWarnings("serial")
public abstract class FileChooserCustomized extends JFileChooser implements FileChooserCustomizedInter {

	/**
	 * Used as flag to indicate that file chooser should open build in default directory.
	 */
	protected String defaultDirectory = "";
	
	/**
	 * The file chooser appearance, which is integrated into the frame.
	 */
	protected javax.swing.JFileChooser jFileChooserCustomized;

	/**
	 * Controller for file chooser.
	 */
	protected FileChooserControllerInter fileChooserController;
	
	/**
	 * Map containing mapping of file description to file extension.
	 */
	protected Map<String, String> fileDescriptionsAndExtensions;
	
	
	/**
	 * Defines visual appearance of the file chooser in several dialog forms: Open and Save for now.
     * Limits the file choosing to extensions specified in the map of file descriptions and extensions. 
	 * @param fileDescriptionsAndExtensions,map containing mapping of file description to file extension.
	 * @param fileChooserController, controller for file chooser.
	 * @param defaultDirectory, default directory to open.
	 */
	public FileChooserCustomized(Map<String, String> fileDescriptionsAndExtensions, FileChooserControllerInter fileChooserController,String defaultDirectory){
		this.fileDescriptionsAndExtensions= fileDescriptionsAndExtensions;
		this.fileChooserController= fileChooserController;
		this.defaultDirectory =defaultDirectory;
		initComponents();
	}
	
	/** 
	 * This method is called from within the constructor to initialize the form(frame) of the file chooser.
	 */	
	public void initComponents() {
       if (defaultDirectory.isEmpty()){
		jFileChooserCustomized = new javax.swing.JFileChooser();
       }else{
    	   jFileChooserCustomized = new javax.swing.JFileChooser(defaultDirectory);    	   
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
