package ussr.aGui.enumerations.hintpanel;

/**
 * Contains hints displayed in Display for hints for Tab called Assign Controllers.
 * NOTE Nr.1: In oder to change the hint text just modify it here.
 * @author Konstantinas
 *
 */
public enum HintsAssignControllersTab  implements HintsTabsInter  {

	DEFAULT("Follow hints displayed here to explore all available functionality. When done assigning controllers save simulation in XML file and run it (green arrow in the lop-left corner of main window)"),
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
