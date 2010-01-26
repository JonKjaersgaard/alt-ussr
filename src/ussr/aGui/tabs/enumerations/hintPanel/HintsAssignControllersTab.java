package ussr.aGui.tabs.enumerations.hintPanel;


/**
 * Contains hints displayed in Display for hints for Tab called Assign Controllers.
 * NOTE Nr.1: In oder to change the hint text just modify it here.
 * @author Konstantinas
 *
 */
public enum HintsAssignControllersTab  implements HintsTabsInter  {

	DEFAULT("This tab allows to experiment with interactive assignment of primitive controllers for each supported modular robot. Begin with choosing one of supported modular robots."),
	
	TAB_AVAILABLE_DURING_RUNNING_SIMULATION("This tab will be available during running simulation!"),
	;
	
	
	/**
	 * The text of hint.
	 */
	private String hintText;
	
	/**
	 * Contains build in hints for tab called "Assign Controller". Hints are displayed in Display for hints(panel).
	 * @param hintText, the hint text.
	 */
	HintsAssignControllersTab(String hintText){
		this.hintText=hintText;
	}
	
	/**
	 * Returns the text of hint.
	 * @return the text of hint.
	 */
	public String getHintText() {
		return hintText;
	}
	
}
