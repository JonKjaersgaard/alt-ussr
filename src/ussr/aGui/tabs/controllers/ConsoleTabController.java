package ussr.aGui.tabs.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsoleTabController {

	private static InputStream inputStream; 
	
	
	
	public static void setInputStream(InputStream inputStream) {
		ConsoleTabController.inputStream = inputStream;
	}



	public static String addText(){
	/*	byte b[] = null;
		try {
			inputStream.read(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String aString = new String(b);*/
		return "";
	}
	
	
	public void some(){
	/*	
		BufferedReader input = new BufferedReader(new InputStreamReader(stream));
        while(true) {
            String line;
            try {
                line = input.readLine();
                if(line==null) break;
                System.out.println(prefix+": "+line);
            } catch (IOException e) {
                throw new Error("Unable to dump stream: "+e); 
            }
        }*/
		
		
	}
	
}
