package distributedControl;

import distributedControl.Program.Instruction;
import distributedControl.Program.Opcode;

public class InterpreterStateMachine extends StateMachine {
    private int localID;
    private int localActiveState = 255;
    private Program program;
    
    public InterpreterStateMachine(Program program) {
        this.program = program;
    }

    private boolean doneRotatingTo(int goal) { 

        if(api.isRotating()) {
            return false; 
        }

        return true;
    }

    private void rotateDirTo(int to, boolean direction) {
        api.rotateDirToInDegrees(to, direction);
    }


    private void connect(int connector) {
        api.connect(connector);
    }

    private void disconnect(int connector) {
        api.disconnect(connector);
    }

    private boolean notDoneConnecting(int connector) {
        return !api.isConnected(connector);
    } 

    private boolean notDoneDisconnecting(int connector) {
        return api.isConnected(connector);
    }

    @Override
    public boolean checkPendingStateResponsibility(int address, int pendingState) {
        return false;
    }

    @Override
    public void init(int id) {
        localID = id;
        localActiveState = 255;
        reset_state();
        stateManager.init(id,0);
    }

    @Override
    protected void stateMachine() {
        api.yield();
        if(localActiveState == 255) { /* try to see if there's a new state for me */
            localActiveState = stateManager.getMyNewState();
            if(localActiveState!=255) {
                System.out.println(getLocalID()+": Now performing state "+localActiveState);
            }
        }
        if(localActiveState!=255 && localActiveState<getProgram().size()) {
            Instruction ins = getProgram().get(localActiveState);
            if(ins.getModule()!=getLocalID()) throw new Error("Incorrect module selection");
            /* Execute next instruction */
            Opcode code = ins.getCode();
            if(code==Opcode.StartClose) {
                connect(ins.getArguments()[0]);
            } else if(code==Opcode.FinishClose) {
                if(notDoneConnecting(ins.getArguments()[0])) return;
            } else if(code==Opcode.StartOpen) {
                disconnect(ins.getArguments()[0]);
            } else if(code==Opcode.FinishOpen) {
                if(notDoneDisconnecting(ins.getArguments()[0])) return;
            } else if(code==Opcode.StartRotateFromTo) {
                rotateDirTo(ins.getArguments()[1],ins.getArguments()[2]!=0);
            } else if(code==Opcode.FinishRotateFromTo) {
                if(!doneRotatingTo(ins.getArguments()[1])) return;
            }
            /* Advance, local or remote */
            if(localActiveState+1==getProgram().size())
                localActiveState = 255;
            else if(getProgram().nextInstructionIsLocal(localActiveState))
                localActiveState++;
            else {
                stateManager.sendState(localActiveState+1,getProgram().get(localActiveState+1).getModule());
                localActiveState = 255;
            }
        }
    }

    protected int getLocalID() {
        return localID;
    }

    public void reset_state() {
        stateManager.reset_state();
    }

    protected Program getProgram() { return program; }

}
