package ussr.aGui.tabs.additionalResources;

public interface HintPanelInter {

	public final String[] builInHints = 
	                             /*0*/   {"Follow hints displayed here to explore all available functionality.  ",
			
			                     /*1*/    "Now locate newly added module in the simulation environment. Hold right side of the mouse to look around and W,A,S,D to move. Next there are two options: 1)" +
			                             "Apply rotation to newly added module or 2) Zoom in closer to module and select connectors (black-white geometric shapes)", 
			
		                        "New hint"};
	
	public enum HintPanelTypesOfIcons {

		DEFAULT,// just dummy for initialization
		ATTENTION,
		INFORMATION, 
		ERROR;
	}
}
