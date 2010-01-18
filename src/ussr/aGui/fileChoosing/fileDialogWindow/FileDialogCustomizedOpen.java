package ussr.aGui.fileChoosing.fileDialogWindow;

import java.awt.FileDialog;
import java.awt.Frame;

import ussr.aGui.fileChoosing.fileDialogWindow.controllers.FileDialogControllerInter;

public class FileDialogCustomizedOpen extends FileDialogCustomized  {


	public FileDialogCustomizedOpen(Frame parent,FileDialogTypes  fileDialogType,FileDialogControllerInter fileDialogController) {
		super(parent);
		super.fileDialogType = fileDialogType;
		super.fileDialogController = fileDialogController;
		chageToOpenDialog();
	}
	
	private void chageToOpenDialog(){
		super.fileDialogCustomized.setMode(FileDialog.LOAD);
		if (super.fileDialogType.equals(FileDialogTypes.OPEN_ROBOT_XML)){
		super.fileDialogCustomized.setTitle(OPEN_ROBOT_TITLE);
		}else{
			super.fileDialogCustomized.setTitle(OPEN_SIMULATION_TITLE);
		}			
	}

	@Override
	public void activate() {
		super.fileDialogCustomized.setVisible(true);
	}

}
