package ussr.builder.helpers;

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
}
