package ussr.aGui.enumerations;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;

/**
 * Contains icons used in main GUI window.
 * NOTE NR1: Most of icons are 20x20 pixels,however small(this is a keyword) ones are:15x15.
 * NOTE NR2: This enumeration was introduced in order to make it easy to change the directory for all icons,
 * their names and common extension.
 * NOTE NR3: Here are used constants of file names as arguments. The main reason for doing so is to
 * keep ability to specify the directory, icon name and extension directly without using this enumeration.
 * @author Konstantinas
 */
public enum MainFrameIcons{
    /*Constant icons*/
	
	/*Save and Open*/	
	SAVE(MainFramesInter.SAVE),
	SAVE_ROLLOVER(MainFramesInter.SAVE_ROLLOVER),
	SAVE_DISABLED(MainFramesInter.SAVE_DISABLED),
	SAVE_SMALL(MainFramesInter.SAVE_SMALL),
	SAVE_SMALL_DISABLED(MainFramesInter.SAVE_SMALL_DISABLED),
	
	OPEN(MainFramesInter.OPEN),
	OPEN_ROLLOVER(MainFramesInter.OPEN_ROLLOVER),
	OPEN_DISABLED(MainFramesInter.OPEN_DISABLED),
	OPEN_SMALL(MainFramesInter.OPEN_SMALL),
	OPEN_SMALL_DISABLED(MainFramesInter.OPEN_SMALL_DISABLED),
	
	EXIT_SMALL(MainFramesInter.EXIT_SMALL),
	
	/*First tool bar in main GUI window*/
	RUN_REAL_TIME(MainFramesInter.RUN_REAL_TIME),
	RUN_REAL_TIME_ROLLOVER(MainFramesInter.RUN_REAL_TIME_ROLLOVER),
	RUN_REAL_TIME_DISABLED(MainFramesInter.RUN_REAL_TIME_DISABLED),
	
	RUN_FAST(MainFramesInter.RUN_FAST),
	RUN_FAST_ROLLOVER(MainFramesInter.RUN_FAST_ROLLOVER),
	RUN_FAST_DISABLED(MainFramesInter.RUN_FAST_DISABLED),
	
	RUN_STEP_BY_STEP(MainFramesInter.RUN_STEP_BY_STEP),
	RUN_STEP_BY_STEP_ROLLOVER(MainFramesInter.RUN_STEP_BY_STEP_ROLLOVER),
	RUN_STEP_BY_STEP_DISABLED(MainFramesInter.RUN_STEP_BY_STEP_DISABLED),
	
	PAUSE(MainFramesInter.PAUSE),
	PAUSE_ROLLOVER(MainFramesInter.PAUSE_ROLLOVER),
	PAUSE_DISABLED(MainFramesInter.PAUSE_DISABLED),
	
	TERMINATE(MainFramesInter.TERMINATE),
	TERMINATE_ROLLOVER(MainFramesInter.TERMINATE_ROLLOVER),
	TERMINATE_DISABLED(MainFramesInter.TERMINATE_DISABLED),
	
    RESTART(MainFramesInter.RESTART),
    RESTART_ROLLOVER(MainFramesInter.RESTART_ROLLOVER),
    RESTART_DISABLED(MainFramesInter.RESTART_DISABLED),
    
    CONSTRUCT_ROBOT(MainFramesInter.CONSTRUCT_ROBOT),
    CONSTRUCT_ROBOT_ROLLOVER(MainFramesInter.CONSTRUCT_ROBOT_ROLLOVER),
    CONSTRUCT_ROBOT_DISABLED(MainFramesInter.CONSTRUCT_ROBOT_DISABLED),
    
    VISUALIZER(MainFramesInter.VISUALIZER),
    VISUALIZER_ROLLOVER(MainFramesInter.VISUALIZER_ROLLOVER),
    VISUALIZER_DISABLED(MainFramesInter.VISUALIZER_DISABLED),
    
    CONSOLE (MainFramesInter.CONSOLE),
    
    YOUR_NEW_TAB(MainFramesInter.YOUR_NEW_TAB),
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
	 * Contains icons used in main GUI window.
	 * @param imageName, the name of image file, not including extension.
	 */
	MainFrameIcons(String imageName){
		imageDirectory = formatIconDirectory(imageName);
		this.imageIcon = new ImageIcon(imageDirectory);
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
	/**
	 * Formats directory, where icon is located.
	 * @param imageName, the name of the image file.
	 * @return directory, where icon is located.
	 */
	private static String formatIconDirectory(String imageName){	
		return MainFramesInter.DIRECTORY_ICONS+imageName+MainFramesInter.DEFAULT_ICON_EXTENSION1;
	}
	
}
