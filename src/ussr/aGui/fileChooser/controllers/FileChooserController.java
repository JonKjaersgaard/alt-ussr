package ussr.aGui.fileChooser.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import ussr.aGui.fileChooser.views.FileChooserSaveAsFrame;
import ussr.physics.jme.JMESimulation;

public abstract class FileChooserController implements FileChooserControllerInter  {

	protected JMESimulation jmeSimulation;
	
    public abstract void controlSaveDialog(java.awt.event.ActionEvent evt, javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	public abstract  void controlOpenDialog(java.awt.event.ActionEvent evt,javax.swing.JFileChooser fileChooser,javax.swing.JFrame fileChooserFrame);
	
	
	public abstract void controlSaveAsDialog(ActionEvent evt, JFileChooser fileChooser,
			FileChooserSaveAsFrame fcSaveAsFrame);
}
