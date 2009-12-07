package ussr.aGui;

import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.tabs.ConsoleTab;
import ussr.aGui.tabs.SimulationTab;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.YourNewTab;
import ussr.aGui.tabs.constructionTabs.AssignBehaviorsTab;
import ussr.aGui.tabs.constructionTabs.ConstructRobotTab;
import ussr.aGui.tabs.constructionTabs.LabelingTab;
import ussr.aGui.tabs.visualizer.ModuleCommunicationVisualizer;


/**
 * Supports all instances of main GUI window with common constants.
 * @author Konstantinas
 */
public interface MainFramesInter {

	/**
	 * The directory, for keeping jpg icons used in the main frame design.
	 */
	public final String DIRECTORY_ICONS = "resources/mainFrame/icons/jpg/";

	/**
	 * The default extension of each icon. 
	 */
	//public final String DEFAULT_ICON_EXTENSION = ".jpg";
	public final String DEFAULT_ICON_EXTENSION1 = ".png";
	
	/**
	 * The names(file names) of the icons used in the main frame.
	 */
	public final String NO_ENTRANCE ="noEntrance.jpg",
    
	                    /*Open and Save*/
                        OPEN = "open",OPEN_ROLLOVER ="openRollover",OPEN_DISABLED = "openDisabled",
                        OPEN_SMALL ="openSmall",OPEN_SMALL_DISABLED ="openSmallDisabled",
                        SAVE ="save", SAVE_ROLLOVER ="saveRollover",SAVE_DISABLED = "saveDisabled",
                        SAVE_SMALL = "saveSmall",SAVE_SMALL_DISABLED = "saveSmallDisabled",

                        EXIT_SMALL= "exitSmall",
		
	                    RUN_REAL_TIME = "runRealTime",RUN_REAL_TIME_ROLLOVER ="runRealTimeRollover",RUN_REAL_TIME_DISABLED = "runRealTimeDisabled", 
	                    RUN_FAST ="runFast", RUN_FAST_ROLLOVER = "runFastRollover",RUN_FAST_DISABLED = "runFastDisabled",
	                    RUN_STEP_BY_STEP ="runStepByStep",RUN_STEP_BY_STEP_ROLLOVER ="runStepByStepRollover",RUN_STEP_BY_STEP_DISABLED ="runStepByStepDisabled",
	                    PAUSE ="pause",PAUSE_ROLLOVER = "pauseRollover",PAUSE_DISABLED ="pauseDisabled",
	                    TERMINATE = "terminate",TERMINATE_ROLLOVER = "terminateRollover",TERMINATE_DISABLED ="terminateDisabled",
	                    RESTART ="restart",RESTART_ROLLOVER = "restartRollover",RESTART_DISABLED ="restartDisabled",
	                    CONSTRUCT_ROBOT = "constructRobot",CONSTRUCT_ROBOT_ROLLOVER ="constructRobotRollover",CONSTRUCT_ROBOT_DISABLED = "constructRobotDisabled",
	                    VISUALIZER = "visualizer",VISUALIZER_ROLLOVER ="visualizerRollover",VISUALIZER_DISABLED = "visualizerDisabled",
	                    CONSOLE = "console",
    /*YOUR TAB*/        YOUR_NEW_TAB ="yourNewTab"
	                    ; 
	                    
  
	
	/**
	 * The titles of the tabs plugged in the main GUI window.
	 */
	public final String CONSTRUCT_ROBOT_TAB_TITLE = "1 Step: Construct Robot",
	                    ASSIGN_BEHAVIORS_TAB_TITLE = "2 Step: Adjust Behaviour (Controller)",
	                    LABELING_TAB_TITLE = "Labeling",
	                    MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE = "Communication Visualiser",
	                    SIMULATION_TAB_TITLE = "Simulation",
	                    CONSOLE_TAB_TITLE = "Console",
/*YOUR TAB TITLE*/      YOUR_TAB_TITLE = "Your Tab"
	                    ;
	
	/**     
	 * Instances of tabs plugged in main GUI window
	 */
	public final TabsInter  CONSTRUCT_ROBOT_TAB = new ConstructRobotTab(false,true,CONSTRUCT_ROBOT_TAB_TITLE,MainFrameIcons.CONSTRUCT_ROBOT_ROLLOVER.getImageDirectory()),
	                        ASSIGN_BEHAVIORS_TAB = new AssignBehaviorsTab(false,true,ASSIGN_BEHAVIORS_TAB_TITLE,MainFrameIcons.CONSTRUCT_ROBOT_ROLLOVER.getImageDirectory()),
	                        LABELING_TAB = new LabelingTab(true,true,LABELING_TAB_TITLE,null),
	                        MODULE_COMMUNICATION_VISUALIZER_TAB = new ModuleCommunicationVisualizer(false,true,MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE,MainFrameIcons.VISUALIZER_ROLLOVER.getImageDirectory()),
	                        SIMULATION_CONFIGURATION_TAB = new SimulationTab(true,true,SIMULATION_TAB_TITLE,FramesInter.DIRECTORY_USSR_ICON),
	                        CONSOLE_TAB = new ConsoleTab(true,false,CONSOLE_TAB_TITLE, MainFrameIcons.CONSOLE.getImageDirectory()),
              /*YOUR TAB*/  YOUR_TAB = new YourNewTab(true,true, YOUR_TAB_TITLE, MainFrameIcons.YOUR_NEW_TAB.getImageDirectory())
	                        ;

	/**
	 * Array of tabs plugged in the Main frame.
	 */
	public final TabsInter[] TABS = {
			                          CONSTRUCT_ROBOT_TAB,
		                              ASSIGN_BEHAVIORS_TAB,		                             
		                              MODULE_COMMUNICATION_VISUALIZER_TAB,
		                              SIMULATION_CONFIGURATION_TAB,
		                              CONSOLE_TAB,
		                /*YOUR TAB*/  YOUR_TAB,
		                              LABELING_TAB
		                                 };
	
}
