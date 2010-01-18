package ussr.aGui.fileChoosing.fileDialog;


import ussr.aGui.fileChoosing.FileChoosingInter;
import ussr.aGui.fileChoosing.fileDialog.controllers.FileDialogControllerInter;
import ussr.aGui.fileChoosing.fileDialog.controllers.FileDialogXMLController;


/**
 * Supports with several visual appearances of FileDialog window, from AWT. Was implemented 
 * to support native appearance of file choosing for Mac.
 * In particular, these are open and save forms for simulation and robot xml files.
 * @author Konstantinas
 *
 */
public interface FileDialogCustomizedInter extends FileChoosingInter  {

	/**
	 * Default controller for XML processing. 
	 */
	public static final  FileDialogControllerInter FD_XML_CONTROLLER = new FileDialogXMLController(); 
	 
	
	/**
     * A number of file dialog windows currently supported for Mac.
     */
    public static final  FileDialogCustomizedInter FD_OPEN_SIMULATION = new FileDialogCustomizedOpen(null,FileDialogTypes.OPEN_SIMULATION_XML,FD_XML_CONTROLLER),
                                                  FD_OPEN_ROBOT =  new FileDialogCustomizedOpen(null,FileDialogTypes.OPEN_ROBOT_XML,FD_XML_CONTROLLER),
                                                  FD_SAVE_SIMULATION = new FileDialogCustomizedSave(null,FileDialogTypes.SAVE_SIMULATION_XML,FD_XML_CONTROLLER),
                                                  FD_SAVE_ROBOT = new FileDialogCustomizedSave(null,FileDialogTypes.SAVE_ROBOT_XML,FD_XML_CONTROLLER);
                                                  
    /**
     * Returns the name of the file selected by user.
     * @return the name of the file selected by user.
     */
    public String getFileName();
    
    /**
     * Returns the directory selected by user. 
     * @return the directory selected by user.
     */
    public String getFileDirectory();
    
    /**
     * Sets the name of the file selected in file dialog window.
     * @param selectedFile, the name of the file selected in file dialog window.
     */
    public void setSelectedFile( String selectedFile);
    
    /**
     * Returns controller of the file dialog. 
     * @return controller of the file dialog. 
     */
    public FileDialogControllerInter getFileDialogController();
    
    /**
     * Returns the type of file dialog window.
     * @return the type of file dialog window.
     */
    public FileDialogTypes getFileDialogType(); 
	
}
