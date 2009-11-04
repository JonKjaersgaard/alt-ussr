package ussr.aGui.fileChooser.views;

import java.util.Map;

import ussr.aGui.FramesInter;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;
import ussr.aGui.fileChooser.controllers.FileChooserXMLController;

public interface FileChooserFrameInter extends FramesInter {

	public final String DEFAULT_FILE_EXTENSION =".xml";
	
	public final String DEFAULT_FILE_DESCRIPTION = "Simulation",
	                    ROBOT_FILE_DESCRIPTION = "Robot"; 
	
	public final String DEFAULT_DIRECTORY = "samples/simulations",
	                    DIRECTORY_ROBOTS = "samples/robots";
	
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
