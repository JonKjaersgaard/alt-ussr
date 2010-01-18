package ussr.aGui.fileChoosing;

import ussr.aGui.fileChoosing.fileChooser.FileChooserCustomizedInter;


/**
 * Provides methods for controlling file choosers. 
 * @author Konstantinas
 *
 */
public interface FileChooserControllerInter {
	
    /**
     * Manages the control of the file chooser in Save dialog form.
     * @param evt, event received from file chooser. This is selection of Save or Cancel buttons.
     * @param fileChooser, the file chooser appearance, which is integrated into the frame.
     * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
     */
    public  void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,FileChooserCustomizedInter fileChooserFrame);
	
	/**
	 * Manages the control of the file chooser in Open dialog form.
	 * @param evt, event received from file chooser. This is selection of Open or Cancel buttons.
	 * @param fileChooser,the file chooser appearance, which is integrated into the frame.
	 * @param fileChooserFrame, the frame in which the file chooser appearance is integrated in.
	 */
	public  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,FileChooserCustomizedInter fileChooserFrame);


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
