package ussr.aGui.fileChooser.views;

import java.util.ArrayList;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;


/**
 * Manages the file chooser in the forms of Save dialog.
 * @author Konstantinas
 */
public class FileChooserSaveFrame extends FileChooserFrame  {

	/**
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The file chooser frame in Save dialog form.
	 */
	private static FileChooserSaveFrame fcSaveFrame;

	/**
	 * Manages the file chooser in the form of Save dialog.
	 * @param fileExtensions,extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for file extension.
	 */
	public FileChooserSaveFrame(ArrayList<String> fileExtensions,FileChooserControllerInter fileChooserController) {
		super(fileExtensions,fileChooserController);			
		changeToSaveDialog();
		setFilesToFilter(fileExtensions);
	}


	/**
	 * Changes several components of file chooser so that it is Save dialog.
	 */
	private void changeToSaveDialog(){
		setUSSRicon(this);
		jFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);	
		setTitle("Save");
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlSaveDialog(evt,jFileChooser, fcSaveFrame);//call controller

			}
		});		
	}	




	/**
	 * Starts the window of file chooser in the form of Save dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcSaveFrame = new FileChooserSaveFrame(fileExtensions,fileChooserController);
				fcSaveFrame.setVisible(true);
			}
		});    	
	}
}
