package distributedControl;

import java.util.Arrays;
import java.util.List;

public class Program {

    public static enum Opcode {
        StartRotateFromTo,
        FinishRotateFromTo,
        StartOpen,
        FinishOpen,
        StartClose,
        FinishClose,
        Sync,
        AddStateAsPending,
        WaitForEmptyPending,
        AssertConnectionAvailable
    }
    
    public static Instruction mkStartRotateFromTo(int module, int from, int to, int dir) { return new Instruction(module,Opcode.StartRotateFromTo,new int[] { from,to,dir }); }
    public static Instruction mkFinishRotateFromTo(int module, int from, int to, int dir) { return new Instruction(module,Opcode.StartRotateFromTo,new int[] { from,to,dir }); }
    public static Instruction mkStartOpen(int module, int connector) { return new Instruction(module,Opcode.StartOpen,new int[] { connector }); }
    public static Instruction mkFinishOpen(int module, int connector) { return new Instruction(module,Opcode.FinishOpen,new int[] { connector }); }
    public static Instruction mkStartClose(int module, int connector) { return new Instruction(module,Opcode.StartClose,new int[] { connector }); }
    public static Instruction mkFinishClose(int module, int connector) { return new Instruction(module,Opcode.FinishClose,new int[] { connector }); }

    private List<Instruction> instructions;

    public Program(Instruction[] instructions) {
        this.instructions = Arrays.asList(instructions);
    }

    public static class Instruction {
        private int module;
        private Opcode code;
        private int[] argument;
        public Instruction(int _module, Opcode _code, int[] _args) {
            this.module = _module; this.code = _code; this.argument = _args;
        }
        public int getModule() { return module; }
        public Opcode getCode() { return code; }
        public int[] getArguments() { return argument; }
    }
    
    public Instruction get(int index) { return instructions.get(index); }
    public int size() { return instructions.size(); }
    public boolean nextInstructionIsLocal(int index) {
        return index+1<instructions.size() && instructions.get(index).getModule()==instructions.get(index+1).getModule();
    }

}
