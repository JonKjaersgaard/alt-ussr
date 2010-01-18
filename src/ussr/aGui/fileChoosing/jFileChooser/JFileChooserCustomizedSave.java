package ussr.aGui.fileChoosing.jFileChooser;

import java.util.Map;

import ussr.aGui.MainFrames;
import ussr.aGui.fileChoosing.jFileChooser.controller.JFileChooserControllerInter;

/**
 * Defines visual appearance of the file chooser in the form of Save dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class JFileChooserCustomizedSave extends JFileChooserCustomized  {

	/**
	 * The file chooser frame in Save dialog form.
	 */
	private static JFileChooserCustomizedSave fcSaveFrame;

	/**
	 * Manages the file chooser in the form of Save dialog.
	 * @param fileExtensions,extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for file extension.
	 */
	public JFileChooserCustomizedSave(Map<String, String> fileDescriptionsAndExtensions,JFileChooserControllerInter fileChooserController) {
		super(fileDescriptionsAndExtensions,fileChooserController);			
		changeToSaveDialog();
		setFilesToFilterOutWithDescription();
	}

	/**
	 * Changes several components of file chooser so that it is Save dialog.
	 */
	private void changeToSaveDialog(){		
		jFileChooserCustomized.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);	
		if (super.fileDescriptionsAndExtensions.containsKey(FileFilterTypes.OPEN_SAVE_SIMULATION.getFileDescription())){
			super.jFileChooserCustomized.setDialogTitle(SAVE_SIMULATION_TITLE);
		}else{
			super.jFileChooserCustomized.setDialogTitle(SAVE_ROBOT_TITLE);
		}
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
		jFileChooserCustomized.showSaveDialog(MainFrames.getMainFrame());
	}
}
