package ussr.aGui.fileChooser.controllers;

import ussr.physics.jme.JMESimulation;

public abstract class FileChooserController implements FileChooserControllerInter  {

	protected JMESimulation jmeSimulation;
	
    public abstract void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	public abstract  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
}
