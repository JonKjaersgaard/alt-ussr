package ussr.aGui;
import java.io.File;

/**
 * This class is responsible to act as a file filter for fileChooser.
 * Just set the instance of this class for your file chooser with specific file extension
 * to filter out. For example:  jFileChooser1.setFileFilter(new FileFilter (".xml")); 
 * @author Konstantinas
 */
public class FileFilter extends javax.swing.filechooser.FileFilter {

	/**
	 * The file extension to filter out, for example: ".xml".
	 */
	String fileExtension;
	
	/**
	 * Filters out the files with specific file extension. Is used for fileChoosers.
	 * @param fileExtension, the file extension to filter out 
	 */
	public FileFilter(String fileExtension){
		this.fileExtension =fileExtension; 
	}
	
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(fileExtension);		
	}

	@Override
	public String getDescription() {
		return "*" + fileExtension;		
	}

}
