package ussr.builder.helpers;

public class FileDirectoryHelper {

	/**
	 * The back slash with escape character for splitting(String.split(regex)) the string.
	 */
	public static final String SPLIT_ESCAPE_BACK_SLASH="\\\\";
	
	/**
	 * The back slash with escape character for printing.
	 */
	public static final String PRINT_ESCAPE_BACK_SLASH="\\";
	
	
	/**
	 * Extracts directory from format: directory+fileName(with extension) 
	 * @param fileDirectoryName, the directory plus file name.
	 * @return directory without file name with extension.
	 */
	public static String extractDirectory(String fileDirectoryName){
		String[] temporaryFileDirectoryName = fileDirectoryName.split(SPLIT_ESCAPE_BACK_SLASH);
		String directory = "";
		/*Take only directory and leave aside the filename*/
		for (int index=0;index<temporaryFileDirectoryName.length-1; index++){
			directory = directory+temporaryFileDirectoryName[index]+ PRINT_ESCAPE_BACK_SLASH;
		}
		return directory;
	} 
}
