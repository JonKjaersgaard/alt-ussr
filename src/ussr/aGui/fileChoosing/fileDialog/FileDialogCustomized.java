package ussr.aGui.fileChoosing.fileDialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;

import ussr.aGui.controllers.GeneralController;
import ussr.aGui.fileChoosing.fileDialog.controllers.FileDialogControllerInter;

/**
 * Hosts common implementation for different types of file dialog appearance. In particular
 * Save and Open dialog forms and for simulation and robot xml files.   
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public abstract class FileDialogCustomized extends FileDialog implements FileDialogCustomizedInter {

	/**
	 * The object of file Dialog to create;
	 */
	protected FileDialog fileDialogCustomized;
	
	/**
	 * The type of file dialog appearance;
	 */
	protected FileDialogTypes fileDialogType;
	
	/**
	 * The controller of file dialog.
	 */
	protected FileDialogControllerInter fileDialogController;
	
	/**
	 * Hosts common implementation for different types of file dialog appearance. In particular
     * Save and Open dialog forms and for simulation and robot xml files.
	 * @param parent, the parent frame of file dialog window. 
	 */
	public FileDialogCustomized(Frame parent) {
		super(parent);
		fileDialogCustomized = this;
		initCommonAppearance();		
	}
	
	/**
	 * Initializes visual appearance of file dialog common to variations of it.
	 */
	private void initCommonAppearance(){
		fileDialogCustomized.setAlwaysOnTop(true);
		try {		
			fileDialogCustomized.setDirectory(FILE_IN_CURRENT_DIRECTORY.getCanonicalPath().toString()+DEFAULT_RELATIVE_DIRECTORY);
		} catch (IOException e) {
			throw new Error("Failed to locate  default directory for storing XML files in USSR folder structure, named as: " + DEFAULT_RELATIVE_DIRECTORY);
		}
		fileDialogCustomized.setFilenameFilter(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(DEFAULT_FILE_EXTENSION);
			}
			
		});	
	}
	
	 /**
     * Returns the name of the file selected by user. Is null if user selects Cancel.
     * @return the name of the file selected by user.
     */
	public String getFileName(){
		return fileDialogCustomized.getFile();
	}
	
	 /**
     * Returns the directory selected by user. 
     * @return the directory selected by user.
     */
	public String getFileDirectory(){
		return fileDialogCustomized.getDirectory();
	}
	
	/**
     * Sets the name of the file selected in file dialog window.
     * @param selectedFile, the name of the file selected in file dialog window.
     */
	public void setSelectedFile(String selectedFile){
		fileDialogCustomized.setFile(selectedFile);
	}
	
	/**
     * Returns the type of file dialog window.
     * @return the type of file dialog window.
     */
	public FileDialogTypes getFileDialogType() {
		return fileDialogType;
	}
	
	 /**
     * Returns controller of the file dialog. 
     * @return controller of the file dialog. 
     */
	public FileDialogControllerInter getFileDialogController() {
		return fileDialogController;
	}
	
}
