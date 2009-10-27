package ussr.aGui.fileChooser.views;

import java.util.ArrayList;

import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;
import ussr.physics.jme.JMESimulation;

/**
 * Manages the file chooser in the form of Open dialog.
 * @author Konstantinas
 */
public class FileChooserOpenFrame extends FileChooserFrame  {	
	
	/**
	 * The frame of the file chooser for Open dialog form
	 */
	private  static FileChooserOpenFrame fcOpenFrame;	
	
	/**
	 * Manages the file chooser in the form of Open dialog.
	 * @param fileExtensions, extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for  file extension.
	 */
	public FileChooserOpenFrame(ArrayList<String> fileExtensions, FileChooserControllerInter fileChooserController) {
		super(fileExtensions,fileChooserController);
		changeToOpenDialog();
		setFilesToFilter(fileExtensions);
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
				fcOpenFrame = new FileChooserOpenFrame(fileExtensions,fileChooserController);
				fcOpenFrame.setVisible(true);				
			}
		});    	
	}
}
