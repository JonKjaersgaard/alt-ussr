package ussr.aGui.fileChoosing.fileDialog;

import java.awt.FileDialog;
import java.awt.Frame;

public class FileDialogCustomizedSave extends FileDialogCustomized  {

	private FileDialogTypes  fileDialogType;
	
	public FileDialogCustomizedSave(Frame parent,FileDialogTypes  fileDialogType) {
		super(parent);
		this.fileDialogType = fileDialogType;
		chageToOpenDialog();
	}
	
	private void chageToOpenDialog(){
		super.fileDialogCustomized.setMode(FileDialog.SAVE);
		if (fileDialogType.equals(FileDialogTypes.SAVE_ROBOT_XML)){
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
