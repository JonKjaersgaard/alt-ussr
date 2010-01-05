package ussr.aGui.fileChooser.controllers;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ussr.aGui.fileChooser.FileChooserFrameInter;
import ussr.builder.enumerations.UssrXmlFileTypes;

/**
 * Controls the functionality of both forms of file chooser: Open and Save for XML processing.
 * @author Konstantinas
 *
 */
public class FileChooserXMLController extends FileChooserController {

	
	/**
	 * Manages the control of the file chooser in Open dialog form.
	 * @param evt, event received from file chooser. This is selection of Open or Cancel buttons.
	 * @param fileChooser,the file chooser appearance, which is integrated into the frame.
	 * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
	 */
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			FileChooserFrameInter fileChooserFrame) {
		checkFileDescription(fileChooser);
		String command = evt.getActionCommand();//Selected button command


		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString()) ){ 		
			final String fileDirectoryName = fileChooser.getSelectedFile().toString();// get the directory of selected file	  			

			switch(ussXmlFileType){
			case SIMULATION:
				startSimulation(fileDirectoryName);	
				setSimulationXMLFileDirectory(fileDirectoryName);//CallBack				
				//ConsoleSimulationExample.main(null);
				break;			
			case ROBOT:        
				new Thread() {
					public void run() {
						try {
							builderControl.loadInXML(UssrXmlFileTypes.ROBOT, fileDirectoryName);
						} catch (RemoteException e) {
							throw new Error("Failed to load robot morphology from xml file, due to remote exception");
						}
					}
				}.start();
				break;	
			default: throw new Error("XML file type named as " +ussXmlFileType.toString() +"is not yet supported.");	
			}
		}		
		((Window) fileChooserFrame).dispose();

	}

	
	 /**
     * Manages the control of the file chooser in Save dialog form.
     * @param evt, event received from file chooser. This is selection of Save or Cancel buttons.
     * @param fileChooser, the file chooser appearance, which is integrated into the frame.
     * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
     */
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser,
			FileChooserFrameInter fileChooserFrame) {
		checkFileDescription( fileChooser);
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();

			try {
				remotePhysicsSimulation.saveToXML(ussXmlFileType, fileDirectoryName);
			} catch (RemoteException e) {
				throw new Error("Failed to save "+ ussXmlFileType.toString()+" description in xml file "+ fileDirectoryName+ ", due ro remote exception");
			}						
		}		
		((Window) fileChooserFrame).dispose();
	}	

}
