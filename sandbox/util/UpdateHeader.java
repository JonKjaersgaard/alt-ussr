package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UpdateHeader {

    public static final String HEADER = 
        "/**\n"+
        " * Unified Simulator for Self-Reconfigurable Robots (USSR)\n"+ 
        " * (C) University of Southern Denmark 2008\n"+
        " * This software is distributed under the BSD open-source license.\n"+
        " * For licensing see the file LICENCE.txt included in the root of the USSR distribution.\n"+ 
        " */\n";
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Number of files to process: "+args.length);
        for(int i=0; i<args.length; i++)
            processFile(args[i]);
        System.out.println("All files processed successfully");
    }
    
    private static void processFile(String fileName) {
        try {
            File file = new File(fileName);
            if(!file.canRead()) throw new Error("Unable to read file "+fileName);
            FileReader input = new FileReader(file);
            StringBuffer buffer = new StringBuffer(HEADER);
            BufferedReader reader = new BufferedReader(input);
            // Read until and including package declaration
            while(true) {
                String line = reader.readLine();
                if(line==null) throw new Error("No package declaration found in file "+fileName);
                if(line.startsWith("package")) {
                    buffer.append(line);
                    buffer.append('\n');
                    break;
                }
            }
            // Copy rest into buffer
            while(true) {
                int c = reader.read();
                if(c==-1) break;
                buffer.append((char)c);
            }
            // Done, write to file
            if(!file.canWrite()) throw new Error("Unable to write to file "+fileName);
            reader.close();
            FileWriter writer = new FileWriter(file);
            writer.write(buffer.toString());
            writer.close();
        } catch(IOException exn) {
            throw new Error("Processing failed for file "+fileName+": "+exn);
        }
    }

}
