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
	
	CONSTRUCT_ROBOT_TAB_LIMITATION(JOptionPaneType.OPTION,"Too many robots in simulation environment.","Robot construction is limited to single robot at a time."+"\n Because all modules in simulation enviroment are saved as single robot xml file.",
			                       JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, new Object[]{"Start new robot","Keep first robot","Continue anyway"},1),
	;

	
	public enum JOptionPaneType{
		MESSAGE,
		CONFIRMATION,
		OPTION;		
	}

    private JOptionPaneType jOptionPaneType;
    
	/**
	 * The title and message text displayed in JOptionPane.
	 */
	private String title,messageText;

	/**
	 * The type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on. 
	 */
	private int optionType,messageType;

	/**
	 *  JOptionPane created from parameters passed above.
	 */
	private JOptionPane jOptionPaneMessage;

	/**
	 * Contains JOptionPanes used to inform the user about wrong/inconsistent XML file loading.
	 * @param title, the title displayed in JOptionPane.
	 * @param messageText, the message text displayed in JOptionPane.
	 * @param optionType, the type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on. 
	 */
	JOptionPaneMessages(JOptionPaneType jOptionPaneType,String title, String messageText, int optionType){
		this.jOptionPaneType = jOptionPaneType;
		this.title = title;
		this.messageText= messageText;
		this.optionType = optionType;
		
		this.jOptionPaneMessage = new JOptionPane();
		MainFrames.changeToLookAndFeel(this.jOptionPaneMessage);
	}
	
	private Object[] options;
	private int selectedOptionIndex;
	/**
	 * Contains JOptionPanes used to inform the user about wrong/inconsistent XML file loading.
	 * @param title, the title displayed in JOptionPane.
	 * @param messageText, the message text displayed in JOptionPane.
	 * @param optionType, the type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on.
	 * @param TODO 
	 */
	JOptionPaneMessages(JOptionPaneType jOptionPaneType,String title, String messageText, int optionType, int messageType,Object[] options, int selectedOptionIndex){
		this.jOptionPaneType = jOptionPaneType;
		this.title = title;
		this.messageText= messageText;
		this.optionType = optionType;
		this.messageType = messageType;
		this.options = options;
		this.selectedOptionIndex = selectedOptionIndex;
		
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
			JOptionPane.showMessageDialog(MainFrames.getMainFrame(),this.messageText,this.title,this.optionType);
			returnValue = null;
			break;
		case CONFIRMATION:
			returnValue = JOptionPane.showConfirmDialog(MainFrames.getMainFrame(), this.messageText, this.title,this.optionType);
			break;
		case OPTION:	
			Object[] options = {"Yes, please","No, thanks", "No eggs, no ham!"};
			returnValue = JOptionPane.showOptionDialog(MainFrames.getMainFrame(),this.messageText,this.title,this.optionType,this.messageType,null,this.options,this.selectedOptionIndex);
			break;
			default: throw new Error("The OptionPane named as: "+this.jOptionPaneType.name()+" is not supported yet.");
		}		
		return returnValue;
	}
	
	
}
