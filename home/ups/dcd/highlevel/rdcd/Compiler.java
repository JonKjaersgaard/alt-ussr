package dcd.highlevel.rdcd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.ast.*;
import dcd.highlevel.ast.program.*;

public abstract class Compiler {

    public static final String OUTPUT_FILE = "/Users/ups/eclipse_workspace/ussr/home/ups/rdcd_gen.c";
    
    public abstract Program getProgram();

    public void main() {
        Program program = this.getProgram();
        CopyDownPhase cdp = new CopyDownPhase(program);
        cdp.copyDown();
        VTableResolutionPhase vrp = new VTableResolutionPhase(program);
        vrp.resolve();
        
        RDCDCodeGenerator cfg = new RDCDCodeGenerator("car",program,vrp);
        OutputStreamWriter output;
        try {
            output = new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE));
        } catch (FileNotFoundException e) {
            throw new Error("Unable to open output file: "+OUTPUT_FILE);
        }
        cfg.generate(new CFileBuilder(new PrintWriter(output)));
        
        System.out.println("Done: output written to file "+OUTPUT_FILE);
    }

}
