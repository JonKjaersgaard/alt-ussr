package ussr.aGui.tabs.designHelpers.hintPanel;


/**
 * Supports GUI with display for hints. Giving feedback to the user(communication).
 * @author Konstantinas
 */
public interface HintPanelInter {


	public final String commonTitle = "Display for hints"; 

	/**
	 * The directory for keeping png icons used in hint panel.
	 */
	public final String DIRECTORY_ICONS = "resources\\mainFrame\\icons\\tabs\\png\\hintPanel\\";

	/**
	 * The names of icons used in different hint panel types. 
	 */
	public final String INFORMATION ="information", ERROR ="error", ATTENTION = "attention";

}
