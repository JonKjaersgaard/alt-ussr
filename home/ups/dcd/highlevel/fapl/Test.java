package dcd.highlevel.fapl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.Resolver;
import dcd.highlevel.ast.Invariant;
import dcd.highlevel.ast.program.BinExp;
import dcd.highlevel.ast.program.ConstantRef;
import dcd.highlevel.ast.program.Direction;
import dcd.highlevel.ast.program.Function;
import dcd.highlevel.ast.program.LocalName;
import dcd.highlevel.ast.program.Negate;
import dcd.highlevel.ast.program.Numeric;
import dcd.highlevel.ast.program.SelfFunction;

public class Test {

    public static final String OUTPUT_FILE = "/Users/ups/eclipse_workspace/ussr/home/ups/fapl_gen.c";

    public static void main(String argv[]) {
        Program program = new Program(
                new Unit[] {
                        new RoleInvariantDef("Wheel",null,true,
                                new Invariant[] { 
                                new Invariant(BinExp.EQUALS(SelfFunction.CENTER_POSITION,Direction.EAST_WEST)),
                                new Invariant(BinExp.EQUALS(SelfFunction.TOTAL_CONNECTED,new Numeric(1))),
                                new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(Direction.UP)),new Numeric(1))),
                        }),
                        new RoleInvariantDef("LeftWheel","Wheel",false,
                                new Invariant[] {
                                new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(Direction.WEST)), new Numeric(1)))
                        }),
                        new RoleInvariantDef("RightWheel","Wheel",false,
                                new Invariant[] {
                                new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(Direction.EAST)), new Numeric(1)))
                        }),
                        new RoleInvariantDef("Axle",null,true,
                        		new Invariant[] {
                                new Invariant(BinExp.GREATER(Function.SIZEOF(SelfFunction.CONNECTED_ROLE(Direction.DOWN,"Wheel")),new Numeric(0)))
                         }),
                         new RoleInvariantDef("FrontAxle","Axle",false,
                                 new Invariant[] {
                                 new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(Direction.NORTH)), new Numeric(0)))
                         }),
                         new RoleInvariantDef("RearAxle","Axle",false,
                                 new Invariant[] {
                                 new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(Direction.SOUTH)), new Numeric(0)))
                         }),
                        new FunctionDef("stop", "_", "ANY", new Apply(new PrimName("apply"),new PrimName("centerStop"))),
                        new FunctionDef("turn","degrees","FrontAxle",new Apply(new PrimName("turnTo"),new LocalName("degrees"))),
                        new FunctionDef("turn","degrees","RearAxle",new Apply(new PrimName("turnTo"),new Negate(new LocalName("degrees")))),
                        new FunctionDef("_eval_0","_","ANY",new Apply(new GlobalName("turn"),new Numeric(45))),
                        new Evaluate(new Apply(new PrimName("apply"),new GlobalName("_eval_0"))) // apply* (turn 45)
                });
        // Step 1: resolve values (constants) etc
        // Step 2, for functions, compute set of roles, with associated behaviors [skip now]
        Resolver r = new IncrementalNameResolver();
        PropagateInvariantsPhase pip = new PropagateInvariantsPhase(program);
        pip.propagate();
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
