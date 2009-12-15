package ussr.aGui.fileChooser.views;

import java.util.Map;

import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;

/**
 * TODO NOT USED YET
 * @author Konstantinas
 *
 */
public class FileChooserRobotMorphologyLocation extends FileChooserFrame {
	/**
	 * The frame of the file chooser for Open dialog form
	 */
	private FileChooserRobotMorphologyLocation fcRobotMorphologyFrame;	
	
	/**
	 * Manages the file chooser in the form of TODO dialog.
	 * @param fileDescriptionsAndExtensions, 
	 * @param fileChooserController, the controller for  file extension.
	 * @param defaultDirectory, default directory to open.
	 */
	public FileChooserRobotMorphologyLocation(Map<String, String> fileDescriptionsAndExtensions, FileChooserControllerInter fileChooserController,String defaultDirectory) {
		super(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
		changeToRobotMorphologyDialog();
		setFilesToFilterOutWithDescription();
	}
	
	
	/**
	 * TODO Changes several components of file chooser so that it is Open dialog and calls controllers for file extensions.
	 */
	private void changeToRobotMorphologyDialog(){
		setTitle("Open");
		super.jFileChooserCustomized.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);	
		
		super.jFileChooserCustomized.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				//fileChooserController.controlOpenDialog(evt,jFileChooserCustomized,fcOpenFrame);//call controller				
			}
		});		
	}
	
	@Override
	public void activate() {
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcRobotMorphologyFrame = new FileChooserRobotMorphologyLocation(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
				fcRobotMorphologyFrame.setVisible(true);				
			}
		}); 
		
	}

}
