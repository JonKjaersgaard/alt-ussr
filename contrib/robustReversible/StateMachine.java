package robustReversible;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import robustReversible.EightToCarRobustnessBatch.Parameters;
import ussr.remote.facade.ParameterHolder;

public abstract class StateMachine {
    public static final float DESYNC_MAGIC = 7.0f;
    
    protected DistributedStateManager stateManager;
    private int saved_init_id = -1;
    private float reset_risk;
    private float reset_interval;
    private float last_reset_check_time;
    private Random resetRandomizer;
    protected ATRONStateMachineAPI api;

    private float desync_interval;

    public StateMachine() {
        stateManager = new DistributedStateManager();
        Parameters p = (Parameters)ParameterHolder.get();
        reset_risk = p.resetRisk;
        reset_interval = p.resetInterval;
        resetRandomizer = p.seedMaybe==null?new Random():new Random(p.seedMaybe);
    }
    
    public void setAPI(ATRONStateMachineAPI api) {
      this.api = api;
    }

    public void activate() {
        System.out.println("Statemachine activated");
        while(true) {
            this.stateMachine();
            stateManager.senderAct();
            if(reset_risk>0) checkReset();
        }
    }
    
    private void checkReset() {
        float time = stateManager.getTime();
        if(last_reset_check_time+reset_interval+desync_interval>time) return;
        last_reset_check_time = time;
        if(resetRandomizer.nextFloat()<reset_risk) {
            System.err.println("RESET("+this.saved_init_id+")@"+time);
            this.reset_module();
            api.reportEvent("RESET",time);
        }
    }

    public void initialize(int id) {
        this.saved_init_id = id;
        this.desync_interval = id/DESYNC_MAGIC;
        this.init(id);
    }
    
    public void reset_module() {
        if(saved_init_id==-1) throw new Error("Reset not possible: init address unavailable");
        this.init(saved_init_id);
    }
    
    protected abstract void stateMachine();
    public abstract void init(int id);
    public abstract boolean checkPendingStateResponsibility(int address, int pendingState);
    
    public void setLimitPendingOneWay(boolean b) {
        stateManager.setLimitPendingOneWay(b);
    }
    
    static private Map<Integer,String> states = Collections.synchronizedMap(new HashMap<Integer,String>());
    
    protected void updateLocalState() {
        if(saved_init_id==-1) return;
        StringBuffer state = new StringBuffer("C=");
        for(int i=0; i<8; i++)
            state.append(api.isConnected(i)?"1":0);
        state.append(",A="+api.getAngularPositionDegrees());
        states.put(saved_init_id, state.toString());
    }
    
    protected String getGlobalState() {
        StringBuffer buffer = new StringBuffer();
        for(Integer i: states.keySet())
            buffer.append("#"+i+"="+states.get(i)+" ");
        return buffer.toString();
    }
}
