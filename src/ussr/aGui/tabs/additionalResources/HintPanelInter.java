package ussr.aGui.tabs.additionalResources;

/**
 * Supports GUI with display for hints. Giving feedback to the user(communication).
 * @author Konstantinas
 */
public interface HintPanelInter {

	/**
	 * Array of build in hints for tab called Construct Robot
	 */
	public final String[] builInHintsConstrucRobotTab = 
	                             /*0*/   {"Follow hints displayed here to explore all available functionality. When done constructing robot go to next tab.",
			
			                     /*1*/    "Now locate newly added module in the simulation environment. Hold right side of the mouse to look around and W,A,S,D to move. Next there are two options: 1)" +
			                             "Apply rotation to newly added module or 2) Zoom in closer to module and select connectors (black-white geometric shapes)", 
			                     
			                     /*2*/   "Zoon in closer and select newly added module. If the first selection was unsuccessful, try to select module several times.",
			                     
			                     /*3*/   "Select module (robot) and move it with movement of the mouse. Right side of the mouse should be pressed during this process.",
	           	                        
	                             /*4*/   "Select module (robot) to delete.",	
	            
	                             /*5*/   "Select module to delete. Not recommended to use, if only one module exists in simulation environment.",
	                             
	                             /*6*/   "Select module to color its connectors with color coding (color - connector number ). Here:Black - 0, Red - 1,"+
                                          " Cyan - 2, Grey - 3, Green - 4, Magenta - 5, Orange - 6, Pink - 7, Blue - 8, White - 9, Yellow - 10, Light Grey -11",
	                             
                                 /*7*/   "Zoom in closer to module and select connectors (black-white geometric shapes). On each selected module will be connected new.",
                                 
                                 /*8*/   "Select module to add new module on chosen  number of connector.",
                                 
                                 /*9*/   "Select module to add new modules on all connectors. Next use button Delete to remove excessive modules."+
                                         "It is not recommended to have modules overlaping with the ground plane.",
                                 
                                /*10*/   "Select module to add new module on initial connector. Next use green arows to move module from one connector onto another." +
                                		 "When desired placement achieved, repeat the sequence of actions for next modules.",
                                
                                /*11*/   "Choose modular robot type from four available (ATRON, Odin  and so on).",
                                
                                /*12*/   "This tab is only available before simulation is started!",
                        
	"NEW HINT"};
	
	
	
	
	/**
	 * Array of build in hints for tab called Assign Behavior
	 */
	public final String[] builInHintsAssignBehaviorTab = 
	                             /*0*/   {"Follow hints displayed here to explore all available functionality. When done assigning behaviors and labels save simulation in XML file and run simulation(green arrow in the lop-left corner of main window.). ",
	//TODO UPDATE US		
			                     /*1*/    "Select chosen entity in simulation environment in order to read in its labels. In order to select sensor select connector (black or white geometric  shapes).", 
			                     
			                     /*2*/   "Zoon in closer and select newly added module. If the first selection was unsuccessful, try to select module several times.",
			                     
			                     /*3*/   "Select module (robot) and move it with movement of the mouse. Right side of the mouse should be pressed during this process.",
	           	                        
	                             /*4*/   "Select module (robot) to delete.",	
	            
	                             /*5*/   "Select module to delete. Not recommended to use, if only one module exists in simulation environment.",
	                             
	                             /*6*/   "Select module to color its connectors with color coding (color - connector number ). Here:Black - 0, Red - 1,"+
                                          " Cyan - 2, Grey - 3, Green - 4, Magenta - 5, Orange - 6, Pink - 7, Blue - 8, White - 9, Yellow - 10, Light Grey -11",
	                             
                                 /*7*/   "Zoom in closer to module and select connectors (black-white geometric shapes). On each selected module will be connected new.",
                                 
                                 /*8*/   "Select module to add new module on chosen  number of connector.",
                                 
                                 /*9*/   "Select module to add new modules on all connectors. Next use button Delete to remove excessive modules."+
                                         "It is not recommended to have modules overlaping with the ground plane.",
                                 
                                /*10*/   "Select module to add new module on initial connector. Next use green arows to move module from one connector onto another." +
                                		 "When desired placement achieved, repeat the sequence of actions for next modules.",
                                
                                /*11*/   "Choose modular robot type from four available (ATRON, Odin  and so on).",
                                
                                /*12*/   "This tab is only available before simulation is started!",
                        
	"NEW HINT"};
	
}
