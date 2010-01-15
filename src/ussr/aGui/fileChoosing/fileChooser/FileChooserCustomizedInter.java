package ussr.aGui.fileChoosing.fileChooser;

import ussr.aGui.fileChoosing.FileChoosingInter;


/**
 * Supports different file choosers with common constants and methods.  
 * @author Konstantinas
 */
public interface FileChooserCustomizedInter extends FileChoosingInter {

	/**
	 * File extension descriptions.
	 */
	public final String DEFAULT_FILE_DESCRIPTION = "Simulation",
	                    ROBOT_FILE_DESCRIPTION = "Robot"; 
	 
    /**
     * A number of file choosers currently supported for Windows,Linux and Unix, however not for Mac.
     */
    public final static FileChooserCustomizedInter FC_FRAME_OPEN_SIMULATION = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY),
                                              FC_FRAME_SAVE_SIMULATION = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY),
                                              FC_FRAME_OPEN_ROBOT = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY),
                                              FC_FRAME_SAVE_ROBOT = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER,DEFAULT_DIRECTORY)    
                                              ;
		
	/**
	 * Returns the file filter selected by user.
	 * @return the file filter selected by user.
	 */
	public FileFilterCustomized getSelectedFileFilter();
	
}
