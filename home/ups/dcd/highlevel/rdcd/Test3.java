package dcd.highlevel.rdcd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.ast.*;
import dcd.highlevel.ast.program.*;

public class Test3 extends Compiler {

    
    @Override
    public Program getProgram() {
        Program program = new Program(new Role[] {
                new Role(
                        new Name("Wheel"),
                        new Name("Module"),
                        new Modifier[] { Modifier.ABSTRACT },
                        new ConstantDef[] { new ConstantDef("connected_direction"), new ConstantDef("turn_direction") },
                        new Invariant[] { 
                            new Invariant(BinExp.EQUALS(SelfFunction.CENTER_POSITION,Direction.EAST_WEST)),
                            new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(new ConstantRef("connected_direction"))), new Numeric(1)))
                        },
                        new Method[] { 
                            new Method(Modifier.STARTUP,new Name("move"),new Name("_"),new Block(new Statement[] {
                                    PrimOp.EVAL_NAMED_COMMAND("TURN_CONTINUOUSLY", new ConstantRef("turn_direction"))
                            }))
                        }
                        ),
                 new Role(
                         new Name("RightWheel"),
                         new Name("Wheel"),
                         new Modifier[] { },
                         new ConstantDef[] { 
                             new ConstantDef("connected_direction", Direction.EAST),
                             new ConstantDef("turn_direction", new Numeric(-1))
                         },
                         new Invariant[] { },
                         new Method[] { }
                         ),
                 new Role(
                         new Name("LeftWheel"),
                         new Name("Wheel"),
                         new Modifier[] { },
                         new ConstantDef[] { 
                             new ConstantDef("connected_direction", Direction.WEST),
                             new ConstantDef("turn_direction", new Numeric(1))
                         },
                         new Invariant[] { },
                         new Method[] { }
                 )
        }, new Name[] {
                new Name("RightWheel"), new Name("LeftWheel")
        });
        return program;
    }

    public static void main(String argv[]) {
        new Test3().main();
    }
}
