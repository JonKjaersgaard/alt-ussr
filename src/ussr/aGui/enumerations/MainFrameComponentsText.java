package ussr.aGui.enumerations;

/**
 * Contains text elements presented to the user in GUI(MainFrame). Such as text on GUI elements and tool tips text.
 * NOTE NR1: underscore is later replaced with space. 
 * NOTE NR2: is not complete, because not all of them are used in the code.
 * NOTE NR3: if you want to change specific text, just refactor it and keep in mind that underscore is used instead of space. 
 * @author Konstantinas
 *
 */
public enum MainFrameComponentsText {
    
	/*METHOD: setText()*/
	
	     /*JMenues*/
	     File,Render,Window,
	
	     /*JMenuItems*/
	       //File Menu 
	       Open,Save,Exit, 
           //Render Menu
	       Physics, Wire_frame, Bounds,Normals, Lights,Buffer_depth, 
	       // Window Menu 
	       Focus_on,Interaction,Debugging,
	/*METHOD: setToolTipText()*/
	
	      /*JToolBars*/
	       General_control,
	
	      /*JButtons*/
	      Run_real_time, Run_fast, Run_step_by_step,Pause,Terminate,Restart,Construct_robot,Visualize_communication_of_modules
	      ;
	      
	    
	     /**
	      * Replaces underscore with space.
	     * @param mainFrameComponentsText, text in which to replace "_" with "" 
	     * @return text without underscore. 
	     */
	    public static String replaceUnderscoreWithSpace(MainFrameComponentsText mainFrameComponentsText){
	    	  return mainFrameComponentsText.toString().replace("_", " ");
	     }
	}
