package dcd.highlevel.rdcd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.ast.*;
import dcd.highlevel.ast.program.*;

public class Test4 extends Compiler {

    
    @Override
    public Program getProgram() {
        Program program = new Program(new Role[] {
                new Role(
                        new Name("Head"),
                        new Name("Module"),
                        new Modifier[0],
                        new ConstantDef[0],
                        new Invariant[] {
                            new Invariant(BinExp.EQUALS(SelfFunction.CENTER_POSITION, Direction.NORTH_SOUTH))
                        },
                        new Method[] {
                            new Method(Modifier.STARTUP,new Name("initialize"),new Name("_"),new Block(new Statement[] {
                                    PrimOp.HANDLE_EVENT_MULTI(new Exp[] { ConstantDef.EVENT_HANDLER_1,ConstantDef.EVENT_HANDLER_3 },
                                            new Block(new Statement[] {
                                                new SendCommand("Wheel","evade", new Numeric(0)),
                                                new SingleExp(SelfFunction.SLEEP_CENTIS(new Numeric(200))),
                                                new SingleExp(SelfFunction.ENABLE_EVENT(ConstantDef.EVENT_HANDLER_1)),
                                                new SingleExp(SelfFunction.ENABLE_EVENT(ConstantDef.EVENT_HANDLER_3))
                                            })),     
                            }))
                        }
                ),
                new Role(
                        new Name("Wheel"),
                        new Name("Module"),
                        new Modifier[] { Modifier.ABSTRACT },
                        new ConstantDef[] { 
                            new ConstantDef("connected_direction"), 
                            new ConstantDef("turn_direction"),
                            new ConstantDef("evasion_direction"),
                            new ConstantDef("evasion_sleep", new Numeric(200))
                        },
                        new Invariant[] { 
                            new Invariant(BinExp.EQUALS(SelfFunction.CENTER_POSITION,Direction.EAST_WEST)),
                            new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(new ConstantRef("connected_direction"))), new Numeric(1)))
                        },
                        new Method[] { 
                            new Method(Modifier.STARTUP,new Name("move"),new Name("_"),new Block(new Statement[] {
                                    PrimOp.EVAL_NAMED_COMMAND("TURN_CONTINUOUSLY", new ConstantRef("turn_direction"))
                            })),
                            new Method(Modifier.COMMAND,new Name("evade"),new Name("_"),new Block(new Statement[] {
                                    PrimOp.EVAL_NAMED_COMMAND("TURN_CONTINUOUSLY", new ConstantRef("evasion_direction")),
                                    new SingleExp(SelfFunction.SLEEP_CENTIS(new ConstantRef("evasion_sleep"))),
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
                             new ConstantDef("turn_direction", new Numeric(-100)),
                             new ConstantDef("evasion_direction", new Numeric(100))
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
                             new ConstantDef("turn_direction", new Numeric(100)),
                             new ConstantDef("evasion_direction", new Numeric(100))
                         },
                         new Invariant[] { },
                         new Method[] { }
                 )
        }, new Name[] {
                new Name("Head"), new Name("RightWheel"), new Name("LeftWheel")
        });
        return program;
    }

    public static void main(String argv[]) {
        new Test4().main();
    }
}
