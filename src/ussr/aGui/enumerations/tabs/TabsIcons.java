package ussr.aGui.enumerations.tabs;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.TabsInter;

/**
 * Contains icons used in tabs.
 * NOTE NR1: Most of icons are 20x20 pixels,however small(this is a keyword) ones are:15x15 and big(keyword) 25x25.
 * NOTE NR2: This enumeration was introduced in order to make it easy to change the directory for all icons,
 * their names and common extension. Moreover, to keep icons localized (in one place) and easy to refer to.
 * NOTE NR3: Here are used constants of file names as arguments. The main reason for doing so is to
 * keep ability to specify the directory, icon name and extension directly without using this enumeration.
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
	
	NEW_ROBOT(TabsInter.NEW_ROBOT),
	NEW_ROBOT_ROLLOVER(TabsInter.NEW_ROBOT_ROLLOVER),
	NEW_ROBOT_DISABLED(TabsInter.NEW_ROBOT_DISABLED),
	
	Y_POSITIVE_BIG(TabsInter.Y_POSITIVE_BIG),
	Y_POSITIVE_ROLLOVER_BIG(TabsInter.Y_POSITIVE_ROLLOVER_BIG),
	Y_NEGATIVE_BIG(TabsInter.Y_NEGATIVE_BIG),
	Y_NEGATIVE_ROLLOVER_BIG(TabsInter.Y_NEGATIVE_ROLLOVER_BIG),
	
	X_POSITIVE_BIG(TabsInter.X_POSITIVE_BIG),
	X_POSITIVE_ROLLOVER_BIG(TabsInter.X_POSITIVE_ROLLOVER_BIG),	
	X_NEGATIVE_BIG(TabsInter.X_NEGATIVE_BIG),
	X_NEGATIVE_ROLLOVER_BIG(TabsInter.X_NEGATIVE_ROLLOVER_BIG),
	
	Z_POSITIVE_BIG(TabsInter.Z_POSITIVE_BIG),
	Z_POSITIVE_ROLLOVER_BIG(TabsInter.Z_POSITIVE_ROLLOVER_BIG),
	Z_NEGATIVE_BIG(TabsInter.Z_NEGATIVE_BIG),
	Z_NEGATIVE_ROLLOVER_BIG(TabsInter.Z_NEGATIVE_ROLLOVER_BIG),
	
	ENTER_VALUES (TabsInter.ENTER_VALUES),
	ENTER_VALUES_ROLLOVER (TabsInter.ENTER_VALUES_ROLLOVER),	
	;
	
	/**
	 * The icon created from image file.
	 */
	private ImageIcon imageIcon;
	
	/**
	 * The directory, where image is located in.
	 */
	private String imageDirectory;
	
	/**
	 * The name of image file, not including extension
	 */
	private String imageName;
	
	/**
	 * Contains icons used in tabs.
	 * @param imageName, the name of image file, not including extension.
	 */
	TabsIcons(String imageName){
		this.imageName = imageName;
		this.imageDirectory = formatIconDirectory(imageName);
		this.imageIcon = new ImageIcon(imageDirectory);
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
	
	/**
	 * Returns directory, where image is located in.
	 * @return directory, where image is located in.
	 */
	public String getImageDirectory() {
		return imageDirectory;
	}
	
	/**
	 * Returns the name of image file excluding extension.
	 * @return the name of image file excluding extension.
	 */
	public String getImageName() {
		return imageName;
	}	
}
