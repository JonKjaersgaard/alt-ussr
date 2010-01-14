package ussr.aGui.fileChooser.views;


import java.util.Map;

import ussr.aGui.MainFrames;
import ussr.aGui.fileChooser.FileChooserControllerInter;
import ussr.aGui.fileChooser.FileFilter;
import ussr.aGui.fileChooser.FileFilterTypes;

/**
 * Defines visual appearance of the file chooser in the form of Open dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class FileChooserCustomizedOpen extends FileChooserCustomized  {	
	
	/**
	 * The frame of the file chooser for Open dialog form
	 */
	private static FileChooserCustomizedOpen fcOpenFrame;	
	
	/**
	 * Manages the file chooser in the form of Open dialog.
	 * @param fileDescriptionsAndExtensions, 
	 * @param fileChooserController, the controller for  file extension.
	 * @param defaultDirectory, default directory to open.
	 */
	public FileChooserCustomizedOpen(Map<String, String> fileDescriptionsAndExtensions, FileChooserControllerInter fileChooserController,String defaultDirectory) {
		super(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
		changeToOpenDialog();
		setFilesToFilterOutWithDescription();
		
	}
	
	/**
	 * Changes several components of file chooser so that it is Open dialog and calls controllers for file extensions.
	 */
	private void changeToOpenDialog(){
		super.jFileChooserCustomized.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
		super.jFileChooserCustomized.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlOpenDialog(evt,jFileChooserCustomized,fcOpenFrame);//call controller				
			}
		});		
	}
	
	/**
	 * Starts the window of file chooser in the form of Open dialog.
	 */
	public void activate(){
		jFileChooserCustomized.showOpenDialog(MainFrames.getMainFrame());  	
	}
}
