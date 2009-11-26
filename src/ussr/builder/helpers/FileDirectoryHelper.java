package ussr.builder.helpers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Konstantinas
 *
 */
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
	
	/**
	 * Returns all classes from specified package.
	 * @param pckgname, full name of package.
	 * @return array of classes.
	 */
	public static Class[] getClasses(String packageName)throws ClassNotFoundException {
		ArrayList<Class> classes = new ArrayList<Class>();
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			//String path = '/' + pckgname.replace('.', '/');
			String path = packageName.replace('.', '/');
			URL resource = classLoader.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(packageName + " (" + directory
					+ ") does not appear to be a valid package");
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(packageName + '.'
							+ files[i].substring(0, files[i].length() - 6)));
				}
			}
		} else {
			throw new ClassNotFoundException(packageName
					+ " does not appear to be a valid package");
		}
		Class[] classesA = new Class[classes.size()];
		classes.toArray(classesA);
		return classesA;
	}
}
