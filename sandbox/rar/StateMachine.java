package rar;

import java.util.Random;

import rar.EightToCarRobustnessBatch.Parameters;
import ussr.remote.facade.ParameterHolder;

public abstract class StateMachine {
    protected DistributedStateManager stateManager;
    private int saved_init_id = -1;
    private float reset_risk;
    private float reset_interval;
    private float last_reset_check_time;
    private Random resetRandomizer = new Random();
    protected ATRONStateMachineAPI api;

    public StateMachine() {
        stateManager = new DistributedStateManager();
        Parameters p = (Parameters)ParameterHolder.get();
        reset_risk = p.resetRisk;
        reset_interval = p.resetInterval;
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
        if(last_reset_check_time+reset_interval>time) return;
        last_reset_check_time = time;
        if(resetRandomizer.nextFloat()<reset_risk) {
            System.err.println("RESET");
            this.reset_module();
            api.reportEvent("RESET",time);
        }
    }

    public void initialize(int id) {
        this.saved_init_id = id;
        this.init(id);
    }
    
    public void reset_module() {
        if(saved_init_id==-1) throw new Error("Reset not possible: init address unavailable");
        this.init(saved_init_id);
    }
    
    protected abstract void stateMachine();
    public abstract void init(int id);

    public void setLimitPendingOneWay(boolean b) {
        stateManager.setLimitPendingOneWay(b);
    }
}
