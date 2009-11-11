package ussr.aGui.fileChooser.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import ussr.builder.BuilderMultiRobotPreSimulation;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.remote.ConsoleSimulationExample;
import ussr.remote.GUISimulationAdapter;


/**
 * Controls the functionality of both forms of file chooser: Open and Save.
 * Here also manages calls for XML processing.
 * @author Konstantinas
 *
 */
public class FileChooserXMLController extends FileChooserController {

	/**
	 * Interface for XML processing
	 */
	private SaveLoadXMLFileTemplate saveLoadXML;


	/**
	 * Controls the functionality of both forms of file chooser: Open and Save.
	 * Here also manages calls for XML processing.
	 * @param jmeSimulation, the physical simulation.
	 */
	public FileChooserXMLController(/*JMESimulation jmeSimulation*/){
		/*	this.jmeSimulation = jmeSimulation;*/
	}	

	@Override
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		String fileDescription = fileChooser.getFileFilter().getDescription();
		checkFileDescription( fileDescription);		

		String command = evt.getActionCommand();//Selected button command				
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString()) ){ 		
			final String fileDirectoryName = fileChooser.getSelectedFile().toString();// get the directory of selected file	  			
			
            if (ussXmlFileType.equals(UssrXmlFileTypes.SIMULATION)){
			new Thread() {
				public void run() {
					try {
						GUISimulationAdapter.consoleSimulationExample(fileDirectoryName);
					} catch (IOException e) {
						throw new Error("Failed to run simulation file located at "+ fileDirectoryName+ " , due to remote exception");
					}
				}
			}.start();
			fileChooserFrame.dispose(); //close the frame(window)
            }
            //else{
            	
            //}
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window) 	  			
		}	
	}

	@Override
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		String fileDescription = fileChooser.getFileFilter().getDescription();
		checkFileDescription( fileDescription);
		//TODO Repeating code
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();
			saveLoadXML = new InSimulationXMLSerializer(this.jmeSimulation);
			saveLoadXML.saveXMLfile(ussXmlFileType, fileDirectoryName);

			fileChooserFrame.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window)			
		}		
	}	

}
