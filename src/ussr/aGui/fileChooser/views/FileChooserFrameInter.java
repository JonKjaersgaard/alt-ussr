package ussr.aGui.fileChooser.views;

import java.util.Map;

import ussr.aGui.FramesInter;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;
import ussr.aGui.fileChooser.controllers.FileChooserXMLController;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;

/**
 * Supports different file choosers with common constants and methods.  
 * @author Konstantinas
 */
public interface FileChooserFrameInter extends FramesInter {

	/**
	 * Default file extension for file filter.
	 */
	public final String DEFAULT_FILE_EXTENSION = SaveLoadXMLFileTemplate.XML_EXTENSION;
	
	/**
	 * File extension descriptions.
	 */
	public final String DEFAULT_FILE_DESCRIPTION = "Simulation",
	                    ROBOT_FILE_DESCRIPTION = "Robot"; 
	
	/**
	 * Default directory for file choosers to open.
	 */
	public final String DEFAULT_DIRECTORY = "samples/simulations";
	         
	
	/**
	 * Default controller for XML processing. 
	 */
	public final FileChooserControllerInter FC_XML_CONTROLLER = new FileChooserXMLController();
	

	/**
	 * Sets specific default directory to open.
	 * @param defaultDirectory, the directory for file chooser to open as default.
	 */	
	public void setDefaultDirectory(String defaultDirectory);
	
	/**
	 * Sets file extensions(with descriptions) for file chooser to filter.
	 * @param fileDescriptionsAndExtensions, map containing mapping of file description to file extension.
	 */
	public void setFileFiltersWithDescriptions(Map<String, String> fileDescriptionsAndExtensions);
}
