package ussr.aGui;

import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.tabs.ConsoleTab;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.YourNewTab;
import ussr.aGui.tabs.constructionTabs.AssignControllerTab;
import ussr.aGui.tabs.constructionTabs.ConstructRobotTab;
import ussr.aGui.tabs.constructionTabs.AssignLabelsTab;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.aGui.tabs.visualizer.ModuleCommunicationVisualizer;


/**
 * Supports all instances of main GUI window with common constants.
 * @author Konstantinas
 */
public interface MainFramesInter {

	/**
	 * The directory, for keeping jpg icons used in the main frame design.
	 */
	public static final String DIRECTORY_ICONS = "resources/mainFrame/icons/jpg/";
	
	/**
	 * The location of xml file describing default(new) simulation.
	 */
	public static final String LOCATION_DEFAULT_NEW_SIMULATION = "resources/mainFrame/newSimulation/newSimulation.xml";

	/**
	 * The default extension of each icon. 
	 */
	//public final String DEFAULT_ICON_EXTENSION = ".jpg";
	public static final String DEFAULT_ICON_EXTENSION1 = ".png";
	
	/**
	 * The names(file names) of the icons used in the main frame.
	 */
	public static final String   
	                    /*Open and Save*/	
	                    NEW_SIMULATION = "newSimulation", NEW_SIMULATION_ROLLOVER = "newSimulationRollover",NEW_SIMULATION_DISABLED = "newSimulationDisabled",
	                    NEW_SIMULATION_SMALL ="newSimulationSmall", NEW_SIMULATION_SMALL_DISABLED ="newSimulationSmallDisabled", 
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
	                    TABBED_PANES ="tabbedPanes",INTERACTION_MAXIMIZED = "interactionMaximized",DEBUGGING_MAXIMIZED ="debuggingMaximized",
	                    TABBED_PANES_BOTH_SELECTED = "tabbedPanesBothSelected",TABBED_PANES_DISABLED = "tabbedPanesDisabled",
	                    CONSOLE = "console",
    /*YOUR TAB*/        YOUR_NEW_TAB ="yourNewTab"
	                    ; 
	/**
	 * The titles of the tabs plugged in the main GUI window.
	 */
	public static final String CONSTRUCT_ROBOT_TAB_TITLE = "Construct Robot",
	                    ASSIGN_BEHAVIORS_TAB_TITLE = "Assign Controller",
	                    ASSIGN_LABELS_TAB_TITLE = "Assign Labels",
	                    MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE = "Communication Visualiser",
	                    SIMULATION_TAB_TITLE = "Simulation",
	                    CONSOLE_TAB_TITLE = "Console",
/*YOUR TAB TITLE*/      YOUR_TAB_TITLE = "Your Tab"
	                    ;
	
	/**
	 * The directory of icon displayed on tabs related to construction of modular robot.
	 */
	public static final String contructRobotIconDirectory = MainFrameIcons.CONSTRUCT_ROBOT_ROLLOVER.getImageDirectory(); 
	
	/**     
	 * Instances of tabs plugged in main GUI window
	 */
	public static final TabsInter  CONSTRUCT_ROBOT_TAB = new ConstructRobotTab(false,true,CONSTRUCT_ROBOT_TAB_TITLE,contructRobotIconDirectory),
	                        ASSIGN_BEHAVIORS_TAB = new AssignControllerTab(false,true,ASSIGN_BEHAVIORS_TAB_TITLE,contructRobotIconDirectory),
	                        ASSIGN_LABELS_TAB = new AssignLabelsTab(false,true,ASSIGN_LABELS_TAB_TITLE,contructRobotIconDirectory),
	                        MODULE_COMMUNICATION_VISUALIZER_TAB = new ModuleCommunicationVisualizer(false,true,MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE,MainFrameIcons.VISUALIZER_ROLLOVER.getImageDirectory()),
	                        SIMULATION_CONFIGURATION_TAB = new SimulationTab(true,true,SIMULATION_TAB_TITLE,FramesInter.DIRECTORY_USSR_ICON),
	                        CONSOLE_TAB = new ConsoleTab(true,false,CONSOLE_TAB_TITLE, MainFrameIcons.CONSOLE.getImageDirectory()),
              /*YOUR TAB*/  YOUR_TAB = new YourNewTab(true,true, YOUR_TAB_TITLE, MainFrameIcons.YOUR_NEW_TAB.getImageDirectory())
	                        ;

	/**
	 * Array of tabs plugged in the Main frame.
	 */
	public static final TabsInter[] ALL_TABS = {
			                          CONSTRUCT_ROBOT_TAB,
		                              ASSIGN_BEHAVIORS_TAB,		                             
		                              MODULE_COMMUNICATION_VISUALIZER_TAB,
		                              SIMULATION_CONFIGURATION_TAB,
		                              CONSOLE_TAB,
		               /* YOUR TAB */ YOUR_TAB,
		                              ASSIGN_LABELS_TAB
		                                 };
	
}
