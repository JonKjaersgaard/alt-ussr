package ussr.aGui.fileChooser;

import ussr.aGui.GuiFrames;

/**
 * Manages the FileChooser in two dialog forms: Open and Save.
 * Limits the file choosing to ".xml" extension   
 * @author  Konstantinas
 */
public abstract class FileChooserFrame extends GuiFrames{

	/**
	 * The file chooser
	 */
	protected javax.swing.JFileChooser jFileChooser;
	
	
	protected String fileExtension;
	
	/** 
	 * This method is called from within the constructor to initialize the form(frame) of the file chooser.
	 */	
	protected void initComponents() {

		jFileChooser = new javax.swing.JFileChooser();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new java.awt.FlowLayout());		
		jFileChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
		
		getContentPane().add(jFileChooser);
		jFileChooser.setAcceptAllFileFilterUsed(false);		
		pack();
		changeToSetLookAndFeel(this);// for all platforms
		setSize(580,450);//THINK MORE HERE
	}
	//ADD POSSIBILITY  TO HAVE SEVERAL FILE FILTERS
	public void setFilesToFilter(String fileExtension){
		jFileChooser.setFileFilter(new FileFilter (fileExtension));//limit file choosing to only one file type
	}
}
