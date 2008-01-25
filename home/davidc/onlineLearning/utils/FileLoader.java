package onlineLearning.utils;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader {
	public static BufferedReader loadFile()
	{
		Frame frame = new Frame();
		BufferedReader br = null;
		FileDialog fd = new FileDialog(frame, "Load File", FileDialog.LOAD);
		fd.setVisible(true);
		String filename = fd.getDirectory()+"/"+fd.getFile();
		try {
			File file = new File(filename);
			if (file.exists())
			{
				br = new BufferedReader(new FileReader(filename));
				return br;
			}
			else {
				System.out.println("File "+filename+" Not Found");
			}
		}catch (Exception e) {}
		
		return br;
	}

	public static float[][] convertToFloatArray(BufferedReader br) {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try {
			String[] strArray;
			while(br.ready()) {
				strArray = br.readLine().split("\t");
				if(strArray.length!=0 && !(strArray[0].length()==0)) {
					lines.add(strArray);
				}
			}
		} catch (IOException e) {e.printStackTrace();}
		
		float[][] floatFile = new float[lines.size()][lines.get(0).length];
		//System.out.println("Dimensions of file: "+lines.size()+" lines of length "+lines.get(0).length+" elemenets");
		for(int i=0;i<floatFile.length;i++) {
			for(int j=0;j<lines.get(i).length;j++) {
				try {
					floatFile[i][j] = Float.parseFloat(lines.get(i)[j]);
				}
				catch(NumberFormatException e) {System.out.println("Exception at line "+i+" index "+j+" "+e);}
			}
		}
		return floatFile;
	}
}
