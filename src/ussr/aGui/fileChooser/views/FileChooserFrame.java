package ussr.aGui.fileChooser.views;
import java.util.Iterator;
import java.util.Map;

import ussr.aGui.GuiFrames;
import ussr.aGui.fileChooser.FileFilter;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;


/**
 * Defines visual appearance of the file chooser in several dialog forms: Open and Save for now.
 * Limits the file choosing to extensions specified in the map of file descriptions and extensions.   
 * @author Konstantinas
 */

@SuppressWarnings("serial")
public abstract class FileChooserFrame extends GuiFrames implements FileChooserFrameInter {

	
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
	public FileChooserFrame(Map<String, String> fileDescriptionsAndExtensions, FileChooserControllerInter fileChooserController,String defaultDirectory){
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
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);// do not allow for file chooser to maximized.
		getContentPane().setLayout(new java.awt.FlowLayout());			
		getContentPane().add(jFileChooserCustomized);
		jFileChooserCustomized.setAcceptAllFileFilterUsed(false);
		pack();
		changeToLookAndFeel(this);// for all platforms
		setSize(580,450);//THINK MORE HERE
	}	


	
	/**
	 * Changes default file filters with ones specified in the map. 
	 */
	public void setFilesToFilterOutWithDescription(){
		Iterator<String> keyIterator = fileDescriptionsAndExtensions.keySet().iterator();
		Iterator<String> valueIterator = fileDescriptionsAndExtensions.values().iterator();
		while(keyIterator.hasNext()){
			jFileChooserCustomized.setFileFilter(new FileFilter (keyIterator.next(),valueIterator.next()));			
		}
	}
	
	/**
	 * Sets specific default directory to open.
	 * @param defaultDirectory, the directory for file chooser to open as default.
	 */
	public void setDefaultDirectory(String defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
		
	}
	
	/**
	 * Sets file extensions(with descriptions) for file chooser to filter.
	 * @param fileDescriptionsAndExtensions, map containing mapping of file description to file extension.
	 */
	public void setFileFiltersWithDescriptions(Map<String, String> fileDescriptionsAndExtensions){
		this.fileDescriptionsAndExtensions = fileDescriptionsAndExtensions;
	}
	
}
