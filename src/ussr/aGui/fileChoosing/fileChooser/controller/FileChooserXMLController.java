package ussr.aGui.fileChoosing.fileChooser.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;

import ussr.aGui.MainFrames;
import ussr.aGui.MainFramesInter;
import ussr.aGui.fileChoosing.fileChooser.FileChooserCustomizedInter;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;

/**
 * Controls the functionality of both forms of file chooser: Open and Save for XML processing.
 * @author Konstantinas
 *
 */
public class FileChooserXMLController extends FileChooserController {

	private static boolean includeSimulationTermination = false;
	
	private static boolean includeStartNewSimulation= false;
	
	
	
	public static void setIncludeStartNewSimulation(
			boolean includeStartNewSimulation) {
		FileChooserXMLController.includeStartNewSimulation = includeStartNewSimulation;
	}


	public static void setIncludeSimulationTermination(boolean includeSimulationTermination) {
		FileChooserXMLController.includeSimulationTermination = includeSimulationTermination;
	}


	/**
	 * Manages the control of the file chooser in Open dialog form.
	 * @param evt, event received from file chooser. This is selection of Open or Cancel buttons.
	 * @param fileChooser,the file chooser appearance, which is integrated into the frame.
	 * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
	 */
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			FileChooserCustomizedInter fileChooserFrame) {
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
				setSimulationXMLFileDirectory(fileDirectoryName);//CallBack //FIXME NO LONGER NEEDED
				break;			
			case ROBOT:  
				loadRobot(fileDirectoryName);
				/*new Thread() {
					public void run() {
						try {
							builderControl.loadInXML(UssrXmlFileTypes.ROBOT, fileDirectoryName);
						} catch (RemoteException e) {
							throw new Error("Failed to load robot morphology from xml file, due to remote exception");
						}
					}
				}.start();*/
				break;	
			default: throw new Error("XML file type named as " +ussXmlFileType.toString() +"is not yet supported.");	
			}
		}		

	}

	
	 /**
     * Manages the control of the file chooser in Save dialog form.
     * @param evt, event received from file chooser. This is selection of Save or Cancel buttons.
     * @param fileChooser, the file chooser appearance, which is integrated into the frame.
     * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
     */
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser,
			FileChooserCustomizedInter fileChooserFrame) {
		checkFileDescription( fileChooser);
		String command = evt.getActionCommand();//Selected button command			
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();

			saveInXml(ussXmlFileType,fileDirectoryName);
		/*	try {
				remotePhysicsSimulation.saveToXML(ussXmlFileType, fileDirectoryName);
			} catch (RemoteException e) {
				throw new Error("Failed to save "+ ussXmlFileType.toString()+" description in xml file "+ fileDirectoryName+ ", due ro remote exception");
			}*/
			if (includeSimulationTermination&&includeStartNewSimulation){
				terminateSimulation();
				startSimulation(MainFramesInter.LOCATION_DEFAULT_NEW_SIMULATION);
			}else if(includeSimulationTermination==true&&includeStartNewSimulation==false){
				terminateSimulation();
				FileChooserCustomizedInter.FC_OPEN_SIMULATION.activate();
			}
			
		}		
	}	

}
