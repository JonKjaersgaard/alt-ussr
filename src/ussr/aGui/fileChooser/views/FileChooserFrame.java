package ussr.aGui.fileChooser.views;
import java.util.ArrayList;

import ussr.aGui.GuiFrames;
import ussr.aGui.fileChooser.FileFilter;
import ussr.aGui.fileChooser.controllers.FileChooserControllerInter;


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
	 * The controller for file extension.
	 */
	protected FileChooserControllerInter fileChooserController;
	
	public FileChooserFrame(ArrayList<String> fileExtensions, FileChooserControllerInter fileChooserController){
		this.fileExtensions= fileExtensions;
		this.fileChooserController= fileChooserController;
		initComponents();
	}
	
	
	/** 
	 * This method is called from within the constructor to initialize the form(frame) of the file chooser.
	 */	
	public void initComponents() {

		jFileChooser = new javax.swing.JFileChooser();
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);		
		getContentPane().setLayout(new java.awt.FlowLayout());	
		jFileChooser.setAcceptAllFileFilterUsed(false);	
		getContentPane().add(jFileChooser);// MAC HAS PROBLEMS WITH THAT		
		pack();
		changeToLookAndFeel(this);// for all platforms
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
			}else{
				throw new Error("Wrong format of file extension. Point is missing in the beginning. It should be for instance: .xml");
			}
		}
	}
	
}
