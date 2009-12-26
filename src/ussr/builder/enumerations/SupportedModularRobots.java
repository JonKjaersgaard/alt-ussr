package ussr.builder.enumerations;

import java.awt.Color;

/**
 * Modular robots supported by builder for interactive
 * construction of their morphology and assignment of behaviors.
 * @author Konstantinas
 */
public enum SupportedModularRobots {
	
	DUMMY("dummy"),
	/*ATRON is homogeneous */
	ATRON("Atron"),
	/*MTRAN is homogeneous*/
	MTRAN("M-Tran"),
	/*ODIN is heterogeneous*/
	ODIN("Odin"),
	/*CKBOT is homogeneous*/
	CKBOT_STANDARD("CKbot Standard"); //PARTIALLY SUPPORTED
	
	/**
	 * Arrays of number of connectors for each modular robot.
	 */
	public final static String[] ATRON_CONNECTORS = {"0","1","2","3","4","5","6","7"},
	                             ODIN_BALL_CONNECTORS = {"0","1","2","3","4","5","6","7","8","9","10","11"},
                                 ODIN_MUSCLE_CONNECTORS = {"0","1"},
                                 MTRAN_CONNECTORS = {"0","1","2","3","4","5"},
	                             CKBOTSTANDARD_CONNECTORS = {"0","1","2","3"}	                          
	                             ;
	
	
	public final static Color[] ATRON_CONNECTORS_COLORS = {Color.black,Color.white,Color.black,Color.white,Color.black,Color.white,Color.black,Color.white},
	                            ODIN_CONNECTORS_COLORS = {Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE},
	                            MTRAN_CONNECTORS_COLORS = {Color.BLACK,Color.BLACK,Color.BLACK,Color.WHITE,Color.WHITE,Color.WHITE},
	                            CKBOTSTANDARD_CONNECTORS_COLORS = {Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE};
    
	public final static String[] ODIN_TYPES_MODULES = {"OdinBall","OdinMuscle", "OdinHinge", "OdinBattery", "OdinSpring","OdinWheel"};
	
	/**
	 * The name of modular robot in the form as presented to the user in GUI.
	 */
	private String userFriendlyName;
	
	/**
	 * Modular robots supported by builder for interactive
     * construction of their morphology and assignment of behaviors.
	 * @param userFriendlyName,the name of modular robot in the form as presented to the user in GUI.
	 */
	SupportedModularRobots(String userFriedlyName){
		this.userFriendlyName = userFriedlyName;
	}
	
	/**
	 * Returns the name of modular robot in the form as presented to the user in GUI.
	 * @return userFriendlyName, the name of modular robot in the form as presented to the user in GUI.
	 */
	public String getUserFriendlyName() {
		return userFriendlyName;
	}
	
	/**
	 * If exists, returns the name of rotation in Java convention format.
	 * @param userFriendlyName,the name of rotation in the form as presented to the user in GUI.
	 * @return rotationSystemName, the name of rotation in Java convention format.
	 */
	public static SupportedModularRobots getModularRobotSystemName(String userFriendlyName){
		String modularRobotSystemName =" ";
		for (int modularRobotNr=0;modularRobotNr<values().length;modularRobotNr++ ){
			String currentUserFriendlyName = values()[modularRobotNr].getUserFriendlyName();
			if (currentUserFriendlyName.equalsIgnoreCase(userFriendlyName)||userFriendlyName.equals(values()[modularRobotNr].toString())){
				modularRobotSystemName = values()[modularRobotNr].toString(); 
			};
		}
		
		if (modularRobotSystemName.equals(" ")){
			throw new Error("There is no such system name matching the following user frienly name: " + userFriendlyName );
		}
		return SupportedModularRobots.valueOf(modularRobotSystemName);
	}
	

	/**
	 * Checks the name of modular robot received from underlying logic of USSR(property named as type of module) and returns 
	 * the constant introduced here.
     * @param supportedModularRobot, the name of modular robot from underlying logic of USSR(getProperty(moduleType)).
     * @return constant name introduced in builder package. 
     */
    public static SupportedModularRobots getConsistentMRName (String supportedModularRobot){
    
    	for (int newIndex=0; newIndex<values().length; newIndex++){
    		String currentMR =values()[newIndex].toString();  
    		if (supportedModularRobot.contains(currentMR)||supportedModularRobot.toLowerCase().contains(currentMR.toLowerCase())){
    			return SupportedModularRobots.valueOf(currentMR);
    		};
    	}
    	throw new Error("Modular robot named as "+supportedModularRobot+ "is not supported yet");
    } 
    
    
     
}
