package ussr.aGui.fileChooser.view;

import java.util.ArrayList;

import ussr.aGui.GuiFrames;

import ussr.aGui.fileChooser.FileFilter;
import ussr.aGui.fileChooser.controller.FileChooserControllerInter;
import ussr.physics.jme.JMESimulation;

/**
 * Manages the FileChooser in two dialog forms: Open and Save.
 * Limits the file choosing to extensions specified in fileExtensions ArrayList.
 * Currently supports filtering of only .xml files.    
 * @author  Konstantinas
 */
public abstract class FileChooserFrame extends GuiFrames{

	/**
	 * The file chooser appearance, which is integrated into the frame.
	 */
	protected javax.swing.JFileChooser jFileChooser;

	/**
	 * Extensions of the files, which will be available to filter out by the file chooser
	 */
	protected ArrayList<String> fileExtensions;

	/**
	 * The controllers for each file extension.
	 */
	protected ArrayList<FileChooserControllerInter> fileChooserControllers;
	
	/** 
	 * This method is called from within the constructor to initialize the form(frame) of the file chooser.
	 */	
	protected void initComponents() {

		jFileChooser = new javax.swing.JFileChooser();
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);		
		getContentPane().setLayout(new java.awt.FlowLayout());		
		getContentPane().add(jFileChooser);
		jFileChooser.setAcceptAllFileFilterUsed(false);		
		pack();
		changeToSetLookAndFeel(this);// for all platforms
		setSize(580,450);//THINK MORE HERE
	}	

	/**
	 * Sets the file extensions for file chooser to filter out (file filters).
	 * @param fileExtensions, the ArrayList containing the file extensions in the format (for instance: ".xml").
	 */
	public void setFilesToFilter(ArrayList<String> fileExtensions){		
		for (int index=0; index<fileExtensions.size(); index++){
			if (fileExtensions.get(index).indexOf(".")==0){// if format has "." in the beginning. For example ".xml"
				jFileChooser.setFileFilter(new FileFilter (fileExtensions.get(index)));
				//TODO setController(index);				
			}else{
				throw new Error("Wrong format of file extension. Point is missing in the beginning. It should be for instance: .xml");
			}
		}
	}
	
//TODO ADD setting of controllers depending on file extension(Here experimenting).
/*	public void setController(int index){

		int sizeExtensions = fileExtensions.size();
		int sizeControllers = fileChooserControllers.size();		
		//if(sizeExtensions>1&&sizeControllers>1){
		//TODO For future implementation. Add calls for controllers if there will be more file extensions than one.
		//throw new Error("The implementation is missing for more than one extension");
		if (sizeExtensions==sizeControllers){
										
				addFileChooserActionListener(fileChooserControllers.get(index));
			
		}else{
			throw new Error("The amount of file extensions is not matching the amount of controllers to control them ");
		}

	}*/

/*	private void addFileChooserActionListener(final FileChooserControllerInter fileChooserController){
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Some");
				if (fcFrame instanceof FileChooserOpenFrame){
					System.out.println("Some1");
					fileChooserController.controlOpenDialog(evt, jFileChooser, fcFrame);
				}else if (fcFrame instanceof FileChooserSaveFrame) {
					System.out.println("Some2");
					fileChooserController.controlSaveDialog(evt, jFileChooser, fcFrame);
				}				
			}
		});
	}*/

}
