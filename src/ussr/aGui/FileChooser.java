package ussr.aGui;

import ussr.builder.BuilderMultiRobotSimulation;
import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.physics.jme.JMESimulation;

/**
 * The main responsibility of this class is to display file chooser in two types(forms):
 * 1) open dialog and 2) save dialog.
 * @author  Konstantinas
 */
public class FileChooser extends GuiFrame{

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;

	private SaveLoadXMLFileTemplate saveLoadXML ;

	/**
	 * The type of FileChooser. For example true if it is open dialog and false if it is save dialog 
	 */
	private boolean type;




	/** Creates new form FileChooser */
	public FileChooser(/*JMESimulation simulation, boolean type*/) {
		this.simulation = simulation;
		this.type = type;
		initComponents();        
		changeOpenSaveDialog(type);
		changeToSetLookAndFeel(this);
		//this.saveLoadXML =  new InSimulationXMLSerializer(this.simulation); 
	}

	/** This method is called from within the constructor to
	 * initialize the form(frame).
	 */	
	private void initComponents() {

		jFileChooser1 = new javax.swing.JFileChooser();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Choose the file");
		getContentPane().setLayout(new java.awt.FlowLayout());

		jFileChooser1.setDialogTitle("Choose the file to save or open");
		jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
		jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				jFileChooser1ActionPerformed(evt);

			}
		});
		getContentPane().add(jFileChooser1);
		jFileChooser1.setAcceptAllFileFilterUsed(false);
		jFileChooser1.setFileFilter(new FileFilter (".xml"));//limit file choosing to only one (XML)type
		pack();
	}

	/**
	 * Changes the type of dialog window to Open or Save
	 * @param type, true if Open  and false if Save dialog
	 */
	private void changeOpenSaveDialog(boolean type){
		if (type){
			jFileChooser1.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
			jFileChooser1.setDialogTitle("Open file");
		}else {
			jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
			jFileChooser1.setDialogTitle("Save as");
		}
	}


	private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
		String command = evt.getActionCommand();//Selected button command
		if(command.equalsIgnoreCase("ApproveSelection") && this.type == false){//Save dialog			        
			String fileDirectoryName = jFileChooser1.getSelectedFile().toString();			
			saveLoadXML.saveXMLfile(fileDirectoryName);			
			this.dispose();//close the frame(window)
			
		}else if(command.equalsIgnoreCase("ApproveSelection") && this.type == true ){//Open dialog       
			new BuilderMultiRobotSimulation();
			//String fileDirectoryName = jFileChooser1.getSelectedFile().toString();
			//saveLoadXML.loadXMLfile(fileDirectoryName);	
			//this.dispose(); //close the frame(window)
			
		}else if (command.equalsIgnoreCase("CancelSelection")){//Cancel pressed			
			this.dispose();//close the frame(window)
		}
	}                                             

	/**
	 * Starts the window of file chooser.
	 */
	public void activate(){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() {           
				FileChooser fileChooser = new FileChooser(/*simulation, type*/);
				fileChooser.setSize(580, 450);
				fileChooser.setVisible(true);
			}
		});    	
	}

	// Variables declaration - do not modify
	private javax.swing.JFileChooser jFileChooser1;
	// End of variables declaration

}
