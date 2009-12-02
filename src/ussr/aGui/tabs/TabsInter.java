package ussr.aGui.tabs;

import java.util.ArrayList;

/**
 * Supports definition of visual appearance(view in MVC pattern) of tabs, which are pluged-in main GUI window. 
 * @author Konstantinas
 */
public interface TabsInter {
	
	/**
	 * The directory for keeping jpg icons used in the tabs.
	 */
	public final String DIRECTORY_ICONS = "resources/mainFrame/icons/tabs/jpg/";
	
	/**
	 * The names of the icons used in tabs
	 */
	public final String 
	                    ERROR = "error.jpg", INFORMATION ="information.jpg", CONSOLE = "console.jpg",
	                    NEXT = "next.jpg",
	                    PREVIOUS = "previous.jpg",ASSIGN_LABELS = "assignLabels.jpg", READ_LABELS ="readLabels.jpg",
	                    NEW_TAB = "newTab.jpg", RESET = "reset.jpg";
	
	public final String MOVE = "move", MOVE_ROLLOVER ="moveRollover",
	                    DELETE ="delete",DELETE_ROLLOVER ="deleteRollover",
	                    EXPANSION_CLOSED_SMALL ="expansionClosedSmall", EXPANSION_OPENED_SMALL ="expansionOpenedSmall",FINAL_LEAF_SMALL = "finalLeafSmall",
	                    OPPOSITE_ROTATION ="oppositeRotation", OPPOSITE_ROTATION_ROLLOVER ="oppositeRotationRollover",
	                    AVAILABLE_ROTATIONS = "availableRotations", AVAILABLE_ROTATIONS_ROLLOVER = "availableRotationsRollover",
	                    AVAILABLE_ROTATIONS_SELECTED = "availableRotationsSelected",  
	                    
	                    CONNECT_ALL_MODULES ="connectAllModules",CONNECT_ALL_MODULES_ROLLOVER="connectAllModulesRollover",
	                    ON_SELECTED_CONNECTOR ="onSelectedConnector", ON_SELECTED_CONNECTOR_ROLLOVER ="onSelectedConnectorRollover",
	                    JUMP_FROM_CON_TO_CON = "jumpFromConToCon",JUMP_FROM_CON_TO_CON_ROLLOVER = "jumpFromConToConRollover",
	                    COLOR_CONNECTORS ="colorConnectors", COLOR_CONNECTORS_ROLLOVER ="colorConnectorsRollover",
	                    VARY_PROPERTIES = "varyProperties",VARY_PROPERTIES_ROLLOVER = "varyPropertiesRollover"
	                    
	                    ;
	
	/**
	 * Returns JComponent, which is the main container of components situated in the tab.
	 * @return JComponent, which is the main container of components situated in the tab.
	 */
	public javax.swing.JComponent getJComponent();
	
	/**
	 * Returns the title of the tab.
	 * @return tabTitle, the title of the tab.
	 */
	public String getTabTitle();
	
	
	/**
	 * Returns true if the tab is situated in the first tabbed pane of main GUI window, else it is situated in second.
	 * @return true if the tab is situated in the first tabbed pane of main GUI window, else it is situated in second.
	 */
	public boolean isFirstTabbedPane();
	
	
	/**
	 * Returns the directory where the icon of the tab is located.
	 * @return the directory where the icon of the tab is located.
	 */
	public String getImageIconDirectory();   
    
	/**
	 * Returns true if the tab is visible when main GUI window is activated.
	 * @return true if the tab is visible when main GUI window is activated.
	 */
	public boolean isInitiallyVisible();

	
	public ArrayList<javax.swing.JComponent> getComponents();
}
