package ussr.aGui.fileChooser.controllers;

import javax.swing.JFileChooser;
import ussr.aGui.GeneralController;
import ussr.builder.enumerations.UssrXmlFileTypes;


/**
 * @author Konstantinas
 *
 */
public abstract class FileChooserController extends GeneralController implements FileChooserControllerInter  {

	protected UssrXmlFileTypes ussXmlFileType;
	
    public abstract void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	public abstract  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	
	/**
	 * Sets the xml file type control depending on file filter chosen in file chooser  by user.
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
