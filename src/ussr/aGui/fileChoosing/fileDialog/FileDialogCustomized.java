package ussr.aGui.fileChoosing.fileDialog;

import java.awt.FileDialog;
import java.awt.Frame;

import ussr.aGui.MainFrames;
import ussr.aGui.fileChoosing.FileChoosingInter;

public abstract class FileDialogCustomized extends FileDialog implements FileChoosingInter {

	protected FileDialog fileDialogCustomized;
	
	public FileDialogCustomized(Frame parent) {
		super(parent);
	}

	
	
}
