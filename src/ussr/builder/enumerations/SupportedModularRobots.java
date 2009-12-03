package ussr.builder.enumerations;

/**
 * Modular robots supported by builder for interactive
 * construction of their morphology and assignment of behaviors.
 * @author Konstantinas
 */
public enum SupportedModularRobots {
	/*ATRON is homogeneous */
	ATRON,
	/*MTRAN is homogeneous*/
	MTRAN,
	/*ODIN is heterogeneous*/
	ODIN,
	/*CKBOT is homogeneous*/
	CKBOTSTANDARD; //PARTIALLY SUPPORTED
	
	/**
	 * 
	 */
	public final static int[] ATRON_CONNECTORS = {0,1,2,3,4,5,6,7},
	                          CKBOTSTANDARD_CONNECTORS = {0,1,2,3},
	                          MTRAN_CONNECTORS = {0,1,2,3,4,5},
	                          ODIN_BALL_CONNECTORS = {0,1,2,3,4,5,6,7,8,9,10,11};
	
    public static String getConsistentMRName (String supportedModularRobot){
    
    	SupportedModularRobots[] supportedMRobots = SupportedModularRobots.values();
    	for (int newIndex=0; newIndex<supportedMRobots.length; newIndex++){
    		String currentMR =supportedMRobots[newIndex].toString();  
    		if (supportedModularRobot.contains(currentMR)||supportedModularRobot.toLowerCase().contains(currentMR.toLowerCase())){
    			return currentMR;
    		};
    	}
    	throw new Error("Modular robot named as "+supportedModularRobot+ "is not supported yet");
    }
    
}
