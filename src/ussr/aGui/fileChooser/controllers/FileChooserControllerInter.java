package ussr.aGui.fileChooser.controllers;

import ussr.physics.jme.JMESimulation;

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

}
