package ussr.aGui.enumerations;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;

/**
 * Contains icons used in main GUI window.
 * NOTE: Most of icons are 20x20 pixels,however small(this is a keyword) ones are:15x15.
 * @author Konstantinas
 */
public enum MainFrameIcons{
    /*Constant icons*/
	
	/*Save and Open*/	
	SAVE(MainFramesInter.SAVE),SAVE_ROLLOVER(MainFramesInter.SAVE_ROLLOVER),
	SAVE_SMALL(MainFramesInter.SAVE_SMALL),
	OPEN(MainFramesInter.OPEN),OPEN_ROLLOVER(MainFramesInter.OPEN_ROLLOVER),
	OPEN_SMAL(MainFramesInter.OPEN_SMAL),
	
	EXIT_SMALL(MainFramesInter.EXIT_SMALL),
	NO_ENTRANCE_SMALL(MainFramesInter.NO_EN_SMALL),
	
	/*First tool bar in main GUI window*/
	RUN_REAL_TIME(MainFramesInter.RUN_REAL_TIME),
	RUN_REAL_TIME_ROLLOVER(MainFramesInter.RUN_REAL_TIME_ROLLOVER),
	
	RUN_FAST(MainFramesInter.RUN_FAST),
	RUN_FAST_ROLLOVER(MainFramesInter.RUN_FAST_ROLLOVER),
	
	RUN_STEP_BY_STEP(MainFramesInter.RUN_STEP_BY_STEP),
	RUN_STEP_BY_STEP_ROLLOVER(MainFramesInter.RUN_STEP_BY_STEP_ROLLOVER),
	
	PAUSE(MainFramesInter.PAUSE),
	PAUSE_ROLLOVER(MainFramesInter.PAUSE_ROLLOVER),
	
	TERMINATE(MainFramesInter.TERMINATE),
	TERMINATE_ROLLOVER(MainFramesInter.TERMINATE_ROLLOVER),
	
    RESTART(MainFramesInter.RESTART),
    RESTART_ROLLOVER(MainFramesInter.RESTART_ROLLOVER),
    
    CONSTRUCT_ROBOT(MainFramesInter.CONSTRUCT_ROBOT),
    CONSTRUCT_ROBOT_ROLLOVER(MainFramesInter.CONSTRUCT_ROBOT_ROLLOVER),
    
    VISUALIZER(MainFramesInter.VISUALIZER),
    VISUALIZER_ROLLOVER(MainFramesInter.VISUALIZER_ROLLOVER),
			
	/*used to indicate disabled components(buttons)*/
	NO_ENTRANCE(MainFramesInter.NO_EN)
	
	;
	

	/**
	 * The icon created from image file.
	 */
	private ImageIcon imageIcon;

	/**
	 * Contains icons used in main GUI window.
	 * @param imageName, the name of image file, not including extension.
	 */
	MainFrameIcons(String imageName){
		this.imageIcon = new ImageIcon(formatIconDirectory(imageName));
	}

	/**
	 * Returns image icon with respect to it's name (name of image file).
	 * @return image icon with respect to it's name (name of image file).
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
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
