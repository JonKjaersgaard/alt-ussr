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
	public final String MOVE = "move.jpg", COLOUR_CONNECTORS = "colourConnectors.jpg",
	                    ERROR = "error.jpg", INFORMATION ="information.jpg", CONSOLE = "console.jpg", OPPOSITE = "opposite.jpg",
	                    ON_SELECTED_CONNECTOR ="onSelectedConnector.jpg", CONNECT_ALL_MODULES = "connectAllModules.jpg",
	                    JUMP_FROM_CONN_TO_CONNECTOR = "jumpFromConnToConnector.jpg", NEXT = "next.jpg",
	                    PREVIOUS = "previous.jpg",ASSIGN_LABELS = "assignLabels.jpg", READ_LABELS ="readLabels.jpg",
	                    NEW_TAB = "newTab.jpg", RESET = "reset.jpg", VARY_PROPERTIES = "varyProperties.jpg",
	                    AVAILABLE_ROTATIONS = "availableRotations.jpg", EXPANSION_CLOSED = "expansionClosed.jpg",
	                    EXPANSION_OPENED ="expansionOpened.jpg", FINAL_LEAF = "finalLeaf.jpg", 
	                    PLANE_TITL_RIGHT ="planeTiltRight.jpg";
	
	public final String DELETE ="delete",DELETE_ROLLOVER ="deleteRollover",
	                    EXPAN_CLOSED ="expansionClosed", EXPAN_OPENED ="expansionOpened",FINAL_L = "finalLeaf",
	                    OPPOSITE_ROTATION ="oppositeRotation", OPPOSITE_ROTATION_ROLLOVER ="oppositeRotationRollover",
	                    AVAILABLE_ROT = "availableRotations", AVAILABLE_ROTATIONS_ROLLOVER = "availableRotationsRollover",
	                    AVAILABLE_ROTATIONS_SELECTED = "availableRotationsSelected", MOV ="move", MOVE_ROLLOVER ="moveRollover",
	                    
	                    CONNECT_ALL_MODULE ="connectAllModules",CONNECT_ALL_MODULES_ROLLOVER="connectAllModulesRollover",
	                    ON_SELECTED_CONNECTO ="onSelectedConnector", ON_SELECTED_CONNECTOR_ROLLOVER ="onSelectedConnectorRollover",
	                    JUMP_FROM_CON_TO_CON = "jumpFromConToCon",JUMP_FROM_CON_TO_CON_ROLLOVER = "jumpFromConToConRollover"	
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
