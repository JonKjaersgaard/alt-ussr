package ussr.aGui.fileChooser;

/**
 * Manages the file chooser in the form of Open dialog.
 * @author Konstantinas
 */
public class FileChooserOpenFrame extends FileChooserFrame  {	
	
	
	
	/**
	 * Manages the file chooser in the form of Open dialog.
	 */
	public FileChooserOpenFrame(String fileExtension) {
		this.fileExtension = fileExtension;
		initComponents();
		changeToOpenDialog();
		setFilesToFilter(fileExtension);
	}
	
	/**
	 * Changes several components of file chooser so that it is Open dialog.
	 */
	private void changeToOpenDialog(){
		jFileChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jFileChooser1ActionPerformed(evt);
			}
		});
		setTitle("Load simulation from the file");
	}
	
	
	/**
	 * Starts the window of file chooser in the form of Open dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				new FileChooserOpenFrame(fileExtension);
				setVisible(true);
			}
		});    	
	}
	
	
	private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt){
		String command = evt.getActionCommand();//Selected button command
		
	  if(command.equalsIgnoreCase("ApproveSelection") ){ 		
			String fileDirectoryName = jFileChooser.getSelectedFile().toString();
			System.out.println("File(Open):"+ fileDirectoryName);
			//saveLoadXML.loadXMLfile(fileDirectoryName);	
			this.dispose(); //close the frame(window)
			
		}else if (command.equalsIgnoreCase("CancelSelection")){//Cancel pressed			
			this.dispose();//close the frame(window)
		}
	} 
	
}
