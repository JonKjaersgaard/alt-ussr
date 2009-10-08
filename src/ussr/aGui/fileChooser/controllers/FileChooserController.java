package ussr.aGui.fileChooser.controllers;

public abstract class FileChooserController implements FileChooserControllerInter  {

	
	
    public abstract void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	public abstract  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
}
