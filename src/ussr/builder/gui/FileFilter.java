package ussr.builder.gui;
import java.io.File;

/**
 *
 * @author Konstantinas
 */
public class FileFilter extends javax.swing.filechooser.FileFilter {

	String fileExtension;
	public FileFilter(String fileExtension){
		this.fileExtension =fileExtension; 
	}

	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(fileExtension);
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getDescription() {
		return "*" + fileExtension;
		//throw new UnsupportedOperationException("Not supported yet.");
	}

}
