package ussr.aGui.enumerations;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import ussr.aGui.GuiFrames;
import ussr.aGui.MainFrames;

/**
 * Contains  a number of JOptionPanes used to inform the user about wrong/inconsistent XML file loading and so on. 
 * @author Konstantinas
 * NOTE NR1: New JOptionPanes can be added if there is a need for that.
 * NOTE NR2: You can display new JOptionPane in this way:JOptionPaneMessages.LOADED_SIMULATION_FILE_IS_ROBOT.displayMessage();
 */
public enum JOptionPaneMessages {

	LOADED_SIMUL_OR_ROBOT_FILE_INCONSISTENT(JOptionPaneType.MESSAGE,"Wrong XML file format",new Object[]{"You are attempting to load simulation(or robot) from XML file not compatible with USSR."},JOptionPane.ERROR_MESSAGE),
	LOADED_SIMULATION_FILE_IS_ROBOT(JOptionPaneType.MESSAGE,"Wrong XML file format",new Object []{"You are attempting to load Robot instead of expected Simulation XML file."},JOptionPane.ERROR_MESSAGE),
	LOADED_ROBOT_FILE_IS_SIMULATION(JOptionPaneType.MESSAGE,"Wrong XML file format",new Object[]{"You are attempting to load Simulation instead of expected Robot XML file."},JOptionPane.ERROR_MESSAGE),
	ROBOT_XML_FILE_NOT_FOUND(JOptionPaneType.MESSAGE,"Robot XML file not found!",new Object[]{"Was not able to find Robot XML file in the following directory:\n"},JOptionPane.WARNING_MESSAGE),
	
	SAVE_CURRENT_SIMULATION(JOptionPaneType.CONFIRMATION,"Save current simulation before continue?",new Object[]{"Save current simulation?"},JOptionPane.YES_NO_CANCEL_OPTION),
	
	ROBOT_CONSTRUCTION_TABS_LIMITATION(JOptionPaneType.OPTION,"Too many robots in simulation environment.",new Object[]{"Robot construction is limited to single robot at a time."+"\nBecause all modules in simulation enviroment are saved as single robot xml file.", new JCheckBox("Remember my choice and do not show this message again.")},
			                       JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, new Object[]{"Start new robot","Continue anyway","Cancel"},1),
			                       
	// YOUR NEW JOptionPane		                       
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
	private String title;
	
	private Object[] message;

	/**
	 * The type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on. 
	 */
	private int optionType,messageType;

	/**
	 * Contains JOptionPanes used to inform the user about wrong/inconsistent XML file loading.
	 * @param title, the title displayed in JOptionPane.
	 * @param messageText, the message text displayed in JOptionPane.
	 * @param optionType, the type of JOptionPane(the icon to display). For instance ERROR,INFORMATION and so on. 
	 */
	JOptionPaneMessages(JOptionPaneType jOptionPaneType,String title, Object[] message, int optionType){
		this.jOptionPaneType = jOptionPaneType;
		this.title = title;
		this.message= message;
		this.optionType = optionType;
	}
	
	public void setMessage(Object[] message) {
		this.message = message;
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
	JOptionPaneMessages(JOptionPaneType jOptionPaneType,String title, Object[] message, int optionType, int messageType,Object[] options, int selectedOptionIndex){
		this.jOptionPaneType = jOptionPaneType;
		this.title = title;
		this.message= message;
		this.optionType = optionType;
		this.messageType = messageType;
		this.options = options;
		this.selectedOptionIndex = selectedOptionIndex;
	}
	
	public Object[] getMessage() {
		return message;
	}

	/**
	 * Displays chosen JOptionPane.
	 */
	public Object displayMessage(){
		
		GuiFrames.changeToLookAndFeel(new JOptionPane());
		Object returnValue = null;
		switch(this.jOptionPaneType){
		
		case MESSAGE:			
			JOptionPane.showMessageDialog(MainFrames.getMainFrame(),this.message,this.title,this.optionType);
			returnValue = null;
			break;
		case CONFIRMATION:
			returnValue = JOptionPane.showConfirmDialog(MainFrames.getMainFrame(), this.message, this.title,this.optionType);
			break;
		case OPTION:	
			returnValue = JOptionPane.showOptionDialog(MainFrames.getMainFrame(), this.message, this.title, this.optionType, this.messageType, null,this.options,this.selectedOptionIndex);   
			break;
			default: throw new Error("The JOptionPane named as: "+this.jOptionPaneType.name()+" is not supported yet.");
		}		
		return returnValue;
	}
	
	
}
