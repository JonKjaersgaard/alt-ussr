package ussr.aGui.fileChooser.controllers;

import javax.swing.JFileChooser;
import ussr.aGui.GeneralController;
import ussr.aGui.fileChooser.FileChooserControllerInter;
import ussr.aGui.fileChooser.FileChooserCustomizedInter;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;

/**
 * Defines common methods for controllers implemented to control file choosers.  
 * @author Konstantinas
 *
 */
public abstract class FileChooserController extends GeneralController implements FileChooserControllerInter  {

	/**
	 * XML file type supported in USSR (Simulation or Robot).
	 */
	protected UssrXmlFileTypes ussXmlFileType;
	
	 /**
     * Manages the control of the file chooser in Save dialog form.
     * @param evt, event received from file chooser. This is selection of Save or Cancel buttons.
     * @param fileChooser, the file chooser appearance, which is integrated into the frame.
     * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
     */
    public abstract void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,FileChooserCustomizedInter fileChooserFrame);
	
    /**
	 * Manages the control of the file chooser in Open dialog form.
	 * @param evt, event received from file chooser. This is selection of Open or Cancel buttons.
	 * @param fileChooser,the file chooser appearance, which is integrated into the frame.
	 * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
	 */
	public abstract  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,FileChooserCustomizedInter fileChooserFrame);
	
	
	/**
	 * Sets the xml file type to control depending on file filter chosen in file chooser  by user.
	 * @param fileChooser, the file chooser dialog form.
	 */
	public void checkFileDescription(JFileChooser fileChooser){
		String fileDescription = fileChooser.getFileFilter().getDescription();
		if(fileDescription.contains(UssrXmlFileTypes.ROBOT.toString().toLowerCase().replaceFirst("r", "R"))){
			ussXmlFileType =UssrXmlFileTypes.ROBOT;
		}else if (fileDescription.contains(UssrXmlFileTypes.SIMULATION.toString().toLowerCase().replaceFirst("s", "S"))){
			ussXmlFileType = UssrXmlFileTypes.SIMULATION;
		}
	}
	
}
