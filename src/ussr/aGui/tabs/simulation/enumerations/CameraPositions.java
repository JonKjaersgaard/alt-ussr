package ussr.aGui.tabs.simulation.enumerations;

import java.util.Vector;

import ussr.description.setup.WorldDescription.CameraPosition;

public enum CameraPositions {

	DEFAULT(CameraPosition.DEFAULT),
	MIDDLE (CameraPosition.MIDDLE),
	FAROUT (CameraPosition.FAROUT)
	;
	
	private CameraPosition cameraPosition;
	
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
	
}
