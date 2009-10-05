package ussr.aGui;

public class FileChooserSave extends FileChooser  {
	
	public FileChooserSave() {	
		initComponents();
		changeToSaveDialog();			
	}
	
	private void changeToSaveDialog(){
		jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
		jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jFileChooser1ActionPerformed(evt);
			}
		});
		setTitle("Save simulation in the file");
	}
	
	
	/**
	 * Starts the window of file chooser in the form of open dialog.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				new FileChooserSave();
				setVisible(true);
			}
		});    	
	}
	
	private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
		String command = evt.getActionCommand();//Selected button command
		if(command.equalsIgnoreCase("ApproveSelection")  ){		        
			String fileDirectoryName = jFileChooser1.getSelectedFile().toString();
			System.out.println("File(Save):"+ fileDirectoryName);
			//saveLoadXML.saveXMLfile(fileDirectoryName);			
			this.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase("CancelSelection")){//Cancel pressed			
			this.dispose();//close the frame(window)
		}
	}       
	
}
