package ussr.builder.enumerations.rotations;

import java.util.Vector;

/**
 * Contains rotations supported for ATRON modular robot. At the same time associates rotations system(Java convention) name
 * with the name presented to the user in GUI (userFriendlyName).
 * @author Konstantinas
 *
 */
public enum ATRONStandardRotations {
	EW("east-west"), 
	WE("west-east"),
	DU("down-up"), 
	UD("up-down"), 
	SN("south-north"),
	NS("north-south");	
	
	/**
	 * The name of rotation in the form as presented to the user in GUI.
	 */
	private String userFriendlyName;
	
	/**
	 * Contains rotations supported for ATRON modular robot.
	 * @param userFriendlyName, the name of rotation in the form as presented to the user in GUI.
	 */
	ATRONStandardRotations(String userFriedlyName){
		this.userFriendlyName = userFriedlyName;
	}
	
	/**
	 * Returns the name of rotation in the form as presented to the user in GUI.
	 * @return userFriendlyName, the name of rotation in the form as presented to the user in GUI.
	 */
	public String getUserFriendlyName() {
		return userFriendlyName;
	}

	/**
	 * Returns the array of objects representing the names of ATRON rotations in user friendly format.
	 * @return the array of objects representing the names of ATRON rotations in user friendly format.
	 */
	public static Object[] getAllInUserFriendlyFormat(){
 		Vector <String> namesTetxtures = new Vector<String>();
 		for (int textureNr=0;textureNr<values().length;textureNr++){
 			namesTetxtures.add(values()[textureNr].getUserFriendlyName()) ;
 		} 		
 		return namesTetxtures.toArray();
 	}
	
	/**
	 * If exists, returns the name of rotation in Java convention format.
	 * @param userFriendlyName,the name of rotation in the form as presented to the user in GUI.
	 * @return rotationSystemName, the name of rotation in Java convention format.
	 */
	public static String getRotationSystemName(String userFriendlyName){
		String rotationSystemName =" ";
		for (int rotationNr=0;rotationNr<values().length;rotationNr++ ){
			String currentUserFriendlyName = values()[rotationNr].getUserFriendlyName();
			if (currentUserFriendlyName.equalsIgnoreCase(userFriendlyName)||userFriendlyName.equals(values()[rotationNr].toString())){
				rotationSystemName = values()[rotationNr].toString(); 
			};
		}
		
		if (rotationSystemName.equals(" ")){
			throw new Error("There is no such system name matching the following user frienly name: " + userFriendlyName );
		}
		return rotationSystemName;
	}
}
