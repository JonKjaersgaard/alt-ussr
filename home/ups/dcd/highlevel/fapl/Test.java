package dcd.highlevel.fapl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.Resolver;
import dcd.highlevel.ast.program.Numeric;

public class Test {

    public static final String OUTPUT_FILE = "/Users/ups/eclipse_workspace/ussr/home/ups/fapl_gen.c";

    public static void main(String argv[]) {
        Program program = new Program(
                new Unit[] {
                   //new Function("stop", new Parameter[0], new Apply(new PrimName("apply*"),new PrimName("@centerStop"))),
                   //new Evaluate(new Apply(new FunName("stop"),new Numeric(0)))
                        new Evaluate(new Apply(new PrimName("apply"),new PrimName("centerStop")))
                });
        // Step 1: resolve values (constants) etc
        // Step 2, for functions, compute set of roles, with associated behaviors [skip now]
        Resolver r = new IncrementalNameResolver();
        FAPLCodeGenerator gen = new FAPLCodeGenerator("stop_all",program,r);
        OutputStreamWriter output;
        try {
            output = new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE));
        } catch (FileNotFoundException e) {
            throw new Error("Unable to open output file: "+OUTPUT_FILE);
        }
        gen.generate(new CFileBuilder(new PrintWriter(output)));
        System.out.println("Wrote program to "+OUTPUT_FILE);
    }
    
}
