package dcd.highlevel.rdcd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dcd.highlevel.CFileBuilder;
import dcd.highlevel.ast.*;
import dcd.highlevel.ast.program.*;

public class Test1 extends Compiler {

    
    @Override
    public Program getProgram() {
        Program program = new Program(new Role[] {
                new Role(
                        new Name("Wheel"),
                        new Name("Module"),
                        new Modifier[] { Modifier.ABSTRACT },
                        new ConstantDef[] { new ConstantDef("connected_direction"), new ConstantDef("event_handler"), new ConstantDef("turn_direction") },
                        new Invariant[] { 
                            new Invariant(BinExp.EQUALS(SelfFunction.CENTER_POSITION,Direction.EAST_WEST)),
                            new Invariant(BinExp.EQUALS(SelfFunction.TOTAL_CONNECTED,new Numeric(1))),
                            new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(Direction.UP)),new Numeric(1))),
                            new Invariant(BinExp.EQUALS(Function.SIZEOF(SelfFunction.CONNECTED(new ConstantRef("connected_direction"))), new Numeric(1)))
                        },
                        new Method[] { 
                            new Method(Modifier.STARTUP,new Name("move"),new Name("_"),new Block(new Statement[] {
                                    new Conditional(
                                            BinExp.GREATER(SelfFunction.Y,new Numeric(0)),
                                            PrimOp.HANDLE_EVENT(new ConstantRef("event_handler"),
                                                    new Block(new Statement[] {
                                                        new SingleExp(SelfFunction.DISABLE_EVENT(new ConstantRef("event_handler"))),
                                                        new SendCommand("Wheel","stop", new Numeric(0))
                                                    })),
                                            new Nop()),
                                    //new SendCommand("Wheel","reportZ",SelfFunction.Z),
                                    PrimOp.EVAL_NAMED_COMMAND("TURN_CONTINUOUSLY", new ConstantRef("turn_direction"))
                            })),
                            new Method(Modifier.COMMAND,new Name("stop"),new Name("_"),new Block(new Statement[] {
                                    new AssumeRole("Reverse",new Block(new Statement[] {
                                            PrimOp.EVAL_NAMED_COMMAND("TURN_CONTINUOUSLY",new Negate(new ConstantRef("turn_direction"))),
                                            new SingleExp(SelfFunction.SLEEP_WHILE_TURNING(new Numeric(3))),
                                            PrimOp.EVAL_NAMED_COMMAND("TURN_CONTINUOUSLY",new ConstantRef("turn_direction")),
                                            new SingleExp(SelfFunction.ENABLE_EVENT(new ConstantRef("event_handler")))
                                    }))
                            }))//,
                            /*new Method(Modifier.COMMAND,new Name("reportZ"),new Name("z"),new Block(new Statement[] {
                                    new Conditional(
                                            BinExp.GREATER(SelfFunction.Z, new LocalName("z")),
                                            new SingleExp(SelfFunction.INHIBIT_ROLE()),
                                            new Nop())
                            }))*/
                        }
                        ),
                 new Role(
                         new Name("RightWheel"),
                         new Name("Wheel"),
                         new Modifier[] { },
                         new ConstantDef[] { 
                             new ConstantDef("connected_direction", Direction.EAST),
                             new ConstantDef("event_handler", ConstantDef.EVENT_HANDLER_5),
                             new ConstantDef("turn_direction", new Numeric(1))
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
                             new ConstantDef("event_handler", ConstantDef.EVENT_HANDLER_3),
                             new ConstantDef("turn_direction", new Numeric(-1))
                         },
                         new Invariant[] { },
                         new Method[] { }
                 ),
                 new Role(
                         new Name("Reverse"),
                         new Name("Module"),
                         new Modifier[] { Modifier.MIXIN },
                         new ConstantDef[] { },
                         new Invariant[] { },
                         new Method[] { }
                 ),
                 new Role(
                         new Name("Axle"),
                         new Name("Module"),
                         new Modifier[] { },
                         new ConstantDef[] { },
                         new Invariant[] {
                             new Invariant(BinExp.GREATER(Function.SIZEOF(SelfFunction.CONNECTED_ROLE(Direction.DOWN,"Wheel")),new Numeric(0))),
                         },
                         new Method[] {
                             new Method(Modifier.BEHAVIOR,new Name("steer"),new Name("_"),new Block(new Statement[] {
                                     new Conditional(
                                             BinExp.GREATER(Function.SIZEOF(SelfFunction.CONNECTED_ROLE(Direction.DOWN, "Reverse")),new Numeric(0)),
                                             new Conditional(
                                                     BinExp.GREATER(SelfFunction.Y, new Numeric(0)),
                                                     PrimOp.EVAL_NAMED_COMMAND("ROTATE_TO", new Numeric(20)),
                                                     PrimOp.EVAL_NAMED_COMMAND("ROTATE_TO", new Numeric(16))),
                                             PrimOp.EVAL_NAMED_COMMAND("ROTATE_TO", new Numeric(18)))
                             }))
                         }
                         )
        }, new Name[] {
                new Name("RightWheel"), new Name("LeftWheel"), new Name("Axle")
        });
        return program;
    }

    public static void main(String argv[]) {
        new Test1().main();
    }
}
