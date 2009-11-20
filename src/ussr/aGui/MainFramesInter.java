package ussr.aGui;

import ussr.aGui.tabs.ConsoleTab;
import ussr.aGui.tabs.SimulationTab;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.YourNewTab;
import ussr.aGui.tabs.constructionTabs.AssignBehaviorsTab;
import ussr.aGui.tabs.constructionTabs.ConstructRobotTab;
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
	 * The names of the icons used in the main frame.
	 */
	public final String RUN_REAL_TIME = "runRealTime.jpg",RUN_FAST = "runFast.jpg", PAUSE = "pause.jpg", STEP_BY_STEP ="stepByStep.jpg",
	SAVE ="save.jpg",SAVE_SMALL ="saveSmall.jpg", EXIT = "exit.jpg", OPEN = "open.jpg",OPEN_SMALL ="openSmall.jpg",
	NO_ENTRANCE ="noEntrance.jpg", CONSTRUCT_ROBOT = "constructRobot.jpg", VISUALIZER = "visualizer.jpg",
	TERMINATE = "terminate.jpg", NO_ENTRANCE_SMALL = "noEntranceSmall.jpg";
  
	
	/**
	 * The titles of the tabs plugged in the main GUI window.
	 */
	public final String CONSTRUCT_ROBOT_TAB_TITLE = "1 Step: Construct Robot",
	                    ASSIGN_BEHAVIORS_TAB_TITLE = "2 Step: Assign Behaviour (Controller)",
	                    MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE = "Communication Visualiser",
	                    SIMULATION_TAB_TITLE = "Simulation",
	                    CONSOLE_TAB_TITLE = "Console",
/*YOUR TAB TITLE*/      YOUR_TAB_TITLE = "YOUR TAB";
	
	/**     
	 * Instances of tabs plugged in main GUI window
	 */
	public final TabsInter  CONSTRUCT_ROBOT_TAB = new ConstructRobotTab(false,true,CONSTRUCT_ROBOT_TAB_TITLE,DIRECTORY_ICONS+CONSTRUCT_ROBOT),
	                        ASSIGN_BEHAVIORS_TAB = new AssignBehaviorsTab(false,true,ASSIGN_BEHAVIORS_TAB_TITLE,DIRECTORY_ICONS+CONSTRUCT_ROBOT),
	                        MODULE_COMMUNICATION_VISUALIZER_TAB = new ModuleCommunicationVisualizer(false,true,MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE,DIRECTORY_ICONS+VISUALIZER),
	                        SIMULATION_CONFIGURATION_TAB = new SimulationTab(true,true,SIMULATION_TAB_TITLE,FramesInter.DIRECTORY_USSR_ICON),
	                        CONSOLE_TAB = new ConsoleTab(true,false,CONSOLE_TAB_TITLE, TabsInter.DIRECTORY_ICONS+TabsInter.CONSOLE),
              /*YOUR TAB*/  YOUR_TAB = new YourNewTab(true,true, YOUR_TAB_TITLE,TabsInter.DIRECTORY_ICONS+TabsInter.NEW_TAB);

	/**
	 * Array of tabs plugged in the Main frame.
	 */
	public final TabsInter[] TABS = {
		                              CONSTRUCT_ROBOT_TAB,
		                              ASSIGN_BEHAVIORS_TAB,
		                              MODULE_COMMUNICATION_VISUALIZER_TAB,
		                              SIMULATION_CONFIGURATION_TAB,
		                              CONSOLE_TAB,
		                /*YOUR TAB*/  YOUR_TAB};
	
}
