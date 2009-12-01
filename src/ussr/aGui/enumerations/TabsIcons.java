package ussr.aGui.enumerations;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.TabsInter;

/**
 * Contains icons used in tabs.
 * @author Konstantinas 
 */
public enum TabsIcons {
	
	DELETE(TabsInter.DELETE),
	DELETE_ROLLOVER(TabsInter.DELETE_ROLLOVER),

	CONNECT_ALL_MODULES(TabsInter.CONNECT_ALL_MODULE),
	CONNECT_ALL_MODULES_ROLLOVER(TabsInter.CONNECT_ALL_MODULES_ROLLOVER),
	
	ON_SELECTED_CONNECTOR(TabsInter.ON_SELECTED_CONNECTO),
	ON_SELECTED_CONNECTOR_ROLLOVER(TabsInter.ON_SELECTED_CONNECTOR_ROLLOVER),
	
	JUMP_FROM_CON_TO_CON(TabsInter.JUMP_FROM_CON_TO_CON),
	JUMP_FROM_CON_TO_CON_ROLLOVER(TabsInter.JUMP_FROM_CON_TO_CON_ROLLOVER),
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
