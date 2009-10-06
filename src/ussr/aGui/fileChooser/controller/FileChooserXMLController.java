package ussr.aGui.fileChooser.controller;

import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Controls the functionality of both forms of file chooser: Open and Save.
 * Here also manages calls for XML processing.
 * @author Konstantinas
 *
 */
public class FileChooserXMLController extends FileChooserController {

	@Override
	public void controlOpenDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		String command = evt.getActionCommand();//Selected button command
		//System.out.println("Action:"+ command);//for debugging		
	  	  if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString()) ){ 		
	  			String fileDirectoryName = fileChooser.getSelectedFile().toString();
	  			System.out.println("File(OpenAction):"+ fileDirectoryName);//for debugging
	  			//HERE LOADING  OF XML
	  			//saveLoadXML.loadXMLfile(fileDirectoryName);	
	  			fileChooserFrame.dispose(); //close the frame(window)	  			
	  		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
	  			fileChooserFrame.dispose();//close the frame(window) 	  			
	  		}		
	}

	@Override
	public void controlSaveDialog(ActionEvent evt, JFileChooser fileChooser,
			JFrame fileChooserFrame) {
		String command = evt.getActionCommand();//Selected button command
		//System.out.println("Action:"+ command);//for debugging		
		if(command.equalsIgnoreCase(ActionCommands.APPROVESELECTION.toString())  ){		        
			String fileDirectoryName = fileChooser.getSelectedFile().toString();
			System.out.println("File(Save):"+ fileDirectoryName);//for debugging
			//HERE Saving OF XML
			//saveLoadXML.saveXMLfile(fileDirectoryName);	
			fileChooserFrame.dispose();//close the frame(window)			
		}else if (command.equalsIgnoreCase(ActionCommands.CANCELSELECTION.toString())){//Cancel pressed			
			fileChooserFrame.dispose();//close the frame(window)			
		}		
	}
}
