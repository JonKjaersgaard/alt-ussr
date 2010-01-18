package ussr.aGui.fileChoosing.jFileChooser;
import java.io.File;

/**
 * Acts as a file filter for fileChooser.
 * Just set the instance of this class for your file chooser with specific file extension
 * to filter out and description of this extension. For instance: "XMLfile", ".xml" or just extension ".xml". 
 * @author Konstantinas
 */
public class FileFilterCustomized extends javax.swing.filechooser.FileFilter {

	/**
	 * The file extension to filter out, for example: ".xml".
	 */
	String fileExtension;
	
	/**
	 * Description of the file extension.
	 */
	String fileExtensionDescription;
	
	/**
	 * Filters out the files with specific file extension and displays short description of extension. Is used for fileChoosers.
	 * @param fileDescription, description of the file extension.
	 * @param fileExtension, the file extension to filter out 
	 */
	public FileFilterCustomized( String fileDescription, String fileExtension){
		this.fileExtensionDescription = fileDescription;
		this.fileExtension =fileExtension;		
	}
	
	/**
	 * Filters out the files with specific file extension. Is used for fileChoosers.
	 * @param fileExtension, the file extension to filter out 
	 */
	public FileFilterCustomized(String fileExtension){
		this.fileExtension =fileExtension;		
	}
	
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(fileExtension);		
	}

	@Override
	public String getDescription() {
		if (fileExtensionDescription==null){
			return "*" + fileExtension;	
		}
		else return fileExtensionDescription +" ("+"*" + fileExtension+")";
	}

}
