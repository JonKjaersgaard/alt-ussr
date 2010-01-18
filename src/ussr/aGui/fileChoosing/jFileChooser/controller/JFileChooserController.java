package ussr.aGui.fileChoosing.jFileChooser.controller;

import javax.swing.JFileChooser;

import ussr.aGui.controllers.GeneralController;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;

/**
 * Defines common methods for controllers implemented to control file choosers.  
 * @author Konstantinas
 *
 */
public abstract class JFileChooserController extends GeneralController implements JFileChooserControllerInter  {

	/**
	 * XML file type supported in USSR (Simulation or Robot).
	 */
	protected UssrXmlFileTypes ussXmlFileType;
	
	 
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
