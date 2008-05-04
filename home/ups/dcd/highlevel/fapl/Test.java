package dcd.highlevel.fapl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.Resolver;
import dcd.highlevel.ast.program.Negate;
import dcd.highlevel.ast.program.Numeric;

public class Test {

    public static final String OUTPUT_FILE = "/Users/ups/eclipse_workspace/ussr/home/ups/fapl_gen.c";

    public static void main(String argv[]) {
        Program program = new Program(
                new Unit[] {
                   //new Function("stop", new Parameter[0], new Apply(new PrimName("apply*"),new PrimName("@centerStop"))),
                   //new Evaluate(new Apply(new FunName("stop"),new Numeric(0)))
		    //new Evaluate(new Apply(new PrimName("apply"),new PrimName("centerStop")))
		    //		    new Value("rotateAll", new Closure(new Env[], SelfFunction.TURN_TOWARDS(new Numeric(30)))),
		    //		    new Evaluate(new Apply(new PrimName("apply"),new VarName("rotateAll")))
/*
INS_INSTALL_COMMAND, ROLE_ANY, 128, _pos, _len,
MIGRATE_CONTINUE,
TURN_TOWARDS, 30,
END_TERMINATE

PUSHC 128
PUSHC PRIM_APPLY
APPLY
 */
		    new Function("turn","degrees","FrontAxle",new Apply(new PrimName("turnTo"),new LocalName("degrees"))),
		    new Function("turn","degrees","RearAxle",new Apply(new PrimName("turnTo"),new Negate(new LocalName("degrees")))),
		    new Function("_eval_0","_","ANY",new Apply(new GlobalName("turn"),new Numeric(45))),
		    new Evaluate(new Apply(new PrimName("apply"),new GlobalName("_eval_0"))) // apply* (turn 45)
		    /*
INS_INSTALL_COMMAND, 128, ROLE_FRONTAXLE, _pos, _len, // could be conditional install
MIGRATE_CONTINUE,
PUSH_ARG,
PUSHC, PRIM_TURNTO,
APPLY,
END_TERMINATE

...also 128...

INS_INSTALL_COMMAND, 129, ROLE_ANY, _pos, _len,
MIGRATE_CONTINUE,
PUSHC, 45,
PUSHC, 128,
APPLY,
END_TERMINATE

PUSHC 128,
PUSHC PRIM_APPLY
APPLY
END_TERMINATE
		     */
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
