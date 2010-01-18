package ussr.aGui.fileChoosing.fileDialogWindow.controllers;

import ussr.aGui.fileChoosing.FileDialogCustomizedInter;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;

public class FileDialogXMLController extends FileDialogController{

	@Override
	public void controlOpenDialog(FileDialogCustomizedInter fdOpenDialog) {
		fdOpenDialog.activate();
		String selectedFileName = fdOpenDialog.getFileName();
		String selectedDirectory= fdOpenDialog.getFileDirectory();
		startSimulation(selectedDirectory + selectedFileName);
		
		
		
	}

	@Override
	public void controlSaveDialog(FileDialogCustomizedInter fdSaveDialog) {
		fdSaveDialog.activate();
		String selectedFileName = fdSaveDialog.getFileName();
		//System.out.println("File:"+ selectedFileName);
		String selectedDirectory= fdSaveDialog.getFileDirectory();
		//System.out.println("Dir:"+ selectedDirectory);
		String title = fdSaveDialog.getTopTitle();
		
		
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
