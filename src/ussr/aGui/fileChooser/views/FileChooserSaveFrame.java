package ussr.aGui.fileChooser.views;


import java.util.Map;

import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;

/**
 * Defines visual appearance of the file chooser in the form of Save dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class FileChooserSaveFrame extends FileChooserFrame  {

	/**
	 * The file chooser frame in Save dialog form.
	 */
	private static FileChooserSaveFrame fcSaveFrame;

	/**
	 * Manages the file chooser in the form of Save dialog.
	 * @param fileExtensions,extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for file extension.
	 * @param defaultDirectory, default directory to open.
	 */
	public FileChooserSaveFrame(Map<String, String> fileDescriptionsAndExtensions,FileChooserControllerInter fileChooserController,String defaultDirectory) {
		super(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);			
		changeToSaveDialog();
		setFilesToFilterOutWithDescription();
	}


	/**
	 * Changes several components of file chooser so that it is Save dialog.
	 */
	private void changeToSaveDialog(){		
		jFileChooserCustomized.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);	
		setTitle("Save");
		jFileChooserCustomized.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlSaveDialog(evt,jFileChooserCustomized, fcSaveFrame);//call controller

			}
		});		
	}

	/**
	 * Starts the window of file chooser in the form of Save dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
				fcSaveFrame.setVisible(true);
			}
		});    	
	}
}
