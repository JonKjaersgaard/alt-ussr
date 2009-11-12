package ussr.aGui.fileChooser.controllers;



import ussr.aGui.GeneralController;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.physics.jme.JMESimulation;

public abstract class FileChooserController extends GeneralController implements FileChooserControllerInter  {

	protected JMESimulation jmeSimulation;
	
	protected UssrXmlFileTypes ussXmlFileType;
	
    public abstract void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	public abstract  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	
	public void checkFileDescription(String fileDescription){
		if(fileDescription.contains(UssrXmlFileTypes.ROBOT.toString().toLowerCase().replaceFirst("r", "R"))){
			ussXmlFileType =UssrXmlFileTypes.ROBOT;
		}else if (fileDescription.contains(UssrXmlFileTypes.SIMULATION.toString().toLowerCase().replaceFirst("s", "S"))){
			ussXmlFileType = UssrXmlFileTypes.SIMULATION;
		}
	}
	
}
