package ussr.aGui.enumerations;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.TabsInter;

/**
 * Contains icons used in tabs.
 * @author Konstantinas 
 */
public enum TabsIcons {
	
	
	
	EXPANSION_CLOSED_SMALL(TabsInter.EXPANSION_CLOSED_SMALL),
	EXPANSION_OPENED_SMALL(TabsInter.EXPANSION_OPENED_SMALL),
	FINAL_LEAF_SMALL(TabsInter.FINAL_LEAF_SMALL),
	
	OPPOSITE_ROTATION(TabsInter.OPPOSITE_ROTATION),
	OPPOSITE_ROTATION_ROLLOVER(TabsInter.OPPOSITE_ROTATION_ROLLOVER),
	OPPOSITE_ROTATION_DISABLED(TabsInter.OPPOSITE_ROTATION_DISABLED),
	
	AVAILABLE_ROTATIONS(TabsInter.AVAILABLE_ROTATIONS),
	AVAILABLE_ROTATIONS_SELECTED(TabsInter.AVAILABLE_ROTATIONS_SELECTED),
	AVAILABLE_ROTATIONS_ROLLOVER(TabsInter.AVAILABLE_ROTATIONS_ROLLOVER),
	AVAILABLE_ROTATIONS_DISABLED(TabsInter.AVAILABLE_ROTATIONS_DISABLED),
	
	MOVE(TabsInter.MOVE),
	MOVE_ROLLOVER(TabsInter.MOVE_ROLLOVER),
	MOVE_DISABLED(TabsInter.MOVE_DISABLED),
	
	DELETE(TabsInter.DELETE),
	DELETE_ROLLOVER(TabsInter.DELETE_ROLLOVER),
	DELETE_DISABLED(TabsInter.DELETE_DISABLED),
	
	COLOR_CONNECTORS(TabsInter.COLOR_CONNECTORS),
	COLOR_CONNECTORS_ROLLOVER(TabsInter.COLOR_CONNECTORS_ROLLOVER),
	COLOR_CONNECTORS_DISABLED(TabsInter.COLOR_CONNECTORS_DISABLED),
	
	CONNECT_ALL_MODULES(TabsInter.CONNECT_ALL_MODULES),
	CONNECT_ALL_MODULES_ROLLOVER(TabsInter.CONNECT_ALL_MODULES_ROLLOVER),
	CONNECT_ALL_MODULES_DISABLED(TabsInter.CONNECT_ALL_MODULES_DISABLED),
	
	ON_SELECTED_CONNECTOR(TabsInter.ON_SELECTED_CONNECTOR),
	ON_SELECTED_CONNECTOR_ROLLOVER(TabsInter.ON_SELECTED_CONNECTOR_ROLLOVER),
	ON_SELECTED_CONNECTOR_DISABLED(TabsInter.ON_SELECTED_CONNECTOR_DISABLED),
	
	JUMP_FROM_CON_TO_CON(TabsInter.JUMP_FROM_CON_TO_CON),
	JUMP_FROM_CON_TO_CON_ROLLOVER(TabsInter.JUMP_FROM_CON_TO_CON_ROLLOVER),
	JUMP_FROM_CON_TO_CON_DISABLED(TabsInter.JUMP_FROM_CON_TO_CON_DISABLED),
	
	VARY_PROPERTIES(TabsInter.VARY_PROPERTIES),
	VARY_PROPERTIES_ROLLOVER(TabsInter.VARY_PROPERTIES_ROLLOVER),
	VARY_PROPERTIES_DISABLED(TabsInter.VARY_PROPERTIES_DISABLED),
	
	ASSIGN_LABELS(TabsInter.ASSIGN_LABELS),
	ASSIGN_LABELS_ROLLOVER(TabsInter.ASSIGN_LABELS_ROLLOVER),
	ASSIGN_LABELS_DISABLED(TabsInter.ASSIGN_LABELS_DISABLED),

	READ_LABELS(TabsInter.READ_LABEL),
	READ_LABELS_ROLLOVER(TabsInter.READ_LABELS_ROLLOVER),
	READ_LABELS_DISABLED(TabsInter.READ_LABELS_DISABLED),
	;
	
	
	/**
	 * The icon created from image file.
	 */
	private ImageIcon imageIcon;
	
	/**
	 * Contains icons used in tabs.
	 * @param imageName, the name of image file, not including extension.
	 */
	TabsIcons(String imageName){
		this.imageIcon = new ImageIcon(formatIconDirectory(imageName));
	}
	
	/**
	 * Formats directory, where icon is located.
	 * @param imageName, the name of the image file.
	 * @return directory, where icon is located.
	 */
	private static String formatIconDirectory(String imageName){	
		return TabsInter.DIRECTORY_ICONS+imageName+MainFramesInter.DEFAULT_ICON_EXTENSION1;
	}
	
	/**
	 * Returns image icon with respect to it's name (name of image file).
	 * @return image icon with respect to it's name (name of image file).
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	
	
}
