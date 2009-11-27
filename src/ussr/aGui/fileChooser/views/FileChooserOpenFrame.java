package ussr.aGui.fileChooser.views;


import java.util.Map;

import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;

/**
 * Defines visual appearance of the file chooser in the form of Open dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class FileChooserOpenFrame extends FileChooserFrame  {	
	
	/**
	 * The frame of the file chooser for Open dialog form
	 */
	private static FileChooserOpenFrame fcOpenFrame;	
	
	/**
	 * Manages the file chooser in the form of Open dialog.
	 * @param fileDescriptionsAndExtensions, 
	 * @param fileChooserController, the controller for  file extension.
	 * @param defaultDirectory, default directory to open.
	 */
	public FileChooserOpenFrame(Map<String, String> fileDescriptionsAndExtensions, FileChooserControllerInter fileChooserController,String defaultDirectory) {
		super(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
		changeToOpenDialog();
		setFilesToFilterOutWithDescription();
	}
	
	/**
	 * Changes several components of file chooser so that it is Open dialog and calls controllers for file extensions.
	 */
	private void changeToOpenDialog(){
		setTitle("Open");
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
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
				fcOpenFrame.setVisible(true);				
			}
		});    	
	}
}
