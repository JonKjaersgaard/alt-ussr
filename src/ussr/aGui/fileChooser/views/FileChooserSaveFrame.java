package ussr.aGui.fileChooser.views;

import java.util.Map;

import ussr.aGui.fileChooser.FileChooserControllerInter;

/**
 * Defines visual appearance of the file chooser in the form of Save dialog.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class FileChooserSaveFrame extends FileChooserFrame  {

	/**
	 * The file chooser frame in Save dialog form.
	 */
	private static FileChooserSaveFrame fcSaveFrame;

	/**
	 * Manages the file chooser in the form of Save dialog.
	 * @param fileExtensions,extensions of the files, which will be available to filter out by the file chooser.
	 * @param fileChooserController, the controller for file extension.
	 * @param defaultDirectory, default directory to open.
	 */
	public FileChooserSaveFrame(Map<String, String> fileDescriptionsAndExtensions,FileChooserControllerInter fileChooserController,String defaultDirectory) {
		super(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);			
		changeToSaveDialog();
		setFilesToFilterOutWithDescription();
		//runBack();
	}

	//SwingWorker<JFrame,String> worker; 
	/**
	 * Changes several components of file chooser so that it is Save dialog.
	 */
	private void changeToSaveDialog(){		
		jFileChooserCustomized.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);	
		setTitle("Save");
		jFileChooserCustomized.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				fileChooserController.controlSaveDialog(evt,jFileChooserCustomized, fcSaveFrame);//call controller

			}
		});	
	}
	
	
/*	private void runBack(){
		worker = new SwingWorker<JFrame,String>() {
			   public Object construct() {
			      return "Hello" + " " + "World";
			   }

			@Override
			protected JFrame doInBackground() throws Exception {
				fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
				//fcSaveFrame.setVisible(true);
				return fcSaveFrame;
			}
			};
			
			worker.execute();
	}*/

	/**
	 * Starts the window of file chooser in the form of Save dialog.
	 */
	public void activate(){
		new Thread() {
			public void run() {
				fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
				fcSaveFrame.setVisible(true);
			}
		}.start();	
		
		/*This is too slow*/
	/*	java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() { 
				fcSaveFrame = new FileChooserSaveFrame(fileDescriptionsAndExtensions,fileChooserController,defaultDirectory);
				fcSaveFrame.setVisible(true);
			}
		}); */   	
	}
}
