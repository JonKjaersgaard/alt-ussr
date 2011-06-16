package distributedControl;

import java.util.Arrays;
import java.util.BitSet;

public class DistributedStateManager implements CommunicationManager {
    public static final boolean USE_MONITOR = false;
    public static int MAX_N_PENDING_STATES = 5;

    private static byte MAGIC_HEADER = 107;
    private static byte HEADER_PLUS_FIXED_PAYLOAD = 4;
    protected static final int WAITTIME = 100;
    protected static final float WAITTIME_MS = WAITTIME/1000.0f;

    /** 
     * Byte to integer conversion function
     * @param b
     * @return
     */
    private static int b2i(byte b) {
        return (int)b&0xff;
    }
    
    /**
     * Package exchanged between nodes
     * @author ups
     */
    private class EightMsg {

        // Bit for switching to new sequence
        boolean alternateSequenceFlag;
        // Global state
        int state;
        // Current pending states
        int[] pending = new int[MAX_N_PENDING_STATES];
        // ID of module to execute state
        int recipientID;

        EightMsg(byte[] raw) {
            if(raw[0]!=MAGIC_HEADER) throw new Error("Incorrect packet");
            alternateSequenceFlag = raw[1]!=0;
            state = b2i(raw[2]);
            recipientID = b2i(raw[3]);
            for(int i=0; i<MAX_N_PENDING_STATES; i++)
                pending[i] = b2i(raw[4+i]);
        }

        /**
         * @param alternateSequenceFlag
         * @param state
         * @param recipientID
         * @param pending
         */
         EightMsg(boolean alternateSequenceFlag, int state, int recipientID, int[] pending) {
            this.alternateSequenceFlag = alternateSequenceFlag;
            this.state = state;
            this.recipientID = recipientID;
            this.pending = pending;
        }

        byte[] encode() {
            byte[] packet = new byte[HEADER_PLUS_FIXED_PAYLOAD+MAX_N_PENDING_STATES];
            packet[0] = MAGIC_HEADER;
            packet[1] = alternateSequenceFlag ? (byte)1 : 0;
            packet[2] = (byte)state;
            packet[3] = (byte)recipientID;
            for(int i=0; i<MAX_N_PENDING_STATES; i++)
                packet[4+i] = (byte)pendingStates[i];
            return packet;
        }
    }

    // Merge function implementation
    private static void intersect_plus_greater(int set0[], int set1[], int min, int dest[]) {
        int index0, index1, desti;
        desti = 0;
        for(index0=0; index0<MAX_N_PENDING_STATES; index0++) {
            if(set0[index0]==0) continue;
            if(set0[index0]>min) {
                dest[desti++] = set0[index0];
                continue;
            }
            for(index1=0; index1<MAX_N_PENDING_STATES; index1++)
                if(set0[index0]==set1[index1]) {
                    dest[desti++] = set0[index0];
                    break;
                }
        }
        for(index0=desti; index0<MAX_N_PENDING_STATES; index0++)
            dest[index0] = 0;
    }

    private static void intersect(int set0[], int set1[], int dest[]) {
        intersect_plus_greater(set0,set1,255,dest);
    }

    /* local state (s0,p0), incoming state (s1,p1), resulting state (return,p2) */
    /* correctness: see merge.c (tests equivalent implementation) */
    private static int merge(int s0, int p0[], int s1, int p1[], int p2[]) {
        if(s0>s1) {
            intersect_plus_greater(p0,p1,s1,p2);
            return s0;
        }    
        else if(s0==s1) {
            intersect(p0,p1,p2);
            return s0;
        }
        else { /* s0<s1 */
            intersect_plus_greater(p1,p0,s0,p2);
            return s1;
        }
    }

    // Local state of distributed communication
    private CommunicationProvider provider;
    private int myID;
    private int localState;
    private boolean activated = false;
    private static final int INIT_WAITTIME_MS = 0;
    private int[] pendingStates = new int[MAX_N_PENDING_STATES];
    private BitSet pendingHandled = new BitSet();
    private int globalState;
    private int recipientID;
    private boolean startingModule;
    private boolean alternateSequenceFlag;
    private float lastTime;
    private boolean limitPendingOneWay;
    private boolean firstInit = true;
    
    public void senderAct() {
        float time = provider.getTime();
        if(lastTime+WAITTIME_MS>time) return;
        lastTime = time;
        EightMsg msg; 
        synchronized(DistributedStateManager.this) {
            msg = new EightMsg(alternateSequenceFlag, globalState, recipientID, pendingStates);
        }
        provider.broadcastMessage(msg.encode());
    }
    
    public synchronized int getMyNewState() {
        int state = localState;
        localState = 255;
        return state;
    }

    public synchronized boolean pendingStatesPresent() {
        int i;
        for(i=0; i<MAX_N_PENDING_STATES; i++) {
            if(pendingStates[i] != 0) {
                //System.out.println("[Pending states are present]");
                return true;
            }
        }
        return false;
    }

    public synchronized void sendState(int state, int recpID) {
        if(state>globalState) {
            globalState = state;
            recipientID = recpID;
        }
    }

    public void reset_sequence() {
        throw new Error("Sequence reset not implemented");
    }

    public synchronized void reset_state() {
        int i;
        startingModule = true;
        /* reset the vars to their init values */
        globalState = 0;
        localState = 255;
        recipientID = 0;
        for(i=0; i<MAX_N_PENDING_STATES; i++) pendingStates[i] = 0;
        pendingHandled = new BitSet();
        /* flip the flag (can't do that with ~alternateSequenceFlag, beware!) */
        /*if(alternateSequenceFlag)
            alternateSequenceFlag = false;
        else
            alternateSequenceFlag = true;*/
        this.notifyAll();
        if(USE_MONITOR) update();
    }

    public void init(int myID, int firstModuleID) {
        this.myID = myID;
        //if(firstInit) {
        //firstInit = false;
        if(myID==firstModuleID) {
            provider.delay(INIT_WAITTIME_MS);
            if(globalState==0) localState = 0;
        }
  //      }
        if(USE_MONITOR) update();
    }

    public int getGlobalState() {
        return globalState;
    }

    private static final int[] emptyComparator = new int[MAX_N_PENDING_STATES]; 
    public synchronized void receive(byte[] rawMessage) {
        EightMsg msg = new EightMsg(rawMessage);
        /* this should not apply to the module starting the sequence*/
        if(alternateSequenceFlag != msg.alternateSequenceFlag && (!startingModule)) {
            /* reset the vars to their init values */
            globalState = 0;
            localState = 255;
            recipientID = 0;
            for(int i=0; i<MAX_N_PENDING_STATES; i++) pendingStates[i] = 0;
            alternateSequenceFlag = msg.alternateSequenceFlag;
        }
        /* if we are the starting module we should disregard msgs coming from the previous run!*/
        else if(alternateSequenceFlag != msg.alternateSequenceFlag && (startingModule))
            return;

        int previousState = globalState;
        if(limitPendingOneWay && msg.state<previousState) return;

        int pendingBuffer[] = new int[MAX_N_PENDING_STATES];
        globalState = merge(globalState, pendingStates, msg.state, msg.pending, pendingBuffer);
        int newPending[] = findNew(pendingStates,pendingBuffer);
        //        if(!Arrays.equals(newPending, emptyComparator) && globalState>previousState) {
        //            System.out.println("New pending states module "+myID+": "+Arrays.toString(newPending)+" incoming global state "+globalState+" local was "+previousState);
        //        }
        if(msg.state<previousState && !Arrays.equals(pendingStates, pendingBuffer))
            System.out.println("*** Out-of-order merge");
        int[] tmp = pendingStates; pendingStates = pendingBuffer; pendingBuffer = tmp; /* swap */
        if(globalState>previousState) {
            recipientID = msg.recipientID;
            if( msg.recipientID == myID ) {
                System.out.println("Module "+myID+" updated local state to "+globalState);
                localState = globalState;
            }
        } else if(responsibleForPendingState(newPending)) {
            int pendingState = findResponsiblePendingState(newPending);
            if(!pendingHandled.get(pendingState)) {
                System.out.println("*** "+myID+" setting local state to "+pendingState);
                localState = pendingState;
            }
        }
        if(USE_MONITOR) update();
    }

    private int findResponsiblePendingState(int[] newPending) {
        for(int i=0; i<newPending.length; i++)
            if(provider.isResponsible(myID, newPending[i])) return newPending[i];
        throw new Error("No responsible pending state found");
    }

    private boolean responsibleForPendingState(int[] newPending) {
        for(int i=0; i<newPending.length; i++)
            if(newPending[i]!=0 && provider.isResponsible(myID,newPending[i])) return true;
        return false;
    }

    private int[] findNew(int[] oldStates, int[] newStates) {
        int[] result = new int[MAX_N_PENDING_STATES];
        int index = 0;
        for(int i=0; i<newStates.length; i++) {
            int j=0;
            for(; j<oldStates.length; j++)
                if(newStates[i]!=0 && newStates[i]==oldStates[j]) break;
            if(j<oldStates.length) result[index++]=oldStates[j];
        }
        return result;
    }

    public void setCommunicationProvider(CommunicationProvider provider) {
        this.provider = provider;
    }

    public void addPendingState(int state) {
        pendingHandled.set(state);
        for(int i=0; i<MAX_N_PENDING_STATES; i++) {
            if(pendingStates[i] == state)
                return;
            if(pendingStates[i] == 0) {
                pendingStates[i] = state;
                break;
            }
        }
    }

    public void removePendingState(int state) {
        for(int i=0; i< MAX_N_PENDING_STATES; i++) {
            if(pendingStates[i] == state) {
                pendingStates[i] = 0;
                break;
            }
        }
    }

    public String dump() {
        StringBuffer res = new StringBuffer(this.globalState+" [ ");
        for(int i=0; i<MAX_N_PENDING_STATES; i++) res.append(pendingStates[i]+" ");
        res.append("]");
        return res.toString();
    }

    public void setLimitPendingOneWay(boolean b) {
        this.limitPendingOneWay = b;
    }

    public float getTime() {
        return provider.getTime();
    }

    public void update() {
        MonitorWindow.update(myID,globalState,pendingStates);
    }
}
