package ussr.aGui.enumerations;

import javax.swing.JOptionPane;

import ussr.aGui.MainFrames;

public enum JOptionPaneMessages {

	
	LOADED_SIMUL_OR_ROBOT_FILE_INCONSISTENT("Wrong XML file format","You are attempting to load simulation(or robot) from XML file not compatible with USSR.",JOptionPane.ERROR_MESSAGE),
	LOADED_SIMULATION_FILE_IS_ROBOT("Wrong XML file format","You are attempting to load Robot instead of expected Simulation XML file.",JOptionPane.ERROR_MESSAGE),
	LOADED_ROBOT_FILE_IS_SIMULATION("Wrong XML file format","You are attempting to load Simulation instead of expected Robot XML file.",JOptionPane.ERROR_MESSAGE);
	
	private int type;
	
	private JOptionPane message;
	
	private String title,messageText;
	
	JOptionPaneMessages(String title, String messageText, int type){
		this.title = title;
		this.messageText= messageText;
		this.type = type;
		this.message = new JOptionPane();
		MainFrames.changeToLookAndFeel(this.message);
	}
	
	public void displayMessage(){
		message.showMessageDialog(null,this.messageText,this.title,this.type);
	}
	
	
	
}
