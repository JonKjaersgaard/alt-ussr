package ussr.aGui.fileChooser.views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ussr.aGui.fileChooser.FileFilter;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;


/**
 * Defines visual appearance of the file chooser in the forms of Save As dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class FileChooserSaveAsFrame extends FileChooserFrame  {

	/**
	 * The file chooser frame in Save dialog form.
	 */
	private static FileChooserSaveAsFrame fcSaveFrame;


	private  HashMap<String,String>  fileDescriptionsAndExtensions;


	/**
	 * Defines visual appearance the file chooser in the form of Save As dialog.
	 * @param fileDescriptionsAndExtensions,descriptions and extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for file extension.
	 */
	public FileChooserSaveAsFrame(Map<String, String> fileDescriptionsAndExtensions,FileChooserControllerInter fileChooserController) {
		super(fileDescriptionsAndExtensions, fileChooserController);	
		this.fileDescriptionsAndExtensions = (HashMap<String, String>) fileDescriptionsAndExtensions;
		changeToSaveAsDialog();
		setFilesToFilterOutWithDescription();
	}


	/**
	 * Changes several components of file chooser so that it is Save As dialog.
	 */
	private void changeToSaveAsDialog(){
		setUSSRicon(this);
		jFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);	
		setTitle("Save As...");
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlSaveAsDialog(evt,jFileChooser, fcSaveFrame);//call controller
			}
		});		
	}	

	/**
	 * Starts the window of file chooser in the form of Save As dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcSaveFrame = new FileChooserSaveAsFrame(fileDescriptionsAndExtensions,fileChooserController);
				fcSaveFrame.setVisible(true);
			}
		});    	
	}


}
