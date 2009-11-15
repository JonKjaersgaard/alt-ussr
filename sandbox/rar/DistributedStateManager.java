package rar;

public class DistributedStateManager {
    private static byte MAGIC_HEADER = 107;
    private static byte HEADER_PLUS_FIXED_PAYLOAD = 4;
    protected static final int WAITTIME = 100;
    protected static final float WAITTIME_MS = WAITTIME/1000.0f;
    
    private class EightMsg {

        boolean alternateSequenceFlag;
        int state;
        int[] pending = new int[MAX_N_PENDING_STATES];
        int recipientID;

        EightMsg(byte[] raw) {
            if(raw[0]!=MAGIC_HEADER) throw new Error("Incorrect packet");
            alternateSequenceFlag = raw[1]!=0;
            state = raw[2];
            recipientID = raw[3];
            for(int i=0; i<MAX_N_PENDING_STATES; i++)
                pending[i] = raw[4+i];
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

    private CommunicationProvider provider;
    private int myID;
    private int localState;
    private boolean activated = false;
    private static final int MAX_N_PENDING_STATES = 5;
    private int[] pendingStates = new int[MAX_N_PENDING_STATES];
    private int globalState;
    private int recipientID;
    private boolean startingModule;
    private boolean alternateSequenceFlag;
    private float lastTime;
    
    public DistributedStateManager() {
        startSender();
    }
    
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
    
    private void startSender() {
        if(true) return;
        new Thread() {
            public void run() {
                synchronized(DistributedStateManager.this) {
                    try {
                        DistributedStateManager.this.wait();
                    } catch(InterruptedException exn) {
                        throw new Error("Interrupted");
                    }
                }
                while(true) {
                    EightMsg msg; 
                    synchronized(DistributedStateManager.this) {
                        msg = new EightMsg(alternateSequenceFlag, globalState, recipientID, pendingStates);
                    }
                    provider.broadcastMessage(msg.encode());
                    provider.delay(WAITTIME);
                }
            }
        }.start();
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
        /* flip the flag (can't do that with ~alternateSequenceFlag, beware!) */
        if(alternateSequenceFlag)
            alternateSequenceFlag = false;
        else
            alternateSequenceFlag = true;  
        this.notifyAll();
    }

    public void init(int myID) {
        this.myID = myID;
    }

    public int getGlobalState() {
        return globalState;
    }

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

        int pendingBuffer[] = new int[MAX_N_PENDING_STATES];
        globalState = merge(globalState, pendingStates, msg.state, msg.pending, pendingBuffer);
        int[] tmp = pendingStates; pendingStates = pendingBuffer; pendingBuffer = tmp; /* swap */
        if(globalState>previousState) {
            recipientID = msg.recipientID;
            if( msg.recipientID == myID )
                localState = globalState;
        }
    }

    public void setCommunicationProvider(CommunicationProvider provider) {
        this.provider = provider;
    }

    public void addPendingState(int state) {
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

}
