package ussr.aGui.fileChooser;

import java.util.Hashtable;
import java.util.Map;

public enum FileFilterTypes {

	
	OPEN_SAVE_SIMULATION(FileChooserFrameInter.DEFAULT_FILE_DESCRIPTION,FileChooserFrameInter.DEFAULT_FILE_EXTENSION),
	OPEN_SAVE_ROBOT(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION,FileChooserFrameInter.DEFAULT_FILE_EXTENSION),
	;
	
	private String fileDescription;


	private String  fileExtension;
	
	
	

	FileFilterTypes(String fileDescription, String fileExtension){
		this.fileDescription = fileDescription;
		this.fileExtension = fileExtension;
	}
	
	public Map<String,String> getMap(){
		Map<String,String> newMap =  new Hashtable<String,String>();
		newMap.put(this.getFileDescription(), this.getFileExtension());
		return newMap;
	}
	
	public String getFileDescription() {
		return fileDescription;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
	
}
