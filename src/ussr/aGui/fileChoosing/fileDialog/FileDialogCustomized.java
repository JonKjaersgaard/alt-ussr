package ussr.aGui.fileChoosing.fileDialog;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import ussr.aGui.fileChoosing.FileChoosingInter;

public abstract class FileDialogCustomized extends FileDialog implements FileDialogCustomizedInter {

	protected FileDialog fileDialogCustomized;
	
	public FileDialogCustomized(Frame parent) {
		super(parent);
		fileDialogCustomized = this;
		initCommonAppearance();		
	}
	
	private void initCommonAppearance(){
		fileDialogCustomized.setDirectory(DEFAULT_DIRECTORY);//is not working
		fileDialogCustomized.setAlwaysOnTop(true);

		fileDialogCustomized.setFilenameFilter(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(DEFAULT_FILE_EXTENSION);
			}
			
		});			
	}
	
	public String getFileName(){
		return fileDialogCustomized.getFile();
	}
	
	public String getFileDirectory(){
		return fileDialogCustomized.getDirectory();
	}
	
	public String getTopTitle(){
		return fileDialogCustomized.getTitle();
	}	
	
	public void setSelectedFile( String selectedFile){
		fileDialogCustomized.setFile(selectedFile);
	}
}
