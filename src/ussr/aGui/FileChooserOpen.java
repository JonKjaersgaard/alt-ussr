package ussr.aGui;

public class FileChooserOpen extends FileChooser  {	
	
	public FileChooserOpen() {	
		initComponents();
		changeToOpenDialog();						
	}
	
	private void changeToOpenDialog(){
		jFileChooser1.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
		jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jFileChooser1ActionPerformed(evt);
			}
		});
		setTitle("Open simulation from file");
	}
	
	
	/**
	 * Starts the window of file chooser.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				new FileChooserOpen();
				setVisible(true);
			}
		});    	
	}
	
	
	private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt){
		String command = evt.getActionCommand();//Selected button command
		
	  if(command.equalsIgnoreCase("ApproveSelection") ){ 		
			String fileDirectoryName = jFileChooser1.getSelectedFile().toString();
			System.out.println("File(Open):"+ fileDirectoryName);
			//saveLoadXML.loadXMLfile(fileDirectoryName);	
			this.dispose(); //close the frame(window)
			
		}else if (command.equalsIgnoreCase("CancelSelection")){//Cancel pressed			
			this.dispose();//close the frame(window)
		}
	} 
	
}
