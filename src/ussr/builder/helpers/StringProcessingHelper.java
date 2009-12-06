package ussr.builder.helpers;

import ussr.aGui.enumerations.MainFrameComponentsText;

public class StringProcessingHelper {


	public static String covertToString(char[] charArray){
		String newString ="";
		if (charArray.length==0){
			throw new Error("Array is empty!");
		}else{			
			for (int index=0; index<charArray.length;index++){
				newString = newString + charArray[index];
			}
		}
		return newString;
	}


	/**
	 * Extracts the value of specific coordinate from the string of VectorDescription.
	 * @param textString, the string  of VectorDescription. 
	 * @param coordinate, the coordinate to extract.
	 * @return the value of the coordinate.
	 */
	public static float extractFromPosition(String textString, String coordinate){		
		String cleanedTextString1 =textString.replace("(", "");
		String cleanedTextString2 =cleanedTextString1.replace(")", "");
		String[] temporary = cleanedTextString2.split(",");		

		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){
			extractedValue = Float.parseFloat(temporary[0]);			
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[1]);			
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[2]);
		}else throw new Error ("There is no such coordinate");
		return extractedValue; 
	}

	/**
	 * Replaces underscore with space.
	 * @param object, the object convertible to string. 
	 * @return text without underscore. 
	 */
	public static String replaceUnderscoreWithSpace(Object text){
		return text.toString().replace("_", " ");
	}


}
