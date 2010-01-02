package ussr.aGui.fileChooser.controllers;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import ussr.builder.enumerations.UssrXmlFileTypes;

/**
 * Controls the functionality of both forms of file chooser: Open and Save.
 * Here also manages calls for remote XML processing.
 * @author Konstantinas
 *
 */
public class FileChooserXMLController extends FileChooserController {

	private boolean firstTime = true;
	

	@Override
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		checkFileDescription( fileChooser);
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
		
		fileChooserFrame.dispose();
	}

	@Override
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		checkFileDescription( fileChooser);
		//TODO Repeating code
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();

			try {
				remotePhysicsSimulation.saveToXML(ussXmlFileType, fileDirectoryName);
			} catch (RemoteException e) {
				throw new Error("Failed to save "+ ussXmlFileType.toString()+" description in xml file "+ fileDirectoryName+ ", due ro remote exception");
			}						
		}		
		fileChooserFrame.dispose();//close the frame(window)
	}	

}
