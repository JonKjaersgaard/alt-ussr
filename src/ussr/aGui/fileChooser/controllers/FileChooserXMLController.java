package ussr.aGui.fileChooser.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.remote.GUISimulationAdapter;


/**
 * Controls the functionality of both forms of file chooser: Open and Save.
 * Here also manages calls for remote XML processing.
 * @author Konstantinas
 *
 */
public class FileChooserXMLController extends FileChooserController {

	
	@Override
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		checkFileDescription( fileChooser);
		String command = evt.getActionCommand();//Selected button command
		
		
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString()) ){ 		
			final String fileDirectoryName = fileChooser.getSelectedFile().toString();// get the directory of selected file	  			
			
			switch(ussXmlFileType){
			case SIMULATION:
				new Thread() {
					public void run() {
						try {
							GUISimulationAdapter.consoleSimulationExample(fileDirectoryName);
							//GUISimulationAdapter.main(null);
						} catch (IOException e) {
							throw new Error("Failed to run simulation file located at "+ fileDirectoryName+ " , due to remote exception");
						}
					}
				}.start();
				break;			
			case ROBOT:
				try {
						builderControl.saveToXML(UssrXmlFileTypes.ROBOT, fileDirectoryName);
					} catch (RemoteException e) {
						throw new Error("Failed to save robot morphology in xml file, due to remote exception");
					}
				break;
				default: throw new Error("XML file type named as " +ussXmlFileType.toString() +"is not yet supported.");
			}
			 //close the frame(window)          
            fileChooserFrame.dispose();
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window) 	  			
		}	
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

			fileChooserFrame.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window)			
		}		
	}	

}
