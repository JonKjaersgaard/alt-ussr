package ussr.builder.enumerations;

import java.util.Vector;

/**
 * Contains the names of types of modules supported for Odin modular robot. Moreover, associates each of them
 * with their user friendly(presented to the user) and underlying logic name (used by USSR to create new module). 
 * @author Konstantinas
 */
public enum OdinTypesModules {
	
	ODIN_BALL("Ball","OdinBall"),
	ODIN_MUSCLE("Muscle","OdinMuscle"),
	ODIN_HINGE("Hinge","OdinHinge"),
	ODIN_BATTERY("Battery","OdinBattery"),
	ODIN_SPRING("Spring","OdinSpring"),
	ODIN_WHEEL ("Wheel","OdinWheel"),
	;
	
	/**
	 * The name of module type in the form as presented to the user in GUI.
	 */
	private String userFriendlyName;
	
	/**
	 * The name of module type as used by USSR to create new module.
	 */
	private String underlyingLogicName;
	
	/**
	 * Contains the names of types of modules supported for Odin modular robot.
	 * @param userFriendlyName, the name of rotation in the form as presented to the user in GUI.
	 * @param underlyingLogicName, the name of module as used by USSR to create new module.
	 */
	OdinTypesModules(String userFriedlyName, String underlyingLogicName){
		this.userFriendlyName = userFriedlyName;
		this.underlyingLogicName = underlyingLogicName;
	}
	
	/**
	 * Returns the type of module in the form as presented to the user in GUI.
	 * @return userFriendlyName, the name of rotation in the form as presented to the user in GUI.
	 */
	public String getUserFriendlyName() {
		return userFriendlyName;
	}
	
	/**
	 * Returns the name of module type as used by USSR to create new module.
	 * @return the name of module type as used by USSR to create new module.
	 */
	public String getUnderlyingLogicName() {
		return underlyingLogicName;
	}	
	
	/**
	 * Returns the name of module type in underlying logic format according to user friendly format.
	 * @param userFriedlyName, the type of module in the form as presented to the user in GUI.
	 * @return  the name of module type in underlying logic format according to user friendly format.
	 */
	public static String getUnderlyingLogicNameFromUserFriendly(String userFriedlyName ){
		String underlyingLogicName ="";
		for (int type=0; type<values().length;type++){
			if (values()[type].getUserFriendlyName().equals(userFriedlyName)){
				underlyingLogicName = values()[type].getUnderlyingLogicName();  
			}
		}
		if (underlyingLogicName ==""){
			throw new Error("There is no such underlyging logic name matching  the following user friendly name: "+userFriedlyName);
		}
		return underlyingLogicName;
	} 
	
	/**
	 * Returns the array of objects representing the names of enumerations in users friendly format.
	 * @param startFrom, the index to start taking names from.
	 * @return the array of objects representing the names of enumerations in users friendly format.
	 */
	public static Object[] getAllInUserFriendlyFormat(int startFrom){
 		Vector <String> namesTetxtures = new Vector<String>();
 		for (int textureNr=startFrom;textureNr<values().length;textureNr++){
 			namesTetxtures.add(values()[textureNr].getUserFriendlyName()) ;
 		} 		
 		return namesTetxtures.toArray();
 	}	
}
