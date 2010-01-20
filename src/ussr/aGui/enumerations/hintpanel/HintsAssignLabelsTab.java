package ussr.aGui.enumerations.hintpanel;

/**
 * Contains hints displayed in Display for hints for Tab called Assign Labels.
 * NOTE Nr.1: In oder to change the hint text just modify it here.
 * @author Konstantinas
 *
 */
public enum HintsAssignLabelsTab implements HintsTabsInter{

	DEFAULT("This tab is dedicated to assignment of labels, which are unique idetifies used during implementation of controllers. Use this functionality if you are well aware of consequences. Begin with choosing an entity to read/assign the labels."),
	
    ENTITY_CHOSEN("Select chosen entity in simulation environment in order to read in its labels in the table. Next modify the labels in the table(delete or type in desired ones) and select button for assigning labels."),
	
	ASSIGN_LABELS("Select chosen entity in simulation environment in order to assign to it the labels displayed in the table. Next use read labels button to make sure the labels were assigned to desired entity.");
	;
	
	
	/**
	 * The text of hint.
	 */
	private String hintText;
	
	/**
	 * Contains build in hints for tab called "Assign Labels". Hints are displayed in Display for hints(panel).
	 * @param hintText, the hint text.
	 */
	HintsAssignLabelsTab(String hintText){
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
