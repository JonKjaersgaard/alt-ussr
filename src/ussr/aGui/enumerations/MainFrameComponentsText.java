package ussr.aGui.enumerations;

/**
 * Contains text elements presented to the user in GUI(MainFrame). Such as text on GUI elements and tool tips text.
 * NOTE: underscore is later replaced with space. 
 * NOTE: is not complete, because not all of them are used in the code. 
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
	      Run_real_time, Run_fast, Run_step_by_step,Pause,Terminate,Construct_robot,Visualize_communication_of_modules
	      
	      
	      ;
	      
	      public static String replaceUnderscoreWithSpace(MainFrameComponentsText mainFrameComponentsText){
	    	  return mainFrameComponentsText.toString().replace("_", " ");
	     }
	}
