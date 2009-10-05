package ussr.aGui.fileChooser;

/**
 * Manages the file chooser in the form of Save dialog.
 * @author Konstantinas
 */
public class FileChooserSaveFrame extends FileChooserFrame  {
	
	
	/**
	 * Manages the file chooser in the form of Save dialog.
	 */
	public FileChooserSaveFrame(String fileExtension) {	
		this.fileExtension = fileExtension;
		initComponents();
		changeToSaveDialog();
		setFilesToFilter(fileExtension);
	}
	
	/**
	 * Changes several components of file chooser so that it is Save dialog.
	 */
	private void changeToSaveDialog(){
		jFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
		jFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				jFileChooser1ActionPerformed(evt);
			}
		});
		setTitle("Save simulation in the file");
	}
	
	
	/**
	 * Starts the window of file chooser in the form of Save dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				new FileChooserSaveFrame(fileExtension);
				setVisible(true);
			}
		});    	
	}
	
	private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
		String command = evt.getActionCommand();//Selected button command
		if(command.equalsIgnoreCase("ApproveSelection")  ){		        
			String fileDirectoryName = jFileChooser.getSelectedFile().toString();
			System.out.println("File(Save):"+ fileDirectoryName);
			//saveLoadXML.saveXMLfile(fileDirectoryName);			
			this.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase("CancelSelection")){//Cancel pressed			
			this.dispose();//close the frame(window)
		}
	}       
	
}
