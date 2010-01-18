package ussr.aGui.fileChoosing.fileDialog.controllers;

import ussr.aGui.fileChoosing.fileDialog.FileDialogCustomizedInter;

/**
 * Supports with common methods for controlling file dialog windows.
 * @author Konstantinas
 *
 */
public interface FileDialogControllerInter {

	/**
	 * Controls save event.
	 * @param fdSaveDialog, the instance of file dialog window in save form.
	 */
	public void controlSaveDialog(FileDialogCustomizedInter fdSaveDialog);
	
	/**
	 * Controls open event.
	 * @param fdOpenDialog, the instance of file dialog window in open form.
	 */
	public void controlOpenDialog(FileDialogCustomizedInter fdOpenDialog);
	
}
