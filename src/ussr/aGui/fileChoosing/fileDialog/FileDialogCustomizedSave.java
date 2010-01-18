package ussr.aGui.fileChoosing.fileDialog;

import java.awt.FileDialog;
import java.awt.Frame;

import ussr.aGui.fileChoosing.fileDialog.controllers.FileDialogControllerInter;

/**
 * Defines visual appearance of file dialog in the form "Save". For both simulation and robot xml files.
 * @author Konstantinas
 *
 */
@SuppressWarnings("serial")
public class FileDialogCustomizedSave extends FileDialogCustomized  {

	
	/**
	 * Defines visual appearance of file dialog in the form "Save". For both simulation and robot xml files.
	 * @param parent, the parent frame of file dialog window. 
	 * @param fileDialogType, the type of visual appearance.
	 * @param fileDialogController, the controller of file dialog window.
	 */
	public FileDialogCustomizedSave(Frame parent,FileDialogTypes  fileDialogType,FileDialogControllerInter fileDialogController) {
		super(parent);
		super.fileDialogType = fileDialogType;
		super.fileDialogController = fileDialogController;
		chageToOpenDialog();
	}
	
	/**
	 * Introduces changes in visual appearance so that it is Save dialog. 
	 */
	private void chageToOpenDialog(){
		super.fileDialogCustomized.setMode(FileDialog.SAVE);
		if (super.fileDialogType.equals(FileDialogTypes.SAVE_ROBOT_XML)){
		super.fileDialogCustomized.setTitle(SAVE_ROBOT_TITLE);
		}else{
			super.fileDialogCustomized.setTitle(SAVE_SIMULATION_TITLE);
		}		
	}

	/**
	 * Starts the file dialog in the form of Save dialog.
	 * Follows strategy pattern. 
	 */
	public void activate() {
		super.fileDialogCustomized.setVisible(true);
	}

}
