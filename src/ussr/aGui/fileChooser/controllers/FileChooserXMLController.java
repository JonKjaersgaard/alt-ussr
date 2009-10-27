package ussr.aGui.fileChooser.controllers;

import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ussr.aGui.fileChooser.views.FileChooserSaveAsFrame;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;
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
	  			saveLoadXML.loadXMLfile(UssrXmlFileTypes.ROBOT, fileDirectoryName);
	  			
	  			//saveLoadXML.loadXMLfile(UssrXmlFileTypes.SIMULATION, fileDirectoryName);
	  			
	  			fileChooserFrame.dispose(); //close the frame(window)
	  			
	  			ConstructRobotTabController.adaptTabToModuleInSimulation(this.jmeSimulation);
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
			saveLoadXML.saveXMLfile(UssrXmlFileTypes.ROBOT, fileDirectoryName);
			//saveLoadXML.saveXMLfile(UssrXmlFileTypes.SIMULATION, fileDirectoryName);
			
			fileChooserFrame.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window)			
		}		
	}

	
	private UssrXmlFileTypes ussXmlFileType;
	
	@Override
	public void controlSaveAsDialog(ActionEvent evt, JFileChooser fileChooser,
			FileChooserSaveAsFrame fcSaveAsFrame) {		
		
		String fileDescription = fileChooser.getFileFilter().getDescription();
		if(fileDescription.contains(UssrXmlFileTypes.ROBOT.toString().toLowerCase().replaceFirst("r", "R"))){
			ussXmlFileType =UssrXmlFileTypes.ROBOT;
		}else if (fileDescription.contains(UssrXmlFileTypes.SIMULATION.toString().toLowerCase().replaceFirst("s", "S"))){
			ussXmlFileType = UssrXmlFileTypes.SIMULATION;
		}
			//TODO Repeating code
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();
			saveLoadXML = new InSimulationXMLSerializer(this.jmeSimulation);
			saveLoadXML.saveXMLfile(ussXmlFileType, fileDirectoryName);
			
			fcSaveAsFrame.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fcSaveAsFrame.dispose();//close the frame(window)			
		}		
		
	}
}
