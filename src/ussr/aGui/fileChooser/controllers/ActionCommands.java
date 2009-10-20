package ussr.aGui.fileChooser.controllers;

/**
 * Commands supported by file chooser. ApproveSelection command is for Open or Save button, depending
 * on the form of file chooser (Open or Save dialog respectively).CancelSelection is common for both
 * forms.
 * @author Konstantinas
 */
public enum ActionCommands {
	
	/*Command for Open or Save button, depending on the form of file chooser (Open or Save dialog respectively)*/
	APPROVESELECTION,
	/*Command, which is common for both forms*/
    CANCELSELECTION;

}
