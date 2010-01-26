package ussr.aGui.designHelpers.hintPanel;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;
import ussr.aGui.tabs.designHelpers.hintPanel.HintPanelInter;

/**
 * Contains different types of hint panel. The type corresponds to the icon displayed.  
 * @author Konstantinas
 * NOTE: There is a plenty of room for improvement. Meaning many different types of hint panel
 * can be supported.
 */
public enum HintPanelTypes{

	INFORMATION(HintPanelInter.INFORMATION),//Default 
	ATTENTION(HintPanelInter.ATTENTION),		
	ERROR(HintPanelInter.ERROR);
	
	
	/**
	 * The icon created from image file.
	 */
	private ImageIcon imageIcon;	
	
	/**
	 * The directory, where image is located in.
	 */
	private String imageDirectory;
	
	/**
	 * Contains icons used in hint panel.
	 * @param imageName, the name of image file, not including extension.
	 */
	HintPanelTypes(String imageName){
		imageDirectory = formatIconDirectory(imageName);
		this.imageIcon = new ImageIcon(imageDirectory);
	}
	
	/**
	 * Formats directory, where icon is located.
	 * @param imageName, the name of the image file.
	 * @return directory, where icon is located.
	 */
	private static String formatIconDirectory(String imageName){	
		return HintPanelInter.DIRECTORY_ICONS+imageName+MainFramesInter.DEFAULT_ICON_EXTENSION1;
	}
	
	/**
	 * Returns image icon with respect to it's name (name of image file).
	 * @return image icon with respect to it's name (name of image file).
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	/**
	 * 
	 * Returns directory, where image is located in.
	 * @return directory, where image is located in.
	 */
	public String getImageDirectory() {
		return imageDirectory;
	}

}
