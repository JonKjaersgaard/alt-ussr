package distributedControl;

import distributedControl.Program.Instruction;

public class TestPrograms {

    protected static final Program p1 = new Program(new Program.Instruction[] {
            Program.mkStartOpen(0, 0),
            Program.mkFinishOpen(0, 0),
            Program.mkStartOpen(3, 4),
            Program.mkFinishOpen(3, 4),
            Program.mkStartRotateFromTo(3, 0, 324, 0)
    });

}
