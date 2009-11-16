package rar;

public abstract class StateMachine {
    protected DistributedStateManager stateManager;

    public StateMachine() {
        stateManager = new DistributedStateManager();
    }
    
    public void activate() {
        System.out.println("Statemachine activated");
        while(true) {
            this.stateMachine();
            stateManager.senderAct();
        }
    }
    
    protected abstract void stateMachine();
    public abstract void init(int id);
    public abstract void setAPI(Object api);

    public void setLimitPendingOneWay(boolean b) {
        stateManager.setLimitPendingOneWay(b);
    }
}
