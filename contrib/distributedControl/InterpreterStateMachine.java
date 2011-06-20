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
        api.rotateDirToInDegrees(to*108, direction);
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
        if(id!=6) program = new Program();
        localID = id;
        localActiveState = 255;
        reset_state();
        stateManager.init(id,0,getProgram());
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
        if(localActiveState!=255) {
            if(!getProgram().instructionAvailableAt(localActiveState)) {
                System.out.println(getLocalID()+": Waiting for instruction");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new Error("Unexpected interruption");
                }
                return;
            } else if(localActiveState>=getProgram().programSize()) {
                throw new Error("Program size exceeded");
            } else if(program.getModule(localActiveState)!=getLocalID()) {
                throw new Error("Incorrect module selection");
            } else {
                /* Execute next instruction */
                switch(program.getOpcode(localActiveState)) {
                case Opcode.StartClose: {
                    connect(program.getArg(localActiveState, 0));
                    break;
                }
                case Opcode.FinishClose: {
                    if(notDoneConnecting(program.getArg(localActiveState, 0))) return;
                    break;
                }
                case Opcode.StartOpen: {
                    disconnect(program.getArg(localActiveState,0));
                    break;
                }
                case Opcode.FinishOpen: {
                    if(notDoneDisconnecting(program.getArg(localActiveState,0))) return;
                    break;
                }
                case Opcode.StartRotateFromTo: {
                    rotateDirTo(program.getArg(localActiveState,1),program.getArg(localActiveState,2)!=0);
                    break;
                } 
                case Opcode.FinishRotateFromTo: {
                    if(!doneRotatingTo(program.getArg(localActiveState,1))) return;
                    break;
                }
                default: throw new Error("Unknown opcode");
                }
                /* Advance, local or remote */
                if(getProgram().next(localActiveState)==getProgram().programSize())
                    localActiveState = 255;
                else if(getProgram().nextInstructionIsLocal(localActiveState))
                    localActiveState = getProgram().next(localActiveState);
                else {
                    int next = getProgram().next(localActiveState);
                    stateManager.sendState(next,getProgram().getModule(next));
                    localActiveState = 255;
                }
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
