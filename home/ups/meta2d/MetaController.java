/**
 * 
 */
package meta2d;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import ussr.samples.atron.ATRONController;

public class MetaController extends ATRONController {
    
    public static final String META_ROLE = "meta2d:role";
    public static final String META_GROUP = "meta2d:group";

    private int metaPosition = Meta2DSimulation.MetaPosition_UNKNOWN;
    private int metaGroup = 0; 
    private Map<Integer,Integer> neighborPositions = new HashMap<Integer,Integer>();
    private int programStep = 0;
    private Object stepLock = new Object();
    private static Program active_program; // Implicit global communication

    public String toString() {
        return "MetaController("+getMetaPosition()+"@"+programStep+")";
    }

    public int getPosition() {
        return getMetaPosition();
    }

    @Override
    public void activate() {
        while(module.getSimulation().isPaused()) super.yield();
        System.out.println(showNeighbors());
        String moduleDesc = module.getProperty("name").split("/")[1];
        String modulePos = moduleDesc.split(":")[0];
        String moduleGroup = moduleDesc.split(":")[1];
        if(modulePos.equals("N")) this.setMetaPosition(Meta2DSimulation.MetaPosition_NORTH);
        else if(modulePos.equals("W")) this.setMetaPosition(Meta2DSimulation.MetaPosition_WEST);
        else if(modulePos.equals("S")) this.setMetaPosition(Meta2DSimulation.MetaPosition_SOUTH);
        else if(modulePos.equals("E")) this.setMetaPosition(Meta2DSimulation.MetaPosition_EAST);
        else throw new Error("Illegal role identifier: "+modulePos);
        module.setProperty(META_GROUP, moduleGroup);
        this.setMetaGroup(Integer.parseInt(moduleGroup));
        float t1 = module.getSimulation().getTime();
        while(module.getSimulation().getTime()-t1 < 1) super.yield();
        //
        if(this.getMetaPosition()==Meta2DSimulation.MetaPosition_NORTH && this.getMetaGroup() == 1) {
            this.handle_MSG_SetActiveProgram(-1,0,0);
            this.handle_MSG_NorthEastMetaStep(-1, 0, 1);
        }
        //
        int oldStep = 0;
        Program oldProgram = MetaController.active_program;
        while(true) {
            synchronized(stepLock) {
                while(programStep==oldStep)
                    try {
                        stepLock.wait();
                    } catch (InterruptedException e) {
                        throw new Error("Unexpected interruption");
                    }
                    if(oldProgram!=MetaController.active_program) {
                        oldProgram = MetaController.active_program;
                        oldStep = 0;
                    }
                    System.out.println("% Module "+this.getModule().getProperty("name")
                            +"<"+this.getPosition()+"> registed step update (old="+oldStep+",current="+programStep+")");

            }
            oldStep = programStep;
            if(programStep>MetaController.active_program.getStatements().length) throw new Error("PC out of bounds");
            Statement statement = MetaController.active_program.getStatements()[programStep-1];
            if(statement.evaluate(this)) {
                this.delay(2000);
                programStep++;
                if(!statement.isGroupStatement()) this.localCall(-1, Meta2DSimulation.MSG_NorthEastMetaStep, 0, programStep);
            }
        }
    }

    public void reverseMeta() {
        this.handle_MSG_SetActiveProgram(-1,0,1);
        this.handle_MSG_NorthEastMetaStep(-1, 0, 1);
    }

    public void normalMeta() {
        this.handle_MSG_SetActiveProgram(-1,0,0);
        this.handle_MSG_NorthEastMetaStep(-1, 0, 1);
    }

    @Override
    public void handleMessage(byte[] message, int messageSize, int channel) {
        if(message.length==0) {
            System.out.println("*** Empty message received");
            return;
        }
        switch(message[0]) {
        case Meta2DSimulation.MSG_DisconnectThisConnector:
            this.handle_MSG_DisconnectThisConnector(channel);
            break;
        case Meta2DSimulation.MSG_ConnectThisConnector:
            this.handle_MSG_ConnectThisConnector(channel);
            break;
        default:
            if(message.length>=2) {
                if(message[1]==0 || message[1]==this.getMetaGroup()) this.handleMetaMessage(message, messageSize, channel);
            } else
                System.out.println("*** Message not understood: "+message[0]);
        }

    }

    private final static int HEADER_INC = 1;
    public void handleMetaMessage(byte[] message, int messageSize, int channel) {
        switch(message[0]) {
        case Meta2DSimulation.MSG_MetaPositionSet:
            this.handle_MSG_MetaPositionSet(channel,message[HEADER_INC+1]);
            break;
        case Meta2DSimulation.MSG_NorthEastMetaStep:
            this.handle_MSG_NorthEastMetaStep(channel, message[HEADER_INC+1], message[HEADER_INC+2]);
            break;
        case Meta2DSimulation.MSG_SetActiveProgram:
            this.handle_MSG_SetActiveProgram(channel, message[HEADER_INC+1], message[HEADER_INC+2]);
            break;
        default:
            System.out.println("Unknown meta command: "+message[0]);
        }
    }

    public void handle_MSG_SetActiveProgram(int channel, int seen, int program) {
        if((seen & getMetaPosition())!=0) return;
        localCall(channel,Meta2DSimulation.MSG_SetActiveProgram,seen|getMetaPosition(),program);
        MetaController.active_program = Meta2DSimulation.program_store[program];
        programStep = 0;
    }
    
    public void handle_MSG_DisconnectThisConnector(int channel) {
        if(!this.canDisconnect(channel)) System.out.println("# Error: requested disconnect for undisconnectable connector");
        this.disconnect(channel);
    }

    public void handle_MSG_ConnectThisConnector(int channel) {
        if(!this.canConnect(channel)) System.out.println("# Error: requested connect for unconnectable connector");
        this.connect(channel);
    }

    public void handle_MSG_MetaPositionSet(int channel, int position) {
        neighborPositions.put(position,channel);
    }

    public void handle_MSG_NorthEastMetaStep(int channel, int seen, int step) {
        System.out.println("* Module "+this.getModule().getProperty("name")
                +"<"+this.getPosition()+"> received <"+seen+","+step+"> on channel"+channel);
        if((seen & getMetaPosition())!=0) return;
        System.out.println("* Module "+this.getModule().getProperty("name")
                +"<"+this.getPosition()+"> proceeding");
        if(step<=programStep) {
            System.out.println("* Module "+this.getModule().getProperty("name")
                    +"<"+this.getPosition()+"> ignoring step "+step+" (local: "+this.programStep+")");
            return;
        }
        localCall(channel, Meta2DSimulation.MSG_NorthEastMetaStep, seen|getMetaPosition(), step);
        System.out.println("* Module "+this.getModule().getProperty("name")
                +"<"+this.getPosition()+"> setting program step to "+step+" (was: "+this.programStep+")");
        synchronized(this.stepLock) {
            this.programStep = step;
            this.stepLock.notify();
        }
        System.out.println("* Module "+this.getModule().getProperty("name")
                +"<"+this.getPosition()+"> done handling message");
    }

    public void localCall(int omit_channel, int message, int argument) {
        localCall(omit_channel, message, new int[] { argument });
    }

    public void localCall(int omit_channel, int message, int argument1, int argument2) {
        localCall(omit_channel, message, new int[] { argument1, argument2 });
    }

    public void localCall(int omit_channel, int message, int[] argument) {
        for(int channel: neighborPositions.values())
            if(channel!=omit_channel) singleCall(channel, message, true, argument);
    }

    public void setMetaPosition(int position) {
        module.setProperty(META_ROLE, Integer.toString(position));
        this.metaPosition = position;
        multiCall(Meta2DSimulation.MSG_MetaPositionSet,true,position);
    }

    public int multiCall(int message, boolean includeGroup, int argument) {
        return this.multiCall(message, includeGroup, new int[] { argument });
    }

    public int multiCall(int message, boolean includeGroup, int argument1, int argument2) {
        return this.multiCall(message, includeGroup, new int[] { argument1, argument2 });
    }

    public int multiCall(int operation, boolean includeGroup, int[] arguments) {
        int count = 0;
        for(int c=0; c<8; c++) {
            if(this.isOtherConnectorNearby(c)) {
                singleCall(c, operation, includeGroup, arguments);
                count++;
            }
        }
        return count;
    }

    public void singleCall(int channel, int operation, boolean includeGroup) {
        singleCall(channel, operation, includeGroup, new int[0]);
    }

    public void singleCall(int channel, int operation, boolean includeGroup, int[] arguments) {
        int offset = 2;
        byte[] message = new byte[arguments.length+offset];
        message[0] = (byte)operation;
        message[1] = includeGroup ? (byte)this.getMetaGroup() : 0;
        for(int i=0; i<arguments.length; i++)
            message[i+offset] = (byte)arguments[i];
        this.sendMessage(message, (byte)message.length, (byte)channel);
    }

    // Debugging

    public String showNeighbors() {
        StringBuffer result = new StringBuffer();
        result.append("Connections for module "+module.getProperty("name")+": ");
        for(int i=0; i<8; i++)
            if(this.isOtherConnectorNearby(i)) result.append(i+" ");
        return result.toString();
    }

    public int role2channel(int where) {
        Integer channel = neighborPositions.get(where);
        if(channel==null) throw new Error("Internal error: unknown role number "+where);
        return channel;
    }

    public Collection<Integer> getMetaNeighbors() {
        return neighborPositions.values();
    }

    public void symmetricDisconnect(int connector) {
        if(this.canDisconnect(connector))
            this.disconnect(connector);
        else
            this.singleCall(connector,Meta2DSimulation.MSG_DisconnectThisConnector,false);
    }

    public void symmetricConnect(int connector) {
        if(this.canConnect(connector))
            this.connect(connector);
        else
            this.singleCall(connector,Meta2DSimulation.MSG_ConnectThisConnector,false);
    }

    public int getMetaPosition() {
        if(metaPosition!=Meta2DSimulation.MetaPosition_UNKNOWN) return metaPosition;
        return Integer.parseInt(module.getProperty("name").split("/")[1].split(":")[0]);
    }

    private void setMetaGroup(int metaGroup) {
        this.metaGroup = metaGroup;
    }

    public int getMetaGroup() {
        if(metaGroup!=0) return metaGroup;
        return Integer.parseInt(module.getProperty("name").split("/")[1].split(":")[1]);
    }
}