package ussr.aGui.tabs.enumerations.hintPanel;


/**
 * Serves as an example for keeping hints used(referenced) from  your new tab implementation.
 * @author Konstantinas
 *
 */
public enum HintsYourNewTab implements HintsTabsInter {
	
	DEFAULT("The purpose of this tab is to exemplify how your custom projects can be integrated into the GUI as a separate tab. Look in USSR source code \"ussr.aGui.tabs.YourNewTab.java\" for more."),
	;

	/**
	 * The text of hint.
	 */
	private String hintText;
	
	
	/**
	 * Contains build in hints for tab called "YourNewTab". Hints are displayed in Display for hints(panel).
	 * @param hintText, the hint text.
	 */
	HintsYourNewTab(String hintText){
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
