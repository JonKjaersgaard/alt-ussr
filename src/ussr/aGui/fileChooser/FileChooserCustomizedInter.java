package ussr.aGui.fileChooser;

import java.io.File;
import java.util.Map;

import ussr.aGui.FramesInter;
import ussr.aGui.fileChooser.controllers.FileChooserXMLController;
import ussr.aGui.fileChooser.views.FileChooserCustomizedOpen;
import ussr.aGui.fileChooser.views.FileChooserCustomizedSave;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;

/**
 * Supports different file choosers with common constants and methods.  
 * @author Konstantinas
 */
public interface FileChooserCustomizedInter extends FramesInter {

	/**
	 * Default file extension for file filter.
	 */
	public final String DEFAULT_FILE_EXTENSION = SaveLoadXMLFileTemplateInter.XML_EXTENSION;
	
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
     * A number of file choosers currently supported.
     */
    public final static FileChooserCustomizedInter FC_FRAME_OPEN_SIMULATION = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY),
                                              FC_FRAME_SAVE_SIMULATION = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY),
                                              FC_FRAME_OPEN_ROBOT = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY),
                                              FC_FRAME_SAVE_ROBOT = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY)    
                                              ;
	/**
	 * A number of titles for each type of file chooser appearance;
	 */
	public static final String OPEN_ROBOT_TITLE = "Load robot XML file", OPEN_SIMULATION_TITLE = "Open simulation XML file",
	                           SAVE_ROBOT_TITLE = "Save simulation XML file", SAVE_SIMULATION_TITLE = "Save simulation XML file";  
	/**
	 * Sets file extensions(with descriptions) for file chooser to filter.
	 * @param fileDescriptionsAndExtensions, map containing mapping of file description to file extension.
	 */
	public void setFileFiltersWithDescriptions(Map<String, String> fileDescriptionsAndExtensions);

	
	
	public FileFilterCustomized getSelectedFileFilter();
	
}
