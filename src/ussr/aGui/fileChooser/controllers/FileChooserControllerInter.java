package ussr.aGui.fileChooser.controllers;



public interface FileChooserControllerInter {
	
    /**
     * Manages the control of the file chooser in Save dialog form.
     * @param evt
     * @param fileChooser
     * @param fileChooserFrame
     */
    public  void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	/**
	 * Manages the control of the file chooser in Open dialog form.
	 * @param evt, the event of 
	 * @param fileChooser
	 * @param fileChooserFrame
	 */
	public  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);


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

}
