package onlineLearning.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class DataLogger {
	private static File dir;
	private static Hashtable<String,BufferedWriter> logFiles;
	public static void init(String dirName) {
		dir = new File(dirName);
		if(!dir.mkdirs()) throw new RuntimeException("Direcory name "+dirName+" not created");
		System.out.println("Directory created "+dir.getAbsolutePath());
		logFiles = new Hashtable<String,BufferedWriter>();
	}
	public static void init(String dirName, String subDirName) {
		File d = new File(dirName);
		dir = new File(d,dirName);
	//	dir.mkdirs()
	}
	public static void addFile(String name) {
		File f = new File(dir.getAbsolutePath()+"\\"+name);
		try {
			f.createNewFile();
			logFiles.put(name,new BufferedWriter(new FileWriter(f)));
		} catch (IOException e) {e.printStackTrace();}
	}
	public static synchronized void logln(String data, String fileName, boolean echo) {
		BufferedWriter f = logFiles.get(fileName);
		try {
			f.append(data);
			f.newLine();
			f.flush();
		} catch (IOException e) {e.printStackTrace();}
		if(echo) System.out.println(fileName+" << "+data);
	}
	public static synchronized void log(String data, String fileName, boolean echo) {
		BufferedWriter f = logFiles.get(fileName);
		try {
			f.append(data);
			f.flush();
		} catch (IOException e) {e.printStackTrace();}
		if(echo) System.out.print(fileName+" << "+data);
	}
}
