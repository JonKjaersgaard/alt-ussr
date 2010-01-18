package ussr.aGui.fileChoosing.fileDialog;

import java.awt.FileDialog;
import java.awt.Frame;

import ussr.aGui.fileChoosing.fileDialog.controllers.FileDialogControllerInter;

public class FileDialogCustomizedSave extends FileDialogCustomized  {

	
	public FileDialogCustomizedSave(Frame parent,FileDialogTypes  fileDialogType,FileDialogControllerInter fileDialogController) {
		super(parent);
		super.fileDialogType = fileDialogType;
		super.fileDialogController = fileDialogController;
		chageToOpenDialog();
	}
	
	private void chageToOpenDialog(){
		super.fileDialogCustomized.setMode(FileDialog.SAVE);
		if (super.fileDialogType.equals(FileDialogTypes.SAVE_ROBOT_XML)){
		super.fileDialogCustomized.setTitle(SAVE_ROBOT_TITLE);
		}else{
			super.fileDialogCustomized.setTitle(SAVE_SIMULATION_TITLE);
		}		
	}

	@Override
	public void activate() {
		super.fileDialogCustomized.setVisible(true);
	}

}
