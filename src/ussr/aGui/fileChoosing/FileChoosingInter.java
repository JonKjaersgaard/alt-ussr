package ussr.aGui.fileChoosing;

import ussr.aGui.fileChoosing.fileChooser.controllers.FileChooserControllerInter;
import ussr.aGui.fileChoosing.fileChooser.controllers.FileChooserXMLController;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;

public interface FileChoosingInter {

	/**
	 * Default file extension for file filter.
	 */
	public final String DEFAULT_FILE_EXTENSION = SaveLoadXMLFileTemplateInter.XML_EXTENSION;
	
	
	/**
	 * Default directory for file choosers to open.
	 */
	public final String DEFAULT_DIRECTORY = "samples/simulations";
	
	
	/**
	 * Default controller for XML processing. 
	 */
	public final FileChooserControllerInter FC_XML_CONTROLLER = new FileChooserXMLController();
	
	/**
	 * A number of titles for each type of file chooser appearance;
	 */
	public static final String OPEN_ROBOT_TITLE = "Load robot XML file", OPEN_SIMULATION_TITLE = "Open simulation XML file",
	                           SAVE_ROBOT_TITLE = "Save simulation XML file", SAVE_SIMULATION_TITLE = "Save simulation XML file";  
	
	
	/**
	 * Starts the file choosing dialog windows.
	 * Follows strategy pattern. 
	 */
	public abstract void activate();
}
