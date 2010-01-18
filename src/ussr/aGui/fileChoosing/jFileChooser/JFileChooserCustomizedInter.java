package ussr.aGui.fileChoosing.jFileChooser;

import ussr.aGui.fileChoosing.FileChoosingInter;
import ussr.aGui.fileChoosing.jFileChooser.controller.FileChooserXMLController;
import ussr.aGui.fileChoosing.jFileChooser.controller.JFileChooserControllerInter;


/**
 * Supports with several visual appearances of JFileChooser form, from Swing.
 * In particular, there are open and save forms for simulation and robot xml files.   
 * @author Konstantinas
 */
public interface JFileChooserCustomizedInter extends FileChoosingInter {

	/**
	 * File extension descriptions.
	 */
	public static final String DEFAULT_FILE_DESCRIPTION = "Simulation",
	                    ROBOT_FILE_DESCRIPTION = "Robot"; 
	
	/**
	 * Default controller for XML processing. 
	 */
	public static final JFileChooserControllerInter FC_XML_CONTROLLER = new FileChooserXMLController();
	 
    /**
     * A number of file choosers currently supported for Windows,Linux and Unix(not tested), however not for Mac.
     */
    public static final  JFileChooserCustomizedInter FC_OPEN_SIMULATION = new JFileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER),
                                              FC_SAVE_SIMULATION = new JFileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER),
                                              FC_OPEN_ROBOT = new JFileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER),
                                              FC_SAVE_ROBOT = new JFileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER);
	/**
	 * Returns the file filter selected by user.
	 * @return the file filter selected by user.
	 */
	public abstract FileFilterCustomized getSelectedFileFilter();
	
}
