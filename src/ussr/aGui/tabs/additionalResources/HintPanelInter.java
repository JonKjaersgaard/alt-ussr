package ussr.aGui.tabs.additionalResources;

public interface HintPanelInter {

	public final String[] builInHints = 
	                             /*0*/   {"Follow hints displayed here to explore all available functionality. When done constructing robot go to next tab.",
			
			                     /*1*/    "Now locate newly added module in the simulation environment. Hold right side of the mouse to look around and W,A,S,D to move. Next there are two options: 1)" +
			                             "Apply rotation to newly added module or 2) Zoom in closer to module and select connectors (black-white geometric shapes)", 
			                     
			                     /*2*/   "Zoon in closer and select newly added module. If the first selection was unsuccessful, try to select module several times.",
			                     
			                     /*3*/   "Select module (robot) and move it with movement of the mouse. Right side of the mouse should be pressed during this process.",
	           	                        
	                             /*4*/   "Select module (robot) to delete.",	
	            
	                             /*5*/   "Select module to delete. Not recommended to use, if only one module exists in simulation environment.",
	                             
	                             /*6*/   "Select module to color its connectors with color coding (nr. of connector - color). Here:0-Black, 1-Red,"+
                                          " 2-Cyan, 3-Grey, 4-Green, 5-Magenta, 6-Orange, 7-Pink, 8-Blue, 9-White, 10-Yellow, 11-Light Grey",
	                             
                                 /*7*/   "Zoom in closer to module and select connectors (black-white geometric shapes). On each selected module will be connected new.",
                                 
                                 /*8*/   "Select module to add new module on chosen  number of connector.",
                                 
                                 /*9*/   "Select module to add new modules on all connectors. Next use button Delete to remove excessive modules."+
                                         "It is not recommended to have modules overlaping with the ground plane.",
                                 
                                /*10*/   "Select module to add new module on initial connector. Next use green arows to move module from one connector onto another." +
                                		 "When desired placement achieved, repeat the sequence of actions for next modules.",
                        
	"NEW HINT"};
	
	public enum HintPanelTypesOfIcons {

		DEFAULT,// just dummy for initialization
		ATTENTION,
		INFORMATION, 
		ERROR;
	}
}
