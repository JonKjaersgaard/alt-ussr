package ussr.aGui.fileChooser.view;

import java.util.ArrayList;

import ussr.aGui.fileChooser.controller.FileChooserControllerInter;
import ussr.physics.jme.JMESimulation;

/**
 * Manages the file chooser in the form of Save dialog.
 * @author Konstantinas
 */
public class FileChooserSaveFrame extends FileChooserFrame  {
	
	
	private static FileChooserSaveFrame fcSaveFrame;
	
	/**
	 * Manages the file chooser in the form of Save dialog.
	 * @param fileExtensions,extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserControllers, the controllers for each file extension.
	 * @param jmeSimulation,
	 */
	public FileChooserSaveFrame(ArrayList<String> fileExtensions,ArrayList<FileChooserControllerInter> fileChooserControllers) {	
		this.fileExtensions = fileExtensions;
		this.fileChooserControllers = fileChooserControllers;
		initComponents();
		changeToSaveDialog();
		setFilesToFilter(fileExtensions);
	}
	
	
	/**
	 * Changes several components of file chooser so that it is Save dialog.
	 */
	private void changeToSaveDialog(){
		setUSSRicon(this);
		setTitle("Save simulation in the file");
		jFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);		
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(fileExtensions.size()>1&&fileChooserControllers.size()>1){
					//TODO For future implementation. Add calls for controllers if there will be more file extensions than one.
					throw new Error("The implementation is missing for more than one extension");
				}else{
				fileChooserControllers.get(0).controlSaveDialog(evt,jFileChooser, fcSaveFrame);//call controller
				}
			}
		});		
	}
	
	
	/**
	 * Starts the window of file chooser in the form of Save dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcSaveFrame = new FileChooserSaveFrame(fileExtensions,fileChooserControllers);
				fcSaveFrame.setVisible(true);
			}
		});    	
	}
}
