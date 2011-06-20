package distributedControl;

import java.util.Arrays;
import java.util.List;

public class Program {

    public static final class Opcode {
        public static final byte StartRotateFromTo = 1;
        public static final byte FinishRotateFromTo = 2;
        public static final byte StartOpen = 3;
        public static final byte FinishOpen = 4;
        public static final byte StartClose = 5;
        public static final byte FinishClose = 6;
        public static final byte Sync = 7;
        public static final byte AddStateAsPending = 8;
        public static final byte WaitForEmptyPending = 9;
        public static final byte AssertConnectionAvailable = 10;
    }
    private static final byte MODULE_UNDEFINED = 127;
    
    public static Instruction mkStartRotateFromTo(int module, int from, int to, int dir) { return new Instruction(module,Opcode.StartRotateFromTo,new int[] { from,to,dir }); }
    public static Instruction mkFinishRotateFromTo(int module, int from, int to, int dir) { return new Instruction(module,Opcode.StartRotateFromTo,new int[] { from,to,dir }); }
    public static Instruction mkStartOpen(int module, int connector) { return new Instruction(module,Opcode.StartOpen,new int[] { connector }); }
    public static Instruction mkFinishOpen(int module, int connector) { return new Instruction(module,Opcode.FinishOpen,new int[] { connector }); }
    public static Instruction mkStartClose(int module, int connector) { return new Instruction(module,Opcode.StartClose,new int[] { connector }); }
    public static Instruction mkFinishClose(int module, int connector) { return new Instruction(module,Opcode.FinishClose,new int[] { connector }); }

    public static class Instruction {
        private int module;
        private byte code;
        private int[] argument;
        public Instruction(int _module, byte _code, int[] _args) {
            this.module = _module; this.code = _code; this.argument = _args;
            if(_module==MODULE_UNDEFINED) throw new Error("Illegal module ID");
        }
        public byte getModule() { return (byte)module; }
        public byte getCode() { return code; }
        public int[] getArguments() { return argument; }
        public int size() { return 2+argument.length; }
    }
    
    private byte[] instructions;

    public Program(Instruction[] instructions) {
        int size = 0;
        for(int i=0; i<instructions.length; i++)
            size+=instructions[i].size();
        byte[] result = new byte[size];
        int index=0;
        for(int i=0; i<instructions.length; i++) {
            Instruction ins = instructions[i];
            result[index++] = ins.getModule();
            result[index++] = ins.getCode();
            for(int j=0; j<ins.getArguments().length; j++)
                result[index++] = (byte)ins.getArguments()[j];
        }
        this.instructions = result;
    }

    public Program() {
        instructions = new byte[0];
    }

    public synchronized void addInstruction(int size, int state, byte module, byte opcode, byte[] args) {
        if(instructions.length==0) {
            instructions = new byte[size];
            for(int i=0; i<size; i++)
                instructions[i] = MODULE_UNDEFINED;
        }
        instructions[state] = module;
        instructions[state+1] = opcode;
        for(int i=0; i<args.length; i++)
            instructions[state+2+i] = args[i];
    }
    
    public synchronized byte getModule(int index) { return instructions[index]; }
    public synchronized byte getOpcode(int index) { return instructions[index+1]; }
    public synchronized byte getArg(int index, int n) { return instructions[index+2+n]; }
    public int programSize() { return instructions.length; }
    public int instructionSize(int index) {
        return 2+nArgs(getOpcode(index));
    }
    public static int nArgs(int opcode) throws Error {
        switch(opcode) {
        case Opcode.StartRotateFromTo:
        case Opcode.FinishRotateFromTo: return 3;
        case Opcode.StartOpen: 
        case Opcode.FinishOpen:
        case Opcode.StartClose:
        case Opcode.FinishClose: return 1;
        default: throw new Error("Unknown opcode at index: opcode");
        }
    }
    public int next(int index) { return index+instructionSize(index); }
    public boolean nextInstructionIsLocal(int index) {
        return next(index)<programSize() && getModule(index)==getModule(next(index));
    }
    public boolean instructionAvailableAt(int index) {
        return index<instructions.length && instructions[index]!=MODULE_UNDEFINED;
    }
    public byte[] getArgs(int index) {
        int n = nArgs(getOpcode(index));
        byte[] result = new byte[n];
        for(int i=0;i<n;i++)
            result[i] = getArg(index,i);
        return result;        
    }

}
