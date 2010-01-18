package ussr.aGui.fileChoosing.jFileChooser;


import java.util.Map;

import ussr.aGui.MainFrames;
import ussr.aGui.fileChoosing.jFileChooser.controller.JFileChooserControllerInter;

/**
 * Defines visual appearance of the file chooser in the form of Open dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class JFileChooserCustomizedOpen extends JFileChooserCustomized  {	
	
	/**
	 * Manages the file chooser in the form of Open dialog.
	 * @param fileDescriptionsAndExtensions, 
	 * @param fileChooserController, the controller for  file extension.
	 */
	public JFileChooserCustomizedOpen(Map<String, String> fileDescriptionsAndExtensions, JFileChooserControllerInter fileChooserController) {
		super(fileDescriptionsAndExtensions,fileChooserController);
		changeToOpenDialog();
		setFilesToFilterOutWithDescription();
		
	}
	
	/**
	 * Changes several components of file chooser so that it is Open dialog and calls controllers for file extensions.
	 */
	private void changeToOpenDialog(){
		super.jFileChooserCustomized.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
		if (super.fileDescriptionsAndExtensions.containsKey(FileFilterSpecifications.OPEN_SAVE_SIMULATION.getFileDescription())){
			super.jFileChooserCustomized.setDialogTitle(OPEN_SIMULATION_TITLE);
		}else{
			super.jFileChooserCustomized.setDialogTitle(OPEN_ROBOT_TITLE);
		}
		super.jFileChooserCustomized.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlOpenDialog(evt,jFileChooserCustomized);//call controller				
			}
		});		
	}
	
	/**
	 * Starts the window of file chooser in the form of Open dialog.
	 */
	public void activate(){
		jFileChooserCustomized.showOpenDialog(MainFrames.getMainFrame());  	
	}
}
