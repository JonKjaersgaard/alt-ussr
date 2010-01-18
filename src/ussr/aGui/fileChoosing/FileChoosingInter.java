package ussr.aGui.fileChoosing;

import java.io.File;

import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;

/**
 * Supports file choosing with two slightly differing forms of implementation for: 1) JFileChooser from Swing and 2) FileDialog from  AWT libraries.
 * The reason why there are two implementations is that  JFileChooser is well supported for Windows and Linux platforms,
 * however there are problems with (look and feel) and file filtering on Macintosh(Mac). It seems that FileDialog do not have these problems 
 * on Mac. The disadvantage is "double coding".
 * FUTURE SOLUTIONS: It is possible to implement custom look and feel(too time consuming at this moment) and second is to wait
 * until JFileChooser will be better supported on Mac. 
 * @author Konstantinas
 *
 */
public interface FileChoosingInter {

	/**
	 * Default file extension for file filter.
	 */
	public static final String DEFAULT_FILE_EXTENSION = SaveLoadXMLFileTemplateInter.XML_EXTENSION;
	
	/**
	 * Default directory for file choosers to open.
	 */
	public static final String DEFAULT_DIRECTORY = "\\samples\\simulations";
	
	/**
	 * Used as a reference to determine current directory;
	 */
	public  static final File FILE_IN_CURRENT_DIRECTORY =  new File(".");
	
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
