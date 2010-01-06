package ussr.aGui.enumerations;

import javax.swing.JOptionPane;

import ussr.aGui.MainFrames;

/**
 * Contains JOptionPanes used to inform the user about wrong/inconsistent XML file loading. 
 * @author Konstantinas
 * NOTE NR1: New JOptionPanes can be added if there is a need for that.
 * NOTE NR2: You can display new JOptionPane in this way:JOptionPaneMessages.LOADED_SIMULATION_FILE_IS_ROBOT.displayMessage();
 */
public enum JOptionPaneMessages {

	LOADED_SIMUL_OR_ROBOT_FILE_INCONSISTENT(JOptionPaneType.MESSAGE,"Wrong XML file format","You are attempting to load simulation(or robot) from XML file not compatible with USSR.",JOptionPane.ERROR_MESSAGE),
	LOADED_SIMULATION_FILE_IS_ROBOT(JOptionPaneType.MESSAGE,"Wrong XML file format","You are attempting to load Robot instead of expected Simulation XML file.",JOptionPane.ERROR_MESSAGE),
	LOADED_ROBOT_FILE_IS_SIMULATION(JOptionPaneType.MESSAGE,"Wrong XML file format","You are attempting to load Simulation instead of expected Robot XML file.",JOptionPane.ERROR_MESSAGE),
	
	SAVE_CURRENT_SIMULATION(JOptionPaneType.CONFIRMATION,"Save current simulation before continue?","Save current simulation?",JOptionPane.YES_NO_CANCEL_OPTION),
	;

	
	public enum JOptionPaneType{
		MESSAGE,
		CONFIRMATION;		
	}

    private JOptionPaneType jOptionPaneType;
    
	/**
	 * The title and message text displayed in JOptionPane.
	 */
	private String title,messageText;

	/**
	 * The type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on. 
	 */
	private int type;

	/**
	 *  JOptionPane created from parameters passed above.
	 */
	private JOptionPane jOptionPaneMessage;

	/**
	 * Contains JOptionPanes used to inform the user about wrong/inconsistent XML file loading.
	 * @param title, the title displayed in JOptionPane.
	 * @param messageText, the message text displayed in JOptionPane.
	 * @param type, the type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on. 
	 */
	JOptionPaneMessages(JOptionPaneType jOptionPaneType,String title, String messageText, int type){
		this.jOptionPaneType = jOptionPaneType;
		this.title = title;
		this.messageText= messageText;
		this.type = type;
		this.jOptionPaneMessage = new JOptionPane();
		MainFrames.changeToLookAndFeel(this.jOptionPaneMessage);
	}

	/**
	 * Displays chosen JOptionPane.
	 */
	public Object displayMessage(){
		Object returnValue = null;
		switch(this.jOptionPaneType){
		case MESSAGE:
			jOptionPaneMessage.showMessageDialog(MainFrames.getMainFrame(),this.messageText,this.title,this.type);
			returnValue = "void";
			break;
		case CONFIRMATION:
			returnValue = JOptionPane.showConfirmDialog(MainFrames.getMainFrame(), this.messageText, this.title,this.type);
			break;
		}		
		return returnValue;
	}
	
	
}
