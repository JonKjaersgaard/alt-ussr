package ussr.aGui.fileChoosing.fileDialog;

import java.awt.FileDialog;
import java.awt.Frame;

public class FileDialogCustomizedOpen extends FileDialogCustomized  {

	private FileDialogTypes  fileDialogType;
	
	public FileDialogCustomizedOpen(Frame parent,FileDialogTypes  fileDialogType) {
		super(parent);
		this.fileDialogType = fileDialogType;
		chageToOpenDialog();
	}
	
	private void chageToOpenDialog(){
		super.fileDialogCustomized.setMode(FileDialog.LOAD);
		if (fileDialogType.equals(FileDialogTypes.OPEN_ROBOT_XML)){
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
