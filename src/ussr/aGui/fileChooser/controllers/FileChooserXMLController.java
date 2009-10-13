package ussr.aGui.fileChooser.controllers;

import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.physics.jme.JMESimulation;

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
	public FileChooserXMLController(JMESimulation jmeSimulation){
		this.jmeSimulation = jmeSimulation;
	}	
	
	@Override
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		String command = evt.getActionCommand();//Selected button command				
	  	  if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString()) ){ 		
	  			String fileDirectoryName = fileChooser.getSelectedFile().toString();// get the directory of selected file	  			
	  			saveLoadXML = new InSimulationXMLSerializer(this.jmeSimulation);
	  			saveLoadXML.loadXMLfile(fileDirectoryName);	
	  			fileChooserFrame.dispose(); //close the frame(window)	  			
	  		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
	  			fileChooserFrame.dispose();//close the frame(window) 	  			
	  		}		
	}

	@Override
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();
			saveLoadXML = new InSimulationXMLSerializer(this.jmeSimulation);
			saveLoadXML.saveXMLfile(fileDirectoryName);	
			fileChooserFrame.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window)			
		}		
	}
}
