package dcd.highlevel;

import java.io.PrintWriter;

import dcd.highlevel.ast.*;
import dcd.highlevel.ast.program.*;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
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
                            new Method(Modifier.STARTUP,new Name("move"),new Block(new Statement[] {
                                    new Conditional(
                                            BinExp.GREATER(SelfFunction.Y,new Numeric(0)),
                                            PrimOp.HANDLE_EVENT(new ConstantRef("event_handler"),
                                                    new Block(new Statement[] {
                                                        new SingleExp(SelfFunction.DISABLE_EVENT(new ConstantRef("event_handler"))),
                                                        new Command("Wheel","stop")
                                                    })),
                                            new Nop())
                            })),
                            new Method(Modifier.COMMAND,new Name("stop"),new Block(new Statement[] {
                                    new AssumeRole("Reverse",new Block(new Statement[] {
                                            new SingleExp(SelfFunction.TURN_CONTINUOUSLY(UnaryExp.NEGATE(new ConstantRef("turn_direction"))))
                                    }))
                            }))
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
                         new Name("Axle"),
                         new Name("Module"),
                         new Modifier[] { },
                         new ConstantDef[] { },
                         new Invariant[] { },
                         new Method[] {
                             new Method(Modifier.BEHAVIOR,new Name("turn"),new Block(new Statement[] {
                                     new SingleExp(SelfFunction.TURN_CONTINUOUSLY(new Numeric(87)))
                             }))
                         }
                         )
        }, new Name[] {
                new Name("RightWheel")
        });
        
        CopyDownPhase cdp = new CopyDownPhase(program);
        cdp.copyDown();
        VTableResolutionPhase vrp = new VTableResolutionPhase(program);
        vrp.resolve();
        
        CFileGenerator cfg = new CFileGenerator(program,vrp);
        cfg.generate(new PrintWriter(System.out));
        
        System.out.println("Done!");
    }

}
