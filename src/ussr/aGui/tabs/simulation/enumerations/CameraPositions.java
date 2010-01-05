package ussr.aGui.tabs.simulation.enumerations;

import java.util.Vector;

import ussr.description.setup.WorldDescription.CameraPosition;

/**
 * Contains constants defining camera positions supported in USSR.
 * NOTE NR.1: Add new camera positions here in case of new ones required and in "CameraPosition".
 * @author Konstantinas
 */
public enum CameraPositions {

	DEFAULT(CameraPosition.DEFAULT),
	MIDDLE (CameraPosition.MIDDLE),
	FAROUT (CameraPosition.FAROUT)
	;
	
	/**
	 * Camera position as represented in lower logic of USSR.
	 */
	private CameraPosition cameraPosition;
	
	/**
	 * Contains constants defining camera positions supported in USSR.
	 * @param cameraPosition, camera position as represented in lower logic of USSR.
	 */
	CameraPositions(CameraPosition cameraPosition){
		this.cameraPosition = cameraPosition;
	}
	

	/**
 	 * Returns the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
 	 * underscore is replaced with space and so on.
 	 * @return the name of chosen enumeration with changes in it such that Java convention for constants(upper case) is replaced wit lower and
 	 * underscore is replaced with space.
 	 */
 	public String getUserFriendlyName(){
 		char[] characters = this.toString().toLowerCase().toCharArray();
 		String name = (characters[0]+"").toUpperCase();
         for (int index =1;index<characters.length;index++){
         	name = name+characters[index];
         }		 
 		return name;
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
	 * Returns Java convention name of camera position from user friendly name.
	 * @param userFriendlyCameraPosition, the name of camera position in user friendly format.
	 * @return Java convention name of camera position from user friendly name.
	 */
	public static String toJavaUSSRConvention(String userFriendlyCameraPosition){
 		return userFriendlyCameraPosition.toUpperCase(); 
 	}
	
}
