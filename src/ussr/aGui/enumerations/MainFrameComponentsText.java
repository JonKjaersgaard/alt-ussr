package ussr.aGui.enumerations;

/**
 * Contains text elements presented to the user in GUI(MainFrame). Such as text on GUI elements and tool tips text.
 * NOTE NR1: underscore is later replaced with space. 
 * NOTE NR2: is not complete, because not all of them are used in the code.
 * NOTE NR3: if you want to change specific text, just re-factor it and keep in mind that underscore is used instead of space. 
 * @author Konstantinas
 */
public enum MainFrameComponentsText {
    
	/*METHOD: setText()*/
	
	     /*JMenues*/
	     SIMULATION,RENDER,WINDOW,
	
	     /*JMenuItems*/
	       //File Menu 
	       START_NEW_SIMULATION,OPEN,SAVE,EXIT, NEW, 
           //Render Menu
	       PHYSICS, WIRE_FRAME, BOUNDS,NORMALS, LIGHTS,BUFFER_DEPTH, 
	       // Window Menu                  
	       HIDE, INTERACTION,DEBUGGING,DISPLAY_FOR_HINTS,
	       
	/*METHOD: setToolTipText()*/
	
	      /*JToolBars*/
	       GENERAL_CONTROL,
	
	      /*JButtons*/
	      RUN_REAL_TIME, RUN_FAST, RUN_STEP_BY_STEP,PAUSE,TERMINATE,RESTART,
	      CONSTRUCT_ROBOT,VISUALIZE_COMMUNICATION_OF_MODULES,HIDE_INTERACTION,HIDE_DEBUGGING,
	      
	      /*JTabbedPanes*/
	      //Interaction_with_simulation, 
	    ; 
	     /**
	 	 * Returns the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
	 	 * underscore is replaced with space.
	 	 * @return the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
	 	 * underscore is replaced with space.
	 	 */
	 	public String getUserFriendlyName(){
	 		char[] characters = this.toString().replace("_", " ").toLowerCase().toCharArray();
	 		String name = (characters[0]+"").toUpperCase();
	         for (int index =1;index<characters.length;index++){
	         	name = name+characters[index];
	         }		 
	 		return name;
	 	}
	}
