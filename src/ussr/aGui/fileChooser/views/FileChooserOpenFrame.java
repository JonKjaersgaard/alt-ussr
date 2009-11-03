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
	private  static FileChooserOpenFrame fcOpenFrame;	
	
	/**
	 * Manages the file chooser in the form of Open dialog.
	 * @param fileDescriptionsAndExtensions, 
	 * @param fileChooserController, the controller for  file extension.
	 */
	public FileChooserOpenFrame(Map<String, String> fileDescriptionsAndExtensions, FileChooserControllerInter fileChooserController) {
		super(fileDescriptionsAndExtensions,fileChooserController);
		changeToOpenDialog();
		setFilesToFilterOutWithDescription();
	}
	
	/**
	 * Changes several components of file chooser so that it is Open dialog and calls controllers for file extensions.
	 */
	private void changeToOpenDialog(){
		setUSSRicon(this);
		setTitle("Open");
		super.jFileChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);	
		
		super.jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlOpenDialog(evt,jFileChooser,fcOpenFrame);//call controller				
			}
		});		
	}
	
	
	/**
	 * Starts the window of file chooser in the form of Open dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcOpenFrame = new FileChooserOpenFrame(fileDescriptionsAndExtensions,fileChooserController);
				fcOpenFrame.setVisible(true);				
			}
		});    	
	}
}
