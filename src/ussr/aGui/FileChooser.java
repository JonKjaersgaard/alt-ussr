package ussr.aGui;

/**
 * The main responsibility of this class is to display file chooser in two types(forms):
 * 1) open dialog and 2) save dialog.
 * @author  Konstantinas
 */
public abstract class FileChooser extends GuiFrames{

	protected javax.swing.JFileChooser jFileChooser1;
	
	/** This method is called from within the constructor to
	 * initialize the form(frame).
	 */	
	protected void initComponents() {

		jFileChooser1 = new javax.swing.JFileChooser();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new java.awt.FlowLayout());		
		jFileChooser1.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
		
		getContentPane().add(jFileChooser1);
		jFileChooser1.setAcceptAllFileFilterUsed(false);
		jFileChooser1.setFileFilter(new FileFilter (".xml"));//limit file choosing to only one (XML)type
		pack();
		changeToSetLookAndFeel(this);
		setSize(580,450);
	}
}
