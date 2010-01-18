package ussr.aGui.fileChoosing.fileDialog.controllers;

import ussr.aGui.fileChoosing.fileDialog.FileDialogCustomizedInter;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;

/**
 * Controls the functionality of both forms of file dialog windows: Open and Save for XML processing.
 * @author Konstantinas
 */
public class FileDialogXMLController extends FileDialogController{

	/**
	 * Controls save event for xml saving.
	 * @param fdSaveDialog, the instance of file dialog window in save form.
	 */
	public void controlOpenDialog(FileDialogCustomizedInter fdOpenDialog) {
		fdOpenDialog.activate();
		String selectedFileName = fdOpenDialog.getFileName();
		String selectedDirectory= fdOpenDialog.getFileDirectory();
		System.out.println("Path:" +selectedDirectory+selectedFileName );
		if (selectedFileName!=null ){// means Open button was pressed, else - Cancel

			switch(fdOpenDialog.getFileDialogType()){
			case OPEN_SIMULATION_XML:
				startSimulation(selectedDirectory + selectedFileName);
				setSimulationXMLFileDirectory(selectedDirectory + selectedFileName);
				break;
			case OPEN_ROBOT_XML:
				loadRobot(selectedDirectory + selectedFileName);
				break;
			default: throw new Error ("The file dialog type named as: " + fdOpenDialog.getFileDialogType()+ " is not supported yet.");

			}
		}

	}

	/**
	 * Controls open event for xml loading.
	 * @param fdOpenDialog, the instance of file dialog window in open form.
	 */
	public void controlSaveDialog(FileDialogCustomizedInter fdSaveDialog) {
		fdSaveDialog.activate();
		String selectedFileName = fdSaveDialog.getFileName();
		String selectedDirectory= fdSaveDialog.getFileDirectory();

		if (selectedFileName!=null ){// means Save button was pressed, else - Cancel

			switch(fdSaveDialog.getFileDialogType()){
			case SAVE_SIMULATION_XML:
				saveInXml(UssrXmlFileTypes.SIMULATION,selectedDirectory+selectedFileName);
				break;
			case SAVE_ROBOT_XML:
				saveInXml(UssrXmlFileTypes.ROBOT,selectedDirectory+selectedFileName);
				break;
			default: throw new Error ("The file dialog type named as: " + fdSaveDialog.getFileDialogType()+ " is not supported yet.");

			}
		}
	}
}
