package ussr.aGui.fileChooser;

import java.util.Hashtable;
import java.util.Map;

/**
 * Contains file filter descriptions in format: file description, file extension. For instance:"Simulation",".xml"
 * @author Konstantinas
 *
 */
public enum FileFilterTypes {

	
	OPEN_SAVE_SIMULATION(FileChooserFrameInter.DEFAULT_FILE_DESCRIPTION,FileChooserFrameInter.DEFAULT_FILE_EXTENSION),
	OPEN_SAVE_ROBOT(FileChooserFrameInter.ROBOT_FILE_DESCRIPTION,FileChooserFrameInter.DEFAULT_FILE_EXTENSION),
	;
	
	/**
	 * Textual description of file filter
	 */
	private String fileDescription;

	/**
	 * File extension of filter.
	 */
	private String  fileExtension;
	
	/**
	 * Contains file filter descriptions in format: file description, file extension. For instance:"Simulation",".xml"
	 * @param fileDescription, textual description of file filter.
	 * @param fileExtension, file extension of filter.
	 */
	FileFilterTypes(String fileDescription, String fileExtension){
		this.fileDescription = fileDescription;
		this.fileExtension = fileExtension;
	}
	
	/**
	 * Returns map(hash table) representing chosen file filter. For instance: <Simulation>,<.xml>
	 * @return map(hash table) representing chosen file filter.For instance: <Simulation>,<.xml>
	 */
	public Map<String,String> getMap(){
		Map<String,String> newMap =  new Hashtable<String,String>();
		newMap.put(this.getFileDescription(), this.getFileExtension());
		return newMap;
	}
	
	/**
	 * Returns textual description of file filter.
	 * @return textual description of file filter.
	 */
	public String getFileDescription() {
		return fileDescription;
	}
	
	/**
	 * File extension of filter.
	 * @return
	 */
	public String getFileExtension() {
		return fileExtension;
	}
	
}
