package ussr.aGui.tabs.views.constructionTabs;

import java.awt.GridBagConstraints;

public interface ConstructionTabsInter {

	/**
	 * The constrains of grid bag layout used during design of both tabs.
	 */
	public GridBagConstraints gridBagConstraints = new GridBagConstraints();
	
	/**
	 * Keeps strings used to set the text value on components. setText() method.
	 * @author Konstantinas
	 */
	public enum CommonJComponentsText{
		/*Modular robots names on radio buttons*/
		ATRON,Odin,MTran,CKBotStandard,		
	}
}
