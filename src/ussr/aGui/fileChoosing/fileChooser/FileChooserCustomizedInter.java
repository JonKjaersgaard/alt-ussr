package ussr.aGui.fileChoosing.fileChooser;

import ussr.aGui.fileChoosing.FileChoosingWindowInter;


/**
 * Supports different file choosers with common constants and methods.  
 * @author Konstantinas
 */
public interface FileChooserCustomizedInter extends FileChoosingWindowInter {

	/**
	 * File extension descriptions.
	 */
	public final String DEFAULT_FILE_DESCRIPTION = "Simulation",
	                    ROBOT_FILE_DESCRIPTION = "Robot"; 
	 
    /**
     * A number of file choosers currently supported for Windows,Linux and Unix, however not for Mac.
     */
    public final static FileChooserCustomizedInter FC_OPEN_SIMULATION = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER),
                                              FC_SAVE_SIMULATION = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER),
                                              FC_OPEN_ROBOT = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER),
                                              FC_SAVE_ROBOT = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER)    
                                              ;
		
	/**
	 * Returns the file filter selected by user.
	 * @return the file filter selected by user.
	 */
	public FileFilterCustomized getSelectedFileFilter();
	
}
