package ussr.builder.enumerations;

import java.util.Vector;

public enum ATRONTypesModules {
	
	ATRON_GENTLE("ATRON gentle", "gentle"),
	ATRON_SUPER("ATRON super", "super"),
	ATRON_REALISTIC("ATRON realistic", "realistic"),
	ATRON_SMOOTH("ATRON smooth", "smooth"),
	ATRON_RUBBER_RING("ATRON rubberRing", "rubberRing"),	
	ATRON_RADIO("ATRON radio", "radio"),
	ATRON_HALF_DUPLEX("ATRON halfDuplex", "halfDuplex")
	
;
	
	private String moduleType;
	
	private String userFriendlyName;
	
	public String getUserFriendlyName() {
		return userFriendlyName;
	}


	ATRONTypesModules(String moduleType, String userFriendlyName){
		this.moduleType = moduleType;
		this.userFriendlyName = userFriendlyName;
	}
	
	
	public String getModuleType() {
		return moduleType;
	}
	
	
	/**
	 * Returns the array of objects representing the names of enumerations in users friendly format.
	 * @return the array of objects representing the names of enumerations in users friendly format.
	 */
	public static Object[] getAllInUserFriendlyFromat(){
 		Vector <String> namesTetxtures = new Vector<String>();
 		for (int textureNr=0;textureNr<values().length;textureNr++){
 			namesTetxtures.add(values()[textureNr].getUserFriendlyName()) ;
 		} 		
 		return namesTetxtures.toArray();
 	}
	
	
	/**
	 * TODO
	 * Returns the name of module type in underlying logic format according to user friendly format.
	 * @param userFriedlyName, the type of module in the form as presented to the user in GUI.
	 * @return  the name of module type in underlying logic format according to user friendly format.
	 */
	public static String getModuleTypeFromUserFriendly(String userFriedlyName ){
		String underlyingLogicName ="";
		for (int type=0; type<values().length;type++){
			if (values()[type].getUserFriendlyName().equals(userFriedlyName)){
				underlyingLogicName = values()[type].getModuleType();  
			}
		}
		if (underlyingLogicName ==""){
			throw new Error("There is no such underlyging logic name matching  the following user friendly name: "+userFriedlyName);
		}
		return underlyingLogicName;
	} 
}
