package ussr.aGui.fileChoosing.jFileChooser.controller;


import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import ussr.aGui.MainFramesInter;
import ussr.aGui.fileChoosing.jFileChooser.JFileChooserCustomizedInter;

/**
 * Controls the functionality of both forms of file chooser: Open and Save for XML processing.
 * @author Konstantinas
 *
 */
public class JFileChooserXMLController extends JFileChooserController {

	/**
	 * The flag to indicate that current simulation should be terminated.
	 */
	private static boolean includeSimulationTermination = false;

	/**
	 * The flag indicating that new simulation should be started.
	 */
	private static boolean includeStartNewSimulation= false;


	/**
	 * Manages the control of the file chooser in Open dialog form.
	 * @param evt, event received from file chooser. This is selection of Open or Cancel buttons.
	 * @param fileChooser,the file chooser appearance, which is integrated into the frame.
	 */
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser) {
		checkFileDescription(fileChooser);
		String command = evt.getActionCommand();//Selected button command


		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString()) ){ 		
			final String fileDirectoryName = fileChooser.getSelectedFile().toString();// get the directory of selected file	  			

			switch(ussXmlFileType){
			case SIMULATION:
				if (includeSimulationTermination==true&&includeStartNewSimulation==false){
					terminateSimulation();
				}
				startSimulation(fileDirectoryName);	
				setSimulationXMLFileDirectory(fileDirectoryName);
				break;			
			case ROBOT:  
				loadRobot(fileDirectoryName);
				break;	
			default: throw new Error("XML file type named as " +ussXmlFileType.toString() +"is not yet supported.");	
			}
		}		

	}


	/**
	 * Manages the control of the file chooser in Save dialog form.
	 * @param evt, event received from file chooser. This is selection of Save or Cancel buttons.
	 * @param fileChooser, the file chooser appearance, which is integrated into the frame.
	 */
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser) {
		checkFileDescription( fileChooser);
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();

			saveInXml(ussXmlFileType,fileDirectoryName);
			if (includeSimulationTermination&&includeStartNewSimulation){
				terminateSimulation();
				startSimulation(MainFramesInter.LOCATION_DEFAULT_NEW_SIMULATION);
			}else if(includeSimulationTermination==true&&includeStartNewSimulation==false){
				terminateSimulation();
				JFileChooserCustomizedInter.FC_OPEN_SIMULATION.activate();
			}

		}		
	}

	/**
	 * Sets the flag to indicate that new simulation should be started.
	 * @param includeStartNewSimulation, flag indicating that new simulation should be started.
	 */
	public static void setIncludeStartNewSimulation(boolean includeStartNewSimulation) {
		JFileChooserXMLController.includeStartNewSimulation = includeStartNewSimulation;
	}


	/**
	 * Sets the flag indicating that current simulation should be terminated.
	 * @param includeSimulationTermination, the flag indicating that current simulation should be terminated.
	 */
	public static void setIncludeSimulationTermination(boolean includeSimulationTermination) {
		JFileChooserXMLController.includeSimulationTermination = includeSimulationTermination;
	}

}
