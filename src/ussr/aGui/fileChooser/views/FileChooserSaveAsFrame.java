package ussr.aGui.fileChooser.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ussr.aGui.fileChooser.FileFilter;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;


/**
 * Manages the file chooser in the forms of Save dialog.
 * @author Konstantinas
 */
public class FileChooserSaveAsFrame extends FileChooserFrame  {

	/**
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The file chooser frame in Save dialog form.
	 */
	private static FileChooserSaveAsFrame fcSaveFrame;
	
	
	private  HashMap<String,String>  fileDescriptionsAndExtensions;


	/**
	 * Manages the file chooser in the form of Save As dialog.
	 * @param fileDescriptionsAndExtensions,descriptions and extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for file extension.
	 */
	public FileChooserSaveAsFrame(Map<String, String> fileDescriptionsAndExtensions,FileChooserControllerInter fileChooserController) {
		super(null, fileChooserController);	
		this.fileDescriptionsAndExtensions = (HashMap<String, String>) fileDescriptionsAndExtensions;
		changeToSaveAsDialog();
		setFilesToFilterOut();
	}


	/**
	 * Changes several components of file chooser so that it is Save dialog.
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

	private void setFilesToFilterOut(){
		Iterator<String> keyIterator = fileDescriptionsAndExtensions.keySet().iterator();
		Iterator<String> valueIterator = fileDescriptionsAndExtensions.values().iterator();
		while(keyIterator.hasNext()){
			jFileChooser.setFileFilter(new FileFilter (keyIterator.next(),valueIterator.next()));			
		}
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
