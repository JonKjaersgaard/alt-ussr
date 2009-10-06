package ussr.aGui.fileChooser.appearance;

import java.util.ArrayList;
import ussr.aGui.fileChooser.controller.FileChooserControllerInter;

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
	 * @param fileChooserControllers, the controllers for each file extension.
	 */
	public FileChooserOpenFrame(ArrayList<String> fileExtensions, ArrayList<FileChooserControllerInter> fileChooserControllers) {
		this.fileExtensions = fileExtensions;
		this.fileChooserControllers = fileChooserControllers; 
		initComponents();
		changeToOpenDialog();
		setFilesToFilter(fileExtensions);
	}
	
	/**
	 * Changes several components of file chooser so that it is Open dialog and calls controllers for file extensions.
	 */
	private void changeToOpenDialog(){
		setTitle("Load simulation from the file");
		jFileChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);	
		
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int sizeExtensions = fileExtensions.size();
				int sizeControllers = fileChooserControllers.size();
				if(sizeExtensions>1&&sizeControllers>1){
					//TODO For future implementation. Add calls for controllers if there will be more file extensions than one.
					//throw new Error("The implementation is missing for more than one extension");
				}else{
				fileChooserControllers.get(0).controlOpenDialog(evt,jFileChooser,fcOpenFrame);//call controller
				}
			}
		});		
	}
	
	
	/**
	 * Starts the window of file chooser in the form of Open dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcOpenFrame = new FileChooserOpenFrame(fileExtensions,fileChooserControllers);
				fcOpenFrame.setVisible(true);				
			}
		});    	
	}
}
